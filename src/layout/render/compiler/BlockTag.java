package layout.render.compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract public class BlockTag extends Render {
    protected final Map<java.lang.String, java.lang.String> params = new HashMap<>();
    private final List<Render> executers = new ArrayList<>();

    public void addRender(Render render) {
        executers.add(render);
    }

    public List<Render> getRenders() {
        return new ArrayList<>(executers);
    }

    protected Map<String, String> getParams() {
        return new HashMap<>(params);
    }

    public void setParams(Map<java.lang.String, java.lang.String> params) {
        this.params.putAll(params);
    }
}
