package day06

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 6
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay06Part1, ::solveDay06Part2)
}

// Final population of lantern fish: 355386
fun solveDay06Part1(input: List<String>): Int {
    val population = Population(input.first())
    population.ageBy(days = 80)
    println("Final population of lantern fish: ${population.size}")
    return -1
}

// Final population of lantern fish: 1613415325809
fun solveDay06Part2(input: List<String>): Int {
    val population = Population(input.first())
    population.ageBy(days = 256)
    println("Final population of lantern fish: ${population.size}")
    return -1
}

class Population(initial: String) {
    var population: MutableMap<Int, Long> = mutableMapOf()

    val size: Long
        get() = population.toList().sumOf { (_, amount) -> amount }

    init {
        initial.split(',').map { it.toInt() }.forEach {
            population[it] = population.getOrDefault(it, 0L) + 1L
        }
    }

    fun ageBy(days: Int) {
        repeat(days) { ageOneDay() }
    }

    private fun ageOneDay() {
        var populationOnNextDay: MutableMap<Int, Long> = mutableMapOf()
        population.forEach { (daysUntilBirth, amountOfFish) ->
            val nextDaysUntilBirth = when (daysUntilBirth) {
                0 -> {
                    populationOnNextDay.birth(amountOfFish)
                    6
                }
                else -> daysUntilBirth - 1
            }
            populationOnNextDay.addFish(daysUntilBirth = nextDaysUntilBirth, amountOfFish)
        }
        population = populationOnNextDay
    }

    private fun MutableMap<Int, Long>.addFish(daysUntilBirth: Int, amountOfFish: Long) {
        this[daysUntilBirth] = getOrDefault(daysUntilBirth, 0L) + amountOfFish
    }

    private fun MutableMap<Int, Long>.birth(amount: Long) {
        this[8] = amount
    }
}
