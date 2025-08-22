package com.pruebas.mijuego.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pruebas.mijuego.MiJuego;
import com.pruebas.mijuego.Scenes.EndGameHud;
import com.pruebas.mijuego.Scenes.GameHud;


public class EndGameScreen implements Screen {
    /**
     * Viewport de la pantalla
     */
    private Viewport viewport;
    /**
     * Escenario de renderizado
     */
    private Stage stage;
    /**
     * Instancia del juego
     */
    private Game game;
    /**
     * HUD de la pantalla de fin de partida
     */
    private EndGameHud hud;
    /**
     * Textura de fondo
     */
    private Texture backgroundTexture;
    /**
     * SpriteBatch
     */
    private SpriteBatch batch;
    /**
     * numero del nivel
     */
    private int nivel;
    /**
     * puntuacion del jugador
     */
    private int score;

    /**
     * Constructor de la pantalla de fin de partida
     * @param game instancia del juego
     * @param numNivel numero del nivel
     * @param win indica si el jugador ha muerto o si ha completado el nivel
     */
    public EndGameScreen(Game game, int numNivel, boolean win) {
        this.game = game;
        viewport = new FitViewport(MiJuego.v_width, MiJuego.v_height, new OrthographicCamera());
        stage = new Stage(viewport, ((MiJuego) game).batch);
        hud = new EndGameHud(((MiJuego) game).batch);
        batch = new SpriteBatch();
        backgroundTexture = new Texture(Gdx.files.internal("textures/frame2.png"));

        score = 0;
        nivel = numNivel;

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label endGameLabel;
        if (!win) {
            endGameLabel = new Label(MiJuego.langManager.getText("gameover"), font);
        } else {
            endGameLabel = new Label(MiJuego.langManager.getText("completado"), font);
            score += 250 + GameHud.getWorldTimer();
            if (nivel == 1) {
                if (!MiJuego.isLogro1()) {
                    MiJuego.setLogro1(true);
                }
            } else {
                if (!MiJuego.isLogro2()) {
                    MiJuego.setLogro2(true);
                }
            }
            MiJuego.guardarLogros();
        }
        score += GameHud.getScore();

        Label scoreLabel = new Label(MiJuego.langManager.getText("puntuacion") + ": " + score, font);

        table.add(endGameLabel).expandX().padBottom(20);
        table.row();
        table.add(scoreLabel).expandX().padBottom(30);

        stage.addActor(table);

        if (score > MiJuego.maxScore) {
            MiJuego.guardarEstadisticas(MiJuego.saltos, MiJuego.muertes, MiJuego.eliminaciones, score);
            MiJuego.maxScore = score;
        }
        if (score > 2000) {
            if (!MiJuego.isLogro3()) {
                MiJuego.setLogro3(true);
                MiJuego.guardarLogros();
            }
        }
    }

    @Override
    public void show() {

    }
    /**
     * Maneja la entrada del usuario.
     * @param dt El tiempo delta desde el último fotograma
     */
    public void handleInput(float dt) {
        if (hud.isRetryPressed()) {
            game.setScreen(new PlayScreen((MiJuego) game, nivel));
        } else if (hud.isBackPressed()) {
            game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
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
        stage.dispose();
        backgroundTexture.dispose();
        batch.dispose();
    }
}
