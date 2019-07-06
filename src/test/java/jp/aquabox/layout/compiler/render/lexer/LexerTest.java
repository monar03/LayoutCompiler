package jp.aquabox.layout.compiler.render.lexer;

import jp.aquabox.layout.compiler.render.lexer.result.StringResult;
import jp.aquabox.layout.compiler.render.lexer.result.TagEndResult;
import jp.aquabox.layout.compiler.render.lexer.result.TagStartResult;
import jp.aquabox.layout.compiler.render.lexer.result.Type;
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
        assertThat(((StringResult) lexer.analysis().get(0)).getText().value, Is.is("test"));
    }

    @Test
    public void analysis_文字列検出_空白を無視() {
        final Lexer lexer = new Lexer("te st");
        assertThat(lexer.analysis().get(0), IsInstanceOf.instanceOf(StringResult.class));
        assertThat(((StringResult) lexer.analysis().get(0)).getText().value, Is.is("test"));
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
        assertThat(((TagStartResult) lexer.analysis().get(0)).getParam().get("a").value, Is.is("param"));
        assertThat(((TagStartResult) lexer.analysis().get(0)).getParam().get("a").type, Is.is(jp.aquabox.layout.compiler.render.lexer.result.Type.CONST));
    }

    @Test
    public void analysis_属性情報付きタグスタートのパース_url() {
        final Lexer lexer = new Lexer("<test src=\"https://avatars1.githubusercontent.com/u/2632317?s=460&v=4\" />");
        assertThat(lexer.analysis().get(0), IsInstanceOf.instanceOf(TagStartResult.class));
        assertThat(((TagStartResult) lexer.analysis().get(0)).getName(), Is.is("test"));
        assertThat(((TagStartResult) lexer.analysis().get(0)).getParam().get("src").type, Is.is(jp.aquabox.layout.compiler.render.lexer.result.Type.CONST));
        assertThat(((TagStartResult) lexer.analysis().get(0)).getParam().get("src").value, Is.is("https://avatars1.githubusercontent.com/u/2632317?s=460&v=4"));
    }

    @Test
    public void analysis_変数属性情報付きタグスタートのパース() {
        final Lexer lexer = new Lexer("<test a={{param}}>");
        assertThat(lexer.analysis().get(0), IsInstanceOf.instanceOf(TagStartResult.class));
        assertThat(((TagStartResult) lexer.analysis().get(0)).getName(), Is.is("test"));
        assertThat(((TagStartResult) lexer.analysis().get(0)).getParam().get("a").value, Is.is("param"));
        assertThat(((TagStartResult) lexer.analysis().get(0)).getParam().get("a").type, Is.is(Type.VARIABLE));
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
        assertThat(((StringResult) lexer.analysis().get(1)).getText().value, Is.is("test"));
        assertThat(lexer.analysis().get(2), IsInstanceOf.instanceOf(TagEndResult.class));
        assertThat(((TagEndResult) lexer.analysis().get(2)).getName(), Is.is("test"));
    }
}