package iron

/**
 * (A, B)에서 C로 가는 함수를 커리한 함수로 바꾸는 함수를 작성하라
 */
class `Ex3-10` : Example {

    fun <T, U, V> changeCurryFunc(targetFunc: (T, U) -> V): (T) -> (U) -> V = {
            t -> {
                u -> targetFunc(t, u)
            }
    }

    override fun run() {
        val targetFunc: (Int, Int) -> Int = { x, y -> x * y}

        val changeFunc = changeCurryFunc(targetFunc)

        println(targetFunc(5, 3))
        println(changeFunc(5)(3))
    }

}
