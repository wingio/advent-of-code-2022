package days

import inputs.dayEightInput

object DayEight: Day {

    private val forest = dayEightInput.split("\n").map {
        it.split("").filter { c -> c.isNotBlank() }.map { c ->
            c.toInt()
        }
    }

    override fun solve() {
        val visible = forest.indices.sumOf { row ->
            forest[row].indices.count { col ->
                isVisible(row, col)
            }
        }

        println("Trees visible from the outside (Part 1): $visible")

        val score = forest.indices.maxOf { row ->
            forest[row].indices.maxOf { col ->
                treeScore(row, col)
            }
        }

        println("Highest score possible (Part 2): $score")
    }

    private fun isVisible(rowIndex: Int, columnIndex: Int): Boolean {
        val height = forest[rowIndex][columnIndex]
        val row = forest[rowIndex]
        val col = getColumn(columnIndex)
        if (rowIndex == 0 || rowIndex == row.size -1 || columnIndex == 0 || columnIndex == forest.size -1) {
            return true
        }

        val left = row.subList(0, columnIndex).max()
        val right = row.subList(columnIndex + 1, forest.size).max()
        val up = col.subList(0, rowIndex).max()
        val down = col.subList(rowIndex + 1, forest.size).max()
        return height > left || height > right || height > up || height > down
    }

    private fun treeScore(rowIndex: Int, columnIndex: Int): Int {
        val height = forest[rowIndex][columnIndex]
        val row = forest[rowIndex]
        val col = getColumn(columnIndex)
        if (rowIndex == 0 || rowIndex == row.size -1 || columnIndex == 0 || columnIndex == forest.size -1) {
            return 0
        }

        fun getScore(treeLine: List<Int>) : Int {
            var count = 0
            for (t in treeLine) {
                count++
                if (t >= height) {
                    break
                }
            }
            return count
        }

        val left = getScore(row.subList(0, columnIndex).reversed())
        val right = getScore(row.subList(columnIndex + 1, forest.size))
        val up = getScore(col.subList(0, rowIndex).reversed())
        val down = getScore(col.subList(rowIndex + 1, forest.size))

        return left * right * up * down
    }

    private fun getColumn(index: Int) = forest.map {
        it[index]
    }

}