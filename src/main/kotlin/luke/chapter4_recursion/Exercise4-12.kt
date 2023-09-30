package luke.chapter4_recursion

private fun range(start: Int, end: Int): List<Int> {
    tailrec fun doRange(i: Int, accumulated: List<Int>): List<Int> =
        if (i > end)
            accumulated
        else
            doRange(i + 1, accumulated + i)

    return doRange(start, emptyList())
}

fun main() {
    println(range(2, 5)) // [2, 3, 4, 5]
}
