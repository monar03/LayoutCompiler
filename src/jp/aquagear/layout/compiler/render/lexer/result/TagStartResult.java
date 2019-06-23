package jp.aquagear.layout.compiler.render.lexer.result;

import java.util.HashMap;
import java.util.Map;

public class TagStartResult extends Result {
    private final Map<String, StringVariable.Parameter> params = new HashMap<>();
    private String name;

    public TagStartResult(String s) {
        boolean firstElementEnd = false;
        final String[] list = s.trim().split(" ");
        for (String str : list) {
            if (str.isEmpty()) {
                continue;
            }

            if (!firstElementEnd) {
                name = str;
                firstElementEnd = true;
            } else {
                final String[] params = str.split("=");
                //@XXX "のtrimをどうするか考える
                this.params.put(params[0].trim(), StringVariable.variableParse(params[1].trim().replace("\"", "")));
            }
        }

        if (name == null) {
            throw new IllegalStateException("empty start tag");
        }
    }

    public String getName() {
        return name;
    }

    public Map<String, StringVariable.Parameter> getParam() {
        return new HashMap<>(params);
    }
}
