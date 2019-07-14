package com.simondudley.poker;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Stream;

import static com.simondudley.poker.Card.Rank;
import static com.simondudley.poker.Card.Suit;
import static com.simondudley.poker.Hand.HandValue.*;
import static java.util.stream.Collectors.*;

class Hand implements Comparable<Hand> {

    enum HandValue {
        HIGH_CARD,
        ONE_PAIR,
        TWO_PAIR,
        THREE_OF_A_KIND,
        STRAIGHT,
        FLUSH,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        STRAIGHT_FLUSH, // Royal = Ace high
    }

    private final HandValue handValue;
    private final List<Card> cards;

    Hand(HandValue handValue, List<Card> cards) {
        this.handValue = handValue;
        this.cards = Lists.reverse(cards.stream().sorted().collect(toList()));
    }

    HandValue getHandValue() {
        return handValue;
    }

    static Hand from(List<Card> hand) {
        return new Hand(determineHandValue(hand), hand);
    }

    private static HandValue determineHandValue(List<Card> hand) {
        if (hand.size() != 5) {
            throw new IllegalArgumentException("There must be five cards in yo' hand");
        }

        // x of a kind...
        List<Integer> frequencies = getRankFrequencies(hand);

        HandValue handValue;
        if (isStraight(hand) && isFlush(hand)) {
            handValue = STRAIGHT_FLUSH; // highest in hand wins
        } else if (frequencies.contains(4)) {
            handValue = FOUR_OF_A_KIND; // highest in hand wins; if four of a kind on the board, then kicker
        } else if (frequencies.contains(3) && frequencies.contains(2)) {
            handValue = FULL_HOUSE; // highest three wins, then highest pair, then kicker
        } else if (isFlush(hand)) {
            handValue = FLUSH; // highest non-equal rank card wins (even if it's the lowest in the flush)
        } else if (isStraight(hand)) {
            handValue = STRAIGHT; // highest in hand wins, straight on board - split pot unless
        } else if (frequencies.contains(3)) {
            handValue = THREE_OF_A_KIND; // highest in hand wins
        } else if (frequencies.stream().filter(f -> f == 2).count() == 2) {
            handValue = TWO_PAIR; // highest pair wins, then highest second pair, then kicker
        } else if (frequencies.contains(2)) {
            handValue = ONE_PAIR; // highest in hand, if equal then kicker
        } else {
            handValue = HIGH_CARD; // highest in hand, check next card
        }
        return handValue;
    }

    private static List<Integer> getRankFrequencies(List<Card> hand) {
        Map<Rank, Long> frequencyByRank = hand.stream().collect(groupingBy(c -> c.rank, () -> new EnumMap<>(Rank.class), counting()));
        return frequencyByRank.values().stream().map(Long::intValue).collect(toList());
    }

    private static boolean isStraight(List<Card> hand) {
        List<Integer> sortedRankValues = hand.stream()
                .map(e -> e.rank.getValue())
                .sorted()
                .collect(toList());

        for (int i = 1; i < sortedRankValues.size(); i++) {
            if (sortedRankValues.get(i - 1) != sortedRankValues.get(i) - 1) {
                return false;
            }
        }
        return true;
    }

    private static boolean isFlush(List<Card> hand) {
        return Stream.of(Suit.values()).anyMatch(suit -> hand.stream().allMatch(card -> card.suit.equals(suit)));
    }

    @Override
    public int compareTo(Hand o) {

        boolean handsValuesAreNotEqual = !this.handValue.equals(o.handValue);

        if (handsValuesAreNotEqual) {
            return this.getHandValue().compareTo(o.getHandValue());
        } else {
            return compareByRank(o);
        }
    }

    private int compareByRank(Hand o) {
        if (FULL_HOUSE.equals(handValue)) {
            return compareFullHouse(o);
        }

        // assumes cards are sorted in reverse order
        // compare highest ranks in hand, individually
        Iterator<Card> otherator = o.cards.iterator();
        for (Card highCard : this.cards) {
            Card otherHighCard = otherator.next();
            if (!highCard.rank.equals(otherHighCard.rank)) {
                return highCard.rank.compareTo(otherHighCard.rank);
            } // else continue
        }

        return 0; // then they must be equal
    }

    private int compareFullHouse(Hand o) {
        Map<Rank, List<Card>> byRank = cards.stream().collect(groupingBy(c -> c.rank, () -> new EnumMap<>(Rank.class), toList()));
        Map<Rank, List<Card>> byRankOther = o.cards.stream().collect(groupingBy(c -> c.rank, () -> new EnumMap<>(Rank.class), toList()));

        // compare three of a kind
        List<Map.Entry<Rank, List<Card>>> threes = byRank.entrySet().stream().filter(e -> e.getValue().size() == 3).collect(toList());
        List<Map.Entry<Rank, List<Card>>> threesOther = byRankOther.entrySet().stream().filter(e -> e.getValue().size() == 3).collect(toList());
        Rank threesRank = threes.get(0).getKey();
        Rank threesRankOther = threesOther.get(0).getKey();
        if (!threesRank.equals(threesRankOther)) {
            return threesRank.compareTo(threesRankOther);
        }

        // compare pair
        List<Map.Entry<Rank, List<Card>>> pair = byRank.entrySet().stream().filter(e -> e.getValue().size() == 2).collect(toList());
        List<Map.Entry<Rank, List<Card>>> pairOther = byRankOther.entrySet().stream().filter(e -> e.getValue().size() == 2).collect(toList());
        Rank pairRank = pair.get(0).getKey();
        Rank pairRankOther = pairOther.get(0).getKey();
        return pairRank.compareTo(pairRankOther);
    }

    @Override
    public int hashCode() {
        return Objects.hash(handValue, cards);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hand)) return false;
        Hand other = (Hand) o;

        Iterator<Card> otherator = other.cards.iterator();
        for (Card highCard : this.cards) {
            Card otherHighCard = otherator.next();
            if (!highCard.rank.equals(otherHighCard.rank)) {
                return false;
            } // else continue
        }

        return handValue == other.handValue;
    }

    @Override
    public String toString() {
        return "Hand{" +
                "handValue=" + handValue +
                ", cards=" + cards +
                '}';
    }
}
