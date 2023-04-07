package model.domain.state

import model.domain.tools.Board
import model.domain.tools.Location
import model.domain.tools.Stone.BLACK
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class BlackTurnTest {
    @Test
    fun `BlackTurn 에서 돌을 하나 놓으면 WhiteTurn 이 된다`() {
        // given
        val board = Board.from(15).apply {
            placeStone(Location(1, 1), BLACK)
        }
        val state: State = BlackTurn(board)

        // when
        val actual = state.place(Location(1, 1))

        // then
        assertThat(actual is WhiteTurn).isTrue
    }

    @Test
    fun `BlackTurn 에서 오목이 발생하면 종료되고 바둑돌은 검정색이다`() {
        // given
        val board = Board.from(15).apply {
            placeStone(Location(1, 1), BLACK)
            placeStone(Location(2, 2), BLACK)
            placeStone(Location(3, 3), BLACK)
            placeStone(Location(4, 4), BLACK)
        }
        val state: State = BlackTurn(board)

        // when
        val actual = state.place(Location(5, 5))

        // then
        assertAll(
            { assertThat(actual is End).isTrue },
            { assertThat(actual.stone).isEqualTo(BLACK) },
        )
    }

    @Test
    fun `BlackTurn 은 금수 룰이 적용되어 BlackTurn 을 반환한다`() {
        // given
        val board = Board.from(15).apply {
            placeStone(Location(5, 6), BLACK)
            placeStone(Location(5, 7), BLACK)
            placeStone(Location(6, 5), BLACK)
            placeStone(Location(7, 5), BLACK)
        }
        val state: State = BlackTurn(board)

        // when
        val actual = state.place(Location(5, 5))

        // then
        assertThat(actual is BlackTurn).isTrue
    }

    @Test
    fun `BlackTurn 은 장목 룰이 적용되어 BlackTurn 을 반환한다`() {
        // given
        val board = Board.from(15).apply {
            placeStone(Location(0, 1), BLACK)
            placeStone(Location(0, 2), BLACK)
            placeStone(Location(0, 4), BLACK)
            placeStone(Location(0, 5), BLACK)
            placeStone(Location(0, 6), BLACK)
        }
        val state: State = BlackTurn(board)

        // when
        val actual = state.place(Location(0, 3))

        // then
        assertThat(actual is BlackTurn).isTrue
    }
}
