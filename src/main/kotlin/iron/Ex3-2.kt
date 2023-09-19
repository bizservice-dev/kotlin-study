package iron

/**
 * compose 함수를 타입 파라미터를 사용하는 다형적 함수로 만들라.
 */
class `Ex3-2` : Example {
    fun square(n: Int) = n * n

    fun triple(n: Int) = n * 3

    // 작성한 답
//    fun <T> compose(f: (T) -> T, g: (T) -> T): (T) -> T = { f(g(it)) }

    // answer
    fun <T, U, V> compose(f: (U) -> V, g: (T) -> U): (T) -> V = { f(g(it)) }

    override fun run() {
        val squareTripe: (Int) -> Int = compose( { square(it) }, { triple(it) })

        println(squareTripe(3))
    }
}
