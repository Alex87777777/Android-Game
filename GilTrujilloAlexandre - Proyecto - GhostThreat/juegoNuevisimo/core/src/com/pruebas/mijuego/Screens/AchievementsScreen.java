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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pruebas.mijuego.MiJuego;
import com.pruebas.mijuego.Scenes.CreditsHud;

public class AchievementsScreen implements Screen {
    /**
     * Indica si se pulsa el botón de atrás
     */
    private boolean backPressed;
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
     * SpriteBatch
     */
    private SpriteBatch batch;
    /**
     * Textura del fondo
     */
    private Texture backgroundTexture;
    /**
     * Textura del tablero
     */
    private Texture boardTexture;
    /**
     * Textura del checkbox activado
     */
    private Texture checkBoxOnTexture;
    /**
     * Textura del checkbox desactivado
     */
    private Texture checkBoxOffTexture;
    /**
     * Imagen del logro 1
     */
    Image logro1;
    /**
     * Imagen del logro 2
     */
    Image logro2;
    /**
     * Imagen del logro 3
     */
    Image logro3;

    /**
     * Constructor de la pantalla de Logros
     * @param game Instancia del juego
     */
    public AchievementsScreen(Game game) {
        this.game = game;
        viewport = new FitViewport(MiJuego.v_width, MiJuego.v_height, new OrthographicCamera());
        stage = new Stage(viewport, ((MiJuego) game).batch);
        Gdx.input.setInputProcessor(stage);
        backgroundTexture = new Texture(Gdx.files.internal("textures/fondo4.jpg"));
        boardTexture = new Texture(Gdx.files.internal("textures/frame.png"));
        batch = new SpriteBatch();

        MiJuego.cargarLogros();

        Label.LabelStyle fuente1 = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        Label.LabelStyle fuente2 = crearFuente(14);

        Table topTable = new Table();
        stage.addActor(topTable);
        topTable.setFillParent(true);
        Table mainTable = new Table();
        stage.addActor(mainTable);
        mainTable.setFillParent(true);

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

        Label mainLabel = new Label(MiJuego.langManager.getText("logros"), fuente1);
        topTable.top().left();
        topTable.add(backImg).size(backImg.getWidth(), backImg.getHeight());
        topTable.add(mainLabel).padLeft((MiJuego.v_width / 3));

        checkBoxOnTexture = new Texture("checkBoxOn.png");
        checkBoxOffTexture = new Texture("checkBoxOff.png");

        logro1 = MiJuego.isLogro1() ? new Image( checkBoxOnTexture) : new Image( checkBoxOffTexture);
        logro1.setSize(25, 25);

        logro2 = MiJuego.isLogro2() ? new Image( checkBoxOnTexture) : new Image( checkBoxOffTexture);
        logro2.setSize(25, 25);

        logro3 = MiJuego.isLogro3() ? new Image( checkBoxOnTexture) : new Image( checkBoxOffTexture);
        logro3.setSize(25, 25);

        Label label1 = new Label(MiJuego.langManager.getText("logro1"), fuente2);
        Label label2 = new Label(MiJuego.langManager.getText("logro2"), fuente2);
        Label label3 = new Label(MiJuego.langManager.getText("logro3"), fuente2);
        Label label4 = new Label(MiJuego.langManager.getText("logro3.5"), fuente2);

        mainTable.center();
        mainTable.add(label1).padRight(10);
        mainTable.add(logro1).size(logro1.getWidth(), logro1.getHeight());
        mainTable.row();
        mainTable.add(label2).padRight(10);
        mainTable.add(logro2).size(logro2.getWidth(), logro2.getHeight());
        mainTable.row();
        mainTable.add(label3).padRight(10);
        mainTable.row();
        mainTable.add(label4).padRight(10);
        mainTable.add(logro3).size(logro3.getWidth(), logro3.getHeight());
        mainTable.row();
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
        if (backPressed) {
            game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(63 / 255f, 63 / 255f, 63 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(boardTexture, stage.getWidth() / 2, stage.getHeight() / 2, Gdx.graphics.getWidth() * 0.8f, Gdx.graphics.getHeight() * 0.8f);
        batch.end();
        stage.draw();
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
