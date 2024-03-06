/*
 *  Copyright (c) 2022~2024 chr_56
 */

package tools.release.upload

import org.gradle.internal.impldep.com.google.gson.Gson
import org.gradle.internal.impldep.com.google.gson.GsonBuilder
import org.gradle.internal.impldep.org.apache.http.HttpEntity
import org.gradle.internal.impldep.org.apache.http.client.ClientProtocolException
import org.gradle.internal.impldep.org.apache.http.client.methods.CloseableHttpResponse
import org.gradle.internal.impldep.org.apache.http.client.methods.HttpGet
import org.gradle.internal.impldep.org.apache.http.client.methods.HttpPost
import org.gradle.internal.impldep.org.apache.http.client.methods.HttpUriRequest
import org.gradle.internal.impldep.org.apache.http.entity.ContentType
import org.gradle.internal.impldep.org.apache.http.entity.FileEntity
import org.gradle.internal.impldep.org.apache.http.impl.client.CloseableHttpClient
import org.gradle.internal.impldep.org.apache.http.impl.client.HttpClientBuilder
import org.gradle.internal.impldep.org.apache.http.message.BasicHeader
import java.io.Closeable
import java.io.File
import java.io.IOException

class GitHubClient(token: String) : Closeable {

    private val httpClient: CloseableHttpClient = prepareClient(token)

    private val gson: Gson by lazy {
        GsonBuilder()
            .setPrettyPrinting()
            .create()
    }

    @Throws(IOException::class, ClientProtocolException::class)
    fun getRelease(owner: String, repo: String, tag: String): ResponseResult<GitHubRelease> {

        val response: CloseableHttpResponse =
            execute(HttpGet("https://$BASE_URL/repos/$owner/$repo/releases/tags/$tag")) ?: return ResponseResult.Error()

        val statusLine = response.statusLine
        return when (statusLine.statusCode) {
            200 -> {
                parseResult(response.entity, statusLine.statusCode, GitHubRelease::class.java)
            }

            404 -> {
                println("Release Not Found: $owner/$repo tag $tag")
                ResponseResult.Failed(statusLine.statusCode)
            }

            else -> {
                println("Error: $statusLine")
                ResponseResult.Failed(statusLine.statusCode)
            }
        }

    }

    @Throws(IOException::class, ClientProtocolException::class)
    fun uploadReleaseAsset(owner: String, repo: String, releaseId: String, file: File): ResponseResult<GitHubAsset> {
        require(file.exists() && file.isFile) { "${file.path} is not an existed file!" }
        val filename = file.name
        val request =
            HttpPost("https://$UPLOAD_URL/repos/$owner/$repo/releases/$releaseId/assets?name=$filename").apply {
                entity = FileEntity(file, ContentType.APPLICATION_OCTET_STREAM)
            }

        val response: CloseableHttpResponse = execute(request) ?: return ResponseResult.Error()

        val statusLine = response.statusLine
        return when (statusLine.statusCode) {
            201 -> {
                parseResult(response.entity, statusLine.statusCode, GitHubAsset::class.java)
            }

            422 -> {
                println("Already Exist!")
                ResponseResult.Failed(statusLine.statusCode)
            }

            else -> {
                println("Error: $statusLine")
                ResponseResult.Failed(statusLine.statusCode)
            }
        }
    }

    private fun <T> parseResult(entity: HttpEntity?, statusCode: Int, type: Class<T>): ResponseResult<T> {
        return if (entity != null) {
            entity.content.bufferedReader().use { reader ->
                val text = reader.readText()
                val asset = gson.fromJson(text, type)
                ResponseResult.OK(statusCode, asset)
            }
        } else {
            println("No responding")
            ResponseResult.Error()
        }
    }


    private fun execute(request: HttpUriRequest): CloseableHttpResponse? {
        return try {
            httpClient.execute(request)
        } catch (e: ClientProtocolException) {
            e.printStackTrace()
            println("Protocol Error")
            null
        } catch (e: IOException) {
            e.printStackTrace()
            println("IOException!!")
            null
        }
    }

    override fun close() {
        httpClient.close()
    }

    companion object {
        const val BASE_URL = "api.github.com"
        const val UPLOAD_URL = "uploads.github.com"

        private fun prepareClient(token: String): CloseableHttpClient =
            HttpClientBuilder.create()
                .setDefaultHeaders(
                    listOf(
                        BasicHeader("Accept", "application/vnd.github+json"),
                        BasicHeader("Authorization", "Bearer $token"),
                        BasicHeader("X-GitHub-Api-Version", "2022-11-28"),
                    )
                )
                .build()
    }
}


