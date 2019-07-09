package com.simondudley.poker;

import org.junit.Test;

import java.util.Set;

import static com.simondudley.poker.Deck.Rank.*;
import static com.simondudley.poker.Deck.Suit.*;
import static org.assertj.core.api.Assertions.assertThat;

public class HandTest {

    @Test
    public void shouldBePair() {
        Set<Deck.Card> hand = Set.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(ACE, CLUBS),
                new Deck.Card(TWO, CLUBS),
                new Deck.Card(THREE, CLUBS),
                new Deck.Card(FOUR, CLUBS)
        );
        assertThat(Hand.from(hand)).isEqualTo(Hand.ONE_PAIR);
    }

    @Test
    public void shouldBeTwoPair() {
        Set<Deck.Card> hand = Set.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(ACE, CLUBS),
                new Deck.Card(TWO, CLUBS),
                new Deck.Card(TWO, HEARTS),
                new Deck.Card(FOUR, CLUBS)
        );
        assertThat(Hand.from(hand)).isEqualTo(Hand.TWO_PAIR);
    }

    @Test
    public void shouldBeThreeOfAKind() {
        Set<Deck.Card> hand = Set.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(ACE, CLUBS),
                new Deck.Card(ACE, HEARTS),
                new Deck.Card(TWO, HEARTS),
                new Deck.Card(FOUR, CLUBS)
        );
        assertThat(Hand.from(hand)).isEqualTo(Hand.THREE_OF_A_KIND);
    }

    @Test
    public void shouldBeStraight() {
        Set<Deck.Card> hand = Set.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(KING, CLUBS),
                new Deck.Card(QUEEN, HEARTS),
                new Deck.Card(JACK, HEARTS),
                new Deck.Card(TEN, CLUBS)
        );
        assertThat(Hand.from(hand)).isEqualTo(Hand.STRAIGHT);
    }

    @Test
    public void shouldBeFlush() {
        Set<Deck.Card> hand = Set.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(TWO, SPADES),
                new Deck.Card(SEVEN, SPADES),
                new Deck.Card(JACK, SPADES),
                new Deck.Card(NINE, SPADES)
        );
        assertThat(Hand.from(hand)).isEqualTo(Hand.FLUSH);
    }

    @Test
    public void shouldBeFullHouse() {
        Set<Deck.Card> hand = Set.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(ACE, CLUBS),
                new Deck.Card(ACE, HEARTS),
                new Deck.Card(TWO, HEARTS),
                new Deck.Card(TWO, CLUBS)
        );
        assertThat(Hand.from(hand)).isEqualTo(Hand.FULL_HOUSE);
    }

    @Test
    public void shouldBeFourOfAKind() {
        Set<Deck.Card> hand = Set.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(ACE, CLUBS),
                new Deck.Card(ACE, HEARTS),
                new Deck.Card(ACE, DIAMONDS),
                new Deck.Card(TWO, CLUBS)
        );
        assertThat(Hand.from(hand)).isEqualTo(Hand.FOUR_OF_A_KIND);
    }

    @Test
    public void shouldBeStraightFlush() {
        Set<Deck.Card> hand = Set.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(KING, SPADES),
                new Deck.Card(QUEEN, SPADES),
                new Deck.Card(JACK, SPADES),
                new Deck.Card(TEN, SPADES)
        );
        assertThat(Hand.from(hand)).isEqualTo(Hand.STRAIGHT_FLUSH);
    }
}