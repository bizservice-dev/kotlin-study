package luke.chapter4_recursion

private fun <T> iterate(seed: T, f: (T) -> T, n: Int): List<T> {
    tailrec fun doIterate(elem: T, accumulated: List<T>): List<T> =
        if (accumulated.size < n)
            doIterate(f(elem), accumulated + elem)
        else
            accumulated

    return doIterate(seed, emptyList())
}
