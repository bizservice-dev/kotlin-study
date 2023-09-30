package ellie

import java.math.BigInteger

// 연습문제 4-1
private fun inc(n: Int) = n + 1
private fun dec(n: Int) = n - 1

private fun add(a: Int, b: Int): Int {
    tailrec fun add(acc: Int, i: Int):Int = if (i == 0) acc else add(inc(acc), dec(i))

    return add(a, b)
}

// 연습문제 4-2
// 풀이 1 (Int) -> BigInteger
private val calculator = object {
    val factorial:(Int) -> BigInteger by lazy { { n: Int ->
        if (n == 0) BigInteger.ONE
        else BigInteger.valueOf(n.toLong()) * factorial(n - 1)
    } }
}

// 풀이 2 익명함수 & 꼬리재귀함수
private val factorial = fun(n: Int): BigInteger {
    tailrec fun factorial(acc: BigInteger, i: Int): BigInteger =
        if (i == 0) acc else factorial(acc * BigInteger.valueOf(i.toLong()), i - 1)

    return factorial(BigInteger.ONE, n)
}

// 연습문제 4-3
private fun fib(n: Int): BigInteger {
    tailrec fun fib(val1: BigInteger, val2: BigInteger, i: Int): BigInteger =
        if (i == n) val1 + val2 else fib(val2, val1 + val2, i + 1)

    return if (n == 0) BigInteger.ONE else fib(BigInteger.ZERO, BigInteger.ONE, 1)
}

// 연습문제 4-4
private fun <T> List<T>.head(): T =
    if (this.isEmpty())
        throw IllegalArgumentException("head called on empty list")
    else
        this[0]

private fun <T> List<T>.tail(): List<T> =
    if (this.isEmpty())
        throw IllegalArgumentException("tail called on empty list")
    else
        this.drop(1)

private fun <T> makeString(list: List<T>, delim: String): String {
    tailrec fun makeString_(list: List<T>, acc: String): String = when {
        list.isEmpty() -> acc
        acc.isEmpty() -> makeString_(list.tail(), "${list.head()}")
        else -> makeString_(list.tail(), "$acc$delim${list.head()}")
    }
    return makeString_(list, "")
}

// 연습문제 4-5
// 리스트에 대한 꼬리 재귀 제네릭 함수를 만들어라. (sum, toString, makeString 을 정의할 때 사용할 수 있게)
private fun <T, U> foldLeft(list: List<T>, initVal: U, func: (U, T) -> U): U {
    tailrec fun foldLeft(list: List<T>, acc: U): U =
        if (list.isEmpty()) acc
        else foldLeft(list.tail(), func(acc, list.head()))

    return foldLeft(list, initVal)
}

private fun sumByFoldLeft(list: List<Int>) = foldLeft(list, 0) { acc, num -> acc + num }
private fun toStringByFoldLeft(list: List<Char>) = foldLeft(list, "") { acc, c -> acc + c }
private fun <T> makeStringByFoldLeft(list: List<T>, delim: String) = foldLeft(list, "") { acc, t -> if (acc.isEmpty()) "$t" else "$acc$delim$t" }


// 연습문제 4-6
// 추상화된 재귀함수를 구현하여 toString 함수을 구현하라
private fun prepend(c: Char, s: String) = c + s

private fun <T, U> foldRight(list: List<T>, identity: U, f: (T, U) -> U): U =
    if (list.isEmpty())
        identity
    else
        f(list.head(), foldRight(list.tail(), identity, f))

private fun toStringByFoldRight(list: List<Char>) = foldRight(list, "") { c, s -> prepend(c, s)}


// 연습문제 4-7
private fun <T> prepend(list: List<T>, element: T): List<T> = listOf(element) + list


private fun <T> reverse(list: List<T>): List<T> = foldLeft(list, listOf(), ::prepend)


// 연습문제 4-8
private fun <T> prependByFoldLeft(list: List<T>, element: T): List<T> = foldLeft(list, listOf(element)) { l, e -> l + e }

private fun <T> reverse2(list: List<T>): List<T> = foldLeft(list, listOf(), ::prependByFoldLeft)


// 연습문제 4-9
// range 함수의 루프기반 구현
private fun range(start: Int, end: Int): List<Int> {
    val result = mutableListOf<Int>()
    var index = start
    while (index < end) {
        result.add(index)
        index++
    }
    return result
}


// 연습문제 4-10
// 임의의 타입과 조거에 대해 작동하는 range와 비슷한 함수를 만들어라.
private fun <T> unfold(seed: T, f: (T) -> T, p: (T) -> Boolean): List<T> {
    val result = mutableListOf<T>()
    var element = seed
    while (p(element)) {
        result.add(element)
        element = f(element)
    }
    return result
}


// 연습문제 4-11
// unfold로 range를 구현하라.
private fun rangeByUnfold(start: Int, end: Int) = unfold(start, { it + 1 }) { it < end }


// 연습문제 4-12
// range의 재귀 버전을 작성하라
private fun recursiveRange(start: Int, end: Int): List<Int> = if (end <= start) listOf() else prepend(recursiveRange(start + 1, end), start)


// 연습문제 4-13
// unfold의 재귀 버전을 작성하라
private fun <T> recursiveUnfold(seed: T, f: (T) -> T, p: (T) -> Boolean): List<T> =
    if (!p(seed)) listOf() else prepend(recursiveUnfold(f(seed), f, p), seed)

// 연습문제 4-14
// 재귀적 unfold의 꼬리 재귀 버전을 만들 수 있을까?
private fun <T> unfoldWithTCE(seed: T, f: (T) -> T, p: (T) -> Boolean): List<T> {
    tailrec fun unfoldWithTCE(acc: List<T>, seed: T): List<T> = if (p(seed)) unfoldWithTCE(acc + seed, f(seed)) else acc

    return unfoldWithTCE(listOf(), seed)
}


// 연습문제 4-15
// 정수 n을 인자로 받아서 피보나치 함수의 0번 항부터 n번 항까지 모든 항을 순서대로 콤마로 구분해서 나열한 문자열을 반환하는 함수를 작성하라
fun fibString(n: Int): String {
    tailrec fun fib(acc: List<BigInteger>, accPreviousStep: BigInteger, accThisStep: BigInteger, index: Int): List<BigInteger> {
        return when (index) {
            0 -> acc
            1 -> acc + (accPreviousStep + accThisStep)
            else -> fib(acc + (accPreviousStep + accThisStep), accThisStep, accPreviousStep + accThisStep, index - 1)
        }
    }
    val fibList = fib(listOf(), BigInteger.ZERO, BigInteger.ONE, n)
    return makeString(fibList, ", ")
}


// 연습문제 4-16
// unfold 처럼 작동하는 iterate 함수를 작성하라. 단, 주어진 횟수만큼 자신을 재귀 호출하도록 구현한다.
private fun <T> iterate(seed: T, f: (T) -> T, n: Int): List<T> {
    tailrec fun iterate(acc: List<T>, seed: T): List<T> = if (acc.size < n) iterate(acc + seed, f(seed)) else acc

    return iterate(listOf(), seed)
}

// 연습문제 4-17
// (T) -> U 타입의 함수를 List<T> 타입 리스트의 모든 원소에 적용해 만든 List<U> 타입의 리스트를 돌려주는 map 함수 구현
private fun <T, U> map(list: List<T>, f: (T) -> U): List<U> = foldLeft(list, listOf()) { lst, e -> lst + f(e) }

// 연습문제 4-18
// n 항까지의 피보나치 수열 문자열을 반환하는 공재귀 함수를 구현하라
private fun fibCorecursive(n: Int): String {
    val first = BigInteger.ZERO to BigInteger.ONE
    val getNextPair = { x: Pair<BigInteger, BigInteger> -> Pair(x.second, x.first + x.second) }
    val pairList = iterate(first, getNextPair, n + 1)
    val numList = map(pairList) { it.second }.tail()
    return makeString(numList, ", ")
}

fun main() {
    // 연습문제 4-1
    println("연습문제 4-1 결과: add(5, 3) = ${add(5,3)}")

    // 연습문제 4-2
    println("연습문제 4-2-1 결과: 5! = ${calculator.factorial(5)}")
    println("연습문제 4-2-2 익명함수 & 꼬리재귀함수 풀이 결과: 5! = ${factorial(5)}")

    // 연습문제 4-3
    println("연습문제 4-3 결과: fib(9) = ${fib(9)}") // expected: 55

    // 연습문제 4-4
    val charList = listOf('a', 'b', 'c')
    println("연습문제 4-4 결과: makeString($charList, :) = ${makeString(charList, ":")}")

    // 연습문제 4-5
    val numList = listOf(1, 2, 3, 4, 5)
    println("연습문제 4-5-1 결과: sumByFoldLeft(list) = ${sumByFoldLeft(numList)}")
    println("연습문제 4-5-2 결과: toStringByFoldLeft(list) = ${toStringByFoldLeft(charList)}")
    println("연습문제 4-5-3 결과: makeStringByFoldLeft(list) = ${makeStringByFoldLeft(charList, ":")}")

    // 연습문제 4-6
    println("연습문제 4-6 결과: toStringByFoldRight(list) = ${toStringByFoldRight(charList)}")

    // 연습문제 4-15
    println("연습문제 4-15 결과: fibString(9) = ${fibString(9)}")

    // 연습문제 4-16
    println("연습문제 4-16 결과: iterate(0, {it + 2}, 5) = ${iterate(0, {it + 2}, 5)}") // expected: [0, 2, 4, 6, 8]

    // 연습문제 4-17
    println("연습문제 4-17 결과: map(numList) { it + 10 } = ${map(numList) { it + 10 }}")

    // 연습문제 4-18
    println("연습문제 4-18 결과: fibCorecursive(9) = ${fibCorecursive(9)}")
}