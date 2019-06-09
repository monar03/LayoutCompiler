package lexer;

import lexer.result.StringResult;
import lexer.result.TagEndResult;
import lexer.result.TagStartResult;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertThat;

public class LexerTest {

    @Test
    public void analysis_文字列検出() {
        final Lexer lexer = new Lexer("test");
        assertThat(lexer.analysis().get(0), IsInstanceOf.instanceOf(StringResult.class));
        assertThat(((StringResult) lexer.analysis().get(0)).getText(), Is.is("test"));
    }

    @Test
    public void analysis() {
        final Lexer lexer = new Lexer("");
        assertThat(lexer.analysis(), IsInstanceOf.instanceOf(List.class));
    }

    @Test
    public void analysis_タグスタートのパース() {
        final Lexer lexer = new Lexer("<test>");
        assertThat(lexer.analysis().get(0), IsInstanceOf.instanceOf(TagStartResult.class));
        assertThat(((TagStartResult) lexer.analysis().get(0)).getName(), Is.is("test"));
    }

    @Test
    public void analysis_タグエンドのパース() {
        final Lexer lexer = new Lexer("</test>");
        assertThat(lexer.analysis().get(0), IsInstanceOf.instanceOf(TagEndResult.class));
        assertThat(((TagEndResult) lexer.analysis().get(0)).getName(), Is.is("test"));
    }

    @Test
    public void analysis_タグスタートエンドのパース() {
        final Lexer lexer = new Lexer("<test></test>");
        assertThat(lexer.analysis().get(0), IsInstanceOf.instanceOf(TagStartResult.class));
        assertThat(((TagStartResult) lexer.analysis().get(0)).getName(), Is.is("test"));
        assertThat(lexer.analysis().get(1), IsInstanceOf.instanceOf(TagEndResult.class));
        assertThat(((TagEndResult) lexer.analysis().get(1)).getName(), Is.is("test"));
    }

    @Test
    public void analysis_タグスタート文字列エンドのパース() {
        final Lexer lexer = new Lexer("<test>test</test>");
        assertThat(lexer.analysis().get(0), IsInstanceOf.instanceOf(TagStartResult.class));
        assertThat(((TagStartResult) lexer.analysis().get(0)).getName(), Is.is("test"));
        assertThat(lexer.analysis().get(1), IsInstanceOf.instanceOf(StringResult.class));
        assertThat(((StringResult) lexer.analysis().get(1)).getText(), Is.is("test"));
        assertThat(lexer.analysis().get(2), IsInstanceOf.instanceOf(TagEndResult.class));
        assertThat(((TagEndResult) lexer.analysis().get(2)).getName(), Is.is("test"));
    }
}