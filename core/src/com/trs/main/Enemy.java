package com.trs.main;

import com.badlogic.gdx.math.Vector2;

public class Enemy extends FightObject{
	
    boolean move = false;
    
    public Enemy(float x, float y, AnimatedSprite sprite, Stats stats, int id) {
            super(x, y, sprite, stats, id);
    }
    
    public void act(){
        if(POI == null && !move){
            POI = new Vector2(x, y - 32);
            move = true;
        }
        else if(move && POI == null){
            state = 2;
            move = false;
        }
    }

}
