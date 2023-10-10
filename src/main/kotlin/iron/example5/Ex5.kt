package iron.example5

sealed class List<out A> {
    abstract fun isEmpty(): Boolean

    internal object Nil: List<Nothing>() {
        override fun isEmpty(): Boolean = true
        override fun toString(): String = "[NIL]"
    }

    internal class Cons<A>(
        internal val head: A,
        internal val tail: List<A>
    ): List<A>() {
        override fun isEmpty(): Boolean = false
        override fun toString(): String = "[${toString("", this)}NIL]"
        private tailrec fun toString(acc: String, list: List<A>): String {
            return when (list) {
                is Nil -> acc
                is Cons -> toString("$acc${list.head}, ", list.tail)
            }
        }
    }

    companion object {
        operator fun <A> invoke(vararg az: A): List<A> {
            return az.foldRight(Nil as List<A>) { a: A, list: List<A> ->
                Cons(a, list)
            }
        }

        fun <A, B> foldRight(list: List<A>, identity: B, f: (A) -> (B) -> B): B =
            when (list) {
                List.Nil -> identity
                is List.Cons -> f(list.head)(foldRight(list.tail, identity, f))
            }

        tailrec fun <A, B> foldLeft(list: List<A>, acc: B, f: (B) -> (A) -> B): B {
            return when (list) {
                Nil -> acc
                is Cons -> foldLeft(list.tail, f(acc)(list.head), f)
            }
        }

        // ex5-13
        tailrec fun <A, B> coFoldRight(list: List<A>, acc: B, f: (A) -> (B) -> B): B = when (list) {
            Nil -> acc
            is Cons -> coFoldRight(list.tail, f(list.head)(acc), f)
        }

        // ex5-14
        fun <A> concat(list1: List<A>, list2: List<A>): List<A> = foldRight(list1, list2) { item -> { list -> list.cons(item) } }

        // ex5-15
        fun <A> flatten(list: List<List<A>>): List<A> = list.foldRight(Nil) { list: List<A> -> { flatList: List<A> -> list.concat(flatList) } }

    }

    // ex5-1
    internal fun cons(a: @UnsafeVariance A) = Cons(a, this)

    // ex5-2
    fun setHead(a: @UnsafeVariance A): List<A> = when(this) {
        Nil -> throw IllegalArgumentException()
        is Cons -> tail.cons(a)
    }

    // ex5-3
    fun drop(n: Int): List<A> {
        tailrec fun drop(n: Int, dropList: List<A>): List<A> {
            return if (n <= 0) {
                dropList
            } else {
                when (dropList) {
                    Nil -> dropList
                    is Cons -> drop(n-1, dropList.tail)
                }
            }
        }

        return drop(n, this)
    }

    // ex5-4
    fun dropWhile(p: (A) -> Boolean): List<A> {
        tailrec fun dropWhile(dropList: List<A>, p: (A) -> Boolean): List<A> {
            return when (dropList) {
                Nil -> dropList
                is Cons -> if (p(dropList.head)) dropWhile(dropList.tail, p) else dropList
            }
        }

        return dropWhile(this, p)
    }

    // ex5-5
    fun init(): List<A> {
        return reverse().drop(1).reverse()
    }

    fun reverse(): List<A> {
        tailrec fun reverse(acc: List<A>, list: List<A>): List<A> {
            return when (list) {
                Nil -> acc
                is Cons -> reverse(acc.cons(list.head), list.tail)
            }
        }

        return reverse(invoke(), this)
    }

    fun <B> foldRight(identity: B, f: (A) -> (B) -> B): B = Companion.foldRight(this, identity, f)

    // ex5-8
    fun length(): Int {
        return this.foldRight(0) { { it + 1 } }
    }

    // ex5-9
    fun <B> foldLeft(identity: B, f: (B) -> (A) -> B): B {
        return foldLeft(this, identity, f)
    }

    // ex5-10 -> 아래 패키지 레벨에 sum, product 구현
    fun safeStackLength(): Int = foldLeft(0) { x -> { x + 1 } }

    // ex5-11
    fun foldLeftReverse(): List<A> = foldLeft(invoke()) { list -> { acc -> list.cons(acc) } }

    // ex5-12
    fun <B> foldRightUseFoldLeft(identity: B, f: (A) -> (B) -> B): B = this.reverse().foldLeft(identity) { b -> { a -> f(a)(b) } }

    // ex5-13 -> Companion Object에 도우미 함수 구현
    fun <B> coFoldRight(identity: B, f: (A) -> (B) -> B): B = Companion.coFoldRight(this.reverse(), identity, f)

    // ex5-14 -> Companion Object에 도우미 함수 구현
    fun concat(list: List<@UnsafeVariance A>): List<A> = this.coFoldRight(list) { item -> { list -> list.cons(item) }}

    // ex5-18
    fun <B> map(f: (A) -> B): List<B> = foldLeft(Nil) { list: List<B> -> { item -> list.cons(f(item)) } }.reverse()

    // ex5-19
    fun filter(p: (A) -> Boolean): List<A> = foldLeft(Nil) { list: List<A> -> { item -> if(p(item)) list.cons(item) else list}}.reverse()

    // ex5-20
    fun <B> flatMap(f: (A) -> List<B>): List<B> {
        return flatten(this.map(f))
    }

    // ex5-21
    fun filterUseFlatMap(p: (A) -> Boolean): List<A> = this.flatMap { if(p(it)) List(it) else Nil }


}

// ex5-6
fun sum(intList: List<Int>): Int {
    tailrec fun sum(acc: Int, list: List<Int>): Int {
        return when (list) {
            List.Nil -> acc
            is List.Cons -> sum(acc + list.head, list.tail)
        }
    }

    return sum(0, intList)
}

// ex5-7
fun product(doubleList: List<Double>): Double {
    tailrec fun product(acc: Double, list: List<Double>): Double {
        return when (list) {
            List.Nil -> acc
//            is List.Cons -> product(acc * list.head, list.tail)
            // short circuit
            is List.Cons -> if (list.head == 0.0) {
                0.0
            } else {
                product(acc * list.head, list.tail)
            }
        }
    }

    return product(1.0, doubleList)
}

// ex5-10
fun safeStackSum(intList: List<Int>): Int = intList.foldLeft(0) { x -> { y -> x + y }}

fun safeStackProduct(doubleList: List<Double>): Double = doubleList.foldLeft(1.0) { x -> { y -> x * y }}

// ex-5-16
fun triple(intList: List<Int>): List<Int> {
    return intList.foldRight(List.invoke()) { item -> { list: List<Int> -> list.cons(item * 3)}}
}

fun doubleToString(doubleList: List<Double>): List<String> {
    return doubleList.foldRight(List.invoke()) { item -> { list: List<String> -> list.cons(item.toString())}}
}


fun main() {
    val list: List<Int> = List(2, 3, 4, 5, 6)
    val consList = list.cons(1)
    println("Ex5-1 ans : $consList")

    val setHeadList = consList.setHead(0)
    println("Ex5-2 ans : $setHeadList")

    val dropNList = consList.drop(3)
    println("Ex5-3 ans : $dropNList")

    val dropWhileList = consList.dropWhile { it < 2 }
    println("Ex5-4 ans : $dropWhileList")

    val initList = list.init()
    println("Ex5-5 ans : $initList")

    println("Ex5-6 ans : ${sum(consList)}")

    val doubleList = List(1.0, 2.0, 3.0, 4.0, 5.0)
    println("Ex5-7 ans : ${product(doubleList)}")

    println("Ex5-8 ans : ${list.length()}")

    println("Ex5-10 sum ans : ${safeStackSum(consList)}")
    println("Ex5-10 product ans : ${safeStackProduct(doubleList)}")
    println("Ex5-10 length ans : ${list.safeStackLength()}")

    println("Ex5-11 foldLeft reverse ans : ${list.foldLeftReverse()}")

    val flattenList = List(List(1,2,3), List(4,5,6), List(7,8,9))
    println("Ex5-15 flatten ans : ${List.flatten(flattenList)}")

    println("Ex5-16 triple ans : ${triple(list)}")
    println("Ex5-17 doubleToString ans : ${doubleToString(doubleList)}")
    println("Ex5-18 map ans : ${list.map { it * 10 }}")
    println("Ex5-19 filter ans : ${list.filter { it % 2 == 0 }}")

    val flatMapList = List(1,2,3).flatMap { i -> List(i, -i) }
    println("Ex5-20 flatMap ans : $flatMapList")
    println("Ex5-21 filter use flatmap ans : ${list.filterUseFlatMap { it % 2 == 0 }}")
}
