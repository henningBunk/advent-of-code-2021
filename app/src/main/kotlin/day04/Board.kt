package day04

typealias BoardList = List<List<Number>>

class Board(
    boardStrings: List<String>,
    private val doDiagonalsCount: Boolean = false
) {

    private var lastCheckedNumber: Int = -1

    private val board: BoardList = boardStrings.map { row ->
        row
            .trim()
            .split("""\s+""".toRegex())
            .map(::Number)
    }

    fun check(drawnNumber: Int) {
        if (hasWon()) return
        board.flatten().forEach {
            if (it.value == drawnNumber) {
                it.check()
                lastCheckedNumber = drawnNumber
            }
        }
    }

    fun hasWon(): Boolean {
        return board.hasWinningRow() ||
                board.hasWinningColumn() ||
                (doDiagonalsCount && board.hasWinningDiagonal())
    }

    fun getScore(): Int = lastCheckedNumber * board
        .flatten()
        .filter { !it.isChecked }
        .sumOf { it.value }

    private fun BoardList.hasWinningRow(): Boolean =
        any { row -> row.all { it.isChecked } }

    private fun BoardList.hasWinningColumn(): Boolean =
        fold(
            initial = List(first().size) { true }
        ) { acc: List<Boolean>, row: List<Number> ->
            acc.zip(row) { allPreviousWereChecked, number ->
                allPreviousWereChecked && number.isChecked
            }
        }.any { allChecked -> allChecked }

    private fun BoardList.hasWinningDiagonal(): Boolean =
        first().indices.all { index -> get(index)[index].isChecked } ||
                first().indices.all { index -> get(index)[lastIndex - index].isChecked }
}

class Number(init: String) {

    val value: Int = init.toInt()

    private var _isChecked: Boolean = false
    val isChecked: Boolean
        get() = _isChecked

    fun check() {
        _isChecked = true
    }
}