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


    fun moveFiles(blocks: List<Block>) : List<Block> {

        fun moveFiles(lastId: Long = blocks.getLastFileId(), tempList: MutableList<Block>): MutableList<Block> {
            val lastFile = tempList.getLastFileById(lastId)
            val emptyIdx = tempList.findFirstEmptyId(lastFile.size)
            var result = tempList

            if (emptyIdx != -1 && tempList.getStartIndexOfLastFile(lastFile) > emptyIdx) {
                val removed = tempList.removeLastFile(lastId)
                val filled = removed.fillEmpty(emptyIdx, lastFile)
                result = filled.toMutableList()
            }

            return if (lastId <= 0) {
                result
            } else {
                moveFiles(lastId.minus(1), result)
            }
        }

        return moveFiles(blocks.getLastFileId(), blocks.toMutableList())
    }

    fun List<Block>.getLastFileById(id: Long): List<Block> = this.filter { it.id == id }
    fun List<Block>.getStartIndexOfLastFile(id: List<Block>): Int = this.indexOfFirst { it == id.first() }
    fun List<Block>.getLastFileId(): Long = this.last { it != Block.EMPTY }.id
    fun List<Block>.removeLastFile(lastId: Long): List<Block> = this.map { if (it.id == lastId) Block.EMPTY else it}
    fun List<Block>.fillEmpty(startIdx: Int, file: List<Block>): List<Block> =
        this.subList(0, startIdx) + file + this.subList(startIdx + file.size, this.size)
    fun List<Block>.findFirstEmptyId(fileSize: Int) : Int = let { list ->
        list.forEachIndexed { index, block ->
            if (block == Block.EMPTY && index + fileSize <= list.size) {
                val printSublist = list.subList(index, index + fileSize)
                if (printSublist.all { it == Block.EMPTY }) {
                    return@let index
                }
            }
        }
        return@let -1
    }

    fun calculateChecksum(blocks: List<Block>): Long = blocks
        .foldIndexed(0) { index, acc, block ->
            if (block != Block.EMPTY) acc + block.id * index else acc
        }

    override fun partOne(input: String) = diskMapToBlocks(input)
        .let { blocks -> moveBlocks(blocks) }
        .let { blocks -> calculateChecksum(blocks) }

    override fun partTwo(input: String) = diskMapToBlocks(input)
        .let { blocks -> moveFiles(blocks) }
        .let { blocks -> calculateChecksum(blocks) }

}
