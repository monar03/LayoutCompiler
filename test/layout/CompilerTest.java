package layout;

import layout.render.compiler.BlockTag;
import layout.render.compiler.Render;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertThat;

public class CompilerTest {

    @Test
    public void compiler() {
        final Compiler compiler = new Compiler(".test{ padding : 1px; \n margin:2px; }");
        final Map<String, Class> classMap = new HashMap<>();
        classMap.put("test", TestTag.class);
        final Render render = compiler.compile("<test><test class=\"test\"></test></test>", classMap);

        assertThat(render, IsInstanceOf.instanceOf(BlockTag.class));
        assertThat(render.getRenders().get(0), IsInstanceOf.instanceOf(TestTag.class));
        assertThat(render.getRenders().get(0).getRenders().get(0), IsInstanceOf.instanceOf(TestTag.class));

    }

    public static
    class TestTag extends BlockTag {
        @Override
        public void render(Render render) {

        }
    }
}

