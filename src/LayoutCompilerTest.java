import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;


import static org.junit.Assert.*;

public class LayoutCompilerTest {
    @Test
    public void execute() {
        assertThat(new LayoutCompiler(), IsInstanceOf.instanceOf(LayoutCompiler.class));
    }
}