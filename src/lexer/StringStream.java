package lexer;

class StringStream {
    private int position = 0;
    private String str;

    StringStream(String s) {
        str = s;
    }

    char getChar() {
        return str.charAt(position);
    }

    void next() {
        position++;
    }

    boolean isEnd() {
        return str.length() <= position;
    }
}
