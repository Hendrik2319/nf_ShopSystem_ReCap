public class ProductIdAlreadyExistsException extends Exception {
    public ProductIdAlreadyExistsException(String format, Object... args) {
        super(format.formatted(args));
    }
}
