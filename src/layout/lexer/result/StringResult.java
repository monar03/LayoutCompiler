package layout.lexer.result;

public class StringResult extends Result {
    private final String text;

    public StringResult(String text) {
        this.text = text.trim();
    }

    public String getText() {
        return text;
    }

}
