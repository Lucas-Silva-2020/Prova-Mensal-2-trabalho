package blackjack;

public class Card {
    public enum Suit {
        SPADES("\u2660"),
        CLUBS("\u2663"),
        HEARTS("\u2665"),
        DIAMONDS("\u2666");

        private String displayValue;

        Suit(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }

        @Override
        public String toString() {
            return displayValue;
        }
    }

    private final int rank;
    private final Suit suit;

    public Card(int rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public String toString() {
        String rankStr;
        switch (rank) {
            case 1:
                rankStr = "A";
                break;
            case 11:
                rankStr = "J";
                break;
            case 12:
                rankStr = "Q";
                break;
            case 13:
                rankStr = "K";
                break;
            default:
                rankStr = Integer.toString(rank);
                break;
        }
        return rankStr + suit.getDisplayValue();
    }
}
