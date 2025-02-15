package view

import model.domain.tools.Board
import model.domain.tools.Location
import model.domain.tools.Stone.BLACK
import model.domain.tools.Stone.EMPTY
import model.domain.tools.Stone.WHITE

object BoardView {

    private const val BLACK_STONE = '●'
    private const val WHITE_STONE = '◌'
    private const val LEFT_INTERVAL = 4
    private const val RIGHT_INTERVAL = 3
    private val board = OmokBoardViewMaker.board

    fun printBoard(omokBoard: Board) {
        repeat(OmokBoardViewMaker.BOARD_SIZE) { row -> printBoardLine(row, omokBoard) }
        println(board.last())
    }

    private fun printBoardLine(row: Int, omokBoard: Board) {
        val stringBuilder = StringBuilder(board[row])
        repeat(OmokBoardViewMaker.BOARD_SIZE) { col ->
            val stoneState = getStoneState(Location(row, col), omokBoard)
            if (stoneState != null) {
                stringBuilder.setCharAt(LEFT_INTERVAL + col * RIGHT_INTERVAL, stoneState)
            }
        }
        println(stringBuilder.toString())
    }

    private fun getStoneState(location: Location, omokBoard: Board) =
        when (omokBoard.getStone(location)) {
            BLACK -> BLACK_STONE
            WHITE -> WHITE_STONE
            EMPTY -> null
        }
}
