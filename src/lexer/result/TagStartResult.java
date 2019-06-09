package lexer.result;

public class TagStartResult extends Result {
    public final String name;

    public TagStartResult(String name) {
        this.name = name.trim();
    }

    public String getName() {
        return name;
    }
}
