package y2021.day04

import common.Answers
import common.AocSolution
import common.annotations.AoCPuzzle

fun main(args: Array<String>) {
    Day04GiantSquid().solveThem()
}

@AoCPuzzle(2021, 4)
class Day04GiantSquid : AocSolution {
    override val answers = Answers(samplePart1 = 4512, samplePart2 = 1924, part1 = 10680, part2 = 31892)

    override fun solvePart1(input: List<String>): Any {
        val (drawnNumbers, boards) = readInput(input)

        drawnNumbers.takeWhile {
            boards.forEach { board -> board.check(it) }
            boards.none { it.hasWon() }
        }

        return boards.find { board -> board.hasWon() }?.getScore() ?: -1
    }

    override fun solvePart2(input: List<String>): Any {
        val (drawnNumbers, boards) = readInput(input)

        drawnNumbers.takeWhile {
            boards.forEach { board -> board.check(it) }
            boards.count { !it.hasWon() } > 1
        }

        val lastBoardToWin = boards.find { !it.hasWon() }
        drawnNumbers.takeWhile {
            lastBoardToWin?.check(it)
            lastBoardToWin?.hasWon() == false
        }

        return lastBoardToWin?.getScore() ?: -1
    }

    fun readInput(input: List<String>, boardHeight: Int = 5): Pair<MutableList<Int>, List<Board>> {
        val inputs = input
            .first()
            .split(',')
            .map { it.toInt() }
            .toMutableList()
        val boards = input
            .drop(1)
            .filter { it.isNotEmpty() }
            .chunked(boardHeight)
            .map(::Board)
        return Pair(inputs, boards)
    }
}


