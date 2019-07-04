package jp.aquagear.layout.compiler.design.lexer;

import jp.aquagear.layout.compiler.render.lexer.StringStream;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final StringStream stringStream;
    private List<Result> results = new ArrayList<>();

    public Lexer(String s) {
        this.stringStream = new StringStream(s.trim());
    }

    public List<Result> analysis() {
        while (!stringStream.isEnd()) {
            switch (stringStream.getChar()) {
                case ' ':
                case '}': {
                    stringStream.next();
                    break;
                }
                case '.': {
                    stringStream.next();
                    classAnalysis();
                    break;
                }
                default:
                    stringStream.next();
            }
        }
        return new ArrayList<>(results);
    }

    private void classAnalysis() {
        final StringBuilder stringBuilder = new StringBuilder();
        while (!stringStream.isEnd()) {
            final char c = stringStream.getChar();
            switch (c) {
                case '{':
                    stringStream.next();
                    results.add(new ClassResult(stringBuilder.toString()));
                    analysisDesignParams();
                    return;
                case '}':
                case '.': {
                    throw new IllegalStateException("cannot parse");
                }
                case '\n':
                case ' ': {
                    stringStream.next();
                    break;
                }
                default:
                    stringBuilder.append(c);
                    stringStream.next();
                    break;
            }
        }
    }

    private void analysisDesignParams() {
        results.add(new DesignStartResult());
        while (!stringStream.isEnd()) {
            final char c = stringStream.getChar();
            switch (c) {
                case '}': {
                    stringStream.next();
                    results.add(new DesignEndResult());
                    return;
                }
                case '\n':
                case ' ': {
                    stringStream.next();
                    break;
                }
                case '{': {
                    throw new IllegalStateException("cannot parse. design param");
                }
                default: {
                    analysisParam();
                }
            }
        }
    }

    private void analysisParam() {
        final StringBuilder key = new StringBuilder();
        KEY:
        while (!stringStream.isEnd()) {
            final char c = stringStream.getChar();
            switch (c) {
                case ':': {
                    stringStream.next();
                    break KEY;
                }
                case '\n':
                case ';': {
                    throw new IllegalStateException("cannot input value");
                }
                case ' ': {
                    stringStream.next();
                    break;
                }
                default: {
                    key.append(c);
                    stringStream.next();
                }
            }
        }

        final StringBuilder value = new StringBuilder();
        VALUE:
        while (!stringStream.isEnd()) {
            final char c = stringStream.getChar();
            if (value.length() <= 0 && c == '"') {
                stringStream.next();
                value.append(parseString());
            } else {
                switch (c) {
                    case ':':
                    case '\n': {
                        throw new IllegalStateException("cannot input space");
                    }
                    case ' ': {
                        stringStream.next();
                        break;
                    }
                    case ';': {
                        stringStream.next();
                        break VALUE;
                    }
                    default: {
                        value.append(c);
                        stringStream.next();
                        break;
                    }
                }
            }
        }

        results.add(new DesignParamResult(key.toString(), value.toString()));
    }

    private String parseString() {
        final StringBuilder str = new StringBuilder();
        while (!stringStream.isEnd()) {
            final char c = stringStream.getChar();
            if (c == '"') {
                stringStream.next();
                return str.toString();
            } else if (c==';') {
                return str.toString();
            } else {
                stringStream.next();
                str.append(c);
            }
        }

        return str.toString();
    }
}
