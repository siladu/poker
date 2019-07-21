package com.simondudley.poker;

import com.google.common.collect.Sets;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

class Round {

    Map<Game.Player, Hand> startRound(List<Game.Player> players) {
        if (players.size() > 23) {
            throw new IllegalArgumentException("Maximum number of players is 23 for one deck of cards");
        }

        Deck deck = createAndShuffleDeck();

        dealToPlayers(deck, players);
        List<Card> board = dealBoard(deck);
        Map<Game.Player, Hand> playersBestHands = determinePlayersBestHands(players, board);
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

    private List<Game.Player> dealToPlayers(Deck deck, List<Game.Player> players) {
        for (Game.Player player : players) {
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

    Map<Game.Player, Hand> determinePlayersBestHands(List<Game.Player> players, List<Card> board) {
        Map<Game.Player, Hand> playersBestHands = new HashMap<>();
        for (Game.Player player : players) {
            playersBestHands.put(player, bestHandFor(player, board));
        }
        return playersBestHands;
    }

    Map<Game.Player, Hand> determineWinners(Map<Game.Player, Hand> playersBestHands) {
        Hand winningHand = playersBestHands.entrySet().stream().max(comparing(Map.Entry::getValue)).map(Map.Entry::getValue).orElseThrow();

        Map<Game.Player, Hand> winners =
                playersBestHands.entrySet().stream()
                    .filter(e -> e.getValue().equals(winningHand))
                    .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));

        System.out.println("WINNERS = " + winners);
        return winners;
    }

    private Hand bestHandFor(Game.Player player, List<Card> board) {

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

}
