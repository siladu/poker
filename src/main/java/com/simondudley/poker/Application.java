package com.simondudley.poker;

final class Application {

    public static void main(String[] args) {

        Game game = new Game(23);
        game.playThroughGameNoBetting();
    }
}