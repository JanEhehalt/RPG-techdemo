/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trs.main.view.screens;

import com.badlogic.gdx.Game;
import com.trs.main.Main;
import com.trs.main.MapContainer;
import com.trs.main.Player;

/**
 *
 * @author Jan
 */
public class GameScreen extends AbstractScreen{
    
    MapContainer map;

    public GameScreen(Game game, float CAMERA_WIDTH, float CAMERA_HEIGHT) {
        super(game, CAMERA_WIDTH, CAMERA_HEIGHT);
        //setTextbox(new Textbox("How are you doing my friend How are you doing my friend How are you doing my friend How are you doing my friend", "good", "bad"));
        
        map = new MapContainer(CAMERA_WIDTH, CAMERA_HEIGHT, new Player(200, 200), "map2.tmx", 0);
    }
    
    public void loadNewMap(int map, int doorId){
    	String filename = "map" + map + ".tmx";
        this.map = new MapContainer(Main.CAMERA_WIDTH, Main.CAMERA_HEIGHT, this.map.getPlayer(), filename, doorId);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float f) {
        map.render(f);
        /*
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)){
            loadNewMap("map.tmx");
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)){
            loadNewMap("map2.tmx");
        }
        */
        if(map.collidingDoor != null) {
        	loadNewMap(map.collidingDoor.destinationMap, map.collidingDoor.destinationDoor);
        }
    }

    @Override
    public void resize(int width, int height) {
        map.resize(width, height);
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        paused = false;
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

    
    
}
