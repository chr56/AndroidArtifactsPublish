/*
 *  Copyright (c) 2022~2024 chr_56
 */

package tools.release

import org.gradle.api.Project
import tools.release.git.getGitHash
import tools.release.text.NameSegment

object Default {

    const val outputDir: String = Constants.DEFAULT_PRODUCTS_DIR

    val hashAlgorithm: Set<String> = setOf("SHA-1", "SHA-256")

    fun nameStyle(project: Project, debugRelease: Boolean): List<NameSegment> =
        if (!debugRelease) {
            listOf(NameSegment.VersionName)
        } else {
            listOf(NameSegment.VersionName, NameSegment.GitHash(project.getGitHash(true)), NameSegment.Time)
        }
}