/*
 * Copyright (c) 2022~2023 chr_56
 */

package tools.release

import com.android.build.api.variant.ApplicationVariant
import org.gradle.api.tasks.TaskContainer
import tools.release.text.canonicalName

/**
 * register PublishArtifactsTask for specific ApplicationVariant
 * @param name generic name for artifacts. It would be the prefix of all output
 * @param variant target [ApplicationVariant] that needs registering
 */
fun TaskContainer.registerPublishTask(
    name: String,
    variant: ApplicationVariant,
) {
    val variantName: String = variant.canonicalName
    register(
        "publish$variantName", PublishArtifactsTask::class.java,
        name, variant
    ).configure {
        dependsOn("assemble$variantName")
    }
}

/**
 * Java fallback
 * @see [TaskContainer.registerPublishTask]
 */
@JvmName("RegisterPublishTask")
fun registerPublishTask(
    tasks: TaskContainer,
    name: String,
    variant: ApplicationVariant,
) = tasks.registerPublishTask(name, variant)