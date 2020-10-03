package com.glaikunt.game.collision;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.glaikunt.application.ApplicationResources;
import com.glaikunt.ecs.components.BlockComponent;
import com.glaikunt.ecs.components.PositionComponent;
import com.glaikunt.ecs.components.SizeComponent;

public class BlockActor extends Actor {

    private ApplicationResources applicationResources;
    private Entity blockEntity;

    private PositionComponent position;
    private SizeComponent size;

    public BlockActor(ApplicationResources applicationResources, float xPos, float yPos) {

        this.applicationResources = applicationResources;
        this.blockEntity = new Entity();
        this.position = new PositionComponent(xPos, yPos);
        this.size = new SizeComponent(16, 16);

        blockEntity.add(position);
        blockEntity.add(size);
        blockEntity.add(new BlockComponent());
        applicationResources.getEngine().addEntity(blockEntity);
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {

        shapes.setColor(Color.BLUE);
        shapes.rect(position.x, position.y, size.x, size.y);
    }


}
