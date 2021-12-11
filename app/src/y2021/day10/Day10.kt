package y2021.day10

import common.Answers
import common.AocSolution
import common.annotations.AoCPuzzle


fun main(args: Array<String>) {
    Day10().solveThem()
}

@AoCPuzzle(2021, 10)
class Day10 : AocSolution {
    override val answers = Answers(samplePart1 = 26397, samplePart2 = 288957, part1 = 392367, part2 = 2192104158)

    override fun solvePart1(input: List<String>): Any = input
        .mapNotNull { it.getFirstIllegalCharacter() }
        .sumOf { it.points }

    override fun solvePart2(input: List<String>): Any = input
        .filter { it.getFirstIllegalCharacter() == null }
        .map { it.getPointsForClosingString() }
        .sorted()
        .let { scores -> scores[scores.size / 2] }
}

fun String.getFirstIllegalCharacter(): Chunk? {
    val stack: MutableList<Chunk> = mutableListOf()
    this.forEach {
        val chunk = it.getChunk()
        when {
            it.isOpening() -> stack.add(chunk)
            else -> {
                if (stack.last().isCorrectClosing(chunk)) {
                    stack.removeLast()
                } else {
                    return chunk
                }
            }
        }
    }
    return null
}

fun String.getPointsForClosingString(): Long {
    val stack: MutableList<Chunk> = mutableListOf()
    this.forEach {
        val chunk = it.getChunk()
        when {
            it.isOpening() -> stack.add(chunk)
            else -> {
                if (stack.last().isCorrectClosing(chunk)) {
                    stack.removeLast()
                }
            }
        }
    }
    return stack
        .reversed()
        .fold(0L) { acc, next ->
            acc * 5 + next.missingPoints
        }
}

enum class Chunk(
    val opening: Char,
    val closing: Char,
    val points: Int,
    val missingPoints: Int,
) {
    ROUND('(', ')', points = 3, missingPoints = 1),
    SQUARE('[', ']', points = 57, missingPoints = 2),
    CURLY('{', '}', points = 1197, missingPoints = 3),
    POINTY('<', '>', points = 25137, missingPoints = 4);

    fun isCorrectClosing(chunk: Chunk): Boolean = this == chunk
}

fun Char.getChunk(): Chunk = when (this) {
    Chunk.CURLY.opening, Chunk.CURLY.closing -> Chunk.CURLY
    Chunk.POINTY.opening, Chunk.POINTY.closing -> Chunk.POINTY
    Chunk.SQUARE.opening, Chunk.SQUARE.closing -> Chunk.SQUARE
    Chunk.ROUND.opening, Chunk.ROUND.closing -> Chunk.ROUND
    else -> error("OH NOOOOOO")
}

fun Char.isOpening(): Boolean = when (this) {
    Chunk.CURLY.opening,
    Chunk.POINTY.opening,
    Chunk.SQUARE.opening,
    Chunk.ROUND.opening -> true
    else -> false
}
