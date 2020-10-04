package com.glaikunt.dialog;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.glaikunt.application.ApplicationResources;
import com.glaikunt.application.cache.FontCache;
import com.glaikunt.ecs.components.PositionComponent;


public class WrongWordsActor extends Actor {

    private ApplicationResources applicationResources;

    private BitmapFont font;
    private GlyphLayout weak, attention, snap_out, dramatic;
    private PositionComponent weakPos, attentionPos, snapOutPos, dramaticPos;
    private float weakOrbit, attentionOrbit, snapOutOrbit, dramaticOrbit;
    private float rad;

    public WrongWordsActor(ApplicationResources applicationResources) {

        this.applicationResources = applicationResources;
        this.font = applicationResources.getCacheRetriever().getFontCache(FontCache.SENTENCE_FONT);

        this.weak = new GlyphLayout();
        this.weak.setText(font, "Weak", new Color(1, 0, 0, .05f), 0, Align.center, false);

        this.attention = new GlyphLayout();
        this.attention.setText(font, "Attention", new Color(1, 0, 0, .05f), 0, Align.center, false);

        this.snap_out = new GlyphLayout();
        this.snap_out.setText(font, "Snap Out", new Color(1, 0, 0, .05f), 0, Align.center, false);

        this.dramatic = new GlyphLayout();
        this.dramatic.setText(font, "Dramatic", new Color(1, 0, 0, .05f), 0, Align.center, false);

        this.weakPos = new PositionComponent(0, 0);
        this.attentionPos = new PositionComponent(0, 0);
        this.snapOutPos = new PositionComponent(0, 0);
        this.dramaticPos = new PositionComponent(0, 0);
        this.weakOrbit = 15;
        this.attentionOrbit = 12;
        this.snapOutOrbit = 19;
        this.dramaticOrbit = 10;
        this.rad = 50;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        font.draw(batch, weak, 525 + (weak.width/2) + weakPos.x, 78 + (weak.height/2) + weakPos.y);
        font.draw(batch, attention, 340 + (attention.width/2) + attentionPos.x, 350 + (attention.height/2) + attentionPos.y);
        font.draw(batch, snap_out, 403 + (snap_out.width/2) + snapOutPos.x, 232 + (snap_out.height/2) + snapOutPos.y);
        font.draw(batch, dramatic, 15 + (dramatic.width/2) + dramaticPos.x, 76 + (dramatic.height/2) + dramaticPos.y);
    }

    @Override
    public void act(float delta) {

        rad += .8f * delta;
        weakPos.x = (float) (weakOrbit * Math.cos(rad));
        weakPos.y = (float) (weakOrbit * Math.sin(rad));

        attentionPos.x = (float) (attentionOrbit * Math.cos(rad));
        attentionPos.y = (float) (attentionOrbit * Math.sin(rad));

        snapOutPos.x = (float) (snapOutOrbit * Math.cos(rad));
        snapOutPos.y = (float) (snapOutOrbit * Math.sin(rad));

        dramaticPos.x = (float) (dramaticOrbit * Math.cos(rad));
        dramaticPos.y = (float) (dramaticOrbit * Math.sin(rad));
    }
}
