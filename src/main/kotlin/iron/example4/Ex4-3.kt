package iron.example4

import iron.Example

/**
 * 꼬리 재귀 버전의 피보나치 함수를 만들라
 */



class `Ex4-3` : Example {

    fun fibonacci(n: Long): Long {
        tailrec fun fibonacciSupport(firstFiboSum: Long, secondFiboSum: Long, x: Long): Long {
            return when (x) {
                0L -> 1L
                1L -> firstFiboSum + secondFiboSum
                else -> fibonacciSupport(secondFiboSum, firstFiboSum + secondFiboSum, x-1)
            }
        }

        return fibonacciSupport(0L, 1L, n)
    }

    override fun run() {
        (0..30).toList().forEach {
            println("pibo $it = ${fibonacci(it.toLong())}")
        }
    }
}
