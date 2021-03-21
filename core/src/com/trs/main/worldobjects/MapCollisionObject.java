/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trs.main.worldobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.trs.main.Main;

/**
 *
 * @author Jan
 */
public class MapCollisionObject extends Actor{
    
    private Rectangle r;
    ShapeRenderer shapeRenderer;
    
    public MapCollisionObject(int x, int y, int width, int height, ShapeRenderer uiRenderer){
        setName("mapobject");
        r = new Rectangle(x, y, width, height);
        setBounds(x, y, width, height);
        
        shapeRenderer = uiRenderer;
    }

    @Override
    public void act(float delta) {
        super.act(delta); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(Main.gamestate == -1 || Main.gamestate == -2){
            debug(batch);
        }
        super.draw(batch, parentAlpha); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void debug(Batch batch){
        batch.end();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        //ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(r.x, r.y, r.width, r.height);
        shapeRenderer.end();
        
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1f,0f,0f,0.2f);
        shapeRenderer.rect(r.x, r.y, r.width, r.height);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        batch.begin();
        
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
