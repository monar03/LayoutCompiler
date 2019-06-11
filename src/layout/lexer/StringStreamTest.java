package layout.lexer;

import org.hamcrest.core.Is;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class StringStreamTest {

    @Test
    public void getChar() {
        final StringStream stringStream = new StringStream("aaa");
        assertThat(stringStream.getChar(), Is.is('a'));
    }

    @Test
    public void next() {
        final StringStream stringStream = new StringStream("abc");
        stringStream.next();
        assertThat(stringStream.getChar(), Is.is('b'));
    }

    @Test
    public void isEnd_終端じゃないとき() {
        final StringStream stringStream = new StringStream("a");
        assertThat(stringStream.isEnd(), Is.is(false));
    }

    @Test
    public void isEnd_終端の時() {
        final StringStream stringStream = new StringStream("a");
        stringStream.getChar();
        stringStream.next();
        assertThat(stringStream.isEnd(), Is.is(true));
    }
}