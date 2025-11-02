package com.awin.billing;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;


public class SteinScherePapier {

    private final static String RED = "\033[31m";
    private final static String BLUE = "\033[34m";
    private final static String GREEN = "\033[32m";
    private final static String NO_COLOR = "\033[0m";

    enum Player {
        HUMAN,
        COMPUTER
    }

    enum Choice {
        STEIN(1, "Stein"),
        SCHERE(2, "Schere"),
        PAPIER(3, "Papier");

        private final int id;
        private final String name;

        Choice(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public static Choice of(int value) {
            return Arrays.stream(values())
                .filter(choice -> choice.id == value)
                .findFirst().orElseThrow();
        }
    }

    public static void main(String[] args) throws IOException {

        System.out.println("Hey, spiel mit mir Stein-Schere-Papier!");

        var humansChoice = getHumansChoice();
        var computersChoice = getComputersChoice();
        var winner = determineWinner(humansChoice, computersChoice);

        if (winner.isEmpty()) {
            System.out.println("Unentschieden!");
            return;
        }
        System.out.println("Der Gewinner steht fest: " + GREEN + getPlayerName(winner.get()) + NO_COLOR + "!");
    }

    private static Choice getComputersChoice() {
        var choiceInt = (int) Math.floor(Math.random() * 3) + 1;
        var choice = Choice.of(choiceInt);
        System.out.println("Ich habe " + BLUE + choice.name + NO_COLOR + " gewählt.");
        return choice;
    }

    private static Choice getHumansChoice() throws IOException {
        while (true) {
            System.out.println("""
             Was wählst du?
               1: Stein
               2: Schere
               3: Papier""");
            System.out.print("> ");
            var playersChoice = readSingleCharacter();
            var playersChoiceInt = playersChoice - '0';
            if (playersChoiceInt >= 1 && playersChoiceInt <= 3) {
               var choice = Choice.of(playersChoiceInt);
                System.out.println("Du hast " +  BLUE + choice.name + NO_COLOR + " gewählt.");
                return choice;
            }
            System.out.println(RED + "Ungültige Eingabe. Bitte wähle 1, 2 oder 3" + NO_COLOR);
        }
    }

    private static Optional<Player> determineWinner(Choice humansChoice, Choice computersChoice) {
        if (humansChoice == computersChoice) {
            return Optional.empty();
        }
        switch (humansChoice) {
            case STEIN:
                if (computersChoice == Choice.SCHERE) {
                    return Optional.of(Player.HUMAN);
                } else {
                    return Optional.of(Player.COMPUTER);
                }
            case SCHERE:
                if (computersChoice == Choice.PAPIER) {
                    return Optional.of(Player.HUMAN);
                } else {
                    return Optional.of(Player.COMPUTER);
                }
            case PAPIER:
                if (computersChoice == Choice.STEIN) {
                    return Optional.of(Player.HUMAN);
                } else {
                    return Optional.of(Player.COMPUTER);
                }
            default:
                return Optional.of(Player.COMPUTER);
        }
    }

    private static String getPlayerName(Player player) {
        return switch (player) {
            case HUMAN -> "Du";
            case COMPUTER -> "Ich";
        };
    }

    private static int readSingleCharacter() throws IOException {
        // read the first character of the input
        var character = System.in.read();
        // throw everything else away until newline
        while (System.in.available() > 0) { System.in.read(); }
        return character;
    }

}
