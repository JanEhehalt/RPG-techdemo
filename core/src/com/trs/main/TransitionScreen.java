/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trs.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 *
 * @author janeh
 */
public class TransitionScreen{
    
    ShapeRenderer renderer;
    float opacity = 1f;
    float speed;

    public TransitionScreen(float speed) {
        super();
        this.speed = speed;
        renderer = new ShapeRenderer();
    }
    

    public void draw(Batch batch, float x, float y, Matrix4 m) {
        renderer.setProjectionMatrix(m);
        if(opacity - speed > 0){
            opacity -= speed;
        }
        else opacity = 0;
        renderer.setColor(0, 0, 0, opacity);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.rect(x - Main.CAMERA_WIDTH/2, y- Main.CAMERA_HEIGHT/2, Main.CAMERA_WIDTH, Main.CAMERA_HEIGHT);
        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    
    }
    
    
}
