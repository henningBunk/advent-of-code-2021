package y2021.day12

import common.Answers
import common.AocSolution
import common.annotations.AoCPuzzle

fun main(args: Array<String>) {
    Day12PassagePathing().solveThem()
}

@AoCPuzzle(2021, 12)
class Day12PassagePathing : AocSolution {
    override val answers = Answers(samplePart1 = 19, samplePart2 = 103, part1 = 3887, part2 = 104834)

    lateinit var caves: Map<String, List<String>>

    override fun solvePart1(input: List<String>): Any {
        initCaveMap(input)
        return findNumberOfPossibleRoutes("start", emptyList())
    }

    override fun solvePart2(input: List<String>): Any {
        initCaveMap(input)
        return findNumberOfPossibleRoutes("start", emptyList(), mayVisitOneSmallCaveTwice = true)
    }

    private fun initCaveMap(input: List<String>) {
        val mapBuilder = mutableMapOf<String, MutableList<String>>()
        input.forEach {
            it.split("-").let { (a, b) ->
                mapBuilder.getOrPut(a) { mutableListOf() }.add(b)
                mapBuilder.getOrPut(b) { mutableListOf() }.add(a)
            }
        }
        caves = mapBuilder
    }

    private fun findNumberOfPossibleRoutes(
        visit: String,
        visited: List<String>,
        mayVisitOneSmallCaveTwice: Boolean = false,
    ): Int {
        if (visit == "end") return 1
        val nowVisited = visited.toMutableList().apply { add(visit) }.toList()

        val neighboursICanVisit = caves[visit]?.filter { neighbour ->
            when {
                neighbour == "start" -> false
                neighbour.first().isLowerCase() -> {
                    neighbour !in visited || (mayVisitOneSmallCaveTwice && !hasVisitedASmallCaveTwiceAlready(nowVisited))
                }
                else -> true
            }
        }
        if (neighboursICanVisit?.isEmpty() == true) return 0

        return neighboursICanVisit?.sumOf { neighbour ->
            findNumberOfPossibleRoutes(
                visit = neighbour,
                visited = nowVisited,
                mayVisitOneSmallCaveTwice = mayVisitOneSmallCaveTwice
            )
        } ?: 0
    }

    private fun hasVisitedASmallCaveTwiceAlready(visited: List<String>): Boolean {
        val smallCavesVisited = visited.filter { it.first().isLowerCase() && it != "start" }
        return smallCavesVisited.size != smallCavesVisited.distinct().size
    }
}
