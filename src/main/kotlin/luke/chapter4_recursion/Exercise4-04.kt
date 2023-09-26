package luke.chapter4_recursion

private fun <T> List<T>.head(): T =
    if (this.isEmpty())
        throw IllegalArgumentException("head called on empty list")
    else
        this[0]

private fun <T> List<T>.tail(): List<T> =
    if (this.isEmpty())
        throw IllegalArgumentException("tail called on empty list")
    else
        this.drop(1)

private fun <T> makeString(list: List<T>, delim: String): String =
    when {
        list.isEmpty() -> ""
        list.tail().isEmpty() -> "${list.head()}${makeString(list.tail(), delim)}"
        else -> "${list.head()}$delim${makeString(list.tail(), delim)}"
    }

private fun <T> makeStringWithTailRec(list: List<T>, delim: String): String {
    tailrec fun <T> doMakeStringWithTailRec(list: List<T>, accumulated: String): String =
        when {
            list.isEmpty() -> accumulated
            accumulated.isEmpty() -> doMakeStringWithTailRec(list.tail(), "${list.head()}")
            else -> doMakeStringWithTailRec(list.tail(), "$accumulated$delim${list.head()}")
        }


    return doMakeStringWithTailRec(list, "")
}

fun main() {
    val list = listOf(1, 2, 3)

    println(makeString(list, ","))

    println(makeStringWithTailRec(list, ","))
}
