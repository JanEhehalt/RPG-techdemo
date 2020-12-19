/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trs.main.view.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.trs.main.Player;
import com.trs.main.Textbox;

/**
 *
 * @author Jan
 */
public class GameScreen extends AbstractScreen{
    
    boolean textbox = false;

    public GameScreen(Game game, float CAMERA_WIDTH, float CAMERA_HEIGHT) {
        super(game, CAMERA_WIDTH, CAMERA_HEIGHT);
        
        //setTextbox(new Textbox("How are you doing my friend How are you doing my friend How are you doing my friend How are you doing my friend", "good", "bad"));
    }
    
    @Override
    public void setTextbox(Textbox t) {
        stage.addActor(t);
        textbox = true;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float f) {
        if(!textbox){
            stage.act(f);
            stage.draw();
        }
        else{
            Textbox t = null;
            for(Actor a : stage.getActors()){
                if(a.getName().equals("textbox")){
                    a.act(f);
                    a.draw(stage.getBatch(), CAMERA_WIDTH);
                    t = (Textbox)a;
                    if(t.getState() == 2){
                        a.remove();
                    }
                }
            }
        }
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
