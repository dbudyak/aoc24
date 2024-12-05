package aockt.y2024

import io.github.jadarma.aockt.core.Solution

object Y2024D04 : Solution {
    private fun parseInput(input: String): Array<CharArray> =
        input
            .split("\n")
            .map { it.toCharArray() }
            .toTypedArray()

    enum class Direction {
        UP,
        UP_RIGHT,
        RIGHT,
        RIGHT_DOWN,
        DOWN,
        DOWN_LEFT,
        LEFT,
        LEFT_UP,
    }

    private fun getDirectionalIndexes(
        i: Int,
        j: Int,
        direction: Direction,
    ): List<Pair<Int, Int>> =
        when (direction) {
            Direction.UP -> listOf(i to j, i - 1 to j, i - 2 to j, i - 3 to j)
            Direction.UP_RIGHT -> listOf(i to j, i - 1 to j + 1, i - 2 to j + 2, i - 3 to j + 3)
            Direction.RIGHT -> listOf(i to j, i to j + 1, i to j + 2, i to j + 3)
            Direction.RIGHT_DOWN -> listOf(i to j, i + 1 to j + 1, i + 2 to j + 2, i + 3 to j + 3)
            Direction.DOWN -> listOf(i to j, i + 1 to j, i + 2 to j, i + 3 to j)
            Direction.DOWN_LEFT -> listOf(i to j, i + 1 to j - 1, i + 2 to j - 2, i + 3 to j - 3)
            Direction.LEFT -> listOf(i to j, i to j - 1, i to j - 2, i to j - 3)
            Direction.LEFT_UP -> listOf(i to j, i - 1 to j - 1, i - 2 to j - 2, i - 3 to j - 3)
        }

    override fun partOne(input: String): Int {
        val array = parseInput(input)
        var count = 0

        for (i in array.indices) {
            for (j in array[i].indices) {
                Direction.entries.forEach {
                    val word =
                        getDirectionalIndexes(i, j, it)
                            .map { (newI, newJ) ->
                                array.getOrNull(newI)?.getOrNull(newJ)
                            }.toList()
                    if (word == listOf('X', 'M', 'A', 'S')) {
                        count++
                    }
                }
            }
        }

        return count
    }

    override fun partTwo(input: String): Int {
        val array = parseInput(input)
        var count = 0

        for (i in array.indices) {
            for (j in array[i].indices) {
                if (array[i][j] == 'A') {
                    val topLeft = array.getOrNull(i - 1)?.getOrNull(j - 1)
                    val topRight = array.getOrNull(i - 1)?.getOrNull(j + 1)
                    val bottomLeft = array.getOrNull(i + 1)?.getOrNull(j - 1)
                    val bottomRight = array.getOrNull(i + 1)?.getOrNull(j + 1)

                    val leftWord = listOf(topLeft, bottomRight).sortedBy { it }
                    val rightWord = listOf(topRight, bottomLeft).sortedBy { it }

                    val ms = listOf('M', 'S')

                    if (leftWord == ms && rightWord == ms) {
                        count++
                    }
                }
            }
        }

        return count
    }
}
