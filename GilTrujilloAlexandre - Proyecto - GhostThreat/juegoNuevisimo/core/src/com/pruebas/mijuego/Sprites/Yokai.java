package com.pruebas.mijuego.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.pruebas.mijuego.MiJuego;
import com.pruebas.mijuego.Screens.PlayScreen;

public class Yokai extends Enemy {
    /**
     * tiempo del estado actual
     */
    private float stateTime;
    /**
     * Animacion de caminar
     */
    private Animation<TextureRegion> walkAnimation;
    /**
     * Animacion de morir
     */
    private Animation<TextureRegion> deathAnimation;
    /**
     * Conjunto de frames
     */
    private Array<TextureRegion> frames;
    /**
     * Atlas del enemigo
     */
    private TextureAtlas atlas;
    /**
     * indica si el enemigo debe ser destruido
     */
    private boolean setToDestroy;
    /**
     * indica si el enemigo ha sido destruido
     */
    private boolean destroyed;
    /**
     * indica si el enemigo esta muerto
     */
    private boolean dead;
    /**
     * indica si el enemigo esta corriendo hacia la derecha
     */
    public boolean runningRight;
    /**
     * Definicion del cuerpo del enemigo
     */
    public BodyDef bdef;
    /**
     * Definicion del fixture del enemigo
     */
    FixtureDef fdef;
    /**
     * Sonido del enemigo al golpearlo
     */
    private Sound hitEnemy;

    /**
     * Constructor del Yokai
     * @param screen pantalla del juego
     * @param x posicion del eje x
     * @param y posicion del eje y
     */
    public Yokai(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        runningRight = true;
        hitEnemy = MiJuego.manager.get("audio/sounds/enemyHit.mp3", Sound.class);
        atlas = new TextureAtlas("textures/aa.txt");
        TextureRegion region = atlas.findRegion("Run");
        setRegion(region);
        setColor(1, 1, 1, 1);
        setSize(region.getRegionWidth(), region.getRegionHeight());

        frames = new Array<>();
        for (int i = 0; i < 6; i++) {
            frames.add(new TextureRegion(getTexture(), (i * 128) + 778, 296, 80, 120));
        }
        walkAnimation = new Animation(0.15f, frames);

        frames = new Array<>();
        for (int i = 0; i < 6; i++) {
            frames.add(new TextureRegion(getTexture(), (i * 128) + 20, 168, 80, 120));
        }
        deathAnimation = new Animation(0.15f, frames);

        stateTime = 0;
        setBounds(getX(), getY(), 30 / MiJuego.PPM, 40 / MiJuego.PPM);
        setToDestroy = false;
        destroyed = false;
        dead = false;
    }

    /**
     * Actualiza el enemigo, su posicion, region, etc
     * @param dt tiempo delta desde el ultimo update
     */
    public void update(float dt) {
        stateTime += dt;
        if (setToDestroy && !destroyed) {
            setRegion(deathAnimation.getKeyFrame(stateTime, false));

            Filter filter = b2body.getFixtureList().get(0).getFilterData();
            filter.categoryBits = MiJuego.ENEMY_BIT;
            filter.maskBits = MiJuego.GROUND_BIT |
                    MiJuego.WALL_BIT;
            b2body.getFixtureList().get(0).setFilterData(filter);

            if (deathAnimation.isAnimationFinished(stateTime)) {
                world.destroyBody(b2body);
                destroyed = true;
                dead = true;
            }
        } else if (!destroyed) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
            setRegion(getFrame(dt));
        }
    }

    /**
     * Devuelve un frame dependiendo de su direccion
     * @param dt tiempo delta desde la ultima llamada
     * @return frame correspondiente
     */
    public TextureRegion getFrame(float dt) {
        TextureRegion region = walkAnimation.getKeyFrame(stateTime, true);
        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }
        return region;
    }
    /**
     * Define el cuerpo y hitbox del enemigo
     */
    @Override
    protected void defineEnemy() {
        bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-6, 15).scl(1 / MiJuego.PPM);
        vertice[1] = new Vector2(6, 15).scl(1 / MiJuego.PPM);
        vertice[2] = new Vector2(-6, -6).scl(1 / MiJuego.PPM);
        vertice[3] = new Vector2(6, -6).scl(1 / MiJuego.PPM);
        shape.set(vertice);
        fdef.filter.categoryBits = MiJuego.ENEMY_BIT;
        fdef.filter.maskBits = MiJuego.GROUND_BIT |
                MiJuego.ENEMY_BIT |
                MiJuego.WALL_BIT |
                MiJuego.PLAYER_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    /**
     * dibuja al enemigo en el batch correspondiente
     * @param batch Batch
     */
    public void draw(Batch batch) {
        if (!dead) {
            super.draw(batch);
        }
    }
    /**
     * Mata al enemigo y lo elimina
     */
    @Override
    public void hit() {
        if (!setToDestroy) {
            MiJuego.guardarEstadisticas(MiJuego.saltos, MiJuego.muertes, MiJuego.eliminaciones++, MiJuego.maxScore);
        }
        setToDestroy = true;
        stateTime = 0;
        Gdx.app.log("eliminacion", "si");
        MiJuego.playSound(hitEnemy);
    }
}
