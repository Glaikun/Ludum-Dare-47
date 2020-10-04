package com.glaikunt.ecs.components;

import com.badlogic.ashley.core.Component;

public class LevelComponent implements Component {

    private int currentLevel = 1;

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }
}
