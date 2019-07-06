package jp.aquabox.layout.compiler.render.compiler;

import jp.aquabox.layout.compiler.render.lexer.result.StringVariable;

// @XXX 文字列の取り扱いどうしようかな。。
public class StringRender extends Render {
    protected StringVariable.Parameter parameter;

    public StringRender(StringVariable.Parameter parameter) {
        this.parameter = parameter;
    }

    public Object render() {
        return parameter;
    }
}
