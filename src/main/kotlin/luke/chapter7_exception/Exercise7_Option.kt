package luke.chapter7_exception

private sealed class Either<out E, out A> {

    // 연습문제 7-01
    abstract fun <B> map(f: (A) -> B): Either<E, B>

    // 연습문제 7-02
    abstract fun <B> flatMap(f: (A) -> Either<@UnsafeVariance E, B>): Either<E, B>

    // 연습문제 7-03
    fun getOrElse(defaultValue: () -> @UnsafeVariance A): A = when (this) {
        is Right -> this.value
        is Left -> defaultValue()
    }

    // 연습문제 7-03
    fun orElse(defaultValue: () -> Either<@UnsafeVariance E, @UnsafeVariance A>): Either<E, A> = this.map { this }.getOrElse(defaultValue)

    internal class Left<out E, out A>(
        internal val value: E
    ) : Either<E, A>() {

        // 연습문제 7-01
        override fun <B> map(f: (A) -> B): Either<E, B> = Left(value)

        // 연습문제 7-02
        override fun <B> flatMap(f: (A) -> Either<@UnsafeVariance E, B>): Either<E, B> = Left(value)

        override fun toString(): String = "Left($value)"

    }

    internal class Right<out E, out A>(
        internal val value: A
    ) : Either<E, A>() {

        // 연습문제 7-01
        override fun <B> map(f: (A) -> B): Either<E, B> = Right(f(value))

        // 연습문제 7-02
        override fun <B> flatMap(f: (A) -> Either<@UnsafeVariance E, B>): Either<E, B> = f(this.value)

        override fun toString(): String = "Right($value)"

    }

    companion object {

        fun <A, B> left(value: A): Either<A, B> = Left(value)

        fun <A, B> right(value: B): Either<A, B> = Right(value)

    }

}
