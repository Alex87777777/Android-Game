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

public class SelectLevelHud {
    /**
     * Indica si el boton del nivel 1 esta pulsado
     */
    boolean level1Pressed;
    /**
     * Indica si el boton del nivel 2 esta pulsado
     */
    boolean level2Pressed;
    /**
     * Indica si el boton de atrás esta pulsado
     */
    boolean backPressed;
    /**
     * Viewport de la pantalla
     */
    Viewport viewport;
    /**
     * Escenario de renderizado
     */
    public Stage stage;

    /**
     * Constructor del HUD del menú de seleccion de nivel
     * @param batch
     */
    public SelectLevelHud(SpriteBatch batch) {
        viewport = new FitViewport(MiJuego.v_width, MiJuego.v_height);
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        Table topTable = new Table();
        topTable.setFillParent(true);

        Label selectLabel = new Label(MiJuego.langManager.getText("select"), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        Image level1Img = new Image(new Texture("textures/level1.png"));
        level1Img.setSize(50, 50);
        level1Img.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                level1Pressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                level1Pressed = false;

            }
        });

        Image level2Img = new Image(new Texture("textures/level2.png"));
        level2Img.setSize(50, 50);
        level2Img.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                level2Pressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                level2Pressed = false;
            }
        });

        Image backImg = new Image(new Texture("textures/backIcon.png"));
        backImg.setSize(35, 35);
        backImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                backPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                backPressed = false;

            }
        });

        topTable.top().left();
        topTable.add(backImg).size(backImg.getWidth(), backImg.getHeight());


        table.center();
        table.add(selectLabel).colspan(2).expandX().center();
        table.row();
        table.add(level1Img).size(level1Img.getWidth(), level1Img.getHeight()).padTop(20).padLeft(100);
        table.add(level2Img).size(level2Img.getWidth(), level2Img.getHeight()).padTop(20).padRight(100);

        stage.addActor(table);
        stage.addActor(topTable);
    }

    public boolean isBackPressed() {
        return backPressed;
    }

    public void setBackPressed(boolean backPressed) {
        this.backPressed = backPressed;
    }

    public boolean isLevel1Pressed() {
        return level1Pressed;
    }

    public boolean isLevel2Pressed() {
        return level2Pressed;
    }

    /**
     * Dibuja el escenario
     */
    public void draw() {
        stage.draw();
    }
}
