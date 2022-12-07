package days

import inputs.daySevenInput

object DaySeven: Day {

    private val termOutput = daySevenInput.split("\n")

    private val fs = File("/", 0, null, mutableListOf(), true)
    private var currentDir: File? = fs
    override fun solve() {
        termOutput.forEachIndexed { i, line ->
            if(line.startsWith("$")) {
                val (_, command) = line.split(" ")
                when(command) {
                    "cd" -> cd(line.split(" ")[2])
                    "ls" -> ls(i)
                }
            }
        }

        val allFiles = fs.traverse()
        val neededSpace = 30000000 - (70000000 - allFiles.sumOf { it.size })
        allFiles.filter { it.isDir && it.realSize <= 100000 }.sumOf { it.realSize }.also {
            println("Total size of all dirs under 100000 (Part 1): $it")
        }

        allFiles.sortedBy { it.realSize }.first { it.isDir && it.realSize >= neededSpace }.also {
            println("Directory to be deleted: ${it.name} (${it.realSize})")
        }
    }

    private fun cd(dest: String) {
        currentDir = when(dest) {
            "/" -> fs
            ".." -> currentDir?.parent
            else -> currentDir?.children?.first { it.name == dest }
        }
    }

    private fun ls(currentIndex: Int) {
        for (item in termOutput.drop(currentIndex + 1)) {
            if(item.startsWith("$")) break
            val (sizeOrType, name) = item.split(" ")
            val isDir = sizeOrType == "dir"
            val file = File(
                name = name,
                size = if(isDir) 0 else sizeOrType.toInt(),
                parent = currentDir,
                children = mutableListOf(),
                isDir = isDir
            )
            (currentDir ?: fs).children.add(file)
        }
    }
}

data class File(
    val name: String,
    val size: Int,
    val parent: File?,
    val children: MutableList<File>,
    val isDir: Boolean
) {

    val realSize: Int
        get() = if(isDir) children.sumOf { it.realSize } else size

    fun traverse(): List<File> {
        val allFiles = mutableListOf<File>()
        fun fe(file: File) {
            allFiles.addAll(file.children)
            file.children.forEach(::fe)
        }
        children.forEach(::fe)
        allFiles.addAll(children)
        return allFiles
    }

}