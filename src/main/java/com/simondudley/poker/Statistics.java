package com.simondudley.poker;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;
import static com.simondudley.poker.Hand.HandValue;

final class Statistics {

    public static void main(String[] args) {

        runTrialsFor(10000, 4);
//        runTrialsFor( 10000, 5);
//        runTrialsFor(10000, 6);
    }

    private static void runTrialsFor(int trials, int numPlayers) {
        Map<HandValue, Integer> frequencyByHandValue = new HashMap<>();
        IntStream.rangeClosed(1, trials).forEach((i) -> {
            Game game = new Game();
            Map<Game.Player, Hand> playersBestHands = game.startGame(numPlayers);
            List<Hand> hands = new ArrayList<>(playersBestHands.values());

            System.out.println(playersBestHands);
            Map<HandValue, List<Hand>> handsByHandValue = hands.stream().collect(groupingBy(Hand::getHandValue));
            Map<HandValue, Integer> handValueFrequencies = handsByHandValue.entrySet().stream().collect(toMap(Map.Entry::getKey, e -> e.getValue().size()));
            System.out.println(handValueFrequencies);
            for (Map.Entry<HandValue, Integer> entry : handValueFrequencies.entrySet()) {
                frequencyByHandValue.computeIfPresent(entry.getKey(), (handValue, currFreq) -> currFreq + entry.getValue());
                frequencyByHandValue.putIfAbsent(entry.getKey(), entry.getValue());
            }
        });

        System.out.println(String.format("For %s trials of %s players:", trials, numPlayers));
        List<Map.Entry<HandValue, Integer>> sorted = frequencyByHandValue.entrySet().stream().sorted(comparing(Map.Entry::getValue)).collect(toList());
        List<Map.Entry<HandValue, Integer>> reverse = Lists.reverse(sorted);
        for (Map.Entry<HandValue, Integer> entry : reverse) {
            double handPercentage = 100 * entry.getValue() / (double) (trials * numPlayers);
            System.out.println(String.format("%s%% %s %s", handPercentage, entry.getKey(), entry.getValue()));
        }
    }
}