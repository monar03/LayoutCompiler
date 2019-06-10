import com.sun.istack.internal.NotNull;
import compiler.BlockTag;
import compiler.Executer;
import lexer.Lexer;
import lexer.result.Result;
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

    private Executer compile() {
        final Result result = results.poll();
        if (result instanceof TagStartResult) {
            return getTagStartResult((TagStartResult) result);
        }

        return new Executer() {
            @Override
            public void execute() {

            }
        };
    }

    private Executer compile(BlockTag blockTag) {
        final Result result = results.poll();
        if (result instanceof TagEndResult) {
            if (blockTag.getClass() == classMap.get(((TagEndResult) result).name)) {
                return blockTag;
            }
        } else if (result instanceof TagStartResult) {
            blockTag.addExecuter(getTagStartResult((TagStartResult) result));
            return blockTag;
        }

        return blockTag;
    }

    private Executer getTagStartResult(TagStartResult result) {
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
