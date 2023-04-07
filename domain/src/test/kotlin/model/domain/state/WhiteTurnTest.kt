package model.domain.state

import model.domain.tools.Board
import model.domain.tools.Location
import model.domain.tools.Stone.WHITE
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class WhiteTurnTest {
    @Test
    fun `WhiteTurn 에서 돌을 하나 놓으면 BlackTurn 이 된다`() {
        // given
        val board = Board.from(15)
        val state: State = WhiteTurn(board)

        // when
        val actual = state.place(Location(1, 1))

        // then
        assertThat(actual is BlackTurn).isTrue
    }

    @Test
    fun `WhiteTurn 에서 오목이 발생하면 종료되고 바둑돌은 하얀색이다`() {
        // given
        val board = Board.from(15).apply {
            placeStone(Location(1, 1), WHITE)
            placeStone(Location(2, 2), WHITE)
            placeStone(Location(3, 3), WHITE)
            placeStone(Location(4, 4), WHITE)
        }
        val state: State = WhiteTurn(board)

        // when
        val actual = state.place(Location(5, 5))

        // then
        assertAll(
            { assertThat(actual is End).isTrue },
            { assertThat(actual.stone).isEqualTo(WHITE) },
        )
    }

    @Test
    fun `WhiteTurn 은 금수 룰이 적용되지 않아 BlackTurn 을 반환한다`() {
        // given
        val board = Board.from(15).apply {
            placeStone(Location(5, 6), WHITE)
            placeStone(Location(5, 7), WHITE)
            placeStone(Location(6, 5), WHITE)
            placeStone(Location(7, 5), WHITE)
        }
        val state: State = WhiteTurn(board)

        // when
        val actual = state.place(Location(5, 5))

        // then
        assertThat(actual is BlackTurn).isTrue
    }

    @Test
    fun `WhiteTurn 은 장목 룰이 적용되자 않아 BlackTurn 을 반환한다`() {
        // given
        val board = Board.from(15).apply {
            placeStone(Location(0, 1), WHITE)
            placeStone(Location(0, 2), WHITE)
            placeStone(Location(0, 4), WHITE)
            placeStone(Location(0, 5), WHITE)
            placeStone(Location(0, 6), WHITE)
        }
        val state: State = WhiteTurn(board)

        // when
        val actual = state.place(Location(0, 3))

        // then
        assertThat(actual is End).isTrue
    }
}
