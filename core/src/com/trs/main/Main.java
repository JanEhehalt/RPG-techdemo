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
	
        // -1: Debug
	// 0: normal game world, 1: dialogue, 2: fight
        // 7: Load MenuScreen 8: Load GameScreen 9: Load InventoryScreen
    public static int gamestate = 0;
    public static boolean debugUI = false;
    private int fallbackState = 0;
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
        
        if(Gdx.input.isKeyJustPressed(Input.Keys.ALT_LEFT)){
            if(gamestate == -1){
                gamestate = fallbackState;
            }
            else{
                fallbackState = gamestate;
                gamestate = -1;
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.F1)){
            if(!debugUI){
                debugUI = true;
            }
            else{
                debugUI = false;
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
