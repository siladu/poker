package com.simondudley.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static com.simondudley.poker.Card.Rank;
import static com.simondudley.poker.Card.Suit;

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

    public List<Card> removeOptional(int n) {

        List<Card> removed = new ArrayList<>();

        for (int i = 0; i < Math.min(n, SIZE); i++) {
            removeOptional().ifPresent(removed::add);
        }

        return removed;
    }

    public Optional<Card> removeOptional() {
        Optional<Card> first = cards.stream().findFirst();
        first.ifPresent(cards::remove);
        return first;
    }

    public Card remove() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Cannot remove from empty deck");
        }

        Card card = cards.stream().findFirst().get();
        cards.remove(card);
        return card;
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
}
