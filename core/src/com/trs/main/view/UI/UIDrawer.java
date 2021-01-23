/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trs.main.view.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.sun.tools.javac.util.Abort;

/**
 *
 * @author janeh
 */
public class UIDrawer {
    
    
    static Sprite box32 = new Sprite(new Texture(Gdx.files.internal("textureData/UI/box.png")));
    static Texture questbook = new Texture(Gdx.files.internal("textureData/UI/questbook.png"));
    
    static void drawCharBox(Batch batch, BitmapFont font, float x, float y, int size, String character){
        if(!batch.isDrawing()) batch.begin();
        font.setColor(Color.WHITE);
        switch(size){
            case 16:
                throw new OutOfMemoryError("nice");
            case 32:
                box32.setPosition(x, y);
                box32.draw(batch);
                font.getData().setScale(1.5f);
                font.draw(batch, ""+character, x + 16 - getTextWidth(font, character)/2, y + 16 + (getTextHeight(font, character)/2) - 2);
                font.getData().setScale(1);
                break;
            default:
                throw new OutOfMemoryError("nice");
        }
        
        batch.end();
    }
    
    static void drawIcon(Batch batch, float x, float y, int iconID){
        if(!batch.isDrawing()) batch.begin();
        switch(iconID){
            case 0:
                batch.draw(questbook, x - questbook.getWidth()/2, y - questbook.getHeight()/2);
                break;
            default:
                throw new Abort();
        }
        
        batch.end();
    }
    
    static float getTextWidth(BitmapFont font, String text){
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font,text);
        return glyphLayout.width;
    }
    static float getTextHeight(BitmapFont font, String text){
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font,text);
        return glyphLayout.height;
    }
    
}
