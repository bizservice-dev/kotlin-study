package iron.example4

import iron.Example

/**
 * 정수 n을 인자로 받아서 피보나치 함수의 0번 항부터 n번 항까지 모든 항을 순서대로 콤마로 구분해서 나열한 문자열을 반환하는 함수를 작성하라
 */

// 공재귀인가 재귀인가?
class `Ex4-15` : Example {

    fun fiboToString(n: Int): String {
        tailrec fun fibo(acc: StringBuilder, firstFiboSum: Long, secondFiboSum: Long, x: Long): StringBuilder {
            return when(x) {
                0L -> acc.append(1L)
                1L -> acc.append(firstFiboSum + secondFiboSum)
                else -> fibo(acc.append(firstFiboSum + secondFiboSum).append(","), secondFiboSum, firstFiboSum+secondFiboSum, x-1)
            }
        }

        return fibo(StringBuilder(), 0, 1, n.toLong()).toString()
    }

    override fun run() {
        println(fiboToString(6))
    }
}
