/*
 * Copyright (c) 2022~2023 chr_56
 */

package tools.release

import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.ApplicationVariant
import com.android.build.api.variant.BuiltArtifact
import com.android.build.api.variant.BuiltArtifacts
import com.android.build.api.variant.BuiltArtifactsLoader
import org.gradle.api.DefaultTask
import org.gradle.api.file.Directory
import org.gradle.api.file.RegularFile
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.findByType
import tools.release.file.assureDir
import tools.release.file.hashValidationFile
import tools.release.git.getGitHash
import tools.release.text.NameGenerator
import tools.release.text.NameSegment
import tools.release.text.canonicalName
import tools.release.zip.gzip
import java.io.File
import javax.inject.Inject

open class PublishArtifactsTask @Inject constructor(
    private val name: String,
    private val variant: ApplicationVariant,
) : DefaultTask() {

    init {
        description = "Publish Artifacts to target directory"
    }

    private var outputDirectoryPath: String = Constants.DEFAULT_PRODUCTS_DIR

    private var hashAlgorithm: Set<String> = setOf("SHA-1", "SHA-256")

    private fun loadConfig() {
        val pluginExtension = project.extensions.findByType<AndroidArtifactsPublishExtension>()
        if (pluginExtension != null) {
            if (pluginExtension.outputDir != null) {
                outputDirectoryPath = pluginExtension.outputDir!!
            }
            if (!pluginExtension.hashAlgorithm.isNullOrEmpty()) {
                hashAlgorithm = pluginExtension.hashAlgorithm!!
            }
        }
    }

    @TaskAction
    fun publish() {
        loadConfig()
        collect()
    }

    private fun collect() {
        val loader: BuiltArtifactsLoader = variant.artifacts.getBuiltArtifactsLoader()
        val apkDirectory: Directory = variant.artifacts.get(SingleArtifact.APK).get()
        val mappingFile: RegularFile? = variant.artifacts.get(SingleArtifact.OBFUSCATION_MAPPING_FILE).orNull

        val builtApkArtifacts: BuiltArtifacts? = loader.load(apkDirectory)
        if (builtApkArtifacts == null) {
            println("No apks generated!")
            return
        }

        val nameStyle: List<NameSegment> =
            if (variant.buildType != "debug") {
                listOf(NameSegment.VersionName)
            } else {
                listOf(NameSegment.VersionName, NameSegment.GitHash(project.getGitHash(true)), NameSegment.Time)
            }

        val outputDir = File(project.rootDir, outputDirectoryPath).also { it.mkdirs() }
        val destinationDir: File = File(outputDir, variant.canonicalName).assureDir()

        exportArtifact(
            variant,
            builtApkArtifacts.elements,
            mappingFile,
            name,
            nameStyle,
            destinationDir,
            hashAlgorithm,
        )
    }

    companion object {
        private fun exportArtifact(
            variant: ApplicationVariant,
            artifacts: Collection<BuiltArtifact>,
            mappingFile: RegularFile?,
            name: String,
            nameStyle: List<NameSegment>,
            destinationDir: File,
            enabledHashAlgorithm: Collection<String>,
            overwrite: Boolean = true,
        ) {

            for (artifact in artifacts) {
                val apkName = NameGenerator.generateApkName(name, variant, artifact, nameStyle)
                val destination = File(destinationDir, "$apkName.apk")
                val file = File(artifact.outputFile)
                file.copyTo(destination, overwrite)
                for (algorithm in enabledHashAlgorithm) {
                    destination.hashValidationFile(algorithm)
                }
                notify(destination)
            }

            val mapping: File? = mappingFile?.asFile
            if (mapping != null && mapping.exists()) {
                val mappingName = NameGenerator.generateMappingName(name, variant, nameStyle)
                val destination = File(destinationDir, "$mappingName.txt.gz")
                val file = mapping.gzip()
                file.copyTo(destination, overwrite)
                notify(destination)
            }
        }

        private fun notify(file: File) {
            val osName = System.getProperty("os.name").lowercase()
            val location =
                if (osName.contains("windows")) {
                    file.toURI().toString().replace("file:/", "file:///")
                } else {
                    file.toURI().toString()
                }
            println("Copied! $location")
        }
    }


}