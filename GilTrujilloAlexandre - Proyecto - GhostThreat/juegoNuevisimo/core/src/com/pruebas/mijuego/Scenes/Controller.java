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
import com.pruebas.mijuego.Screens.PlayScreen;

public class Controller {
    /**
     * Indica si el botón izquierdo esta pulsado
     */
    boolean leftPressed;
    /**
     * Indica si el botón derecho esta pulsado
     */
    boolean rightPressed;
    /**
     * Indica si el botón de salto esta pulsado
     */
    boolean upPressed;
    /**
     * Indica si el botón de ataque esta pulsado
     */
    boolean attPressed;
    /**
     * Indica si el botón de pausa esta pulsado
     */
    boolean pausePressed;
    /**
     * Viewport de la pantalla
     */
    Viewport viewport;
    /**
     * Escenario de renderizado
     */
    public Stage stage;

    /**
     * Constructor del controller
     * @param batch SpriteBatch principal
     */
    public Controller(SpriteBatch batch) {
        viewport = new FitViewport(MiJuego.v_width, MiJuego.v_height);
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);

        Image pauseImg = new Image(new Texture("textures/pauseButton.png"));
        pauseImg.setSize(30, 25);
        pauseImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pausePressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                pausePressed = false;
            }
        });

        Image upImg = new Image(new Texture("textures/upIcon.png"));
        upImg.setSize(50, 50);
        upImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;

            }
        });
        Image attImg = new Image(new Texture("textures/attackIcon.png"));
        attImg.setSize(35, 35);
        attImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                attPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                attPressed = false;

            }
        });
        Image leftImg = new Image(new Texture("textures/leftIcon.png"));
        leftImg.setSize(35, 35);
        leftImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;

            }
        });
        Image rightImg = new Image(new Texture("textures/rightIcon.png"));
        rightImg.setSize(35, 35);
        rightImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;

            }
        });

        table.top().right();
        table.add(pauseImg).size(pauseImg.getWidth(), pauseImg.getHeight());
        table.row().pad(125, 5, 5, 5);
        table.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight());
        table.add();
        table.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight());
        anadirCeldas(table, 20);
        table.add(attImg).size(attImg.getWidth(), attImg.getHeight());
        table.add();
        table.add(upImg).size(upImg.getWidth(), upImg.getHeight());
        table.add();

        stage.addActor(table);
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isUpPressed() {
        return upPressed;
    }
    public boolean isAttPressed() {
        return attPressed;
    }
    public boolean isPausePressed() {
        return pausePressed;
    }
    public void setPausePressed(boolean pausePressed) {
        this.pausePressed = pausePressed;
    }

    /**
     * Dibuja el controller
     */
    public void draw() {
        stage.draw();
    }

    /**
     * Añade x celdas a una tabla
     * @param table Tabla en la que se quiere añadir celdas
     * @param n Numerod e celdas a añadir
     */
    public void anadirCeldas(Table table, int n) {
        for (int i = 0; i < n; i++) {
            table.add();
        }
    }
}
