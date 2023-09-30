package iron.example4

import iron.Example


class `Ex4-6` : Example {

    // 해당 함수는 tailrec을 사용할 수 없다.
    fun <T, U> foldRight(list: List<T>, identity: U, prepend: (T, U) -> U): U {
        return if (list.isEmpty()) {
            identity
        } else {
            prepend(list.head(), foldRight(list.tail(), identity, prepend))
        }
    }

    fun toString(list: List<Char>): String {
        return foldRight(list, "") {
            c, s -> "$c$s"
        }
    }


    override fun run() {
        val charList = listOf('a', 'b', 'c', 'd')
        println(toString(charList))
    }
}
