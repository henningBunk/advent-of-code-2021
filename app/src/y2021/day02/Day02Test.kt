package y2021.day02

import common.InputRepo
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day02Test : FreeSpec({

    val repo = InputRepo()

    "Day 2" - {
        "part 1 should return the correct value" - {
            "for the sample input" {
                println(System.getProperty("user.dir"))
                Day02().solvePart1(repo.getSample(2021, 2)) shouldBe Day02().answers.samplePart1
            }

            "for the real input" {

            }
        }
    }

})
