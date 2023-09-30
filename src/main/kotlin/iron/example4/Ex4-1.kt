package iron.example4

import iron.Example

/**
 * 양의 정수에 대해 작동하는 공재귀 add 함수를 작성하라. add 구현에는 +나 -연산자를 사용하지 않고 다음 두 함수만 사용한다
 */

class `Ex4-1` : Example {

    fun inc(n: Int) = n + 1
    fun dec(n: Int) = n - 1

    // answer
    tailrec fun add(a: Int, b: Int): Int {
        return if (b == 0) {
            a
        } else {
            add(inc(a), dec(b))
        }
    }

    override fun run() {
        // tailrec을 붙이지 않으면 stackOverFlowError
        println(add(50000, 30000))
    }

}
