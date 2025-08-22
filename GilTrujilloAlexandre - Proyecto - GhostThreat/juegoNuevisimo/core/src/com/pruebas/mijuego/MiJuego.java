package com.pruebas.mijuego;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pruebas.mijuego.Screens.MainMenuScreen;
import com.pruebas.mijuego.Tools.LanguageManager;


public class MiJuego extends Game {
    /**
     * Ancho del viewport
     */
    public static final int v_width = 420;
    /**
     * Alto del viewport
     */
    public static final int v_height = 200;
    /**
     * Unidad de medida del juego
     */
    public static final float PPM = 100;
    /**
     * Bit del suelo
     */
    public static final short GROUND_BIT = 1;
    /**
     * Bit del jugador
     */
    public static final short PLAYER_BIT = 2;
    /**
     * Bit del item corazón
     */
    public static final short HEART_ITEM_BIT = 4;
    /**
     * Bit del item de salto
     */
    public static final short JUMP_ITEM_BIT = 8;
    /**
     * Bit de la meta
     */
    public static final short GOAL_BIT = 16;
    /**
     * Bit de las paredes de los enemigos
     */
    public static final short WALL_BIT = 32;
    /**
     * Bit de los enemigos
     */
    public static final short ENEMY_BIT = 64;
    /**
     * Bit de las escaleras
     */
    public static final short LADDER_BIT = 128;
    /**
     * Bit de los pies del jugador
     */
    public static final short PLAYER_BOTTOM_BIT = 256;
    /**
     * SpriteBatch
     */
    public SpriteBatch batch;
    /**
     * manager de audio
     */
    public static AssetManager manager;
    /**
     * manager de idioma
     */
    public static LanguageManager langManager;
    /**
     * indica si el sonido esta activado
     */
    private static boolean sonido;
    /**
     * indica si la musica esta activado
     */
    private static boolean musica;
    /**
     * indica si la vibracion esta activado
     */
    private static boolean vibration;
    /**
     * indica si el logro 1 está conseguido
     */
    private static boolean logro1;
    /**
     * indica si el logro 2 está conseguido
     */
    private static boolean logro2;
    /**
     * indica si el logro 3 está conseguido
     */
    private static boolean logro3;
    /**
     * Guarda los ajustes, estadisticas y logros del juego
     */
    public static Preferences prefs;
    /**
     * Numero de saltos
     */
    public static int saltos;
    /**
     * Numero de muertes
     */
    public static int muertes;
    /**
     * Numero de eliminaciones
     */
    public static int eliminaciones;
    /**
     * Puntuacion mas alta
     */
    public static int maxScore;
    /**
     * Musica del menu
     */
    public static Music menuMusic;

    @Override
    public void create() {
        batch = new SpriteBatch();
        prefs = Gdx.app.getPreferences("Preferences");
        guardarEstadisticas(0, 0, 0, 0);
        cargarEstadisticas();
        cargarAjustes();
        cargarLogros();
        langManager = LanguageManager.getInstance();
        manager = new AssetManager();
        manager.load("audio/music/level1_music.mp3", Music.class);
        manager.load("audio/music/level2_music.mp3", Music.class);
        manager.load("audio/music/menu_music.mp3", Music.class);
        manager.load("audio/sounds/hit1.mp3", Sound.class);
        manager.load("audio/sounds/enemyHit.mp3", Sound.class);
        manager.load("audio/sounds/game_over.mp3", Sound.class);
        manager.load("audio/sounds/win.mp3", Sound.class);
        manager.load("audio/sounds/item.mp3", Sound.class);
        manager.finishLoading();
        menuMusic = manager.get("audio/music/menu_music.mp3", Music.class);
        playMusica(menuMusic);
        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        manager.dispose();
        batch.dispose();
    }

    @Override
    public void render() {
        super.render();
    }

    /**
     * Guarda las estadisticas del jugador
     * @param saltos saltos del jugador
     * @param muertes muertes del jugador
     * @param eliminaciones eliminaciones del jugador
     * @param maxScore maxScore del jugador
     */
    public static void guardarEstadisticas(int saltos, int muertes, int eliminaciones, int maxScore) {
        prefs.putInteger("saltos", saltos);
        prefs.putInteger("muertes", muertes);
        prefs.putInteger("eliminaciones", eliminaciones);
        prefs.putInteger("maxScore", maxScore);
        prefs.flush();
    }

    /**
     * Carga las estadisticas del jugador
     */
    public static void cargarEstadisticas() {
        saltos = prefs.getInteger("saltos", 0);
        muertes = prefs.getInteger("muertes", 0);
        eliminaciones = prefs.getInteger("eliminaciones", 0);
        maxScore = prefs.getInteger("maxScore", 0);
        Gdx.app.log("Estadisticas", "Saltos: " + saltos + ", Muertes: " + muertes + ", Eliminaciones: " + eliminaciones + ", Max Score: " + maxScore);
    }

    /**
     * Guarda los ajustes del jugador
     */
    public static void guardarAjustes() {
        prefs.putBoolean("sonido", isSonido());
        prefs.putBoolean("musica", isMusica());
        prefs.putBoolean("vibration", isVibration());
        prefs.flush();
    }

    /**
     * Carga los ajustes del jugador
     */
    public static void cargarAjustes() {
        setSonido(prefs.getBoolean("sonido", true));
        setMusica(prefs.getBoolean("musica", true));
        setVibration(prefs.getBoolean("vibration", true));
        Gdx.app.log("Ajustes", "Sonido: " + isSonido() + ", Musica: " + isMusica() + ", Vibration: " + isVibration());
    }

    /**
     * guarda los logros del jugador
     */
    public static void guardarLogros() {
        prefs.putBoolean("logro1", isLogro1());
        prefs.putBoolean("logro2", isLogro2());
        prefs.putBoolean("logro3", isLogro3());
        prefs.flush();
    }

    /**
     * Carga los logros del jugador
     */
    public static void cargarLogros() {
        setLogro1(prefs.getBoolean("logro1", true));
        setLogro2(prefs.getBoolean("logro2", true));
        setLogro3(prefs.getBoolean("logro3", true));
        Gdx.app.log("Logros", "Logro 1: " + isLogro1() + ", Logro 2: " + isLogro2() + ", Logro 3: " + isLogro3());
    }

    public static boolean isMusica() {
        return musica;
    }

    public static void setMusica(boolean musica) {
        MiJuego.musica = musica;
    }

    public static boolean isSonido() {
        return sonido;
    }

    public static void setSonido(boolean sonido) {
        MiJuego.sonido = sonido;
    }

    public static boolean isVibration() {
        return vibration;
    }

    public static void setVibration(boolean vibration) {
        MiJuego.vibration = vibration;
    }

    public static boolean isLogro1() {
        return logro1;
    }

    public static void setLogro1(boolean logro1) {
        MiJuego.logro1 = logro1;
    }

    public static boolean isLogro2() {
        return logro2;
    }

    public static void setLogro2(boolean logro2) {
        MiJuego.logro2 = logro2;
    }

    public static boolean isLogro3() {
        return logro3;
    }

    public static void setLogro3(boolean logro3) {
        MiJuego.logro3 = logro3;
    }

    /**
     * Reproduce si está activado el sonido, un sonido
     * @param sound sonido que se quiere reproducir
     */
    public static void playSound(Sound sound) {
        if (isSonido()) {
            sound.play();
        }
    }

    /**
     * Reproduce si está activada la musica, una musica
     * @param music musica que se quiere reproducir
     */
    public static void playMusica(Music music) {
        if (isMusica()) {
            music.play();
        }
    }

    /**
     * Vibra durante x tiempo, si esta activada la vibracion
     * @param time tiempo de vibracion
     */
    public static void vibrate(int time) {
        if (isVibration()) {
            Gdx.input.vibrate(time);
        }
    }
}