package aockt.y2024

import io.github.jadarma.aockt.test.AdventDay
import io.github.jadarma.aockt.test.AdventSpec

@AdventDay(2024, 1, "Historian Hysteria")
class Y2024D01Test : AdventSpec<Y2024D01>({

    partOne {
        """
            1   2
            3   5
            1   4
        """.trimIndent() shouldOutput 6
    }

    partTwo {
        """
            3   4
            4   3
            2   5
            1   3
            3   9
            3   3
        """.trimIndent() shouldOutput 31
    }

})
