package lexer.result;

import lexer.Type;

public class TagEndResult extends Result {
    public final String name;

    public TagEndResult(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
