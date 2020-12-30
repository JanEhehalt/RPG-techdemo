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
        
        map = new MapContainer(CAMERA_WIDTH, CAMERA_HEIGHT, new Player(200, 200), "tiledmapData/maps/map1.tmx", 2, 1);
    }
    
    public void loadNewMap(int map, int doorId){
    	String filename = "tiledmapData/maps/map" + map + ".tmx";
        this.map = new MapContainer(Main.CAMERA_WIDTH, Main.CAMERA_HEIGHT, this.map.getPlayer(), filename, doorId, map);
        System.out.println("Doorid: " + doorId);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float f) {
        map.render(f);
        
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
