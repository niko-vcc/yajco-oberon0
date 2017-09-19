package yajco.oberon0;

public class ParserError {
    private String message;

    public ParserError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
