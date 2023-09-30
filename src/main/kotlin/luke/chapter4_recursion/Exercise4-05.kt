package luke.chapter4_recursion

private tailrec fun <T, U> foldLeft(list: List<T>, accumulated: U, accumulate: (T, U) -> U): U =
    if (list.isEmpty())
        accumulated
    else
        foldLeft(list.drop(1), accumulate(list.first(), accumulated), accumulate)

private fun sum(list: List<Int>): Int = foldLeft(list, 0) { n: Int, acc: Int ->
    n + acc
}

private fun toString(list: List<Char>): String = foldLeft(list, "") { c: Char, acc: String ->
    "$acc$c"
}

private fun <T> makString(list: List<T>, delim: String): String = foldLeft(list, "") { elem: T, acc: String ->
    if (acc.isEmpty())
        "$elem"
    else
        "$acc$delim$elem"
}


fun main() {
    println(sum(listOf(1, 2, 3, -1, 5))) // 10

    println(toString(listOf('a', 'b', 'c'))) // abc

    println(makString(listOf(1, 2, 3), ",")) // 1,2,3
}
