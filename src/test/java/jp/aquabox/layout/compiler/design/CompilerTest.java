package jp.aquabox.layout.compiler.design;

import org.hamcrest.core.Is;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class CompilerTest {

    @Test
    public void compile() {
        final Compiler compiler = new Compiler();
        assertThat(compiler.compile("").size(), Is.is(0));
    }

    @Test
    public void compile_クラスの定義1() {
        final Compiler compiler = new Compiler();
        assertThat(compiler.compile(".small-p{}").containsKey("small-p"), Is.is(true));
    }

    @Test
    public void compile_クラスの定義_Param1() {
        final Compiler compiler = new Compiler();
        assertThat(compiler.compile(".small-p{ padding : 1px; }").containsKey("small-p"), Is.is(true));
        assertThat(compiler.compile(".small-p{ padding : 1px; }").get("small-p").get("padding"), Is.is("1px"));
    }

    @Test
    public void compile_クラスの定義_Param2() {
        final Compiler compiler = new Compiler();
        assertThat(compiler.compile(".small-p{ padding : 1px; \n margin:2px; }").containsKey("small-p"), Is.is(true));
        assertThat(compiler.compile(".small-p{ padding : 1px; \n margin:2px; }").get("small-p").get("padding"), Is.is("1px"));
        assertThat(compiler.compile(".small-p{ padding : 1px; \n margin:2px; }").get("small-p").get("margin"), Is.is("2px"));
    }
}