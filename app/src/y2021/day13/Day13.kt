package y2021.day13

import common.Answers
import common.AocSolution
import common.annotations.AoCPuzzle
import transpose

typealias Paper = List<List<Boolean>>

fun main(args: Array<String>) {
    Day13().solveThem(
        ignoreSamples = true
    )
}

@AoCPuzzle(2021, 13)
class Day13 : AocSolution {
    override val answers = Answers(samplePart1 = 17, samplePart2 = -1)

    override fun solvePart1(input: List<String>): Any = input
        .parse()
        .let { (paper, instructions) ->
            instructions
                .first()(paper)
                .flatten()
                .count { it }
        }


    override fun solvePart2(input: List<String>): Any {
        TODO("Not yet implemented.")
    }

    private fun List<String>.parse(): Pair<Paper, List<FoldingOperation>> = this
        .filter { it.isNotEmpty() }
        .partition { !it.startsWith("fold") }
        .let { (pointStrings, instructionStrings) ->
            val paper = pointStrings
                .map { it.split(",").let { it.first().toInt() to it.last().toInt() } }
                .let { points ->
                    val maxX = points.maxOf { it.first }
                    val maxY = points.maxOf { it.second }
                    val paper = List(maxY + 1) { MutableList(maxX + 1) { false } }
                    points.forEach { (x, y) ->
                        paper[y][x] = true
                    }
                    paper
                }

            val instructions = instructionStrings
                .map { it.split("=") }
                .map {
                    when {
                        it.first().endsWith("x") -> FoldLeft(it.last().toInt())
                        else -> FoldUp(it.last().toInt())
                    }
                }

            Pair(paper, instructions)
        }
}

interface FoldingOperation {
    operator fun invoke(paper: Paper): Paper
}

class FoldUp(private val y: Int) : FoldingOperation {
    override fun invoke(paper: Paper): Paper = paper
        .transpose()
        .let { FoldLeft(y)(it) }
        .transpose()
        .also { println("Folding up by $y") }
}

class FoldLeft(private val x: Int) : FoldingOperation {
    override fun invoke(paper: Paper): Paper = paper
        .map {
            it.subList(0, x)
                .zip(it.subList(x, it.size).reversed())
                .map { (a, b) -> a || b }
        }
        .also { println("Folding left by $x") }
}

fun List<List<Boolean>>.println() {
    this.forEach { line ->
        line.forEach { cell -> print(if(cell) '#' else '.') }
        kotlin.io.println()
    }
    kotlin.io.println()
}