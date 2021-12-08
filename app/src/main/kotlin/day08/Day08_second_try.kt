package day08

import common.InputRepo
import common.readSessionCookie
import common.solve
import java.util.*

fun main(args: Array<String>) {
    val day = 8
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay08Part1_2, ::solveDay08Part2_2)
}

// The solution for day 8 part 1 is: 512
fun solveDay08Part1_2(input: List<String>): Int = -1

// The solution for day 8 part 2 is: 1091165
fun solveDay08Part2_2(input: List<String>): Int = input.sumOf { line ->
    line.split(" | ").let { (left, right) ->
        val codes = left.split(" ").map { it.toSortedSet() }.toMutableSet()

        val decoded: MutableMap<Int, Set<Char>> = mutableMapOf()

        val wiring = Wiring(left)

        decoded[1] = codes.singleAndRemove(segments = 2)
        decoded[4] = codes.singleAndRemove(segments = 4)
        decoded[7] = codes.singleAndRemove(segments = 3)
        decoded[8] = codes.singleAndRemove(segments = 7)
        decoded[3] = codes.singleAndRemove(segments = 5, sharesAllSegmentsWith = 1)
        decoded[9] = codes.singleAndRemove(segments = 6) { it.containsAll(decoded.getValue(3)) }
        decoded[6] =
            codes.singleAndRemove(segments = 6) { it.containsAll(decoded.getValue(4).minus(decoded.getValue(1))) }
        decoded[0] = codes.singleAndRemove(segments = 6)
        decoded[5] = codes.singleAndRemove(segments = 5) { decoded.getValue(9).containsAll(it) }
        decoded[2] = codes.single()

        right
            .split(" ")
            .map { digit -> decoded.filterValues { it == digit.toSortedSet() }.keys.single() }
            .joinToString("").also(::println)
            .toInt()
    }
}

private fun MutableSet<SortedSet<Char>>.singleAndRemove(
    segments: Int,
    sharesAllSegmentsWith: Int? = null,
    predicate: (SortedSet<Char>) -> Boolean = { true }
): Set<Char> = single {
    it.size == segments && it.containsAll(this) }
}.also { this.remove(it) }

private class Wiring(
    inputLine: String
) {
    val codes = inputLine.split(" ").map { it.toSortedSet() }.toMutableSet()

    val decoded: MutableMap<Int, Set<Char>> = mutableMapOf()
}