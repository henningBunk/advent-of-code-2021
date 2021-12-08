package day08

import common.InputRepo
import common.readSessionCookie
import common.solve

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

        decoded[1] = codes.single { it.size == 2 }.also { codes.remove(it) }
        decoded[4] = codes.single { it.size == 4 }.also { codes.remove(it) }
        decoded[7] = codes.single { it.size == 3 }.also { codes.remove(it) }
        decoded[8] = codes.single { it.size == 7 }.also { codes.remove(it) }

        decoded[3] = codes.single { it.size == 5 && it.containsAll(decoded[1]!!) }.also { codes.remove(it) }

        decoded[9] = codes.single { it.size == 6 && it.containsAll(decoded[3]!!) }.also { codes.remove(it) }
        decoded[6] = codes.single { it.size == 6 && it.containsAll(decoded[4]!!.minus(decoded[1]!!)) }
            .also { codes.remove(it) }
        decoded[0] = codes.single { it.size == 6 }.also { codes.remove(it) }

        decoded[5] = codes.single { it.size == 5 && decoded[9]!!.containsAll(it) }.also { codes.remove(it) }
        decoded[2] = codes.single { true }.also { codes.remove(it) }

        right
            .split(" ")
            .map { digit -> decoded.filterValues { it == digit.toSortedSet() }.keys.single() }
            .joinToString("").also(::println)
            .toInt()
    }
}
