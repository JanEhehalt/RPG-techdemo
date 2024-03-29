/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trs.main.view.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

/**
 *
 * @author Jan
 */
public abstract class AbstractScreen implements Screen{
    
    
    protected Game game;
    protected boolean paused;
    

    public AbstractScreen(Game game, float CAMERA_WIDTH, float CAMERA_HEIGHT) {
        this.game = game;
        this.paused = true;
    }
    
    @Override
    public abstract void show();

    @Override
    public abstract void render(float f);

    @Override
    public abstract void resize(int i, int i1);

    @Override
    public abstract void pause();

    @Override
    public abstract void resume();

    @Override
    public abstract void hide();

    @Override
    public abstract void dispose();
    
    public boolean getPaused(){
        return paused;
    }
    
}
