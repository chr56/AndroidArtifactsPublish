import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`

    id("com.gradle.plugin-publish") version "1.2.1"
}

version = "0.1.0"
group = "io.github.chr56"


java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        apiVersion = "1.9"
        languageVersion = "1.9"
        jvmTarget = "17"
    }
}

dependencies {
    compileOnly(gradleApi())
    compileOnly(libs.androidGradlePluginAPI)
}

@Suppress("UnstableApiUsage")
gradlePlugin {
    plugins {
        create("android-artifacts-publish") {
            id = "io.github.chr56.tools.release"
            implementationClass = "tools.release.AndroidArtifactsPublishPlugin"

            displayName = "Android Artifacts Publish"
            description = "A tiny Gradle Plugin for publishing Android Artifacts!"
            tags.set(listOf("android"))
        }
    }

    website.set("https://github.com/chr56/AndroidArtifactsPublish")
    vcsUrl.set("https://github.com/chr56/AndroidArtifactsPublish")
}