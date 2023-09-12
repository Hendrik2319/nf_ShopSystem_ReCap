public class ProductNotFoundException extends Exception{
    public ProductNotFoundException(String format, Object... args) {
        super(format.formatted(args));
    }
}
