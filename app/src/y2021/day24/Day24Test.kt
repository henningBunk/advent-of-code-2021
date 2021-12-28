package y2021.day24

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import y2021.day24.Variable.*

class Day24Test : FreeSpec({

    "An ALU" - {

        "should be able to take the inverse" {
            val alu = ALU()
            alu.inp(X, 5)
            alu.mul(X, -1)

            alu.x shouldBe -5
        }

        "should be able to find a number which is three times larger than another" {
            val alu = ALU()
            alu.inp(Z, 4)
            alu.inp(X, 12)
            alu.mul(Z, 3)
            alu.eql(Z, X)
            alu.z shouldBe 1
        }

        "should be able to find a number which is not three times larger than another" {
            val alu = ALU()
            alu.inp(Z, 4)
            alu.inp(X, 8)
            alu.mul(Z, 3)
            alu.eql(Z, X)
            alu.z shouldBe 0
        }

        "should be able to store a binary numbers" - {
            fun binary(value: Int): ALU {
                val alu = ALU()
                alu.inp(W, value)
                alu.add(Z, W)
                alu.mod(Z, 2)
                alu.div(W, 2)
                alu.add(Y, W)
                alu.mod(Y, 2)
                alu.div(W, 2)
                alu.add(X, W)
                alu.mod(X, 2)
                alu.div(W, 2)
                alu.mod(W, 2)
                return alu
            }

            "15" {
                with(binary(15)) {
                    w shouldBe 1
                    x shouldBe 1
                    y shouldBe 1
                    z shouldBe 1
                }
            }

            "0" {
                with(binary(0)) {
                    w shouldBe 0
                    x shouldBe 0
                    y shouldBe 0
                    z shouldBe 0
                }
            }

            "1" {
                with(binary(1)) {
                    w shouldBe 0
                    x shouldBe 0
                    y shouldBe 0
                    z shouldBe 1
                }
            }

            "2" {
                with(binary(2)) {
                    w shouldBe 0
                    x shouldBe 0
                    y shouldBe 1
                    z shouldBe 0
                }
            }

            "4" {
                with(binary(4)) {
                    w shouldBe 0
                    x shouldBe 1
                    y shouldBe 0
                    z shouldBe 0
                }
            }

            "8" {
                with(binary(8)) {
                    w shouldBe 1
                    x shouldBe 0
                    y shouldBe 0
                    z shouldBe 0
                }
            }
        }
    }
})