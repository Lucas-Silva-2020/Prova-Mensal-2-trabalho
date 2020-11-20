package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    private final String name;
    private List<Card> hand = new ArrayList<>();
    private boolean passed = false;

    public Player(String name) {
        this.name = name;
    }

    public Player(Player player) {
        this.name = player.name;
        this.hand.addAll(player.hand);
        this.passed = player.passed;
    }

    public void reset() {
        hand.clear();
        passed = false;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public void hit(Card card) throws InvalidPlayException {
        if (passed) {
            throw new InvalidPlayException("Não pode comprar após passar");
        }
        hand.add(card);
    }

    public void pass() {
        passed = true;
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return Collections.unmodifiableList(hand);
    }

    public int getHandValue() {
        if (hand.size() == 2) {
            boolean hasAce = false;
            boolean hasFigure = false;
            for (Card card : hand) {
                if (card.getRank() == 1) {
                    hasAce = true;
                }
                if (card.getRank() >= 11) {
                    hasFigure = true;
                }
            }
            if (hasAce && hasFigure) {
                return 21;
            }
        }

        int totalScore = 0;
        for (Card card : hand) {
            totalScore += Math.min(10, card.getRank());
        }
        return totalScore;
    }

    public boolean isPlayable() {
        return !passed && getHandValue() <= 21;
    }

    public boolean canHit() {
        return getHandValue() < 21;
    }
}
