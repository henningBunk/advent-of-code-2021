package y2021.day14

import common.Answers
import common.AocSolution
import common.annotations.AoCPuzzle

typealias PolymerLut = Map<String, List<String>>
typealias PolymerFrequency = Map<String, Long>

fun main(args: Array<String>) {
    Day14ExtendedPolymerization().solveThem()
}

@AoCPuzzle(2021, 14)
class Day14ExtendedPolymerization : AocSolution {
    override val answers = Answers(samplePart1 = 1588, samplePart2 = 2188189693529, part1 = 2797, part2 = 2926813379532)

    override fun solvePart1(input: List<String>): Long {
        val (startingPolymer, polymerLut) = input.parse()
        return solvePuzzle(startingPolymer, polymerLut, steps = 10)
    }

    override fun solvePart2(input: List<String>): Long {
        val (startingPolymer, polymerLut) = input.parse()
        return solvePuzzle(startingPolymer, polymerLut, steps = 40)
    }

    private fun solvePuzzle(startingPolymer: String, polymerLut: PolymerLut, steps: Int): Long =
        startingPolymer
            .countPolymerFrequencies()
            .proceed(steps, polymerLut)
            .getLetterFrequencies(startingPolymer)
            .sorted()
            .let { it.last() - it.first() }

    private fun String.countPolymerFrequencies(): PolymerFrequency = this
        .windowed(2)
        .groupBy { it }
        .mapValues { it.value.size.toLong() }

    private fun PolymerFrequency.proceed(steps: Int, lut: PolymerLut): PolymerFrequency {
        var mapA: Map<String, Long> = this
        repeat(steps) {
            val mapB: MutableMap<String, Long> = mutableMapOf()
            mapA.forEach { (polymer, amount) ->
                lut[polymer]?.forEach {
                    mapB[it] = mapB.getOrDefault(it, 0) + amount
                }
            }
            mapA = mapB
        }
        return mapA
    }

    private fun PolymerFrequency.getLetterFrequencies(startingPolymer: String): List<Long> {
        fun Char.isOnEdge(): Boolean =
            this == startingPolymer.first() || this == startingPolymer.last()

        return keys
            .joinToString("")
            .toList()
            .distinct()
            .map { letter ->
                this
                    .filter { it.key.contains(letter) }
                    .map { (polymer, amount) -> if (polymer == "$letter$letter") 2 * amount else amount }
                    .sum()
                    .let {
                        it / 2 + if (letter.isOnEdge()) 1 else 0
                    }
            }
    }

    private fun List<String>.parse(): Pair<String, PolymerLut> = Pair(
        first(),
        subList(2, this.size)
            .map { it.split(" -> ") }
            .associate { (left, right) ->
                Pair(
                    left,
                    listOf("${left.first()}$right", "$right${left.last()}")
                )
            }
    )
}