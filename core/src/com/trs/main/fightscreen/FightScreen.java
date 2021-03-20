package com.trs.main.fightscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.trs.main.Main;
import com.trs.main.StaticMath;

public class FightScreen {
	
        final int gridWidth = 18;
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
            this.fightDialogue = new FightDialogue();

            gridPos = new Vector2();

            gridPos.x = (float)(Math.ceil((double)(camX-Main.CAMERA_WIDTH/2)/32.0) * 32.0) + 64;
            gridPos.y = (float)(Math.ceil((double)(camY-Main.CAMERA_HEIGHT/2)/32.0) * 32.0) + 32;


            // SORTING OBJECTS BY INITIATIVE STAT

            for(int j = 0; j < objects.length-1; j++){
                for(int i = objects.length-1; i >= 0; i--){
                    if(i > 0 && objects[i].getStats().getInit() > objects[i-1].getStats().getInit()){
                        FightObject temp = objects[i-1];
                        objects[i-1] = objects[i];
                        objects[i] = temp;
                    }
                }
            }
            objects[0].startAction();
	}
	
	public void act(float deltatime) {
            System.out.println(getPlayer().getState());
                if(state == 0){
                    boolean finished = true;
                    for(FightObject object : objects){
                        if(object.getX() % 32 != 0 || object.getY() % 32 != 0){
                            object.setPOI(getNearestGridTile(object.getX(), object.getY()));
                        }
                    }
                    gotoPOI();
                    for(FightObject object : objects){
                        if(object.getPOI() != null){
                            finished = false;
                            break;
                        }
                    }
                    if(finished){
                        state = 1;
                    }
                }
                else if(state == 1){
                    for(int i = 0; i < objects.length; i++){
                        if(objects[i].getState() == 1){
                            if(objects[i] instanceof FightPlayer){
                                if(Gdx.input.isKeyJustPressed(Input.Keys.W)){
                                    if(objects[i].getPOI() == null){
                                        objects[i].setPOI(new Vector2(objects[i].getX(), objects[i].getY() + 32));
                                        System.out.println("W");
                                    }
                                }
                                if(Gdx.input.isKeyJustPressed(Input.Keys.A)){
                                    if(objects[i].getPOI() == null){
                                        objects[i].setPOI(new Vector2(objects[i].getX() - 32, objects[i].getY()));
                                        System.out.println("A");
                                    }
                                }
                                if(Gdx.input.isKeyJustPressed(Input.Keys.S)){
                                    if(objects[i].getPOI() == null){
                                        objects[i].setPOI(new Vector2(objects[i].getX(), objects[i].getY() - 32));
                                        System.out.println("S");
                                    }
                                }
                                if(Gdx.input.isKeyJustPressed(Input.Keys.D)){
                                    if(objects[i].getPOI() == null){
                                        objects[i].setPOI(new Vector2(objects[i].getX() + 32, objects[i].getY()));
                                        System.out.println("D");
                                    }
                                }
                                if(Gdx.input.isKeyJustPressed(Input.Keys.F)){
                                        System.out.println("F");
                                        objects[i].setState(2);
                                }
                            }
                            else if(objects[i] instanceof Enemy){
                            	for(FightObject player : objects) {
                            		if(player instanceof FightPlayer) {
                            			((Enemy)objects[i]).act((FightPlayer) player);
                            			break;
                            		}
                            	}
                            }
                        }
                        else if(objects[i].getState() == 2){
                            objects[i].setState(0);
                            if(i == objects.length-1){
                                objects[0].startAction();
                            }
                            else{
                                objects[i+1].startAction();
                            }
                        }
                    }
                    
                    
                    
                }
                else if(state == 2){
                
                }
                gotoPOI();
		for(FightObject object : objects) {
			object.getSprite().updateAnimation(deltatime);
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
                    object.getSprite().draw(batch);
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
    			object.getStats().setHp(-5);
    		}
    	}
    }
    
    public FightPlayer getPlayer(){
        for(FightObject object : objects){
            if(object instanceof FightPlayer){
                return (FightPlayer) object;
            }
        }
        System.out.println("gro�es Problem hahgaeu9ihgbidesrufhgred");
        return null;
    }
    
    public void setState(int state){
        this.state = state;
    }
    public int getState(){
        return state;
    }
    
    public FightObject[] getObjects(){
        return objects;
    }
    
    
    
    public void gotoPOI(){
        for(FightObject object : objects){
            if(object.getPOI() != null){
                //object.POI = new Vector2((int)(Math.ceil((double)(object.x)/32.0) * 32.0) - 16, (int)(Math.ceil((double)(object.y)/32.0) * 32.0));
                float speed = 3f;

                if(StaticMath.calculateDistance(object.getX(), object.getY(), object.getPOI().x, object.getPOI().y) < 3f && StaticMath.calculateDistance(object.getX(), object.getY(), object.getPOI().x, object.getPOI().y) != 0) {
                    speed = Math.abs(Vector2.dst(object.getX(), object.getY(), object.getPOI().x, object.getPOI().y));
                } 


                Vector2 movement = new Vector2(speed,0);
                movement.setAngleRad(StaticMath.calculateAngle(object.getX(), object.getY(), object.getPOI().x, object.getPOI().y));
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

                if((int)object.getX() == (int)object.getPOI().x && (int)object.getY() == (int)object.getPOI().y) {
                    movement.x = 0;
                    movement.y = 0;
                }       

                object.setX(object.getX() + movement.x);
                object.setY(object.getY() + movement.y);

                int animationRow = 0;
                if(movement.x != 0 || movement.y != 0) {
                        animationRow = 8;
                }
                else{
                    object.setPOI(null);
                }
                object.getSprite().setRow(animationRow + facing);
            }
        }
    }
    
    public Vector2 getNearestGridTile(float x, float y){
        
        //o o o
        //o + o
        //x o o
        if(x <= gridPos.x && y <= gridPos.y){
            return new Vector2(gridPos.x -16, gridPos.y);
        }
        //o o o
        //x + o
        //o o o
        else if(x < gridPos.x && y > gridPos.y && y < gridPos.y + gridHeight*32){
            return new Vector2(gridPos.x -16, (int) (Math.ceil((double) (y) / 32.0) * 32.0));
        }
        //x o o
        //o + o
        //o o o
        else if(x <= gridPos.x && y >= gridPos.y + gridHeight*32){
            return new Vector2(gridPos.x -16, gridPos.y + gridHeight*32);
        }
        //o x o
        //o + o
        //o o o
        else if(x > gridPos.x && x < gridPos.x + gridWidth*32 && y > gridPos.y + gridHeight*32){
            return new Vector2((int) (Math.ceil((double) (x) / 32.0) * 32.0) -16, gridPos.y + gridHeight*32);
        }
        //o o x
        //o + o
        //o o o
        else if(x >= gridPos.x + gridWidth*32 && y >= gridPos.y + gridHeight*32){
            return new Vector2(gridPos.x + gridWidth*32 -16, gridPos.y + gridHeight*32);
        }
        //o o o
        //o + x
        //o o o
        else if(x > gridPos.x + gridWidth*32 && y < gridPos.y + gridHeight*32 && y > gridPos.y){
            return new Vector2(gridPos.x + (gridWidth-1)*32 -16, (int) (Math.ceil((double) (y) / 32.0) * 32.0));
        }
        //o o o
        //o + o
        //o o x
        else if(x >= gridPos.x + gridWidth*32 && y <= gridPos.y){
            return new Vector2(gridPos.x + gridWidth*32 -16, gridPos.y);
        }
        //o o o
        //o + o
        //o x o
        else if(x > gridPos.x && x < gridPos.x + gridWidth*32 && y < gridPos.y){
            return new Vector2((int) (Math.ceil((double) (x) / 32.0) * 32.0) -16, gridPos.y);
        }
        //o o o
        //o x o
        //o o o
        else{
            return new Vector2((int) (Math.ceil((double) (x) / 32.0) * 32.0) - 16, (int) (Math.ceil((double) (y) / 32.0) * 32.0));
        }
    }
       
}
