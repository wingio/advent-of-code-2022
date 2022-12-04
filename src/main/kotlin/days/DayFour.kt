package days

import inputs.dayFourInput

object DayFour: Day {

    val assignments = dayFourInput.split("\n").map {
        val elves = it.split(",")
        elves[0].toIntRange() to elves[1].toIntRange()
    }

    override fun solve() {
        val rowsFullyOverlap = assignments.count { elves ->
            elves.first.all { elves.second.contains(it) } || elves.second.all { elves.first.contains(it) }
        }
        println("Rows fully containing each-other (Part 1): $rowsFullyOverlap")

        val rowsPartiallyOverlap = assignments.count { elves ->
            elves.first.any { elves.second.contains(it) } || elves.second.any { elves.first.contains(it) }
        }
        println("Rows overlapping (Part 2): $rowsPartiallyOverlap")
    }

    private fun String.toIntRange(): IntRange {
        val ends = split("-")
        return IntRange(ends[0].toInt(), ends[1].toInt())
    }

}