package y2021.day01

import common.Answers
import common.AocSolution
import common.annotations.AoCPuzzle

fun main(args: Array<String>) {
    Day01SonarSweep().solveThem()
}

@AoCPuzzle(2021, 1)
class Day01SonarSweep : AocSolution {
    override val answers = Answers(samplePart1 = 7, samplePart2 = 5, part1 = 1400, part2 = 1429)

    override fun solvePart1(input: List<String>): Int = input
        .map { it.toInt() }
        .countDepthIncreases(2)

    override fun solvePart2(input: List<String>): Int = input
        .map { it.toInt() }
        .countDepthIncreases(4)

    private fun List<Int>.countDepthIncreases(stepWidth: Int): Int =
        windowed(stepWidth)
            .count { it.first() < it.last() }
}

