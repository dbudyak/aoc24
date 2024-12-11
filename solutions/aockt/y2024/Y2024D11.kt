package aockt.y2024

import io.github.jadarma.aockt.core.Solution

object Y2024D11 : Solution {

    fun parseInput(input: String): List<Long> = input.lines().first().split(" ").map { it.toLong() }

    fun Long.splitHalfDigits(): Pair<Long, Long> {
        val intStr = toString()
        val k = intStr.length / 2
        return intStr.substring(0, k).toLong() to intStr.substring(k).toLong()
    }

    private fun Long.digitCount() = toString().length

    fun blink(stones: List<Long>) : List<Long> = stones.flatMap {
        if (it >= Long.MAX_VALUE) throw Error("fix it")
        when {
            it == 0L -> listOf(1)
            it.digitCount() % 2 == 0 -> it.splitHalfDigits().toList()
            else -> listOf(it * 2024)
        }
    }

    fun blink(acc: Int, stones: List<Long>) : List<Long> = when (acc) {
        0 -> stones
        else -> blink(acc - 1, blink(stones))
    }

    override fun partOne(input: String) : Int = parseInput(input).let {
        var stones = it
        repeat(25) { stones = blink(stones) }
        return@let stones.size
    }

    override fun partTwo(input: String): Int = parseInput(input).let {
        return@let blink(25, it).size
    }
}
