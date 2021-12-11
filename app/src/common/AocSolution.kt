package common

import common.annotations.AoCPuzzle
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

data class Answers(
    val samplePart1: Any,
    val samplePart2: Any,
    val part1: Any? = null,
    val part2: Any? = null,
)

interface AocSolution {

    val answers: Answers

    fun solveThem() {

        val aocAnnotation =
            this.javaClass.annotations.find { it is AoCPuzzle } as? AoCPuzzle ?: error("No @AoCPuzzle annotation given")
        val year = aocAnnotation.year
        val day = aocAnnotation.day

        val repo = InputRepo()
        val sampleInput = repo.getSample(year, day)
        val input = repo.get(year, day)

        staggeredSolving(
            year = year,
            day = day,
            sampleInput = sampleInput,
            sampleAnswer = answers.samplePart1,
            answer = answers.part1,
            input = input,
            part = Part.Part1,
            solvingFun = ::solvePart1,
        )

        staggeredSolving(
            year = year,
            day = day,
            sampleInput = sampleInput,
            sampleAnswer = answers.samplePart2,
            answer = answers.part2,
            input = input,
            part = Part.Part2,
            solvingFun = ::solvePart2,
        )
    }

    fun solvePart1(input: List<String>): Any

    fun solvePart2(input: List<String>): Any

    @OptIn(ExperimentalTime::class)
    private fun staggeredSolving(
        year: Int,
        day: Int,
        sampleInput: List<String>,
        sampleAnswer: Any,
        answer: Any?,
        input: List<String>,
        part: Part,
        solvingFun: (List<String>) -> Any,
    ) {
        try {
            val sampleResult = solvingFun(sampleInput)
            if (sampleResult.toString() == sampleAnswer.toString()) {
                println("\n✅ Solution for part $part successfully verified against the sample data.")
            } else {
                println(
                    """|
                       |❌ Oh no! Your solution for part 1 did not return the expected answer for the sample input.
                       |
                       |Your sample input:
                       |###########################
                       |$sampleInput
                       |###########################
                       |
                       |Expected result: $sampleAnswer
                       |Actual result:   $sampleResult   
                """.trimMargin()
                )
                return
            }

            println("\nStart solving $year day $day part $part.")
            val solution = measureTimedValue { solvingFun(input) }
            println(
                "\n⏱ Solving $year day $day part $part took ${
                    solution.duration.toString(
                        DurationUnit.MILLISECONDS,
                        2
                    )
                }."
            )
            println("Your solution for $year day $day part $part is: ${solution.value}")
            if (answer == null) {
                println("⭐ Go get your star!")
                println("✉️ Do you want to submit your answer to adventofcode.com? [Y/n]:")

                if (readLine() != "n") {
                    AnswerSubmitter().submitAnswer(year, day, part, solution.value.toString())
                }

            } else if (solution.value.toString() == answer.toString()) {
                println("✅ That is correct!")
            } else {
                println("❌ But sadly this is wrong. The real answer would be $answer")
            }

        } catch (error: NotImplementedError) {
            println("\nSkipped $year day $day part $part. ${error.message} 🚧")
        }
    }
}