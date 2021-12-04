package day04

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 4
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay04Part1, ::solveDay04Part2)
}

// Your puzzle answer was 10680.
fun solveDay04Part1(input: List<String>): Int {
    val (drawnNumbers, boards) = readInput(input)

    do {
        boards.forEach { board ->
            board.check(drawnNumbers.first())
        }
        drawnNumbers.removeFirst()
    } while (boards.none { it.hasWon() })

    return boards.find { board -> board.hasWon() }?.getScore() ?: -1
}

// Your puzzle answer was 31892.
fun solveDay04Part2(input: List<String>): Int {
    val (drawnNumbers, boards) = readInput(input)

    do {
        boards.forEach { board ->
            board.check(drawnNumbers.first())
        }
        drawnNumbers.removeFirst()
    } while (boards.count { !it.hasWon() } > 1)

    val lastBoardToWin = boards.find { !it.hasWon() }
    do {
        lastBoardToWin?.check(drawnNumbers.first())
        drawnNumbers.removeFirst()
    } while (lastBoardToWin?.hasWon() == false)

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


