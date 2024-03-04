package tools.release

import org.gradle.api.Project
import org.gradle.api.provider.SetProperty
import org.gradle.kotlin.dsl.setProperty


abstract class AndroidArtifactsPublishExtension(project: Project) {

    /**
     * target output directory path
     */
    abstract var outputDir: String?

    /**
     * hash methods
     * @see [tools.release.file.HashAlgorithm]
     */
    var hashAlgorithm: SetProperty<String> = project.objects.setProperty()
        internal set

}