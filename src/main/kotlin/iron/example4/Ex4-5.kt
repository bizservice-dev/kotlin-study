package iron.example4

import iron.Example

/**
 * sum, toString, makeString을 정의할 때 쓸 수 있는 꼬리 재귀 제네릭 함수를 만들어라
 */

class `Ex4-5` : Example {

    fun <T, U> foldLeft(list: List<T>, start: U, func: (U, T) -> U): U {
        tailrec fun foldLeft(list: List<T>, merge: U): U {
            return if (list.isEmpty()) {
                merge
            } else {
                foldLeft(list.tail(), func(merge, list.head()))
            }
        }

        return foldLeft(list, start)
    }


    fun sum(list: List<Int>): Int {
        return foldLeft(list, 0) {
            merge, head -> merge + head
        }
    }

    fun toString(list: List<Char>): String {
        return foldLeft(list, "") {
            merge, head -> "$merge$head"
        }
    }

    fun <T> makeString(list: List<T>, delim: String): String {
        return foldLeft(list, "") {
            merge, head ->
            if (merge.isEmpty()) {
                "$head"
            } else {
                "$merge$delim$head"
            }
        }
    }

    override fun run() {
        val intList = (0..10).toList()
        val charList = listOf('a','b','c','d')
        println(sum(intList))
        println(toString(charList))
        println(makeString(intList, ","))
    }
}
