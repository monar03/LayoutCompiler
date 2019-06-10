import compiler.BlockTag;
import compiler.Executer;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertThat;

public class LayoutCompilerTest {
    @Test
    public void execute() {
        assertThat(new LayoutCompiler(), IsInstanceOf.instanceOf(LayoutCompiler.class));
    }

    @Test
    public void compile_sample_test_tag() {
        final LayoutCompiler layoutCompiler = new LayoutCompiler();
        layoutCompiler.addTag("test", TestTag.class);
        final Executer executer = layoutCompiler.compile("<test></test>");

        assertThat(executer, IsInstanceOf.instanceOf(TestTag.class));
    }

    @Test
    public void compile_sample_test_tag2() {
        final LayoutCompiler layoutCompiler = new LayoutCompiler();
        layoutCompiler.addTag("test", TestTag.class);
        final Executer executer = layoutCompiler.compile("<test><test></test></test>");

        assertThat(executer, IsInstanceOf.instanceOf(TestTag.class));
        assertThat(((TestTag) executer).getExecuters().get(0), IsInstanceOf.instanceOf(TestTag.class));
    }

}

class TestTag extends BlockTag {
    public List<Executer> getExecuters() {
        return executers;
    }

    @Override
    public void execute() {

    }
}