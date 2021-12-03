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
        .map { it.toCharArray().toList() }
        .transpose()
        .map { it.findMostCommon() }
        .joinToBitString().also { println(it) }

    val epsilon = gamma.invert()

    return gamma.toInt(2) * epsilon.toInt(2)
}

fun solveDay03Part2(input: List<String>): Int {
    val inputAsCharList = input.map { it.toCharArray().toList() }
    val oxygenGeneratorRating = findRecursive(
        input = inputAsCharList,
        index = 0,
        criteria = ::oxygenGeneratorRatingBitCriteria
    )
        .joinToBitString()
        .toInt(2)

    val co2ScrubberRating = findRecursive(
        input = inputAsCharList,
        index = 0,
        criteria = ::co2ScrubberRatingBitCriteria
    )
        .joinToBitString()
        .toInt(2)

    println("Oxygen Generator Rating: $oxygenGeneratorRating")
    println("CO2 Scrubber Rating: $co2ScrubberRating")
    return oxygenGeneratorRating * co2ScrubberRating
}


fun <T> List<List<T>>.transpose(): List<List<T>> {
    val target: MutableList<List<T>> = emptyList<List<T>>().toMutableList()
    (0..first().lastIndex).forEach { index ->
        target.add(map { it[index] })
    }
    return target
}

fun List<Char>.findMostCommon(): Char =
    if (count { it == '1' } >= count { it == '0' }) '1' else '0'

fun List<Char>.findLeastCommon(): Char =
    if (count { it == '1' } >= count { it == '0' }) '0' else '1'

fun String.invert(): String = toList()
    .map { if (it == '0') '1' else '0' }
    .joinToString(separator = "")

fun List<Char>.joinToBitString(): String = joinToString(separator = "")

fun oxygenGeneratorRatingBitCriteria(bit: List<Char>, diagnosticReport: List<List<Char>>, index: Int): Boolean {
    val mostCommonForIndex = diagnosticReport
        .transpose()
        .map { it.findMostCommon() }
        .get(index)
    return bit[index] == mostCommonForIndex
}

fun co2ScrubberRatingBitCriteria(bit: List<Char>, diagnosticReport: List<List<Char>>, index: Int): Boolean {
    val leastCommonForIndex = diagnosticReport
        .transpose()
        .map { it.findLeastCommon() }
        .get(index)
    return bit[index] == leastCommonForIndex
}

fun findRecursive(
    input: List<List<Char>>,
    index: Int,
    criteria: (List<Char>, List<List<Char>>, Int) -> Boolean,
): List<Char> {
    println("Index: $index, Size: ${input.size}")

    val filteredInput = input.filter { criteria(it, input, index) }
    return when (filteredInput.size) {
        1 -> filteredInput.first()
        else -> findRecursive(filteredInput, index + 1, criteria)
    }
}