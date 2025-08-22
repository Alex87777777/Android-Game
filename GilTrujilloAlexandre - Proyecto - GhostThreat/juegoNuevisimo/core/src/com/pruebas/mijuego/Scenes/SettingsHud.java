package com.pruebas.mijuego.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pruebas.mijuego.MiJuego;
import com.pruebas.mijuego.Tools.LanguageManager;

public class SettingsHud {
    /**
     * Indica si el botón de atrás esta pulsado
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
     * Imagen del switch de sonido
     */
    Image switchSonido;
    /**
     * Imagen del switch de musica
     */
    Image switchMusica;
    /**
     * Imagen del switch de vibracion
     */
    Image switchVibracion;
    /**
     * Imagen del switch de idioma
     */
    Image switchIdioma;
    /**
     * Textura del switch encendido
     */
    Texture switchOnTexture;
    /**
     * Textura del switch apagado
     */
    Texture switchOffTexture;
    /**
     * Textura del idioma en español
     */
    Texture esTexture;
    /**
     * Textura del idioma en inglés
     */
    Texture enTexture;

    /**
     * Constructor del HUD de Settings
     * @param batch SpriteBatch principal
     */
    public SettingsHud(SpriteBatch batch) {
        viewport = new FitViewport(MiJuego.v_width, MiJuego.v_height);
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        MiJuego.cargarAjustes();

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

        Label settingsLabel = new Label(MiJuego.langManager.getText("ajustes"), font);

        topTable.top().left();
        topTable.setFillParent(true);
        topTable.add(backImg).size(backImg.getWidth(), backImg.getHeight());
        topTable.add(settingsLabel).padLeft(MiJuego.v_width / 3);


        switchOnTexture = new Texture("textures/switch_on.png");
        switchOffTexture = new Texture("textures/switch_off.png");
        esTexture = new Texture("textures/es.png");
        enTexture = new Texture("textures/en.png");

        switchSonido = MiJuego.isSonido() ? new Image(switchOnTexture) : new Image(switchOffTexture);
        switchSonido.setSize(25, 25);
        switchSonido.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MiJuego.setSonido(!MiJuego.isSonido());
                switchSonido.setDrawable(new TextureRegionDrawable(new TextureRegion(MiJuego.isSonido() ? switchOnTexture : switchOffTexture)));
                return true;
            }
        });

        switchMusica = MiJuego.isMusica() ? new Image(switchOnTexture) : new Image(switchOffTexture);
        switchMusica.setSize(25, 25);
        switchMusica.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MiJuego.setMusica(!MiJuego.isMusica());
                if (!MiJuego.menuMusic.isPlaying()) {
                    MiJuego.playMusica(MiJuego.menuMusic);
                } else if (!MiJuego.isMusica() && MiJuego.menuMusic.isPlaying()) {
                    MiJuego.menuMusic.stop();
                }
                switchMusica.setDrawable(new TextureRegionDrawable(new TextureRegion(MiJuego.isMusica() ? switchOnTexture : switchOffTexture)));
                return true;
            }
        });

        switchVibracion = MiJuego.isVibration() ? new Image(switchOnTexture) : new Image(switchOffTexture);
        switchVibracion.setSize(25, 25);
        switchVibracion.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MiJuego.setVibration(!MiJuego.isVibration());
                switchVibracion.setDrawable(new TextureRegionDrawable(new TextureRegion(MiJuego.isVibration() ? switchOnTexture : switchOffTexture)));
                return true;
            }
        });

        switchIdioma = MiJuego.prefs.getString("lang").equals("es") ? new Image(esTexture) : new Image(enTexture);
        switchIdioma.setSize(25, 25);
        switchIdioma.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                String newLang = MiJuego.prefs.getString("lang").equals("es") ? "en" : "es";
                LanguageManager.getInstance().changeLanguage(newLang);
                MiJuego.prefs.putString("lang", newLang);
                MiJuego.prefs.flush();
                switchIdioma.setDrawable(new TextureRegionDrawable(new TextureRegion(MiJuego.prefs.getString("lang").equals("es") ? esTexture : enTexture)));
                return true;
            }
        });

        Label sonidoLabel = new Label(MiJuego.langManager.getText("sonido"), font);
        Label musicaLabel = new Label(MiJuego.langManager.getText("musica"), font);
        Label vibrationLabel = new Label(MiJuego.langManager.getText("vibracion"), font);
        Label idiomaLabel = new Label(MiJuego.langManager.getText("idioma"), font);

        centerTable.center();
        centerTable.setFillParent(true);
        centerTable.add(sonidoLabel);
        centerTable.add(switchSonido).size(switchSonido.getWidth(), switchSonido.getHeight());
        centerTable.row().pad(5, 5, 5, 5);
        centerTable.add(musicaLabel);
        centerTable.add(switchMusica).size(switchMusica.getWidth(), switchMusica.getHeight());
        centerTable.row().pad(5, 5, 5, 5);
        centerTable.add(vibrationLabel);
        centerTable.add(switchVibracion).size(switchVibracion.getWidth(), switchVibracion.getHeight());
        centerTable.row().pad(5, 5, 5, 5);
        centerTable.add(idiomaLabel);
        centerTable.add(switchIdioma).size(switchIdioma.getWidth(), switchIdioma.getHeight());

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
        switchOnTexture.dispose();
        switchOffTexture.dispose();
    }
}
