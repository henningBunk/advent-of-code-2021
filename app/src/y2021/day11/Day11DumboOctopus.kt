package y2021.day11

import common.Answers
import common.AocSolution
import common.annotations.AoCPuzzle
import kotlin.properties.Delegates

fun main(args: Array<String>) {
    Day11DumboOctopus().solveThem()
}

@AoCPuzzle(2021, 11)
class Day11DumboOctopus : AocSolution {
    override val answers = Answers(samplePart1 = 1656, samplePart2 = 195, part1 = 1571, part2 = 387)

    override fun solvePart1(input: List<String>): Any {
        val herdOfDumboOctopuses = createOctopuses(input)

        var sumOfFlashes = 0
        repeat(100) {
            // Let each octopus know that there is a step
            herdOfDumboOctopuses.forEach { it.increaseEnergy() }
            // Check and reset each octopus
            sumOfFlashes += herdOfDumboOctopuses.count { it.hasFlashedLastStep() }
        }
        return sumOfFlashes
    }

    override fun solvePart2(input: List<String>): Any {
        val herdOfDumboOctopuses = createOctopuses(input)

        var step = 0
        while (true) {
            step++
            herdOfDumboOctopuses.forEach { it.increaseEnergy() }
            if(herdOfDumboOctopuses.count { it.hasFlashedLastStep() } == herdOfDumboOctopuses.size) {
                break
            }
        }
        return step
    }

    private fun createOctopuses(input: List<String>): List<Octopus> {
        val herdOfDumboOctopuses = mutableMapOf<Pair<Int, Int>, Octopus>()
        // Create a map of octopus
        forAll(input.first().lastIndex, input.lastIndex) { x, y ->
            herdOfDumboOctopuses[x to y] = Octopus(initialEnergyLevel = Character.getNumericValue(input[y][x]))
        }

        // Associate the neighbour situation
        forAll(input.first().lastIndex, input.lastIndex) { x, y ->
            val position = x to y
            for (direction in directions) {
                herdOfDumboOctopuses[position]?.addNeighbour(herdOfDumboOctopuses[position + direction])
            }
        }
        return herdOfDumboOctopuses.values.toList()
    }

    private fun forAll(lastX: Int, lastY: Int, action: (x: Int, y: Int)->Unit) {
        for (y in (0..lastX)) {
            for (x in (0..lastY)) {
                action(x, y)
            }
        }
    }
}

class Octopus(
    initialEnergyLevel: Int
) {
    private var energyLevel: Int by Delegates.observable(initialEnergyLevel) { _, _, new ->
        if (new == 10) neighbours.forEach { it.increaseEnergy() }
    }

    private val neighbours: MutableList<Octopus> = mutableListOf()

    fun addNeighbour(octopus: Octopus?) {
        octopus?.let(neighbours::add)
    }

    fun increaseEnergy() {
        energyLevel++
    }

    fun hasFlashedLastStep(): Boolean = when (energyLevel > 9) {
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
