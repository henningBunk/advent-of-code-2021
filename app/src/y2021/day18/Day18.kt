package y2021.day18

import common.*
import common.annotations.AoCPuzzle

fun main(args: Array<String>) {
    Day18().solveThem()
}

@AoCPuzzle(2021, 18)
class Day18 : AocSolution {
    override val answers = Answers(samplePart1 = -1, samplePart2 = -1)

    override fun solvePart1(input: List<String>): Any {
        TODO()
    }

    override fun solvePart2(input: List<String>): Any {
        TODO()
    }
}

fun List<Pair<Int, Int>>.createSnailfishNumber() = Pair(Pair(Pair(Pair(1,1), Pair(2,2)), Pair(3,3)), Pair(4,4))