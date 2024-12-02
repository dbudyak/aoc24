package aockt.y2023

import io.github.jadarma.aockt.core.Solution
import kotlin.streams.asSequence

object Y2023D01 : Solution {

    private fun extractCalibrationValue(it: String): Int {
        val list = it.chars()
            .asSequence()
            .filter(Character::isDigit)
            .map { Character.toString(it) }
            .toList()

        return (list.first() + list.last()).toInt()
    }

    override fun partOne(input: String) = input.lines().sumOf { extractCalibrationValue(it) }

    // 54078 but 
    override fun partTwo(input: String) = input.lines().map { replaceSpelled(it) }.sumOf { extractCalibrationValue(it) }

    private fun replaceSpelled(line: String): String = line
        .replace("one", "1")
        .replace("two", "2")
        .replace("three", "3")
        .replace("four", "4")
        .replace("five", "5")
        .replace("six", "6")
        .replace("seven", "7")
        .replace("eight", "8")
        .replace("nine", "9")
}