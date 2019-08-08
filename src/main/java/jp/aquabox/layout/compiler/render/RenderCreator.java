package jp.aquabox.layout.compiler.render;

import jp.aquabox.layout.compiler.render.compiler.Render;

abstract public class RenderCreator {
    public abstract Render create(String name);
}
