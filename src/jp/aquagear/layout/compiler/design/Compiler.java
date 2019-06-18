package jp.aquagear.layout.compiler.design;

import jp.aquagear.layout.compiler.design.lexer.*;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class Compiler {
    private final Queue<Result> results = new ArrayDeque<>();

    public Map<String, Map<String, String>> compile(String s) {
        final Lexer lexer = new Lexer(s);
        results.addAll(lexer.analysis());
        return compile();
    }

    private Map<String, Map<String, String>> compile() {
        final Map<String, Map<String, String>> designMap = new HashMap<>();
        while (true) {
            final Result result = results.poll();
            if (result == null) {
                break;
            }

            if (result instanceof ClassResult) {
                designMap.put(((ClassResult) result).getName(), compileStart());
            }
        }

        return designMap;
    }

    private Map<String, String> compileStart() {
        final Result result = results.poll();
        if (result instanceof DesignStartResult) {
            return compileParams();
        }

        throw new IllegalStateException("cannot parse error.");
    }

    private Map<String, String> compileParams() {
        final Map<String, String> map = new HashMap<>();
        while (true) {
            final Result result = results.poll();
            if (result == null) {
                break;
            }

            if (result instanceof DesignEndResult) {
                return map;
            } else if (result instanceof DesignParamResult) {
                map.put(((DesignParamResult) result).getKey(), ((DesignParamResult) result).getValue());
            }
        }

        throw new IllegalStateException("cannot parse error");
    }
}
