package common

import common.annotations.AoCPuzzle
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

    fun solveThem(
        solutionDescription: String? = null,
        ignorePart1: Boolean = false,
        ignorePart2: Boolean = false,
    ) {
        val aocAnnotation =
            this.javaClass.annotations.find { it is AoCPuzzle } as? AoCPuzzle ?: error("No @AoCPuzzle annotation given")
        val year = aocAnnotation.year
        val day = aocAnnotation.day

        val repo = InputRepo()
        val sampleInput = repo.getSample(year, day)
        val input = repo.get(year, day)

        solutionDescription?.let(Output.Solving::printDescription)
        if (!ignorePart1) {
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
        } else {
            Output.Solving.printIgnorePart(Part.Part1)
        }

        if (!ignorePart2) {
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
        } else {
            Output.Solving.printIgnorePart(Part.Part2)
        }
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
        Output.Solving.printHeader(year, day, part)
        try {
            val sampleResult = solvingFun(sampleInput)
            when {
                checkResult(sampleResult, sampleAnswer) -> Output.Solving.printCorrectAgainstSampleData()
                else -> Output.Solving.printIncorrectAgainstSampleData(
                    part,
                    sampleInput,
                    sampleAnswer,
                    sampleResult
                )
                    .also { return }
            }

            Output.Solving.printStartSolving()
            val solution = measureTimedValue { solvingFun(input) }
            Output.Solving.printResults(solution)

            when (answer) {
                null -> {
                    Output.Solving.printSubmitAnswer()
                    if (readLine() != "n") {
                        AnswerSubmitter().submitAnswer(year, day, part, solution.value.toString())
                    }
                }
                else -> {
                    Output.Solving.printVerification(
                        isCorrect = checkResult(solution.value, answer),
                        answer
                    )
                }
            }
        } catch (error: NotImplementedError) {
            Output.Solving.printSkipped(error)
        }
    }

    private fun checkResult(solution: Any, expected: Any): Boolean {
        return solution.toString() == expected.toString()
    }
}