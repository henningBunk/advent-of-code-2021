package day04

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day04Test : FreeSpec({

    val sampleInput: List<String> = listOf(
        "7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1",
        "",
        "22 13 17 11  0",
        "8  2 23  4 24",
        "21  9 14 16  7",
        "6 10  3 18  5",
        "1 12 20 15 19",
        "",
        "3 15  0  2 22",
        "9 18 13 17  5",
        "19  8  7 25 23",
        "20 11 10 24  4",
        "14 21 16 12  6",
        "",
        "14 21 17 24  4",
        "10 16 15  9 19",
        "18  8 23 26 20",
        "22 11 13  6  5",
        "2  0 12  3  7",
    )

    val sampleSolutionPart1: Int = 4512

    val sampleSolutionPart2: Int = 1924

    "Solving day 4" - {
        "part 1 for the sample input should return the correct output" {
            solveDay04Part1(sampleInput) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay04Part2(sampleInput) shouldBe sampleSolutionPart2
        }
    }

    "A Number" - {
        "can be initialized with a String" {
            Number("15")
        }

        "is not checked after initialization" {
            Number("15").isChecked shouldBe false
        }

        "returns the initialized value as Integer" {
            Number("15").value shouldBe 15
        }

        "is checked after when 'check()' was called" {
            val number = Number("15")
            number.check()
            number.isChecked shouldBe true
        }
    }

    "A board" - {

        val sampleBoard: List<String> = listOf(
            "11 12 13 14 15",
            "21 22 23 24 25",
            "31 32 33 34 35",
            "41 42 43 44 45",
            "51 52 53 54 55",
        )

        "can be initialized with a list of Strings with multiple spaces" {
            Board(sampleBoard)
        }

        "can be marked with a number which is not on the board" {
            val board = Board(sampleBoard)
            board.check(1)
            board.check(-1)
            board.check(100)
        }

        "can be marked with a number which is on the board" {
            val board = Board(sampleBoard)
            board.check(11)
            board.check(55)
            board.check(15)
            board.check(51)
        }

        "has not won when it is marked with four numbers in a row" {
            val board = Board(sampleBoard).apply {
                check(11)
                check(12)
                check(13)
                check(14)
                check(22)
                check(23)
                check(24)
                check(25)
                check(31)
                check(32)
                check(34)
                check(35)
                check(51)
                check(53)
                check(54)
                check(55)
            }
            board.hasWon() shouldBe false
        }

        "has not won when it is marked with four numbers in a column" {
            val board = Board(sampleBoard).apply {
                check(11)
                check(21)
                check(31)
                check(41)
                check(22)
                check(32)
                check(42)
                check(52)
                check(13)
                check(23)
                check(43)
                check(53)
                check(15)
                check(35)
                check(45)
                check(55)
            }
            board.hasWon() shouldBe false
        }

        "has won when it is marked with five numbers in a row" {
            val boardA = Board(sampleBoard)
            boardA.apply {
                check(11)
                check(12)
                check(13)
                check(14)
                check(15)
            }
            val boardB = Board(sampleBoard)
            boardB.apply {
                check(31)
                check(32)
                check(33)
                check(34)
                check(35)
            }
            val boardC = Board(sampleBoard)
            boardC.apply {
                check(51)
                check(52)
                check(53)
                check(54)
                check(55)
            }
            boardA.hasWon() shouldBe true
            boardB.hasWon() shouldBe true
            boardC.hasWon() shouldBe true
        }

        "has won when it is marked with five numbers in a column" {
            val boardA = Board(sampleBoard)
            boardA.apply {
                check(11)
                check(21)
                check(31)
                check(41)
                check(51)
            }
            val boardB = Board(sampleBoard)
            boardB.apply {
                check(13)
                check(23)
                check(33)
                check(43)
                check(53)
            }
            val boardC = Board(sampleBoard)
            boardC.apply {
                check(15)
                check(25)
                check(35)
                check(45)
                check(55)
            }
            boardA.hasWon() shouldBe true
            boardB.hasWon() shouldBe true
            boardC.hasWon() shouldBe true
        }

        "where diagonals don't count" - {

            "has not won when only four numbers of a diagonal are checked" {
                val board = Board(sampleBoard, doDiagonalsCount = false).apply {
                    check(11)
                    check(22)
                    check(44)
                    check(55)
                    check(15)
                    check(24)
                    check(42)
                    check(51)
                }
                board.hasWon() shouldBe false
            }

            "has not won when all five numbers of a diagonal are checked" {
                val boardA = Board(sampleBoard, doDiagonalsCount = false).apply {
                    check(11)
                    check(22)
                    check(33)
                    check(44)
                    check(55)
                }
                boardA.hasWon() shouldBe false
                val boardB = Board(sampleBoard, doDiagonalsCount = false).apply {
                    check(15)
                    check(24)
                    check(33)
                    check(42)
                    check(51)
                }
                boardB.hasWon() shouldBe false
            }
        }

        "where diagonals do count" - {

            "has not won when only four numbers of a diagonal are checked" {
                val board = Board(sampleBoard, doDiagonalsCount = true).apply {
                    check(11)
                    check(22)
                    check(44)
                    check(55)
                    check(15)
                    check(24)
                    check(42)
                    check(51)
                }
                board.hasWon() shouldBe false
            }

            "has won when all five numbers of a diagonal are checked" {
                val boardA = Board(sampleBoard, doDiagonalsCount = true).apply {
                    check(11)
                    check(22)
                    check(33)
                    check(44)
                    check(55)
                }
                boardA.hasWon() shouldBe true
                val boardB = Board(sampleBoard, doDiagonalsCount = true).apply {
                    check(15)
                    check(24)
                    check(33)
                    check(42)
                    check(51)
                }
                boardB.hasWon() shouldBe true
            }
        }
    }

    "The board from the example" - {
        val winningExampleBoard = listOf(
            "14 21 17 24  4",
            "10 16 15  9 19",
            "18  8 23 26 20",
            "22 11 13  6  5",
            "2  0 12  3  7",
        )
        val drawnNumbers = listOf(7, 4, 9, 5, 11, 17, 23, 2, 0, 14, 21, 24)
        val additionalNumbersOnBoard = listOf(10, 16, 15, 19, 18, 8, 26, 3)

        "wins when the 24 is drawn" {
            val board = Board(winningExampleBoard)

            drawnNumbers.dropLast(1).forEach(board::check)
            board.hasWon() shouldBe false

            board.check(drawnNumbers.last())
            board.hasWon() shouldBe true
        }

        "has the final score of 4512" - {
            "directly after the board won" {
                val board = Board(winningExampleBoard)
                drawnNumbers.forEach(board::check)
                board.getScore() shouldBe sampleSolutionPart1
            }

            "and even after additional numbers were checked" {
                val board = Board(winningExampleBoard)
                drawnNumbers.forEach(board::check)
                additionalNumbersOnBoard.forEach(board::check)
                board.getScore() shouldBe sampleSolutionPart1
            }
        }
    }
})
