package com.simondudley.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static com.simondudley.poker.Card.Rank;
import static com.simondudley.poker.Card.Suit;

class Deck {

    private static final int SIZE = 52;

    private List<Card> cards;

    void shuffle() {
        Collections.shuffle(cards);
    }

    static Deck newInstance() {
        List<Card> cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
        return new Deck(cards);
    }

    static Deck emptyDeck() {
        return new Deck(new ArrayList<>());
    }

    private Deck(List<Card> cards) {
        this.cards = cards;
    }

    void add(Card card) {
        if (!cards.contains(card)) {
            cards.add(card);
        }
    }

    List<Card> removeOptional(int n) {

        List<Card> removed = new ArrayList<>();

        for (int i = 0; i < Math.min(n, SIZE); i++) {
            removeOptional().ifPresent(removed::add);
        }

        return removed;
    }

    Optional<Card> removeOptional() {
        Optional<Card> first = cards.stream().findFirst();
        first.ifPresent(cards::remove);
        return first;
    }

    Card remove() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Cannot remove from empty deck");
        }

        Card card = cards.stream().findFirst().get();
        cards.remove(card);
        return card;
    }

    int size() {
        return cards.size();
    }

    boolean isEmpty() {
        return cards.size() == 0;
    }

    @Override
    public String toString() {
        return "Deck{" +
                "cards=" + cards +
                '}';
    }
}
