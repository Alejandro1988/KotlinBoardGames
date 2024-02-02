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
    //el tablero del juego es 4x4 (15 piezas y un espacio vacio)
    private val board: GameBoard<Int?> = createGameBoard(4)
    override fun initialize() {
        TODO("Not yet implemented")
    }

    override fun canMove(): Boolean {
        TODO("Not yet implemented")
    }

    override fun hasWon(): Boolean {
        TODO("Not yet implemented")
    }

    override fun processMove(direction: Direction) {
        TODO("Not yet implemented")
    }

    override fun get(i: Int, j: Int): Int? {
        TODO("Not yet implemented")
    }


}