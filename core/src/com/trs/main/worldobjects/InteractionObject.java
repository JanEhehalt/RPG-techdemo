package com.trs.main.worldobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.trs.main.Dialogue;
import com.trs.main.DialogueParser;
import com.trs.main.Main;
import com.trs.main.view.UI.Textbox;

public class InteractionObject extends Actor{
    Textbox t;
    Rectangle collisionRect;
    private AnimatedSprite animatedSprite;
    
    boolean currentlyTalking;
    DialogueParser parser;
    
    int id;
    
    String dialoguePath;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    
    public InteractionObject(Rectangle collisionRect, float xPos, float yPos,int mapId, int id, String texture){
        setName("interactive");
        this.id = id;
        Texture t = new Texture(Gdx.files.internal("textureData/sprites/"+texture));
        
        currentlyTalking = false;

        animatedSprite = new AnimatedSprite(t, 32, 32, false);
        animatedSprite.setRow(0);
        this.collisionRect = collisionRect;
        
        dialoguePath = "mapData/map"+mapId+"/interactionObjects/"+id+"/dialogue.txt";
        parser = new DialogueParser(dialoguePath);
        Dialogue nextDialogue = parser.firstDialogue();
        this.t = new Textbox(nextDialogue.question, nextDialogue.ans);
        
        setBounds(xPos, yPos, animatedSprite.getSprite().getWidth(), animatedSprite.getSprite().getHeight());
    }
    
    public InteractionObject(Rectangle collisionRect, float xPos, float yPos,int mapId, int id){
        setName("interactive");
        this.id = id;
        
        currentlyTalking = false;

        this.collisionRect = collisionRect;
        
        dialoguePath = "mapData/map"+mapId+"/interactionObjects/"+id+"/dialogue.txt";
        parser = new DialogueParser(dialoguePath);
        Dialogue nextDialogue = parser.firstDialogue();
        this.t = new Textbox(nextDialogue.question, nextDialogue.ans);
        
        setBounds(xPos, yPos, collisionRect.getWidth(), collisionRect.getHeight());
    }
    
    public void startDialogue(float xPos, float yPos) {
    	currentlyTalking = true;
    	getStage().addActor(t);
    }
    
    @Override
    protected void positionChanged() {
        
        if(animatedSprite != null)animatedSprite.setSpritePosition((int)getX(), (int)getY());
        
        collisionRect = new Rectangle(getX() + 16, getY(), 32, 48);
        super.positionChanged();
    }
    
    @Override
    public void act(float delta) {
        if(Main.gamestate == 1 && currentlyTalking) {
            if(currentlyTalking) {
                for(Actor a : getStage().getActors().toArray(Actor.class)) {
                    if(a instanceof Textbox) {
                        if(((Textbox) a).getState() == 2) {
                            int answer = ((Textbox) a).getSelectedAsw();
                            Dialogue newDialogue = parser.nextDialogue(answer + 1);

                            if(newDialogue == null) {
                                currentlyTalking = false;
                                parser = new DialogueParser(dialoguePath);
                                Dialogue nextDialogue = parser.firstDialogue();
                                this.t = new Textbox(nextDialogue.question, nextDialogue.ans);
                            }
                            else {
                                ((Textbox)a).update(newDialogue);
                                System.out.println("update nicencie");
                            }
                        }
                    }
                }
            }
        }
        
        if(animatedSprite != null)animatedSprite.updateAnimation(delta);
        
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(animatedSprite != null){animatedSprite.draw(batch);}
        if(Main.gamestate == -1){
            debug(batch);
        }
        super.draw(batch, parentAlpha); //To change body of generated methods, choose Tools | Templates.
    }
    
     private void debug(Batch batch){
            batch.end();
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            
            shapeRenderer.end();
            
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.rect(getX(), getY(), getWidth(),  getHeight());
            
            shapeRenderer.end();
            
            batch.begin();
    }
    
    public boolean collidingWithMapCollisionObject(){
        boolean  value = false;
        for(Actor a : getStage().getActors()){
                if(a.getName().equals("mapobject")){
                    //Rectangle p = new Rectangle(getX(), getY(), getWidth(), getHeight());
                    Rectangle o = new Rectangle(a.getX(), a.getY(), a.getWidth(), a.getHeight());
                    if(Intersector.overlaps(collisionRect, o)){
                        value = true;
                        break;
                    }
                }
        }
        return value;
    }  
    
    public Textbox getTextbox(){
        return t;
    }
    
}
