package com.cloudsurfe.editor.paragraph

import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastForEachReversed
import com.cloudsurfe.editor.core.EditorSpan
import com.cloudsurfe.editor.paragraph.type.DefaultParagraph
import com.cloudsurfe.editor.paragraph.type.ParagraphType
import com.cloudsurfe.editor.paragraph.type.ParagraphType.Companion.startText

internal class EditorParagraph(
    val key: Int = 0,
    val children: MutableList<EditorSpan> = mutableListOf(),
    var paragraphStyle: ParagraphStyle = DefaultParagraphStyle,
    var type: ParagraphType = DefaultParagraph()
) {

    fun getRichSpanByTextIndex(
        paragraphIndex: Int,
        textIndex: Int,
        offset: Int = 0,
        ignoreCustomFiltering: Boolean = false,
    ): Pair<Int, Boolean> {
        var index = offset

        if (paragraphIndex > 0)
            index++

        type.startEditorSpan.paragraph = this
        type.startEditorSpan.textRange = TextRange(index, index + type.startText.length)

        index += type.startText.length

        if (children.isNotEmpty())
            children.add(
                EditorSpan(
                    paragraph = this,
                    textRange = TextRange(index)
                )
            )

        if (index > textIndex)
            return index to getFirstNonEmptyChild()
    }

    fun removeTextRange(
        textRange: TextRange,
        offset: Int,
    ): EditorParagraph?{
        var index = offset
        val toRemoveIndices = mutableListOf<Int>()

        for (i in 0..children.lastIndex) {
            val child = children[i]
            val result = child.removeTextRange()
        }
    }

    fun getTextRange(): TextRange {
        var start = type.startEditorSpan.textRange.min
        var end = 0

        if (type.startEditorSpan.text.isNotEmpty())
            end += type.startEditorSpan.text.length

        children.lastOrNull()?.let { editorSpan ->
            end = editorSpan.fullTextRange.end
        }

        return TextRange(start, end)
    }

    fun isEmpty(ignoreStatEditorSpan: Boolean = true): Boolean {
        if (!ignoreStatEditorSpan && !type.startEditorSpan.isEmpty()) return false

        if (children.isEmpty()) return true
        children.fastForEach { editorSpan ->
            if (!editorSpan.isEmpty()) return false
        }
        return true
    }

    fun isNotEmpty(ignoreStartEditorSpan: Boolean = true): Boolean = !isEmpty(ignoreStartEditorSpan)

    fun isBlank(ignoreStartEditorSpan: Boolean = true): Boolean {
        if (!ignoreStartEditorSpan && !type.startEditorSpan.isBlank()) return false

        if (children.isEmpty()) return true
        children.fastForEach { editorSpan ->
            if (!editorSpan.isBlank()) return false
        }
        return true
    }

    fun isNotBlank(ignoreStartEditorSpan: Boolean = true): Boolean = !isBlank(ignoreStartEditorSpan)

    fun getStartTextSpanStyle(): SpanStyle? {
        children.fastForEach { editorSpan ->
            if (editorSpan.text.isNotEmpty()) {
                return editorSpan.spanStyle
            } else {
                val result = editorSpan.getStartTextSpanStyle(SpanStyle())

                if (result != null)
                    return result
            }
        }

        val firstChild = children.firstOrNull()

        children.clear()

        if (firstChild != null) {
            firstChild.children.clear()

            children.add(firstChild)
        }

        return firstChild?.spanStyle
    }

    fun getFirstNonEmptyChild(offset: Int = -1): EditorSpan? {
        children.fastForEach { editorSpan ->
            if (editorSpan.text.isNotEmpty()) {
                if (offset != -1) {
                    editorSpan.textRange = TextRange(offset, offset + editorSpan.text.length)

                    return editorSpan
                }
            } else {
                val result = editorSpan.getFirstNonEmptyChild(offset)

                if (result != null)
                    return result
            }
        }

        val firstChild = children.firstOrNull()

        children.clear()

        if (firstChild != null) {
            firstChild.children.clear()

            if (offset != -1)
                firstChild.textRange = TextRange(offset, offset + firstChild.text.length)

            children.add(firstChild)
        }

        return firstChild
    }

    fun getLastNonEmptyChild(): EditorSpan? {
        for (i in children.lastIndex downTo 0) {
            val editorSpan = children[i]
            if (editorSpan.text.isNotEmpty())
                return editorSpan

            val result = editorSpan.getLastNonEmptyChild()
            if (result != null)
                return result
        }

        return null
    }

    fun trim() {
        val isEmpty = trimStart()
        if (!isEmpty)
            trimEnd()
    }

    fun trimStart(): Boolean {
        children.fastForEach { editorSpan ->
            val isEmpty = editorSpan.trimEnd()

            if (!isEmpty)
                return false
        }

        return true
    }

    fun trimEnd(): Boolean {
        children.fastForEachReversed { editorSpan ->
            val isEmpty = editorSpan.trimEnd()

            if (!isEmpty)
                return false
        }

        return true
    }

    companion object {
        val DefaultParagraphStyle = ParagraphStyle()
    }
}