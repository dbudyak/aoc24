package aockt.y2024

import io.github.jadarma.aockt.core.Solution
import java.awt.Point
import java.awt.geom.Line2D

typealias Frequency = Char

data class Antenna(val frequency: Frequency, val x: Int, val y: Int) : Point(x, y)
data class AntennaLine(val a: Antenna, val b: Antenna) : Line2D.Double(a, b)

fun List<Antenna>.toLines(): List<AntennaLine> =
    groupBy(Antenna::frequency)
        .values
        .flatMap { antennas ->
            antennas.flatMapIndexed { i, a ->
                antennas
                    .drop(i + 1)
                    .filter { b -> a.distance(b) != 0.0 }
                    .map { b -> AntennaLine(a, b) }
            }
        }
        .distinctBy { listOf(it.a, it.b).sortedBy { antenna -> antenna.frequency } }

fun AntennaLine.extrapolated(): AntennaLine {
    require(a.frequency == b.frequency)

    val dx = b.x - a.x
    val dy = b.y - a.y

    val antennaLine = AntennaLine(
        a = Antenna(frequency = a.frequency, x = a.x - dx, y = a.y - dy),
        b = Antenna(frequency = a.frequency, x = b.x + dx, y = b.y + dy)
    )

    return antennaLine
}

object Y2024D08 : Solution {

    fun parseInput(input: String): List<AntennaLine> =
        parseAntennas(input).map { (_, antennas) -> antennas.toLines() }.flatten()

    private fun parseAntennas(
        input: String,
    ): Map<Frequency, List<Antenna>> {
        val antennas = mutableMapOf<Frequency, List<Antenna>>()
        input.lines().forEachIndexed { yIdx, line ->
            line.forEachIndexed { xIdx, freq ->
                if (freq.isLetterOrDigit()) {
                    antennas[freq] = antennas.getOrDefault(freq, emptyList()) + Antenna(freq, xIdx, yIdx)
                }
            }
        }
        return antennas
    }

    override fun partOne(input: String) = parseInput(input)
        .map { line -> line.extrapolated() }
        .flatMap { listOf(it.a, it.b) }
        .filter { antenna -> isWithinBounds(antenna, input) }
        .distinctBy { it.x to it.y }
        .distinct()
        .onEach { println(it) }
        .count()

    private fun isWithinBounds(ea: Antenna, input: String): Boolean =
        ea.x in (0..<input.lines()[0].length) && ea.y in (0..<input.lines().size)

    override fun partTwo(input: String) = Unit
}
