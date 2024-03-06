/*
 *  Copyright (c) 2022~2024 chr_56
 */

@file:Suppress("SpellCheckingInspection")

package tools.release.upload

import org.gradle.internal.impldep.com.google.gson.annotations.SerializedName


data class GitHubRelease(
    @SerializedName("id") val id: Int,
    @SerializedName("node_id") val nodeId: String,
    @SerializedName("name") val name: String?,
    @SerializedName("url") val url: String,
    @SerializedName("html_url") val htmlUrl: String,
    @SerializedName("author") val author: User,
    @SerializedName("target_commitish") val targetCommitish: String,
    @SerializedName("tag_name") val tagName: String,
    @SerializedName("draft") val draft: Boolean,
    @SerializedName("prerelease") val prerelease: Boolean,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("published_at") val publishedAt: String,
    @SerializedName("assets") val assets: List<GitHubAsset>?,
    @SerializedName("assets_url") val assetsUrl: String?,
    @SerializedName("tarball_url") val tarballUrl: String,
    @SerializedName("zipball_url") val zipballUrl: String,
    @SerializedName("upload_url") val uploadUrl: String,
    @SerializedName("body") val body: String?,
)

data class GitHubAsset(
    @SerializedName("id") val id: Int,
    @SerializedName("node_id") val nodeId: String,
    @SerializedName("name") val name: String?,
    @SerializedName("url") val url: String,
    @SerializedName("browser_download_url") val browserDownloadUrl: String,
    @SerializedName("uploader") val uploader: User,
    @SerializedName("content_type") val contentType: String?,
    @SerializedName("state") val state: String,
    @SerializedName("size") val size: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("download_count") val downloadCount: Int,
)

data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("node_id") val nodeId: String,
    @SerializedName("url") val url: String,
    @SerializedName("html_url") val htmlUrl: String,
    @SerializedName("login") val login: String,
    @SerializedName("type") val type: String?,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("gravatar_id") val gravatarId: String,
    @SerializedName("organizationsUrl") val organizationsUrl: String?,
)