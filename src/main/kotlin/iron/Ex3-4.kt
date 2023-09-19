package iron

/**
 * 두 함수를 합성하는 함수 값을 만들라. 예를들어 3.2.8절 예제에서 봤던 square와 triple을
 * 함수 값으로 다시 정의하고 이 둘을 합성한 squareOfTriple을 만들어라
 */

typealias IntUnaryOp = (Int) -> Int

class `Ex3-4` : Example {

    val square: (Int) -> Int = { it * it }
    val triple: (Int) -> Int = { it * 3 }

    val compose: ((Int) -> Int) -> ((Int) -> Int) -> (Int) -> Int =
        { x ->
            { y ->
                { z -> x(y(z)) }
            }
        }
    val squareOfTripe = compose(square)(triple)

    // 해법
    val composeAnswer: (IntUnaryOp) -> (IntUnaryOp) -> IntUnaryOp = { x ->
        { y ->
            { z -> x(y(z)) }
        }
    }
    val squareOfTripleAnswer = compose(square)(triple)

    override fun run() {
        println(squareOfTripe(2))
        println(squareOfTripleAnswer(2))
    }
}
