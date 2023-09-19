package luke.chapter3_function

private fun <T, U, V> func(param: T, f: (T) -> (U) -> V): (U) -> V {
    return f(param)
}

private fun <T, U, V> func(): (T) -> ((T) -> (U) -> V) -> ((U) -> V) = { param ->
    { f ->
        f(param)
    }
}
