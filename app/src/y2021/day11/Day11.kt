package y2021.day11

import common.Answers
import common.AocSolution
import common.annotations.AoCPuzzle
import kotlin.properties.Delegates

fun main(args: Array<String>) {
    Day11().solveThem()
}

@AoCPuzzle(2021, 11)
class Day11 : AocSolution {
    override val answers = Answers(samplePart1 = 1656, samplePart2 = -1)

    override fun solvePart1(input: List<String>): Any {
        val herdOfDumboOctopuses = mutableMapOf<Pair<Int, Int>, Octopus>()

        // Create a map of octopus
        for (y in (0..input.lastIndex)) {
            for (x in (0..input[y].lastIndex)) {
                herdOfDumboOctopuses[x to y] = Octopus(
                    initialEnergyLevel = Character.getNumericValue(input[y][x])
                )
            }
        }

        // Associate the neighbour situation
        for (y in (0..input.lastIndex)) {
            for (x in (0..input[y].lastIndex)) {
                val position = x to y
                for (direction in directions) {
                    herdOfDumboOctopuses[position]
                        ?.addNeighbour(herdOfDumboOctopuses[position + direction])
                }
            }
        }

        var sumOfFlashes = 0
        repeat(100) {
            println("Step: $it")
            // Let each octopus know that there is a step
            herdOfDumboOctopuses.values.forEach { it.increaseEnergy() }
            // Check and reset each octopus
            sumOfFlashes += herdOfDumboOctopuses.values.count { it.hasFlashedLastStep() }
            println("Sum of flashes: $sumOfFlashes")
            println("Map: $herdOfDumboOctopuses")
        }

        return sumOfFlashes
    }

    override fun solvePart2(input: List<String>): Any {
        TODO()
    }
}

class Octopus(
    initialEnergyLevel: Int
) {
    private var energyLevel: Int by Delegates.observable(initialEnergyLevel) { _, _, new ->
        if (new == 9) neighbours.forEach { it.increaseEnergy() }
    }

    private val neighbours: MutableList<Octopus> = mutableListOf()

    fun addNeighbour(octopus: Octopus?) {
        octopus?.let(neighbours::add)
    }

    fun increaseEnergy() {
        energyLevel++
    }

    fun hasFlashedLastStep(): Boolean = when (energyLevel >= 9) {
        true -> true.also { energyLevel = 0 }
        else -> false
    }

    override fun toString(): String = "$energyLevel"
}

val directions = listOf(
    Pair(0, -1),
    Pair(1, -1),
    Pair(1, 0),
    Pair(1, 1),
    Pair(0, 1),
    Pair(-1, 1),
    Pair(-1, 0),
    Pair(-1, -1),
)

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> =
    first + other.first to second + other.second
