/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trs.main.view.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.trs.main.Main;
import com.trs.main.MapContainer;
import com.trs.main.Quest;
import com.trs.main.fightscreen.FightDialogue;
import com.trs.main.view.UI.DebugUI;
import com.trs.main.view.UI.QuestWindow;
import com.trs.main.view.UI.Textbox;
import com.trs.main.worldobjects.Player;

/**
 *
 * @author Jan
 */
public class GameScreen extends AbstractScreen{
    
    MapContainer map;
    QuestWindow qw;
    DebugUI debugUI;
    
    public ShapeRenderer uiRenderer = new ShapeRenderer();
    
    public GameScreen(Game game, float CAMERA_WIDTH, float CAMERA_HEIGHT) {
        super(game, CAMERA_WIDTH, CAMERA_HEIGHT);
        map = new MapContainer(CAMERA_WIDTH, CAMERA_HEIGHT, new Player(200, 200, uiRenderer), "tiledmapData/maps/map1.tmx", 2, 1, uiRenderer);
        qw = new QuestWindow(map.getCamera().combined);
        debugUI = new DebugUI(map.getCamera().combined);
        Matrix4 uiMatrix = map.getCamera().combined.cpy();
        uiMatrix.setToOrtho2D(0, 0, Main.CAMERA_WIDTH, Main.CAMERA_HEIGHT);
        Textbox.m = uiMatrix;
        FightDialogue.m = uiMatrix;
    }
    
    public void loadNewMap(int map, int doorId){
    	String filename = "tiledmapData/maps/map" + map + ".tmx";
        this.map = new MapContainer(Main.CAMERA_WIDTH, Main.CAMERA_HEIGHT, this.map.getPlayer(), filename, doorId, map, uiRenderer);
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
        
        //qw.draw(rects, map.getStage().getBatch(), map.getPlayer().getX()+32, map.getPlayer().getY()+32);
        qw.draw(rects);
        
        if(map.getCollidingDoor() != null) {
        	loadNewMap(map.getCollidingDoor().destinationMap, map.getCollidingDoor().destinationDoor);
        }
        
        if(Main.gamestate == -2){
            float camSpeed = 10;
            if(Gdx.input.isKeyPressed(Input.Keys.ALT_RIGHT)){
                camSpeed = 1;
            }
            
            
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                map.getStage().getCamera().translate(-camSpeed, 0, 0);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                map.getStage().getCamera().translate(camSpeed, 0, 0);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.UP)){
                map.getStage().getCamera().translate(0, camSpeed, 0);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                map.getStage().getCamera().translate(0, -camSpeed, 0);
            }
        }
        else{
            Player a = map.getPlayer();
            map.getStage().getCamera().position.set((a.getX()+a.getWidth()/2), (a.getY()+a.getHeight()/2), 0);
            //map.getStage().getCamera().update();
        }
        
        if(Main.debugModeActive){
            map.debugDoor(uiRenderer);
            debugUI.draw(map.getStage().getActors().size, new Vector2(map.getStage().getCamera().position.x, map.getStage().getCamera().position.y));
        }
    }
    

    @Override
    public void resize(int width, int height) {
        map.resize(width, height);
        debugUI.resize();
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
