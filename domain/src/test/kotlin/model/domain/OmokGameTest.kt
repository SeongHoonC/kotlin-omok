package model.domain

import model.domain.state.BlackTurn
import model.domain.tools.Board
import model.domain.tools.Dot
import model.domain.tools.Stone
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OmokGameTest {
    @Test
    fun `돌을 놓을 수 없으면 상태가 바뀌지 않는다 `() {
        // given
        val dots = mutableListOf(Dot(1, 1), Dot(1, 1))
        fun getTestDot(stone: Stone): Dot = dots.removeFirst()
        val state = BlackTurn(Board.from(15))
        val omokGame = OmokGame()

        // when
        val before = omokGame.playNextTurn(state, getTestDot(state.stone))
        val actual = omokGame.playNextTurn(before, getTestDot(before.stone))

        // then
        assertThat(actual).isInstanceOf(before.javaClass)
    }

    @Test
    fun `돌을 놓을 수 있으면 상태가 바뀐다`() {
// given
        val dots = mutableListOf(Dot(1, 1), Dot(2, 2))
        fun getTestDot(stone: Stone): Dot = dots.removeFirst()
        val state = BlackTurn(Board.from(15))
        val omokGame = OmokGame()

        // when
        val before = omokGame.playNextTurn(state, getTestDot(state.stone))
        val actual = omokGame.playNextTurn(before, getTestDot(before.stone))

        // then
        assertThat(actual).isNotInstanceOf(before.javaClass)
    }
}
