package days

import inputs.dayFiveInput

typealias Grid<T> = MutableList<MutableList<T>>

object DayFive: Day {

    private val instructionRegex = "move (\\d+) from (\\d) to (\\d+)".toRegex()
    private val input = dayFiveInput.split("\n\n")

    override fun solve() {
        println("Top crates (Part 1): ${moveCrates(canLiftMultiple = false)}")
        println("Top crates (Part 2): ${moveCrates(canLiftMultiple = true)}")
    }

    private fun parseCrates(): Grid<String> {
        val rows = input[0].split("\n")
        val grid = rows.map { it.chunked(4).map { s -> s.replace(" ", "")  }.toMutableList() }.toMutableList()
        grid[0].removeAt(0) // Idk
        grid.removeLast()
        val columns: Grid<String> = mutableListOf()
        grid.forEach {
            it.forEachIndexed { i, col ->
                    if(columns.getOrNull(i) == null )
                        columns.add(i, mutableListOf(col))
                    else
                        columns[i] += col
            }
        }
        return columns.onEach {
            it.removeIf { col -> col.isBlank() }
        }
    }

    private fun parseInstructions() = input[1].split("\n").map {
        val match = instructionRegex.matchEntire(it)!!
        listOf(match.groups[1]!!.value.toInt(), match.groups[2]!!.value.toInt(), match.groups[3]!!.value.toInt())
    }

    private fun moveCrates(canLiftMultiple: Boolean): String {
        val grid = parseCrates()
        val instructions = parseInstructions()
        instructions.forEach {
            val (amount, from, to) = it
            val movedCrates = grid[from - 1].take(amount)
            for (i in movedCrates.indices)
                grid[from - 1].removeFirst()
            grid[to - 1].addAll(0, if(canLiftMultiple) movedCrates else movedCrates.reversed())
        }
        val topCrates = grid.joinToString("") {
            it[0][1].toString()
        }
        return topCrates
    }

}