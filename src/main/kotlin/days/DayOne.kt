package days

import inputs.dayOneInput

object DayOne: Day {

    private val elves = dayOneInput.split("\n\n")
    private val calories = elves.map { elf ->
        elf.split("\n").sumOf { it.toInt() }
    }

    override fun solve() {
        println("Part 1: ${totalFromTopElves(1)}")
        println("Part 2: ${totalFromTopElves(3)}")
    }

    private fun totalFromTopElves(count: Int) =
        calories
            .sortedDescending()
            .subList(0, count)
            .sum()

}