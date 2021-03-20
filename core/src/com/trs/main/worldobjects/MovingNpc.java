/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trs.main.worldobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.trs.main.Dialogue;
import com.trs.main.DialogueParser;
import com.trs.main.InformationQuest;
import com.trs.main.Main;
import com.trs.main.Quest;
import com.trs.main.StaticMath;
import com.trs.main.view.UI.Textbox;

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
    
    private AnimatedSprite questBubble;
    
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    
    public MovingNpc(Rectangle area, float xPos, float yPos, int id, int mapId, String texture){
        super();
        setName("npc");
        this.id = id;
        this.mapId = mapId;
        Texture t = new Texture(Gdx.files.internal("textureData/sprites/"+texture));
        
        questBubble = new AnimatedSprite(new Texture("textureData/sprites/questbubble.png"), 32, 32, false);
        questBubble.setRow(0);
        
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
        this.t = new Textbox(nextDialogue.question, nextDialogue.ans);
        
        collisionRect = new Rectangle(xPos + 16, yPos, 32, 16);
        
        setBounds(xPos, yPos, animatedSprite.getSprite().getWidth(), animatedSprite.getSprite().getHeight());
        
    }
    
    public void startDialogue(float xPos, float yPos) {
    	currentlyTalking = true;
    	getStage().addActor(t);
    }
    
    public void startSpecialDialogue(String path, float xPos, float yPos){
        tempSpecialDialogueParser = new DialogueParser(path);
    	currentlyTalking = true;
        specialDialogue = true;
        Dialogue nextDialogue = tempSpecialDialogueParser.firstDialogue();
        this.t = new Textbox(nextDialogue.question, nextDialogue.ans);
    	getStage().addActor(t);
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
                                    Dialogue nextDialogue = parser.firstDialogue();
                                    this.t = new Textbox(nextDialogue.question, nextDialogue.ans);
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
                                    this.t = new Textbox(nextDialogue.question, nextDialogue.ans);
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
	        movement.setAngleRad(StaticMath.calculateAngle(getX() + animatedSprite.getSprite().getWidth()/2, getY() + animatedSprite.getSprite().getHeight()/2, POI.x, POI.y));
	        
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
	        
	        if(StaticMath.calculateDistance(getX() + animatedSprite.getSprite().getWidth()/2, getY() + animatedSprite.getSprite().getHeight()/2, POI.x, POI.y) < 10f) {
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
                        POI = null;
	            }
	        }
	        else if(movementY == 0 && movementX != 0){
	            setX(getX()+movementX);
	            if(collidingWithMapCollisionObject()){
	                setX(getX()-movementX);
                        POI = null;
	            }
	        }
	        else if(movementX != 0 && movementY != 0){
	        	setX(getX()+ (movementX));
	            if(collidingWithMapCollisionObject()){
	                setX(getX() - (movementX));
                        POI = null;
	            }
	
	            setY(getY() + (movementY));
	            if(collidingWithMapCollisionObject()){
	                setY(getY()- (movementY));
                        POI = null;
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
        
        for(Actor a : getStage().getActors()){
            if(a instanceof Player){
                for(Quest quest : ((Player)a).getQuests()){
                    if(quest instanceof InformationQuest){
                        if(((InformationQuest)quest).hasSpecialDialogue(id, mapId)){
                            questBubble.setSpritePosition((int)(getX()-8), (int)(getY() + getHeight() - 16));
                            questBubble.updateAnimation(0.01f);
                            break;
                        }   
                    }
                }
                break;
            }
        }
        
    }

    @Override
    public void draw(Batch batch, float delta) {
        animatedSprite.draw(batch);
        
        for(Actor a : getStage().getActors()){
            if(a instanceof Player){
                for(Quest quest : ((Player)a).getQuests()){
                    if(quest instanceof InformationQuest){
                        if(((InformationQuest)quest).hasSpecialDialogue(id, mapId)){
                            questBubble.draw(batch);
                            break;
                        }   
                    }
                }
                break;
            }
        }
        
        if(Main.gamestate == -1){
            debug(batch);
        }
        
        super.draw(batch, delta); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void debug(Batch batch){
            batch.end();
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            if(POI != null){
                shapeRenderer.setColor(Color.RED);
                shapeRenderer.circle(POI.x, POI.y, 5);
            }
            shapeRenderer.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            if(POI != null){
                shapeRenderer.setColor(Color.ORANGE);
                shapeRenderer.line(getCenterX(), getCenterY(), POI.x, POI.y);
            }
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.rect(getX(), getY(), animatedSprite.getSprite().getWidth(),  animatedSprite.getSprite().getHeight());
            
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.rect(area.x, area.y, area.width, area.height);
            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.line(getCenterX(), getCenterY(), area.x, area.y);
            shapeRenderer.line(getCenterX(), getCenterY(), area.x, area.y + area.height);
            shapeRenderer.line(getCenterX(), getCenterY(), area.x + area.width, area.y);
            shapeRenderer.line(getCenterX(), getCenterY(), area.x + area.width, area.y+area.height);
            
            shapeRenderer.end();
            
            batch.begin();
        }
    
    
    public float getCenterX(){
        return getX()+ animatedSprite.getSprite().getWidth()/2;
    }
    public float getCenterY(){
        return getY()+animatedSprite.getSprite().getHeight()/2;
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
