package blackjack;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        int numPlayers;
        while (true) {
            System.out.print("Insira o número de jogadores: ");
            try {
                numPlayers = scanner.nextInt();
                if (numPlayers < 1) {
                    System.out.println("Precisa de pelo menos um jogador!");
                    continue;
                }
                if (numPlayers > 8) {
                    System.out.println("Tem jogadores demais! Insira um número menor ou igual a 8.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Inválido. Tente novamente.");
            }
        }

        System.out.println("Iniciando um jogo de Blackjack com " + numPlayers + (numPlayers > 1 ? " jogadores" : " jogador"));
        final Game game = new Game(numPlayers);
        try {
            game.run();
        } catch (DeckEmptyException e) {
            System.out.println("Sem cartas.");
        }
    }
}
