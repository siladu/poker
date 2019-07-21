package com.simondudley.poker;

final class Application {

    public static void main(String[] args) {

        Game game = new Game(2);
        game.playThroughGameNoBetting();

        game = new Game(2);
        game.startGame();
    }
}