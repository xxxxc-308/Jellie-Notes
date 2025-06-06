package com.cloudsurfe.editor.paragraph

interface OrderedListStyleType {

    fun format(number: Int, listLevel: Int): String = number.toString()

    fun getSuffix(listLevel: Int): String = ". "

    object Decimal : OrderedListStyleType {
        override fun format(number: Int, listLevel: Int): String = number.toString()
    }

    object ArabicIndic : OrderedListStyleType {
        override fun format(number: Int, listLevel: Int): String =
            number
                .toString()
                .map { ch ->
                    when (ch) {
                        '0' -> '٠'
                        '1' -> '١'
                        '2' -> '٢'
                        '3' -> '٣'
                        '4' -> '٤'
                        '5' -> '٥'
                        '6' -> '٦'
                        '7' -> '٧'
                        '8' -> '٨'
                        '9' -> '٩'
                        else -> ch
                    }
                }
                .joinToString("")
    }

    object Arabic : OrderedListStyleType{
        override fun format(number: Int, listLevel: Int): String {
            return super.format(number, listLevel)
        }
    }

}
