package ellie

import java.io.Serializable

private sealed class Either<out E, out A> {
    // 연습문제 7-1
    abstract fun <B> map(f: (A) -> B): Either<E, B>
    // 연습문제 7-2
    abstract fun <B> flatMap(f: (A) -> Either<@UnsafeVariance E, B>): Either<E, B>

    internal class Left<out E, out A> (internal val value: E) : Either<E, A>() {
        override fun toString(): String = "Left($value)"

        override fun <B> map(f: (A) -> B): Either<E, B> = Left(value)
        override fun <B> flatMap(f: (A) -> Either<@UnsafeVariance E, B>): Either<E, B> = Left(value)
    }

    internal class Right<out E, out A> (internal val value: A) : Either<E, A>() {
        override fun toString(): String = "Right($value)"

        override fun <B> map(f: (A) -> B): Either<E, B> = Right(f(value))
        override fun <B> flatMap(f: (A) -> Either<@UnsafeVariance E, B>): Either<E, B> = f(value)
    }

    companion object {
        fun <E, A> left(value: E): Either<E, A> = Left(value)
        fun <E, A> right(value: A): Either<E, A> = Right(value)
    }

    // 연습문제 7-3
    fun getOrElse(defaultValue: () -> @UnsafeVariance A): A = when(this) {
        is Left -> defaultValue()
        is Right -> this.value
    }
    fun orElse(defaultValue: () -> Either<@UnsafeVariance E, @UnsafeVariance A>): Either<E, A> =
        map { this }.getOrElse(defaultValue)

}

private sealed class Result<out A>: Serializable {
    // 연습문제 7-4
    abstract fun <B> map(f: (A) -> B): Result<B>
    abstract fun <B> flatMap(f: (A) -> Result<B>): Result<B>
    // 연습문제 7-7
    abstract fun mapFailure(message: String): Result<A>
    // 연습문제 7-9
    abstract fun forEach(effect: (A) -> Unit)
    // 연습문제 7-10
    abstract fun forEachOrElse(onSuccess: (A) -> Unit, onFailure: (RuntimeException) -> Unit, onEmpty: () -> Unit)
    // 연습문제 7-11
    abstract fun forEach(onSuccess: (A) -> Unit = {},
                         onFailure: (RuntimeException) -> Unit = {},
                         onEmpty: () -> Unit = {})

    internal object Empty: Result<Nothing>() {
        override fun toString(): String = "Empty"
        override fun <B> map(f: (Nothing) -> B): Result<B> = Empty
        override fun <B> flatMap(f: (Nothing) -> Result<B>): Result<B> = Empty
        override fun mapFailure(message: String): Result<Nothing> = this
        override fun forEach(effect: (Nothing) -> Unit) {}
        override fun forEachOrElse(onSuccess: (Nothing) -> Unit, onFailure: (RuntimeException) -> Unit, onEmpty: () -> Unit) = onEmpty()
        override fun forEach(onSuccess: (Nothing) -> Unit, onFailure: (RuntimeException) -> Unit, onEmpty: () -> Unit) = onEmpty()


    }

    internal class Failure<out A>(internal val exception: RuntimeException) : Result<A>() {
        override fun toString(): String = "Failure(${exception.message})"

        override fun <B> map(f: (A) -> B): Result<B> = Failure(exception)
        override fun <B> flatMap(f: (A) -> Result<B>): Result<B> = Failure(exception)
        override fun mapFailure(message: String): Result<A> = Failure(RuntimeException(message, exception))
        override fun forEach(effect: (A) -> Unit) {}
        override fun forEachOrElse(onSuccess: (A) -> Unit, onFailure: (RuntimeException) -> Unit, onEmpty: () -> Unit) = onFailure(exception)
        override fun forEach(onSuccess: (A) -> Unit, onFailure: (RuntimeException) -> Unit, onEmpty: () -> Unit) = onFailure(exception)
    }

    internal class Success<out A>(internal val value: A) : Result<A>() {
        override fun toString(): String = "Success($value)"

        override fun <B> map(f: (A) -> B): Result<B> = try {
            Success(f(value))
        } catch (e: RuntimeException) {
            Failure(e)
        } catch (e: Exception) {
            Failure(RuntimeException(e))
        }

        override fun <B> flatMap(f: (A) -> Result<B>): Result<B> = try {
            f(value)
        } catch (e: RuntimeException) {
            Failure(e)
        } catch (e: Exception) {
            Failure(RuntimeException(e))
        }

        override fun mapFailure(message: String): Result<A> = this
        override fun forEach(effect: (A) -> Unit) = effect(value)
        override fun forEachOrElse(onSuccess: (A) -> Unit, onFailure: (RuntimeException) -> Unit, onEmpty: () -> Unit) = onSuccess(value)
        override fun forEach(onSuccess: (A) -> Unit, onFailure: (RuntimeException) -> Unit, onEmpty: () -> Unit) = onSuccess(value)
    }

    companion object {
        operator fun <A> invoke(a: A? = null): Result<A> = when (a) {
            null -> Failure(NullPointerException())
            else -> Success(a)
        }
        operator fun <A> invoke(): Result<A> = Empty
        // 연습문제 7-8
        operator fun <A> invoke(a: A? = null, message: String): Result<A> = when (a) {
            null -> Failure(NullPointerException(message))
            else -> Success(a)
        }
        operator fun <A> invoke(a: A? = null, p: (A) -> Boolean): Result<A> = when (a) {
            null -> Failure(NullPointerException())
            else -> when {
                p(a) -> Success(a)
                else -> Empty
            }
        }
        operator fun <A> invoke(a: A? = null, message: String, p: (A) -> Boolean): Result<A> = when (a) {
            null -> Failure(NullPointerException())
            else -> when {
                p(a) -> Success(a)
                else -> Failure(IllegalArgumentException("Argument $a does not match condition: $message"))
            }
        }

        fun <A> failure(message: String): Result<A> = Failure(IllegalStateException(message))
        fun <A> failure(exception: RuntimeException): Result<A> = Failure(exception)
        fun <A> failure(exception: Exception): Result<A> = Failure(IllegalStateException(exception))
    }

    fun getOrElse(defaultValue: @UnsafeVariance A): A = when (this) {
        is Success -> this.value
        else -> defaultValue
    }

    fun orElse(defaultValue: () -> Result<@UnsafeVariance A>): Result<A> = when (this) {
        is Success -> this
        else -> try {
            defaultValue()
        } catch (e: RuntimeException) {
            Result.failure<A>(e)
        } catch (e: Exception) {
            Result.failure<A>(RuntimeException(e))
        }
    }

    // 연습문제 7-5
    fun filter(p: (A) -> Boolean): Result<A> = flatMap { if (p(it)) this else failure("Condition not matched")}
    fun filter(message: String, p: (A) -> Boolean): Result<A> = flatMap { if (p(it)) this else failure(message) }

    // 연습문제 7-6
    fun exists(p: (A) -> Boolean): Boolean = map(p).getOrElse(false)
}

// 연습문제 7-12
private fun <A, B> lift(f: (A) -> B): (Result<A>) -> Result<B> = { it.map(f) }

// 연습문제 7-13
private fun <A, B, C> lift2(f: (A) -> (B) -> C): (Result<A>) -> (Result<B>) -> Result<C> = { a ->
    { b -> a.map(f).flatMap { b.map(it) } }
}

private fun <A, B, C, D> lift3(f: (A) -> (B) -> (C) -> D): (Result<A>) -> (Result<B>) -> (Result<C>) -> Result<D> = { a ->
    { b -> {
        c -> a.map(f).flatMap { b.map(it) }.flatMap { c.map(it) }
    }}
}

// 연습문제 7-14
private fun <A, B, C> map2(ra: Result<A>, rb: Result<B>, f: (A) -> (B) -> C): Result<C> = lift2(f)(ra)(rb)