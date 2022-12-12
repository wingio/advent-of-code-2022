package days

import inputs.dayElevenInput
import java.math.BigDecimal
import java.math.MathContext

object DayEleven: Day {

    override fun solve() {
        println("Guh couldn't solve part 2")

        println("Monkey business (Part 1): ${testMonkeys(true, 20)}")
        println("Monkey business (Part 2): ${testMonkeys(false, 10000)}")
    }

    private fun testMonkeys(initialRelief: Boolean, rounds: Int): Long {
        val monkeys = dayElevenInput.split("\n\n").map { Monkey.fromInput(it) }
        fun throwToMonkey(monkey: Int, item: Long) = monkeys[monkey].items.add(item)

        val inspections = mutableListOf<Long>()
        monkeys.forEach { _ ->
            inspections.add(0L)
        }

        for (i in 0 until rounds) {
            monkeys.forEachIndexed { index, it ->
                for (x in it.items.indices) {
                    var newWorryLevel = it.operation(it.items[x])
                    if(initialRelief) newWorryLevel = newWorryLevel.floorDiv(3)
                    inspections[index] += 1L
                    it.items[x] = newWorryLevel
                    if(it.test(newWorryLevel))
                        throwToMonkey(it.passedMonkey, newWorryLevel)
                    else
                        throwToMonkey(it.failedMonkey, newWorryLevel)

                }
                it.items.clear()
            }
        }

        inspections.sortDescending()
        val (first, second) = inspections
        println(inspections)
        return first.toLong() * second
    }

}

data class Monkey(
    val items: MutableList<Long>,
    val operation: (Long) -> Long,
    val test: (Long) -> Boolean,
    val passedMonkey: Int,
    val failedMonkey: Int
) {
    companion object {

        private val monkeyRegex = "Monkey \\d+:\\n +Starting items: ((\\d+,? ?)+)\\n +Operation: new = old ([*/+-]) (\\d+|old)\\n +Test: divisible by (\\d+)\\n +If true: throw to monkey (\\d+)+\\n +If false: throw to monkey (\\d+)".toRegex()

        fun fromInput(input: String) = with(input) {
            val parsed = monkeyRegex.matchEntire(input)!!

            val startingItems = parsed.groups[1]!!.value.split(", ").map { it.toLong() }.toMutableList()
            val operator = parsed.groups[3]!!.value
            val operated = parsed.groups[4]!!.value

            val divisibleBy = parsed.groups[5]!!.value.toInt()
            val testPassed = parsed.groups[6]!!.value.toInt()
            val testFailed = parsed.groups[7]!!.value.toInt()

            Monkey(
                items = startingItems,
                operation = {
                    val operand = if(operated == "old") it else operated.toLong()
                    when(operator) {
                        "+" -> it + operand
                        "*" -> it * operand
                        else -> throw NoWhenBranchMatchedException()
                    }
                },
                test = {
                    val bd = it.toBigDecimal()
                    bd.divide(divisibleBy.toBigDecimal(), MathContext.DECIMAL128).remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0
                },
                testPassed,
                testFailed
            )
        }

    }
}