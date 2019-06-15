package layout.design;

import layout.lexer.StringStream;

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
                case ' ': {
                    break;
                }
                case '.': {
                    stringStream.next();
                    classAnalysis();
                    break;
                }
                case '}': {
                    stringStream.next();
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
                    analysisDesignParam();
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

    private void analysisDesignParam() {
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
            }
        }
    }
}
