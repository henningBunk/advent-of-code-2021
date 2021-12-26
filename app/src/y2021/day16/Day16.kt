package y2021.day16

import common.*
import common.annotations.AoCPuzzle
import javax.xml.stream.events.Characters

fun main(args: Array<String>) {
    Day16().solveThem()
}

@AoCPuzzle(2021, 16)
class Day16 : AocSolution {
    override val answers = Answers(samplePart1 = 31, samplePart2 = -1)

    override fun solvePart1(input: List<String>): Any {
        TODO()
    }

    override fun solvePart2(input: List<String>): Any {
        TODO()
    }
}

fun String.hexToBinary(): String = this.map {
    it
        .digitToInt(16)
        .toByte()
        .toString(2)
        .padStart(4, '0')
}.joinToString("")

class BitsPacket(private val binary: String) {

    val version: Int = binary
        .substring(0, 3)
        .toInt(2)

    val typeId: Int = binary
        .substring(3, 6)
        .toInt(2)

    val type: PacketType = when (typeId) {
        4 -> parseLiteralValue()
        else -> parseOperatorPacket()
    }

    private fun parseLiteralValue(): Literal = binary
        .substring(startIndex = 6)
        .chunked(5)
        .let { chunks ->
            var literal: String = ""
            var size = 6
            for (chunk in chunks) {
                literal += chunk.substring(1)
                size += 5
                if (chunk[0] == '0') break
            }
            Literal(literal.toInt(2), size)
        }

    private fun parseOperatorPacket(): Operator {
        val lengthTypeId = Character.getNumericValue(binary[6])
        val subPacketLength = binary.substring(7, 22).toInt(2)

        // todo parse the two literal subpackets

        return Operator(lengthTypeId, subPacketLength, 0)
    }
}

sealed class PacketType(val size: Int)
class Literal(val value: Int, size: Int) : PacketType(size)
class Operator(
    val lengthTypeId: Int,
    val subPacketLength: Int,
    size: Int,
    vararg val subPackets: BitsPacket,
) : PacketType(size)

//0 = 0000
//1 = 0001
//2 = 0010
//3 = 0011
//4 = 0100
//5 = 0101
//6 = 0110
//7 = 0111
//8 = 1000
//9 = 1001
//A = 1010
//B = 1011
//C = 1100
//D = 1101
//E = 1110
//F = 1111