package day05

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day05Test : FreeSpec({

    val sampleInput: List<String> = listOf(
        "0,9 -> 5,9",
        "8,0 -> 0,8",
        "9,4 -> 3,4",
        "2,2 -> 2,1",
        "7,0 -> 7,4",
        "6,4 -> 2,0",
        "0,9 -> 2,9",
        "3,4 -> 1,4",
        "0,0 -> 8,8",
        "5,5 -> 8,2",
    )

    val sampleSolutionPart1: Int = 5

    val sampleSolutionPart2: Int = 12

    "Solving day 5" - {
        "part 1 for the sample input should return the correct output" {
            solveDay05Part1(sampleInput) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay05Part2(sampleInput) shouldBe sampleSolutionPart2
        }
    }

    "Parsing the input should" - {
        "return the list of Strings as a List of Pairs of Points" {
            val sampleInputAsPoints = listOf(
                Pair(Point(0, 9), Point(5, 9)),
                Pair(Point(8, 0), Point(0, 8)),
                Pair(Point(9, 4), Point(3, 4)),
                Pair(Point(2, 2), Point(2, 1)),
                Pair(Point(7, 0), Point(7, 4)),
                Pair(Point(6, 4), Point(2, 0)),
                Pair(Point(0, 9), Point(2, 9)),
                Pair(Point(3, 4), Point(1, 4)),
                Pair(Point(0, 0), Point(8, 8)),
                Pair(Point(5, 5), Point(8, 2)),
            )
            sampleInput.parse() shouldBe sampleInputAsPoints
        }

        "work with numbers with more than one digit" {
            val longNumbers: List<String> = listOf("111,222 -> 333,9666666")
            val longNumbersParsed = listOf(Pair(Point(111, 222), Point(333, 9666666)))

            longNumbers.parse() shouldBe longNumbersParsed
        }
    }

    "Filter diagonals should return a list with only straight or vertical lines" {
        val sampleInputAsPoints = listOf(
            Pair(Point(0, 9), Point(5, 9)),
            Pair(Point(8, 0), Point(0, 8)),
            Pair(Point(9, 4), Point(3, 4)),
            Pair(Point(2, 2), Point(2, 1)),
            Pair(Point(7, 0), Point(7, 4)),
            Pair(Point(6, 4), Point(2, 0)),
            Pair(Point(0, 9), Point(2, 9)),
            Pair(Point(3, 4), Point(1, 4)),
            Pair(Point(0, 0), Point(8, 8)),
            Pair(Point(5, 5), Point(8, 2)),
        )

        val sampleInputWithoutDiagonals = listOf(
            Pair(Point(0, 9), Point(5, 9)),
            Pair(Point(9, 4), Point(3, 4)),
            Pair(Point(2, 2), Point(2, 1)),
            Pair(Point(7, 0), Point(7, 4)),
            Pair(Point(0, 9), Point(2, 9)),
            Pair(Point(3, 4), Point(1, 4)),
        )
        sampleInputAsPoints.filterDiagonals() shouldBe sampleInputWithoutDiagonals
    }

    "determine map size returns the correct size" {
        val sampleInputAsPoints = listOf(
            Pair(Point(0, 9), Point(5, 9)),
            Pair(Point(8, 0), Point(0, 8)),
            Pair(Point(9, 4), Point(3, 4)),
            Pair(Point(2, 2), Point(2, 1)),
            Pair(Point(7, 0), Point(7, 4)),
            Pair(Point(6, 4), Point(2, 0)),
            Pair(Point(0, 9), Point(2, 9)),
            Pair(Point(3, 4), Point(1, 4)),
            Pair(Point(0, 0), Point(8, 8)),
            Pair(Point(5, 5), Point(8, 2)),
        )
        determineMapSize(sampleInputAsPoints) shouldBe Point(10, 10)
    }

    "drawLine" - {
        "draws a horizontal " - {
            "left to right line correctly on an empty map " {

                val map = emptyMap(Point(3, 3))
                map.all { row -> row.all { field -> field == 0 } } shouldBe true

                map.drawLine(Pair(Point(0, 0), Point(2, 0)))
                map.get(0).all { field -> field == 1 } shouldBe true
                map.subList(1, 2).all { row -> row.all { field -> field == 0 } } shouldBe true
            }

            "right to left line correctly on an empty map " {
                val map = emptyMap(Point(3, 3))
                map.all { row -> row.all { field -> field == 0 } } shouldBe true

                map.drawLine(Pair(Point(2, 0), Point(0, 0)))
                map.get(0).all { field -> field == 1 } shouldBe true
                map.subList(1, 2).all { row -> row.all { field -> field == 0 } } shouldBe true
            }


            "line correctly on a map with another line" {
                val map = emptyMap(Point(3, 3))
                map.all { row -> row.all { field -> field == 0 } } shouldBe true

                map.drawLine(Pair(Point(0, 0), Point(2, 0)))
                map.get(0).all { field -> field == 1 } shouldBe true
                map.subList(1, 2).all { row -> row.all { field -> field == 0 } } shouldBe true

                map.drawLine(Pair(Point(0, 1), Point(2, 1)))
                map.get(0).all { field -> field == 1 } shouldBe true
                map.get(1).all { field -> field == 1 } shouldBe true
                map.get(2).all { field -> field == 0 } shouldBe true
            }


            "line correctly on top of another line" {
                val map = emptyMap(Point(3, 3))
                map.all { row -> row.all { field -> field == 0 } } shouldBe true

                map.drawLine(Pair(Point(0, 0), Point(2, 0)))
                map.drawLine(Pair(Point(0, 0), Point(2, 0)))
                map.get(0).all { field -> field == 2 } shouldBe true
                map.subList(1, 2).all { row -> row.all { field -> field == 0 } } shouldBe true
            }
        }

        "draws a vertical " - {
            "top to bottom line correctly on an empty map" {
                val map = emptyMap(Point(3, 3))
                map.all { row -> row.all { field -> field == 0 } } shouldBe true

                map.drawLine(Pair(Point(0, 0), Point(0, 2)))
                map.all { row -> row[0] == 1 && row[1] == 0 && row[2] == 0 } shouldBe true
            }

            "bottom to top line correctly on an empty map" {
                val map = emptyMap(Point(3, 3))
                map.all { row -> row.all { field -> field == 0 } } shouldBe true

                map.drawLine(Pair(Point(0, 2), Point(0, 0)))
                map.all { row -> row[0] == 1 && row[1] == 0 && row[2] == 0 } shouldBe true
            }

            "line correctly on a map with another line" {
                val map = emptyMap(Point(3, 3))
                map.all { row -> row.all { field -> field == 0 } } shouldBe true

                map.drawLine(Pair(Point(0, 0), Point(0, 2)))
                map.all { row -> row[0] == 1 && row[1] == 0 && row[2] == 0 } shouldBe true

                map.drawLine(Pair(Point(1, 0), Point(1, 2)))
                map.all { row -> row[0] == 1 && row[1] == 1 && row[2] == 0 } shouldBe true
            }

            "line correctly on top of another line" {
                val map = emptyMap(Point(3, 3))

                map.drawLine(Pair(Point(0, 0), Point(0, 2)))
                map.drawLine(Pair(Point(0, 0), Point(0, 2)))
                map.all { row -> row[0] == 2 && row[1] == 0 && row[2] == 0 } shouldBe true
            }
        }

        "draws a diagonal " - {
            "top left to bottom right line correctly on an empty map" {
                val map = emptyMap(Point(3, 3))
                map.all { row -> row.all { field -> field == 0 } } shouldBe true

                map.drawLine(Pair(Point(0, 0), Point(2, 2)))
                map.get(0).get(0) shouldBe 1
                map.get(1).get(1) shouldBe 1
                map.get(2).get(2) shouldBe 1

                map.get(0).get(1) shouldBe 0
                map.get(0).get(2) shouldBe 0
                map.get(1).get(0) shouldBe 0
                map.get(1).get(2) shouldBe 0
                map.get(2).get(0) shouldBe 0
                map.get(2).get(1) shouldBe 0
            }

            "top right to bottom left line correctly on an empty map" {
                val map = emptyMap(Point(3, 3))
                map.all { row -> row.all { field -> field == 0 } } shouldBe true

                map.drawLine(Pair(Point(2, 0), Point(0, 2)))
                map.get(0).get(2) shouldBe 1
                map.get(1).get(1) shouldBe 1
                map.get(2).get(0) shouldBe 1

                map.get(0).get(0) shouldBe 0
                map.get(0).get(1) shouldBe 0
                map.get(1).get(0) shouldBe 0
                map.get(1).get(2) shouldBe 0
                map.get(2).get(1) shouldBe 0
                map.get(2).get(2) shouldBe 0
            }

            "bottom right to top left line correctly on an empty map" {
                val map = emptyMap(Point(3, 3))
                map.all { row -> row.all { field -> field == 0 } } shouldBe true

                map.drawLine(Pair(Point(2, 2), Point(0, 0)))
                map.get(0).get(0) shouldBe 1
                map.get(1).get(1) shouldBe 1
                map.get(2).get(2) shouldBe 1

                map.get(0).get(1) shouldBe 0
                map.get(0).get(2) shouldBe 0
                map.get(1).get(0) shouldBe 0
                map.get(1).get(2) shouldBe 0
                map.get(2).get(0) shouldBe 0
                map.get(2).get(1) shouldBe 0
            }

            "bottom left to top right line correctly on an empty map" {
                val map = emptyMap(Point(3, 3))
                map.all { row -> row.all { field -> field == 0 } } shouldBe true

                map.drawLine(Pair(Point(0, 2), Point(2, 0)))
                map.get(0).get(2) shouldBe 1
                map.get(1).get(1) shouldBe 1
                map.get(2).get(0) shouldBe 1

                map.get(0).get(0) shouldBe 0
                map.get(0).get(1) shouldBe 0
                map.get(1).get(0) shouldBe 0
                map.get(1).get(2) shouldBe 0
                map.get(2).get(1) shouldBe 0
                map.get(2).get(2) shouldBe 0
            }
        }
    }
})
