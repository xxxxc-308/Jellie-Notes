package com.cloudsurfe.editor.core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.isSpecified
import androidx.compose.ui.unit.sp
import com.cloudsurfe.editor.utils.getBoundingBoxes

//TODO UNDER WORK
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
        ): Unit = Unit

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
        private val padding: TextPaddingValues = TextPaddingValues(
            horizontal = 2.sp,
            vertical = 2.sp
        )
    ) : EditorSpanStyle {
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

            boxes.forEachIndexed { index, box ->
                path.addRoundRect(
                    RoundRect(
                        rect = box.copy(
                            left = box.left - padding.horizontal.toPx() + startPadding,
                            right = box.right + padding.horizontal.toPx() + startPadding,
                            top = box.top - padding.vertical.toPx() + topPadding,
                            bottom = box.bottom + padding.vertical.toPx() + topPadding,
                        ),
                        topLeft = if (index == 0) cornerRadius else CornerRadius.Zero,
                        bottomLeft = if (index == 0) cornerRadius else CornerRadius.Zero,
                        topRight = if (index == boxes.lastIndex) cornerRadius else CornerRadius.Zero
                    )
                )
                drawPath(
                    path = path,
                    color = strokeColor,
                    style = Fill
                )
                drawPath(
                    path = path,
                    color = strokeColor,
                    style = Stroke(
                        width = strokeWidth.toPx()
                    )
                )
            }
        }

        override val acceptNewTextInTheEdges: Boolean =
            true

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Code) return false

            if (cornerRadius != other.cornerRadius) return false
            if (strokeWidth != other.strokeWidth) return false
            if (padding != other.padding) return false

            return true
        }

        override fun hashCode(): Int {
            var result = cornerRadius.hashCode()
            result = 31 * result + strokeWidth.hashCode()
            result = 31 * result + padding.hashCode()
            return result
        }
    }

    public class Image(
        public val model: Any,
        width: TextUnit,
        height: TextUnit,
        public val contentDescription: String? = null,
    ) : EditorSpanStyle {

        init {
            require(width.isSpecified || height.isSpecified) {
                "At least one of the width or height should be specified"
            }

            require(width.value >= 0 || height.value >= 0) {
                "The width and height should be greater then or equal to 0"
            }

            require(width.value.isFinite() || height.value.isFinite()) {
                "The width and height should be finite"
            }
        }

        public var width: TextUnit by mutableStateOf(width)
            private set

        public var height: TextUnit by mutableStateOf(height)
            private set

        private val id get() = "$model-${width.value}-${height.value}"

        override val spanStyle: (EditorTextConfig) -> SpanStyle = {
            SpanStyle()
        }

        override val acceptNewTextInTheEdges: Boolean = false

        override fun DrawScope.drawCustomStyle(
            layoutResult: TextLayoutResult,
            textRange: TextRange,
            editorTextConfig: EditorTextConfig,
            topPadding: Float,
            startPadding: Float
        ): Unit = Unit

        //TODO: Implement more functions
    }

    public data object Default : EditorSpanStyle {
        override val spanStyle: (EditorTextConfig) -> SpanStyle =
            { SpanStyle() }

        override fun DrawScope.drawCustomStyle(
            layoutResult: TextLayoutResult,
            textRange: TextRange,
            editorTextConfig: EditorTextConfig,
            topPadding: Float,
            startPadding: Float
        ): Unit = Unit

        override val acceptNewTextInTheEdges: Boolean =
            true
    }

    public companion object {
        internal val DefaultSpanStyle = SpanStyle()
    }
}