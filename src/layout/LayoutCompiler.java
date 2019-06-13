package layout;

import com.sun.istack.internal.NotNull;
import layout.compiler.BlockTag;
import layout.compiler.Render;
import layout.compiler.StringRender;
import layout.lexer.Lexer;
import layout.lexer.result.Result;
import layout.lexer.result.StringResult;
import layout.lexer.result.TagEndResult;
import layout.lexer.result.TagStartResult;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class LayoutCompiler {
    private final Map<String, Class> classMap = new HashMap<>();
    private final Queue<Result> results = new ArrayDeque<>();

    public void addTag(@NotNull String key, Class aClass) {
        classMap.put(key, aClass);
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

        ((BlockTag) object).setParams(result.getParam());

        return compile((BlockTag) object);
    }
}
