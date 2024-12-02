package aockt.y2024

import io.github.jadarma.aockt.core.Solution
import kotlin.math.abs

object Y2024D02 : Solution {

    private fun parseInput(input: String) =
        input
            .lines()
            .map { it.split(" ").map(String::toInt) }

    override fun partOne(input: String) = parseInput(input).let { reports ->
        reports.count { report ->
            isSafe(report)
        }
    }

    override fun partTwo(input: String) = parseInput(input).let { reports ->
        reports.count { report ->
            val isSafe = isSafe(report)
            when (isSafe) {
                true -> true
                false -> {
                    report.forEachIndexed { i, _ ->
                        val mutableReport = report.toMutableList()
                        mutableReport.removeAt(i)
                        if (isSafe(mutableReport)) {
                            return@count true
                        }
                    }
                    return@count false
                }
            }
        }
    }

    private fun isSafe(report: List<Int>): Boolean {
        var prevLevel: Int = Int.MIN_VALUE
        var prevDirection: Int = Int.MIN_VALUE

        for (curLevel in report) {
            if (prevLevel == Int.MIN_VALUE) {
                prevLevel = curLevel
                continue
            }

            val range = IntRange(1, 3)
            if (abs(prevLevel - curLevel) !in range) {
                return false
            }

            val curDirection = when {
                curLevel > prevLevel -> 1
                curLevel < prevLevel -> -1
                else -> return false
            }

            if (prevDirection == Int.MIN_VALUE) {
                prevDirection = curDirection
                prevLevel = curLevel
                continue
            }

            if (curDirection != prevDirection) {
                return false
            }

            prevLevel = curLevel
        }
        return true
    }

}
