package com.simondudley.poker;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.simondudley.poker.Card.Rank.*;
import static com.simondudley.poker.Card.Suit.*;
import static com.simondudley.poker.Hand.HandValue.TWO_PAIR;
import static org.assertj.core.api.Assertions.assertThat;

public class GameTest {

    @Test
    public void testCase() {

        var game = new Game();
        Game.Player player1 = new Game.Player(1, List.of(
                new Card(JACK, DIAMONDS),
                new Card(QUEEN, SPADES)
        ));

        var players = List.of(
                player1,
                new Game.Player(2, List.of(
                        new Card(TEN, HEARTS),
                        new Card(THREE, HEARTS)
                )),
                new Game.Player(3, List.of(
                        new Card(TEN, CLUBS),
                        new Card(FIVE, CLUBS)
                ))
        );
        var board = List.of(
                new Card(NINE, SPADES),
                new Card(NINE, HEARTS),
                new Card(SEVEN, HEARTS),
                new Card(THREE, SPADES),
                new Card(SEVEN, DIAMONDS)
        );

        Map<Game.Player, Hand> playersBestHands = game.determinePlayersBestHands(players, board);
        Map<Game.Player, Hand> winners = game.determineWinners(playersBestHands);
        assertThat(winners).hasSize(1);
        assertThat(winners.containsKey(player1));
        assertThat(winners.get(player1).getHandValue()).isEqualTo(TWO_PAIR);
    }
}
