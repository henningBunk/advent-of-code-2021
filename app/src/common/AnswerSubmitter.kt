package common

import Config
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result

class AnswerSubmitter {
    private val sessionCookie: String = Config.SESSION_COOKIE

    fun submitAnswer(year: Int, day: Int, part: Part, solution: String) {
        println("Posting '$solution' as an answer to year $year day $day part ${part.value}.")
        val (_, _, result) = getUrl(year, day)
            .httpPost(
                listOf(
                    "level" to part.value,
                    "answer" to solution,
                )
            )
            .appendHeader(Headers.COOKIE to "session=$sessionCookie")
            .responseString()

        when (result) {
            is Result.Success -> when {
                result.value.contains(Response.Correct.regex) ->
                    println("⭐️ Your answer was correct! ⭐️")
                result.value.contains(Response.TooLow.regex) ->
                    println("❌ Your answer was too low.")
                result.value.contains(Response.TooHigh.regex) ->
                    println("❌ Your answer was too high.")
                result.value.contains(Response.InvalidLevel.regex) ->
                    println("❌ You don't seem to be solving the right level. Are you sure you want to upload a solution for year $year day $day part ${part.value}?")
            }
            is Result.Failure -> throw(result.error)
        }
    }

    private fun getUrl(year: Int, day: Int): String = "https://adventofcode.com/$year/day/$day/answer"
}

enum class Response(val regex: Regex) {
    Correct("""That's the right answer!""".toRegex()),
    TooLow("""your answer is too low""".toRegex()),
    TooHigh("""your answer is too high""".toRegex()),
    InvalidLevel("""You don't seem to be solving the right level""".toRegex())
}

enum class Part(val value: String) {
    Part1("1"),
    Part2("2")
}

