package luke.chapter4_recursion

fun inc(n: Int): Int = n + 1
fun dec(n: Int): Int = n - 1

private tailrec fun addByCoRecursion(a: Int, b: Int): Int {
    return when {
        b > 0 -> addByCoRecursion(inc(a), dec(b))
        b < 0 -> addByCoRecursion(dec(a), inc(b))
        else -> a
    }
}

fun main() {
    println(addByCoRecursion(4, 6)) // 10
    println(addByCoRecursion(4, -6)) // -2
    println(addByCoRecursion(4, 0)) // 4
    println(addByCoRecursion(0, 0)) // 0
    println(addByCoRecursion(0, 1)) // 1
    println(addByCoRecursion(0, -1)) // -1
    println(addByCoRecursion(-2, 0)) // -2
    println(addByCoRecursion(-2, 1)) // -1
    println(addByCoRecursion(-2, -1)) // -3
}
