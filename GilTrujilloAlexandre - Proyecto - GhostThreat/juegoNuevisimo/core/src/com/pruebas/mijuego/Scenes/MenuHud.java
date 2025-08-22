package com.pruebas.mijuego.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pruebas.mijuego.MiJuego;

public class MenuHud {
    /**
     * Indica si se ha pulsado el boton de play
     */
    boolean playPressed;
    /**
     * Indica si se ha pulsado el boton de settings
     */
    boolean settingsPressed;
    /**
     * Indica si se ha pulsado el boton de close
     */
    boolean closePressed;
    /**
     * Indica si se ha pulsado el boton de stats
     */
    boolean statsPressed;
    /**
     * Indica si se ha pulsado el boton de logros
     */
    boolean achievementsPressed;
    /**
     * Indica si se ha pulsado el boton de creditos
     */
    boolean creditsPressed;
    /**
     * Indica si se ha pulsado el boton de ayuda
     */
    boolean helpPressed;
    /**
     * Viewport de la pantalla
     */
    Viewport viewport;
    /**
     * Escenario de renderizado
     */
    public Stage stage;

    /**
     * Constructor del HUD del menu principal
     * @param batch SpriteBatch principal
     */
    public MenuHud(SpriteBatch batch) {
        viewport = new FitViewport(MiJuego.v_width, MiJuego.v_height);
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);
        Table topLeftTable = new Table();
        stage.addActor(topLeftTable);
        Table topRightTable = new Table();
        stage.addActor(topRightTable);
        Table centerTable = new Table();
        stage.addActor(centerTable);

        Image closeImg = new Image(new Texture("textures/closeIcon.png"));
        closeImg.setSize(25, 25);
        closeImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                closePressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                closePressed = false;

            }
        });

        Image helpImg = new Image(new Texture("textures/helpIcon.png"));
        helpImg.setSize(20, 20);
        helpImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                helpPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                helpPressed = false;

            }
        });

        Image playImg = new Image(new Texture("textures/playIcon.png"));
        playImg.setSize(75, 75);
        playImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                playPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                playPressed = false;

            }
        });

        Image settingsImg = new Image(new Texture("textures/settingsIcon.png"));
        settingsImg.setSize(37, 37);
        settingsImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                settingsPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                settingsPressed = false;

            }
        });

        Image statsImg = new Image(new Texture("textures/statsIcon.png"));
        statsImg.setSize(37, 37);
        statsImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                statsPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                statsPressed = false;

            }
        });

        Image achievementsImg = new Image(new Texture("textures/achievementsIcon.png"));
        achievementsImg.setSize(37, 37);
        achievementsImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                achievementsPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                achievementsPressed = false;

            }
        });

        Image creditsImg = new Image(new Texture("textures/creditsIcon.png"));
        creditsImg.setSize(37, 37);
        creditsImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                creditsPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                creditsPressed = false;

            }
        });

        topLeftTable.top().left();
        topLeftTable.setFillParent(true);
        topLeftTable.row().pad(0, 0, 0, 100);
        topLeftTable.add(helpImg).size(helpImg.getWidth(), helpImg.getHeight()).pad(10);

        topRightTable.top().right();
        topRightTable.setFillParent(true);
        topRightTable.row().pad(0, 0, 0, 100);
        topRightTable.add(closeImg).size(closeImg.getWidth(), closeImg.getHeight()).pad(10);

        centerTable.center();
        centerTable.setFillParent(true);
        centerTable.padTop(75);
        centerTable.add(achievementsImg).size(achievementsImg.getWidth(), achievementsImg.getHeight()).padRight(5);
        centerTable.add(statsImg).size(statsImg.getWidth(), statsImg.getHeight());
        centerTable.add(playImg).size(playImg.getWidth(), playImg.getHeight());
        centerTable.add(settingsImg).size(settingsImg.getWidth(), settingsImg.getHeight());
        centerTable.add(creditsImg).size(creditsImg.getWidth(), creditsImg.getHeight()).padLeft(5);
    }

    public boolean isPlayPressed() {
        return playPressed;
    }

    public void setPlayPressed(boolean playPressed) {
        this.playPressed = playPressed;
    }

    public boolean isClosePressed() {
        return closePressed;
    }

    public boolean isSettingsPressed() {
        return settingsPressed;
    }

    public boolean isStatsPressed() {
        return statsPressed;
    }

    public boolean isCreditsPressed() {
        return creditsPressed;
    }

    public boolean isAchievementsPressed() {
        return achievementsPressed;
    }

    public boolean isHelpPressed() {
        return helpPressed;
    }

    /**
     * Dibuja el escenario
     */
    public void draw() {
        stage.draw();
    }
}
