package com.trs.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.trs.main.view.screens.GameScreen;
import com.trs.main.view.screens.InventoryScreen;
import com.trs.main.view.screens.MenuScreen;

public class Main extends Game{
    
    /**
    * TODO: set final Value for World
     */
	// TEST
	
        // -1: Debug centerCam, -2: Debug freeCam
	// 0: normal game world, 1: dialogue, 2: fight
        // 7: Load MenuScreen 8: Load GameScreen 9: Load InventoryScreen
    public static int gamestate = 0;
    private int fallbackState = 0;
    public static float CAMERA_WIDTH = 960;
    public static float CAMERA_HEIGHT = 540;
    /**
     * TODO: 
     *  figure out, how(if) we can change CamWidth and Height while ingame (e.G. for debug mode)
     */
    
    // DEBUG
    public static boolean debugModeActive = false;
    
    public static boolean drawAbove = true;
    public static boolean drawBelow = true;

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
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }
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
        /*
        ShapeRenderer renderer = new ShapeRenderer();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(51f/255f, 26f/255f, 0f/255f, 0.3f);
        renderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        */
        if(debugModeActive){
            if(Gdx.input.isKeyJustPressed(Input.Keys.F8)){
                    if(drawAbove){
                        drawAbove = false;
                    }
                    else{
                        drawAbove = true;
                    }
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.F9)){
                    if(drawBelow){
                        drawBelow = false;
                    }
                    else{
                        drawBelow = true;
                    }
            }
        }
        
        if(Gdx.input.isKeyJustPressed(Input.Keys.F1)){
            if(!debugModeActive){
                fallbackState = gamestate;
                debugModeActive = true;
            }
            gamestate = -1;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.F2)){
            if(!debugModeActive){
                fallbackState = gamestate;
                debugModeActive = true;
            }
            gamestate = -2;
        }   
        if(Gdx.input.isKeyJustPressed(Input.Keys.F3)){
            if(debugModeActive){
                gamestate = fallbackState;
                debugModeActive = false;
                fallbackState = 0;
            }
        }
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
