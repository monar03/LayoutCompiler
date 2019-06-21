package jp.aquagear.layout.compiler.render.lexer.result;

import jp.aquagear.layout.compiler.render.lexer.StringStream;

import java.util.HashMap;
import java.util.Map;

public class TagStartResult extends Result {
    private final Map<String, Parameter> params = new HashMap<>();
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
                this.params.put(params[0].trim(), parseValue(params[1].trim().replace("\"", "")));
            }
        }

        if (name == null) {
            throw new IllegalStateException("empty start tag");
        }
    }

    private Parameter parseValue(String s) {
        final StringBuilder builder = new StringBuilder();
        final StringStream stream = new StringStream(s);
        while (!stream.isEnd()) {
            final char c = stream.getChar();
            switch (c) {
                case '{': {
                    stream.next();
                    if (stream.getChar() == '{') {
                        if (builder.length() > 0) {
                            throw new IllegalStateException("parameter cannot mixed value.");
                        }
                        stream.next();
                        return variableParse(stream);
                    }
                }
                default: {
                    builder.append(c);
                    stream.next();
                    break;
                }
            }
        }

        return new Parameter(builder.toString(), Type.CONST);
    }

    private Parameter variableParse(StringStream stream) {
        final StringBuilder builder = new StringBuilder();
        while (!stream.isEnd()) {
            final char c = stream.getChar();
            switch (c) {
                case '}': {
                    stream.next();
                    if (stream.getChar() == '}') {
                        return new Parameter(builder.toString(), Type.VARIABLE);
                    }
                }
                default: {
                    stream.next();
                    builder.append(c);
                }
            }
        }

        throw new IllegalStateException("cannot variable parameter");
    }

    public String getName() {
        return name;
    }

    public Map<String, Parameter> getParam() {
        return new HashMap<>(params);
    }

    public static class Parameter {
        public final String value;
        public final Type type;

        Parameter(String value, Type type) {
            this.value = value;
            this.type = type;
        }
    }
}
