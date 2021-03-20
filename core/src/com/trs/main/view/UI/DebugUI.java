/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trs.main.view.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.trs.main.Main;
import java.util.ArrayList;

/**
 *
 * @author janeh
 */
public class DebugUI {
    
    private BitmapFont font;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    Batch batch = new SpriteBatch();

    public DebugUI() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fontData/font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 21;
        font = generator.generateFont(parameter);
        generator.dispose();
        font.setColor(Color.BLACK);
    }
    
    
    
    
    public void draw(int entityAmount){
        float textHeight = getTextHeight(font, "A")+5;
        
        ArrayList<String> strings = new ArrayList<>();
        strings.add("DeltaTime: "+Gdx.graphics.getDeltaTime());
        strings.add("FPS: "+(int)(1/Gdx.graphics.getDeltaTime()));
        strings.add("Actors: "+entityAmount);
        
        float width = 0;
        for(String s : strings){
            float textWidth = getTextWidth(font, s);
            if(textWidth > width){
                width = textWidth;
            }
        }
        
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.1f, 0.1f, 0.1f, 0.8f);
        shapeRenderer.rect(5, Gdx.graphics.getHeight() - (strings.size()+1)*textHeight-5, width+10,(strings.size()+1)*textHeight);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        
        
        batch.begin();
        // DRAW  FRAMETIME
        font.setColor(Color.RED);
        
        for(int i = 1; i < strings.size()+1; i++){
            font.draw(batch, strings.get(i-1), 10,Gdx.graphics.getHeight()-textHeight*i);
        }
        
        
        batch.end();
        
        
    
    }
    public float getTextWidth(BitmapFont font, String text){
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font,text);
        return glyphLayout.width;
    }
    public float getTextHeight(BitmapFont font,String text){
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font,text);
        return glyphLayout.height;
    }
}
