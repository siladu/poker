package com.simondudley.poker;

import javax.annotation.concurrent.ThreadSafe;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@ThreadSafe
public class Game {

    private final List<Player> players;

    Game(int numberOfPlayers) {
        this.players = createPlayers(numberOfPlayers);
    }

    void startGame() {
        Round round = new Round();
        round.startRound(players);
        // bet on hand
        // bet on flop
        // bet on turn
        // bet on river
    }

    private List<Game.Player> createPlayers(int n) {
        return IntStream.rangeClosed(1, n).mapToObj(Game.Player::new).collect(toList());
    }

    public static class Player {

        int id;
        List<Card> pocket;

        Player(int id) {
            this.id = id;
        }

        Player(int id, List<Card> pocket) {
            this.id = id;
            this.pocket = pocket;
        }

        @Override
        public String toString() {
            return "Player{" +
                    "id=" + (id < 10 ? "0" + id : id) +
                    ", pocket=" + pocket +
                    '}';
        }
    }
}