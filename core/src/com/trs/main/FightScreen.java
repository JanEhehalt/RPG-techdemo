package com.trs.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class FightScreen {
	
        final int gridWidth = 20;
        final int gridHeight = 12;
    
	Batch batch;
        ShapeRenderer renderer;
	FightObject[] objects;
	Rectangle[] collisionRects;
	
        Vector2 gridPos;
        
	// 0: player turn, 1: enemy turn, 2: fight ends
	int state = 0;
	
	public FightScreen(Batch batch, FightObject[] objects, Rectangle[] collisionRects, float camX, float camY) {
		this.batch = batch;
		this.objects = objects;
		this.collisionRects = collisionRects;
                this.renderer = new ShapeRenderer();
                
                gridPos = new Vector2();
                
                gridPos.x = (float)(Math.ceil((double)(camX-Main.CAMERA_WIDTH/2)/32.0) * 32.0) + 64;
                gridPos.y = (float)(Math.ceil((double)(camY-Main.CAMERA_HEIGHT/2)/32.0) * 32.0) + 32;
                
               // gridPos.x = camX-Main.CAMERA_WIDTH/2;
               // gridPos.y = camY-Main.CAMERA_HEIGHT/2;
		
                /*
		for(int j = 0; j < objects.length-1; j++){
                    for(int i = objects.length-1; i >= 0; i--){
                        if(i > 0 && objects[i].stats.getInit() > objects[i-1].stats.getInit()){
                            FightObject temp = objects[i-1];
                            objects[i-1] = objects[i];
                            objects[i] = temp;
                        }
                    }
                }*/
	}
	
	public void act(float deltatime) {
		
	}
	
	public void draw() {
                
                renderer.setProjectionMatrix(batch.getProjectionMatrix());
                renderer.setColor(0.6f, 0.6f, 0.6f, 1f);
                Gdx.gl.glEnable(GL20.GL_BLEND);
                Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                renderer.begin(ShapeRenderer.ShapeType.Line);
                
                for(int i = 0; i < gridWidth; i++){
                    for(int j = 0; j < gridHeight; j++){
                        Rectangle r = new Rectangle(gridPos.x+i*32, gridPos.y+j*32, 32, 32);
                        if(!collidingMapObject(r)){
                            renderer.rect(r.x, r.y, r.width, r.height);
                        }
                    }
                }
                
                renderer.end();
                Gdx.gl.glDisable(GL20.GL_BLEND);
		
		batch.begin();
                /*
		for(FightObject object : objects) {
			object.sprite.draw(batch);
		}
		*/
		batch.end();
	}
        
        public boolean collidingMapObject(Rectangle r){
            for(Rectangle rect : collisionRects){
                if(Intersector.overlaps(r, rect)){
                    return true;
                }
            }
            return false;
        }
	
}
