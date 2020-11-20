package blackjack;

public class InvalidPlayException extends Exception {
    public InvalidPlayException(String message) {
        super(message);
    }
}
