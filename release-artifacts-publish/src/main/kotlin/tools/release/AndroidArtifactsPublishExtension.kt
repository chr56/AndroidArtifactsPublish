package tools.release

import org.gradle.api.Project


abstract class AndroidArtifactsPublishExtension(project: Project) {

    /**
     * target output directory path
     */
    abstract var outputDir: String?

    /**
     * hash methods
     * @see [tools.release.file.HashAlgorithm]
     */
    abstract var hashAlgorithm: Set<String>?

}