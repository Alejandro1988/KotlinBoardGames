package board

import board.Direction.*

fun createSquareBoard(width: Int): SquareBoard {
    return SquareBoardImpl(width)
}

open class SquareBoardImpl(override val width: Int) : SquareBoard {
    var cells: Array<Array<Cell>> = arrayOf()

    init {
        (1..width).forEach { x ->
            var fila = arrayOf<Cell>()
            (1..width).forEach { y ->
                fila += Cell(x, y) //Agreo celdas a la fila
            }
            cells += fila //agrego la fila al tablero
        }
    }

    fun validateIndex(i: Int, j: Int): Boolean {
        return (i < width) and (i >= 0) and (j < width) and (j >= 0)
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        if (!validateIndex(i - 1, j - 1)) return null
        return cells[i - 1][j - 1]
    }

    override fun getCell(i: Int, j: Int): Cell {
        if (!validateIndex(i - 1, j - 1)) throw IllegalArgumentException("indice fuera de rango")
        return cells[i - 1][j - 1]
    }

    override fun getAllCells(): Collection<Cell> {
        return cells.flatten()
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        val range = if (jRange.last > width) IntRange(jRange.first, width) else jRange
        var results = mutableListOf<Cell>()
        for (j in range) {
            results.add(cells[i - 1][j - 1])
        }
        return results
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        val range = if (iRange.last > width) IntRange(iRange.first, width) else iRange
        var results = mutableListOf<Cell>()
        for (i in range) {
            results.add(cells[i - 1][j - 1])
        }
        return results
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when (direction) {
            UP -> getCellOrNull(this.i - 1, this.j)
            DOWN -> getCellOrNull(this.i + 1, this.j)
            LEFT -> getCellOrNull(this.i, this.j - 1)
            RIGHT -> getCellOrNull(this.i, this.j + 1)
        }
    }
}

fun <T> createGameBoard(width: Int): GameBoard<T> {
    return GameBoardImpl(width)
}


class GameBoardImpl<T>(width: Int) : SquareBoardImpl(width), GameBoard<T> {
    private val boardInfoMap = mutableMapOf<Cell, T?>()

    init {
        cells.forEach { //fila
                columna ->
            columna.forEach { cell ->
                boardInfoMap[cell] = null
            }
        }
    }

    override fun get(cell: Cell): T? {
        return boardInfoMap[cell]
    }

    override fun set(cell: Cell, value: T?) {
        boardInfoMap[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return boardInfoMap.filterValues(predicate).keys
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return boardInfoMap.filterValues(predicate).keys.first()
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return boardInfoMap.values.any(predicate)
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return boardInfoMap.values.all(predicate)
    }
}