package com.cloudsurfe.editor.paragraph

import androidx.compose.ui.text.ParagraphStyle
import com.cloudsurfe.editor.core.RichSpan
import com.cloudsurfe.editor.core.TextConfig

internal interface ParagraphType{

     fun getStyle(config: TextConfig): ParagraphStyle

     val startRichSpan: RichSpan

    fun getNextParagraphType(): ParagraphType

    fun copy(): ParagraphType

    companion object{
        
    }

}