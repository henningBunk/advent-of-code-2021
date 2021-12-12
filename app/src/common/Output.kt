package common

import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue

object Output {

    object InputRepo {

        fun printFromCache(year: Int, day: Int) =
            println("Loading input for $year day $day from cache.")

        fun printDownload(year: Int, day: Int) =
            println("Downloading input for $year day $day.")

        fun printSavedToCache() =
            println("Input saved to cache.")
    }

    object Solving {

        fun printDescription(text: String) =
            println("\n📋 $text")

        fun printHeader(year: Int, day: Int, part: Part) =
            printFramed("$year DAY $day PART ${part.value}")

        private fun printFramed(text: String) {
            println(
                """
                    
                    ${"".padStart(text.length + 18, '#')}
                    #####    $text    #####
                    ${"".padStart(text.length + 18, '#')}
                    
                    """.trimIndent()
            )
        }

        fun printIgnorePart(part: Part) =
            println("\nIgnoring part ${part.value}.")

        fun printCorrectAgainstSampleData() =
            println("✅ Solution successfully verified against the sample input\n")

        fun printIncorrectAgainstSampleData(
            part: Part,
            sampleInput: List<String>,
            sampleAnswer: Any,
            sampleResult: Any
        ) = println(
            """|
                   |❌ Oh no! Your solution for part ${part.value} did not return the expected answer for the sample input.
                   |
                   |Your sample input:
                   |$sampleInput
                   |
                   |Expected result: $sampleAnswer
                   |Actual result:   $sampleResult   
                """.trimMargin()
        )

        fun printStartSolving() = println("Start solving...\n")

        @OptIn(ExperimentalTime::class)
        fun printResults(solution: TimedValue<Any>) = println(
            """
                    👉 Your solution is: ${solution.value}
                    ⏱ Calculating it took ${solution.duration.toString(DurationUnit.MILLISECONDS, 2)}
                """.trimIndent()
        )

        fun printSubmitAnswer() = println(
            """⭐ Go get your star!)
            ✉️ Do you want to submit your answer to adventofcode.com? [Y/n]:""".trimIndent()
        )

        fun printVerification(isCorrect: Boolean, answer: Any?) = println(
            when {
                isCorrect -> "✅ Your solution is correct!"
                else -> "❌ But sadly this is wrong. The real answer would be $answer"
            }
        )

        fun printSkipped(error: Throwable) =
            println("Skipped this part: ${error.message} 🚧")
    }
}