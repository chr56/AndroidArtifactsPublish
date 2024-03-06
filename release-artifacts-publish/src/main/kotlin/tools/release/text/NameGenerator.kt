/*
 *  Copyright (c) 2022~2024 chr_56
 */

package tools.release.text

import com.android.build.api.variant.ApplicationVariant
import com.android.build.api.variant.BuiltArtifact

object NameGenerator {

    fun generateApkName(
        name: String,
        variant: ApplicationVariant,
        artifact: BuiltArtifact,
        style: List<NameSegment>,
    ): String = "${name}_${generate(style, variant, artifact)}"


    fun generateMappingName(
        name: String,
        variant: ApplicationVariant,
        style: List<NameSegment>,
    ): String = "${name}_mapping_${generate(style, variant)}"

    private fun generate(
        style: List<NameSegment>,
        variant: ApplicationVariant,
        artifact: BuiltArtifact? = null,
        separator: CharSequence = "_",
    ): String = style.mapNotNull { it.produce(variant, artifact) }.joinToString(separator)
}