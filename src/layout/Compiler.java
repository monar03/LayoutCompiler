package layout;

import layout.render.compiler.Render;

import java.util.Map;

public class Compiler {
    private final Map<String, Map<String, String>> design;

    public Compiler(String design) {
        this.design = new layout.design.Compiler().compile(design);
    }

    Render compile(String layoutStr, Map<String, Class> classMap) {
        final layout.render.Compiler compiler = new layout.render.Compiler(design);
        compiler.addTagAll(classMap);
        return compiler.compile(layoutStr);
    }
}
