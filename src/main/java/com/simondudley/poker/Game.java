package com.simondudley.poker;

import com.google.common.collect.Sets;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class Game {

    public void startGame(int numPlayers) {
        if (numPlayers > 23) {
            throw new IllegalArgumentException("Maximum number of players is 23 for one deck of cards");
        }

        Deck deck = createAndShuffleDeck();
        List<Player> players = createPlayers(numPlayers);
        dealToPlayers(deck, players);

        players.forEach(System.out::println);

        List<Deck.Card> board;
        List<Deck.Card> flop = deck.removeOptional(3);
        System.out.println("FLOP: " + flop);
        board = flop;
        Deck.Card turn = deck.remove();
        System.out.println("TURN: " + turn);
        board.add(turn);
        Deck.Card river = deck.remove();
        System.out.println("RIVER: " + river);
        board.add(river);
        System.out.println("BOARD: " + board);

        // determine winner
        Map<Player, Hand> playersBestHands = new HashMap<>();
        for (Player player : players) {
            playersBestHands.put(player, bestHandFor(player, board));
        }
        Optional<Map.Entry<Player, Hand>> winner = playersBestHands.entrySet().stream().max(Comparator.comparing(e -> e.getValue()));
        System.out.println("WINNER = " + winner.get());
    }

    private Hand bestHandFor(Player player, List<Deck.Card> board) {

        Set<Deck.Card> handSuperset =
            Stream.of(player.pocket, board)
                    .flatMap(Collection::stream)
                    .collect(toSet());

        System.out.println(String.format("[PLAYER %s] Superset = %s", player.id, handSuperset));

        // combinations of 5
        Set<Set<Deck.Card>> combinations = Sets.combinations(handSuperset, 5);
        List<Hand> hands = combinations.stream().map(ArrayList::new).map(Hand::from).collect(toList());
        Optional<Hand> bestHand = hands.stream().max(Comparator.naturalOrder());
        System.out.println(String.format("[PLAYER %s] Best Hand = %s", player.id, bestHand.get()));
        return bestHand.get();
    }

    private void dealToPlayers(Deck deck, List<Player> players) {
        for (Player player : players) {
            player.pocket = deck.removeOptional(2);
        }
    }

    private Deck createAndShuffleDeck() {
        Deck deck = Deck.newInstance();
        System.out.println(deck);
        System.out.println("SHUFFLE...");
        deck.shuffle();
        System.out.println(deck);
        return deck;
    }

    private List<Player> createPlayers(int n) {
        return IntStream.rangeClosed(1, n).mapToObj(Player::new).collect(toList());
    }

    public static class Player {

        int id;
        List<Deck.Card> pocket;

        Player(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Player{" +
                    "id=" + id +
                    ", pocket=" + pocket +
                    '}';
        }
    }
}
