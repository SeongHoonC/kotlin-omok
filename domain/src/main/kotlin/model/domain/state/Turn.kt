package model.domain.state

import model.domain.rule.OmokRule
import model.domain.rule.omokForbiddenRule.OmokForbiddenRuleAdapter
import model.domain.tools.Board
import model.domain.tools.Location
import model.domain.tools.Stone

abstract class Turn(override val board: Board) : State {
    override fun place(location: Location): State {
        if (isForbidden(location)) return this
        val nextBoard = Board(board.system)
            .apply { placeStone(location, stone) }
        if (isOmok(nextBoard, location)) return End(nextBoard, stone)
        return nextTurn(nextBoard)
    }

    private fun isOmok(nextBoard: Board, location: Location): Boolean =
        OmokRule.isOmok(nextBoard, location, stone)

    private fun nextTurn(nextBoard: Board) = when (stone) {
        Stone.BLACK -> WhiteTurn(nextBoard)
        Stone.WHITE -> BlackTurn(nextBoard)
        else -> throw IllegalStateException()
    }

    private fun isForbidden(location: Location) =
        OmokForbiddenRuleAdapter(board, stone).isForbidden(location)
}
