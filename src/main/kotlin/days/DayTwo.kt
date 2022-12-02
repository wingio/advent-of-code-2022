package days

import inputs.dayTwoInput

object DayTwo: Day {

    private val plays = dayTwoInput.split("\n")

    private val scores = mapOf(
        "A X" to 4,
        "B Y" to 5,
        "C Z" to 6,
        "A Y" to 8,
        "B Z" to 9,
        "C X" to 7,
        "A Z" to 3,
        "B X" to 1,
        "C Y" to 2
    )

    private val key = mapOf(
        "A X" to "A Z",
        "B Y" to "B Y",
        "C Z" to "C X",
        "A Y" to "A X",
        "A Z" to "A Y",
        "B X" to "B X",
        "B Z" to "B Z",
        "C X" to "C Y",
        "C Y" to "C Z"
    )

    override fun solve() {
        val total1 = plays.sumOf { scores[it]!! }
        println("Score (Part 1): $total1")

        val total2 = plays.map { key[it]!! }.sumOf { scores[it]!! }
        println("Score (Part 2): $total2")
    }

}