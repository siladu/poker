package com.simondudley.poker;

import org.junit.Test;

import java.util.List;

import static com.simondudley.poker.Card.Rank.*;
import static com.simondudley.poker.Card.Suit.*;
import static com.simondudley.poker.Hand.HandValue.*;
import static org.assertj.core.api.Assertions.assertThat;

public class HandTest {

    @Test
    public void shouldBePair() {
        List<Card> hand = List.of(
                new Card(ACE, SPADES),
                new Card(ACE, CLUBS),
                new Card(TWO, CLUBS),
                new Card(THREE, CLUBS),
                new Card(FOUR, CLUBS)
        );
        assertThat(Hand.from(hand)).isEqualTo(new Hand(ONE_PAIR, hand));
    }

    @Test
    public void shouldBeTwoPair() {
        List<Card> hand = List.of(
                new Card(ACE, SPADES),
                new Card(ACE, CLUBS),
                new Card(TWO, CLUBS),
                new Card(TWO, HEARTS),
                new Card(FOUR, CLUBS)
        );
        assertThat(Hand.from(hand)).isEqualTo(new Hand(TWO_PAIR, hand));
    }

    @Test
    public void shouldBeThreeOfAKind() {
        List<Card> hand = List.of(
                new Card(ACE, SPADES),
                new Card(ACE, CLUBS),
                new Card(ACE, HEARTS),
                new Card(TWO, HEARTS),
                new Card(FOUR, CLUBS)
        );
        assertThat(Hand.from(hand)).isEqualTo(new Hand(THREE_OF_A_KIND, hand));
    }

    @Test
    public void shouldBeStraight() {
        List<Card> hand = List.of(
                new Card(ACE, SPADES),
                new Card(QUEEN, HEARTS),
                new Card(KING, CLUBS),
                new Card(JACK, HEARTS),
                new Card(TEN, CLUBS)
        );
        assertThat(Hand.from(hand)).isEqualTo(new Hand(STRAIGHT, hand));
    }

    @Test
    public void shouldBeFlush() {
        List<Card> hand = List.of(
                new Card(ACE, SPADES),
                new Card(TWO, SPADES),
                new Card(SEVEN, SPADES),
                new Card(JACK, SPADES),
                new Card(NINE, SPADES)
        );
        assertThat(Hand.from(hand)).isEqualTo(new Hand(FLUSH, hand));
    }

    @Test
    public void shouldBeFullHouse() {
        List<Card> hand = List.of(
                new Card(ACE, SPADES),
                new Card(ACE, CLUBS),
                new Card(ACE, HEARTS),
                new Card(TWO, HEARTS),
                new Card(TWO, CLUBS)
        );
        assertThat(Hand.from(hand)).isEqualTo(new Hand(FULL_HOUSE, hand));
    }

    @Test
    public void shouldBeFourOfAKind() {
        List<Card> hand = List.of(
                new Card(ACE, SPADES),
                new Card(ACE, CLUBS),
                new Card(ACE, HEARTS),
                new Card(ACE, DIAMONDS),
                new Card(TWO, CLUBS)
        );
        assertThat(Hand.from(hand)).isEqualTo(new Hand(FOUR_OF_A_KIND, hand));
    }

    @Test
    public void shouldBeStraightFlush() {
        List<Card> hand = List.of(
                new Card(ACE, SPADES),
                new Card(KING, SPADES),
                new Card(QUEEN, SPADES),
                new Card(JACK, SPADES),
                new Card(TEN, SPADES)
        );
        assertThat(Hand.from(hand)).isEqualTo(new Hand(STRAIGHT_FLUSH, hand));
    }
}