/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trs.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import static com.trs.main.Player.SQRT2;

/**
 *
 * @author Jan
 */
public class MovingNpc extends Actor{
    
    public static final float SQRT2 = 1.414f;
    Textbox t;
    Rectangle area;
    Rectangle collisionRect;
    float speed;
    private AnimatedSprite animatedSprite;
    int facing;
    float movementX;
    float movementY;
    
    Vector2 POI;
    
    public MovingNpc(Rectangle area, float xPos, float yPos){
        setName("npc");
        Texture t = new Texture(Gdx.files.internal("player.png"));
        animatedSprite = new AnimatedSprite(t, 64, 64);
        animatedSprite.setRow(0);
        collisionRect = new Rectangle(xPos + 16, yPos, 32, 48);
        this.area = area;
        speed = 2.5f;
        setBounds(xPos, yPos, animatedSprite.getSprite().getWidth(), animatedSprite.getSprite().getHeight());
    }
    
    @Override
    protected void positionChanged() {
        animatedSprite.setSpritePosition((int)getX(), (int)getY());
        collisionRect = new Rectangle(getX() + 16, getY(), 32, 48);
        super.positionChanged(); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void act(float delta) {
        
        if(POI == null || Math.random() < 0.05f){
            POI = new Vector2(area.getX()+(float)Math.random() * (float)area.getWidth(), area.getY()+(float)Math.random()*(float)area.getHeight());
        }
        Vector2 movement = new Vector2(speed,0);
        movement.setAngleRad((float)Math.atan((double)(POI.y-getY())/(double)(POI.x-getX())));
        System.out.println(movement.angleDeg());
        System.out.println(POI.x + " " + POI.y);
        System.out.println();
        movementX = movement.x;
        movementY = movement.y;
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
        	setX(getX()+ (movementX));
            if(collidingWithMapCollisionObject()){
                setX(getX() - (movementX));
            }

            setY(getY() + (movementY));
            if(collidingWithMapCollisionObject()){
                setY(getY()- (movementY));
            }
        }
        
        int animationRow = 0;
        if(movementX != 0 || movementY != 0) {
        	animationRow = 8;
        }
        		
        animatedSprite.setRow(animationRow + facing);
        animatedSprite.updateAnimation(delta);
        movementX = 0;
        movementY = 0;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        animatedSprite.draw(batch);
        super.draw(batch, parentAlpha); //To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean collidingWithMapCollisionObject(){
        boolean  value = false;
        for(Actor a : getStage().getActors()){
                if(a.getName().equals("mapobject")){
                    //Rectangle p = new Rectangle(getX(), getY(), getWidth(), getHeight());
                    Rectangle o = new Rectangle(a.getX(), a.getY(), a.getWidth(), a.getHeight());
                    if(Intersector.overlaps(collisionRect, o)){
                        value = true;
                        break;
                    }
                }
        }
        return value;
    }  
    
}