package aockt.y2024

import io.github.jadarma.aockt.test.AdventDay
import io.github.jadarma.aockt.test.AdventSpec

@AdventDay(2024, 6, "Guard Gallivant")
class Y2024D06Test :
    AdventSpec<Y2024D06>({

        val maze = """
                ....#.....
                .........#
                ..........
                ..#.......
                .......#..
                ..........
                .#..^.....
                ........#.
                #.........
                ......#...
            """


        partOne {
            maze.trimIndent() shouldOutput 42
        }

        partTwo {
            maze.trimIndent() shouldOutput 6
        }
    })
