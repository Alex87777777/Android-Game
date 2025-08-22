package com.pruebas.mijuego.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.pruebas.mijuego.Screens.PlayScreen;

public abstract class Enemy extends Sprite {
    /**
     * Pantalla de juego
     */
    protected PlayScreen screen;
    /**
     * Mundo del juego
     */
    protected World world;
    /**
     * Cuerpo del enemigo
     */
    public Body b2body;
    /**
     * velocidad del enemigo
     */
    public Vector2 velocity;

    /**
     * Constructor del enemigo
     * @param screen instancia de la pantalla
     * @param x posicion en el eje x
     * @param y posicion en el eje y
     */
    public Enemy(PlayScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(1, 0);
        b2body.setActive(false);
    }

    /**
     * Define el cuerpo y hitbox del enemigo
     */
    protected abstract void defineEnemy();

    /**
     * Actualiza al enemigo
     * @param dt tiempo delta desde el ultimo update
     */
    public abstract void update(float dt);

    /**
     * Mata al enemigo y lo elimina
     */
    public abstract void hit();

    /**
     * Invierte la velocidad del enemigo para que
     * camine en direccion contraria
     * @param x indica si debe invertir la velocidad en eje x
     * @param y indica si debe invertir la velocidad en eje y
     */
    public void reverseVelocity(boolean x, boolean y) {
        if (x) {
            velocity.x = -velocity.x;
        }
        if (y) {
            velocity.y = -velocity.y;
        }
    }
}
