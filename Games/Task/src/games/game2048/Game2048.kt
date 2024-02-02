package games.game2048

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game

/*
 * Your task is to implement the game 2048 https://en.wikipedia.org/wiki/2048_(video_game).
 * Implement the utility methods below.
 *
 * After implementing it you can try to play the game running 'PlayGame2048'.
 */
fun newGame2048(initializer: Game2048Initializer<Int> = RandomGame2048Initializer): Game =
    Game2048(initializer)

class Game2048(private val initializer: Game2048Initializer<Int>) : Game {
    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        repeat(2) {
            board.addNewValue(initializer)
        }
    }

    override fun canMove() = board.any { it == null }

    override fun hasWon() = board.any { it == 2048 }

    override fun processMove(direction: Direction) {
        if (board.moveValues(direction)) {
            board.addNewValue(initializer)
        }
    }

    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }
}

/*
 * Add a new value produced by 'initializer' to a specified cell in a board.
 */
fun GameBoard<Int?>.addNewValue(initializer: Game2048Initializer<Int>) {
    val newValue = initializer.nextValue(this)
    this[newValue!!.first] = newValue.second
}

/*
 * Update the values stored in a board,
 * so that the values were "moved" in a specified rowOrColumn only.
 * Use the helper function 'moveAndMergeEqual' (in Game2048Helper.kt).
 * The values should be moved to the beginning of the row (or column),
 * in the same manner as in the function 'moveAndMergeEqual'.
 * Return 'true' if the values were moved and 'false' otherwise.
 */
fun GameBoard<Int?>.moveValuesInRowOrColumn(rowOrColumn: List<Cell>): Boolean {
    val rowsUpdated = rowOrColumn
        .map { cell -> this[cell] }
        .moveAndMergeEqual { value -> value * 2 }

    if (rowsUpdated.isNotEmpty() && rowsUpdated.size < rowOrColumn.size) { //si logro mergear algo se achico
        updateBoard(rowsUpdated, rowOrColumn)
        return true
    }
    return false
}

/**
 * Se encarga de actualizar en el tablero la row actualizada
 */
private fun GameBoard<Int?>.updateBoard(rowsUpdated: List<Int>, originalRow: List<Cell>) {
    for (i in 0 until width) {
        if (i < rowsUpdated.size) this[originalRow[i]] = rowsUpdated[i]
        else this[originalRow[i]] = null
    }
}


/*
 * Update the values stored in a board,
 * so that the values were "moved" to the specified direction
 * following the rules of the 2048 game .
 * Use the 'moveValuesInRowOrColumn' function above.
 * Return 'true' if the values were moved and 'false' otherwise.
 */
fun GameBoard<Int?>.moveValues(direction: Direction): Boolean {
    val iRange: IntProgression
    val jRange: IntProgression
    val getByColumns: Boolean
    when (direction) {
        Direction.UP -> {
            iRange = IntRange(1, width)
            jRange = IntRange(1, width)
            getByColumns = true
        }

        Direction.DOWN -> {
            iRange = width downTo 1
            jRange = IntRange(1, width)
            getByColumns = true
        }

        Direction.RIGHT -> {
            iRange = IntRange(1, width)
            jRange = width downTo 1
            getByColumns = false
        }

        Direction.LEFT -> {
            iRange = IntRange(1, width)
            jRange = IntRange(1, width)
            getByColumns = false
        }
    }
    var moved = false
    if (getByColumns) {
        for (j in jRange) {
            val row = this.getColumn(iRange, j)
            moved = moved or this.moveValuesInRowOrColumn(row)
        }
    } else {
        for (i in iRange) {
            val row = this.getRow(i, jRange)
            moved = moved or this.moveValuesInRowOrColumn(row)
        }
    }
    return moved
}