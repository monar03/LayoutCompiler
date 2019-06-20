package jp.aquagear.layout.compiler.render.compiler;

// @XXX 文字列の取り扱いどうしようかな。。
public class StringRender extends Render {
    protected String str;

    public StringRender(String str) {
        this.str = str;
    }

    @Override
    public Object render() {
        return str;
    }
}
