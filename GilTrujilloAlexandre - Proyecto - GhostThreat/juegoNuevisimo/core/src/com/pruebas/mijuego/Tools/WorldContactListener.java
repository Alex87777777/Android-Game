package com.pruebas.mijuego.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.pruebas.mijuego.MiJuego;
import com.pruebas.mijuego.Scenes.GameHud;
import com.pruebas.mijuego.Screens.PlayScreen;
import com.pruebas.mijuego.Sprites.Enemy;
import com.pruebas.mijuego.Sprites.Yokai;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        if (!PlayScreen.player.isDead) {

            Fixture fixA = contact.getFixtureA();
            Fixture fixB = contact.getFixtureB();

            int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

            switch (cDef) {
                case MiJuego.ENEMY_BIT | MiJuego.WALL_BIT:
                    if (fixA.getFilterData().categoryBits == MiJuego.WALL_BIT) {
                        ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                    } else {
                        ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                    }
                    break;
                case MiJuego.PLAYER_BIT | MiJuego.ENEMY_BIT:
                    PlayScreen.player.collisionWithEnemy = true;
                    if (fixA.getFilterData().categoryBits == MiJuego.PLAYER_BIT) {
                        playerEnemyCollision((Enemy) fixB.getUserData());
                    } else if (fixB.getFilterData().categoryBits == MiJuego.PLAYER_BIT) {
                        playerEnemyCollision((Enemy) fixA.getUserData());
                    }
                    break;
                case MiJuego.ENEMY_BIT | MiJuego.ENEMY_BIT:
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                    break;
                case MiJuego.PLAYER_BIT | MiJuego.LADDER_BIT:
                    PlayScreen.player.collisionWithLadder = true;
                    break;
                case MiJuego.PLAYER_BOTTOM_BIT | MiJuego.GROUND_BIT:
                    Gdx.app.log("SALTAR", "SIIIIIIIIIIIIIIII");
                    PlayScreen.player.collisionWithGround = true;
                    break;
                case MiJuego.PLAYER_BIT | MiJuego.HEART_ITEM_BIT:
                    PlayScreen.player.collisionWithHeart = true;
                    break;
                case MiJuego.PLAYER_BIT | MiJuego.JUMP_ITEM_BIT:
                    PlayScreen.player.collisionWithJumpBoost = true;
                    break;
                case MiJuego.PLAYER_BOTTOM_BIT | MiJuego.GOAL_BIT:
                    PlayScreen.player.completeLevel();
                    PlayScreen.player.levelCompleted = true;
                    break;
            }
        }
    }

    /**
     * gestiona la colisión del jugador con un enemigo
     * @param enemy enemigo que entra en contacto con el jugador
     */
    private void playerEnemyCollision(Enemy enemy) {
        enemy.velocity.x = 0;
        if (!PlayScreen.player.invulnerable && !PlayScreen.player.attacking) {
            if (PlayScreen.player.vidas <= 1) {
                enemy.velocity.x = 0;
            }
            if (!PlayScreen.player.isDead) {
                PlayScreen.player.hit();
            }
        }
        if (PlayScreen.player.attacking) {
            GameHud.addScore(100);
        }
        enemy.hit();
    }

    @Override
    public void endContact(Contact contact) {
        if (!PlayScreen.player.isDead) {
            Fixture fixA = contact.getFixtureA();
            Fixture fixB = contact.getFixtureB();

            int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

            switch (cDef) {
                case MiJuego.PLAYER_BIT | MiJuego.ENEMY_BIT:
                    PlayScreen.player.collisionWithEnemy = false;
                    if (fixA.getFilterData().categoryBits == MiJuego.PLAYER_BIT) {
                        if (((Yokai) fixB.getUserData()).runningRight) {
                            ((Enemy) fixB.getUserData()).velocity.x = 1;
                        } else {
                            ((Enemy) fixB.getUserData()).velocity.x = -1;
                        }
                    } else if (fixB.getFilterData().categoryBits == MiJuego.PLAYER_BIT) {
                        if (((Yokai) fixA.getUserData()).runningRight) {
                            ((Enemy) fixA.getUserData()).velocity.x = 1;
                        } else {
                            ((Enemy) fixA.getUserData()).velocity.x = -1;
                        }
                    }
                    break;
                case MiJuego.PLAYER_BIT | MiJuego.LADDER_BIT:
                    PlayScreen.player.collisionWithLadder = false;
                    break;
                case MiJuego.PLAYER_BIT | MiJuego.HEART_ITEM_BIT:
                    PlayScreen.player.collisionWithHeart = false;
                    break;
                case MiJuego.PLAYER_BIT | MiJuego.JUMP_ITEM_BIT:
                    PlayScreen.player.collisionWithJumpBoost = false;
                    break;
                case MiJuego.PLAYER_BOTTOM_BIT | MiJuego.GROUND_BIT:
                    Gdx.app.log("SALTAR", "NOOOOOOOOOOOOOO");
                    PlayScreen.player.collisionWithGround = false;
                    break;
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
