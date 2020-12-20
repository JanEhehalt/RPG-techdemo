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
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
/**
 * 
 *
 * @author Jan
 */
public class Player extends Actor{
    
    public static final float SQRT2 = 1.414f;
    
    Texture t;
    private AnimatedSprite playerSprite;
    float movementX = 0;
    float movementY = 0;
    float speed = 3f;
    float velocity = 0.2f;
    // 0: up, 1: left, 2: down, 3: right
    int facing = 0;
    
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
    	if(Main.gamestate == 0) {
    		if(Gdx.input.isKeyPressed(Input.Keys.D)){
                movementX = speed;
                facing = 3;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.A)){
                movementX = -speed;
                facing = 1;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.W)){
                movementY = speed;
                facing = 0;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.S)){
                movementY = -speed;
                facing = 2;
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            	Main.gamestate = 1;
            	getStage().addActor(new Textbox("Dies ist eine coole Test Textbox", "Feier ich", "Ehre"));
            }
    	}
    	else if(Main.gamestate == 1) {
    		// Input handled by invoked textbox
    	}
        /**
        *   return
        *   0:  only vertical movement available
        *   1:  only horizontal movement available
        *   2:  full movement available
        *   3:  no movement available
        */
        if(movementX == 0 && movementY == 0){
        }
        else if(movementX == 0 && movementY != 0){
            setY(getY()+movementY);
            if(collidingWithMapCollisionObject()){
                setY(getY()-movementY);
            }
        }
        else if(movementY == 0 && movementX != 0){
            setX(getX()+movementX);
            if(collidingWithMapCollisionObject()){
                setX(getX()-movementX);
            }
        }
        else if(movementX != 0 && movementY != 0){
        	setX(getX()+ (movementX / SQRT2));
            if(collidingWithMapCollisionObject()){
                setX(getX() - (movementX / SQRT2));
            }

            setY(getY() + (movementY / SQRT2));
            if(collidingWithMapCollisionObject()){
                setY(getY()- (movementY / SQRT2));
            }
        }
        
        int animationRow = 0;
        if(movementX != 0 || movementY != 0) {
        	animationRow = 8;
        }
        		
        playerSprite.setRow(animationRow + facing);
        velocity(velocity);
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
    
    public boolean collidingWithMapCollisionObject(){
        boolean  value = false;
        for(Actor a : getStage().getActors()){
                if(a.getName().equals("mapobject")){
                    Rectangle p = new Rectangle(getX(), getY(), getWidth(), getHeight());
                    Rectangle o = new Rectangle(a.getX(), a.getY(), a.getWidth(), a.getHeight());
                    if(Intersector.overlaps(p, o)){
                        value = true;
                        break;
                    }
                }
        }
        return value;
    }   
    
    public void velocity(float velocity){
        if(movementX > 0){
            movementX -= velocity;
            if(movementX < 0){
                movementX = 0;
            }
        }
        else if(movementX < 0){
            movementX += velocity;
            if(movementX > 0){
                movementX = 0;
            }
        }
        
        if(movementY > 0){
            movementY -= velocity;
            if(movementY < 0){
                movementY = 0;
            }
        }
        else if(movementY < 0){
            movementY += velocity;
            if(movementY > 0){
                movementY = 0;
            }
        }
    }
    /*
    public int getMovementAvailable(){
        
        boolean canMoveVer = true;
        boolean canMoveHor = true;
        boolean canMoveBoth = true;
        for(Actor a : getStage().getActors()){
            if(a.getName().equals("mapobject")){
                Rectangle p = new Rectangle(getX()+movementX, getY() + movementY, getWidth(), getHeight());
                Rectangle o = new Rectangle(a.getX(), a.getY(), a.getWidth(), a.getHeight());
                if(Intersector.overlaps(p, o)){
                    canMoveBoth = false;
                    break;
                }
            }
        }
        if(canMoveBoth){
            return 2;
        }
        
        for(Actor a : getStage().getActors()){
            if(a.getName().equals("mapobject")){
                Rectangle p = new Rectangle(getX()+movementX, getY(), getWidth(), getHeight());
                Rectangle o = new Rectangle(a.getX(), a.getY(), a.getWidth(), a.getHeight());
                if(Intersector.overlaps(p, o)){
                    canMoveHor = false;
                    break;
                }
            }
        }
        if(canMoveHor){
            return 1;
        }
        for(Actor a : getStage().getActors()){
            if(a.getName().equals("mapobject")){
                Rectangle p = new Rectangle(getX(), getY()+movementY, getWidth(), getHeight());
                Rectangle o = new Rectangle(a.getX(), a.getY(), a.getWidth(), a.getHeight());
                if(Intersector.overlaps(p, o)){
                    canMoveVer = false;
                    break;
                }
            }
        }
        if(canMoveVer){
            return 0;
        }
         
        return 3;
    }
    */
}
