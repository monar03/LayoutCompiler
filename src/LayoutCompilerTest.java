import compiler.BlockTag;
import compiler.Executer;
import compiler.StringExecuter;
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
        final Executer executer = layoutCompiler.compile("<test>aaaa</test>");

        assertThat(executer, IsInstanceOf.instanceOf(BlockTag.class));
        assertThat(((BlockTag) executer).getExecuters().get(0), IsInstanceOf.instanceOf(TestTag.class));
        assertThat(((BlockTag) ((BlockTag) executer).getExecuters().get(0)).getExecuters().get(0), IsInstanceOf.instanceOf(StringExecuter.class));
    }

    @Test
    public void compile_sample_test_tag() {
        final LayoutCompiler layoutCompiler = new LayoutCompiler();
        layoutCompiler.addTag("test", TestTag.class);
        final Executer executer = layoutCompiler.compile("<test></test>");

        assertThat(executer, IsInstanceOf.instanceOf(BlockTag.class));
        assertThat(((BlockTag) executer).getExecuters().get(0), IsInstanceOf.instanceOf(TestTag.class));
    }

    @Test
    public void compile_sample_test_tag2() {
        final LayoutCompiler layoutCompiler = new LayoutCompiler();
        layoutCompiler.addTag("test", TestTag.class);
        final Executer executer = layoutCompiler.compile("<test><test></test></test>");

        assertThat(executer, IsInstanceOf.instanceOf(BlockTag.class));

        final TestTag testTag = (TestTag) ((BlockTag) executer).getExecuters().get(0);
        assertThat(testTag, IsInstanceOf.instanceOf(TestTag.class));
        assertThat(testTag.getExecuters().get(0), IsInstanceOf.instanceOf(TestTag.class));
    }

    @Test
    public void compile_sample_test_tag3() {
        final LayoutCompiler layoutCompiler = new LayoutCompiler();
        layoutCompiler.addTag("test", TestTag.class);
        final Executer executer = layoutCompiler.compile("<test><test></test><test></test></test>");

        final TestTag testTag = (TestTag) ((BlockTag) executer).getExecuters().get(0);
        assertThat(testTag, IsInstanceOf.instanceOf(TestTag.class));
        assertThat(testTag.getExecuters().get(0), IsInstanceOf.instanceOf(TestTag.class));
        assertThat(testTag.getExecuters().get(1), IsInstanceOf.instanceOf(TestTag.class));
    }

    @Test
    public void compile_sample_test_tag4() {
        final LayoutCompiler layoutCompiler = new LayoutCompiler();
        layoutCompiler.addTag("test", TestTag.class);
        final Executer executer = layoutCompiler.compile("<test><test><test></test></test><test></test></test>");

        final TestTag testTag = (TestTag) ((BlockTag) executer).getExecuters().get(0);
        assertThat(testTag, IsInstanceOf.instanceOf(TestTag.class));
        assertThat(testTag.getExecuters().get(0), IsInstanceOf.instanceOf(TestTag.class));
        assertThat(((BlockTag) testTag.getExecuters().get(0)).getExecuters().get(0), IsInstanceOf.instanceOf(TestTag.class));
        assertThat(testTag.getExecuters().get(1), IsInstanceOf.instanceOf(TestTag.class));
    }
}

class TestTag extends BlockTag {
    @Override
    public void execute() {

    }
}