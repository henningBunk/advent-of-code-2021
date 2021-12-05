package day05

import common.InputRepo
import common.readSessionCookie
import common.solve
import kotlin.math.sign

typealias VentMap = List<MutableList<Int>>

fun main(args: Array<String>) {
    val day = 5
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay05Part1, ::solveDay05Part2)
}

// The solution for day 5 part 1 is: 7468
fun solveDay05Part1(input: List<String>): Int = input
    .parseToLines()
    .filter { line -> !line.isDiagonal() }
    .countProblematicVents()

//The solution for day 5 part 2 is: 22364
fun solveDay05Part2(input: List<String>): Int = input
    .parseToLines()
    .countProblematicVents()

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
    .let { points -> Point(points.maxOf { it.x } + 1, points.maxOf {it.y} + 1) }

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