package jp.aquagear.layout.compiler;

import jp.aquagear.layout.compiler.render.compiler.Render;

import java.util.List;
import java.util.Map;

public class Compiler {
    private final Map<String, Class> classMap;

    public Compiler(Map<String, Class> classMap) {
        this.classMap = classMap;
    }

    public List<Render> compile(String layoutStr, String design) {
        Map<String, Map<String, String>> designMap = new jp.aquagear.layout.compiler.design.Compiler().compile(design);
        final jp.aquagear.layout.compiler.render.Compiler compiler = new jp.aquagear.layout.compiler.render.Compiler(designMap);
        compiler.addTagAll(classMap);
        return compiler.compile(layoutStr);
    }
}
