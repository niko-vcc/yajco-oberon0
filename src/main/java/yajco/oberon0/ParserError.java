package yajco.oberon0;

import java.util.Formatter;

public class ParserError {
    private String message;

    public ParserError(String message) {
        this.message = message;
    }

    public ParserError(String message, Object... args) {
        this.message = String.format(message, args);
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Parse error: " + message;
    }
}
