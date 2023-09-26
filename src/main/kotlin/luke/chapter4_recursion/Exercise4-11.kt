package luke.chapter4_recursion

private fun <T> unfold(seed: T, f: (T) -> T, p: (T) -> Boolean): List<T> = mutableListOf<T>()
    .apply {
        var curr = seed
        while (p(curr)) {
            add(curr)

            curr = f(curr)
        }
    }

private fun range(start: Int, end: Int) = unfold(start, { x -> x + 1 }) { x -> x <= end }


fun main() {
    println(range(2, 5)) // [2, 3, 4, 5]
}
