package y2021.day13

import common.Answers
import common.AocSolution
import common.annotations.AoCPuzzle
import transpose
import kotlin.math.abs

typealias Paper = List<List<Boolean>>

fun main(args: Array<String>) {
    Day13TransparentOrigami().solveThem(
        ignoreSamples = false,
        ignoreRealInput = false,
    )
}

@AoCPuzzle(2021, 13)
class Day13TransparentOrigami : AocSolution {
    override val answers = Answers(
        samplePart1 = 17,
        part1 = 731,
        samplePart2 = """
                      |█████
                      |█   █
                      |█   █
                      |█   █
                      |█████
                      |     
                      |     """.trimMargin(),
        part2 = """
                      |████ █  █  ██  █  █  ██  ████ █  █  ██  
                      |   █ █ █  █  █ █  █ █  █ █    █  █ █  █ 
                      |  █  ██   █  █ █  █ █    ███  █  █ █    
                      | █   █ █  ████ █  █ █    █    █  █ █    
                      |█    █ █  █  █ █  █ █  █ █    █  █ █  █ 
                      |████ █  █ █  █  ██   ██  █     ██   ██  """.trimMargin()
    )

    override fun solvePart1(input: List<String>): Any = input
        .parse()
        .let { (paper, instructions) ->
            instructions
                .first()(paper)
                .flatten()
                .count { it }
        }


    override fun solvePart2(input: List<String>): Any = input
        .parse()
        .let { (paper, instructions) ->
            instructions
                .fold(paper) { paper, foldingInstruction -> foldingInstruction(paper) }
                .paperToString()
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
    override operator fun invoke(paper: Paper): Paper = paper
        .transpose()
        .let { FoldLeft(y)(it) }
        .transpose()
}

class FoldLeft(private val x: Int) : FoldingOperation {
    override operator fun invoke(paper: Paper): Paper = paper
        .map {
            val left = it.subList(0, x).reversed().toMutableList()
            val right = it.subList(x + 1, it.lastIndex + 1).toMutableList()
            when {
                left.size < right.size -> left
                else -> right
            }.let { it.addAll(List(abs(left.size - right.size)) { false }) }

            left.zip(right)
                .map { (a, b) -> a || b }
                .reversed()
        }
}

fun Paper.paperToString(): String {
    return joinToString("\n") {
        it.joinToString("") { if (it) "█" else " " }
    }
}