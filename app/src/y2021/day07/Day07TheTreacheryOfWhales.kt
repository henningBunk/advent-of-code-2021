package y2021.day07

import common.Answers
import common.AocSolution
import common.annotations.AoCPuzzle
import kotlin.math.abs

fun main(args: Array<String>) {
    Day07TheTreacheryOfWhales().solveThem()
}

@AoCPuzzle(2021, 7)
class Day07TheTreacheryOfWhales : AocSolution {
    override val answers = Answers(samplePart1 = 37, samplePart2 = 168, part1 = 352254, part2 = 99053143)

    override fun solvePart1(input: List<String>): Any = calculateTotalFuelCosts(
        horizontalPositions = input.first().split(",").map { it.toInt() },
        fuelCostCalculation = { crabPosition, target -> abs(target - crabPosition) }
    )

    override fun solvePart2(input: List<String>): Any = calculateTotalFuelCosts(
        horizontalPositions = input.first().split(",").map { it.toInt() },
        fuelCostCalculation = { crabPosition, target ->
            (0..abs(target - crabPosition)).fold(0) { acc, step -> acc + step }
        }
    )

    private fun calculateTotalFuelCosts(horizontalPositions: List<Int>, fuelCostCalculation: (Int, Int) -> Int): Int =
        (0..horizontalPositions.maxOrNull()!!)
            .minOfOrNull { candidate ->
                horizontalPositions.sumOf { crab -> fuelCostCalculation(crab, candidate) }
            } ?: -1
}

/**
 * Runtime under 100 ms
 */
@AoCPuzzle(2021, 7)
class Day07UsingTriangleNumbers : AocSolution {
    override val answers = Answers(samplePart1 = 37, samplePart2 = 168, part1 = 352254, part2 = 99053143)

    override fun solvePart1(input: List<String>): Any = {
        TODO("Only part two is implemented")
    }

    override fun solvePart2(input: List<String>): Any = calculateTotalFuelCosts(
        horizontalPositions = input.first().split(",").map { it.toInt() },
        fuelCostCalculation = { crabPosition, target -> fuelCost(abs(target - crabPosition)) }
    )

    private fun fuelCost(n: Int): Int = n * (n + 1) / 2

    private fun calculateTotalFuelCosts(horizontalPositions: List<Int>, fuelCostCalculation: (Int, Int) -> Int): Int =
        (0..horizontalPositions.maxOrNull()!!)
            .minOfOrNull { candidate ->
                horizontalPositions.sumOf { crab -> fuelCostCalculation(crab, candidate) }
            } ?: -1
}

/**
 * Even more effective solution!
 * Runtime under 20 ms.
 * Wow the higher order function takes a hefty toll
 */
@AoCPuzzle(2021, 7)
class Day07UsingTriangleNumbersButNoHigherOrderFunction : AocSolution {
    override val answers = Answers(samplePart1 = 37, samplePart2 = 168, part1 = 352254, part2 = 99053143)

    override fun solvePart1(input: List<String>): Any = {
        TODO("Only part two is implemented")
    }

    override fun solvePart2(input: List<String>): Any {
        val horizontalPositions = input.first().split(",").map { it.toInt() }
        return (0..horizontalPositions.maxOrNull()!!)
            .minOfOrNull { candidate ->
                horizontalPositions.sumOf { crab -> fuelCost(abs(crab - candidate)) }
            } ?: -1
    }

    private fun fuelCost(n: Int): Int = n * (n + 1) / 2
}

/**
 * Fastest yet, instead of finding the best row by brute forcing them all, just take the average and take the floor of it.
 * Runs in 500 us
 */
@AoCPuzzle(2021, 7)
class Day07UsingTriangleNumbersAndGettingTheCorrectRowByAveraging : AocSolution {
    override val answers = Answers(samplePart1 = 37, samplePart2 = 168, part1 = 352254, part2 = 99053143)

    override fun solvePart1(input: List<String>): Any = {
        TODO("Only part two is implemented")
    }

    override fun solvePart2(input: List<String>): Any {
        val horizontalPositions = input.first().split(",").map { it.toInt() }
        val row = horizontalPositions.average().toInt() // floor the average
        return horizontalPositions.sumOf { crab -> fuelCost(abs(crab - row)) }
    }

    private fun fuelCost(n: Int): Int = n * (n + 1) / 2
}
