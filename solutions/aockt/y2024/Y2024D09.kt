package aockt.y2024

import io.github.jadarma.aockt.core.Solution

object Y2024D09 : Solution {

    data class Block(val id: Long) {
        companion object {
            val EMPTY: Block = Block(-1)
        }

        override fun toString(): String = if (this == EMPTY) "." else id.toString()
    }

    fun diskMapToBlocks(diskMap: String): List<Block> = diskMap
        .toList()
        .map { it.toString().toLong() }
        .flatMapIndexed { index, block ->
            LongRange(1, block).map {
                when (index % 2) {
                    0 -> Block((index / 2).toLong())
                    else -> Block.EMPTY
                }
            }
        }

    fun moveBlocks(blocks: List<Block>): List<Block> {
        val list = blocks.toMutableList()
        while (list.any { it == Block.EMPTY }) {

            while (list.last() == Block.EMPTY) {
                list.removeLast()
            }

            val emptyFirstIndex = list.indexOfFirst { it == Block.EMPTY }

            if (emptyFirstIndex != -1) {
                list[emptyFirstIndex] = list.removeLast()
            }
        }
        return list + blocks.minus(list.toSet())
    }

    fun calculateChecksum(blocks: List<Block>): Long = blocks
        .filterNot { it == Block.EMPTY }
        .foldIndexed(0) { index, acc, block ->
            acc + block.id * index
        }

    override fun partOne(input: String) = diskMapToBlocks(input)
        .let { blocks -> moveBlocks(blocks) }
        .let { blocks -> calculateChecksum(blocks) }

    override fun partTwo(input: String) = Unit
}
