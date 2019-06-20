package jp.aquagear.layout.compiler;

import jp.aquagear.layout.compiler.render.compiler.BlockRender;
import jp.aquagear.layout.compiler.render.compiler.Render;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertThat;

public class CompilerTest {

    @Test
    public void compiler() {
        final Compiler compiler = new Compiler(".test{ padding : 1px; \n margin:2px; }");
        final Map<String, Class> classMap = new HashMap<>();
        classMap.put("test", TestRender.class);
        final List<Render> render = compiler.compile("<test><test class=\"test\"></test></test>", classMap);

        assertThat(render.get(0), IsInstanceOf.instanceOf(TestRender.class));
        assertThat(render.get(0).getRenders().get(0), IsInstanceOf.instanceOf(TestRender.class));

    }

    public static class TestRender extends BlockRender {
        @Override
        public Object render() {
            return null;
        }
    }
}

