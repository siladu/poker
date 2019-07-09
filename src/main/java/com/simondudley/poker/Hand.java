package com.simondudley.poker;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public enum Hand implements Comparable<Hand> {

    STRAIGHT_FLUSH(8), // Royal = Ace high,
    FOUR_OF_A_KIND(9),
    FULL_HOUSE(7),
    FLUSH(6),
    STRAIGHT(5),
    THREE_OF_A_KIND(4),
    TWO_PAIR(3),
    ONE_PAIR(2),
    HIGH_CARD(1);

    private final int value;
    Hand(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    private static final int HAND_SIZE = 5;

    public static Hand from(Set<Deck.Card> hand) {
//        System.out.println();

        // of a kind...
        Map<Deck.Rank, List<Deck.Card>> byRank = hand.stream().collect(Collectors.groupingBy(c -> c.rank));
//        System.out.println(byRank);
        Map<Deck.Rank, Integer> sizeByRank = byRank.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().size()));
        List<Map.Entry<Deck.Rank, Integer>> sortedRankOccurrences = sizeByRank.entrySet().stream()
                .sorted(Comparator.comparingInt(e -> e.getValue()))
                .collect(toList());
        Collections.reverse(sortedRankOccurrences);
        Map.Entry<Deck.Rank, Integer> maxOccurrence = sortedRankOccurrences.get(0);
        Map.Entry<Deck.Rank, Integer> secondOccurrence = sortedRankOccurrences.get(1);

        // Flush
        Map<Deck.Suit, List<Deck.Card>> bySuit = hand.stream().collect(Collectors.groupingBy(c -> c.suit));
//        System.out.println(bySuit);
        Optional<Map.Entry<Deck.Suit, List<Deck.Card>>> flush = bySuit.entrySet().stream().filter(e -> e.getValue().size() == HAND_SIZE).findFirst();

        // straights
        List<Deck.Rank> sorted = hand.stream().map(e -> e.rank).sorted(Comparator.comparingInt(Deck.Rank::getValue)).collect(toList());
        Collections.reverse(sorted);
        Deck.Rank highCard = sorted.get(0);
//        sorted.stream().map(r -> r.getValue()).reduce(true, (isConsecutive, rankValue) -> , (a, b) -> a - 1 == b)
        List<Integer> sortedRankValues = sorted.stream().mapToInt(Deck.Rank::getValue).boxed().collect(toList());
        boolean isConsecutive = true;
        Integer previous = null;
        for (Integer rankValue : sortedRankValues) {
            if (previous != null) {
                isConsecutive = ((previous - 1) == rankValue);
                if (!isConsecutive)
                    break;
            }
            previous = rankValue;
        }
//        Streams.forEachPair();

        if (flush.isPresent() && isConsecutive) {
            return STRAIGHT_FLUSH;
        } else if (maxOccurrence.getValue() == 4) {
            return FOUR_OF_A_KIND;
        } else if (maxOccurrence.getValue() == 3 && secondOccurrence.getValue() == 2) {
            return FULL_HOUSE;
        } else if (flush.isPresent()) {
            return FLUSH;
        } else if (isConsecutive) {
            return STRAIGHT;
        } else if (maxOccurrence.getValue() == 3) {
            return THREE_OF_A_KIND;
        } else if (maxOccurrence.getValue() == 2 && secondOccurrence.getValue() == 2) {
            return TWO_PAIR;
        } else if (maxOccurrence.getValue() == 2) {
            return ONE_PAIR;
        } else {
            return HIGH_CARD;
        }
    }
}
