package com.cloudsurfe.editor.paragraph.type

import androidx.compose.ui.text.ParagraphStyle
import com.cloudsurfe.editor.core.EditorSpan
import com.cloudsurfe.editor.core.EditorTextConfig

internal interface ParagraphType {
    fun getStyle(config: EditorTextConfig): ParagraphStyle

    val startRichSpan: EditorSpan

    fun getNextParagraphType(): ParagraphType

    fun copy(): ParagraphType

    companion object {

    }
}