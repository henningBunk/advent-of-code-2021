package day07

import common.InputRepo
import common.readSessionCookie
import common.solve
import kotlin.math.abs

fun main(args: Array<String>) {
    val day = 7
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay07Part1, ::solveDay07Part2MoreEfficient)
}

// The solution for day 7 part 1 is: 352254
fun solveDay07Part1(input: List<String>): Int = calculateTotalFuelCosts(
    horizontalPositions = input.first().split(",").map { it.toInt() },
    fuelCostCalculation = { crabPosition, target -> abs(target - crabPosition) }
)

// The solution for day 7 part 2 is: 99053143
// Original solution. Runtime of just under 5 seconds
fun solveDay07Part2(input: List<String>): Int = calculateTotalFuelCosts(
    horizontalPositions = input.first().split(",").map { it.toInt() },
    fuelCostCalculation = { crabPosition, target ->
        (0..abs(target - crabPosition)).fold(0) { acc, step -> acc + step }
    }
)

// The solution for day 7 part 2 is: 99053143
// More effective solution. Runtime under 100 ms
fun solveDay07Part2MoreEfficient(input: List<String>): Int = calculateTotalFuelCosts(
    horizontalPositions = input.first().split(",").map { it.toInt() },
    fuelCostCalculation = { crabPosition, target -> fuelCost(abs(target - crabPosition)) }
)
fun fuelCost(n: Int): Int = n * (n + 1) / 2

fun calculateTotalFuelCosts(horizontalPositions: List<Int>, fuelCostCalculation: (Int, Int) -> Int): Int =
    (0..horizontalPositions.maxOrNull()!!)
        .minOfOrNull { candidate ->
            horizontalPositions.sumOf { crab -> fuelCostCalculation(crab, candidate) }
        } ?: -1