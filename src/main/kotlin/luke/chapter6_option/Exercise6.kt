package luke.chapter6_option

import kotlin.math.pow

private sealed class Option<out A> {

    abstract fun isEmpty(): Boolean

    fun getOrElse(default: () -> @UnsafeVariance A): A =
        when (this) {
            None -> default()
            is Some -> this.value
        }

    // 연습문제 6-03
    fun <B> map(f: (A) -> B): Option<B> = when(this) {
        None -> None
        is Some -> Some(f(this.value))
    }

    // 연습문제 6-04
    fun <B> flatMap(f: (A) -> Option<B>): Option<B> = this.map { f(it) }.getOrElse { None }

    // 연습문제 6-05
    fun orElse(default: () -> Option<@UnsafeVariance A>): Option<A> = this.map { _ -> this }.getOrElse { default() }

    // 연습문제 6-06
    fun filter(p: (A) -> Boolean): Option<A> = this.flatMap { if(p(it)) this else None }

    internal object None : Option<Nothing>() {

        override fun isEmpty(): Boolean = true

        override fun toString(): String = "None"

        override fun equals(other: Any?): Boolean = other === None // 데이터가 없는 경우 모두 다 같다고 취급한다.

        override fun hashCode(): Int = 0

    }

    internal data class Some<out A>(
        internal val value: A
    ) : Option<A>() {

        override fun isEmpty(): Boolean = true

    }

    companion object {

        operator fun <A> invoke(a: A? = null): Option<A> =
            when (a) {
                null -> None
                else -> Some(a)
            }

    }

}

// 연습문제 6-01~02
private fun max(list: List<Int>): Option<Int> = Option(list.maxOrNull())

private fun getDefault(): Int = throw RuntimeException()

// 연습문제 6-07
private val variance: (List<Double>) -> Option<Double> = { list ->
    list.mean { it }.flatMap { avg ->
        list.mean {
            (it - avg).pow(2.0)
        }
    }
}

private fun List<Double>.mean(f: (Double) -> Double): Option<Double> =
    if (this.isEmpty())
        Option.None
    else
        Option(this.map(f).average())

// 연습문제 6-08
private fun <A, B> lift(f: (A) -> (B)): (Option<A>) -> Option<B> = { it.map(f) }

// 연습문제 6-09
private fun <A, B> liftWhenThrowing(f: (A) -> B): (Option<A>) -> Option<B> =
    {
        try {
            it.map(f)
        } catch (e: Exception) {
            Option()
        }
    }

// 연습문제 6-10
private fun <A, B, C> map2(ao: Option<A>, bo: Option<B>, f: (A) -> (B) -> C): Option<C> = ao.flatMap { a ->
    bo.map { b ->
        f(a)(b)
    }
}

fun main() {
    val some = Option(1)
    val none = Option<Int>()

    // 연습문제 6-01~02
    println("\n### 연습문제 6-01~02 ###")
    println(some.getOrElse { 0 }) // 1
    println(none.getOrElse { 0 }) // 0

    println(max(listOf(3, 5, 7, 2, 1)).getOrElse { 0 }) // 1
    println(max(listOf()).getOrElse() { 0 }) // 0
    println(max(listOf(3, 5, 7, 2, 1)).getOrElse { getDefault() }) // 7
//    println(max(listOf()).getOrElse() { getDefault() }) // (x) 빈 리스트이기 때문에 예외 발생

    // 연습문제 6-03
    println("\n### 연습문제 6-03 ###")
    println(some.map { it * 2 }.getOrElse { getDefault() }) // 2

}
