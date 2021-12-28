package y2021.day08

import common.Answers
import common.AocSolution
import common.annotations.AoCPuzzle
import java.util.*

/**
 * A way nicer solution.
 * run it by using the main method in the other class
 */
@AoCPuzzle(2021, 8)
class Day08WithoutAnalyzingIndividualSegments : AocSolution {
    override val answers = Answers(samplePart1 = 26, samplePart2 = 61229, part1 = 512, part2 = 1091165)

    override fun solvePart1(input: List<String>): Any {
        TODO("Only part 2 is implemented")
    }

    override fun solvePart2(input: List<String>): Any = input.sumOf { line ->
        line.split(" | ").let { (left, right) ->
            val codes = left.split(" ").map { it.toSortedSet() }.toMutableSet()
            val decoded: MutableMap<Int, Set<Char>> = mutableMapOf()

            with(decoded) {
                put(1, codes.singleAndRemove(segments = 2))
                put(4, codes.singleAndRemove(segments = 4))
                put(7, codes.singleAndRemove(segments = 3))
                put(8, codes.singleAndRemove(segments = 7))
                put(9, codes.singleAndRemove(segments = 6, containsAllSegments = getValue(4)))
                put(0, codes.singleAndRemove(segments = 6, containsAllSegments = getValue(7)))
                put(6, codes.singleAndRemove(segments = 6))
                put(3, codes.singleAndRemove(segments = 5, containsAllSegments = getValue(1)))
                put(5, codes.singleAndRemove(segments = 5, containsAllSegments = getValue(4) - getValue(1)))
                put(2, codes.single())
            }

            right
                .split(" ")
                .map { digit -> decoded.filterValues { it == digit.toSortedSet() }.keys.single() }
                .joinToString("")
                .toInt()
        }
    }

    private fun MutableSet<SortedSet<Char>>.singleAndRemove(
        segments: Int,
        containsAllSegments: Set<Char>? = null
    ): Set<Char> = single {
        it.size == segments && it.containsAll(containsAllSegments ?: emptySet())
    }.also { this.remove(it) }

}



