package jp.aquagear.layout.compiler;

import jp.aquagear.layout.compiler.render.compiler.Render;

import java.util.List;
import java.util.Map;

public class Compiler {
    private final Map<String, Map<String, String>> design;

    public Compiler(String design) {
        this.design = new jp.aquagear.layout.compiler.design.Compiler().compile(design);
    }

    public List<Render> compile(String layoutStr, Map<String, Class> classMap) {
        final jp.aquagear.layout.compiler.render.Compiler compiler = new jp.aquagear.layout.compiler.render.Compiler(design);
        compiler.addTagAll(classMap);
        return compiler.compile(layoutStr);
    }
}
