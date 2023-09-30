package luke.chapter4_recursion

private fun <T, U> map(list: List<T>, f: (T) -> U): List<U> {
    tailrec fun doMap(origin: List<T>, accumulated: List<U>): List<U> =
        if (accumulated.size == list.size)
            accumulated
        else
            doMap(origin.drop(1), accumulated + f(origin.first()))

    return doMap(list, emptyList())
}

fun main() {
    println(map(listOf(1, 2, 3)) { x -> x * 2 }) // [2, 4, 6]
}
