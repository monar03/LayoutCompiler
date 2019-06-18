package jp.aquagear.layout.compiler.design.lexer;

public class DesignParamResult extends Result {
    private final String key;
    private final String value;

    public DesignParamResult(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
