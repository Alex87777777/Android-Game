package com.pruebas.mijuego.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pruebas.mijuego.MiJuego;

public class PauseHud {
    /**
     * Indica si esta pulsado el boton de reset
     */
    boolean resetPressed;
    /**
     * Indica si esta pulsado el boton de menú
     */
    boolean menuPressed;
    /**
     * Indica si esta pulsado el boton de continuar
     */
    boolean resumePressed;
    /**
     * Viewport de la pantalla
     */
    Viewport viewport;
    /**
     * Escenario de renderizado
     */
    public Stage stage;

    /**
     * Constructor del HUD del menú de pausa
     * @param batch SpriteBatch principal
     */
    public PauseHud(SpriteBatch batch) {
        viewport = new FitViewport(MiJuego.v_width, MiJuego.v_height);
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);

        Label pausedLabel = new Label(MiJuego.langManager.getText("pausado"), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        Image resetImg = new Image(new Texture("textures/retryIcon.png"));
        resetImg.setSize(35, 35);
        resetImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                resetPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                resetPressed = false;

            }
        });

        Image resumeImg = new Image(new Texture("textures/playIcon.png"));
        resumeImg.setSize(50, 50);
        resumeImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                resumePressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });

        Image menuImg = new Image(new Texture("textures/menuIcon.png"));
        menuImg.setSize(35, 35);
        menuImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                menuPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                menuPressed = false;

            }
        });

        table.center();
        table.add(pausedLabel).colspan(3).expandX().center();
        table.row();
        table.add(resetImg).size(resetImg.getWidth(), resetImg.getHeight()).padTop(20).padLeft(100);
        table.add(resumeImg).size(resumeImg.getWidth(), resumeImg.getHeight()).padTop(20);
        table.add(menuImg).size(menuImg.getWidth(), menuImg.getHeight()).padTop(20).padRight(100);

        stage.addActor(table);
    }

    public boolean isResetPressed() {
        return resetPressed;
    }
    public boolean isMenuPressed() {
        return menuPressed;
    }
    public boolean isResumePressed() {
        return resumePressed;
    }
    public void setResumePressed(boolean resumePressed) {
        this.resumePressed = resumePressed;
    }

    /**
     * Dibuja el escenario
     */
    public void draw() {
        stage.draw();
    }

}
