package y2021.day09

import common.*
import common.annotations.AoCPuzzle

typealias CaveMap = List<MutableList<Int>>
typealias CaveQueue = MutableList<Pair<Int, Int>>

fun main(args: Array<String>) {
    Day09SmokeBasin().solveThem()
}

@AoCPuzzle(2021, 9)
class Day09SmokeBasin : AocSolution {
    override val answers = Answers(samplePart1 = 15, samplePart2 = 1134, part1 = 516, part2 = 1023660)

    override fun solvePart1(input: List<String>): Any = input
        .toCaveMap()
        .let { caveMap ->
            caveMap
                .getLowPoints()
                .sumOf { (x, y) -> caveMap[y][x] + 1 }
        }

    override fun solvePart2(input: List<String>): Any {
        val caveMap = input.toCaveMap()
        val lowPoints = caveMap.getLowPoints()
        val basinSizes = mutableListOf<Int>()

        for ((x, y) in lowPoints) {
            val queue: CaveQueue = mutableListOf()

            queue.add(x to y)
            caveMap[y][x] = 9
            var basinSize = 0

            while (queue.isNotEmpty()) {
                val (vX, vY) = queue.removeFirst()
                basinSize++
                queue.processNeighbour(x = vX - 1, y = vY, map = caveMap)
                queue.processNeighbour(x = vX + 1, y = vY, map = caveMap)
                queue.processNeighbour(x = vX, y = vY - 1, map = caveMap)
                queue.processNeighbour(x = vX, y = vY + 1, map = caveMap)
            }
            basinSizes.add(basinSize)
        }
        return basinSizes.sorted().takeLast(3).fold(1) { acc, next -> acc * next }
    }

    private fun List<String>.toCaveMap(): CaveMap =
        map { it.toList().map(Character::getNumericValue).toMutableList() }

    private fun CaveMap.getLowPoints(): MutableList<Pair<Int, Int>> {
        val lowPoints = mutableListOf<Pair<Int, Int>>()
        for (y in (0..lastIndex)) {
            for (x in (0..this[y].lastIndex)) {
                if (
                    (x - 1 < 0 || this[y][x] < this[y][x - 1]) &&
                    (x + 1 > this[y].lastIndex || this[y][x] < this[y][x + 1]) &&
                    (y - 1 < 0 || this[y][x] < this[y - 1][x]) &&
                    (y + 1 > lastIndex || this[y][x] < this[y + 1][x])
                ) {
                    lowPoints.add(x to y)
                }
            }
        }
        return lowPoints
    }

    private fun CaveQueue.processNeighbour(x: Int, y: Int, map: CaveMap) {
        if (!map.isOnMap(x, y)) return
        if (map[y][x] == 9) return
        add(x to y)
        map[y][x] = 9
    }

    private fun CaveMap.isOnMap(x: Int, y: Int): Boolean =
        x >= 0 &&
            y >= 0 &&
            y <= this.lastIndex &&
            x <= this[y].lastIndex
}