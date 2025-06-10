package com.cloudsurfe.editor.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.ResolvedTextDirection
import kotlin.math.max
import kotlin.math.min

public fun TextLayoutResult.getBoundingBoxes(
    startOffset: Int,
    endOffset: Int,
    flattenForFullParagraph: Boolean = false
): List<Rect> {
    if (multiParagraph.lineCount == 0)
        return emptyList()

    var lastOffset = 0
    var lastNonEmptyLineIndex = multiParagraph.lineCount - 1

    while (lastOffset == 0 && lastNonEmptyLineIndex >= 0) {
        val lastLinePosition =
            Offset(
                x = multiParagraph.getLineRight(lastNonEmptyLineIndex),
                y = multiParagraph.getLineTop(lastNonEmptyLineIndex)
            )

        lastOffset = multiParagraph.getOffsetForPosition(lastLinePosition)
        lastNonEmptyLineIndex--
    }

    if (startOffset >= lastOffset)
        return emptyList()

    if (startOffset == endOffset)
        return emptyList()

    if (startOffset < 0 || endOffset < 0 || endOffset > layoutInput.text.length)
        return emptyList()

    val start = min(startOffset, endOffset)
    val end = min(max(start, endOffset), lastOffset)

    val startLineNum = getLineForOffset(min(start, end))
    val endLineNum = getLineForOffset(max(start, end))

    if (flattenForFullParagraph) {
        val isFullParagraph = (startLineNum != endLineNum)
                && getLineStart(startLineNum) == start
                && multiParagraph.getLineEnd(endLineNum, visibleEnd = true) == end

        if (isFullParagraph) {
            return listOf(
                Rect(
                    top = getLineTop(startLineNum),
                    bottom = getLineBottom(endLineNum),
                    left = 0f,
                    right = size.width.toFloat()
                )
            )
        }
    }

    val isLtr = multiParagraph.getParagraphDirection(offset = start) == ResolvedTextDirection.Ltr

    return fastMapRange(startLineNum, endLineNum) { lineNum ->
        val left =
            if (lineNum == startLineNum)
                getHorizontalPosition(
                    offset = start,
                    usePrimaryDirection = isLtr
                )
            else {
                getLineLeft(
                    lineIndex = lineNum
                )
            }
        val right =
            if (lineNum == endLineNum)
                getHorizontalPosition(
                    offset = end,
                    usePrimaryDirection = isLtr
                )
            else {
                getLineRight(
                    lineIndex = lineNum
                )
            }
        Rect(
            top = getLineTop(lineNum),
            bottom = getLineBottom(lineNum),
            left = left,
            right = right
        )
    }

}














