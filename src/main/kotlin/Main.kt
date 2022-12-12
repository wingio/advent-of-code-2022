import days.*

val days = listOf(
    DayOne,
    DayTwo,
    DayThree,
    DayFour,
    DayFive,
    DaySix,
    DaySeven,
    DayEight,
    DayNine,
    DayTen,
    DayEleven
)

fun main(args: Array<String>) {
    if(args.isEmpty()) return print("Please specify the day.")


    val day = args.first().toInt()
    days[day - 1].solve()
}