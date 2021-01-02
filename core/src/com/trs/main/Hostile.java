package com.trs.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Hostile extends Actor {
	
	int id;
	Stats stats;
	AnimatedSprite sprite;
	Rectangle collisionRect;
	
	public Hostile(float xPos, float yPos, int id, Stats stats, String texture) {
		
		this.id = id;
		this.stats = stats;
		
		Texture tx = new Texture(Gdx.files.internal("textureData/sprites/" + texture));
		sprite = new AnimatedSprite(tx, 64, 64, true);
		
		collisionRect = new Rectangle(xPos + 16, yPos, 32, 16);
		
		setX(xPos);
		setY(yPos);
	}
	
	@Override
	public void act(float deltatime) {
		sprite.updateAnimation(deltatime);
	}
	
	@Override
	public void draw(Batch batch, float deltatime) {
		sprite.draw(batch);
	}
	
	@Override
    protected void positionChanged() {
        sprite.setSpritePosition((int)getX(), (int)getY());
        collisionRect = new Rectangle(getX() + 16, getY(), 32, 16);
        super.positionChanged(); //To change body of generated methods, choose Tools | Templates.
    }
}
