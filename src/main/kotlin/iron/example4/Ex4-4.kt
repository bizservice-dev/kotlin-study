package iron.example4

import iron.Example
import java.lang.IllegalArgumentException

/**
 * makeString 함수의 꼬리재귀 버전을 만들어라
 */

class `Ex4-4` : Example {

    fun <T> makeString(list: List<T>, delim: String): String {
        tailrec fun <T> makeString(merge: String, list: List<T>): String {
            return if (list.isEmpty()) {
                merge
            } else {
                makeString("$merge$delim${list.head()}", list.tail())
            }
        }

        return makeString("${list.head()}", list.tail())
    }

    override fun run() {
        val intList = (1..10).toList()

        println(makeString(intList, ","))
    }
}

fun <T> List<T>.head(): T =
    if (this.isEmpty()) {
        throw IllegalArgumentException("head called on empty list")
    } else {
        this[0]
    }

fun <T> List<T>.tail(): List<T> =
    if (this.isEmpty()) {
        throw IllegalArgumentException("tail called on empty list")
    } else {
        this.drop(1)
    }
