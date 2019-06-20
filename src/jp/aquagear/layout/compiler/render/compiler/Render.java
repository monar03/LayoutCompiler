package jp.aquagear.layout.compiler.render.compiler;

import java.util.ArrayList;
import java.util.List;

abstract public class Render {
    private final List<Render> executers = new ArrayList<>();

    public void addRender(Render render) {
        executers.add(render);
    }

    public List<Render> getRenders() {
        return new ArrayList<>(executers);
    }

    abstract public Object render();
}
