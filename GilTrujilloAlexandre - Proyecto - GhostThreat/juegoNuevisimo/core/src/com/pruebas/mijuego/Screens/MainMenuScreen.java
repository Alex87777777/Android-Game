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
import com.pruebas.mijuego.Scenes.MenuHud;
import com.pruebas.mijuego.Scenes.SelectLevelHud;

public class MainMenuScreen implements Screen {
    /**
     * viewport de la pantalla
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
     * textura de fondo
     */
    private Texture backgroundTexture;
    /**
     * textura del tablero
     */
    private Texture boardTexture;
    /**
     * SpriteBatch principal
     */
    private SpriteBatch batch;
    /**
     * SpriteBatch del menu de seleccion de nivel
     */
    private SpriteBatch selectBatch;
    /**
     * HUD del menu principal
     */
    private MenuHud menuHud;
    /**
     * HUD de la seleccion de nivel
     */
    private SelectLevelHud selectHud;
    /**
     * Indica si se ha entrado en la seleccion de nivel
     */
    private boolean selectLevel;

    /**
     * Constructor del menu principal
     * @param game instancia del juego
     */
    public MainMenuScreen(Game game) {
        this.game = game;
        viewport = new FitViewport(MiJuego.v_width, MiJuego.v_height, new OrthographicCamera());
        stage = new Stage(viewport, ((MiJuego) game).batch);
        menuHud = new MenuHud(((MiJuego) game).batch);
        selectHud = new SelectLevelHud(((MiJuego) game).batch);
        backgroundTexture = new Texture(Gdx.files.internal("textures/fondo1.png"));
        boardTexture = new Texture(Gdx.files.internal("textures/frame2.png"));
        batch = new SpriteBatch();
        selectBatch = new SpriteBatch();
        MiJuego.playMusica(MiJuego.menuMusic);
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        Image logoKanji = new Image(new Texture("textures/main_logo.png"));

        table.row().pad(5, 5, 0, 5);
        table.add(logoKanji).size(logoKanji.getWidth() / 5, logoKanji.getHeight() / 5);
        table.row().pad(0, 5, 5, 5);

        stage.addActor(table);
    }
    /**
     * Maneja la entrada del usuario.
     * @param dt El tiempo delta desde el último fotograma
     */
    public void handleInput(float dt) {
        if (selectLevel) {
            Gdx.input.setInputProcessor(selectHud.stage);
        } else {
            Gdx.input.setInputProcessor(menuHud.stage);
        }
        if (!selectLevel) {
            if (menuHud.isPlayPressed()) {
                selectLevel = true;
                menuHud.setPlayPressed(false);
            } else if (menuHud.isClosePressed()) {
                Gdx.app.exit();
            } else if (menuHud.isSettingsPressed()) {
                game.setScreen(new SettingsScreen(game));
            } else if (menuHud.isStatsPressed()) {
                game.setScreen(new StatsScreen(game));
            } else if (menuHud.isCreditsPressed()) {
                game.setScreen(new CreditsScreen(game));
            } else if (menuHud.isAchievementsPressed()) {
                game.setScreen(new AchievementsScreen(game));
            } else if (menuHud.isHelpPressed()) {
                game.setScreen(new HelpScreen(game));
            }
        } else {
            if (selectHud.isLevel1Pressed()) {
                if (MiJuego.menuMusic.isPlaying()) {
                    MiJuego.menuMusic.stop();
                }
                game.setScreen(new PlayScreen((MiJuego) game, 1));
            } else if (selectHud.isLevel2Pressed()) {
                if (MiJuego.menuMusic.isPlaying()) {
                    MiJuego.menuMusic.stop();
                }
                game.setScreen(new PlayScreen((MiJuego) game, 2));
            } else if (selectHud.isBackPressed()) {
                selectHud.setBackPressed(false);
                selectLevel = false;
            }
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(menuHud.stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.draw();
        menuHud.draw();
        if (selectLevel) {
            float boardWidth = viewport.getWorldWidth() * 0.7f;
            float boardHeight = viewport.getWorldHeight() * 0.7f;
            float boardX = (viewport.getWorldWidth() - boardWidth) / 2;
            float boardY = (viewport.getWorldHeight() - boardHeight) / 2;

            selectBatch.setProjectionMatrix(viewport.getCamera().combined);
            selectBatch.begin();
            selectBatch.draw(boardTexture, boardX, boardY, boardWidth, boardHeight);
            selectBatch.end();
            selectHud.draw();
        }
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

    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        boardTexture.dispose();
        batch.dispose();
        selectBatch.dispose();
        stage.dispose();
    }
}
