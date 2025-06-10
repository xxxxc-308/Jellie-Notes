package com.cloudsurfe.editor.core

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.cloudsurfe.editor.utils.getBoundingBoxes

public interface EditorSpanStyle {
    public val spanStyle: (EditorTextConfig) -> SpanStyle

    public val acceptNewTextInTheEdges: Boolean

    public fun DrawScope.drawCustomStyle(
        layoutResult: TextLayoutResult,
        textRange: TextRange,
        editorTextConfig: EditorTextConfig,
        topPadding: Float = 0f,
        startPadding: Float = 0f,
    )

    public fun AnnotatedString.Builder.appendCustomContent(
        editorTextConfig: EditorTextConfig
    ): AnnotatedString.Builder = this

    public class Link(
        public val url: String,
    ) : EditorSpanStyle {
        override val spanStyle: (EditorTextConfig) -> SpanStyle = {
            SpanStyle(
                color = it.linkColor,
                textDecoration = it.linkTextDecoration,
            )
        }

        override fun DrawScope.drawCustomStyle(
            layoutResult: TextLayoutResult,
            textRange: TextRange,
            editorTextConfig: EditorTextConfig,
            topPadding: Float,
            startPadding: Float
        ) : Unit = Unit

        override val acceptNewTextInTheEdges: Boolean =
            false

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Link) return false

            if (url != other.url) return false

            return true
        }

        override fun hashCode(): Int {
            return url.hashCode()
        }
    }

    public class Code(
        private val cornerRadius: TextUnit = 8.sp,
        private val strokeWidth: TextUnit = 1.sp,
        private val padding: TextPaddingValues = TextPaddingValues(horizontal = 2.sp, vertical = 2.sp)
    ): EditorSpanStyle{
        override val spanStyle: (EditorTextConfig) -> SpanStyle = {
            SpanStyle(
                color = it.codeSpanColor,
            )
        }

        override fun DrawScope.drawCustomStyle(
            layoutResult: TextLayoutResult,
            textRange: TextRange,
            editorTextConfig: EditorTextConfig,
            topPadding: Float,
            startPadding: Float,
        ) {
            val path = Path()
            val backgroundColor = editorTextConfig.codeSpanBackgroundColor
            val strokeColor = editorTextConfig.codeSpanStrokeColor
            val cornerRadius = CornerRadius(cornerRadius.toPx())
            val boxes = layoutResult.getBoundingBoxes(
                startOffset = textRange.start,
                endOffset = textRange.end,
                flattenForFullParagraph = true
            )

        }

        override val acceptNewTextInTheEdges: Boolean
            get() = TODO("Not yet implemented")

        override fun equals(other: Any?): Boolean {
            return super.equals(other)
        }

        override fun hashCode(): Int {
            return super.hashCode()
        }


    }


















}