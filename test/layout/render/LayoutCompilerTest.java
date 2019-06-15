package layout.render;

import layout.render.compiler.BlockTag;
import layout.render.compiler.Render;
import layout.render.compiler.StringRender;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class LayoutCompilerTest {
    @Test
    public void execute() {
        assertThat(new LayoutCompiler(), IsInstanceOf.instanceOf(LayoutCompiler.class));
    }

    @Test
    public void compile_sample_test_tag_string() {
        final LayoutCompiler layoutCompiler = new LayoutCompiler();
        layoutCompiler.addTag("test", TestTag.class);
        final Render render = layoutCompiler.compile("<test>aaaa</test>");

        assertThat(render, IsInstanceOf.instanceOf(BlockTag.class));
        assertThat(((BlockTag) render).getRenders().get(0), IsInstanceOf.instanceOf(TestTag.class));
        assertThat(((BlockTag) ((BlockTag) render).getRenders().get(0)).getRenders().get(0), IsInstanceOf.instanceOf(StringRender.class));
    }

    @Test
    public void compile_sample_test_tag() {
        final LayoutCompiler layoutCompiler = new LayoutCompiler();
        layoutCompiler.addTag("test", TestTag.class);
        final Render render = layoutCompiler.compile("<test></test>");

        assertThat(render, IsInstanceOf.instanceOf(BlockTag.class));
        assertThat(((BlockTag) render).getRenders().get(0), IsInstanceOf.instanceOf(TestTag.class));
    }

    @Test
    public void compile_sample_test_tag2() {
        final LayoutCompiler layoutCompiler = new LayoutCompiler();
        layoutCompiler.addTag("test", TestTag.class);
        final Render render = layoutCompiler.compile("<test><test></test></test>");

        assertThat(render, IsInstanceOf.instanceOf(BlockTag.class));

        final TestTag testTag = (TestTag) ((BlockTag) render).getRenders().get(0);
        assertThat(testTag, IsInstanceOf.instanceOf(TestTag.class));
        assertThat(testTag.getRenders().get(0), IsInstanceOf.instanceOf(TestTag.class));
    }

    @Test
    public void compile_sample_test_tag3() {
        final LayoutCompiler layoutCompiler = new LayoutCompiler();
        layoutCompiler.addTag("test", TestTag.class);
        final Render executer = layoutCompiler.compile("<test><test></test><test></test></test>");

        final TestTag testTag = (TestTag) ((BlockTag) executer).getRenders().get(0);
        assertThat(testTag, IsInstanceOf.instanceOf(TestTag.class));
        assertThat(testTag.getRenders().get(0), IsInstanceOf.instanceOf(TestTag.class));
        assertThat(testTag.getRenders().get(1), IsInstanceOf.instanceOf(TestTag.class));
    }

    @Test
    public void compile_sample_test_tag4() {
        final LayoutCompiler layoutCompiler = new LayoutCompiler();
        layoutCompiler.addTag("test", TestTag.class);
        final Render executer = layoutCompiler.compile("<test><test><test></test></test><test></test></test>");

        final TestTag testTag = (TestTag) ((BlockTag) executer).getRenders().get(0);
        assertThat(testTag, IsInstanceOf.instanceOf(TestTag.class));
        assertThat(testTag.getRenders().get(0), IsInstanceOf.instanceOf(TestTag.class));
        assertThat(((BlockTag) testTag.getRenders().get(0)).getRenders().get(0), IsInstanceOf.instanceOf(TestTag.class));
        assertThat(testTag.getRenders().get(1), IsInstanceOf.instanceOf(TestTag.class));
    }
}

class TestTag extends BlockTag {
    @Override
    public void render(Render render) {

    }
}