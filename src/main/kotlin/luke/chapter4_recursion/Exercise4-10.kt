package luke.chapter4_recursion

private fun <T> unfold(seed: T, f: (T) -> T, p: (T) -> Boolean): List<T> = mutableListOf<T>()
    .apply {
        var curr = seed
        while (p(curr)) {
            add(curr)

            curr = f(curr)
        }
    }

fun main() {
    println(unfold(2, { x -> x + 1 }, { x -> x < 5 })) // [2, 3, 4]
}
