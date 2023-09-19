package luke.chapter3_function

private fun <A, B, C, D> func(a: A, b: B, c: C, d: D): String = "$a, $b, $c, $d"

private fun <A, B, C, D> func(): (A) -> (B) -> (C) -> (D) -> String = { a ->
    { b ->
        { c ->
            { d ->
               "$a, $b, $c, $d"
            }
        }
    }
}
