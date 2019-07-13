package com.simondudley.poker;

import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class DeckTest {

    @Test
    public void fullDeckShouldRemoveCardsUntilEmpty() {

        Deck deck = Deck.newInstance();

        assertThat(deck.size()).isEqualTo(52);

        IntStream.rangeClosed(1, 52).forEach((i) -> {
            Optional<Deck.Card> card = deck.removeOptional();
            assertThat(card).isPresent();
        });

        assertThat(deck.removeOptional()).isEmpty();
        assertThat(deck.isEmpty()).isTrue();
    }

    @Test
    public void shouldRemoveMultipleCardsUntilEmpty() {
        Deck deck = Deck.newInstance();
        List<Deck.Card> twoRemoved = deck.removeOptional(2);
        assertThat(twoRemoved).hasSize(2);
        assertThat(deck.size()).isEqualTo(50);

        List<Deck.Card> removed = deck.removeOptional(51);
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
            fullDeck.removeOptional().ifPresent(emptyDeck::add);
            assertThat(emptyDeck.size()).isEqualTo(minSize + i);
            assertThat(fullDeck.size()).isEqualTo(maxSize - i);
        });

        Deck secondFullDeck = Deck.newInstance();
        secondFullDeck.removeOptional().ifPresent(emptyDeck::add);
        assertThat(emptyDeck.size()).isEqualTo(maxSize);
    }
}