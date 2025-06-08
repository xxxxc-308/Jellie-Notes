package com.cloudsurfe.editor.core

import com.cloudsurfe.editor.paragraph.RichParagraph

internal class RichSpan(
    internal val key: Int? = null,
    val children: MutableList<RichSpan> = mutableListOf(),
    var paragraph: RichParagraph,
){



}