package com.trs.main.fightscreen;

import com.badlogic.gdx.math.Vector2;
import com.trs.main.worldobjects.AnimatedSprite;
import com.trs.main.Stats;

public abstract class FightObject {
	private AnimatedSprite sprite;
	private Stats stats;
	private int id;
    private float x;
    private float y;
    private Vector2 POI;
    private int moves;
    private int maxMoves;  
    
    // 0: waiting 1: doing action 2: finished action
    private int state = 0;
	
	public FightObject(float x, float y, AnimatedSprite sprite, Stats stats, int id) {
            this.sprite = sprite;
            this.stats = stats;
            this.id = id;
            this.x = x;
            this.y = y;
            maxMoves = 2;
            moves = maxMoves;
	}
        
    void setX(float x) {
        this.x = x;
        this.getSprite().setSpritePosition((int)this.x, (int)this.getY());
    }
    
    void setY(float y) {
        this.y = y;
        this.getSprite().setSpritePosition((int)this.getX(), (int)this.y);
    }
    
    void attack(FightObject o) {
    	if(o != null) {
            o.getStats().setHp(o.getStats().getHp() - getStats().getAtk());
    	}
    }
    
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    
    public Stats getStats(){
        return stats;
    }
    public void setStats(Stats stats){
        this.stats = stats;
    }
    
    public void startAction() {
    	setState(1);
    	setMoves(getMaxMoves());
    }

    /**
     * @return the sprite
     */
    public AnimatedSprite getSprite() {
        return sprite;
    }

    /**
     * @param sprite the sprite to set
     */
    public void setSprite(AnimatedSprite sprite) {
        this.sprite = sprite;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the POI
     */
    public Vector2 getPOI() {
        return POI;
    }

    /**
     * @param POI the POI to set
     */
    public void setPOI(Vector2 POI) {
        this.POI = POI;
    }

    /**
     * @return the moves
     */
    public int getMoves() {
        return moves;
    }

    /**
     * @param moves the moves to set
     */
    public void setMoves(int moves) {
        this.moves = moves;
    }

    /**
     * @return the maxMoves
     */
    public int getMaxMoves() {
        return maxMoves;
    }

    /**
     * @param maxMoves the maxMoves to set
     */
    public void setMaxMoves(int maxMoves) {
        this.maxMoves = maxMoves;
    }

    /**
     * @return the state
     */
    public int getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(int state) {
        this.state = state;
    }
    
}
