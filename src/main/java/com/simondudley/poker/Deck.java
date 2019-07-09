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
            return rank.symbol + suit.symbol;
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

        TWO(2, "2"),
        THREE(3, "3"),
        FOUR(4, "4"),
        FIVE(5, "5"),
        SIX(6, "6"),
        SEVEN(7, "7"),
        EIGHT(8, "8"),
        NINE(9, "9"),
        TEN(10, "10"),
        JACK(11, "J"),
        QUEEN(12, "Q"),
        KING(13, "K"),
        ACE(14, "A");

        private int value;

        private String symbol;
        Rank(int value, String symbol) {
            this.value = value;
            this.symbol = symbol;
        }

        public int getValue() {
            return value;
        }
    }

    enum Suit {
        SPADES("♠"),
        DIAMONDS("♦"),
        HEARTS("♥"),
        CLUBS("♣");

        private String symbol;

        Suit(String symbol) {
            this.symbol = symbol;
        }
    }
}
