package iron.example4

import iron.Example

class `Ex4-10-11` : Example {

    fun <T> unfold(seed: T, f: (T) -> T, p: (T) -> Boolean): List<T> {
        val list = mutableListOf<T>()
        var index = seed
        while(p(index)) {
            list.add(index)
            index = f(index)
        }

        return list
    }

    fun range(start: Int, end: Int): List<Int> {
        return unfold(start, { it + 1 }, { it < end })
    }

    override fun run() {
        println(unfold(1, { x -> x+1}, { it < 10}))
        println(range(1, 10))
    }
}
