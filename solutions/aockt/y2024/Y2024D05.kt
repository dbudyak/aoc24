package aockt.y2024

import io.github.jadarma.aockt.core.Solution

object Y2024D05 : Solution {

    private fun parseInput(input: String): Pair<Map<Int, List<Int>>, Map<Int, List<Int>>> =
        input
            .lines()
            .filter { it.isNotEmpty() }
            .partition { it.contains("|") }
            .let { (rules, updates) ->
                Pair(
                    first = rules.groupBy(
                        keySelector = { it.split("|")[0].toInt() },
                        valueTransform = { it.split("|")[1].toInt() },
                    ),
                    second = updates
                        .mapIndexed { i, update -> i to update.split(",")
                            .filter { it.isNotEmpty() }.map { it.toInt() } }
                        .toMap(),
                )
            }

    private fun getNexPages(currentPage: Int, update: List<Int>): List<Int> =
        update.subList(update.indexOf(currentPage) + 1, update.size)

    private fun getCurrentPageRule(currentPage: Int, rules: Map<Int, List<Int>>): List<Int> =
        rules.getOrElse(currentPage) { emptyList() }

    private fun isUpdatePageBeforeNextPages(currentPage: Int, update: List<Int>, rules: Map<Int, List<Int>>): Boolean =
        getCurrentPageRule(currentPage, rules).containsAll(getNexPages(currentPage, update))

    private fun isCorrectUpdate(update: List<Int>, rules: Map<Int, List<Int>>) =
        update.map { isUpdatePageBeforeNextPages(it, update, rules) }.all { it }

    private fun sortByRules(update: List<Int>, rules: Map<Int, List<Int>>): List<Int> =
        update.sortedWith { currentPage, nextPage ->
            when {
                rules[currentPage]?.contains(nextPage) == true -> -1
                rules[nextPage]?.contains(currentPage) == true -> 1
                else -> 0
            }
        }

    override fun partOne(input: String): Int =
        parseInput(input).let { (rules, updates) ->
            updates.values
                .filter { isCorrectUpdate(it, rules) }
                .sumOf { it[it.size / 2] }
        }

    override fun partTwo(input: String): Int =
        parseInput(input).let { (rules, updates) ->
            updates.values
                .filterNot { isCorrectUpdate(it, rules) }
                .map { sortByRules(it, rules) }
                .sumOf { it[it.size / 2] }
        }

    private fun experimentalOnGraph(
        update: List<Int>,
        rules: Map<Int, List<Int>>,
    ): List<Int> {
        val graph = mutableMapOf<Int, MutableList<Int>>()
        val inDegree = mutableMapOf<Int, Int>()

        update.forEach { page ->
            graph[page] = mutableListOf()
            inDegree[page] = 0
        }

        for ((x, dependents) in rules) {
            if (x in update) {
                for (y in dependents) {
                    if (y in update) {
                        graph[x]!!.add(y)
                        inDegree[y] = inDegree.getOrDefault(y, 0) + 1
                    }
                }
            }
        }

        val sortedPages = mutableListOf<Int>()
        val queue = ArrayDeque<Int>()

        inDegree.filterValues { it == 0 }.keys.forEach { queue.add(it) }

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            sortedPages.add(current)

            for (neighbor in graph[current]!!) {
                inDegree[neighbor] = inDegree[neighbor]!! - 1
                if (inDegree[neighbor] == 0) {
                    queue.add(neighbor)
                }
            }
        }

        return sortedPages
    }
}
