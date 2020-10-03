package com.glaikunt.game.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class PositionComponent extends Vector2 implements Component {

    public PositionComponent(Vector2 vector2) {
        super(vector2);
    }

    public PositionComponent(float xPos, float yPos) {
        super(xPos, yPos);
    }
}
