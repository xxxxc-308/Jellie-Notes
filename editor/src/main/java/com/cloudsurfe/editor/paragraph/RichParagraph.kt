package com.cloudsurfe.editor.paragraph

import androidx.compose.ui.text.ParagraphStyle
import com.cloudsurfe.editor.core.EditorSpan
import com.cloudsurfe.editor.paragraph.type.DefaultParagraph
import com.cloudsurfe.editor.paragraph.type.ParagraphType

internal class RichParagraph(
    val key: Int = 0,
    val children: MutableList<EditorSpan> = mutableListOf(),
    var paragraphStyle: ParagraphStyle = DefaultParagraphStyle,
    var type: ParagraphType = DefaultParagraph()
){
    companion object{
        val DefaultParagraphStyle = ParagraphStyle()
    }
}