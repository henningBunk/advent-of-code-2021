package common

import Config
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import java.io.File
import kotlin.system.exitProcess

class InputRepo {
    private val sessionCookie: String = Config.SESSION_COOKIE

    fun get(year: Int, day: Int): List<String> {
        val file = File("app/src/y$year/day${day.toString().padStart(2, '0')}/input.txt")
        return when {
            file.exists() -> {
                Output.InputRepo.printFromCache(year, day)
                file.read()
            }
            else -> {
                Output.InputRepo.printDownload(year, day)
                download(year, day)
                    .also {
                        file.write(it)
                        Output.InputRepo.printSavedToCache()
                    }
                    .lines()
            }
        }
    }

    fun getSample(year: Int, day: Int): List<String> {
        val path = "app/src/y$year/day${day.toString().padStart(2, '0')}/sample_input.txt"
        val file = File(path)
        return when {
            file.exists() -> {
                println("Loading sample input for $year day $day from cache.")
                file.read()
            }
            else -> {
                error(
                    """Couldn't find the sample input for $year day $day. Please put it in the according sample_input.txt file.
                      |Path: '$path'""".trimIndent()
                )
            }
        }
    }

    private fun File.read(): List<String> = useLines { it.toList() }

    private fun File.write(data: String) {
        parentFile.mkdirs()
        writeText(data)
    }

    private fun download(year: Int, day: Int): String {
        val (_, response, result) = getUrl(year, day)
            .httpGet()
            .appendHeader(Headers.COOKIE to "session=$sessionCookie")
            .responseString()

        when (result) {
            is Result.Success -> return result.get().trim()
            is Result.Failure -> {
                printError(year, day, response, result)
                exitProcess(1)
            }
        }
    }

    private fun printError(year: Int, day: Int, response: Response, result: Result.Failure<FuelError>) {
        println("\nError downloading the input for $year day $day. ${response.statusCode}: ${response.responseMessage}")
        when (response.statusCode) {
            404 -> println("Did you wake up too early?")
            400 -> println("Is your session cookie correctly set up?")
        }
    }

    private fun getUrl(year: Int, day: Int): String = "https://adventofcode.com/$year/day/$day/input"
}