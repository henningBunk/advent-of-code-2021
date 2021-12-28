package y2021.day02

import common.Answers
import common.AocSolution
import common.annotations.AoCPuzzle
import java.security.InvalidKeyException

fun main(args: Array<String>) {
    Day02Dive().solveThem()
}

@AoCPuzzle(2021, 2)
class Day02Dive : AocSolution {
    override val answers = Answers(samplePart1 = 150, samplePart2 = 900, part1 = 1670340, part2 = 1954293920)

    override fun solvePart1(input: List<String>): Int = input
        .map {
            it
                .split(" ")
                .let { (direction, value) -> Pair(direction, value.toInt()) }
        }
        .groupBy(
            keySelector = {
                when (it.first) {
                    "forward" -> "forward"
                    else -> "depth"
                }
            },
            valueTransform = {
                when (it.first) {
                    "up" -> -it.second
                    else -> it.second
                }
            }
        )
        .mapValues { it.value.sum() }
        .values
        .fold(1) { acc, value -> acc * value }

    override fun solvePart2(input: List<String>): Int = input
        .map {
            it
                .split(" ")
                .let { (direction, value) -> Command(direction, value.toInt()) }
        }
        .fold(Position(0, 0, 0)) { position, command ->
            when (command.direction) {
                "down" -> position.aim += command.amount
                "up" -> position.aim -= command.amount
                "forward" -> {
                    position.horizontal += command.amount
                    position.depth += command.amount * position.aim
                }
                else -> throw InvalidKeyException("${command.direction} is not a known command")
            }
            position
        }
        .let { it.horizontal * it.depth }

    class Position(var horizontal: Int, var depth: Int, var aim: Int)

    data class Command(val direction: String, val amount: Int)
}