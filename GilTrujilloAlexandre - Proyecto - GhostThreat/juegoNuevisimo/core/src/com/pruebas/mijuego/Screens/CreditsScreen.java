package com.pruebas.mijuego.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pruebas.mijuego.MiJuego;
import com.pruebas.mijuego.Scenes.CreditsHud;
import com.pruebas.mijuego.Scenes.SettingsHud;

public class CreditsScreen implements Screen {
    /**
     * Viewport de la pantalla
     */
    private Viewport viewport;
    /**
     * Escenario de renderizado
     */
    private Stage stage;
    /**
     * instancia del juego
     */
    private Game game;
    /**
     * SpriteBatch
     */
    private SpriteBatch batch;
    /**
     * Textura del fondo
     */
    private Texture backgroundTexture;
    /**
     * HUD de los creditos
     */
    private CreditsHud hud;

    /**
     * Constructor de la pantalla Creditos
     * @param game Instancia del juego
     */
    public CreditsScreen(Game game) {
        this.game = game;
        viewport = new FitViewport(MiJuego.v_width, MiJuego.v_height, new OrthographicCamera());
        stage = new Stage(viewport, ((MiJuego) game).batch);
        hud = new CreditsHud(((MiJuego) game).batch);
        backgroundTexture = new Texture(Gdx.files.internal("textures/fondo4.jpg"));
        batch = new SpriteBatch();
    }

    @Override
    public void show() {
    }

    /**
     * Maneja la entrada del usuario.
     * @param dt El tiempo delta desde el último fotograma
     */
    public void handleInput(float dt) {
        if (hud.isBackPressed()) {
            game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(63 / 255f, 63 / 255f, 63 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.draw();
        hud.draw();
        handleInput(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        stage.dispose();
        batch.dispose();
    }
}
