package luke.chapter4_recursion

private tailrec fun <T, U> foldRight(list: List<T>, accumulated: U, accumulate: (T, U) -> U): U =
    if (list.isEmpty())
        accumulated
    else
        foldRight(list.dropLast(1), accumulate(list.last(), accumulated), accumulate)

private fun <T> reverse(list: List<T>): List<T> = foldRight(list, emptyList<T>()) { elem: T, acc: List<T> ->
    acc + elem
}

fun main() {
    println(reverse(listOf(1, 2, 3))) // 3,2,1
}
