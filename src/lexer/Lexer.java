package lexer;

import lexer.result.Result;
import lexer.result.StringResult;
import lexer.result.TagEndResult;
import lexer.result.TagStartResult;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final StringStream stringStream;
    private final List<Result> results = new ArrayList<>();

    public Lexer(String s) {
        stringStream = new StringStream(s);
    }

    List<Result> analysis() {
        while (!stringStream.isEnd()) {
            final char c = stringStream.getChar();
            switch (c) {
                case ' ': {
                    stringStream.next();
                    break;
                }
                case '<': {
                    stringStream.next();
                    tag();
                    break;
                }
                default: {
                    string();
                    break;
                }
            }
        }
        return new ArrayList<>(results);
    }

    private void string() {
        final StringBuilder stringBuilder = new StringBuilder();
        while (!stringStream.isEnd()) {
            final char c = stringStream.getChar();
            switch (c) {
                case ' ':
                    stringStream.next();
                    break;
                case '<':
                    results.add(new StringResult(stringBuilder.toString()));
                    return;
                default:
                    stringStream.next();
                    stringBuilder.append(c);
                    break;
            }
        }

        results.add(new StringResult(stringBuilder.toString()));
    }

    private void tag() {
        final char c = stringStream.getChar();
        if (c == '/') {
            stringStream.next();
            tagEnd();
        } else {
            tagStart();
        }
    }

    private void tagEnd() {
        final StringBuilder stringBuilder = new StringBuilder();
        while (!stringStream.isEnd()) {
            final char tc = stringStream.getChar();
            switch (tc) {
                case '>': {
                    stringStream.next();
                    results.add(new TagEndResult(stringBuilder.toString()));
                    return;
                }
                default: {
                    stringStream.next();
                    stringBuilder.append(tc);
                }
            }
        }
    }

    private void tagStart() {
        final StringBuilder stringBuilder = new StringBuilder();
        while (!stringStream.isEnd()) {
            final char tc = stringStream.getChar();
            switch (tc) {
                case '/': {
                    stringStream.next();
                    if (stringStream.getChar() == '>') {
                        stringStream.next();
                        final TagStartResult tagStartResult = new TagStartResult(stringBuilder.toString());
                        results.add(tagStartResult);
                        results.add(new TagEndResult(tagStartResult.getName()));
                        return;
                    } else {
                        stringBuilder.append(tc);
                    }
                }
                case '>': {
                    stringStream.next();
                    results.add(new TagStartResult(stringBuilder.toString()));
                    return;
                }
                default: {
                    stringStream.next();
                    stringBuilder.append(tc);
                }
            }
        }
    }
}
