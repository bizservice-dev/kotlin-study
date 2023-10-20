package ellie

private sealed class AdvancedList<T> {
    abstract val length: Int

    abstract fun isEmpty(): Boolean

    // 연습문제 8-1
    abstract fun lengthMemoized(): Int

    private object Nil : AdvancedList<Nothing>() {
        override val length: Int = 0

        override fun isEmpty() = true
        override fun toString() = "[NIL]"

        override fun lengthMemoized(): Int = 0
    }

    private class Cons<T>(internal val head: T, internal val tail: AdvancedList<T>) : AdvancedList<T>() {
        override val length: Int = tail.length + 1

        override fun isEmpty() = false
        override fun toString() = "[]" // to do
        private tailrec fun toString(acc: String, list: AdvancedList<T>): String =
            when(list) {
                is Nil -> acc
                is Cons -> toString("$acc${list.head}, ", list.tail)
            }

        override fun lengthMemoized(): Int = length
    }

    companion object {
        operator fun <T> invoke(vararg az: T): AdvancedList<T> =
            az.foldRight(Nil as AdvancedList<T>) { a: T, list: AdvancedList<T> -> Cons(a, list) }

    }

}
