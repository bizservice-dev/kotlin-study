package iron

/**
 * 두 Int 값을 더하는 함수를 작성하라
 */
typealias IntBinOp = (Int) -> (Int) -> Int

class `Ex3-3` : Example {

    // 작성한 답
    val add: (Int) -> (Int) -> Int = { x -> { y -> x + y } }

    // 해법
    val addAns: IntBinOp = { x -> { y -> x + y } }
    val multAns: IntBinOp = { x -> { y -> x * y } }


    override fun run() {
        println(add(10)(3))
        println(addAns(10)(3))
    }
}
