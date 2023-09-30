package iron.example4

import iron.Example

class `Ex4-12` : Example {

    fun range(start: Int, end: Int): List<Int> {
        return if (start >= end) {
            listOf()
        } else {
            prepend(range(start+1, end), start)
        }
    }

    fun <T> prepend(list: List<T>, item: T): List<T> {
        return listOf(item) + list
    }

    override fun run() {
        println(range(1, 10))
    }
}
