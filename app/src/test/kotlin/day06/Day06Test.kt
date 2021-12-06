package day06

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day06Test : FreeSpec({

    val sampleInput: List<String> = listOf("3,4,3,1,2")

    val sampleSolutionPart1: Int = 5934

    val sampleSolutionPart2: Long = 26984457539

    "Solving day 6 with solution for day 2" - {
        "part 1 for the sample input should return the correct output" {
            val population = Population(sampleInput.first())
            population.ageBy(days = 80)
            population.size shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            val population = Population(sampleInput.first())
            population.ageBy(days = 256)
            population.size shouldBe sampleSolutionPart2
        }
    }
})