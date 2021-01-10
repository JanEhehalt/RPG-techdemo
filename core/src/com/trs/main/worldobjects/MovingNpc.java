/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trs.main.worldobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.trs.main.Dialogue;
import com.trs.main.DialogueParser;
import com.trs.main.Main;
import com.trs.main.StaticMath;
import com.trs.main.Textbox;

/**
 *
 * @author Jan
 */
public class MovingNpc extends Actor{
    
    public final int mapId;
    public final int id;
    
    public static final float SQRT2 = 1.414f;
    Textbox t;
    Rectangle area;
    Rectangle collisionRect;
    float speed;
    private AnimatedSprite animatedSprite;
    int facing;
    float movementX;
    float movementY;
    
    boolean currentlyTalking;
    
    boolean specialDialogue;
    
    DialogueParser parser;
    DialogueParser tempSpecialDialogueParser;
    
    Vector2 POI;
    
    String dialoguePath;
    
    public MovingNpc(Rectangle area, float xPos, float yPos, int id, int mapId, String texture){
        setName("npc");
        this.id = id;
        this.mapId = mapId;
        Texture t = new Texture(Gdx.files.internal("textureData/sprites/"+texture));
        
        currentlyTalking = false;
        specialDialogue = false;
        animatedSprite = new AnimatedSprite(t, 64, 64, true);
        animatedSprite.setRow(0);
        collisionRect = new Rectangle(xPos + 16, yPos, 32, 48);
        this.area = area;
        speed = 1f;
        
        movementX = 0;
        movementY = 0;
        
        dialoguePath = "mapData/map"+mapId+"/npcs/"+id+"/dialogue.txt";
        parser = new DialogueParser(dialoguePath);
        Dialogue nextDialogue = parser.firstDialogue();
        this.t = new Textbox(nextDialogue.question, nextDialogue.ans, getX()+getWidth()/2, getY()+getHeight()/2);
        
        collisionRect = new Rectangle(xPos + 16, yPos, 32, 16);
        
        setBounds(xPos, yPos, animatedSprite.getSprite().getWidth(), animatedSprite.getSprite().getHeight());
    }
    
    public void startDialogue(float xPos, float yPos) {
    	currentlyTalking = true;
    	getStage().addActor(new Textbox(t, xPos, yPos));
    }
    
    public void startSpecialDialogue(String path, float xPos, float yPos){
        tempSpecialDialogueParser = new DialogueParser(path);
    	currentlyTalking = true;
        specialDialogue = true;
        Dialogue nextDialogue = tempSpecialDialogueParser.firstDialogue();
        this.t = new Textbox(nextDialogue.question, nextDialogue.ans, getX()+getWidth()/2, getY()+getHeight()/2);
    	getStage().addActor(new Textbox(t, xPos, yPos));
    }
    
    @Override
    protected void positionChanged() {
        animatedSprite.setSpritePosition((int)getX(), (int)getY());
        collisionRect = new Rectangle(getX() + 16, getY(), 32, 16);
        super.positionChanged();
    }
    
    @Override
    public void act(float delta) {
        if(Main.gamestate == 1 && currentlyTalking) {
            animatedSprite.setRow(facing);

            if(currentlyTalking) {
                for(Actor a : getStage().getActors()) {
                    if(a instanceof Textbox) {
                        if(((Textbox) a).getState() == 2) {
                            if(!specialDialogue){
                                int answer = ((Textbox) a).getSelectedAsw();
                                Dialogue newDialogue = parser.nextDialogue(answer + 1);

                                if(newDialogue == null) {
                                    currentlyTalking = false;
                                    parser = new DialogueParser(dialoguePath);
                                }
                                else {
                                    ((Textbox)a).update(newDialogue);
                                }
                            }
                            else{
                                int answer = ((Textbox) a).getSelectedAsw();
                                Dialogue newDialogue = tempSpecialDialogueParser.nextDialogue(answer + 1);

                                if(newDialogue == null) {
                                    currentlyTalking = false;
                                    specialDialogue = false;
                                    parser = new DialogueParser(dialoguePath);
                                    Dialogue nextDialogue = parser.firstDialogue();
                                    this.t = new Textbox(nextDialogue.question, nextDialogue.ans, getX()+getWidth()/2, getY()+getHeight()/2);
                                    tempSpecialDialogueParser = null;
                                }
                                else {
                                    ((Textbox)a).update(newDialogue);
                                }
                            }
                        }
                    }
                }
            }
        }
        else {
        	if(POI == null || Math.random() < 0.01f){
	            POI = new Vector2(area.getX() + ((float) Math.random() * (float) area.getWidth()), area.getY() + ((float) Math.random() * (float) area.getHeight()));
	        }
	        Vector2 movement = new Vector2(speed,0);
	        movement.setAngleRad(StaticMath.calculateAngle(getX(), getY(), POI.x, POI.y));
	        
	        if(movement.angleDeg() < 135 && movement.angleDeg() >= 45) {
	        	facing = 0;
	        }
	        else if(movement.angleDeg() >= 135 && movement.angleDeg() < 225) {
	        	facing = 1;
	        }
	        else if(movement.angleDeg() >= 225 && movement.angleDeg() < 315) {
	        	facing = 2;
	        }
	        else {
	        	facing = 3;
	        }
	        
	        if(StaticMath.calculateDistance(getX(), getY(), POI.x, POI.y) < 10f) {
	        	movementX = 0;
	        	movementY = 0;
	        }
	        else {
	            movementX = movement.x;
	            movementY = movement.y;
	        }
	        
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
	        
	        movementX = 0;
	        movementY = 0;
        }
        
        animatedSprite.updateAnimation(delta);
        
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        animatedSprite.draw(batch);
        super.draw(batch, parentAlpha); //To change body of generated methods, choose Tools | Templates.
    }
    
     public boolean collidingWithMapCollisionObject(){
        for(Actor a : getStage().getActors()){
                if(a instanceof MapCollisionObject){
                    Rectangle o = new Rectangle(a.getX(), a.getY(), a.getWidth(), a.getHeight());
                    if(Intersector.overlaps(collisionRect, o)){
                        return true;
                    }
                }
                else if(a instanceof MovingNpc && a != this){
                    if(Intersector.overlaps(collisionRect, ((MovingNpc)a).collisionRect)){
                        return true;
                    }
                }
                else if(a instanceof Player){
                    if(Intersector.overlaps(collisionRect, ((Player)a).getCollisionRect())){
                        return true;
                    }
                }
        }
        return false;
    } 
    
    public Textbox getTextbox(){
        return t;
    }
    
}
