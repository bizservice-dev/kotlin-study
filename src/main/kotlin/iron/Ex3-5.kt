package iron

/**
 * 다형적 compose 함수를 작성하라
 * tip : 3-4에서 (Int) -> Int 만 가능했던 부분에서 다형성을 적용하기
 */
class `Ex3-5` : Example {

    val square: (Int) -> Int = { it * it }
    val triple: (Int) -> Int = { it * 3 }

    fun <T, U, V> compose(): ((U) -> V) -> ((T) -> U) -> (T) -> V = {
        f -> {
            g -> {
                h -> f(g(h))
            }
        }
    }

    val polymorphCompose = compose<Int, Int, Int>()(square)(triple)


    override fun run() {
        println(polymorphCompose(3))
    }
}
