package y${YEAR}.day${DAY}

import common.*
import common.annotations.AoCPuzzle

fun main(args: Array<String>) {
    Day${DAY}().solveThem()
}

@AoCPuzzle(${YEAR}, ${DAY})
class Day${DAY} : AocSolution {
    override val answers = Answers(samplePart1 = -1, samplePart2 = -1)

    override fun solvePart1(input: List<String>): Any {
        TODO()
    }

    override fun solvePart2(input: List<String>): Any {
        TODO()
    }
}