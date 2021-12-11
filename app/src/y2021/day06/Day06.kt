package y2021.day06

import common.Answers
import common.AocSolution
import common.annotations.AoCPuzzle

fun main(args: Array<String>) {
    Day06().solveThem()
}

@AoCPuzzle(year = 2021, day = 6)
class Day06 : AocSolution {
    override val answers = Answers(samplePart1 = 5934, samplePart2 = 26984457539, part1 = 355386, part2 = 1613415325809)

    override fun solvePart1(input: List<String>): Any =
        Population(input.first())
            .apply { ageBy(days = 80) }
            .size

    override fun solvePart2(input: List<String>): Any =
        Population(input.first())
            .apply { ageBy(days = 256) }
            .size
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
