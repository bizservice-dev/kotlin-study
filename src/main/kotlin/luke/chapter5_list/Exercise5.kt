package luke.chapter5_list

// 단일 연결 리스트
private sealed class List<A> { // seal class 생성자는 암묵적으로 private

    abstract fun isEmpty(): Boolean

    abstract fun concat(list: List<A>): List<A>

//    fun cons(elem: @UnsafeVariance A): List<A> = List.Cons(elem, this) // 확장 함수로 정의하면 @UnsafeVariance 을 사용하지 않아도 컴파일 에러는 발생하지 않는다.

    companion object {

        operator fun <A> invoke(vararg az: A): List<A> =
            az.foldRight(Nil as List<A>) { a: A, list: List<A> ->
                Cons(a, list)
            }

    }

    abstract class Empty<A> : List<A>() {

        override fun concat(list: List<A>): List<A> = list

    }

    // 빈 리스트
    object Nil : Empty<Nothing>() {

        override fun isEmpty(): Boolean = true

        override fun toString(): String = "[NIL]"

    }

    // 원소 하나와 다른 리스트의 쌍
    class Cons<A>(
        internal val head: A, // 리스트의 첫 번째 원소
        internal val tail: List<A> // 리스트의 나머지 부분
    ) : List<A>() {

        override fun isEmpty(): Boolean = false

        override fun concat(list: List<A>): List<A> {
            return Cons(this.head, list.concat(this.tail))
        }

        // 항상 NIL 을 맨 마지막 원소로 포함한다.
        override fun toString(): String = "[${toString("", this)}NIL]"

        private tailrec fun toString(acc: String, list: List<A>): String =
            when (list) {
                is Empty -> acc
                is Cons -> toString("$acc${list.head}, ", list.tail)
            }
    }

}

// 연습문제 5-01
private fun <A> List<A>.cons(elem: A): List<A> = List.Cons(elem, this)

// 연습문제 5-02
private fun <A> List<A>.setHead(newHead: A): List<A> =
    when (this) {
        is List.Empty -> throw IllegalStateException("This is empty list!")
        is List.Cons -> List.Cons(newHead, this.tail)
    }

// 연습문제 5-03
private fun <A> List<A>.drop(n: Int): List<A> {
    tailrec fun go(n: Int, target: List<A>): List<A> =
        when {
            target is List.Cons && n > 0 -> go(n - 1, target.tail)
            else -> target
        }

    return go(n, this)
}

// 연습문제 5-04
private fun <A> List<A>.dropWhile(p: (A) -> Boolean): List<A> = dropWhile(p, this)

private fun <A> dropWhile(p: (A) -> Boolean, target: List<A>): List<A> {
    tailrec fun go(target: List<A>): List<A> =
        when (target) {
            is List.Empty -> target
            is List.Cons ->
                if (p(target.head))
                    go(target.tail)
                else
                    target
        }

    return go(target)
}

// 연습문제 5-05
private fun <A> List<A>.init(): List<A> = reverse().drop(1).reverse()

private fun <A> List<A>.reverse(): List<A> = reverse(this, List())

private tailrec fun <A> reverse(remained: List<A>, accumulated: List<A>): List<A> =
    when (remained) {
        is List.Empty -> accumulated
        is List.Cons -> reverse(remained.tail, accumulated.cons(remained.head))
    }

// 연습문제 5-06
private fun List<Int>.sum(): Int = sum(this, 0)

private tailrec fun sum(remained: List<Int>, accumulated: Int): Int =
    when (remained) {
        is List.Cons -> sum(remained.tail, accumulated + remained.head)
        is List.Empty -> accumulated
    }

// 연습문제 5-07
private fun List<Double>.product(): Double = product(this, 1.0)

private tailrec fun product(remained: List<Double>, accumulated: Double): Double =
    when (remained) {
        is List.Empty -> accumulated
        is List.Cons -> product(remained.tail, accumulated * remained.head)
    }

// sum()과 product() 추상화해보기
private fun <A, B> List<A>.foldRight(identify: B, f: (A) -> (B) -> B): B {
    fun <A, B> foldRight(
        list: List<A>,
        identify: B,
        f: (A) -> (B) -> B
    ): B =
        when (list) {
            is List.Empty -> identify
            is List.Cons -> f(list.head)(foldRight(list.tail, identify, f))
        }

    return foldRight(this, identify, f)
}


// foldRight()로 sum() 구현하기
private fun List<Int>.sumByFoldRight(): Int = this.foldRight(0) { a ->
    { accumulated ->
        a + accumulated
    }
}

// foldRight()로 product() 구현하기
private fun List<Double>.productByFoldRight(): Double = this.foldRight(1.0) { a ->
    { accumulated ->
        a * accumulated
    }
}

// 연습문제 5-08
private fun <A> List<A>.length(): Int = this.foldRight(0) {
    { accumulated ->
        accumulated + 1
    }
}

// 연습문제 5-09
private fun <A, B> List<A>.foldLeft(identify: B, f: (B) -> (A) -> B): B {
    tailrec fun <A, B> foldLeft(
        list: List<A>,
        accumulated: B,
        f: (B) -> (A) -> (B)
    ): B =
        when (list) {
            is List.Empty -> accumulated
            is List.Cons -> foldLeft(list.tail, f(accumulated)(list.head), f)
        }

    return foldLeft(this, identify, f)
}


// 연습문제 5-10
private fun List<Int>.sumByFoldLeft(): Int = foldLeft(0) { accumulated ->
    { a ->
        accumulated + a
    }
}

private fun List<Double>.productByFoldLeft(): Double = foldLeft(1.0) { accumulated ->
    { a ->
        accumulated * a
    }
}

private fun <A> List<A>.lengthByFoldLeft(): Int = foldLeft(0) { accumulated ->
    { _ ->
        accumulated + 1
    }
}

// 연습문제 5-11
private fun <A> List<A>.reverseByFoldLeft(): List<A> = foldLeft(List<A>()) { accumulated ->
    { a ->
        accumulated.cons(a)
    }
}

// 연습문제 5-12
private fun <A, B> List<A>.foldRightByFoldLeft(identify: B, f: (A) -> (B) -> B): B = this.reverseByFoldLeft().foldLeft(identify) { accumulated ->
    { a ->
       f(a)(accumulated)
    }
}

// 연습문제 5-13
private fun <A, B> List<A>.coFoldRight(identify: B, f: (A) -> (B) -> B): B {
    tailrec fun <A, B> coFoldRight(accumulated: B, list: List<A>, f: (A) -> (B) -> B): B =
        when (list) {
            is List.Empty -> accumulated
            is List.Cons -> coFoldRight(f(list.head)(accumulated), list.tail, f)
        }

    return coFoldRight(identify, this.reverse(), f)
}

// 연습문제 5-14
private fun <A> List<A>.concatByFoldLeft(list: List<A>): List<A> = this.reverse().foldLeft(list) { acc ->
    { head ->
        acc.cons(head)
    }
}

// 연습문제 5-15
private fun <A> flat(list: List<List<A>>): List<A> {
    tailrec fun flat(list: List<List<A>>, acc: List<A>): List<A> =
        when (list) {
            is List.Empty -> acc
            is List.Cons -> flat(list.tail, acc.concatByFoldLeft(list.head))
        }

    return flat(list, List())
}

private fun <A> flatByFoldLeft(list: List<List<A>>): List<A> = list.foldLeft(List()) { acc ->
    { listOfList ->
        acc.concatByFoldLeft(listOfList)
    }
}

// 연습문제 5-16
private fun List<Int>.triple(): List<Int> = this.reverse().foldLeft(List()) { acc ->
    { num ->
        acc.cons(num * 3)
    }
}

// 연습문제 5-17
private fun List<Double>.toStringList(): List<String> = this.reverse().foldLeft(List()) { acc ->
    { elem ->
        acc.cons(elem.toString())
    }
}

// 연습문제 5-18
private fun <A, B> List<A>.map(f: (A) -> B): List<B> = this.reverse().foldLeft(List()) { acc ->
    { elem ->
        acc.cons(f(elem))
    }
}

// 연습문제 5-19
private fun <A> List<A>.filter(p: (A) -> Boolean): List<A> = this.reverse().foldLeft(List()) { acc ->
    { elem ->
        if (p(elem))
            acc.cons(elem)
        else
            acc
    }
}

// 연습문제 5-20
private fun <A, B> List<A>.flatMap(f: (A) -> List<B>): List<B> = this.map { f(it) }.flatten()

private fun <A> List<List<A>>.flatten(): List<A> = flatByFoldLeft(this)

// 연습문제 5-21
private fun <A> List<A>.filterByFlatMap(p: (A) -> Boolean): List<A> = this.flatMap {
    if (p(it))
        List(it)
    else
        List()
}

fun main() {
    val list: List<Int> = List(1, 2, 3)
    val emptyList = List<Int>()

    println(list) // [1, 2, 3, NIL]

    // 연습문제 5-01
    println("\n### 연습문제 5-01 ###")
    println(list.cons(4)) // [4, 1, 2, 3, NIL]
    println(emptyList.cons(4)) // [4, NIL]

    // 연습문제 5-02
    println("\n### 연습문제 5-02 ###")
    println(list.setHead(4)) // [4, 2, 3, NIL]
//    println(List<Int>().setHead(4)) // (x) IllegalStateException

    // 연습문제 5-03
    println("\n### 연습문제 5-03 ###")
    println(list.drop(1)) // [2, 3, NIL]
    println(list.drop(2)) // [3, NIL]
    println(list.drop(3)) // [NIL]
    println(list.drop(4)) // [NIL]

    // 연습문제 5-04
    println("\n### 연습문제 5-04 ###")
    println(list.dropWhile { it < 2 }) // [2, 3, NIL]
    println(list.dropWhile { it > 0 }) // [NIL]
    println(list.dropWhile { it % 2 == 0 }) // [1, 2, 3, NIL]

    // 연습문제 5-05
    println("\n### 연습문제 5-05 ###")
    println(list.init()) // [1, 2, NIL]
    println(list.init().init()) // [1, NIL]
    println(list.init().init().init()) // [NIL]
    println(list.init().init().init().init()) // [NIL]
    println(emptyList.init()) // [NIL]

    // 연습문제 5-06
    println("\n### 연습문제 5-06 ###")
    println(List(1, 2, 2, -1, 4).sum()) // 8
    println(List<Int>().sum()) // 0

    // 연습문제 5-07
    println("\n### 연습문제 5-07 ###")
    println(List(1.0, 1.5, -1.0, 4.0).product()) // -6.0
    println(List(1.0).product()) // 1.0
    println(List<Double>().product()) // 1.0

    // foldRight 사용하기
    println("\n### foldRight() 사용하기 ###")
    println("sum of [1, 2, 2, -1, 4] : ${List(1, 2, 2, -1, 4).sumByFoldRight()}") // 8
    println("product of [1.0, 1.5, -1.0, 4.0] : ${List(1.0, 1.5, -1.0, 4.0).productByFoldRight()}") // -6.0

    // 연습문제 5-8
    println("\n### 연습문제 5-08 ###")
    println(list.length()) // 3
    println(emptyList.length()) // 0

    // 연습문제 5-10
    println("\n### 연습문제 5-10 ###")
    println(List(1, 2, 2, -1, 4).sumByFoldLeft()) // 8
    println(emptyList.sumByFoldLeft()) // 0

    println(List(1.0, 1.5, -1.0, 4.0).productByFoldLeft()) // -6.0
    println(List<Double>().productByFoldLeft()) // 1.0

    println(list.lengthByFoldLeft()) // 3
    println(emptyList.lengthByFoldLeft()) // 0

    // 연습문제 5-11
    println("\n### 연습문제 5-11 ###")
    println(list.reverseByFoldLeft()) // [3, 2, 1, NIL]
    println(emptyList.reverseByFoldLeft()) // [NIL]

    // 연습문제 5-14
    println("\n### 연습문제 5-14 ###")
    println(list.concatByFoldLeft(List(4, 5, 6))) // [1, 2, 3, 4, 5, 6, NIL]
    println(list.concatByFoldLeft(emptyList)) // [1, 2, 3, NIL]
    println(emptyList.concatByFoldLeft(list)) // [1, 2, 3, NIL]

    // 연습문제 5-15
    println("\n### 연습문제 5-15 ###")
    println(flat(List(List(1, 2, 3), List(), List(4, 5)))) // [1, 2, 3, 4, 5, NIL]
    println(flatByFoldLeft(List(List(1, 2, 3), List(), List(4, 5)))) // [1, 2, 3, 4, 5, NIL]

    // 연습문제 5-16
    println("\n### 연습문제 5-16 ###")
    println(list.triple()) // [3, 6, 9, NIL]
    println(emptyList.triple()) // [NIL]

    // 연습문제 5-17
    println("\n### 연습문제 5-17 ###")
    println(List(1.0, 0.0, 2.0).toStringList()) // [1.0, 0.0, 2.0, NIL]

    // 연습문제 5-18
    println("\n### 연습문제 5-18 ###")
    println(list.map { it * 2 }) // [2, 4, 6, NIL]

    // 연습문제 5-19
    println("\n### 연습문제 5-19 ###")
    println(list.filter { it % 2 == 0 }) // [2, NIL]

    // 연습문제 5-20
    println("\n### 연습문제 5-20 ###")
    println(List(List(1, 2, 3), List(), List(0, -1)).flatMap { it.map { it.toDouble() } }) // [2.0, 4.0, 6.0, 0.0, -1.0, NIL]

    // 연습문제 5-21
    println("\n### 연습문제 5-21 ###")
    println(list.filterByFlatMap { it % 2 == 0 }) // [2, NIL]
}
