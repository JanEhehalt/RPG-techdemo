/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trs.main.fightscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.trs.main.Main;

/**
 *
 * @author janeh
 */
public class FightDialogue {
   
    BitmapFont font;
    Rectangle r;
    ShapeRenderer renderer;
    int selectedAsw = 0;
    String[] ans = {"Melee", "Range", "Skip"};
    
    public static Matrix4 m;
    
    Batch batch = new SpriteBatch();
    
    public FightDialogue(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fontData/font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 21;
        font = generator.generateFont(parameter);
        generator.dispose();
        font.setColor(Color.BLACK);
        renderer = new ShapeRenderer();
        
        r = new Rectangle(Main.CAMERA_WIDTH - 200, 20, 180, Main.CAMERA_HEIGHT-40);
        
    }
    
    public void draw(Batch camBatch){
        
        batch.setProjectionMatrix(m);
        
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            if(selectedAsw < ans.length - 1) {
                    selectedAsw++;
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            if(selectedAsw > 0) {
                    selectedAsw--;
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            // DO THE STUFF NICENICECNICNEICNIENICENI
        }
        
        
        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.setColor(Color.BLUE);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.rect(r.x, r.y, r.width, r.height);
        renderer.end();
        renderer.setColor(Color.RED);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.rect(r.x, r.y, r.width, r.height);
        renderer.end();
        
        batch.begin();
        for(int i = 0; i < ans.length; i++){
            if(i == selectedAsw){
                font.setColor(Color.RED);
            }
            else font.setColor(Color.BLACK);
            font.draw(batch, ans[i], r.x+5, r.y + r.height-i*1.2f*getTextHeight("A") - 5);
        }
        batch.end();
    }
    
    public float getTextWidth(String text){
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font,text);
        return glyphLayout.width;
    }
    public float getTextHeight(String text){
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font,text);
        return glyphLayout.height;
    }
    
}
