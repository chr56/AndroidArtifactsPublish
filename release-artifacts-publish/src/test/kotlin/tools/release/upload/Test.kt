/*
 *  Copyright (c) 2022~2024 chr_56
 */

package tools.release.upload

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.File


class Test {

    companion object {
        @JvmStatic
        @BeforeAll
        fun checkEnvironmentVariable() {
            val token = System.getenv("TOKEN")
            assertNotEquals(true, token.isNullOrEmpty(), "GitHub TOKEN Not Found")
        }
    }

    private val gitHubClient = GitHubClient(System.getenv("TOKEN"))


    @Test
    fun testGetRelease() {
        val release = getRelease(gitHubClient)
        assertNotNull(release, "getRelease")
    }

    private fun getRelease(gitHubClient: GitHubClient): GitHubRelease? {
        when (val responseResult = gitHubClient.getRelease(
            "chr56",
            "AndroidArtifactsPublish",
            "0.1.1"
        )) {
            is ResponseResult.Error -> println("Error occurs")
            is ResponseResult.Failed -> println("Failed")
            is ResponseResult.OK -> {
                val gitHubRelease = responseResult.data
                println(gitHubRelease)
                println()
                gitHubRelease.assets?.forEach { asset ->
                    println("Asset:")
                    println(asset)
                }
                return gitHubRelease
            }
        }
        return null
    }


    @Test
    fun testGetReleases() {
        val release = getReleases(gitHubClient)
        assertNotNull(release, "getReleases")
    }

    private fun getReleases(gitHubClient: GitHubClient): GitHubReleasesList? {
        when (val responseResult = gitHubClient.getReleases(
            "chr56",
            "AndroidArtifactsPublish",
        )) {
            is ResponseResult.Error -> println("Error occurs")
            is ResponseResult.Failed -> println("Failed")
            is ResponseResult.OK -> {
                val gitHubReleases = responseResult.data
                for (gitHubRelease in gitHubReleases) {
                    println(gitHubRelease)
                    println()
                    gitHubRelease.assets?.forEach { asset ->
                        println("Asset:")
                        println(asset)
                    }
                }
                return gitHubReleases
            }
        }
        return null
    }


    @Test
    fun testGetLatestRelease() {
        val release = getLatestRelease(gitHubClient)
        assertNotNull(release, "getLatestRelease")
    }

    private fun getLatestRelease(gitHubClient: GitHubClient): GitHubRelease? {
        when (val responseResult = gitHubClient.getReleaseLatest(
            "chr56",
            "AndroidArtifactsPublish",
        )) {
            is ResponseResult.Error -> println("Error occurs")
            is ResponseResult.Failed -> println("Failed")
            is ResponseResult.OK -> {
                val gitHubRelease = responseResult.data
                println(gitHubRelease)
                println()
                gitHubRelease.assets?.forEach { asset ->
                    println("Asset:")
                    println(asset)
                }
                return gitHubRelease
            }
        }
        return null
    }


    @Test
    fun testUpload() {

        var obj: Any?

        // create
        val id = createRelease(gitHubClient)
        assertNotEquals(0, id)

        // update
        obj = updateRelease(gitHubClient, id)
        assertNotNull(obj, "updateRelease")

        // artifact upload
        obj = uploadReleaseAsset(gitHubClient, id)
        assertNotNull(obj, "uploadReleaseAsset")

    }


    private fun createRelease(gitHubClient: GitHubClient): Long {
        when (val responseResult = gitHubClient.createRelease(
            "chr56",
            "AndroidArtifactsPublish",
            "test-publish",
            "{Test} Android Artifacts Publish",
            prerelease = true
        )) {
            is ResponseResult.Error -> println("Error occurs")
            is ResponseResult.Failed -> println("Failed")
            is ResponseResult.OK -> {
                val message = responseResult.data
                println(message)
                return message.id
            }
        }
        return 0
    }

    private fun updateRelease(gitHubClient: GitHubClient, id: Long): GitHubRelease? {
        when (val responseResult = gitHubClient.updateRelease(
            "chr56",
            "AndroidArtifactsPublish",
            id = id,
            prerelease = false,
        )) {
            is ResponseResult.Error -> println("Error occurs")
            is ResponseResult.Failed -> println("Failed")
            is ResponseResult.OK -> {
                val message = responseResult.data
                println(message)
                return message
            }
        }
        return null
    }

    private fun uploadReleaseAsset(gitHubClient: GitHubClient, id: Long): GitHubAsset? {
        when (val responseResult = gitHubClient.uploadReleaseAsset(
            "chr56",
            "AndroidArtifactsPublish",
            releaseId = id.toString(),
            createTestAssetFile(),
        )) {
            is ResponseResult.Error -> println("Error occurs")
            is ResponseResult.Failed -> println("Failed")
            is ResponseResult.OK -> {
                val asset = responseResult.data
                println(asset)
                return asset
            }
        }
        return null
    }

    private fun createTestAssetFile(): File {
        val file = File("Test.file")
        file.writer().use {
            it.append("Hello!")
            it.append('\n').append('\n')
            it.append("This is a Test File.")
        }
        return file
    }
}
