package jp.aquabox.layout.compiler.render;

import jp.aquabox.layout.compiler.render.compiler.BlockRender;
import jp.aquabox.layout.compiler.render.compiler.Render;
import jp.aquabox.layout.compiler.render.compiler.StringRender;
import jp.aquabox.layout.compiler.render.lexer.Lexer;
import jp.aquabox.layout.compiler.render.lexer.result.*;

import java.util.*;

public class Compiler {
    private final Queue<Result> results = new ArrayDeque<>();
    private final Map<String, Map<String, String>> designMap;
    private RenderCreator renderCreator;


    public Compiler() {
        this.designMap = new HashMap<>();
    }

    public Compiler(Map<String, Map<String, String>> design) {
        this.designMap = new HashMap<>(design);
    }

    public void setCreator(RenderCreator renderCreator) {
        this.renderCreator = renderCreator;
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
        final Render render = renderCreator.create(tagStartResult.getName());

        final Map<String, StringVariable.Parameter> param = tagStartResult.getParam();
        if (param.containsKey("class")) {
            ((BlockRender) render).setStyles(designMap.get(param.get("class").value));
        }
        ((BlockRender) render).setParams(param);

        while (true) {
            final Result result = results.poll();
            if (result == null) {
                break;
            }

            if (result instanceof StringResult) {
                // @FIXME テキストの処理をどうするか考える
                render.addRender(new StringRender(((StringResult) result).getText()));
            } else if (result instanceof TagEndResult) {
                if (tagStartResult.getName().equals(((TagEndResult) result).getName())) {
                    return render;
                }
            } else if (result instanceof TagStartResult) {
                render.addRender(tagCompile((TagStartResult) result));
            }
        }

        return render;
    }
}
