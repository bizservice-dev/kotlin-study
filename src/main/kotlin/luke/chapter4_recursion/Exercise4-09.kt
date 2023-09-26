package luke.chapter4_recursion

private fun range(start: Int, end: Int): List<Int> = mutableListOf<Int>()
    .apply {
        for (i in start..end) {
            add(i)
        }
    }

fun main() {
    println(range(2, 5)) // [2, 3, 4, 5]
}
