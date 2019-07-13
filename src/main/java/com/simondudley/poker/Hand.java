package com.simondudley.poker;

import com.google.common.collect.Lists;

import java.util.*;

import static com.simondudley.poker.Card.Rank;
import static com.simondudley.poker.Card.Suit;
import static com.simondudley.poker.Hand.HandValue.*;
import static java.util.stream.Collectors.*;

class Hand implements Comparable<Hand> {

    enum HandValue implements Comparable<HandValue> {

        STRAIGHT_FLUSH(9), // Royal = Ace high,
        FOUR_OF_A_KIND(8),
        FULL_HOUSE(7),
        FLUSH(6),
        STRAIGHT(5),
        THREE_OF_A_KIND(4),
        TWO_PAIR(3),
        ONE_PAIR(2),
        HIGH_CARD(1);

        private final int value;

        HandValue(int value) {
            this.value = value;
        }

        int getValue() {
            return value;
        }

        static final Comparator<HandValue> HAND_VALUE_COMPARATOR = Comparator.comparingInt(HandValue::getValue);
    }

    private static final int HAND_SIZE = 5;
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
//        System.out.println();

        // of a kind...
        Map<Rank, List<Card>> byRank = hand.stream().collect(groupingBy(c -> c.rank));
        Map<Rank, Integer> sizeByRank = byRank.entrySet().stream().collect(toMap(Map.Entry::getKey, e -> e.getValue().size()));
        List<Map.Entry<Rank, Integer>> sortedRankOccurrences = sizeByRank.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .collect(toList());
        Collections.reverse(sortedRankOccurrences);
        Map.Entry<Rank, Integer> maxOccurrence = sortedRankOccurrences.get(0);
        Map.Entry<Rank, Integer> secondOccurrence = sortedRankOccurrences.get(1);

        // Flush
        Map<Suit, List<Card>> bySuit = hand.stream().collect(groupingBy(c -> c.suit));
        Optional<Map.Entry<Suit, List<Card>>> flush = bySuit.entrySet().stream().filter(e -> e.getValue().size() == HAND_SIZE).findFirst();

        // straights
        List<Rank> sorted = hand.stream().map(e -> e.rank).sorted(Comparator.comparingInt(Rank::getValue)).collect(toList());
        Collections.reverse(sorted);
//        sorted.stream().map(r -> r.getValue()).reduce(true, (isConsecutive, rankValue) -> , (a, b) -> a - 1 == b)
        List<Integer> sortedRankValues = sorted.stream().mapToInt(Rank::getValue).boxed().collect(toList());
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

        HandValue handValue;
        if (flush.isPresent() && isConsecutive) {
            handValue = STRAIGHT_FLUSH; // highest in hand wins
        } else if (maxOccurrence.getValue() == 4) {
            handValue = FOUR_OF_A_KIND; // highest in hand wins; if four of a kind on the board, then kicker
        } else if (maxOccurrence.getValue() == 3 && secondOccurrence.getValue() == 2) {
            handValue = FULL_HOUSE; // highest three wins, then highest pair, then kicker
        } else if (flush.isPresent()) {
            handValue = FLUSH; // highest non-equal rank card wins (even if it's the lowest in the flush)
        } else if (isConsecutive) {
            handValue = STRAIGHT; // highest in hand wins, straight on board - split pot unless
        } else if (maxOccurrence.getValue() == 3) {
            handValue = THREE_OF_A_KIND; // highest in hand wins
        } else if (maxOccurrence.getValue() == 2 && secondOccurrence.getValue() == 2) {
            handValue = TWO_PAIR; // highest pair wins, then highest second pair, then kicker
        } else if (maxOccurrence.getValue() == 2) {
            handValue = ONE_PAIR; // highest in hand, if equal then kicker
        } else {
            handValue = HIGH_CARD; // highest in hand, check next card
        }
        return new Hand(handValue, hand);
    }

    @Override
    public int compareTo(Hand o) {

        boolean handsValuesAreNotEqual = !this.handValue.equals(o.handValue);

        if (handsValuesAreNotEqual) {
            return compareByHandValue(o);
        } else {
            return compareByRank(o);
        }
    }

    private int compareByHandValue(Hand o) {
        return HAND_VALUE_COMPARATOR.compare(this.handValue, o.handValue);
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
        Map<Rank, List<Card>> byRank = cards.stream().collect(groupingBy(c -> c.rank));
        Map<Rank, List<Card>> byRankOther = o.cards.stream().collect(groupingBy(c -> c.rank));

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
