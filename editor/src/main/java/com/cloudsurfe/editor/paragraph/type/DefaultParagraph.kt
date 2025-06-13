package com.cloudsurfe.editor.paragraph.type

import androidx.compose.ui.text.ParagraphStyle
import com.cloudsurfe.editor.core.EditorSpan
import com.cloudsurfe.editor.core.EditorTextConfig
import com.cloudsurfe.editor.paragraph.EditorParagraph

internal class DefaultParagraph() : ParagraphType {
    private val style: ParagraphStyle =
        ParagraphStyle()

    override fun getStyle(config: EditorTextConfig): ParagraphStyle {
        return style
    }

    override val startEditorSpan: EditorSpan = EditorSpan(paragraph = EditorParagraph(type = this))

    override fun getNextParagraphType(): ParagraphType =
        DefaultParagraph()

    override fun copy(): ParagraphType =
        DefaultParagraph()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DefaultParagraph) return false

        return true
    }

    override fun hashCode(): Int {
        return 0
    }
}