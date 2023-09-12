public class NotEnoughAmountException extends Exception {
    public NotEnoughAmountException(String format, Object... args) {
        super(format.formatted(args));
    }
}
