package com.simondudley.poker;

import java.util.*;

public class Deck {

    private static final int SIZE = 52;

    private List<Card> cards;

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public static Deck newInstance() {
        List<Card> cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
        return new Deck(cards);
    }

    public static Deck emptyDeck() {
        return new Deck(new ArrayList<>());
    }

    private Deck(List<Card> cards) {
        this.cards = cards;
    }

    public void add(Card card) {
        if (!cards.contains(card)) {
            cards.add(card);
        }
    }

    public Set<Card> remove(int n) {

        Set<Card> removed = new HashSet<>();

        for (int i = 0; i < Math.min(n, SIZE); i++) {
            remove().ifPresent(removed::add);
        }

        return removed;
    }

    public Optional<Card> remove() {
        Optional<Card> first = cards.stream().findFirst();
        first.ifPresent(cards::remove);
        return first;
    }

    public int size() {
        return cards.size();
    }

    public boolean isEmpty() {
        return cards.size() == 0;
    }

    @Override
    public String toString() {
        return "Deck{" +
                "cards=" + cards +
                '}';
    }

    public static class Card implements Comparable {

        public Rank rank;
        public Suit suit;

        public Card(Rank rank, Suit suit) {
            this.rank = rank;
            this.suit = suit;
        }

        @Override
        public String toString() {
            return "Card{" +
                    "rank=" + rank +
                    ", suit=" + suit +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Card)) return false;
            Card card = (Card) o;
            return rank == card.rank &&
                    suit == card.suit;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rank, suit);
        }

        @Override
        public int compareTo(Object o) {
            return Integer.compare(this.rank.value, ((Card) o).rank.value);
        }
    }

    enum Rank {

        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        TEN(10),
        JACK(11),
        QUEEN(12),
        KING(13),
        ACE(14);

        private int value;

        Rank(int value) {
            this.value = value;
        }
    }

    enum Suit {
        SPADES, DIAMONDS, HEARTS, CLUBS
    }
}
