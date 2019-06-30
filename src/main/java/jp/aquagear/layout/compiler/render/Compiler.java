package jp.aquagear.layout.compiler.render;

import jp.aquagear.layout.compiler.render.compiler.BlockRender;
import jp.aquagear.layout.compiler.render.compiler.Render;
import jp.aquagear.layout.compiler.render.compiler.StringRender;
import jp.aquagear.layout.compiler.render.lexer.Lexer;
import jp.aquagear.layout.compiler.render.lexer.result.*;

import java.util.*;

public class Compiler {
    private final Map<String, Class> classMap = new HashMap<>();
    private final Queue<Result> results = new ArrayDeque<>();
    private final Map<String, Map<String, String>> designMap;


    public Compiler() {
        this.designMap = new HashMap<>();
    }

    public Compiler(Map<String, Map<String, String>> design) {
        this.designMap = new HashMap<>(design);
    }

    void addTag(String key, Class aClass) {
        classMap.put(key, aClass);
    }

    public void addTagAll(Map<String, Class> classMap) {
        this.classMap.putAll(new HashMap<>(classMap));
    }

    public List<Render> compile(String str) {
        final Lexer lexer = new Lexer(str);
        results.addAll(lexer.analysis());

        return compile();
    }

    private List<Render> compile() {
        final List<Render> renders = new ArrayList<>();
        while (true) {
            final Result result = results.poll();
            if (result == null) {
                break;
            }

            if (result instanceof TagStartResult) {
                renders.add(tagCompile((TagStartResult) result));
            } else if (result instanceof StringResult) {
                // @FIXME テキストの処理をどうするか考える
                renders.add(new StringRender(((StringResult) result).getText()));
            }
        }

        return renders;
    }

    private Render tagCompile(TagStartResult tagStartResult) {
        final Object object;
        try {
            object = classMap.get(tagStartResult.getName()).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("cannot excuter");
        }

        if (!(object instanceof Render)) {
            throw new IllegalStateException("cannot executer");
        }

        final Map<String, StringVariable.Parameter> param = tagStartResult.getParam();
        if (param.containsKey("class")) {
            ((BlockRender) object).setStyles(designMap.get(param.get("class").value));
        }
        ((BlockRender) object).setParams(param);

        while (true) {
            final Result result = results.poll();
            if (result == null) {
                break;
            }

            if (result instanceof StringResult) {
                // @FIXME テキストの処理をどうするか考える
                ((BlockRender) object).addRender(new StringRender(((StringResult) result).getText()));
            } else if (result instanceof TagEndResult) {
                if (tagStartResult.getName().equals(((TagEndResult) result).getName())) {
                    return (Render) object;
                }
            } else if (result instanceof TagStartResult) {
                ((BlockRender) object).addRender(tagCompile((TagStartResult) result));
            }
        }

        return (Render) object;
    }
}
