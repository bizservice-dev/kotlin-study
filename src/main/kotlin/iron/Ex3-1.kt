package iron

/**
 * Int에서 Int로 가는 함수의 합성을 허용하는 compose 함수를 작성하라 (fun을 사용해 정의하라)
 */
class `Ex3-1` : Example {

    fun square(n: Int) = n * n

    fun triple(n: Int) = n * 3

    fun compose(f: (Int) -> Int, g: (Int) -> Int): (Int) -> Int = {f(g(it))}


    override fun run() {
        val squareTriple = compose( { square(it) }, { triple(it) })
        println(squareTriple(3))
    }
}
