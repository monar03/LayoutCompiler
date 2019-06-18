package jp.aquagear.layout.compiler.render;

import com.sun.istack.internal.NotNull;
import jp.aquagear.layout.compiler.render.compiler.BlockTag;
import jp.aquagear.layout.compiler.render.compiler.Render;
import jp.aquagear.layout.compiler.render.compiler.StringRender;
import jp.aquagear.layout.compiler.render.lexer.Lexer;
import jp.aquagear.layout.compiler.render.lexer.result.Result;
import jp.aquagear.layout.compiler.render.lexer.result.StringResult;
import jp.aquagear.layout.compiler.render.lexer.result.TagEndResult;
import jp.aquagear.layout.compiler.render.lexer.result.TagStartResult;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

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

    void addTag(@NotNull String key, Class aClass) {
        classMap.put(key, aClass);
    }

    public void addTagAll(@NotNull Map<String, Class> classMap) {
        this.classMap.putAll(new HashMap<>(classMap));
    }

    @NotNull
    public Render compile(@NotNull String str) {
        final Lexer lexer = new Lexer(str);
        results.addAll(lexer.analysis());

        return compile();
    }

    @NotNull
    private Render compile() {
        return compile(new BlockTag() {
            @Override
            public void render(Render render) {
                for (Render executer : getRenders()) {
                    executer.render(this);
                }
            }
        });
    }

    @NotNull
    private Render compile(@NotNull BlockTag blockTag) {
        while (true) {
            final Result result = results.poll();
            if (result == null) {
                break;
            }

            if (result instanceof TagEndResult) {
                if (blockTag.getClass() == classMap.get(((TagEndResult) result).name)) {
                    break;
                }
            } else if (result instanceof TagStartResult) {
                blockTag.addRender(getTagStartResult((TagStartResult) result));
            } else if (result instanceof StringResult) {
                // @FIXME テキストの処理をどうするか考える
                blockTag.addRender(new StringRender(((StringResult) result).getText()));
            }
        }

        return blockTag;
    }

    @NotNull
    private Render getTagStartResult(@NotNull TagStartResult result) {
        final Object object;
        try {
            object = classMap.get(result.getName()).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("cannot excuter");
        }

        if (!(object instanceof Render)) {
            throw new IllegalStateException("cannot executer");
        }

        final Map<String, String> param = result.getParam();
        if (param.containsKey("class")) {
            param.putAll(designMap.get(param.get("class")));
        }
        ((BlockTag) object).setParams(param);

        return compile((BlockTag) object);
    }
}
