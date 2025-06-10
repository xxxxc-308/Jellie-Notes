package com.cloudsurfe.editor.paragraph.type

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.cloudsurfe.editor.core.DefaultListIndent
import com.cloudsurfe.editor.core.DefaultOrderedListStyleType

internal class OrderedList private constructor(
    number: Int,
    initialIndent: Int = DefaultListIndent,
    startTextWidth: TextUnit = 0.sp,
    initialStyleType: OrderedListStyleType = DefaultOrderedListStyleType,
){

}