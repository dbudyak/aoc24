package aockt.y2024

import aockt.y2024.Y2024D08.parseInput
import io.github.jadarma.aockt.test.AdventDay
import io.github.jadarma.aockt.test.AdventSpec
import io.kotest.matchers.shouldBe

@AdventDay(2024, 8, "Resonant Collinearity")
class Y2024D08Test :
    AdventSpec<Y2024D08>({

        val testInput = """
            ............
            ........0...
            .....0......
            .......0....
            ....0.......
            ......A.....
            ............
            ............
            ........A...
            .........A..
            ............
            ............
        """.trimIndent()

        val expectedAntinodes = """
            ......#....#
            ...#....0...
            ....#0....#.
            ..#....0....
            ....0....#..
            .#....A.....
            ...#........
            #......#....
            ........A...
            .........A..
            ..........#.
            ..........#.
        """.trimIndent()

        val expectedTAntinodes = """
            ##....#....#
            .#.#....0...
            ..#.#0....#.
            ..##...0....
            ....0....#..
            .#...#A....#
            ...#..#.....
            #....#.#....
            ..#.....A...
            ....#....A..
            .#........#.
            ...#......##
        """.trimIndent()


        test("should parse antenna lines") {
            val antennas = listOf(
                Antenna('0', 1, 2),
                Antenna('0', 1, 3),
                Antenna('0', 2, 3),
                Antenna('A', 2, 5),
                Antenna('A', 3, 6),
            )
            antennas.toLines().size shouldBe 4
        }

        test("should extrapolate antenna lines") {
            val line = AntennaLine(
                Antenna('0', 1, 2),
                Antenna('0', 1, 3)
            )
            line.extrapolated().a shouldBe Antenna('0', 1, 1)
            line.extrapolated().b shouldBe Antenna('0', 1, 4)
        }

        test("should filter out of bounds antennas") {
            val antennas = listOf(
                Antenna('0', 1, 2),
                Antenna('0', 1, 5),
            )
            antennas.toLines().size shouldBe 1
        }

        test("should print expected antinodes") {
            val array = testInput.split("\n").map { it.toCharArray() }.toTypedArray()

            parseInput(testInput)
                .map { line -> line.extrapolated() }
                .flatMap { listOf(it.a, it.b) }
                .distinct()
                .forEach { antenna ->
                    if (antenna.y in (0..12) && antenna.x in (0..12))
                        if (array[antenna.y][antenna.x] == '.')
                            array[antenna.y][antenna.x] = '#'
                }

            array.joinToString("\n") { it.concatToString() } shouldBe expectedAntinodes
        }

        test("should print expected T-antinodes") {
            val array = testInput.split("\n").map { it.toCharArray() }.toTypedArray()

            parseInput(testInput)
                .map { line -> line.extrapolated() }
                .flatMap { listOf(it.a, it.b) }
                .distinct()
                .forEach { antenna ->
                    if (antenna.y in (0..12) && antenna.x in (0..12))
                        if (array[antenna.y][antenna.x] == '.')
                            array[antenna.y][antenna.x] = '#'
                }

            array.joinToString("\n") { it.concatToString() } shouldBe expectedTAntinodes
        }


        partOne {
            testInput shouldOutput 14
        }

        partTwo {
            testInput shouldOutput 34
        }

    })
