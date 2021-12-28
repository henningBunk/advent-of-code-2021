package y2021.day20

import common.*
import common.annotations.AoCPuzzle

fun main(args: Array<String>) {
    Day20TrenchMap().solveThem()
}

@AoCPuzzle(2021, 20)
class Day20TrenchMap : AocSolution {
    override val answers = Answers(samplePart1 = 35, samplePart2 = 3351, part1 = 5622)

    override fun solvePart1(input: List<String>): Any = input
        .enhance(2)
        .litPixel

    override fun solvePart2(input: List<String>): Any = input
        .enhance(50)
        .litPixel

    private fun List<String>.enhance(steps: Int): Image {
        return (0 until steps).fold(
            Image(
                image = drop(2).joinToString(""),
                width = drop(2).size,
                algorithm = first()
            )
        ) { image, _ -> image.enhance() }
    }

}

class Image(
    private val image: String,
    private val width: Int,
    private val algorithm: String,
    private val isBlack: Boolean = true,
) {

    val litPixel = image.count { it == '#' }

    fun enhance(): Image = Image(
        image = buildString {
            forEachPixel { x, y ->
                append(
                    getFilterCore(x, y)
                        .replace(BLACK, '0')
                        .replace(WHITE, '1')
                        .toInt(2)
                        .let(algorithm::get)
                )
            }
        },
        width = width + 2,
        algorithm = algorithm,
        isBlack = if (algorithm.first() == '#') !isBlack else isBlack
    )

    private fun forEachPixel(action: (x: Int, y: Int) -> Unit) {
        for (y in -1..(image.length / width)) {
            for (x in -1..width) {
                action(x, y)
            }
        }
    }

    private fun getFilterCore(x: Int, y: Int): String {
        return buildString {
            for (dY in -1..1) {
                for (dX in -1..1) {
                    append(getPixel(x + dX, y + dY))
                }
            }
        }
    }

    override fun toString(): String = buildString {
        forEachPixel { x, y ->
            if (x == -1) append("\n")
            append(getPixel(x, y))
        }
    }

    private fun getPixel(x: Int, y: Int): Char = when {
        x < 0 || x >= width -> getPixelFromVoid()
        else -> image.getOrElse(x + y * width) { getPixelFromVoid() }
    }

    private fun getPixelFromVoid(): Char = if (isBlack) BLACK else WHITE

    companion object {
        const val BLACK = '.'
        const val WHITE = '#'
    }
}
