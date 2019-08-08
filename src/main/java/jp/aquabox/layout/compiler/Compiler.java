package jp.aquabox.layout.compiler;

import jp.aquabox.layout.compiler.render.RenderCreator;
import jp.aquabox.layout.compiler.render.compiler.Render;

import java.util.List;
import java.util.Map;

public class Compiler {
    private final RenderCreator renderCreator;

    public Compiler(RenderCreator renderCreator) {
        this.renderCreator = renderCreator;
    }

    public List<Render> compile(String layoutStr, String design) {
        Map<String, Map<String, String>> designMap = new jp.aquabox.layout.compiler.design.Compiler().compile(design);
        final jp.aquabox.layout.compiler.render.Compiler compiler = new jp.aquabox.layout.compiler.render.Compiler(designMap);
        compiler.setCreator(renderCreator);
        return compiler.compile(layoutStr);
    }
}
