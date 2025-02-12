import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    `kotlin-dsl`

    id("com.gradle.plugin-publish") version "1.2.1"
}

version = "0.1.2"
group = "io.github.chr56"


java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}


kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}
tasks.named("compileKotlin", KotlinCompilationTask::class.java) {
    compilerOptions {
        apiVersion.set(KotlinVersion.KOTLIN_2_1)
        languageVersion.set(KotlinVersion.KOTLIN_2_1)
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

dependencies {
    compileOnly(gradleApi())
    compileOnly(libs.androidGradlePluginAPI)


    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
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