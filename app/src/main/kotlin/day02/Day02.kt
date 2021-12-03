package day02

import common.InputRepo
import common.readSessionCookie
import common.solve
import java.security.InvalidKeyException

fun main(args: Array<String>) {
    val day = 2
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay02Part1, ::solveDay02Part2)
}

    fun solveDay02Part1(input: List<String>): Int = input
        .map { it
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

    fun solveDay02Part2(input: List<String>): Int = input
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