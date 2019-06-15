package layout.lexer;

public class StringStream {
    private int position = 0;
    private String str;

    public StringStream(String s) {
        str = s;
    }

    public char getChar() {
        return str.charAt(position);
    }

    public void next() {
        position++;
    }

    public boolean isEnd() {
        return str.length() <= position;
    }
}
