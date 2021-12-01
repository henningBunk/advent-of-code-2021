package day01

import common.InputRepo
import common.readSessionCookie
import common.solve
import kotlin.math.min

fun main(args: Array<String>) {
    val day = 1
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay01Part1, ::solveDay01Part2)
    solve(day, input, ::solveDay01Part1Windowed, ::solveDay01Part2Windowed)
}

// Original solution
fun solveDay01Part1(input: List<String>): Int = input
    .map { it.toInt() }
    .countDepthIncreases()

fun solveDay01Part2(input: List<String>): Int = input
    .map { it.toInt() }
    .sumNeighbours(2)
    .countDepthIncreases()

private fun List<Int>.sumNeighbours(neighbours: Int): List<Int> =
    List(size) { index ->
        subList(index, min(index + neighbours + 1, size)).sum()
    }

private fun List<Int>.countDepthIncreases(): Int =
    mapIndexed { index, value ->
        if (index != 0 && get(index - 1) < value) 1 else 0
    }.sum()


// Alternative solution using .windowed
fun solveDay01Part1Windowed(input: List<String>): Int = input
    .map { it.toInt() }
    .windowed(2)
    .count { it[0] < it[1] }

fun solveDay01Part2Windowed(input: List<String>): Int = input
    .map { it.toInt() }
    .windowed(3)
    .map { it.sum() }
    .windowed(2)
    .count { it[0] < it[1] }
