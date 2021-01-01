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
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import java.util.ArrayList;
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
    float velocity = 0.8f;
    // 0: up, 1: left, 2: down, 3: right
    int facing = 0;
    
    ArrayList<Quest> quests;
    
    Rectangle collisionRect;
    
    Group questGroup;
    
    public Player(int xPos, int yPos){
        setName("player");
        t = new Texture(Gdx.files.internal("textureData/sprites/player.png"));
        playerSprite = new AnimatedSprite(t, 64, 64, true);
        playerSprite.setRow(0);
        collisionRect = new Rectangle(xPos + 16, yPos, 32, 16);
        setBounds(xPos, yPos, playerSprite.getSprite().getWidth(), playerSprite.getSprite().getHeight());
        quests = new ArrayList<>();
        
        int[] n = {1, 1};        
        int[] m = {1, 0};
        quests.add(new InformationQuest(0, "Sprich mit Folgenden NPCs: (Id, mapId, schonGereded?) !Reihenfolge wichtig!", m, n, true));
        quests.add(new InformationQuest(1, "jajajaj nicenicenice", m, n, true));
    }

    @Override
    protected void positionChanged() {
        playerSprite.setSpritePosition((int)getX(), (int)getY());
        collisionRect = new Rectangle(getX() + 16, getY(), 32, 16);
        super.positionChanged(); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    @Override
    public void act(float delta) {
        
        for(Quest quest : quests){
            quest.print();
            System.out.println();
        }
            System.out.println();
        
    	if(Main.gamestate == 0) {
            if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
                speed = 9;
            }
            else speed = 3;
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
            
            if(Gdx.input.isKeyPressed(Input.Keys.NUM_1)){
                movementY = -8;
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)){
                Main.gamestate = 9;
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                Actor a = collidingActor();
                if(a != null) {
                    if(a instanceof MovingNpc){
                        Main.gamestate = 1;
                        boolean dialogueStarted = false;
                        for(Quest quest : quests){
                            if(quest instanceof InformationQuest){
                                if(((InformationQuest)quest).hasSpecialDialogue(((MovingNpc) a).id, ((MovingNpc) a).mapId)){
                                    ((MovingNpc) a).startSpecialDialogue(((InformationQuest)quest).getDialoguePath(((MovingNpc) a).id, ((MovingNpc) a).mapId), getX()+32, getY()+32);
                                    ((InformationQuest) quest).talk((MovingNpc)a);
                                    dialogueStarted = true;
                                    break;
                                }   
                            }
                        }
                        if(!dialogueStarted){
                            ((MovingNpc)a).startDialogue(getX()+32, getY()+32);
                        }
                        
                        movementX = 0;
                        movementY = 0;
                    }
                    else if(a instanceof InteractionObject) {
                    	Main.gamestate = 1;
                        ((InteractionObject)a).startDialogue(getX()+32, getY()+32);
                        movementX = 0;
                        movementY = 0;
                    }
                }
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

        System.out.println("--");
        for(Quest quest : quests){
            quest.updateQuest();
            quest.print();
        System.out.println("--");
        }
        System.out.println("--");
        
        
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
        for(Actor a : getStage().getActors()){
                if(a instanceof MapCollisionObject){
                    Rectangle o = new Rectangle(a.getX(), a.getY(), a.getWidth(), a.getHeight());
                    if(Intersector.overlaps(collisionRect, o)){
                        return true;
                    }
                }
                else if(a instanceof MovingNpc){
                    if(Intersector.overlaps(collisionRect, ((MovingNpc)a).collisionRect)){
                        return true;
                    }
                }
        }
        return false;
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
    
    public Actor collidingActor(){
        for(Actor a : getStage().getActors()){
            if(a instanceof InteractionObject || a instanceof MovingNpc){
                Rectangle p = new Rectangle(getX(), getY(), getWidth(), getHeight());
                Rectangle npc = new Rectangle(a.getX(), a.getY(), a.getWidth(), a.getHeight());
                if(Intersector.overlaps(p, npc)){
                    return a;
                }
            }
        }
        return null;
    }
}
