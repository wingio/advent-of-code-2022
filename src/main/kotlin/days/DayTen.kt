package days

import inputs.dayTenInput

object DayTen: Day {

    private val program = dayTenInput.lines()
    private var x = 1
    private val cycles = mutableListOf<Int>()
    private val display = ".".repeat(240).toMutableList()
    private var sprite = x-1..x+1

    override fun solve() {
        program.forEach {
            val command = it.split(" ")
            when(command[0]) {
                "noop" -> cycle()
                "addx" -> {
                    cycle()
                    cycle()
                    x += command[1].toInt()
                }
            }
        }

        val answer = cycles.sumOfIndexed { i, reg ->
            if(((i + 1) - 20) % 40 == 0) reg * (i + 1)
            else 0
        }

        println("Part 1: $answer")
        println("Part 2:")
        display.chunked(40).map {
            it.joinToString("")
        }.forEach(::println)
    }

    private fun cycle() {
        val pos = cycles.lastIndex
        if(sprite.contains(pos % 40)) {
            display[pos] = '#'
        }
        cycles.add(x)
        sprite = x-1..x+1
    }

    private fun <T> Iterable<T>.sumOfIndexed(selector: (Int, T) -> Int): Int {
        var sum = 0
        iterator().withIndex().forEach { (index, value) ->
            sum += selector(index, value)
        }
        return sum
    }

}