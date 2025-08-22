package com.pruebas.mijuego.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.pruebas.mijuego.MiJuego;
import com.pruebas.mijuego.Screens.PlayScreen;

public class Player extends Sprite {
    /**
     * Diferentes estados en los que puede entrar el jugador
     */
    public enum State {FALLING, JUMPING, STANDING, RUNNING, ATTACKING, DAMAGED, DEAD}

    ;
    /**
     * Estado actual del jugador
     */
    public State currentState;
    /**
     * Estado anterior del jugador
     */
    public State previousState;
    /**
     * Mundo del juego
     */
    public World world;
    /**
     * Cuerpo del jugador
     */
    public Body b2body;
    /**
     * Region de la textura
     */
    private TextureRegion playerInit;
    /**
     * Animacion del jugador cuando está quieto
     */
    private Animation<TextureRegion> playerStand;
    /**
     * Animacion del jugador cuando está corriendo
     */
    private Animation<TextureRegion> playerRun;
    /**
     * Animacion del jugador cuando está saltando
     */
    private Animation<TextureRegion> playerJump;
    /**
     * Animacion del jugador cuando está atacando
     */
    private Animation<TextureRegion> playerAttack;
    /**
     * Animacion del jugador cuando está muriendose
     */
    private Animation<TextureRegion> playerDeath;
    /**
     * Definicion de la fixture
     */
    FixtureDef fdef;
    /**
     * tiempo del estado actual
     */
    private float stateTimer;
    /**
     * temporizador de invulnerabilidad
     */
    public float invulnerableTimer;
    /**
     * tiempo que dura la invulnerabilidad
     */
    public float invulnerableTime;
    /**
     * vidas del jugador
     */
    public int vidas;
    /**
     * indica si el jugador está corriendo
     */
    public boolean running;
    /**
     * indica si el ataque del jugador acabó
     */
    public boolean attackFinished;
    /**
     * indica si el jugador está corriendo hacia la derecha
     */
    public boolean runningRight;
    /**
     * indica si el jugador está atacando
     */
    public boolean attacking;
    /**
     * indica si el jugador está mirando a la derecha
     */
    public boolean lookingRight;
    /**
     * indica si el jugador ha sido dañado
     */
    public boolean damaged;
    /**
     * indica si el jugador está colisionando con un enemigo
     */
    public boolean collisionWithEnemy;
    /**
     * indica si el jugador está muerto
     */
    public boolean isDead;
    /**
     * indica si el jugador es invulnerable
     */
    public boolean invulnerable;
    /**
     * indica si el jugador está colisionando con una escalera
     */
    public boolean collisionWithLadder;
    /**
     * indica si el jugador puede saltar
     */
    public boolean canJump;
    /**
     * indica si el jugador está colisionando con el suelo
     */
    public boolean collisionWithGround;
    /**
     * indica si el jugador está colisionando con un item de corazón
     */
    public boolean collisionWithHeart;
    /**
     * indica si el jugador está colisionando con un itemd e jump boost
     */
    public boolean collisionWithJumpBoost;
    /**
     * indica si el jugador ha completado el nivel
     */
    public boolean levelCompleted;
    /**
     * indica si el jugador tiene el efecto de jump boost
     */
    public boolean jumpBoost;
    /**
     * Definicion del cuerpo del jugador
     */
    public BodyDef bdef;
    /**
     * Sonido de muerte del jugador
     */
    private Sound gameOverSound;
    /**
     * Sonido de golpe a jugador
     */
    private Sound hitSound;
    /**
     * Sonido de nivel completado
     */
    private Sound winSound;

    /**
     * Constructor del jugador
     * @param screen pantalla de juego
     * @param atlas atlas de los sprites del jugador
     */
    public Player(PlayScreen screen, TextureAtlas atlas) {
        super(atlas.findRegion("samurai2"));
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        attackFinished = false;
        running = false;
        lookingRight = true;
        vidas = 3;
        invulnerable = false;
        invulnerableTime = 1.5f;
        invulnerableTimer = invulnerableTime;
        collisionWithEnemy = false;
        collisionWithLadder = false;
        levelCompleted = false;
        jumpBoost = false;
        gameOverSound = MiJuego.manager.get("audio/sounds/game_over.mp3", Sound.class);
        hitSound = MiJuego.manager.get("audio/sounds/hit1.mp3", Sound.class);
        winSound = MiJuego.manager.get("audio/sounds/win.mp3", Sound.class);

        Array<TextureRegion> frames = new com.badlogic.gdx.utils.Array<>();
        for (int i = 0; i < 8; i++) {
            frames.add(new TextureRegion(getTexture(), (i * 128) + 10, 300, 80, 120));
        }
        playerRun = new Animation<>(0.1f, frames);

        frames.clear();
        for (int i = 4; i < 11; i++) {
            frames.add(new TextureRegion(getTexture(), (i * 128) + 25, 420, 80, 120));
        }
        playerJump = new Animation<>(0.1f, frames);

        frames.clear();
        for (int i = 1; i < 6; i++) {
            frames.add(new TextureRegion(getTexture(), (i * 128) + 792, 172, 80, 120));
        }
        playerStand = new Animation<>(0.1f, frames);

        frames.clear();
        for (int i = 10; i < 14; i++) {
            frames.add(new TextureRegion(getTexture(), (i * 128) + 24, 44, 80, 120));
        }
        playerAttack = new Animation<>(0.11f, frames);

        frames.clear();
        for (int i = 7; i < 10; i++) {
            frames.add(new TextureRegion(getTexture(), (i * 128) + 24, 44, 80, 120));
        }
        playerDeath = new Animation<>(0.25f, frames);

        definePlayer();

        playerInit = new TextureRegion(getTexture(), 792, 172, 80, 120);
        setBounds(0, -2, 30 / MiJuego.PPM, 40 / MiJuego.PPM);
        setRegion(playerInit);
    }

    public float getStateTimer() {
        return stateTimer;
    }

    /**
     * Actualiza la posicion y region del jugador
     * @param dt tiempo delta desde el ultimo update
     */
    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    /**
     * Devuelve el frame correspondiente dependiendo del estado del jugador
     * @param dt tiempo delta desde la ultima llamada
     * @return frame en base al stateTimer
     */
    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        Color color = getColor();

        if (currentState == State.DAMAGED || invulnerableTimer < 1.5) {
            invulnerableTimer -= dt;
            if (invulnerableTimer % 0.1 < 0.05) {
                if (color.a == 0.25f) {
                    color.a = 1;
                } else {
                    color.a = 0.25f;
                }
            }
            setColor(color);
        }
        switch (currentState) {
            case DEAD:
                region = playerDeath.getKeyFrame(stateTimer);
                break;
            case JUMPING:
                region = playerJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = playerRun.getKeyFrame(stateTimer, true);
                break;
            case ATTACKING:
                region = playerAttack.getKeyFrame(stateTimer, false);
                EdgeShape attack = new EdgeShape();
                if (lookingRight) {
                    attack.set(new Vector2(6 / MiJuego.PPM, 8 / MiJuego.PPM), new Vector2(21 / MiJuego.PPM, 8 / MiJuego.PPM));
                } else {
                    attack.set(new Vector2(-6 / MiJuego.PPM, 8 / MiJuego.PPM), new Vector2(-21 / MiJuego.PPM, 8 / MiJuego.PPM));
                }
                if (playerAttack.isAnimationFinished(stateTimer)) {
                    stateTimer = 0;
                    attacking = false;
                }
                break;
            case FALLING:
            case STANDING:
            default:
                region = playerStand.getKeyFrame(stateTimer, true);
                break;
        }
        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
            lookingRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
            lookingRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;

        if (invulnerable && invulnerableTimer <= 0) {
            invulnerable = false;
            invulnerableTimer = invulnerableTime;
            Color originalColor = getColor();
            originalColor.a = 1.0f;
            setColor(originalColor);
        } else if (!invulnerable && collisionWithEnemy && !attacking) {
            Gdx.app.log("NO", "NO INVULNERABLE");
            hit();
        }

        previousState = currentState;
        return region;
    }

    /**
     * Devuelve el estado actual del jugador
     * @return estado correspondiente
     */
    public State getState() {
        if (isDead) {
            return State.DEAD;
        }
        if (attacking) {
            return State.ATTACKING;
        }
        if (damaged) {
            damaged = false;
            return State.DAMAGED;
        }
        if (b2body.getLinearVelocity().y > 0 || b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING) {
            return State.JUMPING;
        } else if (b2body.getLinearVelocity().y < 0) {
            return State.FALLING;
        } else if (b2body.getLinearVelocity().x != 0) {
            return State.RUNNING;
        } else {
            return State.STANDING;
        }

    }

    /**
     * Golpea al jugador, eliminando el efecto jump boost si lo tiene y quitandole una vida
     */
    public void hit() {
        if (jumpBoost) {
            jumpBoost = false;
        }
        MiJuego.vibrate(300);
        if (vidas > 1) {
            vidas--;
            damaged = true;
            invulnerable = true;
            Gdx.app.log("vidas: ", vidas + "");
            MiJuego.playSound(hitSound);
        } else {
            kill();
        }
    }

    /**
     * Mata al jugador
     */
    public void kill() {
        vidas = 0;
        b2body.setLinearVelocity(0, 0);
        MiJuego.guardarEstadisticas(MiJuego.saltos, MiJuego.muertes + 1, MiJuego.eliminaciones, MiJuego.maxScore);
//        collisionWithEnemy = false;
        isDead = true;
        invulnerable = true;
        MiJuego.playSound(gameOverSound);
        PlayScreen.music.stop();
//        b2body.destroyFixture(b2body.getFixtureList().get(0));
//        b2body.setActive(false);
    }

    /**
     * Completa el nivel actual
     */
    public void completeLevel() {
        MiJuego.playSound(winSound);
        PlayScreen.music.stop();
    }

    /**
     * Define el cuerpo y fixture del jugador y de sus pies de forma separada
     */
    public void definePlayer() {
        bdef = new BodyDef();
        bdef.position.set(32 / MiJuego.PPM, 32 / MiJuego.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-6, 15).scl(1 / MiJuego.PPM);
        vertice[1] = new Vector2(6, 15).scl(1 / MiJuego.PPM);
        vertice[2] = new Vector2(-6, -5).scl(1 / MiJuego.PPM);
        vertice[3] = new Vector2(6, -5).scl(1 / MiJuego.PPM);
        shape.set(vertice);
        fdef.filter.categoryBits = MiJuego.PLAYER_BIT;
        fdef.filter.maskBits = MiJuego.GROUND_BIT |
                MiJuego.ENEMY_BIT |
                MiJuego.LADDER_BIT |
                MiJuego.HEART_ITEM_BIT |
                MiJuego.JUMP_ITEM_BIT
        ;

        fdef.shape = shape;
        b2body.createFixture(fdef);

        EdgeShape bottom = new EdgeShape();
        bottom.set(new Vector2(-5 / MiJuego.PPM, -6 / MiJuego.PPM), new Vector2(5 / MiJuego.PPM, -6 / MiJuego.PPM));
        fdef.filter.categoryBits = MiJuego.PLAYER_BOTTOM_BIT;
        fdef.filter.maskBits = MiJuego.GROUND_BIT | MiJuego.GOAL_BIT;
        fdef.shape = bottom;
        fdef.isSensor = false;

        b2body.createFixture(fdef).setUserData("bottom");

    }
}
