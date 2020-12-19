package com.trs.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.trs.main.view.screens.GameScreen;

public class Main extends Game{
    
    /**
     * TODO: set final Value for World
     */
       
    @Override
    public void create () {
        screen = new GameScreen(this, 400f, 400f);
    }

    @Override
    public void resize(int width, int height) {
        screen.resize(width, width);
        super.resize(width, height); //To change body of generated methods, choose Tools | Templates.
    }
    
        

    @Override
    public void render () {
        //Gdx.gl.glClearColor(1f, (20f/255f), (147f/255f), 1);
        Gdx.gl.glClearColor(1f, (1), (1), 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
