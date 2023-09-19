package luke.chapter3_function

fun add(a: Int, b: Int): Int = a + b

//fun addWithCurrying(a: Int): (Int) -> Int = {
//    a + it
//}

private val addWithCurrying: (Int) -> (Int) -> Int = { a ->
    { b ->
        a + b
    }
}

fun main() {
    println(addWithCurrying(3)(5)) // 8
}
