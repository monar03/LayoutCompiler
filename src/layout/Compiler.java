package layout;

import layout.render.compiler.Render;

import java.util.Map;

public class Compiler {
    private final Map<String, Map<String, String>> design;

    public Compiler(String design) {
        this.design = new layout.design.Compiler().compile(design);
    }

    Render compiler(String layoutStr) {
        final layout.render.Compiler compiler = new layout.render.Compiler(design);
        return new Render() {
            @Override
            public void render(Render render) {

            }
        };

    }
}
