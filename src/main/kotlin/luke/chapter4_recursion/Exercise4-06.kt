package luke.chapter4_recursion

private tailrec fun <T, U> foldRight(list: List<T>, accumulated: U, accumulate: (T, U) -> U): U =
    if (list.isEmpty())
        accumulated
    else
        foldRight(list.dropLast(1), accumulate(list.last(), accumulated), accumulate)

private fun <T> toString(list: List<T>): String = foldRight(list, "") { elem: T, acc: String ->
    "$elem$acc"
}

fun main() {
    println(toString(listOf('a', 'b', 'c'))) // abc
}
