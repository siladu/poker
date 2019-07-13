package com.simondudley.poker;

import org.junit.Test;

import java.util.List;

import static com.simondudley.poker.Deck.Rank.*;
import static com.simondudley.poker.Deck.Suit.*;
import static com.simondudley.poker.Hand.HandValue.*;
import static org.assertj.core.api.Assertions.assertThat;

public class HandComparisonTest {

    @Test
    public void highestStraightFlushShouldWin() {
        List<Deck.Card> highStraight = List.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(KING, SPADES),
                new Deck.Card(QUEEN, SPADES),
                new Deck.Card(JACK, SPADES),
                new Deck.Card(TEN, SPADES));

        List<Deck.Card> lowStraight = List.of(
                new Deck.Card(KING, SPADES),
                new Deck.Card(QUEEN, SPADES),
                new Deck.Card(JACK, SPADES),
                new Deck.Card(TEN, SPADES),
                new Deck.Card(NINE, SPADES));

        assertThat(Hand.from(highStraight).getHandValue()).isEqualTo(STRAIGHT_FLUSH);
        assertThat(Hand.from(highStraight)).isGreaterThan(Hand.from(lowStraight));
    }

    @Test
    public void straightFlushShouldBeEqual() {
        List<Deck.Card> equalStraight1 = List.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(KING, SPADES),
                new Deck.Card(QUEEN, SPADES),
                new Deck.Card(JACK, SPADES),
                new Deck.Card(TEN, SPADES));

        List<Deck.Card> equalStraight2 = List.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(KING, SPADES),
                new Deck.Card(QUEEN, SPADES),
                new Deck.Card(JACK, SPADES),
                new Deck.Card(TEN, SPADES));

        assertThat(Hand.from(equalStraight1).getHandValue()).isEqualTo(STRAIGHT_FLUSH);
        assertThat(Hand.from(equalStraight1)).isEqualTo(Hand.from(equalStraight2));
    }

    @Test
    public void straightFlushOnBoardShouldBeEqual() {
        List<Deck.Card> equalStraight1 = List.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(KING, SPADES),
                new Deck.Card(QUEEN, SPADES),
                new Deck.Card(JACK, SPADES),
                new Deck.Card(TEN, SPADES));

        List<Deck.Card> equalStraight2 = List.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(KING, SPADES),
                new Deck.Card(QUEEN, SPADES),
                new Deck.Card(JACK, SPADES),
                new Deck.Card(TEN, SPADES));

        assertThat(Hand.from(equalStraight1).getHandValue()).isEqualTo(STRAIGHT_FLUSH);
        assertThat(Hand.from(equalStraight1)).isEqualTo(Hand.from(equalStraight2));
    }

    @Test
    public void highestFourOfAKindShouldWin() {
        List<Deck.Card> highestFour = List.of(
                new Deck.Card(FOUR, SPADES),
                new Deck.Card(FOUR, CLUBS),
                new Deck.Card(FOUR, HEARTS),
                new Deck.Card(FOUR, DIAMONDS),
                new Deck.Card(THREE, SPADES));

        List<Deck.Card> lowestFour = List.of(
                new Deck.Card(THREE, SPADES),
                new Deck.Card(THREE, CLUBS),
                new Deck.Card(THREE, HEARTS),
                new Deck.Card(THREE, DIAMONDS),
                new Deck.Card(TWO, SPADES));

        assertThat(Hand.from(highestFour).getHandValue()).isEqualTo(FOUR_OF_A_KIND);
        assertThat(Hand.from(highestFour)).isGreaterThan(Hand.from(lowestFour));
    }

    @Test
    public void whenFourOfAKindEqual_kickerShouldWin() {
        List<Deck.Card> highestKicker = List.of(
                new Deck.Card(FOUR, SPADES),
                new Deck.Card(FOUR, CLUBS),
                new Deck.Card(FOUR, HEARTS),
                new Deck.Card(FOUR, DIAMONDS),
                new Deck.Card(THREE, SPADES));

        List<Deck.Card> lowestKicker = List.of(
                new Deck.Card(FOUR, SPADES),
                new Deck.Card(FOUR, CLUBS),
                new Deck.Card(FOUR, HEARTS),
                new Deck.Card(FOUR, DIAMONDS),
                new Deck.Card(TWO, SPADES));

        assertThat(Hand.from(highestKicker).getHandValue()).isEqualTo(FOUR_OF_A_KIND);
        assertThat(Hand.from(highestKicker)).isGreaterThan(Hand.from(lowestKicker));
    }

    @Test
    public void fourOfAKindShouldBeEqual() {
        List<Deck.Card> equalKicker1 = List.of(
                new Deck.Card(FOUR, SPADES),
                new Deck.Card(FOUR, CLUBS),
                new Deck.Card(FOUR, HEARTS),
                new Deck.Card(FOUR, DIAMONDS),
                new Deck.Card(THREE, SPADES));

        List<Deck.Card> equalKicker2 = List.of(
                new Deck.Card(FOUR, SPADES),
                new Deck.Card(FOUR, CLUBS),
                new Deck.Card(FOUR, HEARTS),
                new Deck.Card(FOUR, DIAMONDS),
                new Deck.Card(THREE, HEARTS));

        assertThat(Hand.from(equalKicker1).getHandValue()).isEqualTo(FOUR_OF_A_KIND);
        assertThat(Hand.from(equalKicker1)).isEqualTo(Hand.from(equalKicker2));
    }

    @Test
    public void highestThreeOfFullHouseShouldWin() {
        List<Deck.Card> highestThree = List.of(
                new Deck.Card(THREE, SPADES),
                new Deck.Card(THREE, CLUBS),
                new Deck.Card(THREE, HEARTS),
                new Deck.Card(TWO, SPADES),
                new Deck.Card(TWO, CLUBS));

        List<Deck.Card> lowestThree = List.of(
                new Deck.Card(TWO, SPADES),
                new Deck.Card(TWO, CLUBS),
                new Deck.Card(TWO, HEARTS),
                new Deck.Card(ACE, CLUBS),
                new Deck.Card(ACE, SPADES));

        assertThat(Hand.from(highestThree).getHandValue()).isEqualTo(FULL_HOUSE);
        assertThat(Hand.from(highestThree)).isGreaterThan(Hand.from(lowestThree));
    }

    @Test
    public void highestPairOfFullHouseShouldWin() {
        List<Deck.Card> highestPair = List.of(
                new Deck.Card(THREE, SPADES),
                new Deck.Card(THREE, CLUBS),
                new Deck.Card(THREE, HEARTS),
                new Deck.Card(ACE, CLUBS),
                new Deck.Card(ACE, SPADES));

        List<Deck.Card> lowestPair = List.of(
                new Deck.Card(THREE, SPADES),
                new Deck.Card(THREE, CLUBS),
                new Deck.Card(THREE, HEARTS),
                new Deck.Card(KING, CLUBS),
                new Deck.Card(KING, SPADES));

        assertThat(Hand.from(highestPair).getHandValue()).isEqualTo(FULL_HOUSE);
        assertThat(Hand.from(highestPair)).isGreaterThan(Hand.from(lowestPair));
    }

    @Test
    public void fullHouseShouldBeEqual() {
        List<Deck.Card> highestPair = List.of(
                new Deck.Card(THREE, SPADES),
                new Deck.Card(THREE, CLUBS),
                new Deck.Card(THREE, HEARTS),
                new Deck.Card(ACE, CLUBS),
                new Deck.Card(ACE, SPADES));

        List<Deck.Card> lowestPair = List.of(
                new Deck.Card(THREE, SPADES),
                new Deck.Card(THREE, CLUBS),
                new Deck.Card(THREE, HEARTS),
                new Deck.Card(ACE, HEARTS),
                new Deck.Card(ACE, DIAMONDS));

        assertThat(Hand.from(highestPair).getHandValue()).isEqualTo(FULL_HOUSE);
        assertThat(Hand.from(highestPair)).isEqualTo(Hand.from(lowestPair));
    }

    @Test
    public void highestFlushShouldWin() {
        List<Deck.Card> highFlush = List.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(KING, SPADES),
                new Deck.Card(JACK, SPADES),
                new Deck.Card(TEN, SPADES),
                new Deck.Card(THREE, SPADES));

        List<Deck.Card> lowFlush = List.of(
                new Deck.Card(ACE, HEARTS),
                new Deck.Card(KING, HEARTS),
                new Deck.Card(JACK, HEARTS),
                new Deck.Card(TEN, HEARTS),
                new Deck.Card(TWO, HEARTS));

        assertThat(Hand.from(highFlush).getHandValue()).isEqualTo(FLUSH);
        assertThat(Hand.from(highFlush)).isGreaterThan(Hand.from(lowFlush));
    }

    @Test
    public void flushShouldBeEqual() {
        List<Deck.Card> equalFlush1 = List.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(KING, SPADES),
                new Deck.Card(JACK, SPADES),
                new Deck.Card(TEN, SPADES),
                new Deck.Card(THREE, SPADES));

        List<Deck.Card> equalFlush2 = List.of(
                new Deck.Card(ACE, HEARTS),
                new Deck.Card(KING, HEARTS),
                new Deck.Card(JACK, HEARTS),
                new Deck.Card(TEN, HEARTS),
                new Deck.Card(THREE, HEARTS));

        assertThat(Hand.from(equalFlush1).getHandValue()).isEqualTo(FLUSH);
        assertThat(Hand.from(equalFlush1)).isEqualTo(Hand.from(equalFlush2));
    }

    @Test
    public void highestStraightShouldWin() {
        List<Deck.Card> highStraight = List.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(KING, CLUBS),
                new Deck.Card(QUEEN, HEARTS),
                new Deck.Card(JACK, SPADES),
                new Deck.Card(TEN, CLUBS));

        List<Deck.Card> lowStraight = List.of(
                new Deck.Card(KING, HEARTS),
                new Deck.Card(QUEEN, DIAMONDS),
                new Deck.Card(JACK, CLUBS),
                new Deck.Card(TEN, DIAMONDS),
                new Deck.Card(NINE, HEARTS));

        assertThat(Hand.from(highStraight).getHandValue()).isEqualTo(STRAIGHT);
        assertThat(Hand.from(highStraight)).isGreaterThan(Hand.from(lowStraight));
    }

    @Test
    public void straightShouldBeEqual() {
        List<Deck.Card> straight1 = List.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(KING, CLUBS),
                new Deck.Card(QUEEN, HEARTS),
                new Deck.Card(JACK, SPADES),
                new Deck.Card(TEN, CLUBS));

        List<Deck.Card> straight2 = List.of(
                new Deck.Card(ACE, HEARTS),
                new Deck.Card(KING, HEARTS),
                new Deck.Card(QUEEN, DIAMONDS),
                new Deck.Card(JACK, CLUBS),
                new Deck.Card(TEN, DIAMONDS));

        assertThat(Hand.from(straight1).getHandValue()).isEqualTo(STRAIGHT);
        assertThat(Hand.from(straight1)).isEqualTo(Hand.from(straight2));
    }

    @Test
    public void highestThreeOfAKindShouldWin() {
        List<Deck.Card> highThree = List.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(ACE, CLUBS),
                new Deck.Card(ACE, HEARTS),
                new Deck.Card(TWO, SPADES),
                new Deck.Card(FOUR, CLUBS));

        List<Deck.Card> lowThree = List.of(
                new Deck.Card(KING, HEARTS),
                new Deck.Card(KING, DIAMONDS),
                new Deck.Card(KING, CLUBS),
                new Deck.Card(QUEEN, DIAMONDS),
                new Deck.Card(FOUR, HEARTS));

        assertThat(Hand.from(highThree).getHandValue()).isEqualTo(THREE_OF_A_KIND);
        assertThat(Hand.from(highThree)).isGreaterThan(Hand.from(lowThree));
    }

    @Test
    public void whenThreeOfAKindEqual_firstKickerShouldWin() {
        List<Deck.Card> highestKicker = List.of(
                new Deck.Card(FOUR, SPADES),
                new Deck.Card(FOUR, CLUBS),
                new Deck.Card(FOUR, HEARTS),
                new Deck.Card(ACE, DIAMONDS),
                new Deck.Card(TWO, SPADES));

        List<Deck.Card> lowestKicker = List.of(
                new Deck.Card(FOUR, SPADES),
                new Deck.Card(FOUR, CLUBS),
                new Deck.Card(FOUR, HEARTS),
                new Deck.Card(KING, DIAMONDS),
                new Deck.Card(THREE, SPADES));

        assertThat(Hand.from(highestKicker).getHandValue()).isEqualTo(THREE_OF_A_KIND);
        assertThat(Hand.from(highestKicker)).isGreaterThan(Hand.from(lowestKicker));
    }

    @Test
    public void whenThreeOfAKindEqual_secondKickerShouldWin() {
        List<Deck.Card> highestKicker = List.of(
                new Deck.Card(FOUR, SPADES),
                new Deck.Card(FOUR, CLUBS),
                new Deck.Card(FOUR, HEARTS),
                new Deck.Card(ACE, DIAMONDS),
                new Deck.Card(THREE, SPADES));

        List<Deck.Card> lowestKicker = List.of(
                new Deck.Card(FOUR, SPADES),
                new Deck.Card(FOUR, CLUBS),
                new Deck.Card(FOUR, HEARTS),
                new Deck.Card(ACE, DIAMONDS),
                new Deck.Card(TWO, SPADES));

        assertThat(Hand.from(highestKicker).getHandValue()).isEqualTo(THREE_OF_A_KIND);
        assertThat(Hand.from(highestKicker)).isGreaterThan(Hand.from(lowestKicker));
    }

    @Test
    public void threeOfAKindShouldBeEqual() {
        List<Deck.Card> equalKicker1 = List.of(
                new Deck.Card(FOUR, SPADES),
                new Deck.Card(FOUR, CLUBS),
                new Deck.Card(FOUR, HEARTS),
                new Deck.Card(ACE, DIAMONDS),
                new Deck.Card(THREE, SPADES));

        List<Deck.Card> equalKicker2 = List.of(
                new Deck.Card(FOUR, SPADES),
                new Deck.Card(FOUR, CLUBS),
                new Deck.Card(FOUR, HEARTS),
                new Deck.Card(ACE, HEARTS),
                new Deck.Card(THREE, HEARTS));

        assertThat(Hand.from(equalKicker1).getHandValue()).isEqualTo(THREE_OF_A_KIND);
        assertThat(Hand.from(equalKicker1)).isEqualTo(Hand.from(equalKicker2));
    }

    @Test
    public void highestHighPairOfTwoPairShouldWin() {
        List<Deck.Card> highPairTwoPair = List.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(ACE, CLUBS),
                new Deck.Card(TWO, CLUBS),
                new Deck.Card(TWO, SPADES),
                new Deck.Card(FOUR, CLUBS));

        List<Deck.Card> lowPairTwoPair = List.of(
                new Deck.Card(KING, HEARTS),
                new Deck.Card(KING, DIAMONDS),
                new Deck.Card(QUEEN, HEARTS),
                new Deck.Card(QUEEN, DIAMONDS),
                new Deck.Card(FOUR, HEARTS));

        assertThat(Hand.from(highPairTwoPair).getHandValue()).isEqualTo(TWO_PAIR);
        assertThat(Hand.from(highPairTwoPair)).isGreaterThan(Hand.from(lowPairTwoPair));
    }

    @Test
    public void highestLowPairOfTwoPairShouldWin() {
        List<Deck.Card> lowTwoPair = List.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(ACE, CLUBS),
                new Deck.Card(TWO, CLUBS),
                new Deck.Card(TWO, SPADES),
                new Deck.Card(FOUR, CLUBS));

        List<Deck.Card> highTwoPair = List.of(
                new Deck.Card(ACE, HEARTS),
                new Deck.Card(ACE, DIAMONDS),
                new Deck.Card(QUEEN, HEARTS),
                new Deck.Card(QUEEN, DIAMONDS),
                new Deck.Card(FOUR, HEARTS));

        assertThat(Hand.from(highTwoPair).getHandValue()).isEqualTo(TWO_PAIR);
        assertThat(Hand.from(highTwoPair)).isGreaterThan(Hand.from(lowTwoPair));
    }

    @Test
    public void whenTwoPairEqual_kickerShouldWin() {
        List<Deck.Card> lowKicker = List.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(ACE, CLUBS),
                new Deck.Card(QUEEN, CLUBS),
                new Deck.Card(QUEEN, SPADES),
                new Deck.Card(FOUR, CLUBS));

        List<Deck.Card> highKicker = List.of(
                new Deck.Card(ACE, HEARTS),
                new Deck.Card(ACE, DIAMONDS),
                new Deck.Card(QUEEN, HEARTS),
                new Deck.Card(QUEEN, DIAMONDS),
                new Deck.Card(FIVE, HEARTS));

        assertThat(Hand.from(highKicker).getHandValue()).isEqualTo(TWO_PAIR);
        assertThat(Hand.from(highKicker)).isGreaterThan(Hand.from(lowKicker));
    }

    @Test
    public void twoPairShouldBeEqual() {
        List<Deck.Card> equalKicker1 = List.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(ACE, CLUBS),
                new Deck.Card(QUEEN, CLUBS),
                new Deck.Card(QUEEN, SPADES),
                new Deck.Card(FIVE, CLUBS));

        List<Deck.Card> equalKicker2 = List.of(
                new Deck.Card(ACE, HEARTS),
                new Deck.Card(ACE, DIAMONDS),
                new Deck.Card(QUEEN, HEARTS),
                new Deck.Card(QUEEN, DIAMONDS),
                new Deck.Card(FIVE, HEARTS));

        assertThat(Hand.from(equalKicker1).getHandValue()).isEqualTo(TWO_PAIR);
        assertThat(Hand.from(equalKicker1)).isEqualTo(Hand.from(equalKicker2));
    }

    @Test
    public void higherPairShouldWin() {
        List<Deck.Card> highPair = List.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(ACE, CLUBS),
                new Deck.Card(TWO, CLUBS),
                new Deck.Card(THREE, CLUBS),
                new Deck.Card(FOUR, CLUBS));

        List<Deck.Card> lowPair = List.of(
                new Deck.Card(ACE, HEARTS),
                new Deck.Card(TWO, DIAMONDS),
                new Deck.Card(TWO, HEARTS),
                new Deck.Card(THREE, HEARTS),
                new Deck.Card(FOUR, DIAMONDS));

        assertThat(Hand.from(highPair).getHandValue()).isEqualTo(ONE_PAIR);
        assertThat(Hand.from(highPair)).isGreaterThan(Hand.from(lowPair));
    }

    @Test
    public void whenPairEqual_kickerShouldWin() {
        List<Deck.Card> lowKicker = List.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(ACE, CLUBS),
                new Deck.Card(TWO, CLUBS),
                new Deck.Card(THREE, CLUBS),
                new Deck.Card(FOUR, CLUBS));

        List<Deck.Card> highKicker = List.of(
                new Deck.Card(ACE, HEARTS),
                new Deck.Card(ACE, DIAMONDS),
                new Deck.Card(KING, CLUBS),
                new Deck.Card(THREE, HEARTS),
                new Deck.Card(FOUR, HEARTS));

        assertThat(Hand.from(highKicker).getHandValue()).isEqualTo(ONE_PAIR);
        assertThat(Hand.from(highKicker)).isGreaterThan(Hand.from(lowKicker));
    }

    @Test
    public void whenPairEqual_kickerThreeShouldWin() {
        List<Deck.Card> lowKicker = List.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(ACE, CLUBS),
                new Deck.Card(KING, HEARTS),
                new Deck.Card(QUEEN, CLUBS),
                new Deck.Card(TWO, CLUBS));

        List<Deck.Card> highKicker = List.of(
                new Deck.Card(ACE, HEARTS),
                new Deck.Card(ACE, DIAMONDS),
                new Deck.Card(KING, CLUBS),
                new Deck.Card(QUEEN, HEARTS),
                new Deck.Card(THREE, HEARTS));

        assertThat(Hand.from(highKicker).getHandValue()).isEqualTo(ONE_PAIR);
        assertThat(Hand.from(highKicker)).isGreaterThan(Hand.from(lowKicker));
    }

    @Test
    public void pairShouldBeEqual() {
        List<Deck.Card> equalKicker1 = List.of(
                new Deck.Card(ACE, SPADES),
                new Deck.Card(ACE, CLUBS),
                new Deck.Card(KING, HEARTS),
                new Deck.Card(THREE, CLUBS),
                new Deck.Card(FOUR, CLUBS));

        List<Deck.Card> equalKicker2 = List.of(
                new Deck.Card(ACE, HEARTS),
                new Deck.Card(ACE, DIAMONDS),
                new Deck.Card(KING, CLUBS),
                new Deck.Card(THREE, HEARTS),
                new Deck.Card(FOUR, HEARTS));

        assertThat(Hand.from(equalKicker1).getHandValue()).isEqualTo(ONE_PAIR);
        assertThat(Hand.from(equalKicker1)).isEqualTo(Hand.from(equalKicker2));
    }
}