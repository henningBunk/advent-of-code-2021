package y2021.day17

import common.*
import common.annotations.AoCPuzzle
import helper.Point
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.sign

fun main(args: Array<String>) {
    Day17TrickShot().solveThem()
}

@AoCPuzzle(2021, 17)
class Day17TrickShot : AocSolution {
    override val answers = Answers(samplePart1 = 45, samplePart2 = 112, part1 = 5050, part2 = 2223)

    override fun solvePart1(input: List<String>): Any {
        val target = parseToTarget(input.first())

        var maxY = 0

        for (y in (0..100)) {
            var yHasAHit = false
            for(x in (0..100)) {
                val candidate = Probe(Point(x, y))

                while(candidate.isOnTrack(target)) {
                    candidate.step()
                    if(candidate.isInTarget(target)) {
                        maxY = max(maxY, candidate.highestReachedY)
                        yHasAHit = true
                        break
                    }
                }
                if(yHasAHit) break
            }
        }
        return maxY
    }

    override fun solvePart2(input: List<String>): Any {
        val target = parseToTarget(input.first())

        var hitCount = 0
        for (y in (target.y.first..100)) {
            for(x in (0..target.x.last)) {
                val candidate = Probe(Point(x, y))
                while(candidate.isOnTrack(target) && !candidate.isInTarget(target)) {
                    candidate.step()
                    if(candidate.isInTarget(target)) {
                        hitCount++
                        break
                    }
                }
            }
        }
        return hitCount
    }

    private fun parseToTarget(input: String): Target {
        val (xStart, xEnd, yStart, yEnd) = """target area: x=(-?\d+)..(-?\d+), y=(-?\d+)..(-?\d+)"""
            .toRegex()
            .find(input)
            ?.destructured ?: error("Did not match")
        return Target(x = (xStart.toInt()..xEnd.toInt()), y = (yStart.toInt()..yEnd.toInt()))
    }
}

class Probe(private var velocity: Point) {

    private var _position = Point(0, 0)
    val position: Point
        get() = _position

    private var _highestReachedY = 0
    val highestReachedY: Int
        get() = _highestReachedY

    fun step() {
        _position += velocity
        velocity = Point(velocity.x.decrease(), velocity.y - 1)
        _highestReachedY = max(_highestReachedY, _position.y)
    }

    fun isInTarget(target: Target): Boolean =
        position.x in target.x && position.y in target.y

    fun isOnTrack(target: Target): Boolean =
        position.y >= target.y.first

    private fun Int.decrease(): Int = when {
        this == 0 -> 0
        else -> sign * (absoluteValue - 1)
    }
}

data class Target(val x: IntRange, val y: IntRange)
