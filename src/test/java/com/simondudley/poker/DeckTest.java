package com.simondudley.poker;

import org.junit.Test;

import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class DeckTest {

    @Test
    public void fullDeckShouldRemoveCardsUntilEmpty() {

        Deck deck = Deck.newInstance();

        assertThat(deck.size()).isEqualTo(52);

        IntStream.rangeClosed(1, 52).forEach((i) -> {
            Optional<Deck.Card> card = deck.remove();
            assertThat(card).isPresent();
        });

        assertThat(deck.remove()).isEmpty();
        assertThat(deck.isEmpty()).isTrue();
    }

    @Test
    public void shouldRemoveMultipleCardsUntilEmpty() {
        Deck deck = Deck.newInstance();
        Set<Deck.Card> twoRemoved = deck.remove(2);
        assertThat(twoRemoved).hasSize(2);
        assertThat(deck.size()).isEqualTo(50);

        Set<Deck.Card> removed = deck.remove(51);
        assertThat(removed).hasSize(50);
        assertThat(deck.size()).isEqualTo(0);
    }

    @Test
    public void emptyDeckShouldAddCardsUntilFull() {

        Deck emptyDeck = Deck.emptyDeck();
        Deck fullDeck = Deck.newInstance();

        int minSize = 0;
        int maxSize = 52;

        assertThat(emptyDeck.size()).isEqualTo(minSize);
        assertThat(fullDeck.size()).isEqualTo(maxSize);

        IntStream.rangeClosed(1, maxSize).forEach((i) -> {
            fullDeck.remove().ifPresent(emptyDeck::add);
            assertThat(emptyDeck.size()).isEqualTo(minSize + i);
            assertThat(fullDeck.size()).isEqualTo(maxSize - i);
        });

        Deck secondFullDeck = Deck.newInstance();
        secondFullDeck.remove().ifPresent(emptyDeck::add);
        assertThat(emptyDeck.size()).isEqualTo(maxSize);
    }
}