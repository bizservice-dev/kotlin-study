package luke.chapter4_recursion

private val factorial: (Int) -> Int by lazy {
    { n: Int ->
        if (n <= 1) {
            n
        } else {
            n * factorial(n - 1)
        }
    }
}

private val factorialByCoRecursion: (Int) -> Int = { n ->
    tailrec fun factorialHelper(start: Int, accumulation: Int, end: Int): Int {
        return if (start > end)
            accumulation
        else
            factorialHelper(start = start + 1, accumulation = accumulation * start, end = end)
    }

    if (n > 0) factorialHelper(start = 1, accumulation = 1, end = n) else 1
}

fun main() {
    println(factorialByCoRecursion(0)) // 1
    println(factorialByCoRecursion(1)) // 1
    println(factorialByCoRecursion(2)) // 2
    println(factorialByCoRecursion(3)) // 6
    println(factorialByCoRecursion(4)) // 24
    println(factorialByCoRecursion(5)) // 120
    println(factorialByCoRecursion(6)) // 720
}
