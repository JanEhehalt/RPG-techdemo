package com.trs.main;

public abstract class FightObject {
	protected AnimatedSprite sprite;
	protected Stats stats;
	protected int id;
        protected float x;
        protected float y;
	
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
