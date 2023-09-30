package luke.chapter4_recursion

import java.math.BigInteger

private fun fibToString(n: Int): String {
    tailrec fun doFib(list: List<FibNumber>): List<FibNumber> =
        if (list.size > n)
            list
        else
            doFib(list + list.last().next())


    tailrec fun doMakeString(list: List<FibNumber>, accumulated: String): String =
        when {
            list.isEmpty() -> accumulated
            accumulated.isEmpty() -> doMakeString(list.drop(1), "${list.first()}")
            else -> doMakeString(list.drop(1), "$accumulated,${list.first()}")
        }

    return doMakeString(doFib(listOf(FibNumber.Zero)), "")
}

private class FibNumber(
    private val index: Int,
    private val prev: BigInteger,
    private val prevOfPrev: BigInteger
) {

    init {
        require(index >= 0)
    }

    private val value = prev + prevOfPrev

    fun next(): FibNumber =
        if (this == Zero)
            One
        else
            FibNumber(index = index + 1, prev = value, prevOfPrev = prev)

    override fun toString(): String {
        return value.toString()
    }

    companion object {

        val Zero: FibNumber = FibNumber(index = 0, prev = BigInteger.ONE, prevOfPrev = BigInteger.ZERO)
        val One: FibNumber = FibNumber(index = 1, prev = BigInteger.ONE, prevOfPrev = BigInteger.ZERO)

    }

}

fun main() {
    println(fibToString(0)) // 1
    println(fibToString(1)) // 1,1
    println(fibToString(2)) // 1,1,2
    println(fibToString(3)) // 1,1,2,3
    println(fibToString(4)) // 1,1,2,3,5
    println(fibToString(5)) // 1,1,2,3,5,8
    println(fibToString(6)) // 1,1,2,3,5,8,13
    println(fibToString(7)) // 1,1,2,3,5,8,13,21
    println(fibToString(8)) // 1,1,2,3,5,8,13,21,34
}
