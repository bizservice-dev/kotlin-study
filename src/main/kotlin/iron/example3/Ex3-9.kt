package iron.example3

import iron.Example

/**
 * 다음 함수를 커리한 함수로 반환하라
 */
class `Ex3-9` : Example {

    fun <A, B, C, D> func(a: A, b: B, c: C, d: D): String = "$a, $b, $c, $d"

    fun <A, B, C ,D> afterCurryFunc(): (A) -> (B) -> (C) -> (D) -> String = {
        a -> {
            b -> {
                c -> {
                    d -> "$a, $b, $c, $d"
                }
            }
        }
    }

    override fun run() {

        println(afterCurryFunc<String, String, String, String>()("a")("b")("c")("d"))
    }
}
