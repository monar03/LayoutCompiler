package compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract public class BlockTag extends Executer {
    protected final List<Executer> executers = new ArrayList<>();
    protected final Map<java.lang.String, java.lang.String> params = new HashMap<>();

    public void addExecuter(Executer executer) {
        executers.add(executer);
    }

    public void setParams(Map<java.lang.String, java.lang.String> params) {
        this.params.putAll(params);
    }
}
