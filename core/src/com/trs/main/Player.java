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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
/**
 * 
 *
 * @author Jan
 */
public class Player extends Actor{
    
    Texture t;
    private AnimatedSprite playerSprite;
    
    public Player(int xPos, int yPos){
        t = new Texture(Gdx.files.internal("badlogic.jpg"));
        setBounds(xPos, yPos, t.getWidth(), t.getHeight());
        playerSprite = new AnimatedSprite(t, 64, 64);
    }

    @Override
    protected void positionChanged() {
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
        
        playerSprite.updateAnimation(delta);
        super.act(delta); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        playerSprite.draw(batch);
        super.draw(batch, parentAlpha); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean remove() {
        return super.remove(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
