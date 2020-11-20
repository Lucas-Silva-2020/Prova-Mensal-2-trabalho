package blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private final List<Player> players = new ArrayList<>();
    private final List<Round> rounds = new ArrayList<>();
    private final Deck deck = new Deck();
    private final Scanner scanner = new Scanner(System.in);

    public Game(int numPlayers) {
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Player " + (i + 1) + "'s name: ");
            players.add(new Player(scanner.nextLine()));
        }
    }

    public void run() throws DeckEmptyException {
        boolean running = true;
        int roundNumber = 1;
        while (running) {
            System.out.println();
            System.out.println("Rodada " + roundNumber);
            System.out.println("--------");
            deck.reset();
            deck.shuffle();

            for (Player player : players) {
                player.reset();
            }

            // Deal initial cards to every player
            for (int i = 0; i < 2; i++) {
                for (Player player : players) {
                    player.addCard(deck.drawCard());
                }
            }

            boolean atLeastOnePlayerIsPlayable;
            do {
                atLeastOnePlayerIsPlayable = false;
                for (Player player : players) {
                    if (!player.isPlayable()) continue;

                    System.out.println();
                    printPlayerHandAndScore(player);
                    if (player.getHandValue() == 21) {
                        System.out.println("Passou a vez.");
                        player.pass();
                        continue;
                    }

                    atLeastOnePlayerIsPlayable = true;

                    System.out.print("Você pode");
                    if (player.canHit()) System.out.print(" [C]omprar");
                    System.out.println(" [P]assar or [E]ncerrar jogo");
                    System.out.print("Sua ação? ");
                    final String action = scanner.nextLine();
                    switch (action.toUpperCase()) {
                        case "C":
                        case "COMPRAR":
                            if (player.canHit()) {
                                System.out.println("Comprar");
                                try {
                                    final Card card = deck.drawCard();
                                    System.out.println("Pegou " + card);
                                    player.hit(card);
                                    printPlayerHandAndScore(player);
                                } catch (InvalidPlayException e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            break;
                        case "P":
                        case "PASSAR":
                            System.out.println("Passar");
                            player.pass();
                            break;
                        case "E":
                        case "ENCERRAR":
                            System.out.println("Encerrar");
                            running = false;
                            break;
                        default:
                            System.out.println("Ação inválida: \"" + action + "\"");
                            break;
                    }
                    if (!running) break;
                }
            } while (running && atLeastOnePlayerIsPlayable);

            if (running) {
                System.out.println("--------------------");
                System.out.println("Fim da rodada " + roundNumber);

                final Round round = new Round(roundNumber, players);
                this.rounds.add(round);

                System.out.println("Vencedores: ");
                for (Player winner : round.getWinners()) {
                    System.out.println("  " + winner.getName() + " com pontuação de " + winner.getHandValue());
                }

                roundNumber++;
            }
        }

        System.out.println("==========================");
        if (rounds.size() > 0) {
            System.out.println("Resumo das rodadas:");
            for (final Round round : rounds) {
                System.out.println("Rodada " + round.getRoundNumber());
                for (final Player winner : round.getWinners()) {
                    System.out.println("  " + winner.getName() + " venceu com uma pontuação de " + winner.getHandValue());
                    System.out.print("    ");
                    printHand(winner);
                }
            }
        } else {
            System.out.println("Nenhuma rodada foi terminada.");
        }
    }

    private void printPlayerHandAndScore(Player player) {
        System.out.print(player.getName() + "tem essas cartas:");
        printHand(player);
        final int score = player.getHandValue();
        System.out.println("Pontuação: " + score);
        if (score > 21) {
            System.out.println("Perdeu!");
        }
    }

    private void printHand(Player player) {
        for (final Card card : player.getHand()) {
            System.out.print(" " + card);
        }
        System.out.println();
    }
}
