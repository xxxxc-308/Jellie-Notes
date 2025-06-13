package com.cloudsurfe.editor.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.isSpecified

internal fun SpanStyle.customMerge(
    other: SpanStyle?,
    textDecoration: TextDecoration? = null
): SpanStyle {
    if (other == null) return this

    val firstTextDecoration = textDecoration ?: this.textDecoration
    val secondTextDecoration = other.textDecoration

    return if (
        firstTextDecoration != null &&
        secondTextDecoration != null &&
        firstTextDecoration != secondTextDecoration
    ) {
        this.merge(
            other.copy(
                textDecoration = TextDecoration.combine(
                    listOf(
                        firstTextDecoration,
                        secondTextDecoration
                    )
                )
            )
        )
    } else {
        this.merge(other)
    }
}

internal fun SpanStyle.unmerge(
    other: SpanStyle?,
): SpanStyle {
    if (other == null) return this

    return SpanStyle(
        color = if (other.color.isSpecified) Color.Unspecified else this.color,
        fontFamily = if (other.fontFamily != null) null else this.fontFamily,
        fontSize = if (other.fontSize.isSpecified) TextUnit.Unspecified else this.fontSize,
        fontWeight = if (other.fontWeight != null) null else this.fontWeight,
        fontStyle = if (other.fontStyle != null) null else this.fontStyle,
        fontSynthesis = if (other.fontSynthesis != null) null else this.fontSynthesis,
        fontFeatureSettings = if (other.fontFeatureSettings != null) null else this.fontFeatureSettings,
        letterSpacing = if (other.letterSpacing.isSpecified) TextUnit.Unspecified else this.letterSpacing,
        baselineShift = if (other.baselineShift != null) null else this.baselineShift,
        textGeometricTransform = if (other.textGeometricTransform != null) null else this.textGeometricTransform,
        localeList = if (other.localeList != null) null else this.localeList,
        background = if (other.background.isSpecified) Color.Unspecified else this.background,
        textDecoration = if (other.textDecoration != null && other.textDecoration == this.textDecoration) {
            null
        } else if (
            other.textDecoration != null &&
            this.textDecoration != null &&
            other.textDecoration!! in this.textDecoration!!
        ) {
            this.textDecoration!! - other.textDecoration!!
        } else this.textDecoration,
        shadow = if (other.shadow != null) null else this.shadow,
    )
}
