package luke.chapter3_function

/*
- f1(x) : U -> V
- f2(x) : T -> U
- g(x) = f2(f1(x)) : T -> V
 */
private fun <T, U, V> compose(): ((U) -> V) -> ((T) -> U) -> ((T) -> V) = { f2 ->
    { f1 ->
        { x ->
            f2(f1(x))
        }
    }
}

fun main() {
    val squareOfTriple = compose<Int, Int, Int>()(square)(triple)

    println(squareOfTriple(2)) // 36
}
