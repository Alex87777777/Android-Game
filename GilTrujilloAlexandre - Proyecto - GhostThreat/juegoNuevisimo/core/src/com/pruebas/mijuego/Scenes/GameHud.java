package com.pruebas.mijuego.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pruebas.mijuego.MiJuego;
import com.pruebas.mijuego.Screens.PlayScreen;
import com.pruebas.mijuego.Sprites.Player;

public class GameHud implements Disposable {
    /**
     * Escenario de renderizado
     */
    public Stage stage;
    /**
     * Viewport de la pantalla
     */
    private Viewport viewport;
    /**
     * Tiempo restante de la partida
     */
    private static Integer worldTimer;
    /**
     * Tiempo que se actualiza cada fotograma
     */
    private float timeCount;
    /**
     * Puntuación total del jugador
     */
    private static Integer score;
    /**
     * Etiqueta donde se muestra el tiempo restante
     */
    Label countdownLabel;
    /**
     * Etiqueta donde se muestra la puntuacion del jugador
     */
    static Label scoreLabel;
    /**
     * Etiqueta donde se muestra tiempo
     */
    Label timeLabel;
    /**
     * Etiqueta donde se muestra el numero de vidas
     */
    Label numVidasLabel;
    /**
     * Etiqueta donde se muestra vidas
     */
    Label vidasLabel;
    /**
     * Etiqueta donde se muestra puntuacion
     */
    Label puntuacionLabel;

    /**
     * Constructor del HUD de la partida
     * @param sb SpriteBatch principal
     */
    public GameHud(SpriteBatch sb) {
        worldTimer = 300;
        timeCount = 0;
        score = 0;

        viewport = new FitViewport(MiJuego.v_width, MiJuego.v_height, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label(MiJuego.langManager.getText("tiempo"), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        numVidasLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        vidasLabel = new Label(MiJuego.langManager.getText("vidas"), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        puntuacionLabel = new Label(MiJuego.langManager.getText("puntuacion"), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(puntuacionLabel).expandX().padTop(10);
        table.add(vidasLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(numVidasLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);
    }

    /**
     * Se actualiza el tiempo en las labels
     * @param dt Tiempo delta desde el ultimo render
     */
    public void update(float dt) {
        timeCount += dt;
        numVidasLabel.setText(PlayScreen.player.vidas);
        if (timeCount >= 1 && worldTimer >= 1) {
            worldTimer--;
            if (PlayScreen.player.currentState != Player.State.DEAD) {
                countdownLabel.setText(String.format("%03d", worldTimer));
            }
            timeCount = 0;
        }
    }

    /**
     * suma puntuacion a la puntuacion total
     * @param value puntuacion a sumar
     */
    public static void addScore(int value) {
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    public static int getScore() {
        return score;
    }

    public static Integer getWorldTimer() {
        return worldTimer;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
