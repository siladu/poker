package com.simondudley.poker;

import com.google.common.collect.Sets;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

class Game {

    Map<Player, Hand> startGame(int numPlayers) {
        if (numPlayers > 23) {
            throw new IllegalArgumentException("Maximum number of players is 23 for one deck of cards");
        }

        Deck deck = createAndShuffleDeck();
        List<Player> players = createPlayers(numPlayers);
        dealToPlayers(deck, players);
        List<Card> board = dealBoard(deck);
        Map<Player, Hand> playersBestHands = determinePlayersBestHands(players, board);
        determineWinners(playersBestHands);
        return playersBestHands;
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

    private List<Player> dealToPlayers(Deck deck, List<Player> players) {
        for (Player player : players) {
            player.pocket = deck.removeOptional(2);
        }
        return players;
    }

    private List<Card> dealBoard(Deck deck) {
        List<Card> board;
        List<Card> flop = deck.removeOptional(3);
//        System.out.println("FLOP: " + flop);
        board = flop;
        Card turn = deck.remove();
//        System.out.println("TURN: " + turn);
        board.add(turn);
        Card river = deck.remove();
//        System.out.println("RIVER: " + river);
        board.add(river);
//        System.out.println("BOARD: " + board);
        return board;
    }

    Map<Player, Hand> determinePlayersBestHands(List<Player> players, List<Card> board) {
        Map<Player, Hand> playersBestHands = new HashMap<>();
        for (Player player : players) {
            playersBestHands.put(player, bestHandFor(player, board));
        }
        return playersBestHands;
    }

    Map<Player, Hand> determineWinners(Map<Player, Hand> playersBestHands) {
        Hand winningHand = playersBestHands.entrySet().stream().max(comparing(Map.Entry::getValue)).map(Map.Entry::getValue).orElseThrow();

        Map<Player, Hand> winners =
                playersBestHands.entrySet().stream()
                    .filter(e -> e.getValue().equals(winningHand))
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));

        System.out.println("WINNERS = " + winners);
        return winners;
    }

    private Hand bestHandFor(Player player, List<Card> board) {

        Set<Card> handSuperset =
            Stream.of(player.pocket, board)
                    .flatMap(Collection::stream)
                    .collect(toSet());

//        System.out.println(String.format("[PLAYER %s] Superset = %s", player.id, handSuperset));

        // combinations of 5
        Set<Set<Card>> combinations = Sets.combinations(handSuperset, 5);
        List<Hand> hands = combinations.stream().map(ArrayList::new).map(Hand::from).collect(toList());
        Optional<Hand> bestHand = hands.stream().max(Comparator.naturalOrder());

        System.out.println(String.format("%s %s %s", player, board, bestHand.get().getHandValue()));

        return bestHand.get();
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
