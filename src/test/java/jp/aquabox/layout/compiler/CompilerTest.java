package jp.aquabox.layout.compiler;

import jp.aquabox.layout.compiler.render.RenderCreator;
import jp.aquabox.layout.compiler.render.compiler.BlockRender;
import jp.aquabox.layout.compiler.render.compiler.Render;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertThat;

public class CompilerTest {
    private RenderCreator renderCreator = new RenderCreator() {
        @Override
        public Render create(String name) {
            return new TestRender();
        }
    };

    @Test
    public void compiler() {
        final Compiler compiler = new Compiler(renderCreator);
        final List<Render> render = compiler.compile(
                "<test><test class=\"test\"></test></test>",
                ".test{ padding : 1px; \n margin:2px; }");

        assertThat(render.get(0), IsInstanceOf.instanceOf(TestRender.class));
        assertThat(render.get(0).getRenders().get(0), IsInstanceOf.instanceOf(TestRender.class));

    }

    @Test
    public void compiler1() {
        final Compiler compiler = new Compiler(renderCreator);
        final List<Render> render = compiler.compile(
                "<test><test class=\"test\"></test></test>",
                ".test { padding:1dp;}\n .box { orientation : horizon;}");

        assertThat(render.get(0), IsInstanceOf.instanceOf(TestRender.class));
        assertThat(render.get(0).getRenders().get(0), IsInstanceOf.instanceOf(TestRender.class));

    }

    public static class TestRender extends BlockRender {
        public Object render() {
            return null;
        }
    }
}

