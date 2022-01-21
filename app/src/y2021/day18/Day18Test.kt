package y2021.day18

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day18Test : FreeSpec({


    "The correct Snailfish number is created from the input" - {
        "4 input numbers" {
            // [[[[1,1],[2,2]],[3,3]],[4,4]]
            listOf(
                Pair(1, 1),
                Pair(2, 2),
                Pair(3, 3),
                Pair(4, 4),
            ).createSnailfishNumber() shouldBe Pair(Pair(Pair(Pair(1, 1), Pair(2, 2)), Pair(3, 3)), Pair(4, 4))
        }

        "5 input members" {
            // [[[[3,0],[5,3]],[4,4]],[5,5]]
            listOf(
                1 to 1,
                2 to 2,
                3 to 3,
                4 to 4,
                5 to 5,
            ).createSnailfishNumber() shouldBe ((((3 to 0) to (5 to 3)) to (4 to 4)) to (5 to 5))
        }

        "6 input numbers" {
            // [[[[5,0],[7,4]],[5,5]],[6,6]]
            listOf(
                1 to 1,
                2 to 2,
                3 to 3,
                4 to 4,
                5 to 5,
                6 to 6,
            ).createSnailfishNumber() shouldBe ((((5 to 0) to (7 to 4)) to (5 to 5)) to (6 to 6))
        }
    }
})