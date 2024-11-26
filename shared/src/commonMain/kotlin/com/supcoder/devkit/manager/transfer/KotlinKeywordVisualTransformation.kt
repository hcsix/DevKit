package com.supcoder.devkit.manager.transfer

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie
import com.supcoder.devkit.constant.kotlinKeywordList
import java.util.*

/**
 * java关键字高亮显示
 */
object KotlinKeywordVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val textString = text.text
        return TransformedText(toKotlinAnnotatedString(textString), OffsetMapping.Identity)
    }
}


/**
 * String转换为AnnotatedString
 */
fun toKotlinAnnotatedString(string: String): AnnotatedString {

    val keywordColor = Color(0xFFCF8E6D)

    val treeMap = TreeMap<String, String>()
    kotlinKeywordList.forEach {
        treeMap[it] = it
    }

    // 构建 Aho-Corasick 自动机
    val acdat = AhoCorasickDoubleArrayTrie<String>()
    acdat.build(treeMap)

    val stringLength = string.length

    return buildAnnotatedString {
        append(string)

        val hitList = acdat.parseText(string)
        hitList.forEach { hit: AhoCorasickDoubleArrayTrie.Hit<String> ->
            val start = hit.begin
            val end = hit.end

            // 如果后一个字符不是空格，证明不是一个单独的单词，则跳过
            if (end < stringLength) {
                val char = string[end]
                if (!char.isWhitespace()) {
                    return@forEach
                }
            }

            addStyle(
                style = SpanStyle(color = keywordColor),
                start = start,
                end = end
            )

        }
    }
}