package iron.example4

import iron.Example

/**
 * unfold의 재귀 버전을 작성하라
 */

class `Ex4-13` : Example {

    fun <T> unfold(seed: T, f: (T) -> T, p: (T) -> Boolean): List<T> {
        return if (!p(seed)) {
            listOf()
        } else {
            prepend(unfold(f(seed), f, p), seed)
        }
    }

    fun <T> prepend(list: List<T>, item: T): List<T> {
        return listOf(item) + list
    }

    fun range(start: Int, end: Int): List<Int> {
        return unfold(start, { it + 1 }, { it < end })
    }

    override fun run() {
        println(range(1, 10))
    }
}
