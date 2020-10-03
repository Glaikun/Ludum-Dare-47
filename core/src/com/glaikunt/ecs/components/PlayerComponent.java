package com.glaikunt.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class PlayerComponent implements Component {

    private Vector2 northBlock, eastBlock, southBlock, westBlock;

    public Vector2 getNorthBlock() {
        return northBlock;
    }

    public void setNorthBlock(Vector2 northBlock) {
        this.northBlock = northBlock;
    }

    public Vector2 getEastBlock() {
        return eastBlock;
    }

    public void setEastBlock(Vector2 eastBlock) {
        this.eastBlock = eastBlock;
    }

    public Vector2 getSouthBlock() {
        return southBlock;
    }

    public void setSouthBlock(Vector2 southBlock) {
        this.southBlock = southBlock;
    }

    public Vector2 getWestBlock() {
        return westBlock;
    }

    public void setWestBlock(Vector2 westBlock) {
        this.westBlock = westBlock;
    }
}
