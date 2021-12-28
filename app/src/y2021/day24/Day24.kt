package y2021.day24

import common.*
import common.annotations.AoCPuzzle
import y2021.day24.Variable.*
import java.io.File
import javax.xml.stream.events.Characters
import kotlin.math.roundToInt
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds

fun main(args: Array<String>) {
    Day24().solveThem(ignoreSamples = true)
}

@AoCPuzzle(2021, 24)
class Day24 : AocSolution {
    override val answers = Answers(samplePart1 = -1, samplePart2 = -1)

//    val lower = 11_111_111_111_111
//    val upper = 99_999_999_999_999

    val lower = 44_444_444_444_444
    val upper = 55360782312113

    // 44444444444444 is too low
    // 55393888877939 is too high
    // 55555555555555 is too high

    @OptIn(ExperimentalTime::class)
    override fun solvePart1(input: List<String>): Any {

        val start = System.currentTimeMillis()

        var percent = 0.0
        File("monad_in_order.csv").appendText("candidate,result\n")
        for (candidate in (lower..upper).reversed()) {
//        for (candidate in (lower..upper).step(10000)) {
            val result = monad(candidate)

//            if(result != -1) {
//                println("$candidate;$result")
//                File("monad_in_order.csv").appendText("$candidate,$result\n")
//            }

            if (result == 0) {
                println("Valid monad found: $candidate")
                return candidate
            }
            val newPercent = ((upper.toDouble() - candidate) / (upper - lower) * 100 * 1000).roundToInt() / 1000.0
            if (newPercent != percent) {
                val passed = Duration.milliseconds(System.currentTimeMillis() - start)
                println("$newPercent % / time passed: $passed / currently at $candidate")
                percent = newPercent
            }
        }
        return -100
    }

    override fun solvePart2(input: List<String>): Any {
        TODO()
    }
}

class ALU() {
    var w: Int = 0
        private set
    var x: Int = 0
        private set
    var y: Int = 0
        private set
    var z: Int = 0
        private set

    // inp a - Read an input value and write it to variable a.
    fun inp(a: Variable, value: Int) {
        when (a) {
            W -> w = value
            X -> x = value
            Y -> y = value
            Z -> z = value
        }
    }

    // add a b - Add the value of a to the value of b, then store the result in variable a.
    fun add(a: Variable, value: Int) {
        when (a) {
            W -> w += value
            X -> x += value
            Y -> y += value
            Z -> z += value
        }
    }

    fun add(a: Variable, b: Variable) {
        when (b) {
            W -> add(a, w)
            X -> add(a, x)
            Y -> add(a, y)
            Z -> add(a, z)
        }
    }

    // mul a b - Multiply the value of a by the value of b, then store the result in variable a.
    fun mul(a: Variable, value: Int) {
        when (a) {
            W -> w *= value
            X -> x *= value
            Y -> y *= value
            Z -> z *= value
        }
    }

    fun mul(a: Variable, b: Variable) {
        when (b) {
            W -> mul(a, w)
            X -> mul(a, x)
            Y -> mul(a, y)
            Z -> mul(a, z)
        }
    }

    // div a b - Divide the value of a by the value of b, truncate the result to an integer, then store the result in variable a. (Here, "truncate" means to round the value toward zero.)
    fun div(a: Variable, value: Int) {
        when (a) {
            W -> w /= value
            X -> x /= value
            Y -> y /= value
            Z -> z /= value
        }
    }

    fun div(a: Variable, b: Variable) {
        when (b) {
            W -> div(a, w)
            X -> div(a, x)
            Y -> div(a, y)
            Z -> div(a, z)
        }
    }

    // mod a b - Divide the value of a by the value of b, then store the remainder in variable a. (This is also called the modulo operation.)
    fun mod(a: Variable, value: Int) {
        when (a) {
            W -> w %= value
            X -> x %= value
            Y -> y %= value
            Z -> z %= value
        }
    }

    fun mod(a: Variable, b: Variable) {
        when (b) {
            W -> mod(a, w)
            X -> mod(a, x)
            Y -> mod(a, y)
            Z -> mod(a, z)
        }
    }

    // eql a b - If the value of a and b are equal, then store the value 1 in variable a. Otherwise, store the value 0 in variable a.
    fun eql(a: Variable, value: Int) {
        when (a) {
            W -> w = if (w == value) 1 else 0
            X -> x = if (x == value) 1 else 0
            Y -> y = if (y == value) 1 else 0
            Z -> z = if (z == value) 1 else 0
        }
    }

    fun eql(a: Variable, b: Variable) {
        when (b) {
            W -> eql(a, w)
            X -> eql(a, x)
            Y -> eql(a, y)
            Z -> eql(a, z)
        }
    }
}

enum class Variable {
    X, Y, Z, W
}

fun monad(input: Long): Int {
    val alu = ALU()
    val digits = input.toString().map(Character::getNumericValue).toMutableList()
    if (digits.contains(0)) return -1

    alu.inp(W, digits.removeFirst())
    alu.mul(X, 0)
    alu.add(X, Z)
    alu.mod(X, 26)
    alu.div(Z, 1)
    alu.add(X, 10)
    alu.eql(X, W)
    alu.eql(X, 0)
    alu.mul(Y, 0)
    alu.add(Y, 25)
    alu.mul(Y, X)
    alu.add(Y, 1)
    alu.mul(Z, Y)
    alu.mul(Y, 0)
    alu.add(Y, W)
    alu.add(Y, 15)
    alu.mul(Y, X)
    alu.add(Z, Y)
    alu.inp(W, digits.removeFirst())
    alu.mul(X, 0)
    alu.add(X, Z)
    alu.mod(X, 26)
    alu.div(Z, 1)
    alu.add(X, 12)
    alu.eql(X, W)
    alu.eql(X, 0)
    alu.mul(Y, 0)
    alu.add(Y, 25)
    alu.mul(Y, X)
    alu.add(Y, 1)
    alu.mul(Z, Y)
    alu.mul(Y, 0)
    alu.add(Y, W)
    alu.add(Y, 8)
    alu.mul(Y, X)
    alu.add(Z, Y)
    alu.inp(W, digits.removeFirst())
    alu.mul(X, 0)
    alu.add(X, Z)
    alu.mod(X, 26)
    alu.div(Z, 1)
    alu.add(X, 15)
    alu.eql(X, W)
    alu.eql(X, 0)
    alu.mul(Y, 0)
    alu.add(Y, 25)
    alu.mul(Y, X)
    alu.add(Y, 1)
    alu.mul(Z, Y)
    alu.mul(Y, 0)
    alu.add(Y, W)
    alu.add(Y, 2)
    alu.mul(Y, X)
    alu.add(Z, Y)
    alu.inp(W, digits.removeFirst())
    alu.mul(X, 0)
    alu.add(X, Z)
    alu.mod(X, 26)
    alu.div(Z, 26)
    alu.add(X, -9)
    alu.eql(X, W)
    alu.eql(X, 0)
    alu.mul(Y, 0)
    alu.add(Y, 25)
    alu.mul(Y, X)
    alu.add(Y, 1)
    alu.mul(Z, Y)
    alu.mul(Y, 0)
    alu.add(Y, W)
    alu.add(Y, 6)
    alu.mul(Y, X)
    alu.add(Z, Y)
    alu.inp(W, digits.removeFirst())
    alu.mul(X, 0)
    alu.add(X, Z)
    alu.mod(X, 26)
    alu.div(Z, 1)
    alu.add(X, 15)
    alu.eql(X, W)
    alu.eql(X, 0)
    alu.mul(Y, 0)
    alu.add(Y, 25)
    alu.mul(Y, X)
    alu.add(Y, 1)
    alu.mul(Z, Y)
    alu.mul(Y, 0)
    alu.add(Y, W)
    alu.add(Y, 13)
    alu.mul(Y, X)
    alu.add(Z, Y)
    alu.inp(W, digits.removeFirst())
    alu.mul(X, 0)
    alu.add(X, Z)
    alu.mod(X, 26)
    alu.div(Z, 1)
    alu.add(X, 10)
    alu.eql(X, W)
    alu.eql(X, 0)
    alu.mul(Y, 0)
    alu.add(Y, 25)
    alu.mul(Y, X)
    alu.add(Y, 1)
    alu.mul(Z, Y)
    alu.mul(Y, 0)
    alu.add(Y, W)
    alu.add(Y, 4)
    alu.mul(Y, X)
    alu.add(Z, Y)
    alu.inp(W, digits.removeFirst())
    alu.mul(X, 0)
    alu.add(X, Z)
    alu.mod(X, 26)
    alu.div(Z, 1)
    alu.add(X, 14)
    alu.eql(X, W)
    alu.eql(X, 0)
    alu.mul(Y, 0)
    alu.add(Y, 25)
    alu.mul(Y, X)
    alu.add(Y, 1)
    alu.mul(Z, Y)
    alu.mul(Y, 0)
    alu.add(Y, W)
    alu.add(Y, 1)
    alu.mul(Y, X)
    alu.add(Z, Y)
    alu.inp(W, digits.removeFirst())
    alu.mul(X, 0)
    alu.add(X, Z)
    alu.mod(X, 26)
    alu.div(Z, 26)
    alu.add(X, -5)
    alu.eql(X, W)
    alu.eql(X, 0)
    alu.mul(Y, 0)
    alu.add(Y, 25)
    alu.mul(Y, X)
    alu.add(Y, 1)
    alu.mul(Z, Y)
    alu.mul(Y, 0)
    alu.add(Y, W)
    alu.add(Y, 9)
    alu.mul(Y, X)
    alu.add(Z, Y)
    alu.inp(W, digits.removeFirst())
    alu.mul(X, 0)
    alu.add(X, Z)
    alu.mod(X, 26)
    alu.div(Z, 1)
    alu.add(X, 14)
    alu.eql(X, W)
    alu.eql(X, 0)
    alu.mul(Y, 0)
    alu.add(Y, 25)
    alu.mul(Y, X)
    alu.add(Y, 1)
    alu.mul(Z, Y)
    alu.mul(Y, 0)
    alu.add(Y, W)
    alu.add(Y, 5)
    alu.mul(Y, X)
    alu.add(Z, Y)
    alu.inp(W, digits.removeFirst())
    alu.mul(X, 0)
    alu.add(X, Z)
    alu.mod(X, 26)
    alu.div(Z, 26)
    alu.add(X, -7)
    alu.eql(X, W)
    alu.eql(X, 0)
    alu.mul(Y, 0)
    alu.add(Y, 25)
    alu.mul(Y, X)
    alu.add(Y, 1)
    alu.mul(Z, Y)
    alu.mul(Y, 0)
    alu.add(Y, W)
    alu.add(Y, 13)
    alu.mul(Y, X)
    alu.add(Z, Y)
    alu.inp(W, digits.removeFirst())
    alu.mul(X, 0)
    alu.add(X, Z)
    alu.mod(X, 26)
    alu.div(Z, 26)
    alu.add(X, -12)
    alu.eql(X, W)
    alu.eql(X, 0)
    alu.mul(Y, 0)
    alu.add(Y, 25)
    alu.mul(Y, X)
    alu.add(Y, 1)
    alu.mul(Z, Y)
    alu.mul(Y, 0)
    alu.add(Y, W)
    alu.add(Y, 9)
    alu.mul(Y, X)
    alu.add(Z, Y)
    alu.inp(W, digits.removeFirst())
    alu.mul(X, 0)
    alu.add(X, Z)
    alu.mod(X, 26)
    alu.div(Z, 26)
    alu.add(X, -10)
    alu.eql(X, W)
    alu.eql(X, 0)
    alu.mul(Y, 0)
    alu.add(Y, 25)
    alu.mul(Y, X)
    alu.add(Y, 1)
    alu.mul(Z, Y)
    alu.mul(Y, 0)
    alu.add(Y, W)
    alu.add(Y, 6)
    alu.mul(Y, X)
    alu.add(Z, Y)
    alu.inp(W, digits.removeFirst())
    alu.mul(X, 0)
    alu.add(X, Z)
    alu.mod(X, 26)
    alu.div(Z, 26)
    alu.add(X, -1)
    alu.eql(X, W)
    alu.eql(X, 0)
    alu.mul(Y, 0)
    alu.add(Y, 25)
    alu.mul(Y, X)
    alu.add(Y, 1)
    alu.mul(Z, Y)
    alu.mul(Y, 0)
    alu.add(Y, W)
    alu.add(Y, 2)
    alu.mul(Y, X)
    alu.add(Z, Y)
    alu.inp(W, digits.removeFirst())
    alu.mul(X, 0)
    alu.add(X, Z)
    alu.mod(X, 26)
    alu.div(Z, 26)
    alu.add(X, -11)
    alu.eql(X, W)
    alu.eql(X, 0)
    alu.mul(Y, 0)
    alu.add(Y, 25)
    alu.mul(Y, X)
    alu.add(Y, 1)
    alu.mul(Z, Y)
    alu.mul(Y, 0)
    alu.add(Y, W)
    alu.add(Y, 2)
    alu.mul(Y, X)
    alu.add(Z, Y)

    return alu.z
}
