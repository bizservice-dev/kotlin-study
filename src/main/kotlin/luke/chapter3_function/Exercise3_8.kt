package luke.chapter3_function

private fun <T, U, V> func(param: T, f: (U) -> (T) -> V): (U) -> V = { param2 ->
    f(param2)(param)
}

private fun <T, U, V> func(): (T) -> ((U) -> (T) -> V) -> ((U) -> V) = { param ->
    { f1 ->
        { param2 ->
            f1(param2)(param)
        }
    }
}
