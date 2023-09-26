package luke.chapter4_recursion

import java.math.BigInteger
import kotlin.system.measureTimeMillis

/*
f(0) = 1
f(1) = 1
f(n) = f(n - 1) + f(n - 2)
 */
private fun fib(n: Int): BigInteger {
    tailrec fun doFibonacci(i: Int, last: BigInteger, secondLast: BigInteger): BigInteger {
        return when {
            i > n -> last
            i > 0 -> doFibonacci(i + 1, last + secondLast, last)
            else -> doFibonacci(i + 1, BigInteger.ONE, BigInteger.ZERO)
        }
    }

    return doFibonacci(0, BigInteger.ONE, BigInteger.ZERO)
}

private fun <T> List<T>.secondLast(): T =
    if (size < 2)
        throw IllegalStateException("list size is under 2")
    else
        this[lastIndex - 1]

fun main() {
    println(fib(0)) // 1
    println(fib(1)) // 1
    println(fib(2)) // 2
    println(fib(3)) // 3
    println(fib(4)) // 5
    println(fib(5)) // 8
    println(fib(6)) // 13
    println(fib(7)) // 21
    println(fib(8)) // 34

    val time = measureTimeMillis { fib(10000) }
    println("fib(10000) time : $time ms") // 4 ms

    // TODO : 실행시간 기록해보기
    val timeByDoublyRecursive = measureTimeMillis { fibonacciByDoublyRecursive(40) }
    println("fibonacciByDoublyRecursive(40) time : $timeByDoublyRecursive ms") // 1145 ms
}

private fun fibonacciByDoublyRecursive(number: Int): BigInteger =
    if (number == 0 || number == 1)
        BigInteger.ONE
    else
        fibonacciByDoublyRecursive(number - 1) + fibonacciByDoublyRecursive(number - 2)
