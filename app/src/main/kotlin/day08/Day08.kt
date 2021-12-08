package day08

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 8
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay08Part1, ::solveDay08Part2)
}


fun solveDay08Part1(input: List<String>): Int {
    val results = input.parse().map { it.second }
    val count = results.flatten().count {
        when (it.length.also(::println)) {
            2, 3, 4, 7 -> true
            else -> false
        }
    }
    return count
}

fun solveDay08Part2(input: List<String>): Int {
    return input.parse().map { (signal, result) ->
        deductNumber(signal, result)
    }.sum()
}

fun deductNumber(signal: List<String>, result: List<String>): Int {
    val data = (signal + result).map { it.toCharArray() }
    val possibleCandidates = List(7) { "abcdefg" }.toTypedArray()

    data.forEach { number ->
        when (number.size) {
            // Distinct Numbers, must be 1, 4 or 7
            2 -> possibleCandidates.narrowDownCandidates(number, 0, 1, 3, 4, 6)
            3 -> possibleCandidates.narrowDownCandidates(number, 1, 3, 4, 6)
            4 -> possibleCandidates.narrowDownCandidates(number, 0, 4, 6)
        }
    }
    val sharedByFiveSegmentNumbers = data.filter { it.size == 5 }.let { fiveSegmentNumbers ->
        // Five segment numbers, must be 2, 3, or 5
        val appearsInAll = fiveSegmentNumbers.fold("abcdefg") { rest, next ->
            rest.toSet().intersect(next.toSet()).joinToString("")
        }
        // The shared ones must be the horizontal ones
        possibleCandidates.narrowDownCandidates(appearsInAll.toCharArray(), 1, 2, 4, 5)
        appearsInAll
    }

    val sharedBySixSegmentNumbers = data.filter { it.size == 6 }
        .onEach { it.sort() }
        .distinct()
        .map { it.toSet() }
        .let { sixSegmentNumbers ->
            // Six segment numbers, must be 0, 6, 9
            val appearsInAll = listOf(
                sixSegmentNumbers[1].union(sixSegmentNumbers[2]).minus(sixSegmentNumbers[0]).joinToString(""),
                sixSegmentNumbers[0].union(sixSegmentNumbers[2]).minus(sixSegmentNumbers[1]).joinToString(""),
                sixSegmentNumbers[0].union(sixSegmentNumbers[1]).minus(sixSegmentNumbers[2]).joinToString(""),
            ).joinToString("")
            appearsInAll
        }

    val middleHorizontal = sharedByFiveSegmentNumbers.toSet().intersect(sharedBySixSegmentNumbers.toSet()).first()
    possibleCandidates.narrowDownCandidates(listOf(middleHorizontal).toCharArray().opposite, 3)


    val restOfSixDigitNumbers = sharedBySixSegmentNumbers.filter { it != middleHorizontal }
    if (possibleCandidates[2].contains(restOfSixDigitNumbers[1]) || possibleCandidates[5].contains(restOfSixDigitNumbers[1])) {
        possibleCandidates.removeCandidate(arrayOf(restOfSixDigitNumbers[1]).toCharArray().opposite, 2)
        possibleCandidates.removeCandidate(arrayOf(restOfSixDigitNumbers[1]).toCharArray(), 5)

        possibleCandidates.removeCandidate(arrayOf(restOfSixDigitNumbers[0]).toCharArray().opposite, 4)
        possibleCandidates.removeCandidate(arrayOf(restOfSixDigitNumbers[0]).toCharArray(), 1)
    } else {

        possibleCandidates.removeCandidate(arrayOf(restOfSixDigitNumbers[0]).toCharArray().opposite, 2)
        possibleCandidates.removeCandidate(arrayOf(restOfSixDigitNumbers[0]).toCharArray(), 5)

        possibleCandidates.removeCandidate(arrayOf(restOfSixDigitNumbers[1]).toCharArray().opposite, 4)
        possibleCandidates.removeCandidate(arrayOf(restOfSixDigitNumbers[1]).toCharArray(), 1)



    }
    evaluate(possibleCandidates)

    // Bottom horizontal
    val possibleCandidatesBottomHorizontal = possibleCandidates[6].toSet()
    val possibleCandidatesRest = possibleCandidates.copyOfRange(0, 6).map { it[0] }.toSet()
    val letter = possibleCandidatesBottomHorizontal.subtract(possibleCandidatesRest)


    possibleCandidates.narrowDownCandidates(arrayOf(letter.first()).toCharArray().opposite, 6)
    evaluate(possibleCandidates)


    val wiring: CharArray = possibleCandidates.map { it.first() }.toCharArray()
    return result.map { it.getDigit(wiring) }.joinToString("").toInt()
}

private fun String.getDigit(wiring: CharArray): Int {
    print("${wiring.toList()}, $this, ")
    if (length == 2) return 1.also(::println)
    if (length == 3) return 7.also(::println)
    if (length == 4) return 4.also(::println)
    if (length == 7) return 8.also(::println)
    if (length == 5) {
        //2, 3, 5
        if (contains(wiring[1])) return 5.also(::println)
        if (contains(wiring[4])) return 2.also(::println)
        return 3.also(::println)
    } else {
        //0, 6, 9
        if (!contains(wiring[3])) return 0.also(::println)
        if (contains(wiring[2])) return 9.also(::println)
        return 6.also(::println)
    }
    return -1
}

private val CharArray.opposite
    get() = "abcdefg".filterNot(::contains).toCharArray()


private fun Array<String>.narrowDownCandidates(number: CharArray, vararg segments: Int) {
    removeCandidate(number, *segments.toTypedArray().toIntArray())
    removeCandidate(
        number.opposite,
        *listOf(0, 1, 2, 3, 4, 5, 6).filterNot { segments.contains(it) }.toTypedArray().toIntArray()
    )
    evaluate(this)
}


private fun Array<String>.removeCandidate(number: CharArray, vararg segments: Int) {
    segments.forEach { segment ->
        this[segment] = get(segment).filterNot(number::contains)
    }
}

fun evaluate(possibleCandidates: Array<String>) {
    println("Goal      : [d, e, a, f, g, b, c]")
    println("Candidates: ${possibleCandidates.toList()}")
    println()
}

fun List<String>.parse(): List<Pair<List<String>, List<String>>> {
    return map { line ->
        line.split("|")
            .let { (signal, result) ->
                Pair(
                    signal.trim().split(" "),
                    result.trim().split(" ")
                )
            }
    }
}
