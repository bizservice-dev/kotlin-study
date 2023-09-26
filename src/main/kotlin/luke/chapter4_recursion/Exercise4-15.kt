package luke.chapter4_recursion

import java.math.BigInteger

private fun fibToStringUntil(n: Int): String {
    tailrec fun doFib(i: Int, accumulated: List<BigInteger>): List<BigInteger> =
        when {
            i > n -> accumulated
            i > 1 -> doFib(i + 1, accumulated + (accumulated.last() + accumulated.secondLast()))
            else -> doFib(i + 1, accumulated + BigInteger.ONE)
        }

    tailrec fun doMakeString(list: List<BigInteger>, accumulated: String): String =
        when {
            list.isEmpty() -> accumulated
            accumulated.isEmpty() -> doMakeString(list.drop(1), "${list.first()}")
            else -> doMakeString(list.drop(1), "$accumulated,${list.first()}")
        }

    return doMakeString(doFib(0, emptyList()), "")
}

private fun <T> List<T>.secondLast(): T =
    if (this.size < 2)
        throw IllegalStateException("list size is under 2")
    else
        this[this.lastIndex - 1]

fun main() {
    println(fibToStringUntil(0)) // 1
    println(fibToStringUntil(1)) // 1,1
    println(fibToStringUntil(2)) // 1,1,2
    println(fibToStringUntil(3)) // 1,1,2,3
    println(fibToStringUntil(4)) // 1,1,2,3,5
    println(fibToStringUntil(5)) // 1,1,2,3,5,8
    println(fibToStringUntil(6)) // 1,1,2,3,5,8,13
    println(fibToStringUntil(7)) // 1,1,2,3,5,8,13,21
    println(fibToStringUntil(8)) // 1,1,2,3,5,8,13,21,34
}
