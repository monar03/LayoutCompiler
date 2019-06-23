package jp.aquagear.layout.compiler.render.lexer.result;

import jp.aquagear.layout.compiler.render.lexer.StringStream;


public class StringVariable {
    public static Parameter variableParse(String s) {
        final StringBuilder builder = new StringBuilder();
        final StringStream stream = new StringStream(s);
        while (!stream.isEnd()) {
            final char c = stream.getChar();
            if (c == '{') {
                stream.next();
                if (stream.getChar() == '{') {
                    if (builder.length() > 0) {
                        throw new IllegalStateException("parameter cannot mixed value.");
                    }
                    stream.next();
                    final StringBuilder variableBuilder = new StringBuilder();
                    while (!stream.isEnd()) {
                        final char c1 = stream.getChar();
                        if (c1 == '}') {
                            stream.next();
                            if (stream.getChar() == '}') {
                                return new Parameter(variableBuilder.toString(), Type.VARIABLE);
                            }
                        }
                        stream.next();
                        variableBuilder.append(c1);
                    }

                    throw new IllegalStateException("cannot variable parameter");
                }
            }
            builder.append(c);
            stream.next();
        }

        return new Parameter(builder.toString(), Type.CONST);
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