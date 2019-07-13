package com.simondudley.poker;

import org.junit.Test;

import java.util.List;

import static com.simondudley.poker.Card.Rank.*;
import static com.simondudley.poker.Card.Suit.*;
import static com.simondudley.poker.Hand.HandValue.*;
import static org.assertj.core.api.Assertions.assertThat;

public class HandComparisonTest {

    @Test
    public void highestStraightFlushShouldWin() {
        List<Card> highStraight = List.of(
                new Card(ACE, SPADES),
                new Card(KING, SPADES),
                new Card(QUEEN, SPADES),
                new Card(JACK, SPADES),
                new Card(TEN, SPADES));

        List<Card> lowStraight = List.of(
                new Card(KING, SPADES),
                new Card(QUEEN, SPADES),
                new Card(JACK, SPADES),
                new Card(TEN, SPADES),
                new Card(NINE, SPADES));

        assertThat(Hand.from(highStraight).getHandValue()).isEqualTo(STRAIGHT_FLUSH);
        assertThat(Hand.from(highStraight)).isGreaterThan(Hand.from(lowStraight));
    }

    @Test
    public void straightFlushShouldBeEqual() {
        List<Card> equalStraight1 = List.of(
                new Card(ACE, SPADES),
                new Card(KING, SPADES),
                new Card(QUEEN, SPADES),
                new Card(JACK, SPADES),
                new Card(TEN, SPADES));

        List<Card> equalStraight2 = List.of(
                new Card(ACE, SPADES),
                new Card(KING, SPADES),
                new Card(QUEEN, SPADES),
                new Card(JACK, SPADES),
                new Card(TEN, SPADES));

        assertThat(Hand.from(equalStraight1).getHandValue()).isEqualTo(STRAIGHT_FLUSH);
        assertThat(Hand.from(equalStraight1)).isEqualTo(Hand.from(equalStraight2));
    }

    @Test
    public void straightFlushOnBoardShouldBeEqual() {
        List<Card> equalStraight1 = List.of(
                new Card(ACE, SPADES),
                new Card(KING, SPADES),
                new Card(QUEEN, SPADES),
                new Card(JACK, SPADES),
                new Card(TEN, SPADES));

        List<Card> equalStraight2 = List.of(
                new Card(ACE, SPADES),
                new Card(KING, SPADES),
                new Card(QUEEN, SPADES),
                new Card(JACK, SPADES),
                new Card(TEN, SPADES));

        assertThat(Hand.from(equalStraight1).getHandValue()).isEqualTo(STRAIGHT_FLUSH);
        assertThat(Hand.from(equalStraight1)).isEqualTo(Hand.from(equalStraight2));
    }

    @Test
    public void highestFourOfAKindShouldWin() {
        List<Card> highestFour = List.of(
                new Card(FOUR, SPADES),
                new Card(FOUR, CLUBS),
                new Card(FOUR, HEARTS),
                new Card(FOUR, DIAMONDS),
                new Card(THREE, SPADES));

        List<Card> lowestFour = List.of(
                new Card(THREE, SPADES),
                new Card(THREE, CLUBS),
                new Card(THREE, HEARTS),
                new Card(THREE, DIAMONDS),
                new Card(TWO, SPADES));

        assertThat(Hand.from(highestFour).getHandValue()).isEqualTo(FOUR_OF_A_KIND);
        assertThat(Hand.from(highestFour)).isGreaterThan(Hand.from(lowestFour));
    }

    @Test
    public void whenFourOfAKindEqual_kickerShouldWin() {
        List<Card> highestKicker = List.of(
                new Card(FOUR, SPADES),
                new Card(FOUR, CLUBS),
                new Card(FOUR, HEARTS),
                new Card(FOUR, DIAMONDS),
                new Card(THREE, SPADES));

        List<Card> lowestKicker = List.of(
                new Card(FOUR, SPADES),
                new Card(FOUR, CLUBS),
                new Card(FOUR, HEARTS),
                new Card(FOUR, DIAMONDS),
                new Card(TWO, SPADES));

        assertThat(Hand.from(highestKicker).getHandValue()).isEqualTo(FOUR_OF_A_KIND);
        assertThat(Hand.from(highestKicker)).isGreaterThan(Hand.from(lowestKicker));
    }

    @Test
    public void fourOfAKindShouldBeEqual() {
        List<Card> equalKicker1 = List.of(
                new Card(FOUR, SPADES),
                new Card(FOUR, CLUBS),
                new Card(FOUR, HEARTS),
                new Card(FOUR, DIAMONDS),
                new Card(THREE, SPADES));

        List<Card> equalKicker2 = List.of(
                new Card(FOUR, SPADES),
                new Card(FOUR, CLUBS),
                new Card(FOUR, HEARTS),
                new Card(FOUR, DIAMONDS),
                new Card(THREE, HEARTS));

        assertThat(Hand.from(equalKicker1).getHandValue()).isEqualTo(FOUR_OF_A_KIND);
        assertThat(Hand.from(equalKicker1)).isEqualTo(Hand.from(equalKicker2));
    }

    @Test
    public void highestThreeOfFullHouseShouldWin() {
        List<Card> highestThree = List.of(
                new Card(THREE, SPADES),
                new Card(THREE, CLUBS),
                new Card(THREE, HEARTS),
                new Card(TWO, SPADES),
                new Card(TWO, CLUBS));

        List<Card> lowestThree = List.of(
                new Card(TWO, SPADES),
                new Card(TWO, CLUBS),
                new Card(TWO, HEARTS),
                new Card(ACE, CLUBS),
                new Card(ACE, SPADES));

        assertThat(Hand.from(highestThree).getHandValue()).isEqualTo(FULL_HOUSE);
        assertThat(Hand.from(highestThree)).isGreaterThan(Hand.from(lowestThree));
    }

    @Test
    public void highestPairOfFullHouseShouldWin() {
        List<Card> highestPair = List.of(
                new Card(THREE, SPADES),
                new Card(THREE, CLUBS),
                new Card(THREE, HEARTS),
                new Card(ACE, CLUBS),
                new Card(ACE, SPADES));

        List<Card> lowestPair = List.of(
                new Card(THREE, SPADES),
                new Card(THREE, CLUBS),
                new Card(THREE, HEARTS),
                new Card(KING, CLUBS),
                new Card(KING, SPADES));

        assertThat(Hand.from(highestPair).getHandValue()).isEqualTo(FULL_HOUSE);
        assertThat(Hand.from(highestPair)).isGreaterThan(Hand.from(lowestPair));
    }

    @Test
    public void fullHouseShouldBeEqual() {
        List<Card> highestPair = List.of(
                new Card(THREE, SPADES),
                new Card(THREE, CLUBS),
                new Card(THREE, HEARTS),
                new Card(ACE, CLUBS),
                new Card(ACE, SPADES));

        List<Card> lowestPair = List.of(
                new Card(THREE, SPADES),
                new Card(THREE, CLUBS),
                new Card(THREE, HEARTS),
                new Card(ACE, HEARTS),
                new Card(ACE, DIAMONDS));

        assertThat(Hand.from(highestPair).getHandValue()).isEqualTo(FULL_HOUSE);
        assertThat(Hand.from(highestPair)).isEqualTo(Hand.from(lowestPair));
    }

    @Test
    public void highestFlushShouldWin() {
        List<Card> highFlush = List.of(
                new Card(ACE, SPADES),
                new Card(KING, SPADES),
                new Card(JACK, SPADES),
                new Card(TEN, SPADES),
                new Card(THREE, SPADES));

        List<Card> lowFlush = List.of(
                new Card(ACE, HEARTS),
                new Card(KING, HEARTS),
                new Card(JACK, HEARTS),
                new Card(TEN, HEARTS),
                new Card(TWO, HEARTS));

        assertThat(Hand.from(highFlush).getHandValue()).isEqualTo(FLUSH);
        assertThat(Hand.from(highFlush)).isGreaterThan(Hand.from(lowFlush));
    }

    @Test
    public void flushShouldBeEqual() {
        List<Card> equalFlush1 = List.of(
                new Card(ACE, SPADES),
                new Card(KING, SPADES),
                new Card(JACK, SPADES),
                new Card(TEN, SPADES),
                new Card(THREE, SPADES));

        List<Card> equalFlush2 = List.of(
                new Card(ACE, HEARTS),
                new Card(KING, HEARTS),
                new Card(JACK, HEARTS),
                new Card(TEN, HEARTS),
                new Card(THREE, HEARTS));

        assertThat(Hand.from(equalFlush1).getHandValue()).isEqualTo(FLUSH);
        assertThat(Hand.from(equalFlush1)).isEqualTo(Hand.from(equalFlush2));
    }

    @Test
    public void highestStraightShouldWin() {
        List<Card> highStraight = List.of(
                new Card(ACE, SPADES),
                new Card(KING, CLUBS),
                new Card(QUEEN, HEARTS),
                new Card(JACK, SPADES),
                new Card(TEN, CLUBS));

        List<Card> lowStraight = List.of(
                new Card(KING, HEARTS),
                new Card(QUEEN, DIAMONDS),
                new Card(JACK, CLUBS),
                new Card(TEN, DIAMONDS),
                new Card(NINE, HEARTS));

        assertThat(Hand.from(highStraight).getHandValue()).isEqualTo(STRAIGHT);
        assertThat(Hand.from(highStraight)).isGreaterThan(Hand.from(lowStraight));
    }

    @Test
    public void straightShouldBeEqual() {
        List<Card> straight1 = List.of(
                new Card(ACE, SPADES),
                new Card(KING, CLUBS),
                new Card(QUEEN, HEARTS),
                new Card(JACK, SPADES),
                new Card(TEN, CLUBS));

        List<Card> straight2 = List.of(
                new Card(ACE, HEARTS),
                new Card(KING, HEARTS),
                new Card(QUEEN, DIAMONDS),
                new Card(JACK, CLUBS),
                new Card(TEN, DIAMONDS));

        assertThat(Hand.from(straight1).getHandValue()).isEqualTo(STRAIGHT);
        assertThat(Hand.from(straight1)).isEqualTo(Hand.from(straight2));
    }

    @Test
    public void highestThreeOfAKindShouldWin() {
        List<Card> highThree = List.of(
                new Card(ACE, SPADES),
                new Card(ACE, CLUBS),
                new Card(ACE, HEARTS),
                new Card(TWO, SPADES),
                new Card(FOUR, CLUBS));

        List<Card> lowThree = List.of(
                new Card(KING, HEARTS),
                new Card(KING, DIAMONDS),
                new Card(KING, CLUBS),
                new Card(QUEEN, DIAMONDS),
                new Card(FOUR, HEARTS));

        assertThat(Hand.from(highThree).getHandValue()).isEqualTo(THREE_OF_A_KIND);
        assertThat(Hand.from(highThree)).isGreaterThan(Hand.from(lowThree));
    }

    @Test
    public void whenThreeOfAKindEqual_firstKickerShouldWin() {
        List<Card> highestKicker = List.of(
                new Card(FOUR, SPADES),
                new Card(FOUR, CLUBS),
                new Card(FOUR, HEARTS),
                new Card(ACE, DIAMONDS),
                new Card(TWO, SPADES));

        List<Card> lowestKicker = List.of(
                new Card(FOUR, SPADES),
                new Card(FOUR, CLUBS),
                new Card(FOUR, HEARTS),
                new Card(KING, DIAMONDS),
                new Card(THREE, SPADES));

        assertThat(Hand.from(highestKicker).getHandValue()).isEqualTo(THREE_OF_A_KIND);
        assertThat(Hand.from(highestKicker)).isGreaterThan(Hand.from(lowestKicker));
    }

    @Test
    public void whenThreeOfAKindEqual_secondKickerShouldWin() {
        List<Card> highestKicker = List.of(
                new Card(FOUR, SPADES),
                new Card(FOUR, CLUBS),
                new Card(FOUR, HEARTS),
                new Card(ACE, DIAMONDS),
                new Card(THREE, SPADES));

        List<Card> lowestKicker = List.of(
                new Card(FOUR, SPADES),
                new Card(FOUR, CLUBS),
                new Card(FOUR, HEARTS),
                new Card(ACE, DIAMONDS),
                new Card(TWO, SPADES));

        assertThat(Hand.from(highestKicker).getHandValue()).isEqualTo(THREE_OF_A_KIND);
        assertThat(Hand.from(highestKicker)).isGreaterThan(Hand.from(lowestKicker));
    }

    @Test
    public void threeOfAKindShouldBeEqual() {
        List<Card> equalKicker1 = List.of(
                new Card(FOUR, SPADES),
                new Card(FOUR, CLUBS),
                new Card(FOUR, HEARTS),
                new Card(ACE, DIAMONDS),
                new Card(THREE, SPADES));

        List<Card> equalKicker2 = List.of(
                new Card(FOUR, SPADES),
                new Card(FOUR, CLUBS),
                new Card(FOUR, HEARTS),
                new Card(ACE, HEARTS),
                new Card(THREE, HEARTS));

        assertThat(Hand.from(equalKicker1).getHandValue()).isEqualTo(THREE_OF_A_KIND);
        assertThat(Hand.from(equalKicker1)).isEqualTo(Hand.from(equalKicker2));
    }

    @Test
    public void highestHighPairOfTwoPairShouldWin() {
        List<Card> highPairTwoPair = List.of(
                new Card(ACE, SPADES),
                new Card(ACE, CLUBS),
                new Card(TWO, CLUBS),
                new Card(TWO, SPADES),
                new Card(FOUR, CLUBS));

        List<Card> lowPairTwoPair = List.of(
                new Card(KING, HEARTS),
                new Card(KING, DIAMONDS),
                new Card(QUEEN, HEARTS),
                new Card(QUEEN, DIAMONDS),
                new Card(FOUR, HEARTS));

        assertThat(Hand.from(highPairTwoPair).getHandValue()).isEqualTo(TWO_PAIR);
        assertThat(Hand.from(highPairTwoPair)).isGreaterThan(Hand.from(lowPairTwoPair));
    }

    @Test
    public void highestLowPairOfTwoPairShouldWin() {
        List<Card> lowTwoPair = List.of(
                new Card(ACE, SPADES),
                new Card(ACE, CLUBS),
                new Card(TWO, CLUBS),
                new Card(TWO, SPADES),
                new Card(FOUR, CLUBS));

        List<Card> highTwoPair = List.of(
                new Card(ACE, HEARTS),
                new Card(ACE, DIAMONDS),
                new Card(QUEEN, HEARTS),
                new Card(QUEEN, DIAMONDS),
                new Card(FOUR, HEARTS));

        assertThat(Hand.from(highTwoPair).getHandValue()).isEqualTo(TWO_PAIR);
        assertThat(Hand.from(highTwoPair)).isGreaterThan(Hand.from(lowTwoPair));
    }

    @Test
    public void whenTwoPairEqual_kickerShouldWin() {
        List<Card> lowKicker = List.of(
                new Card(ACE, SPADES),
                new Card(ACE, CLUBS),
                new Card(QUEEN, CLUBS),
                new Card(QUEEN, SPADES),
                new Card(FOUR, CLUBS));

        List<Card> highKicker = List.of(
                new Card(ACE, HEARTS),
                new Card(ACE, DIAMONDS),
                new Card(QUEEN, HEARTS),
                new Card(QUEEN, DIAMONDS),
                new Card(FIVE, HEARTS));

        assertThat(Hand.from(highKicker).getHandValue()).isEqualTo(TWO_PAIR);
        assertThat(Hand.from(highKicker)).isGreaterThan(Hand.from(lowKicker));
    }

    @Test
    public void twoPairShouldBeEqual() {
        List<Card> equalKicker1 = List.of(
                new Card(ACE, SPADES),
                new Card(ACE, CLUBS),
                new Card(QUEEN, CLUBS),
                new Card(QUEEN, SPADES),
                new Card(FIVE, CLUBS));

        List<Card> equalKicker2 = List.of(
                new Card(ACE, HEARTS),
                new Card(ACE, DIAMONDS),
                new Card(QUEEN, HEARTS),
                new Card(QUEEN, DIAMONDS),
                new Card(FIVE, HEARTS));

        assertThat(Hand.from(equalKicker1).getHandValue()).isEqualTo(TWO_PAIR);
        assertThat(Hand.from(equalKicker1)).isEqualTo(Hand.from(equalKicker2));
    }

    @Test
    public void higherPairShouldWin() {
        List<Card> highPair = List.of(
                new Card(ACE, SPADES),
                new Card(ACE, CLUBS),
                new Card(TWO, CLUBS),
                new Card(THREE, CLUBS),
                new Card(FOUR, CLUBS));

        List<Card> lowPair = List.of(
                new Card(ACE, HEARTS),
                new Card(TWO, DIAMONDS),
                new Card(TWO, HEARTS),
                new Card(THREE, HEARTS),
                new Card(FOUR, DIAMONDS));

        assertThat(Hand.from(highPair).getHandValue()).isEqualTo(ONE_PAIR);
        assertThat(Hand.from(highPair)).isGreaterThan(Hand.from(lowPair));
    }

    @Test
    public void whenPairEqual_kickerShouldWin() {
        List<Card> lowKicker = List.of(
                new Card(ACE, SPADES),
                new Card(ACE, CLUBS),
                new Card(TWO, CLUBS),
                new Card(THREE, CLUBS),
                new Card(FOUR, CLUBS));

        List<Card> highKicker = List.of(
                new Card(ACE, HEARTS),
                new Card(ACE, DIAMONDS),
                new Card(KING, CLUBS),
                new Card(THREE, HEARTS),
                new Card(FOUR, HEARTS));

        assertThat(Hand.from(highKicker).getHandValue()).isEqualTo(ONE_PAIR);
        assertThat(Hand.from(highKicker)).isGreaterThan(Hand.from(lowKicker));
    }

    @Test
    public void whenPairEqual_kickerThreeShouldWin() {
        List<Card> lowKicker = List.of(
                new Card(ACE, SPADES),
                new Card(ACE, CLUBS),
                new Card(KING, HEARTS),
                new Card(QUEEN, CLUBS),
                new Card(TWO, CLUBS));

        List<Card> highKicker = List.of(
                new Card(ACE, HEARTS),
                new Card(ACE, DIAMONDS),
                new Card(KING, CLUBS),
                new Card(QUEEN, HEARTS),
                new Card(THREE, HEARTS));

        assertThat(Hand.from(highKicker).getHandValue()).isEqualTo(ONE_PAIR);
        assertThat(Hand.from(highKicker)).isGreaterThan(Hand.from(lowKicker));
    }

    @Test
    public void pairShouldBeEqual() {
        List<Card> equalKicker1 = List.of(
                new Card(ACE, SPADES),
                new Card(ACE, CLUBS),
                new Card(KING, HEARTS),
                new Card(THREE, CLUBS),
                new Card(FOUR, CLUBS));

        List<Card> equalKicker2 = List.of(
                new Card(ACE, HEARTS),
                new Card(ACE, DIAMONDS),
                new Card(KING, CLUBS),
                new Card(THREE, HEARTS),
                new Card(FOUR, HEARTS));

        assertThat(Hand.from(equalKicker1).getHandValue()).isEqualTo(ONE_PAIR);
        assertThat(Hand.from(equalKicker1)).isEqualTo(Hand.from(equalKicker2));
    }
}