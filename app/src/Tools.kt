fun <T> List<List<T>>.transpose(): List<List<T>> {
    val target: MutableList<List<T>> = emptyList<List<T>>().toMutableList()
    (0..first().lastIndex).forEach { index ->
        target.add(map { it[index] })
    }
    return target
}