package jp.aquagear.layout.compiler.render.lexer.result;

public class StringResult extends Result {
    private final StringVariable.Parameter text;

    public StringResult(String text) {
        this.text = StringVariable.variableParse(text.trim());
    }

    public StringVariable.Parameter getText() {
        return text;
    }

}
