package jp.aquabox.layout.compiler.render;

import jp.aquabox.layout.compiler.render.compiler.BlockRender;
import jp.aquabox.layout.compiler.render.compiler.Render;
import jp.aquabox.layout.compiler.render.compiler.StringRender;
import jp.aquabox.layout.compiler.render.lexer.result.Type;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertThat;

public class CompilerTest {
    private RenderCreator renderCreator = new RenderCreator() {
        @Override
        public Render create(String name) {
            return new TestRender();
        }
    };

    @Test
    public void execute() {
        assertThat(new Compiler(), IsInstanceOf.instanceOf(Compiler.class));
    }

    @Test
    public void compile_sample_test_tag_string() {
        final Compiler compiler = new Compiler();
        compiler.setCreator(renderCreator);
        final List<Render> renders = compiler.compile("<test>aaaa</test>");

        assertThat(renders.get(0), IsInstanceOf.instanceOf(TestRender.class));
        assertThat(renders.get(0).getRenders().get(0), IsInstanceOf.instanceOf(StringRender.class));
    }

    @Test
    public void compile_sample_test_tag() {
        final Compiler compiler = new Compiler();
        compiler.setCreator(renderCreator);
        final List<Render> renders = compiler.compile("<test></test>");

        assertThat(renders.get(0), IsInstanceOf.instanceOf(TestRender.class));
    }

    @Test
    public void compile_sample_test_tag2() {
        final Compiler compiler = new Compiler();
        compiler.setCreator(renderCreator);
        final List<Render> renders = compiler.compile("<test><test></test></test>");


        final TestRender testTag = (TestRender) renders.get(0);
        assertThat(testTag, IsInstanceOf.instanceOf(TestRender.class));
        assertThat(testTag.getRenders().get(0), IsInstanceOf.instanceOf(TestRender.class));
    }

    @Test
    public void compile_sample_test_tag3() {
        final Compiler compiler = new Compiler();
        compiler.setCreator(renderCreator);
        final List<Render> renders = compiler.compile("<test><test></test><test></test></test>");

        final TestRender testTag = (TestRender) renders.get(0);
        assertThat(testTag, IsInstanceOf.instanceOf(TestRender.class));
        assertThat(testTag.getRenders().get(0), IsInstanceOf.instanceOf(TestRender.class));
        assertThat(testTag.getRenders().get(1), IsInstanceOf.instanceOf(TestRender.class));
    }

    @Test
    public void compile_sample_test_tag4() {
        final Compiler compiler = new Compiler();
        compiler.setCreator(renderCreator);
        final List<Render> renders = compiler.compile("<test><test><test></test></test><test></test></test>");

        final TestRender testTag = (TestRender) renders.get(0);
        assertThat(testTag, IsInstanceOf.instanceOf(TestRender.class));
        assertThat(testTag.getRenders().get(0), IsInstanceOf.instanceOf(TestRender.class));
        assertThat(testTag.getRenders().get(0).getRenders().get(0), IsInstanceOf.instanceOf(TestRender.class));
        assertThat(testTag.getRenders().get(1), IsInstanceOf.instanceOf(TestRender.class));
    }

    @Test
    public void compile_sample_test_tag5() {
        final Compiler compiler = new Compiler();
        compiler.setCreator(renderCreator);
        final List<Render> renders = compiler.compile("<test for=\"{{test}}\"><test><test></test></test><test></test></test>");

        final TestRender testTag = (TestRender) renders.get(0);
        assertThat(testTag, IsInstanceOf.instanceOf(TestRender.class));
        assertThat(testTag.getRenders().get(0), IsInstanceOf.instanceOf(TestRender.class));
        assertThat(testTag.getMode(), Is.is(BlockRender.Mode.TEMPLATE));
        assertThat(testTag.getParams().get("for").value, Is.is("test"));
        assertThat(testTag.getParams().get("for").type, Is.is(Type.VARIABLE));
        assertThat(testTag.getRenders().get(0).getRenders().get(0), IsInstanceOf.instanceOf(TestRender.class));
        assertThat(((BlockRender) testTag.getRenders().get(0).getRenders().get(0)).getMode(), Is.is(BlockRender.Mode.NORMAL));
        assertThat(testTag.getRenders().get(1), IsInstanceOf.instanceOf(TestRender.class));
    }

    @Test
    public void compile_sample_test_design_class1() {
        final Map<String, Map<String, String>> map = new HashMap<>();
        final Map<String, String> cls = new HashMap<>();
        cls.put("padding", "1px");
        map.put("test", cls);

        final Compiler compiler = new Compiler(map);
        compiler.setCreator(renderCreator);
        final List<Render> renders = compiler.compile("<test><test class=\"test\"></test></test>");

        final TestRender testTag = (TestRender) renders.get(0);
        assertThat(testTag, IsInstanceOf.instanceOf(TestRender.class));

        final TestRender testTag1 = (TestRender) testTag.getRenders().get(0);
        assertThat(testTag1, IsInstanceOf.instanceOf(TestRender.class));
        assertThat(testTag1.getStyles().get("padding"), Is.is("1px"));
    }
}

class TestRender extends BlockRender {
    public Object render() {
        return null;
    }
}