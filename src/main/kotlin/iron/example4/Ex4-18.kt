package iron.example4

import iron.Example

/**
 * 피보나치 수열의 첫 n항을 표현하는 문자열을 반환하는 피보나치 함수의 공재귀 버전을 정의하라.
 */

class `Ex4-18` : Example {
    val exampleMap = `Ex4-17`()
    val exampleIterate = `Ex4-16`()

    fun nthFiboToString(n: Int): String {
        val f = { (a, b): Pair<Long, Long> -> Pair(b, a+b)}
        val pairFiboList = exampleIterate.iterate(Pair(1L, 1L), f, n)
        val fiboListToString = exampleMap.map(pairFiboList) { it.first }

        return fiboListToString.joinToString(",")
    }

    override fun run() {
        println(nthFiboToString(6))
    }
}
