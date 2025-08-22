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
import com.pruebas.mijuego.Scenes.SettingsHud;

public class SettingsScreen implements Screen {
    /**
     * viewport de la pantalla
     */
    private Viewport viewport;
    /**
     * escenariod e renderizado
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
     * textura del fondo
     */
    private Texture backgroundTexture;
    /**
     * textura del tablero
     */
    private Texture boardTexture;
    /**
     * HUD de la pantalla de settings
     */
    private SettingsHud hud;

    /**
     * Constructor de la pantalla de ajustes
     * @param game instancia del juego
     */
    public SettingsScreen(Game game) {
        this.game = game;
        viewport = new FitViewport(MiJuego.v_width, MiJuego.v_height, new OrthographicCamera());
        stage = new Stage(viewport, ((MiJuego) game).batch);
        hud = new SettingsHud(((MiJuego) game).batch);
        backgroundTexture = new Texture(Gdx.files.internal("textures/fondo1.png"));
        boardTexture = new Texture(Gdx.files.internal("textures/frame2.png"));
        batch = new SpriteBatch();
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Image boardImg = new Image(boardTexture);
        table.add(boardImg).center().size(300, 150);
        stage.addActor(table);

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
            MiJuego.guardarAjustes();
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
        boardTexture.dispose();
        stage.dispose();
        batch.dispose();
    }
}
