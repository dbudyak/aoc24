package aockt.y2024

import io.github.jadarma.aockt.core.Solution
import kotlin.math.abs

object Y2024D01 : Solution {

    private fun parseInput(input: String): Pair<List<Int>, List<Int>> =
        input
            .lines()
            .map { it.split("   ").map(String::toInt).let { arr -> arr[0] to arr[1] } }
            .unzip()

    override fun partOne(input: String) = parseInput(input).let {
        val left = it.first.toMutableList()
        val right = it.second.toMutableList()

        var count = left.size
        var distance = 0

        while (count-- > 0) {
            val leftMin = left.min()
            val rightMin = right.min()

            distance += abs(leftMin - rightMin)

            left.remove(leftMin)
            right.remove(rightMin)

        }
        distance
    }

    override fun partTwo(input: String) = parseInput(input)
        .let { (left, right) ->
            return@let left.sumOf { leftElement ->
                right.count { rightElement -> rightElement == leftElement } * leftElement
            }
        }
}
