package com.trs.main;

public class Enemy extends FightObject{
	
    public Enemy(float x, float y, AnimatedSprite sprite, Stats stats, int id, boolean isPlayer) {
            super(x, y, sprite, stats, id, isPlayer);
    }

}
