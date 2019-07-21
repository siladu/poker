package com.simondudley.poker;

import javax.annotation.concurrent.ThreadSafe;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@ThreadSafe
public class Game {

    private final List<Player> players;
    private Round round;

    Game(int numberOfPlayers) {
        this.players = createPlayers(numberOfPlayers);
    }

    //betting options to check, call, raise, or fold.

    void startGame() {
        System.out.println("******************************");
        System.out.println("*******  START GAME   ********");
        System.out.println("******************************");
        round = new Round();
        List<Player> players = round.startRound(this.players);
        players.forEach(System.out::println);
        // bet on hand
        bettingRound();
        // bet on flop
        List<Card> flop = round.flop();
        System.out.println("FLOP " + flop);
        bettingRound();
        // bet on turn
        List<Card> turn = round.turn();
        System.out.println("TURN " + turn);
        bettingRound();
        // bet on river
        List<Card> river = round.river();
        System.out.println("RIVER " + river);
        bettingRound();
        Map<Player, Hand> winners = round.determineRoundWinners();
        System.out.println("ROUND WINNERS: " + winners);
    }

    private void bettingRound() {
        for (Player player : this.players) {
            player.bet(new BigDecimal("1.00"), round);
        }
        players.forEach(System.out::println);
    }

    void playThroughGameNoBetting() {
        Round round = new Round();
        round.playThroughRound(players);
    }

    private List<Game.Player> createPlayers(int n) {
        return IntStream.rangeClosed(1, n).mapToObj(Game.Player::new).collect(toList());
    }

    public static class Player {

        int id;
        List<Card> pocket;
        BigDecimal wallet;

        Player(int id) {
            this.id = id;
            this.wallet = new BigDecimal("10.00");
        }

        Player(int id, List<Card> pocket) {
            this.id = id;
            this.pocket = pocket;
        }

        void bet(BigDecimal amount, Round round) {
            if (amount.compareTo(wallet) > 0) {
                throw new IllegalArgumentException(String.format("[Player %s] Cannot bet %s as you only have %s in your wallet", id, amount, wallet));
            }

            wallet = wallet.subtract(amount);
            System.out.println(String.format("[Player %s] Bet %s", id, amount));
            BigDecimal newPot = round.addToPot(amount);
            System.out.println("Pot at " + newPot);
        }

        @Override
        public String toString() {
            return "Player{" +
                    "id=" + (id < 10 ? "0" + id : id) +
                    ", pocket=" + pocket +
                    ", wallet=" + wallet +
                    '}';
        }
    }
}