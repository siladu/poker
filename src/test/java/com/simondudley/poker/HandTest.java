package com.simondudley.poker;

import org.junit.Test;

import java.util.List;

import static com.simondudley.poker.Deck.Rank.*;
import static com.simondudley.poker.Deck.Suit.*;
import static com.simondudley.poker.Hand.HandValue.*;
import static org.assertj.core.api.Assertions.assertThat;

public class HandTest {

    @Test
    public void shouldBePair() {
        List<Deck.Card> hand = List.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(ACE, CLUBS),
                new Deck.Card(TWO, CLUBS),
                new Deck.Card(THREE, CLUBS),
                new Deck.Card(FOUR, CLUBS)
        );
        assertThat(Hand.from(hand)).isEqualTo(new Hand(ONE_PAIR, hand));
    }

    @Test
    public void shouldBeTwoPair() {
        List<Deck.Card> hand = List.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(ACE, CLUBS),
                new Deck.Card(TWO, CLUBS),
                new Deck.Card(TWO, HEARTS),
                new Deck.Card(FOUR, CLUBS)
        );
        assertThat(Hand.from(hand)).isEqualTo(new Hand(TWO_PAIR, hand));
    }

    @Test
    public void shouldBeThreeOfAKind() {
        List<Deck.Card> hand = List.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(ACE, CLUBS),
                new Deck.Card(ACE, HEARTS),
                new Deck.Card(TWO, HEARTS),
                new Deck.Card(FOUR, CLUBS)
        );
        assertThat(Hand.from(hand)).isEqualTo(new Hand(THREE_OF_A_KIND, hand));
    }

    @Test
    public void shouldBeStraight() {
        List<Deck.Card> hand = List.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(KING, CLUBS),
                new Deck.Card(QUEEN, HEARTS),
                new Deck.Card(JACK, HEARTS),
                new Deck.Card(TEN, CLUBS)
        );
        assertThat(Hand.from(hand)).isEqualTo(new Hand(STRAIGHT, hand));
    }

    @Test
    public void shouldBeFlush() {
        List<Deck.Card> hand = List.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(TWO, SPADES),
                new Deck.Card(SEVEN, SPADES),
                new Deck.Card(JACK, SPADES),
                new Deck.Card(NINE, SPADES)
        );
        assertThat(Hand.from(hand)).isEqualTo(new Hand(FLUSH, hand));
    }

    @Test
    public void shouldBeFullHouse() {
        List<Deck.Card> hand = List.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(ACE, CLUBS),
                new Deck.Card(ACE, HEARTS),
                new Deck.Card(TWO, HEARTS),
                new Deck.Card(TWO, CLUBS)
        );
        assertThat(Hand.from(hand)).isEqualTo(new Hand(FULL_HOUSE, hand));
    }

    @Test
    public void shouldBeFourOfAKind() {
        List<Deck.Card> hand = List.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(ACE, CLUBS),
                new Deck.Card(ACE, HEARTS),
                new Deck.Card(ACE, DIAMONDS),
                new Deck.Card(TWO, CLUBS)
        );
        assertThat(Hand.from(hand)).isEqualTo(new Hand(FOUR_OF_A_KIND, hand));
    }

    @Test
    public void shouldBeStraightFlush() {
        List<Deck.Card> hand = List.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(KING, SPADES),
                new Deck.Card(QUEEN, SPADES),
                new Deck.Card(JACK, SPADES),
                new Deck.Card(TEN, SPADES)
        );
        assertThat(Hand.from(hand)).isEqualTo(new Hand(STRAIGHT_FLUSH, hand));
    }
}