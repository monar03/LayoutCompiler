package layout.render;

import layout.render.compiler.BlockTag;
import layout.render.compiler.Render;
import layout.render.compiler.StringRender;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;

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
}

class TestTag extends BlockTag {
    @Override
    public void render(Render render) {

    }
}