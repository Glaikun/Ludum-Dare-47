package com.glaikunt.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class SizeComponent extends Vector2 implements Component {

    public SizeComponent(Vector2 size) {
        super(size);
    }

    public SizeComponent(float x, float y) {
        super(x, y);
    }
}
