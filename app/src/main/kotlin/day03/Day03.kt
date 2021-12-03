package day03

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 3
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay03Part1, ::solveDay03Part2)
}

fun solveDay03Part1(input: List<String>): Int {
    val gamma = input
        .map { it.toList().map(Character::getNumericValue) }
        .transpose()
        .map { it.findMostCommon() }
    val epsilon = gamma.map { it xor 1 }
    return gamma.toDecimal() * epsilon.toDecimal()
}

fun solveDay03Part2(input: List<String>): Int {
    val inputAsIntList = input.map { it.toList().map(Character::getNumericValue) }
    val oxygenGeneratorRating = findRecursive(
        input = inputAsIntList,
        index = 0,
        criteria = ::oxygenGeneratorRatingBitCriteria
    ).toDecimal()

    val co2ScrubberRating = findRecursive(
        input = inputAsIntList,
        index = 0,
        criteria = ::co2ScrubberRatingBitCriteria
    ).toDecimal()

    println("Oxygen Generator Rating: $oxygenGeneratorRating")
    println("CO2 Scrubber Rating: $co2ScrubberRating")
    return oxygenGeneratorRating * co2ScrubberRating
}

fun List<Int>.findMostCommon(): Int =
    if (count { it == 1 } >= count { it == 0 }) 1 else 0

fun List<Int>.findLeastCommon(): Int =
    if (count { it == 1 } >= count { it == 0 }) 0 else 1

fun oxygenGeneratorRatingBitCriteria(bit: List<Int>, transposedDiagnosticReport: List<List<Int>>, index: Int): Boolean {
    val mostCommonForIndex = transposedDiagnosticReport
        .map { it.findMostCommon() }
        .get(index)
    return bit[index] == mostCommonForIndex
}

fun co2ScrubberRatingBitCriteria(bit: List<Int>, transposedDiagnosticReport: List<List<Int>>, index: Int): Boolean {
    val leastCommonForIndex = transposedDiagnosticReport
        .map { it.findLeastCommon() }
        .get(index)
    return bit[index] == leastCommonForIndex
}

fun findRecursive(
    input: List<List<Int>>,
    index: Int,
    criteria: (List<Int>, List<List<Int>>, Int) -> Boolean,
): List<Int> {
    val transposedInput = input.transpose()
    val filteredInput = input.filter { criteria(it, transposedInput, index) }

    return when (filteredInput.size) {
        1 -> filteredInput.first()
        else -> findRecursive(filteredInput, index + 1, criteria)
    }
}

fun <T> List<List<T>>.transpose(): List<List<T>> {
    val target: MutableList<List<T>> = emptyList<List<T>>().toMutableList()
    (0..first().lastIndex).forEach { index ->
        target.add(map { it[index] })
    }
    return target
}

fun List<Int>.toDecimal() = joinToString(separator = "").toInt(2)