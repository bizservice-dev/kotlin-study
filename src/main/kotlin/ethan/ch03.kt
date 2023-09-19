package ethan

/**
 * 연습문제 3-1
 * Int -> Int 함수의 합성을 허용하는 compose함수를 작성하라
 */
fun compose(f: (Int) -> Int, g: (Int) -> Int): (Int) -> Int = { x -> f(g(x)) }


/**
 * 연습문제 3-2
 * compose 함수를 다형적 함수로 만들어라
 */
fun <T, U, V> compose(f: (U) -> V, g: (T) -> U): (T) -> V = { x -> f(g(x)) }


/**
 * 연습문제 3-3
 * 두 Int값을 더하는 함수를 작성하라
 */
val add: (Int) -> (Int) -> Int = { a -> { b -> a + b } }
typealias IntBinOp = (Int) -> (Int) -> Int

val add2: IntBinOp = { a -> { b -> a + b } }


/**
 * 연습문제 3-4
 * 두 함수를 합성하는 함수 값을 만들라. square와 triple함수를 값으로 다시 정의하고, 이 둘을 합성한 squreOfTriple을 만들어라
 */
val square: (Int) -> Int = { a -> a * a }
val triple: (Int) -> Int = { a -> a * 3 }
val compose: ((Int) -> Int) -> ((Int) -> Int) -> (Int) -> Int = { a -> { b -> { c -> a(b(c)) } } }

val squareOfTriple: (Int) -> Int = compose(square)(triple)

/**
 * 연습문제 3-5
 * 다형적 compose함수를 작성하라
 */
fun <T, U, V> higherCompose(): ((U) -> V) -> ((T) -> U) -> (T) -> V = { f ->
    { g ->
        { x ->
            f(g(x))
        }
    }
}

val squareOfTriple2 = higherCompose<Int, Int, Int>()(square)(triple)

/**
 * 연습문제 3-6
 * higherCompose의 반대인 higherAndThen함수를 정의하라
 */

fun <T, U, V> higherAndThen(): ((T) -> U) -> ((U) -> V) -> (T) -> V = { f ->
    { g ->
        { x ->
            g(f(x))
        }
    }
}

/**
 * 연습문제 3-7
 * 인자 두개 받는 함수 작성. param2는 인자를 두개받는 커리한 함수,
 * param1은 param2의 첫번째 인자와 같은 타입.
 * return은 param1을 param2에 적용한 결과를 돌려준다
 */
fun <A, B, C> some1(a: A, f: (A) -> (B) -> C): (B) -> C {
    return f(a)
}

/**
 * 연습문제 3-8
 * 인자 두개 받는 함수 작성.
 * param2는 인자를 두개받는 커리한 함수,
 * param1은 param2의 두번째 인자와 같은 타입
 * returndms param1을 param2의 두번째 인자에 적용한 결과를 돌려준다.
 */
fun <A, B, C> some2(a: B, f: (A) -> (B) -> C): (A) -> C = { x: A ->
    f(x)(a)
}

/**
 * 연습문제 3-9
 * 다음 함수를 커리한 함수로 변환하라
 */
fun <A, B, C, D> func(a: A, b: B, c: C, d: D): String = "$a, $b, $c, $d"
fun <A, B, C, D> curriedFunc(): (A) -> (B) -> (C) -> (D) -> String =
    { a: A ->
        { b: B ->
            { c: C ->
                { d: D
                    ->
                    "$a, $b, $c, $d"
                }
            }

        }
    }

/**
 * 연습문제 3-10
 * (A, B)에서 C로 가는 함수를 커리한 함수로 바꾸는 함수를 작성하라.
 */
fun <A, B, C> curry(f: (A, B) -> C): (A) -> (B) -> C =
    { a: A ->
        { b: B ->
            f(a, b)
        }
    }

/**
 * 연습문제 3-11
 * 커리한 함수의 두 인자의 순서를 뒤바꾼 새로운 함수를 반환하는 fun 함수를 작성하라.
 */
fun <A, B, C> transform(f: (A) -> (B) -> C): (B) -> (A) -> C =
    { x: B ->
        { y: A ->
            f(y)(x)
        }
    }
