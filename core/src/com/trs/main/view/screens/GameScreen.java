/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trs.main.view.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Rectangle;
import com.trs.main.Main;
import com.trs.main.MapContainer;
import com.trs.main.Quest;
import com.trs.main.view.QuestWindow;
import com.trs.main.worldobjects.Player;

/**
 *
 * @author Jan
 */
public class GameScreen extends AbstractScreen{
    
    MapContainer map;
    QuestWindow qw;

    public GameScreen(Game game, float CAMERA_WIDTH, float CAMERA_HEIGHT) {
        super(game, CAMERA_WIDTH, CAMERA_HEIGHT);
        map = new MapContainer(CAMERA_WIDTH, CAMERA_HEIGHT, new Player(200, 200), "tiledmapData/maps/map1.tmx", 2, 1);
        qw = new QuestWindow();
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
        Quest[] rects = new Quest[map.getPlayer().getQuests().size()];
        for(int i = 0; i< map.getPlayer().getQuests().size(); i++){
            rects[i] = map.getPlayer().getQuests().get(i);
        }
        qw.draw(rects, map.getStage().getBatch(), map.getPlayer().getX()+32, map.getPlayer().getY()+32);
        if(map.getCollidingDoor() != null) {
        	loadNewMap(map.getCollidingDoor().destinationMap, map.getCollidingDoor().destinationDoor);
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
