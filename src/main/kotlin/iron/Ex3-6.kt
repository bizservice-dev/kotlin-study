package iron

/**
 * 함수를 합성하되 적용 순서가 반대인 higherAndThen 함수를 정의하라.
 */

class `Ex3-6` : Example {
    val square: (Int) -> Int = { it * it }
    val triple: (Int) -> Int = { it * 3 }

    fun <T, U, V> higherAndThen(): ((T) -> U) -> ((U) -> V) -> (T) -> V= {
        g ->  {
            f -> {
                h : T -> f(g(h))
            }
        }
    }


    val squareOfTripe = higherAndThen<Int, Int, Int>()(triple)(square)

    override fun run() {
        println(squareOfTripe(3))
    }
}
