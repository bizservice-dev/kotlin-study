package ethan

sealed class List<out A> {
    abstract fun isEmpty(): Boolean
    abstract fun drop(n: Int): List<A>

    internal object Nil: List<Nothing>() {
        override fun isEmpty() = true
        override fun toString(): String = "[nil]"
        override fun drop(n: Int) = this
    }

    internal class Cons<A>(internal val head: A, internal val tail: List<A>): List<A>() {
        override fun isEmpty() = false
        override fun toString(): String = "[${toString("", this)}NIL]"
        private tailrec fun toString(acc: String, list: List<A>): String =
            when (list) {
                is Nil -> acc
                is Cons -> toString("$acc${list.head}, ", list.tail)
            }

        override fun drop(n: Int): List<A> {
            tailrec fun drop(n: Int, list: List<A>): List<A> =
                if (n <= 0) list
                else when(list) {
                        is Cons -> drop(n-1, list.tail)
                        is Nil -> list
                    }
            return drop(n, this)
        }
    }

    companion object {
        operator
        fun <A> invoke(vararg az: A): List<A> =
            az.foldRight(Nil as List<A>) { a: A, list: List<A> ->
                Cons(a, list)
            }

        private tailrec fun <A> dropWhile(list: List<A>, p: (A) -> Boolean): List<A> = when(list) {
            Nil -> list
            is Cons -> if (p(list.head)) dropWhile(list.tail, p) else list
        }

        private tailrec fun <A> reverse(acc: List<A>, list: List<A>): List<A> = when(list) {
            Nil -> acc
            is Cons -> reverse(acc.add(list.head), list.tail)
        }

        internal fun <A, B> foldRight(list: List<A>, identity: B, f: (A) -> (B) -> B): B =
            when(list) {
                Nil -> identity
                is Cons -> f(list.head)(foldRight(list.tail, identity, f))
            }

        internal tailrec fun <A, B> foldLeft(list: List<A>, acc: B, f: (B) -> (A) -> B): B =
            when(list) {
                Nil -> acc
                is Cons -> foldLeft(list.tail, f(acc)(list.head), f)
            }
    }

    fun add(el: @UnsafeVariance A): List<A> = Cons(el, this)

    fun setHead(newHead: @UnsafeVariance A): List<A> = when(this) {
        Nil -> throw IllegalStateException("no head for empty class")
        is Cons -> tail.add(newHead)
    }

    fun dropWhile(p: (A) -> Boolean): List<A> = dropWhile(this, p)

    fun init(): List<A> = reverse().drop(1).reverse()

    fun reverse(): List<A> = reverse(List.invoke(), this)

    fun <B> foldRight(identity: B, f:(A) -> (B) -> B): B = List.foldRight(this, identity, f)

    fun <B> foldLeft(identity: B, f: (B) -> (A) -> B): B = List.foldLeft(this, identity, f)

    fun length(): Int = foldRight(0) {{ it + 1 }}

    fun reverseV2(): List<A> = foldLeft(List.invoke()) { acc -> { acc.add(it) }}

    fun <B> foldRightViaFoldLeft(identity: B, f: (A) -> (B) -> B): B =
        this.reverse().foldLeft(identity) { acc -> { y -> f(y)(acc) }}
}

fun sum(ints: List<Int>): Int = when(ints) {
    List.Nil -> 0
    is List.Cons -> ints.head + sum(ints.tail)
}

fun product(doubles: List<Double>): Double = when(doubles) {
    List.Nil -> 1.0
    is List.Cons -> doubles.head * product(doubles.tail)
}


fun sumV2(list: List<Int>): Int = list.foldLeft(0, { acc -> { y -> acc + y}})

fun productV2(list: List<Double>): Double = list.foldLeft(1.0, { acc -> { y -> acc * y}})