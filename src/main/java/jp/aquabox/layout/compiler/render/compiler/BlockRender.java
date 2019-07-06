package jp.aquabox.layout.compiler.render.compiler;

import jp.aquabox.layout.compiler.render.lexer.result.StringVariable;

import java.util.HashMap;
import java.util.Map;

abstract public class BlockRender extends Render {
    protected final Map<String, StringVariable.Parameter> params = new HashMap<>();
    protected final Map<String, String> styles = new HashMap<>();
    protected Mode mode = Mode.NORMAL;

    public Map<String, StringVariable.Parameter> getParams() {
        return new HashMap<>(params);
    }

    public void setParams(Map<java.lang.String, StringVariable.Parameter> params) {
        if (params.get("for") != null) {
            mode = Mode.TEMPLATE;
        }
        this.params.putAll(params);
    }

    public Map<String, String> getStyles() {
        return new HashMap<>(styles);
    }

    public void setStyles(Map<String, String> styles) {
        this.styles.putAll(styles);
    }

    public Mode getMode() {
        return mode;
    }

    public enum Mode {
        TEMPLATE,
        NORMAL
    }
}
