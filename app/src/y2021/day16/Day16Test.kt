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

        "has the correct subPacketLength" {
            (packet.type as Operator).subPacketLength shouldBe 27
        }
    }
})