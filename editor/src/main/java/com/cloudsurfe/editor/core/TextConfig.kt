package com.cloudsurfe.editor.core
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import com.cloudsurfe.editor.paragraph.UnorderedListStyleType

class TextConfig internal constructor(
    private val updateText: () -> Unit
) {
    var linkColor: Color = Color.Blue
        set(value){
            field = value
            updateText()
        }

    var linkTextDecoration : TextDecoration = TextDecoration.Underline
        set(value){
            field = value
            updateText()
        }

    var codeSpanColor : Color = Color.Unspecified
        set(value){
            field = value
            updateText()
        }

    var codeSpanBackgroundColor : Color = Color.Transparent
        set(value){
            field = value
            updateText()
        }

    var codeSpanStrokeColor : Color = Color.LightGray
        set(value){
            field = value
            updateText()
        }



}

internal const val DefaultListIndent = 38

internal val DefaultUnorderedListStyleType =
    UnorderedListStyleType.from("•", "◦", "▪")


