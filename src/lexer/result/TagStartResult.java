package lexer.result;

import com.sun.istack.internal.NotNull;

import java.util.HashMap;
import java.util.Map;

public class TagStartResult extends Result {
    private final Map<String, String> params = new HashMap<>();
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
                this.params.put(params[0].trim(), params[1].trim());
            }
        }

        if (name == null) {
            throw new IllegalStateException("empty start tag");
        }
    }

    public String getName() {
        return name;
    }

    public String getParam(@NotNull String key) {
        return params.get(key);
    }
}
