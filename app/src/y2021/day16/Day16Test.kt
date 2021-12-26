package y2021.day16

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf

class Day16Test : FreeSpec({

    "The packet D2FE28" - {

        val hex = "D2FE28"
        val packet = BitsPacket(hex.hexToBinary())

        "has the correct binary representation" {
            hex.hexToBinary() shouldBe "110100101111111000101000"
        }

        "has the correct packet version" {
            packet.version shouldBe 6
        }

        "has the correct type id" {
            packet.typeId shouldBe 4
        }

        "represents a literal value" {
            packet.type.shouldBeTypeOf<Literal>()
        }

        "represents the correct literal value" {
            (packet.type as Literal).value shouldBe 2021
        }

        "has the correct size" {
            packet.type.size shouldBe 21
        }
    }

    "The operator packet 38006F45291200" - {

        val hex = "38006F45291200"
        val packet = BitsPacket(hex.hexToBinary())

        "has the correct binary representation" {
            hex.hexToBinary() shouldBe "00111000000000000110111101000101001010010001001000000000"
        }

        "has the correct packet version" {
            packet.version shouldBe 1
        }

        "has the correct type id" {
            packet.typeId shouldBe 6
        }

        "represents an operator value" {
            packet.type.shouldBeTypeOf<Operator>()
        }

        "has the correct lengthTypeId" {
            (packet.type as Operator).lengthTypeId shouldBe 0
        }

        "contains two subpackets" {
            (packet.type as Operator).subPackets.size shouldBe 2
        }

        "contains the subpacket 10" {
            (packet.type as Operator).subPackets.any {
                (it.type as? Literal)?.value == 10L
            } shouldBe true
        }

        "contains the subpacket 20" {
            (packet.type as Operator).subPackets.any {
                (it.type as? Literal)?.value == 20L
            } shouldBe true
        }
    }

    "The operator packet EE00D40C823060" - {

        val hex = "EE00D40C823060"
        val packet = BitsPacket(hex.hexToBinary())

        "has the correct binary representation" {
            hex.hexToBinary() shouldBe "11101110000000001101010000001100100000100011000001100000"
        }

        "has the correct packet version" {
            packet.version shouldBe 7
        }

        "has the correct type id" {
            packet.typeId shouldBe 3
        }

        "represents an operator value" {
            packet.type.shouldBeTypeOf<Operator>()
        }

        "has the correct lengthTypeId" {
            (packet.type as Operator).lengthTypeId shouldBe 1
        }

        "contains three subpackets" {
            (packet.type as Operator).subPackets.size shouldBe 3
        }

        "contains the subpacket with the value 1" {
            (packet.type as Operator).subPackets.any {
                (it.type as? Literal)?.value == 1L
            } shouldBe true
        }

        "contains the subpacket with the value 2" {
            (packet.type as Operator).subPackets.any {
                (it.type as? Literal)?.value == 2L
            } shouldBe true
        }

        "contains the subpacket with the value 3" {
            (packet.type as Operator).subPackets.any {
                (it.type as? Literal)?.value == 3L
            } shouldBe true
        }
    }

    "The version sum is correct" - {
        "8A004A801A8002F478" {
            val hex = "8A004A801A8002F478"
            val packet = BitsPacket(hex.hexToBinary())
            packet.versionSum shouldBe 16
        }
        "620080001611562C8802118E34" {
            val hex = "620080001611562C8802118E34"
            val packet = BitsPacket(hex.hexToBinary())
            packet.versionSum shouldBe 12
        }
        "C0015000016115A2E0802F182340" {
            val hex = "C0015000016115A2E0802F182340"
            val packet = BitsPacket(hex.hexToBinary())
            packet.versionSum shouldBe 23
        }
        "A0016C880162017C3686B18A3D4780" {
            val hex = "A0016C880162017C3686B18A3D4780"
            val packet = BitsPacket(hex.hexToBinary())
            packet.versionSum shouldBe 31
        }
    }

    "The outermost values of these transmissions are correct" - {
        "for C200B40A82 which sums values" {
            val hex = "C200B40A82"
            val packet = BitsPacket(hex.hexToBinary())
            packet.value shouldBe 3
        }
        "for 04005AC33890 which multiplies values" {
            val hex = "04005AC33890"
            val packet = BitsPacket(hex.hexToBinary())
            packet.value shouldBe 54
        }
        "for 880086C3E88112 which takes the min value" {
            val hex = "880086C3E88112"
            val packet = BitsPacket(hex.hexToBinary())
            packet.value shouldBe 7
        }
        "for CE00C43D881120 which takes the max value" {
            val hex = "CE00C43D881120"
            val packet = BitsPacket(hex.hexToBinary())
            packet.value shouldBe 9
        }
        "for D8005AC2A8F0 which uses the less than operator" {
            val hex = "D8005AC2A8F0"
            val packet = BitsPacket(hex.hexToBinary())
            packet.value shouldBe 1
        }
        "for F600BC2D8F which uses the greater than operator" {
            val hex = "F600BC2D8F"
            val packet = BitsPacket(hex.hexToBinary())
            packet.value shouldBe 0
        }
        "for 9C005AC2F8F0 which uses the equal operator" {
            val hex = "9C005AC2F8F0"
            val packet = BitsPacket(hex.hexToBinary())
            packet.value shouldBe 0
        }
        "for 9C0141080250320F1802104A08 which uses multiple operators" {
            val hex = "9C0141080250320F1802104A08"
            val packet = BitsPacket(hex.hexToBinary())
            packet.value shouldBe 1
        }
    }
})