package com.simondudley.poker;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.simondudley.poker.Card.Rank.*;
import static com.simondudley.poker.Card.Suit.*;
import static com.simondudley.poker.Hand.HandValue.FOUR_OF_A_KIND;
import static com.simondudley.poker.Hand.HandValue.TWO_PAIR;
import static org.assertj.core.api.Assertions.assertThat;

public class RoundTest {

    @Test
    public void testSplitPot() {
        // four of a kind on the board, two players with equal kicker

        Game.Player player1 = new Game.Player(1, List.of(
                new Card(JACK, DIAMONDS),
                new Card(TEN, SPADES)
        ));
        Game.Player player2 = new Game.Player(2, List.of(
                new Card(JACK, HEARTS),
                new Card(THREE, HEARTS)
        ));
        Game.Player player3 = new Game.Player(3, List.of(
                new Card(TEN, CLUBS),
                new Card(FIVE, CLUBS)
        ));
        var players = List.of(player1, player2, player3);

        var board = List.of(
                new Card(NINE, SPADES),
                new Card(NINE, HEARTS),
                new Card(NINE, DIAMONDS),
                new Card(NINE, CLUBS),
                new Card(SEVEN, DIAMONDS)
        );

        var round = new Round();

        Map<Game.Player, Hand> playersBestHands = round.determinePlayersBestHands(players, board);
        Map<Game.Player, Hand> winners = round.determineWinners(playersBestHands);
        assertThat(winners).hasSize(2);
        assertThat(winners).containsKey(player1);
        assertThat(winners).containsKey(player2);
        assertThat(winners).doesNotContainKey(player3);
        assertThat(winners.get(player1).getHandValue()).isEqualTo(FOUR_OF_A_KIND);
        assertThat(winners.get(player2).getHandValue()).isEqualTo(FOUR_OF_A_KIND);
    }

    @Test
    public void testTwoPairOnBoardWithKickerWinner() {
        // two pair on the board, kicker determines winner

        Game.Player player1 = new Game.Player(1, List.of(
                new Card(JACK, DIAMONDS),
                new Card(QUEEN, SPADES)
        ));

        Game.Player player2 = new Game.Player(2, List.of(
                new Card(TEN, HEARTS),
                new Card(THREE, HEARTS)
        ));
        Game.Player player3 = new Game.Player(3, List.of(
                new Card(TEN, CLUBS),
                new Card(FIVE, CLUBS)
        ));
        var players = List.of(player1, player2, player3);

        var board = List.of(
                new Card(NINE, SPADES),
                new Card(NINE, HEARTS),
                new Card(SEVEN, HEARTS),
                new Card(THREE, SPADES),
                new Card(SEVEN, DIAMONDS)
        );

        var round = new Round();
        Map<Game.Player, Hand> playersBestHands = round.determinePlayersBestHands(players, board);
        Map<Game.Player, Hand> winners = round.determineWinners(playersBestHands);
        assertThat(winners).hasSize(1);
        assertThat(winners).containsKey(player1);
        assertThat(winners).doesNotContainKeys(player2, player3);
        assertThat(winners.get(player1).getHandValue()).isEqualTo(TWO_PAIR);
    }
}
