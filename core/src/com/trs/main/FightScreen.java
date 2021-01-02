package com.trs.main;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public class FightScreen {
	
	Batch batch;
	FightObject[] objects;
	Rectangle[] collisionRects;
	
	// 0: player turn, 1: enemy turn, 2: fight ends
	int state = 0;
	
	public FightScreen(Batch batch, FightObject[] objects, Rectangle[] collisionRects) {
		this.batch = batch;
		this.objects = objects;
		this.collisionRects = collisionRects;
		
		for(int j = 0; j < objects.length-1; j++){
            for(int i = objects.length-1; i >= 0; i--){
                if(i > 0 && objects[i].stats.getInit() > objects[i-1].stats.getInit()){
                    FightObject temp = objects[i-1];
                    objects[i-1] = objects[i];
                    objects[i] = temp;
                }
            }
        }
	}
	
	public void act(float deltatime) {
		
	}
	
	public void draw() {
		batch.begin();
		
		for(FightObject object : objects) {
			object.sprite.draw(batch);
		}
		
		batch.end();
	}
	
}
