package days

import days.DaySix.hasDuplicates
import inputs.daySixInput

object DaySix: Day {

    override fun solve() {
        val (sopm, sopi) = findMarker(4)
        println("Start of packet marker (Part 1): $sopm at $sopi")

        val (somm, somi) = findMarker(14)
        println("Start of message marker (Part 2): $somm at $somi")
    }

    private fun findMarker(length: Int): Pair<String, Int> {
        var marker = ""
        for (i in daySixInput.indices) {
            marker += daySixInput[i]
            if(marker.length > length) marker = marker.drop(1)
            if(marker.length == length && !marker.hasDuplicates()) {
                return marker to i + 1
            }
        }
        return "" to daySixInput.lastIndex
    }

    private fun String.hasDuplicates() = any {
        count { c ->
            c == it
        } > 1
    }
}