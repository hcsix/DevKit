package com.supcoder.apksigner.constant

import androidx.compose.ui.graphics.Color
import kotlin.to


// 构建模式与颜色的映射关系
private val patternColorMap = mapOf(
    "private" to Color(0xFFCF8E6D),
)

/**
 * java 中的关键字
 * 48 + 3 + 2
 */
val keywordList = listOf(
    "abstract",
    "assert",
    "boolean",
    "break",
    "byte",
    "case",
    "catch",
    "char",
    "class",
    "continue",
    "default",
    "do",
    "double",
    "else",
    "enum",
    "extends",
    "final",
    "finally",
    "float",
    "for",
    "if",
    "implements",
    "import",
    "int",
    "interface",
    "instanceof",
    "long",
    "native",
    "new",
    "package",
    "private",
    "protected",
    "public",
    "return",
    "short",
    "static",
    "strictfp",
    "super",
    "switch",
    "synchronized",
    "this",
    "throw",
    "throws",
    "transient",
    "try",
    "void",
    "volatile",
    "while",

    "true",
    "false",
    "null",

    "goto",
    "const",
)

val kotlinKeywordList = listOf(
    "as", "as?", "is", "in", "!in",
    "break", "continue", "return", "throw",
    "class", "object", "interface", "companion", "data", "enum", "sealed", "inline", "inner", "annotation",
    "fun", "val", "var", "typealias", "operator", "infix", "tailrec", "external", "suspend",
    "if", "else", "when", "try", "catch", "finally", "for", "while", "do",
    "true", "false", "null",
    "package", "import", "as", "fun", "val", "var", "typealias",
    "file", "field", "property", "get", "set", "receiver", "param", "delegate",
    "by", "it", "this", "super", "dynamic",
    "reified", "crossinline", "noinline",
    "out", "in", "vararg", " typealias", "where",
    "override", "open", "final", "abstract", "sealed", "inner", "data", "inline", "external", "tailrec", "suspend", "operator", "infix",
    "private", "protected", "public", "internal",
    "const", "lateinit",
    "suspend",
    "yield"
)
