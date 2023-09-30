package iron.example4

import iron.Example

/**
 * 접기 연산 (foldLeft, foldRight)을 사용해 reverse 함수를 만들어라
 */

class `Ex4-7` : Example {
    private val exampleFunc = `Ex4-5`()

    fun <T> reverse(list: List<T>): List<T> {
        return exampleFunc.foldLeft(list, listOf()) {
            addList, item -> prepend(addList, item)
        }

        // 함수 참조를 사용해서 아래와 같이 변경도 가능
        // return exampleFunc.foldLeft(list, listOf, ::prepend)
    }

    fun <T> prepend(addList: List<T>, item: T): List<T> {
        return listOf(item) + addList
    }


    override fun run() {
        val reverseIntList = (10 downTo 0).toList()

        println(reverse(reverseIntList))
    }
}
