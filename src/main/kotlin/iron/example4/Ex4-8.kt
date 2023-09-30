package iron.example4

import iron.Example

/**
 * 두 리스트를 연결하지 않고 리스트 뒤에 원소를 덧붙이는 + 를 사용해 reverse함수를 만들어라
 */


class `Ex4-8` : Example {
    private val exampleFunc = `Ex4-5`()

    fun <T> reverse(list: List<T>): List<T> {
        return exampleFunc.foldLeft(list, listOf(), ::prepend)
    }

    fun <T> prepend(addList: List<T>, item: T): List<T> {
        return exampleFunc.foldLeft(addList, listOf(item)) { list, item -> list + item}
    }

    override fun run() {
        val intList = (0..10).toList()
        println(reverse(intList))
    }
}
