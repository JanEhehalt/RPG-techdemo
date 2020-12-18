/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trs.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
/**
 * 
 *
 * @author Jan
 */
public class Player extends Actor{
    
    //Rectangle r;
    //ShapeRenderer renderer;
    Texture t;
    
    Player(){
        //renderer = new ShapeRenderer();
        //r = new Rectangle(0,0,800,800);
        //setBounds(r.getX(), r.getY(), r.getWidth(), r.getHeight());
        t = new Texture(Gdx.files.internal("badlogic.jpg"));
        setBounds(0, 0, t.getWidth(), t.getHeight());
    }

    @Override
    protected void positionChanged() {
        //r.setX(getX());
        //r.setY(getY());
        
        super.positionChanged(); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    @Override
    public void act(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            setX(getX()+10);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            setX(getX()-10);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            setY(getY()+10);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            setY(getY()-10);
        }
        super.act(delta); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //batch.end();
        //renderer.setProjectionMatrix(batch.getProjectionMatrix());
        //renderer.begin(ShapeRenderer.ShapeType.Filled);
        //renderer.rect(getX(), getY(), getWidth(), getHeight());
        //renderer.end();
        //batch.begin();
        batch.draw(t, getX(), getY());
        super.draw(batch, parentAlpha); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean remove() {
        return super.remove(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
