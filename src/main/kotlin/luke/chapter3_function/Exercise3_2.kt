package luke.chapter3_function

// 연습문제 3-2
private fun <T>  compose(
    fun1: (T) -> T,
    fun2: (T) -> T,
): (T) -> T = {
    fun1(fun2(it))
}

private fun <T, U, V>  composeWithDifferentTypes(
    fun1: (U) -> V,
    fun2: (T) -> U,
): (T) -> V = {
    fun1(fun2(it))
}
