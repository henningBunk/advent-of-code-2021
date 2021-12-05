package day05

import common.InputRepo
import common.readSessionCookie
import common.solve
import kotlin.math.sign

typealias VentMap = List<MutableList<Int>>
typealias VentLine = Pair<Point, Point>

fun main(args: Array<String>) {
    val day = 5
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay05Part1, ::solveDay05Part2)
}

// The solution for day 5 part 1 is: 7468
fun solveDay05Part1(input: List<String>): Int = input
    .parse()
    .filterDiagonals()
    .countProblematicVents()

//The solution for day 5 part 2 is: 22364
fun solveDay05Part2(input: List<String>): Int = input
    .parse()
    .countProblematicVents()

fun List<String>.parse(): List<VentLine> = map {
    val (fromX, fromY, toX, toY) = """(\d+),(\d+) -> (\d+),(\d+)""".toRegex().find(it)?.destructured
        ?: error("Parsing error")
    Pair(Point(fromX.toInt(), fromY.toInt()), Point(toX.toInt(), toY.toInt()))
}

fun List<VentLine>.filterDiagonals(): List<Pair<Point, Point>> =
    this.filter { (from, to) -> from.x == to.x || from.y == to.y }

fun List<VentLine>.countProblematicVents(): Int {
    val mapSize = determineMapSize(this)
    return fold(emptyMap(mapSize)) { ventMap, ventLine ->
        ventMap.drawLine(ventLine)
        ventMap
    }.flatten().count { it > 1 }
}

fun determineMapSize(lines: List<VentLine>): Point = lines
    .fold(Point(0, 0)) { acc, (from, to) ->
        Point(
            listOf(acc.x, from.x, to.x).maxOrNull() ?: 0,
            listOf(acc.y, from.y, to.y).maxOrNull() ?: 0
        )
    }
    .let { Point(it.x + 1, it.y + 1) }

fun emptyMap(size: Point): VentMap {
    return List(size.y) { MutableList(size.x) { 0 } }
}

fun VentMap.drawLine(line: VentLine) {
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