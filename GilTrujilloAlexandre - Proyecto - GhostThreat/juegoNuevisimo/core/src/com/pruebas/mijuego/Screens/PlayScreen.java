package com.pruebas.mijuego.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pruebas.mijuego.Scenes.Controller;
import com.pruebas.mijuego.Scenes.GameHud;
import com.pruebas.mijuego.Scenes.PauseHud;
import com.pruebas.mijuego.Sprites.Enemy;
import com.pruebas.mijuego.Sprites.HeartItem;
import com.pruebas.mijuego.Sprites.JumpItem;
import com.pruebas.mijuego.Sprites.Player;
import com.pruebas.mijuego.Tools.B2WorldCreator;
import com.pruebas.mijuego.Tools.WorldContactListener;
import com.pruebas.mijuego.MiJuego;

public class PlayScreen implements Screen {
    /**
     * Instancia del juego
     */
    private MiJuego game;
    /**
     * Atlas de texturas
     */
    private TextureAtlas atlas2;
    /**
     * textura del fondo
     */
    private Texture backgroundTexture;
    /**
     * textura del tablero
     */
    private Texture boardTexture;
    /**
     * Camara del juego
     */
    private OrthographicCamera gamecam;
    /**
     * Viewport de la pantalla
     */
    private Viewport gamePort;
    /**
     * HUD de la partida
     */
    private GameHud hud;
    /**
     * Cargador de mapa
     */
    private TmxMapLoader maploader;
    /**
     * Mapa del nivel
     */
    private TiledMap map;
    /**
     * Renderizador del mapa
     */
    private OrthogonalTiledMapRenderer renderer;
    /**
     * Mundo del nivel
     */
    private World world;
    /**
     * Box2DDebugRenderer
     */
    private Box2DDebugRenderer b2dr;
    /**
     * Creador del mundo del nivel
     */
    private B2WorldCreator creator;
    /**
     * Jugador
     */
    public static Player player;
    /**
     * Mando para manejar al jugador
     */
    private Controller controller;
    /**
     * HUD de la pantalla de pausa
     */
    private PauseHud pauseHud;
    /**
     * musica del nivel
     */
    public static Music music;
    /**
     * Indica si el juego esta pausado
     */
    private boolean isPaused;
    /**
     * SpriteBatch del fondo
     */
    private SpriteBatch backgroundBatch;
    /**
     * SpriteBatch de pausa
     */
    private SpriteBatch pauseBatch;
    /**
     * velocidad de la camara hacia arriba
     */
    private float camSpeedUp = 0.03f;
    /**
     * velocidad de la camara hacia abajo
     */
    private float camSpeedDown = 0.05f;
    /**
     * Limite de la camara
     */
    private float camLimit = 0.6f;
    /**
     * Agitación del dispositivo requerida
     */
    private float minShake = 20f;
    /**
     * Impulso del jugador al saltar
     */
    private float jumpImpulse = 4f;
    /**
     * numero del nivel
     */
    private int nivel;

    /**
     * Constructor de la pantalla de juego donde se inicializa un
     * mapa, musica y fondo diferentes dependiendo del numero del nivel
     * @param game instancia del juego
     * @param numNivel numero del nivel
     */
    public PlayScreen(MiJuego game, int numNivel) {
        atlas2 = new TextureAtlas("textures/samurai2.txt");
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(MiJuego.v_width / MiJuego.PPM, MiJuego.v_height / MiJuego.PPM, gamecam);
        hud = new GameHud(game.batch);
        boardTexture = new Texture(Gdx.files.internal("textures/frame2.png"));

        nivel = numNivel;

        maploader = new TmxMapLoader();
        if (nivel == 1) {
            map = maploader.load("level1_map.tmx");
            music = MiJuego.manager.get("audio/music/level1_music.mp3", Music.class);
            backgroundTexture = new Texture(Gdx.files.internal("textures/fondo2.png"));
        } else if (nivel == 2) {
            map = maploader.load("level2_map.tmx");
            music = MiJuego.manager.get("audio/music/level2_music.mp3", Music.class);
            backgroundTexture = new Texture(Gdx.files.internal("textures/fondo3.png"));
        }

        backgroundBatch = new SpriteBatch();
        pauseBatch = new SpriteBatch();

        renderer = new OrthogonalTiledMapRenderer(map, 1 / MiJuego.PPM);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(this);

        player = new Player(this, atlas2);

        world.setContactListener(new WorldContactListener());

        pauseHud = new PauseHud(game.batch);
        controller = new Controller(game.batch);

        isPaused = false;

        music.setVolume(0.3f);
        music.setLooping(true);
        MiJuego.playMusica(music);
    }

    @Override
    public void show() {

    }
    /**
     * Maneja la entrada del usuario.
     * @param dt El tiempo delta desde el último fotograma
     */
    public void handleInput(float dt) {
        if (player.currentState != Player.State.DEAD) {
            if (isPaused) {
                Gdx.input.setInputProcessor(pauseHud.stage);
            } else {
                Gdx.input.setInputProcessor(controller.stage);
            }
            if (controller.isPausePressed()) {
                isPaused = true;
                music.pause();
                controller.setPausePressed(false);
                return;
            }
            if (!isPaused) {
                if (controller.isRightPressed()) {
                    player.b2body.setLinearVelocity(new Vector2(1.5f, player.b2body.getLinearVelocity().y));
                    if (controller.isAttPressed() && !player.running) {
                        player.attackFinished = false;
                        player.running = true;
                        player.attacking = false;
                    } else {
                        player.attackFinished = true;
                    }
                } else if (controller.isLeftPressed()) {
                    player.b2body.setLinearVelocity(new Vector2(-1.5f, player.b2body.getLinearVelocity().y));
                    if (controller.isAttPressed() && !player.running) {
                        player.attackFinished = false;
                        player.running = true;
                        player.attacking = false;
                    } else {
                        player.attackFinished = true;
                    }
                } else {
                    player.b2body.setLinearVelocity(new Vector2(0, player.b2body.getLinearVelocity().y));
                    player.running = false;
                }
                if (controller.isUpPressed() && player.b2body.getLinearVelocity().y == 0 && player.canJump) {
                    if (player.collisionWithGround || player.collisionWithLadder) {
                        if (player.jumpBoost) {
                            player.b2body.applyLinearImpulse(new Vector2(0, jumpImpulse * 1.5f), player.b2body.getWorldCenter(), true);
                        } else {
                            player.b2body.applyLinearImpulse(new Vector2(0, jumpImpulse), player.b2body.getWorldCenter(), true);
                        }
                        MiJuego.guardarEstadisticas(MiJuego.saltos++, MiJuego.muertes, MiJuego.eliminaciones, MiJuego.maxScore);
                    }
                }
                if (controller.isAttPressed() && !player.attackFinished) {
                    player.attacking = true;
                }
                if (!controller.isAttPressed() && !controller.isUpPressed() && !controller.isLeftPressed() && !controller.isRightPressed()) {
                    player.attackFinished = false;
                }
            } else {
                if (pauseHud.isResumePressed()) {
                    pauseHud.setResumePressed(false);
                    isPaused = false;
                    MiJuego.playMusica(music);
                }
                if (pauseHud.isMenuPressed()) {
                    music.stop();
                    game.setScreen(new MainMenuScreen(game));
                }
                if (pauseHud.isResetPressed()) {
                    music.stop();
                    game.setScreen(new PlayScreen(game, nivel));
                }
            }
        }
    }

    /**
     * Actualiza al jugador, enemigos, camara, etc
     * @param dt tempo delta desde el ultimo update
     */
    public void update(float dt) {
        if (player.collisionWithGround || player.collisionWithLadder) {
            player.canJump = true;
        }
        handleInput(dt);
        if (!isPaused) {
            world.step(1 / 60f, 6, 2);
            hud.update(dt);
            if (hud.getWorldTimer() <= 0 && !player.isDead || player.b2body.getPosition().y < 0 && !player.isDead) {
                player.kill();
            }
            player.update(dt);
            if (player.collisionWithHeart) {
                checkAcceleration(true);
            }
            if (player.collisionWithJumpBoost) {
                checkAcceleration(false);
            }
            for (Enemy enemy : creator.getEnemies()) {
                enemy.update(dt);
                if (enemy.getX() < player.getX() + 225 / MiJuego.PPM) {
                    if (!player.isDead) {
                        enemy.b2body.setActive(true);
                    } else {
                        enemy.b2body.setActive(false);
                    }
                }
            }

            gamecam.position.x = player.b2body.getPosition().x;

            if (player.b2body.getPosition().y > gamecam.position.y + camLimit) {

                gamecam.position.y = MathUtils.lerp(gamecam.position.y, player.b2body.getPosition().y, camSpeedUp);
            } else if (player.b2body.getPosition().y < gamecam.position.y - camLimit) {
                float newY = Math.max(player.b2body.getPosition().y, gamePort.getWorldHeight() * 0.33f);
                gamecam.position.y = MathUtils.lerp(gamecam.position.y, newY, camSpeedDown);
            }

            gamecam.update();
            renderer.setView(gamecam);
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        backgroundBatch.setProjectionMatrix(gamecam.combined);
        backgroundBatch.begin();
        float backgroundX = gamecam.position.x - gamePort.getWorldWidth() / 2;
        float backgroundY = gamecam.position.y - gamePort.getWorldHeight() / 2;
        backgroundBatch.draw(backgroundTexture, backgroundX, backgroundY, gamePort.getWorldWidth(), gamePort.getWorldHeight());
        backgroundBatch.end();

        renderer.render();

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        for (Enemy enemy : creator.getEnemies()) {
            enemy.draw(game.batch);
        }

        for (HeartItem heart : creator.getHearts()) {
            heart.render(game.batch);
        }

        for (JumpItem jump : creator.getJumps()) {
            jump.render(game.batch);
        }
        game.batch.end();
        controller.draw();
        hud.stage.draw();

        if (isPaused) {
            float boardWidth = gamePort.getWorldWidth() * 0.7f;
            float boardHeight = gamePort.getWorldHeight() * 0.7f;

            float boardX = gamecam.position.x - boardWidth / 2;
            float boardY = gamecam.position.y - boardHeight / 2;

            pauseBatch.setProjectionMatrix(gamecam.combined);
            pauseBatch.begin();
            pauseBatch.draw(boardTexture, boardX, boardY, boardWidth, boardHeight);
            pauseBatch.end();
            pauseHud.draw();
        }

        if (gameOver()) {
            game.setScreen(new EndGameScreen(game, nivel, false));
            dispose();
        } else if (player.levelCompleted) {
            game.setScreen(new EndGameScreen(game, nivel, true));
            dispose();
        }
    }

    /**
     * Comprueba si el jugador ha muerto y si ha acabado la animacion de muerte
     * @return si el jugador ha perdido o no
     */
    public boolean gameOver() {
        if (player.currentState == Player.State.DEAD && player.getStateTimer() > 3) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Comprueba si el dispositivo esta siendo agitado lo suficiente para activar el item
     * @param flag indica si es un heart(true) o un jump boost (false)
     */
    private void checkAcceleration(boolean flag) {
        float acceleration = Math.abs(Gdx.input.getAccelerometerX() + Gdx.input.getAccelerometerY() + Gdx.input.getAccelerometerZ());
        if (acceleration > minShake) {
            if (flag) {
                removeHeartItem();
                player.vidas++;
                player.collisionWithHeart = false;
            } else {
                removeJumpItem();
                player.jumpBoost = true;
                player.collisionWithJumpBoost = false;
            }
        }
    }

    /**
     * Borra el corazón de la lista de corazones
     */
    private void removeHeartItem() {
        for (HeartItem heart : creator.getHearts()) {
            creator.getHearts().removeValue(heart, true);
            heart.use();
            heart.dispose();
            break;
        }
    }

    /**
     * Borra el item de salto de su lista
     */
    private void removeJumpItem() {
        for (JumpItem jump : creator.getJumps()) {
            creator.getJumps().removeValue(jump, true);
            jump.use();
            jump.dispose();
            break;
        }
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
