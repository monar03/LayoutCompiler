package layout.design.lexer;

public class ClassResult extends Result {
    private final String name;

    public ClassResult(String name) {
        this.name = name.trim();
    }

    public String getName() {
        return name;
    }
}
