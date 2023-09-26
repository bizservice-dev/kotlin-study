package luke.chapter4_recursion

private tailrec fun <T, U> foldLeft(list: List<T>, accumulated: U, accumulate: (T, U) -> U): U =
    if (list.isEmpty())
        accumulated
    else
        foldLeft(list.drop(1), accumulate(list.first(), accumulated), accumulate)

private fun <T> reverse(list: List<T>): List<T> = foldLeft(list, emptyList<T>()) { elem: T, acc: List<T> ->
    listOf(elem) + acc.toMutableList()
}

fun main() {
    println(reverse(listOf(1, 2, 3))) // 3,2,1
}
