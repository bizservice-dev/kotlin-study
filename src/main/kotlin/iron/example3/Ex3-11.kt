package iron.example3

/**
 * 커리한 함수의 두 인자의 순서를 뒤바꾼 새로운 함수를 반환하는 fun 함수를 작성하라
 */
class `Ex3-11` {

    fun <A, B, C> reserveCurryFunc(curryFunc: (A) -> (B) -> C): (B) -> (A) -> C = {
        b -> {
            a -> curryFunc(a)(b)
        }
    }
}
