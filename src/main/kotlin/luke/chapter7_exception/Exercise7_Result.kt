package luke.chapter7_exception

import java.io.Serializable

private sealed class Result<out A> : Serializable {

    // 연습문제 7-4
    abstract fun <B> map(f: (A) -> B): Result<B>

    // 연습문제 7-4
    abstract fun <B> flatMap(f: (A) -> Result<B>): Result<B>

    // 연습문제 7-4
    fun getOrElse(defaultValue: @UnsafeVariance A): A = when (this) {
        is Success -> this.value
        else -> defaultValue
    }

    // 연습문제 7-4
    fun orElse(defaultValue: () -> Result<@UnsafeVariance A>): Result<A> = when (this) {
        is Success -> this
        else -> try {
            defaultValue()
        } catch (e: RuntimeException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 연습문제 7-5
    fun filter(p: (A) -> Boolean): Result<A> = this.filter(p, "not matched!")

    fun filter(p: (A) -> Boolean, message: String): Result<A> = this.flatMap {
        if (p(it))
            this
        else
            Result.failure(message)
    }

    // 연습문제 7-6
    fun exists(p: (A) -> Boolean) = this.map(p).getOrElse(false)

    // 연습문제 7-7
    fun mapFailure(message: String): Result<A> = when (this) {
        is Success -> this
        else -> Result.failure(message)
    }

    // 연습문제 7-9
    abstract fun forEach(effect: (A) -> (Unit)): Unit

    // 연습문제 7-10
    abstract fun forEachOrElse(onSuccess: (A) -> Unit, onFailure: (RuntimeException) -> Unit, onEmpty: () -> Unit): Unit

    internal object Empty: Result<Nothing>() {

        override fun <B> map(f: (Nothing) -> B): Result<B> = Empty

        override fun <B> flatMap(f: (Nothing) -> Result<B>): Result<B> = Empty

        override fun forEach(effect: (Nothing) -> Unit) {}

        override fun forEachOrElse(onSuccess: (Nothing) -> Unit, onFailure: (RuntimeException) -> Unit, onEmpty: () -> Unit) = onEmpty()


    }

    internal class Failure<out A>(
        internal val exception: RuntimeException
    ) : Result<A>() {

        override fun <B> map(f: (A) -> B): Result<B> = Failure(exception)

        override fun <B> flatMap(f: (A) -> Result<B>): Result<B> = Failure(exception)

        override fun forEach(effect: (A) -> Unit) {}

        override fun forEachOrElse(onSuccess: (A) -> Unit, onFailure: (RuntimeException) -> Unit, onEmpty: () -> Unit) = onFailure(exception)

        override fun toString(): String = "Failure(${exception.message})"

    }

    internal class Success<out A>(
        internal val value: A
    ) : Result<A>() {

        override fun <B> map(f: (A) -> B): Result<B> = try {
            Success(f(this.value))
        } catch (e: RuntimeException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }

        override fun <B> flatMap(f: (A) -> Result<B>): Result<B> = try {
            f(this.value)
        } catch (e: RuntimeException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }

        override fun forEach(effect: (A) -> Unit) = effect(this.value)

        override fun forEachOrElse(onSuccess: (A) -> Unit, onFailure: (RuntimeException) -> Unit, onEmpty: () -> Unit) = onSuccess(this.value)

        override fun toString(): String = "Success($value)"

    }

    companion object {

        operator fun <A> invoke(a: A? = null): Result<A> = when (a) {
            null -> Failure(NullPointerException())
            else -> Success(a)
        }

        fun <A> failure(message: String): Result<A> = Failure(IllegalStateException(message))

        fun <A> failure(exception: RuntimeException): Result<A> = Failure(exception)

        fun <A> failure(exception: Exception): Result<A> = Failure(IllegalStateException(exception))

    }

}

// 연습문제 7-12
private fun <A, B> lift(f: (A) -> B): (Result<A>) -> Result<B> = { ra ->
    ra.map(f)
}

// 연습문제 7-13
private fun <A, B, C> lift2(f: (A) -> (B) -> C): (Result<A>) -> (Result<B>) -> Result<C> = { ra ->
    { rb ->
        ra.flatMap { a ->
            rb.map { b -> f(a)(b) }
        }
    }
}

// 연습문제 7-14
private fun <A, B, C> map2(ra: Result<A>, rb: Result<B>, f: (A) -> (B) -> C): Result<C> = lift2(f)(ra)(rb)
