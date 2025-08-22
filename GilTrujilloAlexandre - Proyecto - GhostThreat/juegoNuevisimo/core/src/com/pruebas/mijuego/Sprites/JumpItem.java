package com.pruebas.mijuego.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.pruebas.mijuego.MiJuego;
import com.pruebas.mijuego.Screens.PlayScreen;

public class JumpItem {
    /**
     * Pantalla de juego
     */
    private PlayScreen screen;
    /**
     * Mundo del juego
     */
    private World world;
    /**
     * textura del item
     */
    private Texture jumpTexture;
    /**
     * Limites de la hitbox
     */
    private Rectangle bounds;
    /**
     * Cuerpo del item
     */
    private Body b2body;
    /**
     * Sonido del item al usarlo
     */
    private Sound itemSound;
    /**
     * ancho de los bounds
     */
    private final float width = 16 / MiJuego.PPM;
    /**
     * alto de los bounds
     */
    private final float height = 16 / MiJuego.PPM;
    /**
     * Constructor del Item del jump boost
     * @param screen pantalla del juego
     * @param x posicion en el eje x
     * @param y posicion en el eje y
     */
    public JumpItem(PlayScreen screen, float x, float y) {
        this.screen = screen;
        this.world = screen.getWorld();
        this.jumpTexture = new Texture("textures/jumpBoost.png");
        this.bounds = new Rectangle(x, y, width, height);
        defineItem();
        itemSound = MiJuego.manager.get("audio/sounds/item.mp3", Sound.class);
    }
    /**
     * Define el cuerpo y hitbox del item
     */
    private void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(bounds.getX() + bounds.getWidth() / 2, bounds.getY() + bounds.getHeight() / 2);
        b2body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(bounds.getWidth() / 2, bounds.getHeight() / 2);

        FixtureDef fdef = new FixtureDef();
        fdef.filter.categoryBits = MiJuego.JUMP_ITEM_BIT;
        fdef.filter.maskBits = MiJuego.GROUND_BIT |
                MiJuego.PLAYER_BIT;
        fdef.shape = shape;
        fdef.isSensor = true;

        b2body.createFixture(fdef);
        shape.dispose();
    }
    /**
     * dibuja el item en un batch
     * @param batch SpriteBatch donde dibujar el item
     */
    public void render(SpriteBatch batch) {
        batch.draw(jumpTexture, bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());

    }
    /**
     * Usa el item y lo elimina
     */
    public void use() {
        MiJuego.playSound(itemSound);
        world.destroyBody(b2body);
    }
    /**
     * Libera memoria
     */
    public void dispose() {
        jumpTexture.dispose();
    }
}

