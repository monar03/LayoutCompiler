import com.sun.istack.internal.NotNull;
import compiler.BlockTag;
import compiler.Executer;
import compiler.StringExecuter;
import lexer.Lexer;
import lexer.result.Result;
import lexer.result.StringResult;
import lexer.result.TagEndResult;
import lexer.result.TagStartResult;

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
    public Executer compile(@NotNull String str) {
        final Lexer lexer = new Lexer(str);
        results.addAll(lexer.analysis());

        return compile();
    }

    @NotNull
    private Executer compile() {
        return compile(new BlockTag() {
            @Override
            public void execute() {
                for (Executer executer : getExecuters()) {
                    executer.execute();
                }
            }
        });
    }

    @NotNull
    private Executer compile(@NotNull BlockTag blockTag) {
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
                blockTag.addExecuter(getTagStartResult((TagStartResult) result));
            } else if (result instanceof StringResult) {
                // @FIXME テキストの処理をどうするか考える
                blockTag.addExecuter(new StringExecuter(((StringResult) result).getText()));
            }
        }

        return blockTag;
    }

    @NotNull
    private Executer getTagStartResult(@NotNull TagStartResult result) {
        final Object object;
        try {
            object = classMap.get(result.getName()).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("cannot excuter");
        }

        if (!(object instanceof Executer)) {
            throw new IllegalStateException("cannot executer");
        }

        ((BlockTag) object).setParams(result.getParam());

        return compile((BlockTag) object);
    }
}
