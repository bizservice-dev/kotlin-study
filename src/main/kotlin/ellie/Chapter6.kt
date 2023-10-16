package ellie

import kotlin.math.pow

private sealed class Option<out T> {
    abstract fun isEmpty(): Boolean

    internal object None: Option<Nothing>() {
        override fun isEmpty() = true
        override fun toString(): String = "None"
        override fun equals(other: Any?): Boolean = other === None
        override fun hashCode(): Int = 0
    }

    internal data class Some<out T>(internal val value: T) : Option<T>() {
        override fun isEmpty(): Boolean = false
    }

    companion object {
        operator fun <T> invoke(t: T? = null): Option<T> = when (t) {
            null -> None
            else -> Some(t)
        }
    }

    // 연습문제 6-1
    // 단순 get이 아닌 기본값을 넘겨주는 안전한 조회 함수 구현
    fun getOrElse(default: @UnsafeVariance T): T = when (this) {
        is None -> default
        is Some -> value
    }

    // 연습문제 6-2
    // default를 사용할때만 값 조회 함수를 호출하도록 구현
    fun getOrElse(default: () -> @UnsafeVariance T): T = when (this) {
        is None -> default()
        is Some -> value
    }

    // 연습문제 6-3
    fun <U> map(f: (T) -> U): Option<U> = when (this) {
        is None -> None
        is Some -> Some(f(value))
    }

    // 연습문제 6-4
    fun <U> flatMap(f: (T) -> Option<U>) = map(f).getOrElse{None}

    // 연습문제 6-5
    fun orElse(default: () -> Option<@UnsafeVariance T>): Option<T> = map { this }.getOrElse(default)

    // 연습문제 6-6
    fun filter(p: (T) -> Boolean): Option<T> = flatMap { x -> if (p(x)) this else None }

}

// 연습문제 6-7
private val mean: (List<Double>) -> Option<Double> = { list ->
    when {
        list.isEmpty() -> Option()
        else -> Option(list.sum() / list.size)
    }
}

private val variance: (List<Double>) -> Option<Double> = {list ->
    mean(list).flatMap { m ->
        mean(list.map { x -> (x - m).pow(2.0) })
    }
}

// 연습문제 6-8
private fun <A, B> lift(f: (A) -> B): (Option<A>) -> Option<B> = { it.map(f) }

// 연습문제 6-9
// 예외를 던지는 함수에서도 작동하는 lift를 구현
private fun <A, B> safeLift(f: (A) -> B): (Option<A>) -> Option<B> = {
    try {
        it.map(f)
    } catch (e: Exception) {
        Option()
    }
}

// 연습문제 6-10
private fun <A, B, C> map2(oa: Option<A>, ob: Option<B>, f: (A) -> (B) -> C): Option<C> =
    oa.flatMap { a -> ob.map { b -> f(a)(b) } }

// 연습문제 6-11
private fun <A> sequence(list: List<Option<A>>): Option<List<A>> = traverse(list) { x -> x }

// 연습문제 6-12
private fun <A, B> traverse(list: List<A>, f: (A) -> Option<B>): Option<List<B>> =
    list.foldRight(Option(listOf())) { x: A, acc: Option<List<B>> -> map2(f(x), acc) {
        a -> { b: List<B> -> listOf(a) + b} }
    }