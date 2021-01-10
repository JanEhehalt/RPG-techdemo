package com.trs.main.fightscreen;

import com.badlogic.gdx.math.Vector2;
import com.trs.main.worldobjects.AnimatedSprite;
import com.trs.main.StaticMath;
import com.trs.main.Stats;

public class Enemy extends FightObject{
	
    boolean move = false;
    boolean isMelee;
    
    public Enemy(float x, float y, AnimatedSprite sprite, Stats stats, int id, boolean isMelee) {
            super(x, y, sprite, stats, id);
            this.isMelee = isMelee;
    }
    
    public void act(FightPlayer player){
        if(getPOI() == null && !move){
        	double distance = StaticMath.calculateDistance(getX(), getY(), player.getX(), player.getY());
        	System.out.println("PLayer pos " + player.getX() + " " + player.getY());
        	System.out.println("Meine pos " + getX() + " " + getY());
        	System.out.println("ich bin " + isMelee + "mit Distanz " + distance);
        	
        	if(isMelee) {
        		if(distance <= 32f) {
            		System.out.println("Jetzt stirbst du *kawumm*");
            		attack(player);
            		    setMoves(getMoves() - 1);
            		    setState(2);
            	}
            	else {
            		
            		float tempX = getX();
            		float tempY = getY();
            		
            		float deltaX = player.getX() - getX();
            		float deltaY = player.getY() - getY();
            		
            		if(Math.abs(deltaX) >= Math.abs(deltaY)) {
            			tempX += (deltaX / Math.abs(deltaX)) * 32;
            		}
            		else {
            			tempY += (deltaY / Math.abs(deltaY)) * 32;
            		}
            		
                            setPOI(new Vector2(tempX, tempY));
                            setMoves(getMoves() - 1);
                    move = true;
            	}
        	}
        	else {
        		if(distance >= 96f && distance <= 150f) {
            		System.out.println("Wilhelm Tell is ein Scheiß gegen mich *surr*");
            		attack(player);
            		    setMoves(getMoves() - 1);
            		    setState(2);
            	}
            	else if(distance < 96f){
            		
            		float tempX = getX();
            		float tempY = getY();
            		
            		float deltaX = player.getX() - getX();
            		float deltaY = player.getY() - getY();
            		
            		if(Math.abs(deltaX) >= Math.abs(deltaY)) {
            			tempX -= (deltaX / Math.abs(deltaX)) * 32;
            		}
            		else {
            			tempY -= (deltaY / Math.abs(deltaY)) * 32;
            		}
            		
                            setPOI(new Vector2(tempX, tempY));
                            setMoves(getMoves() - 1);
                    move = true;
            	}
            	else {
            		float tempX = getX();
            		float tempY = getY();
            		
            		float deltaX = player.getX() - getX();
            		float deltaY = player.getY() - getY();
            		
            		if(Math.abs(deltaX) >= Math.abs(deltaY)) {
            			tempX += (deltaX / Math.abs(deltaX)) * 32;
            		}
            		else {
            			tempY += (deltaY / Math.abs(deltaY)) * 32;
            		}
            		
                            setPOI(new Vector2(tempX, tempY));
                            setMoves(getMoves() - 1);
                    move = true;
            	}
        	}
        }
        else if(move && getPOI() == null){
            setState(2);
            move = false;
        }
    }

}
