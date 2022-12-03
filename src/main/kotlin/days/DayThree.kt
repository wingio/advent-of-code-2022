package days

import inputs.dayThreeInput

object DayThree : Day {

    private val itemTypes = ('a'..'z') + ('A'..'Z')
    private val sacks = dayThreeInput.split("\n").map {
        it.take((it.lastIndex / 2) + 1) to it.takeLast((it.lastIndex / 2) + 1)
    }

    override fun solve() {
        part1()
        part2()
    }

    private fun part1() {
        val sharedItems = mutableListOf<Char>()
        sacks.forEach { sack ->
            sharedItems += sack.first.first { sack.second.contains(it) }
        }
        println("Total priorities (Part 1): ${sharedItems.sumOf { itemTypes.indexOf(it) + 1 }}")
    }

    private fun part2() {
        val priority = sacks.map { (it.first + it.second) }.chunked(3).sumOf { sacks ->
            val matchingItem = sacks[0].find { item ->
                sacks.all { sack ->
                    sack.contains(item)
                }
            }
            itemTypes.indexOf(matchingItem) + 1
        }
        println("Badge priorities (Part 2): $priority")
    }

}