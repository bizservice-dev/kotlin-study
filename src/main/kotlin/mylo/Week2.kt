package mylo

import java.math.BigInteger

fun main() {
    println("4-1 : ${Week2.add(3,5)}")
    println("4-2 : ${Week2.factorial(4)}")
    print("4-3 : ")
    (0 until 10).forEach { print("${Week2.fibonacci(it)} ")}
    println()
    println("4-4 : ${Week2.makeString(listOf("1", "2", "3"), "-")}")
    println("4-5 : ${Week2.sumFoldLeft(listOf(1, 2, 3, 4))}, ${Week2.toStringFoldLeft(listOf("12", "34", "56"))}, " +
            Week2.makeStringFoldLeft(listOf("1", "2", "3"), "-")
    )
    println("4-6 : ${Week2.toStringFoldRight(listOf('1', '2'))}")
    println("4-7 : ${Week2.reverse(listOf(3, 2, 1))}")
    println("4-8 : ${Week2.reverse2(listOf(3, 2, 1))}")
    println("4-9 : ${Week2.range(0, 10)}")
    println("4-15 : ${Week2.fibonacciResult(10)}")
    println("4-18 : ${Week2.fibo(10)}")

}

object Week2 {
    fun inc(n: Int) = n + 1
    fun dec(n: Int) = n - 1
    fun <T> List<T>.head(): T =
        if (this.isEmpty())
            throw IllegalArgumentException("head called on Empty List")
        else
            this[0]
    fun <T> List<T>.tail(): List<T> =
        if (this.isEmpty())
            throw IllegalArgumentException("tail called on Empty List")
        else
            this.drop(1)
    fun prepend(c: Char, s: String): String = "$c$s"


    // 4-1


    tailrec fun add(a:Int, b:Int): Int =
        if (b > 0) add(inc(a), dec(b)) else a

    // 4-2
    val factorial: (Int) -> Int by lazy { { n: Int -> if (n<2) n else n * factorial(n-1) } }

    // 4-3
    fun fibonacci(n: Int): BigInteger {
        tailrec fun fibonacci(prev2: BigInteger, prev1: BigInteger, n: Int): BigInteger =
            if ( n == 0 )
                prev1
            else
                fibonacci(prev1, prev1 + prev2, n - 1)
        return fibonacci(BigInteger.ZERO, BigInteger.ONE, n)
    }

    // 4-4
    fun <T> makeString(list: List<T>, delim: String): String {
        tailrec fun makeString_(list: List<T>, tmp: String): String =
            when {
                list.isEmpty() -> tmp
                tmp.isEmpty() -> makeString_(list.tail(), "${list.head()}")
                else -> makeString_(list.tail(), "$tmp$delim${list.head()}")
            }
        return makeString_(list, "")
    }

    // 4-5
    fun <T, U> foldLeft(list: List<T>, d: U, f: (T, U) -> U): U {
        tailrec fun foldLeft_(list: List<T>, tmp: U): U =
            if (list.isEmpty())
                tmp
            else
                foldLeft_(list.tail(), f(list.head(), tmp))
        return foldLeft_(list, d)
    }

    fun sumFoldLeft(list: List<Int>): Int = foldLeft(list, 0, Int::plus)
    fun toStringFoldLeft(list: List<String>): String = foldLeft(list, "") { t, u -> u + t}
    fun <T> makeStringFoldLeft(list: List<T>, delim: String) : String = foldLeft(list, "") {
        t, u -> if(u.isEmpty()) "$t" else "$u$delim$t"
    }

    // 4-6
    fun <T, U> foldRight(list: List<T>, identity: U, f: (T, U) -> U): U =
        if (list.isEmpty())
            identity
        else
            f(list.head(), foldRight(list.tail(), identity, f))

    fun toStringFoldRight(list: List<Char>): String =
        foldRight(list, "") { t, u -> prepend(t, u)}

    // 4-7
    fun <T> prependList(t: T, list: List<T>): List<T> = listOf(t) + list
    fun <T> reverse(list: List<T>): List<T> = foldLeft(list, listOf(), ::prependList)

    // 4-8
    fun <T> prependList2(elem: T, list: List<T>): List<T> = foldLeft(list, listOf(elem)) { t, u -> u + t}
    fun <T> reverse2(list: List<T>): List<T> = foldLeft(list, listOf(), ::prependList2)

    // 4-9
    fun range(start: Int, end: Int): List<Int> {
        val result: MutableList<Int> = mutableListOf()
        var idx = start
        while (idx < end) {
            result.add(idx)
            idx++
        }
        return result
    }

    // 4-10
    fun <T> unfold(seed: T, f: (T) -> T, p: (T) -> Boolean): List<T> {
        val result: MutableList<T> = mutableListOf()
        var curr = seed
        while (p(curr)) {
            result.add(curr)
            curr = f(curr)
        }
        return result
    }

    // 4-11
    fun rangeWithUnfold(start: Int, end: Int): List<Int> = unfold(start, { it + 1 }) { it < end }

    // 4-12
    fun rangeRecursion(start: Int, end: Int): List<Int> =
        if ( start >= end )
            listOf()
        else
            prependList(start, rangeRecursion(start + 1, end))

    // 4-13
    fun <T> unfoldRecursion(seed: T, f: (T) -> T, p: (T) -> Boolean): List<T> =
        if (!p(seed))
            listOf()
        else
            prependList(seed, unfoldRecursion(f(seed), f, p))

    // 4-14
    fun <T> unfoldCoRecursion(seed: T, f: (T) -> T, p: (T) -> Boolean): List<T> {
        tailrec fun unfoldCoRecursion_(tmp: List<T>, currSeed: T): List<T> =
            if (!p(currSeed))
                tmp
            else
                unfoldCoRecursion_(tmp + currSeed, f(currSeed))

        return unfoldCoRecursion_(listOf(), seed)
    }

    // 4-15
    fun fibonacciResult(n: Int): String {
        tailrec fun fibonacci(tmp: List<BigInteger>, prev2: BigInteger, prev1: BigInteger, n: Int): List<BigInteger> =
            if ( n == 0 )
                tmp
            else
                fibonacci(tmp + prev1, prev1, prev1 + prev2, n - 1)

        val result = fibonacci(listOf(), BigInteger.ZERO, BigInteger.ONE, n)
        return makeStringFoldLeft(result, ", ")
    }

    // 4-16
    fun <T> unfoldIterate(seed: T, n: Int, f: (T) -> T): List<T> {
        tailrec fun unfoldIterate_(tmp: List<T>, currSeed: T): List<T> =
            if (tmp.size >= n)
                tmp
            else
                unfoldIterate_(tmp + currSeed, f(currSeed))
        return unfoldIterate_(listOf(), seed)
    }

    // 4-17
    fun <T, U> mapList(list: List<T>, f: (T) -> U): List<U> {
        tailrec fun mapList_(tmp: List<U>, list: List<T>): List<U> =
            if (list.isEmpty())
                tmp
            else
                mapList_(tmp + f(list.head()), list.tail())
        return mapList_(listOf(), list)
    }
    fun <T, U> mapListWithFoldLeft(list: List<T>, f: (T) -> U): List<U> =
        foldLeft(list, listOf()) { t, u -> u + f(t)}

    // 4-18
    fun fibo(n: Int): String {
        val list = unfoldIterate(Pair(BigInteger.ZERO, BigInteger.ONE), n) { p ->
            Pair(
                p.second,
                p.first + p.second
            )
        }
        return makeString(list, ", ")
    }
}



