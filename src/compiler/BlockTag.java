package compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract public class BlockTag extends Executer {
    protected final Map<java.lang.String, java.lang.String> params = new HashMap<>();
    private final List<Executer> executers = new ArrayList<>();

    public void addExecuter(Executer executer) {
        executers.add(executer);
    }

    public List<Executer> getExecuters() {
        return new ArrayList<>(executers);
    }

    protected Map<String, String> getParams() {
        return new HashMap<>(params);
    }

    public void setParams(Map<java.lang.String, java.lang.String> params) {
        this.params.putAll(params);
    }
}
