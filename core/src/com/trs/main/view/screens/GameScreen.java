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
import com.trs.main.Player;
import com.trs.main.Textbox;
import java.util.ArrayList;

/**
 *
 * @author Jan
 */
public class GameScreen extends AbstractScreen{
    
    
    TmxMapLoader maploader;
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    ArrayList<TiledMapTileMapObject> object = new ArrayList<>();
    

    public GameScreen(Game game, float CAMERA_WIDTH, float CAMERA_HEIGHT) {
        super(game, CAMERA_WIDTH, CAMERA_HEIGHT);
        stage.addActor(new Player(250,50));
        //setTextbox(new Textbox("How are you doing my friend How are you doing my friend How are you doing my friend How are you doing my friend", "good", "bad"));
        
        maploader = new TmxMapLoader();
        map = maploader.load("map2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        renderer.setView((OrthographicCamera)stage.getCamera());
        stage.getCamera().update();
        
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            stage.addActor(new MapCollisionObject((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight()));
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float f) {
        
        renderer.setView((OrthographicCamera)stage.getCamera());
        
        
        renderer.render();
        
        stage.act(f);
        stage.draw();
        
        if(Main.gamestate == 1) {
            Textbox t = null;
            for(Actor a : stage.getActors()){
                if(a.getName().equals("textbox")){
                    t = (Textbox)a;
                    if(t.getState() == 2){
                        a.remove();
                        Main.gamestate = 0;
                        t.getSelectedAsw(); // DO STUFF NICENICE
                    }
                }
            }
        }
        
        // center camera
        for(Actor a : stage.getActors()){
            if(a.getName().equals("player")){
                stage.getCamera().position.set((a.getX()+a.getWidth()/2), (a.getY()+a.getHeight()/2), 0);
                stage.getCamera().update();
                break;
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
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
