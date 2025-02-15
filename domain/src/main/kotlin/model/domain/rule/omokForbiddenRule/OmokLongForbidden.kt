package model.domain.rule.omokForbiddenRule

import model.domain.tools.Board
import model.domain.tools.Location
import model.domain.tools.Stone

class OmokLongForbidden(private val board: Board, private val stone: Stone) : OmokForbiddenRule {

    override fun isForbidden(location: Location): Boolean {
        val row = location.coordinationX.value
        val col = location.coordinationY.value
        val directions: List<Pair<Int, Int>> = listOf(
            Pair(-1, 1),
            Pair(0, 1),
            Pair(1, 1),
            Pair(1, 0),
        )

        for ((dx, dy) in directions) {
            var count = NOTHING
            var (x, y) = Pair(row, col)

            x -= dx * 4
            y -= dy * 4

            for (cell in 0 until 9) {
                if ((x in COORDINATION_SYSTEM_RANGE) and (y in COORDINATION_SYSTEM_RANGE)) {
                    if (x == row && y == col) {
                        count++
                    } else if (board.getStone(Location(x, y)) == stone) {
                        count++
                        if (count >= LONGOMOK) return true
                    } else {
                        count = NOTHING
                    }
                }
                x += dx
                y += dy
            }
        }
        return false
    }

    companion object {
        private const val START = 0
        private const val END = 14
        private const val NOTHING = 0
        private const val LONGOMOK = 6
        private val COORDINATION_SYSTEM_RANGE = START..END
    }
}
