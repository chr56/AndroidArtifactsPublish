/*
 * Copyright (c) 2022~2023 chr_56
 */

package tools.release.git

import org.gradle.api.Project
import java.io.ByteArrayOutputStream

fun Project.getGitHash(shortHash: Boolean): String =
    ByteArrayOutputStream().use { stdout ->
        exec {
            if (shortHash) {
                commandLine("git", "rev-parse", "--short", "HEAD")
            } else {
                commandLine("git", "rev-parse", "HEAD")
            }
            standardOutput = stdout
        }
        stdout
    }.toString().trim()
