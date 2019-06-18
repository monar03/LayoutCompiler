package jp.aquagear.layout.compiler.render.lexer;

import jp.aquagear.layout.compiler.render.lexer.result.StringResult;
import jp.aquagear.layout.compiler.render.lexer.result.TagEndResult;
import jp.aquagear.layout.compiler.render.lexer.result.TagStartResult;
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
    public void analysis_文字列検出_空白を無視() {
        final Lexer lexer = new Lexer("te st");
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
    public void analysis_属性情報付きタグスタートのパース() {
        final Lexer lexer = new Lexer("<test a=param>");
        assertThat(lexer.analysis().get(0), IsInstanceOf.instanceOf(TagStartResult.class));
        assertThat(((TagStartResult) lexer.analysis().get(0)).getName(), Is.is("test"));
        assertThat(((TagStartResult) lexer.analysis().get(0)).getParam().get("a"), Is.is("param"));
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

    /**
     * @XXX TagEndResultを作るかどうかあとで考える
     */
    @Test
    public void analysis_タグスタートエンドのパース2() {
        final Lexer lexer = new Lexer("<test />");
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