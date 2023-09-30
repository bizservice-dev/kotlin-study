package iron.example4

import iron.Example

/**
 * (T) -> U 타입의 함수를 List<T> 타입 리스트의 모든 원소에 적용해 만든 List<U> 타입의 리스트를 돌려주는 map 함수를 만들라
 */

class `Ex4-17` : Example {

    fun <T, U> map(list: List<T>, f: (T) -> U): List<U> {
        tailrec fun <T, U> map_(list: List<T>, merge: List<U>, f: (T) -> U): List<U> {
            return if (list.isEmpty()) {
                merge
            } else {
                map_(list.tail(), merge + f(list.head()), f)
            }
        }

        return map_(list, listOf(), f)
    }

    override fun run() {
        val beforeList = (1..20).toList()

        println(map(beforeList) { it * 5 })
    }

}
