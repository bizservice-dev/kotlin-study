package luke.chapter3_function

// 연습문제 3.1
private fun compose(
    fun1: (Int) -> Int,
    fun2: (Int) -> Int
): (Int) -> Int = {
    fun1(fun2(it))
}

private fun square(n: Int) = n * n

private fun triple(n: Int) = n * 3

fun main() {
    println(compose(::square, ::triple)(2)) // 36
}
