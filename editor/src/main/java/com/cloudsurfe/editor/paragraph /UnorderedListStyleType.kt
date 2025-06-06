package com.cloudsurfe.editor.paragraph

@ConsistentCopyVisibility
data class UnorderedListStyleType private constructor(
    internal val prefixes : List<String>
){
    companion object{
        fun from(vararg prefix : String): UnorderedListStyleType{
            return UnorderedListStyleType(prefix.toList())
        }

        fun from(prefixes: List<String>): UnorderedListStyleType{
            return UnorderedListStyleType(prefixes)
        }

        val Disc: UnorderedListStyleType = UnorderedListStyleType(
            prefixes = listOf("•")

        )

        val Circle: UnorderedListStyleType = UnorderedListStyleType(
            prefixes = listOf("◦")
        )

        val Square: UnorderedListStyleType = UnorderedListStyleType(
            prefixes = listOf("▪")
        )
    }
}

