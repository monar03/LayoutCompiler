package jp.aquagear.layout.compiler.render.compiler;

import java.util.HashMap;
import java.util.Map;

abstract public class BlockRender extends Render {
    protected final Map<java.lang.String, java.lang.String> params = new HashMap<>();

    public Map<String, String> getParams() {
        return new HashMap<>(params);
    }

    public void setParams(Map<java.lang.String, java.lang.String> params) {
        this.params.putAll(params);
    }
}
