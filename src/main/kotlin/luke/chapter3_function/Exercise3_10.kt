package luke.chapter3_function

private fun <A, B, C> func(f: (A, B) -> C): (A) -> (B) -> C = { a ->
    { b ->
        f(a, b)
    }
}

private fun <A, B, C> func(): ((A, B) -> C) -> (A) -> (B) -> C = { f ->
    { a ->
        { b ->
            f(a, b)
        }
    }
}
