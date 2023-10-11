package ellie

private sealed class List<T> {
    abstract fun isEmpty(): Boolean

    private object Nil : List<Nothing>() {
        override fun isEmpty() = true
        override fun toString() = "[NIL]"
    }

    private class Cons<T>(internal val head: T, internal val tail: List<T>) : List<T>() {
        override fun isEmpty() = false
        override fun toString() = "[]" // to do
        private tailrec fun toString(acc: String, list: List<T>): String =
            when(list) {
                is Nil -> acc
                is Cons -> toString("$acc${list.head}, ", list.tail)
            }
    }

    companion object {
        operator fun <T> invoke(vararg az: T): List<T> =
            az.foldRight(Nil as List<T>) { a: T, list: List<T> -> Cons(a, list) }

        // 연습문제 5-4
        private tailrec fun <T> dropWhile(list: List<T>, p: (T) -> Boolean): List<T> = when(list) {
            is Nil -> list
            is Cons -> if (p(list.head)) dropWhile(list.tail, p) else list
        }

        // 연습문제 5-5
        private tailrec fun <T> reverse(acc: List<T>, list: List<T>): List<T> =
            when(list) {
                Nil -> list
                is Cons -> reverse(acc.cons(list.head), list.tail)
            }

    }

    // 연습문제 5-1
    // 리스트의 맨 앞에 원소를 추가하는 cons 함수를 구현하라
    fun cons(element: T): List<T> = Cons(element, this)

    // 연습문제 5-2
    // List 의 첫 번째 원소를 새로운 값으로 바꾼 리스트를 반환하는 setHead 함수를 구현하라.
    fun setHead(element: T): List<T> = when(this) {
        Nil -> throw IllegalArgumentException("setHead called on an empty list")
        is Cons -> tail.cons(element)
    }

    // 연습문제 5-3
    // 맨 앞의 n 개의 원소를 제거하는 drop 함수를 구현
    fun drop(n: Int): List<T> {
        tailrec fun drop(n: Int, list: List<T>): List<T> =
            if (n <= 0)
                list
            else when(list) {
                is Nil -> list
                is Cons -> drop(n-1, list.tail)
            }
        return drop(n, this)
    }

    // 연습문제 5-4
    // 리스트의 맨 앞에서부터 원소에 대한 조건이 성립하는 동안에만 원소를 제거하는 함수 구현
    fun dropWhile(p: (T) -> Boolean): List<T> = dropWhile(this, p)

    // 연습문제 5-5
    // 리스트의 마지막 원소를 제거하는 함수 작성
    fun reverse(): List<T> = reverse(List.invoke(), this)

    fun init(): List<T> = reverse().drop(1).reverse()
}
