package aockt.y2024

import aockt.y2024.Y2024D04.Direction
import io.github.jadarma.aockt.core.Solution

typealias Maze = Array<CharArray>

const val OBSTACLE = '#'
const val VISITOR = '^'
const val VISITED = 'X'

fun Maze.hasNextObstacle(x: Int, y: Int, direction: Direction): Boolean =
    when (direction) {
        Direction.UP -> this[y - 1][x] == OBSTACLE
        Direction.RIGHT -> this[y][x + 1] == OBSTACLE
        Direction.DOWN -> this[y + 1][x] == OBSTACLE
        Direction.LEFT -> this[y][x - 1] == OBSTACLE
        else -> error("Invalid direction")
    }

fun Maze.markNextAsObstacle(x: Int, y: Int, direction: Direction) =
    when (direction) {
        Direction.UP -> this[y - 1][x] = OBSTACLE
        Direction.RIGHT -> this[y][x + 1] = OBSTACLE
        Direction.DOWN -> this[y + 1][x] = OBSTACLE
        Direction.LEFT -> this[y][x - 1] = OBSTACLE
        else -> error("Invalid direction")
    }

fun Maze.isEnd(x: Int, y: Int, direction: Direction): Boolean =
    when (direction) {
        Direction.UP -> this.getOrNull(y - 1)?.getOrNull(x) == null
        Direction.RIGHT -> this.getOrNull(y)?.getOrNull(x + 1) == null
        Direction.DOWN -> this.getOrNull(y + 1)?.getOrNull(x) == null
        Direction.LEFT -> this.getOrNull(y)?.getOrNull(x - 1) == null
        else -> error("Invalid direction")
    }

fun Maze.findStart(): Pair<Int, Int> {
    for (y in indices) {
        for (x in this[y].indices) {
            if (this[y][x] == VISITOR) {
                return x to y
            }
        }
    }
    error("No start found")
}

fun Maze.visit(x: Int, y: Int) {
    this[y][x] = VISITED
}

fun Maze.isVisited(x: Int, y: Int): Boolean =
    this[y][x] == VISITED

fun Maze.showVisitorPaths(v: Visitor) {
    val maze = this.map { it.copyOf() }.toTypedArray()
    maze[v.y][v.x] = VISITOR
    maze.forEach { println(it) }
}


object Y2024D06 : Solution {

    private fun parseInput(input: String): Maze =
        input.split("\n").map { it.toCharArray() }.toTypedArray()

    override fun partOne(input: String) = parseInput(input).let {
        val maze = it
        val v = Visitor(maze.findStart())
        var visitedCount = 1
        while (!maze.isEnd(v.x, v.y, v.direction)) {
            if (maze.hasNextObstacle(v.x, v.y, v.direction)) {
                v.turnRight()
            } else {
                v.moveForward()
                if (!maze.isVisited(v.x, v.y)) {
                    maze.visit(v.x, v.y)
                    visitedCount++
                }
            }
        }
        maze.visit(v.x, v.y)
        visitedCount
    }

    // 1480
    override fun partTwo(input: String) = parseInput(input).let {
        val origMaze = it
        val origVisitor = Visitor(origMaze.findStart())
        var loopCount = 0
        var visitorId = 0

        while (!origMaze.isEnd(origVisitor.x, origVisitor.y, origVisitor.direction)) {
            if (origMaze.hasNextObstacle(origVisitor.x, origVisitor.y, origVisitor.direction)) {
                origVisitor.turnRight()
            } else {

                val newMaze = origMaze.copyOf()
                val newVisitor = origVisitor.clone()

                if (newMaze.isEnd(newVisitor.x, newVisitor.y, newVisitor.direction)) {
                    break
                }

                newMaze.markNextAsObstacle(newVisitor.x, newVisitor.y, newVisitor.direction)

                val hasLoop = move(++visitorId, newMaze, newVisitor)
                if (hasLoop) {
                    println("Loop detected for visitor $visitorId")
                    loopCount++
                }

                origVisitor.moveForward()
                if (!origMaze.isVisited(origVisitor.x, origVisitor.y)) {
                    origMaze.visit(origVisitor.x, origVisitor.y)
                }

            }
        }

        loopCount
    }

    private fun move(visitorId: Int, maze: Maze, v: Visitor): Boolean {
        println("Launching new move for visitor $visitorId position ${v.x}, ${v.y}")
        var hasLoop = false
        while (!maze.isEnd(v.x, v.y, v.direction)) {
            if (maze.hasNextObstacle(v.x, v.y, v.direction)) {
                v.turnRight()
            } else {
                v.moveForward()
                if (!maze.isVisited(v.x, v.y)) {
                    maze.visit(v.x, v.y)
                }
            }
            hasLoop = v.tracker.hasLoop()

            if (hasLoop) break
            if (maze.isEnd(v.x, v.y, v.direction)) break
        }
        println("Finishing move for visitor $visitorId position ${v.x}, ${v.y}")
        return hasLoop
    }

}

class Visitor(startPos: Pair<Int, Int>) : Cloneable {
    var x = startPos.first
    var y = startPos.second
    var direction = Direction.UP
    val tracker = VisitorPathTracker()

    fun turnRight() {
        direction = when (direction) {
            Direction.UP -> Direction.RIGHT
            Direction.RIGHT -> Direction.DOWN
            Direction.DOWN -> Direction.LEFT
            Direction.LEFT -> Direction.UP
            else -> error("Invalid direction")
        }
    }

    fun moveForward() {
        when (direction) {
            Direction.UP -> y--
            Direction.RIGHT -> x++
            Direction.DOWN -> y++
            Direction.LEFT -> x--
            else -> error("Invalid direction")
        }
        tracker.addPosition(x to y)
    }

    public override fun clone(): Visitor =
        Visitor(x to y).also {
            it.direction = direction
            it.tracker.addPosition(x to y)
        }
}

class VisitorPathTracker {
    val list = ArrayList<Pair<Int, Int>>()

    fun addPosition(position: Pair<Int, Int>) {
        list.add(position)
    }

    fun hasLoop(): Boolean {
        return list.count { it == list.last() } == 10
    }
}


