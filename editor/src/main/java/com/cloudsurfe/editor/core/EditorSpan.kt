package com.cloudsurfe.editor.core

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastForEachReversed
import com.cloudsurfe.editor.paragraph.EditorParagraph

internal class EditorSpan(
    internal val key: Int? = null,
    val children: MutableList<EditorSpan> = mutableListOf(),
    var paragraph: EditorParagraph,
    var parent: EditorSpan? = null,
    var text: String = "",
    var textRange: TextRange = TextRange(start = 0, end = 0),
    var spanStyle: SpanStyle = SpanStyle(),
    var editorSpanStyle: EditorSpanStyle = EditorSpanStyle.Default
) {

    internal val fullTextRange: TextRange
        get() {
            var textRange = this.textRange
            var lastChild: EditorSpan? = this
            while (true) {
                lastChild = lastChild?.children?.lastOrNull()
                if (lastChild == null) break
                textRange = TextRange(
                    start = textRange.min,
                    end = lastChild.textRange.max
                )
            }
            return textRange
        }

    val fullStyle: EditorSpanStyle
        get() {
            var style = this.editorSpanStyle
            var parent = this.parent

            while (parent != null && style::class == EditorSpanStyle.Default::class) {
                style = parent.editorSpanStyle
                parent = parent.parent
            }
            return style
        }

    val before: EditorSpan?
        get() {
            val parentChildren = parent?.children ?: paragraph.children
            val index = parentChildren.indexOf(this)

            return if (index > 0) {
                val beforeChild = parentChildren[index - 1]
                if (beforeChild.isEmpty())
                    beforeChild.before
                else
                    beforeChild
            } else if (parent?.isEmpty() == true)
                parent?.before
            else
                parent
        }

    val after: EditorSpan?
        get() {
            if (children.isNotEmpty())
                return children.first()
            var child: EditorSpan = this
            var parent: EditorSpan? = parent

            while (parent != null) {
                val index = parent.children.indexOf(child)
                if (index < parent.children.lastIndex) {
                    val afterChild = parent.children[index + 1]
                    return if (afterChild.isEmpty())
                        afterChild.after
                    else
                        afterChild
                }
                child = parent
                parent = parent.parent
            }

            val index = child.paragraph.children.indexOf(child)
            return if (index < child.paragraph.children.lastIndex) {
                val afterChild = child.paragraph.children[index + 1]
                if (afterChild.isEmpty())
                    afterChild.after
                else
                    afterChild
            } else
                null
        }

    val isFirstInParagraph: Boolean
        get() {
            var current: EditorSpan
            var parent: EditorSpan = this

            while (true) {
                current = parent
                parent = current.parent ?: break

                if (parent.children.firstOrNull() != current || parent.text.isNotEmpty()) return false
            }

            return parent.children.firstOrNull() == current
        }

    fun isEmpty(): Boolean =
        text.isEmpty() && isChildrenEmpty() && editorSpanStyle !is EditorSpanStyle.Image

    fun isBlank(): Boolean =
        text.isBlank() && isChildrenBlank() && editorSpanStyle !is EditorSpanStyle.Image

    private fun isChildrenEmpty(): Boolean =
        children.all { editorSpan ->
            editorSpan.isEmpty()
        }

    private fun isChildrenBlank(): Boolean =
        children.all { editorSpan ->
            editorSpan.isBlank()
        }

    internal fun getStartTextSpanStyle(
        parentSpanStyle: SpanStyle
    ): SpanStyle? {
        children.fastForEach { editorSpan ->
            if (editorSpan.text.isNotEmpty()) {
                return spanStyle
            } else {
                val result = editorSpan.getStartTextSpanStyle(parentSpanStyle.merge(spanStyle))
                if (result != null) {
                    return result
                }
            }
        }
        return null
    }

    internal fun getFirstNonEmptyChild(offset: Int = -1): EditorSpan? {
        children.fastForEach { editorSpan ->
            if (editorSpan.text.isNotEmpty()) {
                if (offset != -1)
                    editorSpan.textRange = TextRange(offset, offset + editorSpan.text.length)

                return editorSpan
            } else {
                val result = editorSpan.getFirstNonEmptyChild(offset)
                if (result != null) {
                    if (offset != -1) {
                        editorSpan.textRange = TextRange(offset, offset + editorSpan.text.length)

                        return result
                    }
                }
            }

        }
        return null
    }

    internal fun trimStart(): Boolean {
        if (editorSpanStyle is EditorSpanStyle.Image)
            return false

        if (isBlank()) {
            text = ""
            children.clear()
            return true
        }

        text = text.trimStart()

        if (text.isNotEmpty())
            return false

        var isEmpty = true
        val toRemoveIndices = mutableListOf<Int>()

        for (i in children.indices) {
            val editorSpan = children[i]

            val isChildEmpty = editorSpan.trimStart()

            if (isChildEmpty) {
                toRemoveIndices.add(i)
            } else {
                isEmpty = false
                break
            }
        }

        toRemoveIndices.fastForEachReversed {
            children.removeAt(it)
        }
        return isEmpty
    }

    internal fun trimEnd(): Boolean {
        val isImage = editorSpanStyle is EditorSpanStyle.Image

        if (isImage)
            return false

        val isChildrenBlank = isChildrenBlank() && !isImage

        if (text.isBlank() && isChildrenBlank) {
            text = ""
            children.clear()
            return true
        }

        if (isChildrenBlank) {
            children.clear()
            text = text.trimEnd()
            return false
        }

        var isEmpty = true
        val toRemoveIndices = mutableListOf<Int>()

        for (i in children.indices.reversed()) {
            val editorSpan = children[i]

            val isChildEmpty = editorSpan.trimEnd()

            if (isChildEmpty) {
                toRemoveIndices.add(i)
            } else {
                isEmpty = false
                break
            }
        }

        toRemoveIndices.fastForEach {
            children.removeAt(it)
        }

        return isEmpty
    }

    internal fun getLastNonEmptyChild(): EditorSpan? {
        for (i in children.lastIndex downTo 0) {
            val editorSpan = children[i]
            if (editorSpan.text.isNotEmpty())
                return editorSpan

            val result = editorSpan.getLastNonEmptyChild()
            if (editorSpan.text.isNotEmpty())
                return editorSpan
        }

        return null
    }

//    fun getRichSpanByTextIndex(
//        textIndex: Int,
//        offset: Int = 0,
//        ignoreCustomFiltering: Boolean = false
//    ): Pair<Int, EditorSpan?> {
//
//    }






















}