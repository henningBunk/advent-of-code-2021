package day08

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 8
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay08Part1, ::solveDay08Part2)
}


// The solution for day 8 part 1 is: 512
fun solveDay08Part1(input: List<String>): Int = input
    .parseToDisplays()
    .map { it.fourDigitOutputValue }
    .flatten()
    .count {
        when (it.length) {
            2, 3, 4, 7 -> true
            else -> false
        }
    }

// The solution for day 8 part 2 is: 1091165
fun solveDay08Part2(input: List<String>): Int = input
    .parseToDisplays()
    .map { display -> display.read() }
    .sum()

data class Display(
    val tenUniqueSignalPatterns: List<String>,
    val fourDigitOutputValue: List<String>
) {

    private val wiring: Array<Char> = decode(tenUniqueSignalPatterns, fourDigitOutputValue)

    fun read(): Int = fourDigitOutputValue
        .map { it.getDigit(wiring) }
        .joinToString("")
        .toInt()

    /**
     * Decode the wiring by using an array of length 7.
     * Each item of the array represents on segment of the 7 segment display
     * Each item holds an array of chars, starting with abcdefg, representing all possibilities which code could be decoding this segment
     * With few steps one after another we can reduce these possibilities until each item in the array will only contain one code.
     * This is the correct wiring.
     *
     * The indices in the array represent the following segments:
     *
     *         0
     *     ┏━━━━━━━┓
     *     ┃       ┃
     *   1 ┃       ┃ 2
     *     ┃   3   ┃
     *     ┣━━━━━━━┫
     *     ┃       ┃
     *   4 ┃       ┃ 5
     *     ┃       ┃
     *     ┗━━━━━━━┛
     *         6
     *
     */
    private fun decode(
        tenUniqueSignalPatterns: List<String>,
        fourDigitOutputValue: List<String>
    ): Array<Char> {
        val data: List<Array<Char>> = (tenUniqueSignalPatterns + fourDigitOutputValue)
            .map { it.toCharArray().toTypedArray() }
        val possibleCandidates: Array<Array<Char>> = Array(7) { arrayOf('a', 'b', 'c', 'd', 'e', 'f', 'g') }

        // Distinct Numbers 1, 4 or 7
        data.forEach { numberCode: Array<Char> ->
            when (numberCode.size) {
                2 -> possibleCandidates.narrowDownCandidates(numberCode, 2, 5) // 1
                3 -> possibleCandidates.narrowDownCandidates(numberCode, 0, 2, 5) // 7
                4 -> possibleCandidates.narrowDownCandidates(numberCode, 1, 3, 2, 5) // 4
            }
        }

        // Five segment numbers 2, 3, or 5
        val sharedByFiveSegmentNumbers = data
            .filter { it.size == 5 }
            .fold(setOf('a', 'b', 'c', 'd', 'e', 'f', 'g')) { rest, next ->
                rest.intersect(next.toSet())
            }.toTypedArray()

        // The shared ones must be the horizontal ones
        possibleCandidates.narrowDownCandidates(sharedByFiveSegmentNumbers, 0, 3, 6)

        // Six segment numbers 0, 6, 9
        val sharedBySixSegmentNumbers: Array<Char> = data
            .filter { it.size == 6 }
            .map { it.toSet() }
            .let { sixSegmentNumbers ->
                arrayOf(
                    sixSegmentNumbers[1].union(sixSegmentNumbers[2]).minus(sixSegmentNumbers[0]).first(),
                    sixSegmentNumbers[0].union(sixSegmentNumbers[2]).minus(sixSegmentNumbers[1]).first(),
                    sixSegmentNumbers[0].union(sixSegmentNumbers[1]).minus(sixSegmentNumbers[2]).first(),
                )
            }

        val middleHorizontal: Array<Char> = sharedByFiveSegmentNumbers
            .toSet()
            .intersect(sharedBySixSegmentNumbers.toSet())
            .toTypedArray()
        possibleCandidates.narrowDownCandidates(middleHorizontal, 3)

        val sharedBySixSegmentNumbersWithoutHorizontal =
            sharedBySixSegmentNumbers.filter { it != middleHorizontal.first() }

        if (possibleCandidates[2].contains(sharedBySixSegmentNumbersWithoutHorizontal[0]) &&
            possibleCandidates[5].contains(sharedBySixSegmentNumbersWithoutHorizontal[0])
        ) {
            // sharedBySixSegmentNumbersWithoutHorizontal[0] must be a 9 because it shares both segments with one
            possibleCandidates.narrowDownCandidates(arrayOf(sharedBySixSegmentNumbersWithoutHorizontal[0]), 2)
            // sharedBySixSegmentNumbersWithoutHorizontal[1] must be a 6
            possibleCandidates.narrowDownCandidates(arrayOf(sharedBySixSegmentNumbersWithoutHorizontal[1]), 4)
        } else {
            // sharedBySixSegmentNumbersWithoutHorizontal[0] must be a 6 because it shares both segments with one
            possibleCandidates.narrowDownCandidates(arrayOf(sharedBySixSegmentNumbersWithoutHorizontal[0]), 4)
            // sharedBySixSegmentNumbersWithoutHorizontal[1] must be a 9
            possibleCandidates.narrowDownCandidates(arrayOf(sharedBySixSegmentNumbersWithoutHorizontal[1]), 2)
        }

        // Bottom horizontal is the last one we didn't address, it should be unique though by this time.

        return possibleCandidates.map { it.first() }.toCharArray().toTypedArray()
    }

    private fun String.getDigit(wiring: Array<Char>): Int = when (length) {
        2 -> 1
        3 -> 7
        4 -> 4
        7 -> 8
        5 -> when { // 2, 3, 5
            contains(wiring[1]) -> 5
            contains(wiring[4]) -> 2
            else -> 3
        }
        else -> when { // 0, 6, 9
            !contains(wiring[3]) -> 0
            contains(wiring[2]) -> 9
            else -> 6
        }
    }

    /**
     * Called on our array for the candidates. Two things happen:
     *  1. Only the chars which are given as candidates parameter remain for the given segments.
     *  2. The given chars are removed from all other segments
     */
    private fun Array<Array<Char>>.narrowDownCandidates(
        candidates: Array<Char>,
        vararg segments: Int
    ) {
        removeCandidate(candidates.opposite, *segments.toTypedArray().toIntArray())
        removeCandidate(candidates, *segments.toList().opposite.toTypedArray().toIntArray())
    }

    private val Array<Char>.opposite: Array<Char>
        get() = "abcdefg".filterNot(::contains).toCharArray().toTypedArray()

    private val List<Int>.opposite: List<Int>
        get() = (0..6).filterNot { contains(it) }

    private fun Array<Array<Char>>.removeCandidate(number: Array<Char>, vararg segments: Int) {
        segments.forEach { segment ->
            this[segment] = this[segment].filterNot(number::contains).toTypedArray()
        }
    }
}

fun List<String>.parseToDisplays(): List<Display> = map { line ->
    line.split("|")
        .let { (tenUniqueSignalPatterns, fourDigitOutputValue) ->
            Display(
                tenUniqueSignalPatterns.trim().split(" "),
                fourDigitOutputValue.trim().split(" ")
            )
        }
}
