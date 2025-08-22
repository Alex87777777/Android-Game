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

public class StatsHud {
    /**
     * indica si se pulsa el botón de atrás
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
     * Constructor del HUD de Stats
     * @param batch
     */
    public StatsHud(SpriteBatch batch) {
        viewport = new FitViewport(MiJuego.v_width, MiJuego.v_height);
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        Table centerTable = new Table();
        Table topTable = new Table();
        stage.addActor(centerTable);
        stage.addActor(topTable);

        Image backImg = new Image(new Texture("textures/backIcon.png"));
        backImg.setSize(25, 25);
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

        Label mainLabel = new Label(MiJuego.langManager.getText("stats"), font);
        topTable.top().left();
        topTable.setFillParent(true);
        topTable.add(backImg).size(backImg.getWidth(), backImg.getHeight());
        topTable.add(mainLabel).padLeft((MiJuego.v_width / 3));

        MiJuego.cargarEstadisticas();
        int saltos = MiJuego.prefs.getInteger("saltos", 0);
        int muertes = MiJuego.prefs.getInteger("muertes", 0);
        int eliminaciones = MiJuego.prefs.getInteger("eliminaciones", 0);
        int maxScore = MiJuego.prefs.getInteger("maxScore", 0);

        Label saltosLabel = new Label(MiJuego.langManager.getText("saltos") + ": " + saltos, font);
        Label muertesLabel = new Label(MiJuego.langManager.getText("muertes") + ": " + muertes, font);
        Label eliminacionesLabel = new Label(MiJuego.langManager.getText("eliminaciones") + ": " + eliminaciones, font);
        Label maxScoreLabel = new Label(MiJuego.langManager.getText("maxima") + ": " + maxScore, font);

        centerTable.center();
        centerTable.setFillParent(true);
        centerTable.add(saltosLabel);
        centerTable.row().pad(5, 5, 5, 5);
        centerTable.add(muertesLabel);
        centerTable.row().pad(5, 5, 5, 5);
        centerTable.add(eliminacionesLabel);
        centerTable.row().pad(5, 5, 5, 5);
        centerTable.add(maxScoreLabel);

    }

    public boolean isBackPressed() {
        return backPressed;
    }

    /**
     * Dibuja el escenario
     */
    public void draw() {
        stage.draw();
    }

    /**
     * Libera memoria
     */
    public void dispose() {
        stage.dispose();
    }
}
