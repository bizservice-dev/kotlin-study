package luke.chapter3_function

private fun <T, U, V> higherAndThen(): ((T) -> U) -> ((U) -> V) -> ((T) -> V) = { f2 ->
    { f1 ->
        { x ->
            f1(f2(x))
        }
    }
}

fun main() {
    val squareOfTriple = higherAndThen<Int, Int, Int>()(triple)(square)

    println(squareOfTriple(2)) // 36
}
