package aockt.y2024

import aockt.y2024.Y2024D09.Block
import aockt.y2024.Y2024D09.fillEmpty
import aockt.y2024.Y2024D09.findFirstEmptyId
import aockt.y2024.Y2024D09.getLastFileById
import aockt.y2024.Y2024D09.getLastFileId
import aockt.y2024.Y2024D09.getStartIndexOfLastFile
import aockt.y2024.Y2024D09.removeLastFile
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

        test("should move test files") {
            val blocks = Y2024D09.diskMapToBlocks(testInput)
            Y2024D09.moveFiles(blocks).joinToString(separator = "") shouldBe
                    "00992111777.44.333....5555.6666.....8888.."
        }

        test("should calculate checksum") {
            val blocks = Y2024D09.diskMapToBlocks(testInput)
            val movedBlocks = Y2024D09.moveBlocks(blocks)
            Y2024D09.calculateChecksum(movedBlocks) shouldBe 1928
        }

        test("should calculate checksum of moved files") {
            val blocks = Y2024D09.diskMapToBlocks(testInput)
            val movedFiles = Y2024D09.moveFiles(blocks)
            Y2024D09.calculateChecksum(movedFiles) shouldBe 2858
        }

        test("should fill specified empty space with file") {
            val disk = listOf(
                Block(0),
                Block.EMPTY,
                Block.EMPTY,
                Block(1),
                Block(1),
                Block(1),
                Block.EMPTY,
                Block.EMPTY,
                Block(2),
                Block(2),
            )

            disk.fillEmpty(1, listOf(Block(3), Block(3))) shouldBe listOf(
                Block(0),
                Block(3),
                Block(3),
                Block(1),
                Block(1),
                Block(1),
                Block.EMPTY,
                Block.EMPTY,
                Block(2),
                Block(2),
            )

            disk.getLastFileById(2) shouldBe listOf(
                Block(2),
                Block(2),
            )

            disk.getStartIndexOfLastFile(listOf(Block(2), Block(2))) shouldBe 8
        }

        test("should find first empty id") {
            val disk = listOf(
                Block(0),
                Block(1),
                Block(1),
                Block(1),
                Block.EMPTY,
                Block.EMPTY,
                Block(2),
                Block(2),
                Block.EMPTY,
                Block.EMPTY,
                Block.EMPTY,
                Block(1),
                Block(1),


            )

            disk.findFirstEmptyId(2) shouldBe 4
            disk.findFirstEmptyId(3) shouldBe 8
            disk.findFirstEmptyId(4) shouldBe -1
            disk.getLastFileId() shouldBe 1
        }

        test("should return last file by id") {
            val disk = listOf(
                Block(0),
                Block(1),
                Block(1),
                Block(1),
                Block(2),
                Block(2),
                Block.EMPTY,
                Block.EMPTY,
            )

            disk.getLastFileById(2) shouldBe listOf(
                Block(2),
                Block(2),
            )
        }



        test("should remove last file") {
            val disk = listOf(
                Block(0),
                Block(1),
                Block(1),
                Block(1),
                Block.EMPTY,
                Block.EMPTY,
                Block(2),
                Block(2),
            )

            val testFile = listOf(
                Block(0),
                Block(1),
                Block(1),
                Block(1),
                Block.EMPTY,
                Block.EMPTY,
                Block.EMPTY,
                Block.EMPTY,
            )

            disk.removeLastFile(2) shouldBe testFile

            testFile.removeLastFile(1) shouldBe listOf(
                Block(0),
                Block.EMPTY,
                Block.EMPTY,
                Block.EMPTY,
                Block.EMPTY,
                Block.EMPTY,
                Block.EMPTY,
                Block.EMPTY,
            )

        }

        partOne {
            testInput shouldOutput 1928
        }

        partTwo {
            testInput shouldOutput 2858
        }
    })
