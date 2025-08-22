package com.pruebas.mijuego.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.pruebas.mijuego.MiJuego;
import com.pruebas.mijuego.Screens.PlayScreen;
import com.pruebas.mijuego.Sprites.HeartItem;
import com.pruebas.mijuego.Sprites.JumpItem;
import com.pruebas.mijuego.Sprites.Yokai;

public class B2WorldCreator {
    /**
     * lista de los yokais del mapa
     */
    private Array<Yokai> enemies;
    /**
     * lista de los corazones del mapa
     */
    private Array<HeartItem> hearts;
    /**
     * lista de los items de salto del mapa
     */
    private Array<JumpItem> jumps;
    public Array<Yokai> getEnemies() {
        return enemies;
    }
    public Array<HeartItem> getHearts() {
        return hearts;
    }
    public Array<JumpItem> getJumps() {
        return jumps;
    }

    /**
     * Constructor del creador del mundo donde se crean los cuerpos y fixtures de cada capa
     * @param screen pantalla del juego
     */
    public B2WorldCreator(PlayScreen screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        //GROUND FIXTURE
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MiJuego.PPM, (rect.getY() + rect.getHeight() / 2) / MiJuego.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / MiJuego.PPM, rect.getHeight() / 2 / MiJuego.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = MiJuego.GROUND_BIT;
            body.createFixture(fdef);
        }
        //WALLS FIXTURES
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MiJuego.PPM, (rect.getY() + rect.getHeight() / 2) / MiJuego.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / MiJuego.PPM, rect.getHeight() / 2 / MiJuego.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = MiJuego.WALL_BIT;
            body.createFixture(fdef);
        }
        //LADDERS
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MiJuego.PPM, (rect.getY() + rect.getHeight() / 2) / MiJuego.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / MiJuego.PPM, rect.getHeight() / 2 / MiJuego.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = MiJuego.LADDER_BIT;
            body.createFixture(fdef);
        }
        //END FIXTURE
        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MiJuego.PPM, (rect.getY() + rect.getHeight() / 2) / MiJuego.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / MiJuego.PPM, rect.getHeight() / 2 / MiJuego.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = MiJuego.GOAL_BIT;
            body.createFixture(fdef);
        }
        //YOKAIS BODIES/FIXTURES
        enemies = new Array<>();
        for (MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            enemies.add(new Yokai(screen, rect.getX() / MiJuego.PPM, rect.getY() / MiJuego.PPM));
        }
        //HEARTS FIXTURES
        hearts = new Array<>();
        for (MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            hearts.add(new HeartItem(screen, rect.getX() / MiJuego.PPM, rect.getY() / MiJuego.PPM));
        }
        //JUMP ITEMS FIXTURES
        jumps = new Array<>();
        for (MapObject object : map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            jumps.add(new JumpItem(screen, rect.getX() / MiJuego.PPM, rect.getY() / MiJuego.PPM));
        }
    }

}
