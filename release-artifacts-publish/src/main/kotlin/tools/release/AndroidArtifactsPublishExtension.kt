package tools.release

import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty
import org.gradle.kotlin.dsl.property
import org.gradle.kotlin.dsl.setProperty


open class AndroidArtifactsPublishExtension(project: Project) {

    /**
     * target output directory path
     */
    var outputDir: Property<String> = project.objects.property()
        internal set

    /**
     * hash methods
     * @see [tools.release.file.HashAlgorithm]
     */
    var hashAlgorithm: SetProperty<String> = project.objects.setProperty()
        internal set

}