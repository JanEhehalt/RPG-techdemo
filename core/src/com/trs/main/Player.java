/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trs.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
/**
 * 
 *
 * @author Jan
 */
public class Player extends Actor{
    
    Texture t;
    
    Player(){
        t = new Texture(Gdx.files.internal("badlogic.jpg"));
        setBounds(50, 50, t.getWidth(), t.getHeight());
    }

    @Override
    protected void positionChanged() {
        setX(getX());
        setY(getY());
        super.positionChanged(); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    @Override
    public void act(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            setX(getX()+10);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            setX(getX()-10);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            setY(getY()+10);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            setY(getY()-10);
        }
        super.act(delta); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(t, getX(), getY());
        super.draw(batch, parentAlpha); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean remove() {
        return super.remove(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
