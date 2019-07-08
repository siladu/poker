package com.simondudley.poker;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class Game {

    public void startGame(int numPlayers) {

        Deck deck = createAndShuffleDeck();
        List<Player> players = createPlayers(numPlayers);
        dealToPlayers(deck, players);

        players.forEach(System.out::println);

        Set<Deck.Card> flop = deck.remove(3);
        System.out.println("FLOP: " + flop);
        Set<Deck.Card> turn = deck.remove(1);
        System.out.println("TURN: " + turn);
        Set<Deck.Card> river = deck.remove(1);
        System.out.println("RIVER: " + river);
    }

    private void dealToPlayers(Deck deck, List<Player> players) {
        for (Player player : players) {
            Set<Deck.Card> hand = deck.remove(2);
            player.hand = hand;
        }
    }

    private Deck createAndShuffleDeck() {
        Deck deck = Deck.newInstance();
        System.out.println(deck);
        System.out.println("SHUFFLE");
        deck.shuffle();
        System.out.println(deck);
        return deck;
    }

    private List<Player> createPlayers(int n) {
        return IntStream.rangeClosed(1, n).mapToObj(Player::new).collect(toList());
    }

    public static class Player {

        int id;
        Set<Deck.Card> hand;

        Player(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Player{" +
                    "id=" + id +
                    ", hand=" + hand +
                    '}';
        }
    }
}
