package ellie

// 연습문제 3-1
fun compose(f: (Int) -> Int, g: (Int) -> Int): (Int) -> Int = { f(g(it)) }

// 연습문제 3-2
fun <I, O, T> genericCompose(f: (T) -> O, g: (I) -> T): (I) -> O = { f(g(it)) }

// 연습문제 3-3
val add: (Int) -> (Int) -> Int = { val1 -> { val2 -> val1 + val2 } }

// 연습문제 3-4
typealias IntFunction = (Int) -> Int
val curriedCompose: (IntFunction) -> (IntFunction) -> IntFunction = { f -> { g -> { x -> f(g(x)) } } }

// 연습문제 3-5
fun <I, O, T> genericCurriedCompose(): ((T) -> O) -> ((I) -> T) -> (I) -> O = { f -> { g -> { x -> f(g(x)) } } }

// 연습문제 3-6
fun <I, O, T> genericCurriedComposeReverse(): ((I) -> T) -> ((T) -> O) -> (I) -> O = { f -> { g -> { x -> g(f(x)) } } }

// 연습문제 3-7
fun <T, U, V> partialCurriedFunction(v: V, f: (V) -> (T) -> U): (T) -> U = f(v)

// 연습문제 3-8
fun <T, U, V> partialCurriedFunction2(v: V, f: (T) -> (V) -> U): (T) -> U = { x -> f(x)(v) }

// 연습문제 3-9
fun <A, B, C, D> func(a: A, b: B, c: C, d: D): String = "$a, $b, $c, $d"
fun <A, B, C, D> curriedFunc(): (A) -> (B) -> (C) -> (D) -> String =
    { a -> { b -> { c -> { d -> "$a, $b, $c, $d" } } } }

// 연습문제 3-10
fun <A, B, C> convertToCurried(f: (A, B) -> C): (A) -> (B) -> C = { a -> { b -> f(a, b) } }

// 연습문제 3-11
fun <A, B, C> changeOrderOfParam(f: (A) -> (B) -> C): (B) -> (A) -> C = { b -> { a -> f(a)(b) } }

fun main() {
    // 연습문제 3-1
    val f: (Int) -> Int = {it + 1}
    val g: (Int) -> Int = {it * 2}
    val result1 = compose(f, g)(5)
    println("연습문제 3-1 결과: $result1")

    // 연습문제 3-2
    val h: (Int) -> String = { it.toString() + "3" }
    val result2 = genericCompose(h, f)(5)
    println("연습문제 3-2 결과: $result2")

    // 연습문제 3-3
    println("연습문제 3-3 결과: ${add(1)(3)}")

    // 연습문제 3-4
    println("연습문제 3-4 결과: ${curriedCompose(f)(g)(5)}")

    // 연습문제 3-5
    println("연습문제 3-5 결과: ${genericCurriedCompose<Int, Int, Int>()(f)(g)(5)}")

    // 연습문제 3-6
    println("연습문제 3-6 결과: ${genericCurriedComposeReverse<Int, Int, Int>()(f)(g)(5)}")

    // 연습문제 3-7
    val addOne = partialCurriedFunction(1, add)
    println("연습문제 3-7 결과: ${addOne(3)}")

    // 연습문제 3-8
    val addTwo = partialCurriedFunction2(2, add)
    println("연습문제 3-8 결과: ${addTwo(3)}")

    // 연습문제 3-9
    println("연습문제 3-9 기존함수 결과: ${func(1, 2, 3,4)}")
    println("연습문제 3-9 커리한 함수 결과: ${curriedFunc<Int, Int, Int, Int>()(1)(2)(3)(4)}")

    // 연습문제 3-10
    val multiple: (Int, Int) -> Int = { x, y -> x * y }
    val curriedMultiple = convertToCurried(multiple)
    println("연습문제 3-10 커리한 곱셈함수 결과: ${curriedMultiple(5)(3)}")

    // 연습문제 3-11
    val addTaxWithPriceFirst: (Double) -> (Double) -> Double = { price -> { rate -> price + (price / 100) * rate } }
    val addTaxGivenRate = changeOrderOfParam(addTaxWithPriceFirst)(10.0)
    println("연습문제 3-11 10%의 세율계산기 결과: ${addTaxGivenRate(100.0)}")
}
