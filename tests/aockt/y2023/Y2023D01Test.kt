package aockt.y2023

import io.github.jadarma.aockt.test.AdventDay
import io.github.jadarma.aockt.test.AdventSpec

@AdventDay(2023, 1, "Trebuchet?!")
class Y2023D01Test : AdventSpec<Y2023D01>({

    partOne {
        "abc2de3f" shouldOutput 23
        """
        abc2de3f
        abc4de1f
        """.trimIndent() shouldOutput 23 + 41
    }

    partTwo {
        "abc2de3fseven" shouldOutput 27
        """
        abc2de3fseven
        aonebc4de1fone
        """.trimIndent() shouldOutput 27 + 11
    }

})
