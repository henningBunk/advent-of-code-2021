package y2021.day16

import common.*
import common.annotations.AoCPuzzle

fun main(args: Array<String>) {
    Day16PacketDecoder().solveThem()
}

@AoCPuzzle(2021, 16)
class Day16PacketDecoder : AocSolution {
    override val answers = Answers(samplePart1 = 31, samplePart2 = 54, part1 = 889, part2 = 739303923668)

    override fun solvePart1(input: List<String>): Any =
        BitsPacket(input.first().hexToBinary()).versionSum

    override fun solvePart2(input: List<String>): Any =
        BitsPacket(input.first().hexToBinary()).value
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
        .substring(versionLengthStartIndex, versionLengthEndIndex + 1)
        .toInt(2)

    val typeId: Int = binary
        .substring(typeIdLengthStartIndex, typeIdLengthEndIndex + 1)
        .toInt(2)

    val type: PacketType = when (typeId) {
        4 -> parseLiteralValue()
        else -> parseOperatorPacket()
    }

    val versionSum: Int = when (type) {
        is Literal -> version
        is Operator -> version + type.subPackets.sumOf { it.versionSum }
    }

    val value: Long = when (type) {
        is Literal -> type.value
        is Operator -> when(typeId) {
            0 -> type.subPackets.sumOf { it.value }
            1 -> type.subPackets.fold(1) { product, next -> product * next.value}
            2 -> type.subPackets.minOf { it.value }
            3 -> type.subPackets.maxOf { it.value }
            5 -> if (type.subPackets[0].value > type.subPackets[1].value) 1 else 0
            6 -> if (type.subPackets[0].value < type.subPackets[1].value) 1 else 0
            7 -> if (type.subPackets[0].value == type.subPackets[1].value) 1 else 0
            else -> error("this operation is not supported")
        }
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
            Literal(literal.toLong(2), size)
        }

    private fun parseOperatorPacket(): Operator {
        val lengthTypeId = Character.getNumericValue(binary[lengthTypeIdIndex])
        return when (lengthTypeId) {
            0 -> parseLengthType0()
            1 -> parseLengthType1()
            else -> error("unsupported length type id: $lengthTypeId")
        }
    }

    private fun parseLengthType0(): Operator {
        val subPacketLength = binary
            .substring(type0subPacketLengthStartIndex, type0subPacketLengthEndIndex + 1)
            .toInt(2)

        val subPackets: MutableList<BitsPacket> = mutableListOf()
        var nextSubPacketStart = type0firstSubPacketStartIndex
        do {
            BitsPacket(binary.substring(nextSubPacketStart))
                .let {
                    subPackets.add(it)
                    nextSubPacketStart += it.type.size
                }
        } while (type0firstSubPacketStartIndex + subPacketLength - nextSubPacketStart > 6)

        return Operator(
            lengthTypeId = 0,
            size = nextSubPacketStart,
            subPackets = subPackets.toTypedArray()
        )
    }

    private fun parseLengthType1(): Operator {
        val subPacketsAmount = binary
            .substring(type1subPacketAmountStartIndex, type1subPacketAmountEndIndex + 1)
            .toInt(2)

        val subPackets: MutableList<BitsPacket> = mutableListOf()
        var nextSubPacketStart = type1firstSubPacketStartIndex
        repeat(subPacketsAmount) {
            BitsPacket(binary.substring(nextSubPacketStart))
                .let {
                    subPackets.add(it)
                    nextSubPacketStart += it.type.size
                }
        }

        return Operator(
            lengthTypeId = 1,
            size = nextSubPacketStart,
            subPackets = subPackets.toTypedArray()
        )
    }

    companion object {
        const val versionLengthStartIndex = 0
        const val versionLengthEndIndex = 2
        const val typeIdLengthStartIndex = 3
        const val typeIdLengthEndIndex = 5
        const val lengthTypeIdIndex = 6

        const val type0subPacketLengthStartIndex = 7
        const val type0subPacketLengthEndIndex = 21
        const val type0firstSubPacketStartIndex = 22

        const val type1subPacketAmountStartIndex = 7
        const val type1subPacketAmountEndIndex = 17
        const val type1firstSubPacketStartIndex = 18
    }
}

sealed class PacketType(val size: Int)
class Literal(val value: Long, size: Int) : PacketType(size)
class Operator(
    val lengthTypeId: Int,
    size: Int,
    vararg val subPackets: BitsPacket,
) : PacketType(size)
