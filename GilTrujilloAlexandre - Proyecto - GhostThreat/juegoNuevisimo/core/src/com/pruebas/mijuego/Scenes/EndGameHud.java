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

public class EndGameHud {
    /**
     * Indica si se ha pulsado el botón de reintentar
     */
    boolean retryPressed;
    /**
     * Indica si se ha pulsado el botón de atrás
     */
    boolean backPressed;
    /**
     * Viewport de la pantalla
     */
    Viewport viewport;
    /**
     * Escenario de renderizado
     */
    Stage stage;

    /**
     * Constructor del HUD de la pantalla de fin de partida
     * @param batch SpriteBatch principal
     */
    public EndGameHud(SpriteBatch batch) {
        viewport = new FitViewport(MiJuego.v_width, MiJuego.v_height);
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.left().bottom();

        Image retryImg = new Image(new Texture("textures/retryIcon.png"));
        retryImg.setSize(50, 50);
        retryImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                retryPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                retryPressed = false;

            }
        });

        Image backImg = new Image(new Texture("textures/menuIcon.png"));
        backImg.setSize(50, 50);
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

        table.row().pad(5, 5, 25, 5);
        anadirCeldas(table, 12);
        table.add(retryImg).size(retryImg.getWidth(), retryImg.getHeight());
        anadirCeldas(table, 5);
        table.add(backImg).size(backImg.getWidth(), backImg.getHeight());
        stage.addActor(table);
    }

    public boolean isRetryPressed() { return retryPressed;}
    public boolean isBackPressed() { return backPressed;}

    /**
     * Dibuja el escenario
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
