package mylo

fun square(x: Int) = x * x
fun squareFun(): (Int) -> Int = { square(it) }
val squareVal: (Int) -> Int = { square(it) }

fun triple(x: Int) = x * 3
fun tripleFun(): (Int) -> Int = { triple(it) }
val tripleVal: (Int) -> Int = { triple(it) }

// practice 3-1
// 함수 합성 = 함수에 대한 이항 연산
fun compose1(f: (Int) -> Int, g: (Int) -> Int): (Int) -> Int = {x -> f(g(x))}

fun practice1() {
    val compose = compose1(squareFun(), tripleFun())
    printResult(compose(2), "3-1")
}

// practice 3-2
// 다형적 함수 사용하기
fun <P, Q, R> compose2(f: (Q) -> R, g: (P) -> Q): (P) -> R = {x -> f(g(x))}

fun intToString(x: Int): String = x.toString() + x.toString()
fun intToStringFun(): (Int) -> String = { intToString(it) }
val intToStringVal: (Int) -> String = { intToString(it) }

fun stringToLong(x: String): Long = x.toLong()
fun stringToLongFun(): (String) -> Long = { stringToLong(it) }
val stringToLongVal: (String) -> Long = { stringToLong(it) }

fun practice2() {
    val compose = compose2<Int, String, Long>(stringToLongFun(), intToStringFun())
    printResult(compose(4), "3-2")
}

// 3-3
// 인자가 여럿 있는 함수 처리하기
// Int -> ( (Int) -> Int )
val add: (Int) -> (Int) -> Int = {x -> { y -> x + y}}

fun practice3() {
    val addResult = add(1)(2)
    printResult(addResult, "3-3")
}

// 3-4
// squareOfTriple
// compose3 : 함수가 여럿 있는 함수 처리하기
val compose4: ((Int) -> Int) -> ((Int) -> Int) -> (Int) -> Int = { x -> { y -> { z -> x(y(z)) }}}
val squareOfTriple: (Int) -> Int = compose4(squareVal)(tripleVal)

fun practice4() {
    val result = squareOfTriple(2)
    printResult(result, "3-4")
}

// 3-5
// 다형적 compose
// val로 하면 안됨!
// 바깥쪽에서 안으로 처리되는 방식!
fun <P, Q, R> higherCompose(): ((Q) -> R) -> ((P) -> Q) -> (P) -> R = { x -> { y -> { z -> x(y(z)) }}}
val intToStringToLong: (Int) -> Long = higherCompose<Int, String, Long>()(stringToLongVal)(intToStringVal)

fun practice5() {
    val result = intToStringToLong(3)
    printResult(result, "3-5")
}

// 3-6
// 적용순서 반대 & 넣는 위치는 동일
// tripleOfSquare
// 기존: P -> Q : Triple, Q -> R : Square
// 현재: P -> Q : Sqaure, Q -> R : Triple
fun <P, Q, R> higherAndThen(): ((P) -> Q) -> ((Q) -> R) -> (P) -> R = { x -> { y -> { z -> y(x(z)) }}}
val tripleOfSquare: (Int) -> Int = higherAndThen<Int, Int, Int>()(squareVal)(tripleVal)

fun practice6() {
    val result = tripleOfSquare(2)
    printResult(result, "3-6")
}

// 3-7
// 인자를 두 개 받는 fun 함수
// 첫번째 인자(값), 두번째 인자(함수, 커링), 첫번째 인자 = 두번째인자의 첫번째 인자
// return = 첫번째 인자를 적용한 결과
fun <P, Q, R> curing(p: P, f: (P) -> (Q) -> R): (Q) -> R = f(p)

fun practice7() {
    val addVal: (Int) -> Int = curing<Int, Int, Int>(2, add)
    printResult(addVal(3), "3-7")
}

// 3-8
// 인자를 두 개 받는 fun 함수
// 첫번째 인자(값), 두번째 인자(함수, 커링), 첫번째 인자 = 두번째인자의 두번째 인자
// return = 첫번째 인자를 적용한 결과
fun <P, Q, R> curing2(p: Q, f: (P) -> (Q) -> R): (P) -> R = { x: P -> f(x)(p)}
val sub: (Int) -> (Int) -> Int = { x -> { y -> y - x }}

fun practice8() {
    val subVal: (Int) -> Int = curing2<Int, Int, Int>(5, sub)
    printResult(subVal(3), "3-8")
}

// 3-9
// 커리한 함수로 변환하기
fun <A, B, C, D> func(a: A, b: B, c:C, d:D): String = "$a, $b, $c, $d"
fun <A, B, C, D> funcCuring(): (A) -> (B) -> (C) -> (D) -> String = {
        a -> {
            b -> {
                c -> {
                    d -> "$a, $b, $c, $d"
    }}}}

fun practice9() {
    val curingVal: (String) -> (String) -> (String) -> (String) -> String = funcCuring()
    printResult(curingVal("a")("B")("c")("D"), "3-9")
    printResult(funcCuring<String, String, String, String>()("a")("B")("c")("D"), "3-9")
}

// 3-10
// (A, B) -> C
// 여러 인자 -> 커리한 함수로 바꾸는 함수
fun <A, B, C> curingABC(f: (A, B) -> C): (A) -> (B) -> C = {
    a -> {
        b -> f(a, b)
}}

// 3-11
// 커리한 함수의 인자의 순서를 바꾸는 함수
fun <A, B, C> curingReverse(f: (A) -> (B) -> C): (B) -> (A) -> C = {
    b -> {
        a -> f(a)(b)
}}

// ----------------------------

fun main() {
    practice1()
    practice2()
    practice3()
    practice4()
    practice5()
    practice6()
    practice7()
    practice8()
    practice9()
}

fun <T> printResult(result: T, practiceName: String) {
    println("$practiceName RESULT -> ${result.toString()}")
}
