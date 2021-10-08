package com.synthesizer.source.mars.util

import java.util.*

fun String.capitalize(): String {
    return replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.ENGLISH)
        else it.toString()
    }
}