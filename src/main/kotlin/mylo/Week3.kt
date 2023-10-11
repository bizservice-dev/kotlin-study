package mylo

sealed class MyloList<out A> {
    abstract fun isEmpty(): Boolean

    internal object Nil : MyloList<Nothing>() {
        override fun isEmpty(): Boolean = true
        override fun toString(): String = "[NIL]"
    }

    internal class Cons<A>(internal val head: A, internal val tail: MyloList<A>) : MyloList<A>() {
        override fun isEmpty(): Boolean = false
        override fun toString(): String = "[${toString("", this)}NIL]"
        private tailrec fun toString(acc: String, list: MyloList<A>): String =
            when(list) {
                is Nil -> acc
                is Cons -> toString("$acc${list.head}, ", list.tail)
            }
    }

    companion object {
        operator
        fun <A> invoke(vararg az: A): MyloList<A> =
            az.foldRight(Nil as MyloList<A>) { a: A, list: MyloList<A> ->
                Cons(a, list)
            }

        tailrec fun <A> drop(list: MyloList<A>, n: Int): MyloList<A> = when(list) {
            Nil -> list
            is Cons -> if (n <= 0) list else drop(list.tail, n-1)
        }

        // 5-4
        tailrec fun <A> dropWhile(list: MyloList<A>, p: (A) -> Boolean): MyloList<A> = when(list) {
            Nil -> list
            is Cons -> if(p(list.head)) dropWhile(list.tail, p) else list
        }

        tailrec fun <A> reverse(acc: MyloList<A>, list: MyloList<A>): MyloList<A> = when(list) {
            Nil -> acc
            is Cons -> reverse(acc.cons(list.head), list.tail)
        }

        fun <A> concat(list1: MyloList<A>, list2: MyloList<A>): MyloList<A> = when (list1) {
            Nil -> list2
            is Cons -> Cons(list1.head, concat(list1.tail, list2))
        }

        tailrec fun <A, B> coFoldRight(acc: B, list: MyloList<A>, identity: B, f: (A) -> (B) -> B): B =
            when(list) {
                Nil -> acc
                is Cons -> coFoldRight(f(list.head)(acc), list.tail, identity, f)
            }

        fun <A> concatLeft(list1: MyloList<A>, list2: MyloList<A>): MyloList<A> =
            list1.reverse().foldLeft(list2) { acc -> {x -> acc.cons(x)}}
    }

    // 5-1
    fun cons(a: @UnsafeVariance A): MyloList<A> = Cons(a, this)

    // 5-2
    fun setHead(a: @UnsafeVariance A): MyloList<A> = when(this) {
        Nil -> throw IllegalStateException("setHead called on an empty list")
        is Cons -> this.tail.cons(a)
    }

    // 5-3
    fun drop(n: Int): MyloList<A> = drop(this, n)

    // 5-4
    fun dropWhile(p: (A) -> Boolean): MyloList<A> = dropWhile(this, p)

    fun reverse(): MyloList<A> = reverse(invoke(), this)

    // 5-5
    fun init(): MyloList<A> = reverse().drop(1).reverse()

    fun concat(list: MyloList<@UnsafeVariance A>): MyloList<A> = concat(this, list)

    fun <B> foldRight(identity: B, f: (A) -> (B) -> B): B = foldRight(this, identity, f)

    // 5-8
    fun length(): Int = foldRight(0) {{ it + 1 }}

    // 5-9
    fun <B> foldLeft(identity: B, f: (B) -> (A) -> B): B = foldLeft(this, identity, f)

    // 5-10
    fun lengthLeft(): Int = foldLeft(0) {{ _ -> it + 1}}

    // 5-11
    fun reverseLeft(): MyloList<A> = foldLeft(invoke()) { acc -> { acc.cons(it) }}

    // 5-12
    fun <B> foldRightUsingFoldLeft(identity: B, f: (A) -> (B) -> B): B =
        this.reverse().foldLeft(identity) { acc -> { f(it)(acc) }}

    // 5-13
    fun <B> coFoldRight(identity: B, f: (A) -> (B) -> B): B =
        coFoldRight(identity, reverse(), identity, f)

    // 5-14
    fun concatLeft(list: MyloList<@UnsafeVariance A>): MyloList<A> = concatLeft(this, list)

    // 5-18
    fun <B> map(f: (A) -> B): MyloList<B> =
        reverse().foldLeft(Nil) { acc: MyloList<B> -> { acc.cons(f(it))}}

    // 5-19
    fun filter(p: (A) -> Boolean): MyloList<A> =
        reverse().foldLeft(Nil) { acc: MyloList<A> -> { if (p(it)) acc.cons(it) else acc}}
    fun filterRight(p: (A) -> Boolean): MyloList<A> =
        coFoldRight(Nil) {{acc: MyloList<A> -> if(p(it)) Cons(it, acc) else acc}}

    // 5-20
    fun <B> flatMap(f: (A) -> MyloList<B>): MyloList<B> =
        coFoldRight(Nil) {{acc: MyloList<B> -> f(it).concatLeft(acc)}}

    // 5-21
    fun filterWithFlatMap(p: (A) -> Boolean): MyloList<A> =
        flatMap { if (p(it)) MyloList(it) else Nil }
}

// 5-6
private fun sum(list: MyloList<Int>): Int = when(list) {
    MyloList.Nil -> 0
    is MyloList.Cons -> list.head + sum(list.tail)
}

// 5-7
private fun product(list: MyloList<Double>): Double = when(list) {
    MyloList.Nil -> 1.0
    is MyloList.Cons -> list.head * product(list.tail)
}

private fun <A, B> foldRight(list: MyloList<A>, identity: B, f: (A) -> (B) -> B): B =
    when(list) {
        MyloList.Nil -> identity
        is MyloList.Cons -> f(list.head) (foldRight(list.tail, identity, f))
    }

private tailrec fun <A, B> foldLeft(list: MyloList<A>, identity: B, f: (B) -> (A) -> B): B =
    when(list) {
        MyloList.Nil -> identity
        is MyloList.Cons -> foldLeft(list.tail, f(identity)(list.head), f)
    }

// 5-10
private fun sumLeft(list: MyloList<Int>): Int = list.foldLeft(0) {{ acc -> acc + it }}
private fun productLeft(list: MyloList<Double>): Double = list.foldLeft(1.0) {{acc -> acc * it}}

// 5-15
private fun <A> flat(list: MyloList<MyloList<A>>): MyloList<A> = when(list) {
    MyloList.Nil -> MyloList.Nil
    is MyloList.Cons -> list.coFoldRight(MyloList.Nil) { it::concat }
}

// 5-16
private fun triple(list: MyloList<Int>): MyloList<Int> = list.reverse().foldLeft(MyloList()) {acc -> { acc.cons(it* 3) }}
private fun tripleRight(list: MyloList<Int>): MyloList<Int> = list.coFoldRight(MyloList()) {{ acc -> acc.cons(it * 3) }}

// 5-17
private fun doubleToString(list: MyloList<Double>): MyloList<String> = list.coFoldRight((MyloList())) {{ acc -> acc.cons(it.toString())}}


fun main() {
    val test = MyloList(1, 2, 3)
    val doubleTest = MyloList(1.0, 2.0, 3.0, 4.0)
    println("5-1 : ${test.cons(10)}")
    println("5-2 : ${test.setHead(5)}")
    println("5-3 : ${test.drop(4)}")
    println("5-4 : ${test.dropWhile { it < 3 }}")
    println("5-5 : ${test.init()}")
    println("5-6 : ${sum(test)}")
    println("5-7 : ${product(doubleTest)}")
    println("5-8 : ${test.length()}")

    println("5-10 sum : ${sumLeft(test)}")
    println("5-10 product : ${productLeft(doubleTest)}")
    println("5-10 length : ${test.lengthLeft()}")

    println("5-11 : ${doubleTest.reverseLeft()}")

    println("5-14 : ${test.concatLeft(MyloList(4, 5, 6))}")

    val testData = MyloList(MyloList(1, 2, 3), MyloList(9, 8, 7), MyloList(4, 5, 6))
    println("5-15 : ${flat(testData)}")
    println("5-16 : ${triple(test)}")
    println("5-16 with foldRight: ${tripleRight(test)}")
    println("5-17 : ${doubleToString(doubleTest)}")
    println("5-18 : ${test.map { it * 10}}")
    println("5-19 : ${test.filter { it % 2 != 0}}")
    println("5-19 with foldRight: ${test.filterRight { it % 2 != 0}}")
    println("5-20 : ${test.flatMap { MyloList(it * 2, it * 3, it * 4) }}")
    println("5-21 : ${test.filterWithFlatMap { it % 2 != 0}}")
}
