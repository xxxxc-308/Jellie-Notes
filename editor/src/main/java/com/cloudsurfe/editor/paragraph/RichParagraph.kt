package com.cloudsurfe.editor.paragraph

import androidx.compose.ui.text.ParagraphStyle
import com.cloudsurfe.editor.core.RichSpan

internal class RichParagraph(
    val key: Int = 0,
    val children: MutableList<RichSpan> = mutableListOf(),
    var paragraphStyle: ParagraphStyle = DefaultParagraphStyle,
    var type: ParagraphType= DefaultParagraph()
){
    companion object{
        val DefaultParagraphStyle = ParagraphStyle()
    }
}