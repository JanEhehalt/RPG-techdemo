/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trs.main.view.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.trs.main.Player;

/**
 *
 * @author Jan
 */
public class GameScreen extends AbstractScreen{

    public GameScreen(Game game, float CAMERA_WIDTH, float CAMERA_HEIGHT) {
        super(game, CAMERA_WIDTH, CAMERA_HEIGHT);
        Group group = new Group();
        group.addActor(new Player(0,0));
        group.addActor(new Player(256,0));
        group.addActor(new Player(0,256));
        group.addActor(new Player(256,256));
        group.addActor(new Player(512,512));
        group.addActor(new Player(256,512));
        group.addActor(new Player(0,512));
        group.addActor(new Player(512,256));
        group.addActor(new Player(512,0));
        stage.addActor(group);
    }


    @Override
    public void show() {
    }

    @Override
    public void render(float f) {
        stage.act(f);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
    
}
