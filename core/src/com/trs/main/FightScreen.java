package com.trs.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
        
        FightDialogue fightDialogue;
	
        Vector2 gridPos;
        
	// 0: positioning all Objects on the grid 1: player turn, 2: enemy turn, 3: fight ends
	int state = 0;
	
	public FightScreen(Batch batch, FightObject[] objects, Rectangle[] collisionRects, float camX, float camY) {
		this.batch = batch;
		this.objects = objects;
		this.collisionRects = collisionRects;
                this.renderer = new ShapeRenderer();
                this.fightDialogue = new FightDialogue(camX, camY);
                
                gridPos = new Vector2();
                
                gridPos.x = (float)(Math.ceil((double)(camX-Main.CAMERA_WIDTH/2)/32.0) * 32.0) + 64;
                gridPos.y = (float)(Math.ceil((double)(camY-Main.CAMERA_HEIGHT/2)/32.0) * 32.0) + 32;
                
                
               // SORTING OBJECTS BY INITIATIVE STAT
                /*
		for(int j = 0; j < objects.length-1; j++){
                    for(int i = objects.length-1; i >= 0; i--){
                        if(i > 0 && objects[i].stats.getInit() > objects[i-1].stats.getInit()){
                            FightObject temp = objects[i-1];
                            objects[i-1] = objects[i];
                            objects[i] = temp;
                        }
                    }
                }
               */
	}
	
	public void act(float deltatime) {
                if(state == 0){
                    boolean finished = true;
                    for(FightObject object : objects){
                        Vector2 POI = new Vector2((int)(Math.ceil((double)(object.x)/32.0) * 32.0) - 16, (int)(Math.ceil((double)(object.y)/32.0) * 32.0));
                        float speed = 2.5f;
                        
                        if(Math.abs(Vector2.dst(object.x, object.y, POI.x, POI.y)) < 3f && Math.abs(Vector2.dst(object.x, object.y, POI.x, POI.y)) != 0) {
                            speed = Math.abs(Vector2.dst(object.x, object.y, POI.x, POI.y));
                        } 
                        
                        
                        Vector2 movement = new Vector2(speed,0);
                        movement.setAngleRad(StaticMath.calculateAngle(object.x, object.y, POI.x, POI.y));
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
                        
                        if(StaticMath.calculateDistance(object.x, object.y, POI.x, POI.y, movement.angleRad()) == 0) {
                            movement.x = 0;
                            movement.y = 0;
                        }       
                        
                        object.setX(object.x + movement.x);
                        object.setY(object.y + movement.y);
                        
                        int animationRow = 0;
                        if(movement.x != 0 || movement.y != 0) {
                                animationRow = 8;
                        }
                        object.sprite.setRow(animationRow + facing);
                        
                        if(movement.x != 0 || movement.y != 0){
                            finished = false;
                        }
                    }
                    if(finished){
                        state = 1;
                    }
                }
                else if(state == 1){
                    if(Gdx.input.isKeyJustPressed(Input.Keys.W)){
                        if(getPlayer().POI == null){
                            getPlayer().POI = new Vector2(getPlayer().x, getPlayer().y + 32);
                            System.out.println("W");
                        }
                    }
                    if(Gdx.input.isKeyJustPressed(Input.Keys.A)){
                        if(getPlayer().POI == null){
                            getPlayer().POI = new Vector2(getPlayer().x-32, getPlayer().y);
                            System.out.println("A");
                        }
                    }
                    if(Gdx.input.isKeyJustPressed(Input.Keys.S)){
                        if(getPlayer().POI == null){
                            getPlayer().POI = new Vector2(getPlayer().x, getPlayer().y - 32);
                            System.out.println("S");
                        }
                    }
                    if(Gdx.input.isKeyJustPressed(Input.Keys.D)){
                        if(getPlayer().POI == null){
                            getPlayer().POI = new Vector2(getPlayer().x + 32, getPlayer().y);
                            System.out.println("D");
                        }
                    }
                    
                }
                gotoPOI();
		for(FightObject object : objects) {
			object.sprite.updateAnimation(deltatime);
		}
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
                fightDialogue.draw(batch);
                Gdx.gl.glDisable(GL20.GL_BLEND);
		
		batch.begin();
                
		for(FightObject object : objects) {
                    object.sprite.draw(batch);
		}
		
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
    
    public void nuke() {
    	for(FightObject object : objects) {
    		if(object instanceof Enemy) {
    			object.stats.setHp(-5);
    		}
    	}
    }
    
    public FightPlayer getPlayer(){
        for(FightObject object : objects){
            if(object instanceof FightPlayer){
                return (FightPlayer) object;
            }
        }
        System.out.println("großes Problem hahgaeu9ihgbidesrufhgred");
        return null;
    }
    
    public void gotoPOI(){
        for(FightObject object : objects){
            if(object.POI != null){
                //object.POI = new Vector2((int)(Math.ceil((double)(object.x)/32.0) * 32.0) - 16, (int)(Math.ceil((double)(object.y)/32.0) * 32.0));
                float speed = 3f;

                if(StaticMath.calculateDistance(object.x, object.y, object.POI.x, object.POI.y) < 3f && StaticMath.calculateDistance(object.x, object.y, object.POI.x, object.POI.y) != 0) {
                    speed = Math.abs(Vector2.dst(object.x, object.y, object.POI.x, object.POI.y));
                } 


                Vector2 movement = new Vector2(speed,0);
                movement.setAngleRad(StaticMath.calculateAngle(object.x, object.y, object.POI.x, object.POI.y));
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

                if((int)object.x == (int)object.POI.x && (int)object.y == (int)object.POI.y) {
                    movement.x = 0;
                    movement.y = 0;
                }       

                object.setX(object.x + movement.x);
                object.setY(object.y + movement.y);

                int animationRow = 0;
                if(movement.x != 0 || movement.y != 0) {
                        animationRow = 8;
                }
                else{
                    object.POI = null;
                }
                object.sprite.setRow(animationRow + facing);
            }
        }
    }
	
}
