package com.trs.main;

import com.badlogic.gdx.math.Vector2;

public class Enemy extends FightObject{
	
    boolean move = false;
    boolean isMelee;
    
    public Enemy(float x, float y, AnimatedSprite sprite, Stats stats, int id, boolean isMelee) {
            super(x, y, sprite, stats, id);
            this.isMelee = isMelee;
    }
    
    public void act(FightPlayer player){
        if(POI == null && !move){
        	double distance = StaticMath.calculateDistance(x, y, player.x, player.y);
        	
        	if(isMelee) {
        		if(distance <= 32f) {
            		System.out.println("Jetzt stirbst du *kawumm*");
            		attack(player);
            		moves--;
            		state = 2;
            	}
            	else {
            		
            		float tempX = x;
            		float tempY = y;
            		
            		float deltaX = player.x - x;
            		float deltaY = player.y - y;
            		
            		if(Math.abs(deltaX) >= Math.abs(deltaY)) {
            			tempX += (deltaX / Math.abs(deltaX)) * 32;
            		}
            		else {
            			tempY += (deltaY / Math.abs(deltaY)) * 32;
            		}
            		
                    POI = new Vector2(tempX, tempY);
                    moves--;
                    move = true;
            	}
        	}
        	else {
        		if(distance >= 96f && distance <= 150f) {
            		System.out.println("Wilhelm Tell is ein Scheiß gegen mich *surr*");
            		attack(player);
            		moves--;
            		state = 2;
            	}
            	else if(distance < 96f){
            		
            		float tempX = x;
            		float tempY = y;
            		
            		float deltaX = player.x - x;
            		float deltaY = player.y - y;
            		
            		if(Math.abs(deltaX) >= Math.abs(deltaY)) {
            			tempX += -(deltaX / Math.abs(deltaX)) * 32;
            		}
            		else {
            			tempY += -(deltaY / Math.abs(deltaY)) * 32;
            		}
            		
                    POI = new Vector2(tempX, tempY);
                    moves--;
                    move = true;
            	}
            	else {
            		float tempX = x;
            		float tempY = y;
            		
            		float deltaX = player.x - x;
            		float deltaY = player.y - y;
            		
            		if(Math.abs(deltaX) >= Math.abs(deltaY)) {
            			tempX += (deltaX / Math.abs(deltaX)) * 32;
            		}
            		else {
            			tempY += (deltaY / Math.abs(deltaY)) * 32;
            		}
            		
                    POI = new Vector2(tempX, tempY);
                    moves--;
                    move = true;
            	}
        	}
        }
        else if(move && POI == null){
            state = 2;
            move = false;
        }
    }

}
