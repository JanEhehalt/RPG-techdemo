/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trs.main.worldobjects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 *
 * @author Jan
 */
public class MapCollisionObject extends Actor{
    
    private Rectangle r;
    
    public MapCollisionObject(int x, int y, int width, int height){
        setName("mapobject");
        r = new Rectangle(x, y, width, height);
        setBounds(x, y, width, height);
    }

    @Override
    public void act(float delta) {
        super.act(delta); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the r
     */
    public Rectangle getR() {
        return r;
    }

    /**
     * @param r the r to set
     */
    public void setR(Rectangle r) {
        this.r = r;
    }
    
    
    
}
