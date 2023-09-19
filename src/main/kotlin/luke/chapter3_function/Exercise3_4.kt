package luke.chapter3_function

val square: (Int) -> Int = { n ->
    n * n
}

val triple: (Int) -> Int = { n ->
    n * 3
}

//val squareOfTriple: (Int) -> Int = { n ->
//    square(triple(n))
//}

// f2(f1(x)) = g(x)
val compose: ((Int) -> Int) -> ((Int) -> Int) -> ((Int) -> Int) = { f2 ->
    { f1 ->
        { x ->
            f2(f1(x))
        }
    }
}

fun main() {
    val squareOfTriple = compose(square)(triple)

    println(squareOfTriple(2)) // 36
}
