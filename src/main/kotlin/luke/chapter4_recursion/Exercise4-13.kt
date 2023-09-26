package luke.chapter4_recursion

private fun <T> unfold(seed: T, f: (T) -> T, p: (T) -> Boolean): List<T> {
    tailrec fun doUnfold(elem: T, accumulated: List<T>): List<T> =
        if (p(elem))
            doUnfold(f(elem), accumulated + elem)
        else
            accumulated

    return doUnfold(seed, emptyList())
}

fun main() {
    println(unfold(2, { x -> x + 1 }, { x -> x < 5 })) // [2, 3, 4]
}
