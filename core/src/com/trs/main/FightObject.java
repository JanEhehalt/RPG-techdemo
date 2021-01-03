package com.trs.main;

import com.badlogic.gdx.math.Vector2;

public abstract class FightObject {
	protected AnimatedSprite sprite;
	protected Stats stats;
	protected int id;
        protected float x;
        protected float y;
        protected Vector2 POI;
        
        // 0: waiting 1: doing action 2: finished action
        protected int state = 0;
	
	public FightObject(float x, float y, AnimatedSprite sprite, Stats stats, int id) {
		this.sprite = sprite;
		this.stats = stats;
		this.id = id;
                this.x = x;
                this.y = y;
	}
        
        void setX(float x) {
            this.x = x;
            this.sprite.setSpritePosition((int)this.x, (int)this.y);
        }
        
        void setY(float y) {
            this.y = y;
            this.sprite.setSpritePosition((int)this.x, (int)this.y);
        } 
}
