package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Round {
    private final int roundNumber;
    private final List<Player> players = new ArrayList<>();
    private final List<Player> winners = new ArrayList<>();

    public Round(final int roundNumber, final List<Player> players) {
        this.roundNumber = roundNumber;
        for (final Player player : players) {
            this.players.add(new Player(player));
        }
        determineWinners();
    }

    private void determineWinners() {
        int maxScore = 0;
        for (final Player player : players) {
            final int score = player.getHandValue();
            if (score <= 21) {
                if (score > maxScore) {
                    maxScore = score;
                    winners.clear();
                }
                if (score == maxScore) {
                    winners.add(player);
                }
            }
        }
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public List<Player> getWinners() {
        return Collections.unmodifiableList(winners);
    }
}
