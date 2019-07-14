package com.simondudley.poker;

import java.util.Objects;

class Card implements Comparable<Card> {

    Rank rank;
    Suit suit;

    Card(Rank rank, Suit suit) {
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
    public int compareTo(Card o) {
        return Integer.compare(this.rank.value, o.rank.value);
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
        TEN(10, "T"),
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

        int getValue() {
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
