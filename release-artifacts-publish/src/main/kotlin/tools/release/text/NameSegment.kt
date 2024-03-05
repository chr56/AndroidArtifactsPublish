/*
 *  Copyright (c) 2022~2024 chr_56
 */

package tools.release.text

import com.android.build.api.variant.ApplicationVariant
import com.android.build.api.variant.BuiltArtifact
import com.android.build.api.variant.FilterConfiguration

sealed class NameSegment(val code: Char) {
    abstract fun produce(variant: ApplicationVariant, artifact: BuiltArtifact?): CharSequence?

    data object VersionName : NameSegment('V') {
        override fun produce(variant: ApplicationVariant, artifact: BuiltArtifact?): CharSequence? {
            val versionNameFromBuiltArtifact = artifact?.versionName
            val versionNameFromVariant = variant.outputs.mapNotNull { it.versionName.orNull }.commonPrefix()
            return versionNameFromBuiltArtifact ?: versionNameFromVariant
        }
    }

    data object Abi : NameSegment('A') {
        override fun produce(variant: ApplicationVariant, artifact: BuiltArtifact?): CharSequence? {
            if (artifact == null) return null
            val abiList =
                artifact.filters
                    .filter { it.filterType == FilterConfiguration.FilterType.ABI }
                    .takeIf { it.isNotEmpty() }
            return abiList?.joinToString(separator = "-") ?: "universal"
        }
    }

    data object Time : NameSegment('T') {
        override fun produce(variant: ApplicationVariant, artifact: BuiltArtifact?): CharSequence = currentTimeString
    }

    class GitHash(val hash: String) : NameSegment('h') {
        override fun produce(variant: ApplicationVariant, artifact: BuiltArtifact?): CharSequence = hash
    }
}