package y2021.day15

import common.*
import common.annotations.AoCPuzzle
import kotlin.math.min

fun main(args: Array<String>) {
    Day15Chiton().solveThem(ignorePart1 = true)
}

@AoCPuzzle(2021, 15)
class Day15Chiton : AocSolution {
    override val answers = Answers(samplePart1 = 40, samplePart2 = 315, part1 = 398)

    override fun solvePart1(input: List<String>): Any {
        val map = ChitonMap(input)
        return map.findMinimalWayCost()
    }

    // ⏱ Calculating it took 386988,18ms
    override fun solvePart2(input: List<String>): Any {
        val map = ChitonMap(input, mapExpandsBy = 5)
        return map.findMinimalWayCost()
    }
}

class ChitonMap(input: List<String>, mapExpandsBy: Int = 1) {

    private val labels: MutableMap<Coordinate, Int> = hashMapOf()

    private val nodes: MutableList<Node> = mutableListOf()

    private val lastX = (input[0].length * mapExpandsBy) - 1
    private val lastY = (input.size * mapExpandsBy) - 1

    init {
        val riskLevels = input.map { it.map(Character::getNumericValue) }
        val tileWidth = riskLevels[0].size
        val tileHeight = riskLevels.size

        for (y in (0..(lastY))) {
            for (x in (0..lastX)) {
                addNode(Coordinate(x, y))
            }
        }
        for (y in (0..lastY)) {
//            println()
            for (x in (0..lastX)) {
//                print((riskLevels[y % tileHeight][(x)% tileWidth] + x / tileWidth + y / tileHeight).let {
//                    if (it > 9) it % 10 + 1 else it
//                })
                if (x - 1 >= 0) {
                    val cost = (riskLevels[y % tileHeight][(x - 1) % tileWidth] + (x-1) / tileWidth + y / tileHeight).let {
                        if (it > 9) it % 10 + 1 else it
                    }
                    addEdge(Coordinate(x, y), Coordinate(x - 1, y), cost)
                }
                if (x + 1 <= lastX) {
                    val cost = (riskLevels[y % tileHeight][(x + 1) % tileWidth] + (x+1) / tileWidth + y / tileHeight).let {
                        if (it > 9) it % 10 + 1 else it
                    }
                    addEdge(Coordinate(x, y), Coordinate(x + 1, y), cost)
                }
                if (y - 1 >= 0) {
                    val cost = (riskLevels[(y - 1) % tileHeight][x % tileWidth] + x / tileWidth + (y-1) / tileHeight).let {
                        if (it > 9) it % 10 + 1 else it
                    }
                    addEdge(Coordinate(x, y), Coordinate(x, y - 1), cost)
                }
                if (y + 1 <= lastY) {
                    val cost = (riskLevels[(y + 1) % tileHeight][x % tileWidth] + x / tileWidth + (y+1) / tileHeight).let {
                        if (it > 9) it % 10 + 1 else it
                    }
                    addEdge(Coordinate(x, y), Coordinate(x, y + 1), cost)
                }
            }
        }
    }

    private fun addNode(label: Coordinate) {
        nodes.add(Node())
        labels[label] = nodes.lastIndex
    }

    private fun addEdge(source: Coordinate, destination: Coordinate, cost: Int) {
        nodes[labels[source]!!].addEdge(Edge(destination, cost))
    }

    data class Coordinate(val x: Int, val y: Int)

    data class Edge(val destination: Coordinate, val cost: Int)

    class Node {
        private val _edges: MutableList<Edge> = mutableListOf()
        val edges: List<Edge>
            get() = _edges

        fun addEdge(edge: Edge) {
            _edges.add(edge)
        }
    }

    fun findMinimalWayCost(): Int {
        var percentFinished = 0
        val idStart = labels[Coordinate(0, 0)]!!
        val idEnd = labels[Coordinate(lastX, lastY)]!!

        val nodesToVisit = labels.values.associateWith { Int.MAX_VALUE }.toMutableMap()
        nodesToVisit[idStart] = 0

        var costToEnd = Int.MAX_VALUE
        while (nodesToVisit.isNotEmpty()) {
            val nodeCost = nodesToVisit.values.minOf { it }
            val nodeId = nodesToVisit.filter { (k, v) -> v == nodeCost }.keys.first()
            nodesToVisit.remove(nodeId)

            nodes[nodeId].edges.forEach { (destination, cost) ->
                val targetId = labels[destination]
                val previousTargetCost = nodesToVisit[targetId]
                if (targetId != null && previousTargetCost != null) {
                    nodesToVisit[targetId] = min(previousTargetCost, nodeCost + cost)
                }
            }
            if (nodeId == idEnd) {
                costToEnd = nodeCost
            }

            (nodesToVisit.size * 100 / nodes.size).let { percent ->
                if (percentFinished != percent) {
                    percentFinished = percent
                    println("$percent%")
                }
            }
        }
        return costToEnd
    }
}