package aockt.y2024

import aockt.y2024.Y2024D11.blink
import aockt.y2024.Y2024D11.compareAndSet
import aockt.y2024.Y2024D11.splitHalfDigits
import io.github.jadarma.aockt.test.AdventDay
import io.github.jadarma.aockt.test.AdventSpec
import io.kotest.matchers.shouldBe

@AdventDay(2024, 11, "Plutonian Pebbles")
class Y2024D11Test :
    AdventSpec<Y2024D11>({

        test("should split in a half") {
            2024L.splitHalfDigits() shouldBe (20L to 24L)
            11L.splitHalfDigits() shouldBe (1L to 1L)
        }

        test("should change after blink") {
            blink(listOf(0, 1, 11, 0)) shouldBe listOf(1, 2024, 1, 1, 1)
        }

        val state5 = listOf(1036288L, 7L, 2L, 20L, 24L, 4048L, 1L, 4048L, 8096L, 28L, 67L, 60L, 32L)
        val state6 = listOf(2097446912L, 14168L, 4048L, 2L, 0L, 2L, 4L, 40L, 48L, 2024L, 40L, 48L, 80L, 96L, 2L, 8L, 6L, 7L, 6L, 0L, 3L, 2)

        test("should change after blink 2") {
            val state1 = listOf(253000L, 1L, 7L)
            val state2 = listOf(253L, 0L, 2024L, 14168L)
            val state3 = listOf(512072L, 1L, 20L, 24L, 28676032L)
            val state4 = listOf(512L, 72L, 2024L, 2L, 0L, 2L, 4L, 2867L, 6032L)
            Y2024D11.parseInput("125 17").let { blink(it) } shouldBe state1
            blink(state1) shouldBe state2
            blink(state2) shouldBe state3
            blink(state3) shouldBe state4
            blink(state4) shouldBe state5
            blink(state5) shouldBe state6
        }

        test("should blink and compare and set") {
            val map = mutableMapOf<Long, Long>()
            map.compareAndSet(0)
            map shouldBe mapOf(1L to 1)

            val testMap = state5.associateBy({ it }, { 1L }).toMutableMap()
            val testMapKeys = testMap.keys.toList()
            for (k in testMapKeys) {
                testMap.compareAndSet(k)
            }
            testMap.keys.sorted() shouldBe state6.distinct().sorted()
        }

        partOne {
            "125 17" shouldOutput "55312"
        }

        partTwo {
            "125 17" shouldOutput "55312"
        }
    })
