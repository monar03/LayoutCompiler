package lexer.result;

public class StringResult extends Result {
    public final String text;

    public StringResult(String text) {
        this.text = text.trim();
    }

    public String getText() {
        return text;
    }

}
