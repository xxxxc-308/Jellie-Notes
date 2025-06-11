package com.cloudsurfe.editor.core

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import com.cloudsurfe.editor.paragraph.RichParagraph

internal class EditorSpan(
    internal val key: Int? = null,
    val children: MutableList<EditorSpan> = mutableListOf(),
    var paragraph: RichParagraph,
    var parent: EditorSpan? = null,
    var text: String = "",
    var textRange: TextRange = TextRange(start = 0, end = 0),
    var spanStyle: SpanStyle = SpanStyle(),
    var editorSpanStyle: EditorSpanStyle = EditorSpanStyle.Default
){



}