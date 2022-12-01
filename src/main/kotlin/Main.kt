import days.Day
import days.DayOne

val days = listOf<Day>(
    DayOne
)

fun main(args: Array<String>) {
    if(args.isEmpty()) return print("Please specify the day.")

    try {
        val day = args.first().toInt()
        days[day - 1].solve()
    } catch (e: Throwable) {
        println("Invalid input")
    }
}