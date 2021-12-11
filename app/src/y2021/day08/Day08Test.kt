package y2021.day08

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day08Test : FreeSpec({

    val sampleLine: String = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"

    val solutionForSampleLine: Int = 5353

    "Deduct number for the example is correct" {
        listOf(sampleLine)
            .parseToDisplays()
            .first()
            .read() shouldBe solutionForSampleLine
    }
})