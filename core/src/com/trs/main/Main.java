package com.trs.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.trs.main.view.screens.GameScreen;
import com.trs.main.view.screens.InventoryScreen;
import com.trs.main.view.screens.MenuScreen;

public class Main extends Game{
    
    /**
    * TODO: set final Value for World
     */
	// TEST
	
	// 0: normal game world, 1: dialogue, 2: fight
        // 7: Load MenuScreen 8: Load GameScreen 9: Load InventoryScreen
    public static int gamestate = 0;
    public static float CAMERA_WIDTH = 854;
    public static float CAMERA_HEIGHT = 480;

    MenuScreen menuScreen;
    GameScreen gameScreen;
    InventoryScreen inventoryScreen;
        
        
       
    @Override
    public void create () {
        menuScreen = new MenuScreen(this, CAMERA_WIDTH, CAMERA_HEIGHT);
        gameScreen = new GameScreen(this, CAMERA_WIDTH, CAMERA_HEIGHT);
        inventoryScreen = new InventoryScreen(this, CAMERA_WIDTH, CAMERA_HEIGHT);
        
          screen = gameScreen;
    }

    @Override
    public void resize(int width, int height) {
        screen.resize(width, width);
        super.resize(width, height); //To change body of generated methods, choose Tools | Templates.
    }
    
        

    @Override
    public void render () {
        Gdx.gl.glClearColor(0f, (0), (0), 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        switch(gamestate){
            case 7:
                if(menuScreen.getPaused()){
                    //if(screen instanceof GameScreen) gameScreen = (GameScreen)screen;
                    //if(screen instanceof InventoryScreen) inventoryScreen = (InventoryScreen)screen;
                    screen = menuScreen;
                    gamestate = 0;
                }
                break;
            case 8:
                if(gameScreen.getPaused()){
                    //if(screen instanceof MenuScreen) menuScreen = (MenuScreen)screen;
                    //if(screen instanceof InventoryScreen) inventoryScreen = (InventoryScreen)screen;
                    screen = gameScreen;
                    gamestate = 0;
                }
                break;
            case 9:
                if(inventoryScreen.getPaused()){
                    //if(screen instanceof MenuScreen) menuScreen = (MenuScreen)screen;
                    //if(screen instanceof GameScreen) gameScreen = (GameScreen)screen;
                    screen = inventoryScreen;
                    gamestate = 0;
                }
                break;
            default:
                break;
        }
        
        screen.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void dispose () {
        screen.dispose();
    }
/*
    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float f, float f1) {
        return false;
    }
        */
}
