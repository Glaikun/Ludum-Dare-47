package com.glaikunt.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.glaikunt.application.ApplicationResources;
import com.glaikunt.application.Rectangle;
import com.glaikunt.application.cache.FontCache;
import com.glaikunt.ecs.components.PositionComponent;


public class CorrectWordsActor extends Actor {

    private ApplicationResources applicationResources;

    private BitmapFont font;
    private GlyphLayout wasnt, your, fault;
    private PositionComponent wasntPos, yourPos, faultPos;
    private float wasntOrbit, yourOrbit, faultOrbit;

    private float rad;

    private Rectangle wasntRect, youRect, faultRect;

    public CorrectWordsActor(ApplicationResources applicationResources) {

        this.applicationResources = applicationResources;
        this.font = applicationResources.getCacheRetriever().getFontCache(FontCache.SENTENCE_FONT);

        this.wasnt = new GlyphLayout();
        this.wasnt.setText(font, "Wasn't", new Color(0, 1, 0, .5f), 0, Align.center, false);

        this.your = new GlyphLayout();
        this.your.setText(font, "Your", new Color(0, 1, 0, .5f), 0, Align.center, false);

        this.fault = new GlyphLayout();
        this.fault.setText(font, "Fault", new Color(0, 1, 0, .5f), 0, Align.center, false);

        this.wasntRect = new Rectangle();
        this.youRect = new Rectangle();
        this.faultRect = new Rectangle();

        this.wasntPos = new PositionComponent(0, 0);
        this.yourPos = new PositionComponent(0, 0);
        this.faultPos = new PositionComponent(0, 0);
        this.wasntOrbit = 15;
        this.yourOrbit = 12;
        this.faultOrbit = 19;
        this.rad = 50;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        font.draw(batch, wasnt, 150 + (wasnt.width/2) + wasntPos.x, 150 + (wasnt.height/2) + wasntPos.y);
        font.draw(batch, your, 500 + (your.width/2) + yourPos.x, 400 + (your.height/2) + yourPos.y);
        font.draw(batch, fault, 67 + (fault.width/2) + faultPos.x, 350 + (fault.height/2) + faultPos.y);
    }

    @Override
    public void act(float delta) {

        rad += .8f * delta;
        wasntPos.x = (float) (wasntOrbit * Math.cos(rad));
        wasntPos.y = (float) (wasntOrbit * Math.sin(rad));

        yourPos.x = (float) (yourOrbit * Math.cos(rad));
        yourPos.y = (float) (yourOrbit * Math.sin(rad));

        faultPos.x = (float) (faultOrbit * Math.cos(rad));
        faultPos.y = (float) (faultOrbit * Math.sin(rad));

        wasntRect.set(150 + wasntPos.x, 150 - (wasnt.height/2) + wasntPos.y, wasnt.width, wasnt.height);
        youRect.set(500 + yourPos.x, 400 - (your.height/2) + yourPos.y, your.width, your.height);
        faultRect.set(67+ faultPos.x, 350 - (fault.height/2) + faultPos.y, fault.width, fault.height);

        if (Gdx.input.justTouched()) {

            if (wasntRect.contains(applicationResources.getFrontStageMousePosition().x, applicationResources.getFrontStageMousePosition().y)) {

                Gdx.app.log("DEBUG", "WASN'T!");
            } else if (youRect.contains(applicationResources.getFrontStageMousePosition().x, applicationResources.getFrontStageMousePosition().y)) {

                Gdx.app.log("DEBUG", "Your!");
            } else if (faultRect.contains(applicationResources.getFrontStageMousePosition().x, applicationResources.getFrontStageMousePosition().y)) {

                Gdx.app.log("DEBUG", "Fault!");
            }
        }
    }
}
