/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trs.main.view.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.trs.main.Main;

/**
 *
 * @author Jan
 */
public class InventoryScreen extends AbstractScreen{
    
    /**
     * TODO: Inventorydata? | Playerdata in constructor? own function? resume?
     * @param game
     * @param CAMERA_WIDTH
     * @param CAMERA_HEIGHT 
     */
    
    
    public InventoryScreen(Game game, float CAMERA_WIDTH, float CAMERA_HEIGHT) {
        super(game, CAMERA_WIDTH, CAMERA_HEIGHT);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float f) {
            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)){
                Main.gamestate = 8;
            }
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
        paused = false;
    }

    @Override
    public void hide() {
        paused = true;
    }

    @Override
    public void dispose() {
    }
    
}
