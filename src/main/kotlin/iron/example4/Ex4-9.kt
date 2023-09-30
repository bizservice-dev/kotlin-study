package iron.example4

import iron.Example

/**
 * 시작 값, 끝 값, x -> x+1 이라는 함수로 리스트를 생성하는 함수의 루프 기반 구현을 작성하라.
 * 이 함수에 range라는 이름을 붙여라.
 */

class `Ex4-9` : Example {

    fun range(start: Int, end: Int): List<Int> {
        val list = mutableListOf<Int>()
        for (i in start..end) {
            list.add(i+1)
        }

        return list
    }


    override fun run() {
        println(range(10, 20))
    }
}
