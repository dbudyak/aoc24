package aockt.y2024

import aockt.y2024.Y2024D09.Block
import io.github.jadarma.aockt.test.AdventDay
import io.github.jadarma.aockt.test.AdventSpec
import io.kotest.matchers.shouldBe

@AdventDay(2024, 9, "Disk Fragmenter")
class Y2024D09Test :
    AdventSpec<Y2024D09>({

        val testInput = """
            2333133121414131402
        """.trimIndent()

        test("should convert disk map to blocks") {
            Y2024D09.diskMapToBlocks("12345") shouldBe listOf(
                Block(0),
                Block.EMPTY,
                Block.EMPTY,
                Block(1),
                Block(1),
                Block(1),
                Block.EMPTY,
                Block.EMPTY,
                Block.EMPTY,
                Block.EMPTY,
                Block(2),
                Block(2),
                Block(2),
                Block(2),
                Block(2),
            )
        }

        test("should convert another disk map to blocks") {
            Y2024D09.diskMapToBlocks("50321").joinToString(separator = "") shouldBe
                    "00000111..2"
        }


        test("should convert test disk map to blocks") {
            Y2024D09.diskMapToBlocks(testInput).joinToString(separator = "") shouldBe
                    "00...111...2...333.44.5555.6666.777.888899"
        }

        test("should move test blocks") {
            val blocks = Y2024D09.diskMapToBlocks(testInput)
            Y2024D09.moveBlocks(blocks).joinToString(separator = "") shouldBe
                    "0099811188827773336446555566.............."
        }

        test("should calculate checksum") {
            val blocks = Y2024D09.diskMapToBlocks(testInput)
            val movedBlocks = Y2024D09.moveBlocks(blocks)
            Y2024D09.calculateChecksum(movedBlocks) shouldBe 1928
        }

        partOne {
            testInput shouldOutput 1928
        }

        partTwo {
            testInput shouldOutput 2858
        }
    })
