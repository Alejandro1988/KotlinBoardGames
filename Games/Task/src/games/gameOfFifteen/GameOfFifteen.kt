package games.gameOfFifteen

import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game = GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {
    private val board: GameBoard<Int?> = createGameBoard(4)//el tablero del juego es 4x4 (15 piezas y un espacio vacio)

    override fun initialize() {
        board.initializer(initializer.initialPermutation)
    }

    private fun GameBoard<Int?>.initializer(initialList: List<Int>) {
        val elements: MutableList<Int?> = initialList.toMutableList()
        elements.add(null)
        for (i in 1..width) {
            for (j in 1..width) {
                this[this.getCell(i, j)] = elements.removeFirst()
            }
        }
    }

    override fun canMove(): Boolean {
        return true
    }

    override fun hasWon(): Boolean {
        var number = 1
        return board.all { element -> element == null || element == number++ }
    }

    override fun processMove(direction: Direction) {
        TODO("Not yet implemented")
    }

    override fun get(i: Int, j: Int): Int? {
        TODO("Not yet implemented")
    }


}