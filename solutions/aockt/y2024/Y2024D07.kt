package aockt.y2024

import io.github.jadarma.aockt.core.Solution

object Y2024D07 : Solution {

    private fun parseInput(input: String): Map<Long, List<Long>> = input.lines()
        .map { it.split(":").let { arr -> arr[0] to arr[1] } }
        .groupBy(
            keySelector = { it.first.trim().toLong() },
            valueTransform = {
                it.second.trim()
                    .split(" ")
                    .filter { it.isNotEmpty() }
                    .map { it.trim().toLong() }
            }
        ).mapValues { it.value.flatten() }

    override fun partOne(input: String) = parseInput(input)
        .filter { (result, input) -> findMatchingResults(input, result).isNotEmpty() }
        .keys.sum()

    override fun partTwo(input: String) = parseInput(input)
        .filter { (result, input) -> findMatchingResults(input, result, true).isNotEmpty() }
        .keys.sum()


    private fun findMatchingResults(
        elements: List<Long>,
        target: Long,
        withConcatenation: Boolean = false
    ): List<String> {
        val results = mutableListOf<String>()

        fun evaluate(current: Long, expression: String, index: Int) {
            if (index == elements.size) {
                if (current == target) {
                    results.add(expression)
                }
                return
            }

            evaluate(current + elements[index], "$expression + ${elements[index]}", index + 1)
            evaluate(current * elements[index], "$expression * ${elements[index]}", index + 1)
            if (withConcatenation) {
                evaluate("$current${elements[index]}".toLong(), "$expression || ${elements[index]}", index + 1)
            }
        }

        if (elements.isNotEmpty()) {
            evaluate(elements[0], "${elements[0]}", 1)
        }

        return results
    }
}
