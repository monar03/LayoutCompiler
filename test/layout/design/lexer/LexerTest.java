package layout.design.lexer;

import layout.design.lexer.*;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertThat;

public class LexerTest {
    @Test
    public void analysis_空文字のテスト() {
        assertThat(new Lexer("").analysis(), IsInstanceOf.instanceOf(List.class));
    }

    @Test
    public void analysis_クラスの定義1() {
        final List<Result> results = new Lexer(".small-p{}").analysis();
        assertThat(results.get(0), IsInstanceOf.instanceOf(ClassResult.class));
        assertThat(results.get(1), IsInstanceOf.instanceOf(DesignStartResult.class));
        assertThat(results.get(2), IsInstanceOf.instanceOf(DesignEndResult.class));
    }

    @Test
    public void analysis_クラスの定義2() {
        final List<Result> results = new Lexer(".small-p{ }").analysis();
        assertThat(results.get(0), IsInstanceOf.instanceOf(ClassResult.class));
        assertThat(results.get(1), IsInstanceOf.instanceOf(DesignStartResult.class));
        assertThat(results.get(2), IsInstanceOf.instanceOf(DesignEndResult.class));
    }

    @Test
    public void analysis_クラスの定義3() {
        final List<Result> results = new Lexer(".small-p{\n}").analysis();
        assertThat(results.get(0), IsInstanceOf.instanceOf(ClassResult.class));
        assertThat(((ClassResult) results.get(0)).getName(), Is.is("small-p"));
        assertThat(results.get(1), IsInstanceOf.instanceOf(DesignStartResult.class));
        assertThat(results.get(2), IsInstanceOf.instanceOf(DesignEndResult.class));
    }

    @Test
    public void analysis_クラスの定義_パラメータ1() {
        final List<Result> results = new Lexer(".small-p{\n padding:1px; \n}").analysis();
        assertThat(results.get(0), IsInstanceOf.instanceOf(ClassResult.class));
        assertThat(results.get(1), IsInstanceOf.instanceOf(DesignStartResult.class));
        assertThat(results.get(2), IsInstanceOf.instanceOf(DesignParamResult.class));
        assertThat(((DesignParamResult) results.get(2)).getKey(), Is.is("padding"));
        assertThat(((DesignParamResult) results.get(2)).getValue(), Is.is("1px"));
        assertThat(results.get(3), IsInstanceOf.instanceOf(DesignEndResult.class));
    }
}