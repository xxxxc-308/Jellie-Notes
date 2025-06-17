package com.cloudsurfe.editor.paragraph.type

import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.cloudsurfe.editor.core.DefaultListIndent
import com.cloudsurfe.editor.core.DefaultOrderedListStyleType
import com.cloudsurfe.editor.core.EditorSpan
import com.cloudsurfe.editor.core.EditorTextConfig
import com.cloudsurfe.editor.paragraph.EditorParagraph
import kotlin.reflect.typeOf

internal class OrderedList private constructor(
    number: Int,
    initialIndent: Int = DefaultListIndent,
    startTextWidth: TextUnit = 0.sp,
    initialLevel: Int = 1,
    initialStyleType: OrderedListStyleType = DefaultOrderedListStyleType,
) : ParagraphType, ConfigurableStartTextWidth, ConfigurableListLevel {

    constructor(
        number: Int,
        initialLevel: Int = 1
    ) : this(
        number = number,
        initialIndent = DefaultListIndent,
        initialLevel = initialLevel
    )

    constructor(
        number: Int,
        config: EditorTextConfig,
        startTextWidth: TextUnit = 0.sp,
        initialLevel: Int = 1
    ) : this(
        number = number,
        initialIndent = config.orderedListIntent,
        startTextWidth = startTextWidth,
        initialLevel = initialLevel,
        initialStyleType = config.orderedListStyleType,
    )

    var number = number
        set(value) {
            field = value
            startEditorSpan = getNewStartEditorSpan(startEditorSpan.textRange)
        }

    override var startTextWidth: TextUnit = startTextWidth
        set(value) {
            field = value
            style = getNewParagraphStyle()
        }

    private var indent = initialIndent
        set(value) {
            field = value
            style = getNewParagraphStyle()
        }

    override var level = initialLevel
        set(value) {
            field = value
            style = getNewParagraphStyle()
        }

    private var styleType = initialStyleType
        set(value) {
            field = value
            startEditorSpan = getNewStartEditorSpan(startEditorSpan.textRange)
        }

    private var style: ParagraphStyle =
        getNewParagraphStyle()


    override fun getStyle(config: EditorTextConfig): ParagraphStyle {
        if (config.orderedListIntent != indent) {
            indent = config.orderedListIntent
        }

        if (config.orderedListStyleType != styleType) {
            styleType = config.orderedListStyleType
        }

        return style
    }

    private fun getNewParagraphStyle() =
        ParagraphStyle(
            textIndent = TextIndent(
                firstLine = ((indent * level) - startTextWidth.value).sp,
                restLine = (indent * level).sp
            )
        )

    override var startEditorSpan: EditorSpan =
        getNewStartEditorSpan()

    private fun getNewStartEditorSpan(textRange: TextRange = TextRange(0)): EditorSpan {
        val text = styleType.format(number, level) + styleType.getSuffix(level)

        return EditorSpan(
            paragraph = EditorParagraph(type = this),
            text = text,
            textRange = TextRange(
                textRange.min,
                textRange.min + text.length
            )
        )

    }

    override fun getNextParagraphType(): ParagraphType =
        OrderedList(
            number = number + 1,
            initialIndent = indent,
            startTextWidth = startTextWidth,
            initialLevel = level,
            initialStyleType = styleType,
        )

    override fun copy(): ParagraphType =
        OrderedList(
            number = number,
            initialIndent = indent,
            startTextWidth = startTextWidth,
            initialLevel = level,
            initialStyleType = styleType,
        )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OrderedList) return false

        if (number != other.number) return false
        if (indent != other.indent) return false
        if (startTextWidth != other.startTextWidth) return false
        if (level != other.level) return false
        if (styleType != other.styleType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = indent
        result = 31 * result + number
        result = 31 * result + indent
        result = 31 * result + startTextWidth.hashCode()
        result = 31 * result + level
        result = 31 * result + styleType.hashCode()

        return result
    }
}
