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
    float movementX = 0;
    float movementY = 0;
    
    public Player(int xPos, int yPos){
        
        setName("player");
        t = new Texture(Gdx.files.internal("player.png"));
        playerSprite = new AnimatedSprite(t, 64, 64);
        playerSprite.setRow(0);
        setBounds(xPos, yPos, playerSprite.getSprite().getWidth(), playerSprite.getSprite().getHeight());
    }

    @Override
    protected void positionChanged() {
        playerSprite.setSpritePosition((int)getX(), (int)getY());
        super.positionChanged(); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    @Override
    public void act(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            setX(getX()-5);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            setY(getY()+5);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            setY(getY()-5);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            playerSprite.setRow(playerSprite.getRow()+1);
            playerSprite.setFrame(0);
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
