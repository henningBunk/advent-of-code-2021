package y2021.day17

import helper.Point
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day17Test : FreeSpec({

    "A probe which is shot with 5,2" - {
        "is at 0,0 after zero steps" {
            val probe = Probe(Point(5, 2))
            probe.position shouldBe Point(0, 0)
        }

        "is at 5,2 after one step" {
            val probe = Probe(Point(5, 2))
            repeat(1) { probe.step() }
            probe.position shouldBe Point(5, 2)
        }

        "is at 9,3 after two steps" {
            val probe = Probe(Point(5, 2))
            repeat(2) { probe.step() }
            probe.position shouldBe Point(9, 3)
        }

        "is at 12,3 after three steps" {
            val probe = Probe(Point(5, 2))
            repeat(3) { probe.step() }
            probe.position shouldBe Point(12, 3)
        }

        "is at 14,2 after four steps" {
            val probe = Probe(Point(5, 2))
            repeat(4) { probe.step() }
            probe.position shouldBe Point(14, 2)
        }

        "is at 15,0 after five steps" {
            val probe = Probe(Point(5, 2))
            repeat(5) { probe.step() }
            probe.position shouldBe Point(15, 0)
        }

        "is at 15,-3 after six steps" {
            val probe = Probe(Point(5, 2))
            repeat(6) { probe.step() }
            probe.position shouldBe Point(15, -3)
        }
    }

    "A probe with velocity 7,2 and a target of x=20..30, y=-10..-5" - {

        val target = Target((20..30), (-10..-5))

        "is not in the target after 6 steps" {
            val probe = Probe(Point(7, 2))
            repeat(6) { probe.step() }
            probe.isInTarget(target) shouldBe false
        }

        "is in the target after 7 steps" {
            val probe = Probe(Point(7, 2))
            repeat(7) { probe.step() }
            probe.isInTarget(target) shouldBe true
        }
    }

    "A probe with velocity 7,-1 and a target of x=20..30, y=-10..-5" - {

        val target = Target((20..30), (-10..-5))

        "should hit the target" {
            val probe = Probe(Point(7, -1))
            while (probe.isOnTrack(target) && !probe.isInTarget(target)) {
                probe.step()
                println(probe.position)
            }
            probe.isInTarget(target) shouldBe true
        }
    }

    "A probe with velocity 6,0 and a target of x=20..30, y=-10..-5" - {

        val target = Target((20..30), (-10..-5))

        "should hit the target" {
            val probe = Probe(Point(6, 0))
            while (probe.isOnTrack(target) && !probe.isInTarget(target)) {
                probe.step()
                println(probe.position)
            }
            probe.isInTarget(target) shouldBe true
        }
    }

    "A probe with velocity 6,9 reaches the highest point of 45" {
        val probe = Probe(Point(6, 9))
        repeat(50) { probe.step(); println(probe.position.y) }
        probe.highestReachedY shouldBe 45
    }
})