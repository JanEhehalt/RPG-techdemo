/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trs.main.view.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.trs.main.Main;
import com.trs.main.MapCollisionObject;
import com.trs.main.MapContainer;
import com.trs.main.MovingNpc;
import com.trs.main.Player;
import com.trs.main.Textbox;
import java.util.ArrayList;

/**
 *
 * @author Jan
 */
public class GameScreen extends AbstractScreen{
    
    MapContainer map;

    public GameScreen(Game game, float CAMERA_WIDTH, float CAMERA_HEIGHT) {
        super(game, CAMERA_WIDTH, CAMERA_HEIGHT);
        //setTextbox(new Textbox("How are you doing my friend How are you doing my friend How are you doing my friend How are you doing my friend", "good", "bad"));
        
        map = new MapContainer(CAMERA_WIDTH, CAMERA_HEIGHT, new Player(200, 200), "map2.tmx");
        
        
    }
    
    public void loadNewMap(String map){
        this.map = new MapContainer(Main.CAMERA_WIDTH, Main.CAMERA_HEIGHT, this.map.getPlayer(), map);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float f) {
        map.render(f);
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)){
            loadNewMap("map.tmx");
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)){
            loadNewMap("map2.tmx");
        }
    }

    @Override
    public void resize(int width, int height) {
        map.resize(width, height);
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
    }

    
    
}
