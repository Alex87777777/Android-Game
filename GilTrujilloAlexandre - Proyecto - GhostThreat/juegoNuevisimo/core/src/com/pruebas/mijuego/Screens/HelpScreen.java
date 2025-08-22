package com.pruebas.mijuego.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pruebas.mijuego.MiJuego;
import com.pruebas.mijuego.Tools.LanguageManager;

import java.util.Locale;

public class HelpScreen implements Screen {
    /**
     * Indica si el boton OK ha sido pulsado
     */
    private boolean okPressed;
    /**
     * Instancia del juego
     */
    private Game game;
    /**
     * viewport de la pantalla
     */
    private Viewport viewport;
    /**
     * Escenario de renderizado
     */
    private Stage stage;
    /**
     * textura de fondo
     */
    private Texture backgroundTexture;
    /**
     * SpriteBatch
     */
    private SpriteBatch batch;

    /**
     * Constructor de la pantalla de ayuda
     * @param game instancia del juego
     */
    public HelpScreen(Game game) {

        this.game = game;
        viewport = new FitViewport(MiJuego.v_width, MiJuego.v_height, new OrthographicCamera());
        stage = new Stage(viewport, ((MiJuego) game).batch);
        backgroundTexture = new Texture(Gdx.files.internal("textures/frame2.png"));
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        stage.addActor(table);

        Image okImg = new Image(new Texture("textures/boton-ok.png"));
        okImg.setSize(37, 37);
        okImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                okPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                okPressed = false;

            }
        });

        Label.LabelStyle fuente = crearFuente(10);

        Label label1 = new Label(MiJuego.langManager.getText("comojugar"), fuente);
        Label label2 = new Label(MiJuego.langManager.getText("mover") + "\n" + MiJuego.langManager.getText("mover2"), fuente);
        Label label3 = new Label(MiJuego.langManager.getText("atacar") + "\n" + MiJuego.langManager.getText("atacar2"), fuente);
        Label label4 = new Label(MiJuego.langManager.getText("saltar") + "\n" + MiJuego.langManager.getText("saltar2"), fuente);
        Label label5 = new Label(MiJuego.langManager.getText("item") + "\n" + MiJuego.langManager.getText("item2"), fuente);
        Label label6 = new Label(MiJuego.langManager.getText("objetivo") + "\n" + MiJuego.langManager.getText("objetivo2"), fuente);
        table.setFillParent(true);
        table.add(label1).expandX();
        table.row();
        table.add(label2).expandX();
        table.row();
        table.add(label3).expandX();
        table.row();
        table.add(label4);
        table.row();
        table.add(label5).expandX();
        table.row();
        table.add(label6).expandX();
        table.row();
        table.add(okImg).size(okImg.getWidth(), okImg.getHeight());
    }
    /**
     * Crea una fuente a partir de un tamaño
     * @param size tamaño de la fuente
     * @return una nueva fuente
     */
    private Label.LabelStyle crearFuente(int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/fuente.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.color = Color.WHITE;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return new Label.LabelStyle(font, Color.WHITE);
    }

    @Override
    public void show() {

    }
    /**
     * Maneja la entrada del usuario.
     * @param dt El tiempo delta desde el último fotograma
     */
    public void handleInput(float dt) {
        if (okPressed) {
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
        backgroundTexture.dispose();
        stage.dispose();
        batch.dispose();
    }
}
