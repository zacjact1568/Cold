package net.zackzhang.code.haze.common.util

object NumberUtils {

    fun parseIntRange(intRange: String): IntRange? {
        val ends = intRange.split('-')
        val first = ends[0].toInt()
        return when (ends.size) {
            1 -> first..first
            2 -> first..ends[1].toInt()
            else -> null
        }
    }

    fun presentIntRange(intRange: IntRange): String {
        val first = intRange.first
        val last = intRange.last
        return if (first == last) first.toString() else "$first-$last"
    }
}