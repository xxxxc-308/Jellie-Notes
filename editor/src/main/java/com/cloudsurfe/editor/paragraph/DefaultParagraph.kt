package com.cloudsurfe.editor.paragraph

import androidx.compose.ui.text.ParagraphStyle
import com.cloudsurfe.editor.core.RichSpan
import com.cloudsurfe.editor.core.TextConfig

internal class DefaultParagraph() : ParagraphType {

    private val style: ParagraphStyle =
        ParagraphStyle()

    override fun getStyle(config: TextConfig): ParagraphStyle {
        return style
    }

    override val startRichSpan: RichSpan = RichSpan(paragraph = RichParagraph(type = this))

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