package com.cloudsurfe.editor.paragraph.type

public interface OrderedListStyleType {
    public fun format(number: Int, listLevel: Int): String = number.toString()

    public fun getSuffix(listLevel: Int): String = ". "

    public object Decimal : OrderedListStyleType {
        override fun format(number: Int, listLevel: Int): String = number.toString()
    }
    // Arabic-Indic format (١, ٢, ٣, ...)
    public object ArabicIndic : OrderedListStyleType {
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
    // Arabic alphabet style (أ, ب, ج, ...)
    public object Arabic : OrderedListStyleType {
        internal val arabicLetters = charArrayOf(
            'أ', 'ب', 'ج', 'د', 'ه', 'و', 'ز', 'ح', 'ط', 'ي', 'ك', 'ل', 'م',
            'ن', 'س', 'ع', 'ف', 'ص', 'ق', 'ر', 'ش', 'ت', 'ث', 'خ', 'ذ', 'ض', 'ظ', 'غ'
        )

        override fun format(number: Int, listLevel: Int): String =
            formatToArabic(
                number = number,
            )


    }

    public object LowerAlpha : OrderedListStyleType {
        override fun format(number: Int, listLevel: Int): String =
            formatToAlpha(
                number = number,
                base = 'a'
            )
    }

    public object UpperAlpha : OrderedListStyleType {
        override fun format(number: Int, listLevel: Int): String =
            formatToAlpha(
                number = number,
                base = 'A'
            )
    }

    public object LowerRoman : OrderedListStyleType {
        private val romanNumerals = arrayOf(
            "m" to 1000,
            "cm" to 900,
            "d" to 500,
            "cd" to 400,
            "c" to 100,
            "xc" to 90,
            "l" to 50,
            "xl" to 40,
            "x" to 10,
            "ix" to 9,
            "v" to 5,
            "iv" to 4,
            "i" to 1
        )

        override fun format(number: Int, listLevel: Int): String =
            formatToRomanNumber(
                number = number,
                romanNumerals = romanNumerals,
                defaultValue = "i"
            )
    }

    public object UpperRoman : OrderedListStyleType {
        private val romanNumerals = arrayOf(
            "M" to 1000,
            "CM" to 900,
            "D" to 500,
            "CD" to 400,
            "C" to 100,
            "XC" to 90,
            "L" to 50,
            "XL" to 40,
            "X" to 10,
            "IX" to 9,
            "V" to 5,
            "IV" to 4,
            "I" to 1
        )

        override fun format(number: Int, listLevel: Int): String =
            formatToRomanNumber(
                number = number,
                romanNumerals = romanNumerals,
                defaultValue = "I"
            )
    }

    public class Multiple(
        public vararg val styles: OrderedListStyleType
    ) : OrderedListStyleType {
        override fun format(number: Int, listLevel: Int): String {
            if (styles.isEmpty())
                return Decimal.format(number, listLevel)
            val style = styles[(listLevel - 1).coerceIn(styles.indices)]
            return style.format(number, listLevel)
        }

        override fun getSuffix(listLevel: Int): String {
            if (styles.isEmpty())
                return Decimal.getSuffix(listLevel)

            val style = styles[(listLevel - 1).coerceIn(styles.indices)]
            return style.getSuffix(listLevel)
        }
    }

    private companion object {

        private fun formatToArabic(
            number: Int,
        ): String {
            if (number <= 0)
                return Arabic.arabicLetters.first().toString()

            val result = StringBuilder()
            var n = number
            while (n > 0) {
                val remainder = (n - 1) % 28
                result.insert(0, Arabic.arabicLetters[remainder])
                n = (n - 1) / 28
            }
            return result.toString()
        }

        private fun formatToAlpha(
            number: Int,
            base: Char,
        ): String {
            if (number <= 0)
                return base.toString()

            val baseCode = base.code - 1
            val result = StringBuilder()
            var n = number
            while (n > 0) {
                val remainder = (n - 1) % 26 + 1
                result.insert(0, (baseCode + remainder).toChar())
                n = (n - 1) / 26
            }
            return result.toString()
        }

        private fun formatToRomanNumber(
            number: Int,
            romanNumerals: Array<Pair<String, Int>>,
            defaultValue: String,
        ): String {
            if (number <= 0)
                return defaultValue

            var n = number
            val result = StringBuilder()
            for ((roman, value) in romanNumerals) {
                while (n >= value) {
                    result.append(roman)
                    n -= value
                }
            }
            return result.toString()
        }
    }
}