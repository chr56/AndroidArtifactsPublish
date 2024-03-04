/*
 * Copyright (c) 2022~2023 chr_56
 */

package tools.release

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidArtifactsPublishPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.plugins.findPlugin(ANDROID_GRADLE_PLUGIN)
            ?: throw RuntimeException("Application Android Gradle Plugin (com.android.application) not exists!")
        target.extensions.create(Constants.EXTENSION, AndroidArtifactsPublishExtension::class.java, target)
    }

    companion object {
        private const val ANDROID_GRADLE_PLUGIN = "com.android.application"
    }
}