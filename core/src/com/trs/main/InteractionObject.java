package com.trs.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class InteractionObject extends Actor{
    Textbox t;
    Rectangle collisionRect;
    private AnimatedSprite animatedSprite;
    
    boolean currentlyTalking;
    DialogueParser parser;
    
    int id;
    
    public InteractionObject(Rectangle collisionRect, float xPos, float yPos, int id, String texture){
        setName("interactive");
        this.id = id;
        Texture t = new Texture(Gdx.files.internal(texture));
        
        currentlyTalking = false;

        animatedSprite = new AnimatedSprite(t, 32, 32, false);
        animatedSprite.setRow(0);
        this.collisionRect = collisionRect;
        
        parser = new DialogueParser("npcs/1/dialogue/test.txt");
        Dialogue nextDialogue = parser.firstDialogue();
        this.t = new Textbox(nextDialogue.question, nextDialogue.ans, getX()+getWidth()/2, getY()+getHeight()/2);
        
        setBounds(xPos, yPos, animatedSprite.getSprite().getWidth(), animatedSprite.getSprite().getHeight());
    }
    
    public void startDialogue(float xPos, float yPos) {
    	currentlyTalking = true;
    	getStage().addActor(new Textbox(t, xPos, yPos));
    }
    
    @Override
    protected void positionChanged() {
        animatedSprite.setSpritePosition((int)getX(), (int)getY());
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
                                parser = new DialogueParser("npcs/1/dialogue/test.txt");
                                System.out.println("asdfasdf");
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
        
        animatedSprite.updateAnimation(delta);
        
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        animatedSprite.draw(batch);
        super.draw(batch, parentAlpha); //To change body of generated methods, choose Tools | Templates.
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
