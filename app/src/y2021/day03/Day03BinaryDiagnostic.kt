package y2021.day03

import common.Answers
import common.AocSolution
import common.annotations.AoCPuzzle
import transpose

fun main(args: Array<String>) {
    Day03BinaryDiagnostic().solveThem()
}

@AoCPuzzle(2021, 3)
class Day03BinaryDiagnostic : AocSolution {
    override val answers = Answers(samplePart1 = 198, samplePart2 = 230, part1 = 3687446, part2 = 4406844)

    override fun solvePart1(input: List<String>): Any {
        val gamma = input
            .map { it.toList().map(Character::getNumericValue) }
            .transpose()
            .map { it.findMostCommon() }
        val epsilon = gamma.map { it xor 1 }
        return gamma.toDecimal() * epsilon.toDecimal()
    }


    override fun solvePart2(input: List<String>): Any {
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

    private fun List<Int>.findMostCommon(): Int =
        if (count { it == 1 } >= count { it == 0 }) 1 else 0

    private fun List<Int>.findLeastCommon(): Int =
        if (count { it == 1 } >= count { it == 0 }) 0 else 1

    private fun oxygenGeneratorRatingBitCriteria(
        bit: List<Int>,
        transposedDiagnosticReport: List<List<Int>>,
        index: Int
    ): Boolean {
        val mostCommonForIndex = transposedDiagnosticReport
            .map { it.findMostCommon() }
            .get(index)
        return bit[index] == mostCommonForIndex
    }

    private fun co2ScrubberRatingBitCriteria(bit: List<Int>, transposedDiagnosticReport: List<List<Int>>, index: Int): Boolean {
        val leastCommonForIndex = transposedDiagnosticReport
            .map { it.findLeastCommon() }
            .get(index)
        return bit[index] == leastCommonForIndex
    }

    private fun findRecursive(
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

    private fun List<Int>.toDecimal() = joinToString(separator = "").toInt(2)
}