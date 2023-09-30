package iron.example4

import iron.Example

/**
 * 재귀적 unfold의 꼬리재귀 버전을 만들 수 있을까? -> 만들 수 있을 것 같다
 */


class `Ex4-14` : Example {

    // 이상한 건 책처럼 함수에 대한 클로저가 적용이 안된다..
    fun <T> unfold(seed: T, f: (T) -> T, p: (T) -> Boolean): List<T> {
        tailrec fun <T> unfold_(merge: List<T>, item: T, f: (T) -> T, p: (T) -> Boolean): List<T> {
            return if (!p(item)) {
                merge
            } else {
                unfold_(merge + item, f(item), f, p)
            }
        }

        return unfold_(listOf(), seed, f, p)
    }

    fun range(start: Int, end: Int): List<Int> {
        return unfold(start, { it + 1 }, { it < end })
    }

    override fun run() {
        println(range(2, 11))
    }
}
