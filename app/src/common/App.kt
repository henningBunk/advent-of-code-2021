@file:JvmName("App")

package common

import kotlin.system.exitProcess

fun main(args: Array<String>) {

    val day: Int = try { System.getProperty("day").toInt() } catch (_: Exception) {
        println("Please specify which day should be executed by adding '-Dday=1' to your run command.")
        exitProcess(1)
    }

    val sessionCookie: String = try { System.getProperty("sessionCookie") } catch (_: Exception) {
        println("Please set the session cookie in the gradle.properties file or by adding '-DsessionCookie=yourCookie' to your run command.")
        exitProcess(1)
    }

    // TODO use reflection for this
    when (day) {
        1 -> y2021.day01.main(arrayOf(sessionCookie))
        2 -> y2021.day02.main(arrayOf(sessionCookie))
        3 -> y2021.day03.main(arrayOf(sessionCookie))
        4 -> y2021.day04.main(arrayOf(sessionCookie))
        5 -> y2021.day05.main(arrayOf(sessionCookie))
        6 -> y2021.day06.main(arrayOf(sessionCookie))
        7 -> y2021.day07.main(arrayOf(sessionCookie))
        8 -> y2021.day08.main(arrayOf(sessionCookie))
        9 -> y2021.day09.main(arrayOf(sessionCookie))
        10 -> y2021.day10.main(arrayOf(sessionCookie))
        11 -> y2021.day11.main(arrayOf(sessionCookie))
        else -> println("Invalid day.")
    }
}
