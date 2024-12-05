package aockt.y2024

import io.github.jadarma.aockt.core.Solution
import kotlin.math.abs

object Y2024D03 : Solution {

    private fun parseInput(input: String): List<Pair<Int, Int>> {
        val regex = Regex("""mul\((\d+),(\d+)\)""")
        val matches = regex.findAll(input).map { match ->
            val a = match.groups[1]!!.value.toInt()
            val b = match.groups[2]!!.value.toInt()
            Pair(a, b)
        }.toList()
        return matches
    }

    override fun partOne(input: String) = parseInput(input).sumOf { (a, b) -> a * b }

    override fun partTwo(input: String): Int {
        val mulRegex = Regex("""mul\((\d+),(\d+)\)""")
        val controlRegex = Regex("""(do|don't)\(\)""")

        var isMulEnabled = true
        var sum = 0

        val matches = mulRegex.findAll(input).toList()

        input.split(mulRegex).zip(matches).forEach { (betweenText, match) ->
            controlRegex.findAll(betweenText).forEach {
                isMulEnabled = it.value == "do()"
            }

            if (isMulEnabled) {
                val a = match.groups[1]!!.value.toInt()
                val b = match.groups[2]!!.value.toInt()
                sum += a * b
            }
        }

        return sum
    }


}
