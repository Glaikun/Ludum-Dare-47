package com.glaikunt.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.glaikunt.application.Rectangle;
import com.glaikunt.application.TickTimer;
import com.glaikunt.ecs.components.BlockComponent;
import com.glaikunt.ecs.components.PlayerComponent;
import com.glaikunt.ecs.components.PositionComponent;
import com.glaikunt.ecs.components.SizeComponent;

public class DetectCollisionSystem extends EntitySystem {

    private ImmutableArray<Entity> blockEntities, playerEntities;

    private ComponentMapper<BlockComponent> blockCM = ComponentMapper.getFor(BlockComponent.class);
    private ComponentMapper<PlayerComponent> playerCM = ComponentMapper.getFor(PlayerComponent.class);
    private ComponentMapper<PositionComponent> pCM = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<SizeComponent> sCM = ComponentMapper.getFor(SizeComponent.class);

    private Rectangle playerRect = new Rectangle(), blockRect = new Rectangle();

    public DetectCollisionSystem(Engine engine) {
        this.blockEntities = engine.getEntitiesFor(Family.all(BlockComponent.class, PositionComponent.class, SizeComponent.class).get());
        this.playerEntities = engine.getEntitiesFor(Family.all(PlayerComponent.class, PositionComponent.class, SizeComponent.class).get());
    }

    private TickTimer tickTimer = new TickTimer(.2f);

    @Override
    public void update(float deltaTime) {

        tickTimer.tick(deltaTime);
        for (int p = 0; p < playerEntities.size(); ++p) {

            Entity playerEntity = playerEntities.get(p);
            PlayerComponent player = playerCM.get(playerEntity);
            PositionComponent playerPos = pCM.get(playerEntity);
            SizeComponent playerSize = sCM.get(playerEntity);

            boolean isSouthHack = false;
            for (int i = 0; i < blockEntities.size(); ++i) {

                Entity blockEntity = blockEntities.get(i);
                BlockComponent block = blockCM.get(blockEntity);
                PositionComponent blockPos = pCM.get(blockEntity);
                SizeComponent blockSize = sCM.get(blockEntity);

                playerRect.set(playerPos.x, blockPos.y, playerSize.x, playerSize.y);
                blockRect.set(blockPos.x, blockPos.y, blockSize.x, blockSize.y);


                if (playerRect.intersects(blockRect))  {

//                    if (playerPos.y < blockPos.y) {
//                        player.setNorthBlock(new Vector2(blockPos.x, blockPos.y));
//                    }

                    if (playerPos.y >= blockPos.y && player.getSouthBlock() == null) {
                        isSouthHack = true;
                        player.setSouthBlock(new Vector2(blockPos.x, blockPos.y + blockSize.x));
                    } else if (playerPos.y >= blockPos.y && player.getSouthBlock() != null) {
                        isSouthHack = true;
                        blockRect.set(player.getSouthBlock().x, blockPos.y, blockSize.x, blockSize.y);
                        if ((Math.abs((blockPos.y / 16)-(playerPos.y/16)) <= Math.abs((player.getSouthBlock().y / 16)-(playerPos.y/16)))) {
                            player.setSouthBlock(new Vector2(blockPos.x, blockPos.y + blockSize.x));
                        } else if (!playerRect.intersects(blockRect)) {
                            player.setSouthBlock(new Vector2(blockPos.x, blockPos.y + blockSize.x));
                        }
                    }
                }

//                if (isSouthHack && player.getNorthBlock() != null) {
//                    player.setNorthBlock(null);
//                }


            }

            if (!isSouthHack && player.getSouthBlock() != null) {
                player.setSouthBlock(null);
            }

            if (tickTimer.isTimerPassedTarget()) {
                tickTimer.resetTick();
            }
        }
    }
}
