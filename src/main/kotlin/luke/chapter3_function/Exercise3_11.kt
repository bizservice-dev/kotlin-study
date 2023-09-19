package luke.chapter3_function

private fun <A, B, C> func(f: (A, B) -> C): (B, A) -> C = { b: B, a: A ->
    f(a, b)
}

private fun <A, B, C> func(): ((A, B) -> C) -> (B, A) -> C = { f ->
    { b: B, a: A ->
        f(a, b)
    }
}

private fun <A, B, C> func2(f: (A) -> (B) -> C): (B) -> (A) -> C = { b ->
    { a ->
        f(a)(b)
    }
}

private fun <A, B, C> func2(): ((A) -> (B) -> C) -> (B) -> (A) -> C = { f ->
    { b ->
        { a ->
            f(a)(b)
        }
    }
}
