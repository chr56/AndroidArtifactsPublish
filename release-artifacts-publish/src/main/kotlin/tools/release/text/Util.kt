/*
 * Copyright (c) 2022~2023 chr_56
 */

package tools.release.text

import com.android.build.api.variant.ApplicationVariant
import java.io.File
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

val currentTimeString: String
    get() = SimpleDateFormat("yyMMddHHmmss", Locale.ENGLISH).format(Calendar.getInstance().time)

fun String.shiftFirstLetter(): String {
    if (isBlank()) return this
    return this[0].uppercaseChar() + this.substring(1)
}

val ApplicationVariant.canonicalName: String get() = name.shiftFirstLetter()

internal fun Collection<String>.commonPrefix(): String? {
    if (isEmpty()) return null
    if (size == 1) return first()

    var index = -1
    while (true) {
        index++
        val chars = mapNotNull { it.getOrNull(index) }.takeIf { it.size == this.size }?.toSet()
        if (chars != null && chars.size == this.size) {
            continue
        } else {
            index--
            break
        }
    }

    return if (index >= 0) first().substring(index) else null
}