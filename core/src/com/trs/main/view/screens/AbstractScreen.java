/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trs.main.view.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.trs.main.Textbox;

/**
 *
 * @author Jan
 */
public abstract class AbstractScreen implements Screen{
    
    float CAMERA_WIDTH;
    float CAMERA_HEIGHT;
    
    protected Game game;
    Stage stage;
    OrthographicCamera camera;
    

    public AbstractScreen(Game game, float CAMERA_WIDTH, float CAMERA_HEIGHT) {
        this.game = game;
        this.CAMERA_WIDTH = CAMERA_WIDTH;
        this.CAMERA_HEIGHT = CAMERA_HEIGHT;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, this.CAMERA_WIDTH, this.CAMERA_HEIGHT);
        camera.update();
        stage = new Stage(new FitViewport(this.CAMERA_WIDTH, this.CAMERA_HEIGHT, camera));
        Gdx.input.setInputProcessor(stage);
    }
    
    public abstract void setTextbox(Textbox t);
    
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
    
}
