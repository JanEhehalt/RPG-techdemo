package com.trs.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Hostile extends Actor {
	
	int id;
	Stats stats;
	AnimatedSprite sprite;
	Rectangle collisionRect;
	Circle attackCircle;
	Circle attentionCircle;
	boolean isMelee;
	
	// 0: normal movement, 1: locked onto Player, 2: attacking
	int movementState;
	
	public Hostile(float xPos, float yPos, int id, Stats stats, String texture, boolean isMelee) {
		
		this.id = id;
		this.stats = stats;
		this.isMelee = isMelee;
		
		Texture tx = new Texture(Gdx.files.internal("textureData/sprites/" + texture));
		sprite = new AnimatedSprite(tx, 64, 64, true);
		
		collisionRect = new Rectangle(xPos + 16, yPos, 32, 16);
		attackCircle = new Circle(xPos + 16, yPos, 100f);
		attentionCircle = new Circle(xPos + 16, yPos, 300f);
		
		movementState = 0;
		
		setX(xPos);
		setY(yPos);
	}
	
	@Override
	public void act(float deltatime) {
		sprite.updateAnimation(deltatime);
		
		if(movementState == 0) {
			for(Actor a : getStage().getActors()) {
				if(a instanceof Player) {
					if(Intersector.overlaps(attentionCircle, ((Player) a).collisionRect)) {
						movementState = 1;
					}
				}
			}
		}
		
		if(movementState == 1) {
			for(Actor a : getStage().getActors()) {
				if(a instanceof Player) {
					if(Intersector.overlaps(attackCircle, ((Player) a).collisionRect)) {
						movementState = 2;
					}
					if(!Intersector.overlaps(attentionCircle, ((Player) a).collisionRect)) {
						movementState = 0;
					}
					

					Vector2 POI = new Vector2(a.getX(), a.getY());
		            float speed = 2;
		            
		            Vector2 movement = new Vector2(speed,0);
		            movement.setAngleRad(StaticMath.calculateAngle(getX(), getY(), POI.x, POI.y));
		            int facing;
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
		            
		            if(StaticMath.calculateDistance(getX(), getY(), POI.x, POI.y) < 1f) {
		                movement.x = 0;
		                movement.y = 0;
		            }       
		            
		            setX(getX() + movement.x);
		            setY(getY() + movement.y);
		            
		            int animationRow = 0;
		            if(movement.x != 0 || movement.y != 0) {
		                    animationRow = 8;
		            }
		            
		            sprite.setRow(animationRow + facing);
				}
			}
		}
		
		if(movementState == 2) {
			Main.gamestate = 2;
			//System.out.println("Kämpfe mit mir du Spast!");
		}
	}
	
	@Override
	public void draw(Batch batch, float deltatime) {
		sprite.draw(batch);
	}
	
	@Override
    protected void positionChanged() {
        sprite.setSpritePosition((int)getX(), (int)getY());
        collisionRect = new Rectangle(getX() + 16, getY(), 32, 16);
        attackCircle = new Circle(getX() + 16, getY(), 100f);
		attentionCircle = new Circle(getX() + 16, getY(), 300f);
        super.positionChanged(); //To change body of generated methods, choose Tools | Templates.
    }
}
