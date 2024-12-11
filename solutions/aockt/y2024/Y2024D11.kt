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

    fun blink(stones: List<Long>): List<Long> = stones.flatMap {
        if (it >= Long.MAX_VALUE) throw Error("fix it")
        when {
            it == 0L -> listOf(1)
            it.digitCount() % 2 == 0 -> it.splitHalfDigits().toList()
            else -> listOf(it * 2024L)
        }
    }

    private fun MutableMap<Long, Long>.incrementOrSet(stoneNumber: Long) {
        put(stoneNumber, (this[stoneNumber] ?: 0) + 1)
    }

    private fun MutableMap<Long, Long>.decrementOrRemove(stoneNumber: Long) {
        var stoneCount = this[stoneNumber] ?: return
        if (stoneCount < 2L) remove(stoneNumber) else put(stoneNumber, --stoneCount)
    }

    fun MutableMap<Long, Long>.compareAndSet(stoneNumber: Long) {
        when {
            stoneNumber == 0L -> {
                decrementOrRemove(stoneNumber)
                incrementOrSet(1)
            }

            stoneNumber.digitCount() % 2 == 0 -> stoneNumber.splitHalfDigits().let { (a, b) ->
                decrementOrRemove(stoneNumber)
                incrementOrSet(a)
                incrementOrSet(b)
            }

            else -> {
                decrementOrRemove(stoneNumber)
                incrementOrSet(stoneNumber * 2024)
            }
        }
    }

    override fun partOne(input: String): Int = parseInput(input).let {
        var stones = it
        repeat(25) { stones = blink(stones) }
        return@let stones.size
    }

    override fun partTwo(input: String): Long = parseInput(input).let {
        val stones = it.associateBy({ it }, { 1L }).toMutableMap()
        repeat(25) {
            var newStones = stones
            val stoneNumbers = newStones.keys.toList()

            for (stoneNumber in stoneNumbers) {

                val stoneCount = newStones[stoneNumber]!!

                repeat(stoneCount.toInt()) {
                    stones.compareAndSet(stoneNumber)
                }

            }
        }


        return@let stones.values.sum()
    }
}
