package iron.example4

import iron.Example

/**
 * unfold 처럼 작동하는 iterate 함수를 작성하라. 조건을 만족할 때 까지 자기 자신을 재귀호출하는 unfold와 다르게
 * iterate는 주어진 횟수만큼 자신을 재귀호출 한다
 */


class `Ex4-16` : Example {

    fun <T> iterate(seed: T, f: (T) -> T, n: Int): List<T> {
        tailrec fun <T> iterate_(merge: List<T>, item: T, f: (T) -> T, repeat: Int): List<T> {
            return if (repeat <= 0) {
                merge
            } else {
                iterate_(merge + item, f(item), f, repeat-1)
            }
        }

        return iterate_(listOf(), seed, f, n)
    }

    fun range(start: Int, end: Int): List<Int> {
        return iterate(start, { it + 1 }, end-start)
    }

    override fun run() {
        println(range(1, 10))
    }
}
