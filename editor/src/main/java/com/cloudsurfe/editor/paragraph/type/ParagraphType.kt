package com.cloudsurfe.editor.paragraph.type

import androidx.compose.ui.text.ParagraphStyle
import com.cloudsurfe.editor.core.EditorSpan
import com.cloudsurfe.editor.core.EditorTextConfig

internal interface ParagraphType {
    fun getStyle(config: EditorTextConfig): ParagraphStyle

    val startEditorSpan: EditorSpan

    fun getNextParagraphType(): ParagraphType

    fun copy(): ParagraphType

    companion object {
        val ParagraphType.startText: String get() = startEditorSpan.text
    }
}