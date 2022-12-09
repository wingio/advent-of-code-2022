package days

import inputs.dayNineInput
import kotlin.math.abs
import kotlin.math.sign

object DayNine: Day {

    private val instructions = dayNineInput.lines().map {
        it[0] to it.split(" ")[1].toInt()
    }

    private fun followInstructions(knotCount: Int): Int {
        val knots = MutableList(knotCount) { Point(0,0) }
        val uniquePoints = mutableSetOf<Point>()

        instructions.forEach { (dir, steps) ->
            repeat(steps) {
                knots[0] = knots[0] + Point.directions[dir]!!

                knots.indices.windowed(2) { (head, tail) ->
                    if (!(knots[tail] neighbors knots[head])) {
                        knots[tail] = knots[tail] moveTo knots[head]
                    }
                }

                uniquePoints.add(knots.last())
            }
        }

        return uniquePoints.size
    }
    override fun solve() {
        println("Spaces tail has been (Part 1): ${followInstructions(2)}")
        println("Spaces tail has been (Part 2): ${followInstructions(10)}")
    }
}

data class Point(
    val x: Int,
    val y: Int
) {

    operator fun plus(other: Point) = Point(other.x + x, other.y + y)

    infix fun neighbors(other: Point) = abs(other.x - x) < 2 && abs(other.y - y) < 2

    infix fun moveTo(other: Point) = this + Point((other.x - x).sign, (other.y - y).sign)

    companion object {
        val directions = mapOf(
            'U' to Point(0, 1),
            'D' to Point(0, -1),
            'L' to Point(-1, 0),
            'R' to Point(1, 0)
        )
    }

}