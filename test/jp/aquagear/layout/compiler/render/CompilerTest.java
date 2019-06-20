package jp.aquagear.layout.compiler.render;

import jp.aquagear.layout.compiler.render.compiler.BlockTag;
import jp.aquagear.layout.compiler.render.compiler.Render;
import jp.aquagear.layout.compiler.render.compiler.StringRender;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertThat;

public class CompilerTest {
    @Test
    public void execute() {
        assertThat(new Compiler(), IsInstanceOf.instanceOf(Compiler.class));
    }

    @Test
    public void compile_sample_test_tag_string() {
        final Compiler compiler = new Compiler();
        compiler.addTag("test", TestTag.class);
        final Render render = compiler.compile("<test>aaaa</test>");

        assertThat(render, IsInstanceOf.instanceOf(BlockTag.class));
        assertThat(((BlockTag) render).getRenders().get(0), IsInstanceOf.instanceOf(TestTag.class));
        assertThat(((BlockTag) ((BlockTag) render).getRenders().get(0)).getRenders().get(0), IsInstanceOf.instanceOf(StringRender.class));
    }

    @Test
    public void compile_sample_test_tag() {
        final Compiler compiler = new Compiler();
        compiler.addTag("test", TestTag.class);
        final Render render = compiler.compile("<test></test>");

        assertThat(render, IsInstanceOf.instanceOf(BlockTag.class));
        assertThat(((BlockTag) render).getRenders().get(0), IsInstanceOf.instanceOf(TestTag.class));
    }

    @Test
    public void compile_sample_test_tag2() {
        final Compiler compiler = new Compiler();
        compiler.addTag("test", TestTag.class);
        final Render render = compiler.compile("<test><test></test></test>");

        assertThat(render, IsInstanceOf.instanceOf(BlockTag.class));

        final TestTag testTag = (TestTag) ((BlockTag) render).getRenders().get(0);
        assertThat(testTag, IsInstanceOf.instanceOf(TestTag.class));
        assertThat(testTag.getRenders().get(0), IsInstanceOf.instanceOf(TestTag.class));
    }

    @Test
    public void compile_sample_test_tag3() {
        final Compiler compiler = new Compiler();
        compiler.addTag("test", TestTag.class);
        final Render executer = compiler.compile("<test><test></test><test></test></test>");

        final TestTag testTag = (TestTag) ((BlockTag) executer).getRenders().get(0);
        assertThat(testTag, IsInstanceOf.instanceOf(TestTag.class));
        assertThat(testTag.getRenders().get(0), IsInstanceOf.instanceOf(TestTag.class));
        assertThat(testTag.getRenders().get(1), IsInstanceOf.instanceOf(TestTag.class));
    }

    @Test
    public void compile_sample_test_tag4() {
        final Compiler compiler = new Compiler();
        compiler.addTag("test", TestTag.class);
        final Render executer = compiler.compile("<test><test><test></test></test><test></test></test>");

        final TestTag testTag = (TestTag) ((BlockTag) executer).getRenders().get(0);
        assertThat(testTag, IsInstanceOf.instanceOf(TestTag.class));
        assertThat(testTag.getRenders().get(0), IsInstanceOf.instanceOf(TestTag.class));
        assertThat(((BlockTag) testTag.getRenders().get(0)).getRenders().get(0), IsInstanceOf.instanceOf(TestTag.class));
        assertThat(testTag.getRenders().get(1), IsInstanceOf.instanceOf(TestTag.class));
    }

    @Test
    public void compile_sample_test_design_class1() {
        final Map<String, Map<String, String>> map = new HashMap<>();
        final Map<String, String> cls = new HashMap<>();
        cls.put("padding", "1px");
        map.put("test", cls);

        final Compiler compiler = new Compiler(map);
        compiler.addTag("test", TestTag.class);
        final Render render = compiler.compile("<test><test class=\"test\"></test></test>");

        assertThat(render, IsInstanceOf.instanceOf(BlockTag.class));

        final TestTag testTag = (TestTag) ((BlockTag) render).getRenders().get(0);
        assertThat(testTag, IsInstanceOf.instanceOf(TestTag.class));

        final TestTag testTag1 = (TestTag) testTag.getRenders().get(0);
        assertThat(testTag1, IsInstanceOf.instanceOf(TestTag.class));
        assertThat(testTag1.getParams().get("padding"), Is.is("1px"));

    }
}

class TestTag extends BlockTag {
    @Override
    public Object render() {
        return null;
    }
}