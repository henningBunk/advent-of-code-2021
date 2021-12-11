package y2021.day05

import common.Answers
import common.AocSolution
import common.annotations.AoCPuzzle
import kotlin.math.sign

typealias VentMap = List<MutableList<Int>>


fun main(args: Array<String>) {
    Day05().solveThem()
}

@AoCPuzzle(2021, 5)
class Day05 : AocSolution {
    override val answers = Answers(samplePart1 = 5, samplePart2 = 12, part1 = 7468, part2 = 22364)

    override fun solvePart1(input: List<String>): Any = input
        .parseToLines()
        .filter { line -> !line.isDiagonal() }
        .countProblematicVents()

    override fun solvePart2(input: List<String>): Any = input
        .parseToLines()
        .countProblematicVents()

}

fun List<String>.parseToLines(): List<Line> = map {
    val (fromX, fromY, toX, toY) = """(\d+),(\d+) -> (\d+),(\d+)""".toRegex().find(it)?.destructured
        ?: error("Parsing error")
    Line(Point(fromX.toInt(), fromY.toInt()), Point(toX.toInt(), toY.toInt()))
}

fun List<Line>.countProblematicVents(): Int {
    val mapSize = determineMapSize(this)
    return fold(emptyMap(mapSize)) { ventMap, line ->
        ventMap.apply { drawLine(line) }
    }.flatten().count { it > 1 }
}

fun determineMapSize(lines: List<Line>): Point = lines
    .flatMap { line -> listOf(line.a, line.b) }
    .let { points -> Point(points.maxOf { it.x } + 1, points.maxOf { it.y } + 1) }

fun emptyMap(size: Point): VentMap {
    return List(size.y) { MutableList(size.x) { 0 } }
}

fun VentMap.drawLine(line: Line) {
    fun addVent(point: Point) {
        this[point.y][point.x] += 1
    }

    val (from, to) = line
    val direction = (to - from).let { Point(it.x.sign, it.y.sign) }

    var current = from
    while (current != to) {
        addVent(current)
        current += direction
    }
    addVent(to)
}

data class Point(val x: Int, val y: Int) {
    operator fun minus(other: Point): Point = Point(x - other.x, y - other.y)
    operator fun plus(other: Point): Point = Point(x + other.x, y + other.y)
}

data class Line(val a: Point, val b: Point) {
    fun isDiagonal() = a.x != b.x && a.y != b.y
}
