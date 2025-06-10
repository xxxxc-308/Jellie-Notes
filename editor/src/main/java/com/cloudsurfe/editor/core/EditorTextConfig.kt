package com.cloudsurfe.editor.core

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import com.cloudsurfe.editor.paragraph.type.OrderedListStyleType
import com.cloudsurfe.editor.paragraph.type.UnorderedListStyleType

public class EditorTextConfig internal constructor(
    private val updateText: () -> Unit
) {
    public var linkColor: Color = Color.Blue
        set(value) {
            field = value
            updateText()
        }

    public var linkTextDecoration: TextDecoration = TextDecoration.Underline
        set(value) {
            field = value
            updateText()
        }

    public var codeSpanColor: Color = Color.Unspecified
        set(value) {
            field = value
            updateText()
        }

    public var codeSpanBackgroundColor: Color = Color.Transparent
        set(value) {
            field = value
            updateText()
        }

    public var codeSpanStrokeColor: Color = Color.LightGray
        set(value) {
            field = value
            updateText()
        }

    public var orderedListIntent: Int = DefaultListIndent
        set(value) {
            field = value
            updateText()
        }

    public var unorderedListIndent: Int = DefaultListIndent
        set(value) {
            field = value
            updateText()
        }

    public var listIndent: Int = DefaultListIndent
        get() {
            if (orderedListIntent == unorderedListIndent)
                field = orderedListIntent

            return field
        }
        set(value) {
            field = value
            orderedListIntent = value
            unorderedListIndent = value
        }

    public var unorderedListStyleType: UnorderedListStyleType = DefaultUnorderedListStyleType
        set(value) {
            field = value
            updateText()
        }

    public var orderedListStyleType: OrderedListStyleType = DefaultOrderedListStyleType
        set(value) {
            field = value
            updateText()
        }

    public var preserveStyleOnEmptyLine: Boolean = true

    public var exitListOnEmptyItem: Boolean = true
}

internal const val DefaultListIndent = 38

internal val DefaultUnorderedListStyleType =
    UnorderedListStyleType.from("•", "◦", "▪")

internal val DefaultOrderedListStyleType: OrderedListStyleType =
    OrderedListStyleType.Multiple(
        OrderedListStyleType.Decimal,
        OrderedListStyleType.LowerRoman,
        OrderedListStyleType.LowerAlpha,
    )


