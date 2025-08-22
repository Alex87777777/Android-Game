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

public class CreditsHud {
    /**
     * Indica si se pulsa el botón de atrás
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
     * Textura del botón de atrás
     */
    private Texture backTexture;

    /**
     * Constructor del HUD de la pantalla de creditos
     * @param batch SpriteBatch principal
     */
    public CreditsHud(SpriteBatch batch) {
        viewport = new FitViewport(MiJuego.v_width, MiJuego.v_height);
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        Table centerTable = new Table();
        Table topTable = new Table();
        stage.addActor(centerTable);
        stage.addActor(topTable);

        backTexture = new Texture("textures/backIcon.png");
        Image backImg = new Image(backTexture);
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

        topTable.top().left();
        topTable.setFillParent(true);
        topTable.add(backImg).size(backImg.getWidth(), backImg.getHeight());

        Label creditsLabel = new Label(MiJuego.langManager.getText("creditos"), font);
        Label programmerLabel = new Label(MiJuego.langManager.getText("desarrollador")+": Alexandre Gil Trujillo", font);
        Label musicaLabel = new Label(MiJuego.langManager.getText("musica2")+": Suno AI", font);
        Label sfxLabel = new Label(MiJuego.langManager.getText("sfx")+": Pixabay", font);
        Label artLabel = new Label(MiJuego.langManager.getText("arte")+": Flaticon", font);

        centerTable.center();
        centerTable.setFillParent(true);
        centerTable.add(creditsLabel);
        centerTable.row().pad(5, 5, 5, 5);
        centerTable.add(programmerLabel);
        centerTable.row().pad(5, 5, 5, 5);
        centerTable.add(musicaLabel);
        centerTable.row().pad(5, 5, 5, 5);
        centerTable.add(sfxLabel);
        centerTable.row().pad(5, 5, 5, 5);
        centerTable.add(artLabel);
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
        backTexture.dispose();
    }
}
