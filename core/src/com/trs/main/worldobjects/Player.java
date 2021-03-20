/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trs.main.worldobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.trs.main.InformationQuest;
import com.trs.main.Main;
import com.trs.main.Quest;
import com.trs.main.Stats;
import java.util.ArrayList;
/**
 * 
 *
 * @author Jan
 */
public class Player extends Actor{
    
    private static float SQRT2 = 1.414f;
    
    private Texture t;
    private AnimatedSprite playerSprite;
    private float movementX = 0;
    private float movementY = 0;
    private float speed = 3f;
    private float velocity = 0.5f;
    // 0: up, 1: left, 2: down, 3: right
    private int facing = 0;
    
    private ArrayList<Quest> quests;
    
    private Rectangle collisionRect;
    
    private Group questGroup;
    
    private Stats stats;
    
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    
    public Player(int xPos, int yPos){
        setName("player");
        t = new Texture(Gdx.files.internal("textureData/sprites/player.png"));
        playerSprite = new AnimatedSprite(getT(), 64, 64, true);
        playerSprite.setRow(0);
        collisionRect = new Rectangle(xPos + 16, yPos, 32, 16);
        setBounds(xPos, yPos, playerSprite.getSprite().getWidth(), playerSprite.getSprite().getHeight());
        quests = new ArrayList<>();
        
        stats = new Stats();
        
        //TEST QUESTS
        int[] n = {1, 1};        
        int[] m = {1, 0};
        quests.add(new InformationQuest(0, "Sprich mit Folgenden NPCs: (Id, mapId, schonGereded?) !Reihenfolge wichtig!", m, n, true, "gute Quest"));
        quests.add(new InformationQuest(1, "jajajaj nicenicenice", m, n, false, "jojo"));
    }

    @Override
    protected void positionChanged() {
        getPlayerSprite().setSpritePosition((int)getX(), (int)getY());
        setCollisionRect(new Rectangle(getX() + 16, getY(), 32, 16));
        super.positionChanged(); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    @Override
    public void act(float delta) {
        
        // TEST QUESTS
        for(Quest quest : getQuests()){
            //quest.print();
            //System.out.println();
        }
        //System.out.println();
        
        // QUEST HANDLING
        for(Quest quest : getQuests()){
            quest.updateQuest();
        }
        
        // PLAYER ACTING
    	if(Main.gamestate == 0) {
            if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
                setSpeed(9);
            }
            else setSpeed(3);
            if(Gdx.input.isKeyPressed(Input.Keys.D)){
                setMovementX(getSpeed());
                setFacing(3);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.A)){
                setMovementX(-getSpeed());
                setFacing(1);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.W)){
                setMovementY(getSpeed());
                setFacing(0);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.S)){
                setMovementY(-getSpeed());
                setFacing(2);
            }
            
            if(Gdx.input.isKeyPressed(Input.Keys.NUM_1)){
                setMovementY(-8);
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
                        for(Quest quest : getQuests()){
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
                        
                        setMovementX(0);
                        setMovementY(0);
                    }
                    else if(a instanceof InteractionObject) {
                    	Main.gamestate = 1;
                        ((InteractionObject)a).startDialogue(getX()+32, getY()+32);
                        setMovementX(0);
                        setMovementY(0);
                    }
                }
            }
    	}
    	else if(Main.gamestate == 1) {
    		// Input handled by invoked textbox
    	}
        
        // MOVEMENT HANDLING
        if(getMovementX() == 0 && getMovementY() == 0){
        	
        }
        else if(getMovementX() == 0 && getMovementY() != 0){
            setY(getY()+getMovementY());
            if(collidingWithMapCollisionObject()){
                setY(getY()-getMovementY());
            }
        }
        else if(getMovementY() == 0 && getMovementX() != 0){
            setX(getX()+getMovementX());
            if(collidingWithMapCollisionObject()){
                setX(getX()-getMovementX());
            }
        }
        else if(getMovementX() != 0 && getMovementY() != 0){
        	setX(getX()+ (getMovementX() / getSQRT2()));
            if(collidingWithMapCollisionObject()){
                setX(getX() - (getMovementX() / getSQRT2()));
            }

            setY(getY() + (getMovementY() / getSQRT2()));
            if(collidingWithMapCollisionObject()){
                setY(getY()- (getMovementY() / getSQRT2()));
            }
        }
        velocity(getVelocity());
        
        // ANIMATION HANDLING
        int animationRow = 0;
        if(getMovementX() != 0 || getMovementY() != 0) {
        	animationRow = 8;
        }		
        getPlayerSprite().setRow(animationRow + getFacing());
        getPlayerSprite().updateAnimation(delta);
        
        super.act(delta); //To change body of generated methods, choose Tools | Templates.

        
        
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        getPlayerSprite().draw(batch);
        if(Main.gamestate == -1){
            debug(batch);
        }
        super.draw(batch, parentAlpha); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void debug(Batch batch){
            batch.end();
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            
            shapeRenderer.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.rect(getX(), getY(), playerSprite.getSprite().getWidth(),  playerSprite.getSprite().getHeight());
            shapeRenderer.end();
            
            batch.begin();
    }

    @Override
    public boolean remove() {
        return super.remove(); //To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean collidingWithMapCollisionObject(){
        for(Actor a : getStage().getActors()){
                if(a instanceof MapCollisionObject){
                    Rectangle o = new Rectangle(a.getX(), a.getY(), a.getWidth(), a.getHeight());
                    if(Intersector.overlaps(getCollisionRect(), o)){
                        return true;
                    }
                }
                else if(a instanceof MovingNpc){
                    if(Intersector.overlaps(getCollisionRect(), ((MovingNpc)a).collisionRect)){
                        return true;
                    }
                }
        }
        return false;
    }   
    
    // Slowing the players movement by velocity
    public void velocity(float velocity){
        if(getMovementX() > 0){
            setMovementX(getMovementX() - velocity);
            if(getMovementX() < 0){
                setMovementX(0);
            }
        }
        else if(getMovementX() < 0){
            setMovementX(getMovementX() + velocity);
            if(getMovementX() > 0){
                setMovementX(0);
            }
        }
        
        if(getMovementY() > 0){
            setMovementY(getMovementY() - velocity);
            if(getMovementY() < 0){
                setMovementY(0);
            }
        }
        else if(getMovementY() < 0){
            setMovementY(getMovementY() + velocity);
            if(getMovementY() > 0){
                setMovementY(0);
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

    /**
     * @return the SQRT2
     */
    public static float getSQRT2() {
        return SQRT2;
    }

    /**
     * @param aSQRT2 the SQRT2 to set
     */
    public static void setSQRT2(float aSQRT2) {
        SQRT2 = aSQRT2;
    }

    /**
     * @return the t
     */
    public Texture getT() {
        return t;
    }

    /**
     * @param t the t to set
     */
    public void setT(Texture t) {
        this.t = t;
    }

    /**
     * @return the playerSprite
     */
    public AnimatedSprite getPlayerSprite() {
        return playerSprite;
    }

    /**
     * @param playerSprite the playerSprite to set
     */
    public void setPlayerSprite(AnimatedSprite playerSprite) {
        this.playerSprite = playerSprite;
    }

    /**
     * @return the movementX
     */
    public float getMovementX() {
        return movementX;
    }

    /**
     * @param movementX the movementX to set
     */
    public void setMovementX(float movementX) {
        this.movementX = movementX;
    }

    /**
     * @return the movementY
     */
    public float getMovementY() {
        return movementY;
    }

    /**
     * @param movementY the movementY to set
     */
    public void setMovementY(float movementY) {
        this.movementY = movementY;
    }

    /**
     * @return the speed
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * @return the velocity
     */
    public float getVelocity() {
        return velocity;
    }

    /**
     * @param velocity the velocity to set
     */
    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    /**
     * @return the facing
     */
    public int getFacing() {
        return facing;
    }

    /**
     * @param facing the facing to set
     */
    public void setFacing(int facing) {
        this.facing = facing;
    }

    /**
     * @return the quests
     */
    public ArrayList<Quest> getQuests() {
        return quests;
    }

    /**
     * @param quests the quests to set
     */
    public void setQuests(ArrayList<Quest> quests) {
        this.quests = quests;
    }

    /**
     * @return the collisionRect
     */
    public Rectangle getCollisionRect() {
        return collisionRect;
    }

    /**
     * @param collisionRect the collisionRect to set
     */
    public void setCollisionRect(Rectangle collisionRect) {
        this.collisionRect = collisionRect;
    }

    /**
     * @return the questGroup
     */
    public Group getQuestGroup() {
        return questGroup;
    }

    /**
     * @param questGroup the questGroup to set
     */
    public void setQuestGroup(Group questGroup) {
        this.questGroup = questGroup;
    }

    /**
     * @return the stats
     */
    public Stats getStats() {
        return stats;
    }

    /**
     * @param stats the stats to set
     */
    public void setStats(Stats stats) {
        this.stats = stats;
    }
    
    
}
