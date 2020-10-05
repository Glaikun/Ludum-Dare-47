package com.glaikunt.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.glaikunt.Display;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                // Resizable application, uses available space in browser
                GwtApplicationConfiguration gwtApplicationConfiguration = new GwtApplicationConfiguration(640, 480);
                gwtApplicationConfiguration.disableAudio = false;
                return gwtApplicationConfiguration;
                // Fixed size application:
                //return new GwtApplicationConfiguration(480, 320);
        }

        @Override
        public Audio getAudio() {
                return super.getAudio();
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new Display();
        }
}