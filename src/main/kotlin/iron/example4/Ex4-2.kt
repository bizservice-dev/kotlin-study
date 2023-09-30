package iron.example4

import iron.Example

/**
 * 재귀적 계승 함수 값을 작성하라.
 */

class `Ex4-2` : Example {
    val factorial: (Int) -> Int by lazy {
        {
            if (it == 0) 1
            else it * factorial(it - 1)
        }

        /**
         * 알게된 점
         * { x -> ... } 와 같이 사용하는 경우 타입을 붙여주지 않으면 코틀린에서 타입 추론이 불가능하다
         */
    }

    override fun run() {
        println( factorial(10) )
    }
}
