package com.glaikunt.game.game.player;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.glaikunt.game.application.ApplicationResources;
import com.glaikunt.game.ecs.components.PositionComponent;
import com.glaikunt.game.ecs.components.SizeComponent;
import com.glaikunt.game.ecs.components.VelocityComponent;

public class PlayerActor extends Actor implements InputProcessor {

    private ApplicationResources applicationResources;
    private Entity playerEntity;

    private PositionComponent position;
    private VelocityComponent velocity;
    private SizeComponent size;

    private boolean left, right;
    private boolean jump;

    public PlayerActor(ApplicationResources applicationResources) {
        this.applicationResources = applicationResources;
        this.playerEntity = new Entity();

        this.position = new PositionComponent(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        this.size = new SizeComponent(32, 48);
        this.velocity = new VelocityComponent();
        this.velocity.x = 0;
        this.velocity.y = 5;

        this.playerEntity.add(position);
        this.playerEntity.add(velocity);
        this.playerEntity.add(size);
        this.applicationResources.getEngine().addEntity(playerEntity);
    }

    @Override
    public void act(float delta) {

        if (left) {


            velocity.x = Math.min(Math.max(velocity.x + (2.5f * delta), 0), 7.5f);
            System.err.println(velocity.x);
            position.x -= velocity.x;
        } else if (right) {

            velocity.x = Math.min(Math.max(velocity.x + (2.5f * delta), 0), 7.5f);
            System.err.println(velocity.x);
            position.x += velocity.x;
        }

        if (jump) {

            if (velocity.y > .5f) {
                velocity.y -= 8.6f * delta;
            } else {
                velocity.y -= (8.6f * 3) * delta;
            }

            position.y += velocity.y;

            if (position.y < Gdx.graphics.getHeight()/2f) {
                jump = false;
                position.y = Gdx.graphics.getHeight()/2f;
            }
        }
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {

        shapes.set(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(Color.GREEN);
        shapes.rect(position.x, position.y, size.x, size.y);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {

            right = true;
        } else if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {

            left = true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {

            velocity.x = 0;
            right = false;
//            velocity.x = 0;
        }
        if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {

            velocity.x = 0;
            left = false;
//            velocity.x = 0;
        }

        if (!jump && keycode == Input.Keys.SPACE) {

            jump = true;
            velocity.y = 5;
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
