/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trs.main.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.trs.main.InformationQuest;
import com.trs.main.Main;
import com.trs.main.Quest;

/**
 *
 * @author janeh
 */

public class QuestWindow {
    
    BitmapFont font;
    ShapeRenderer renderer;
    boolean visible;
    int selectedQuest;
    
    public QuestWindow(){
        renderer = new ShapeRenderer();
        visible = true;
        selectedQuest = 0;
        
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fontData/font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 21;
        font = generator.generateFont(parameter);
        generator.dispose();
        font.setColor(Color.WHITE);
    }
    
    public void draw(Quest[] quests, Batch batch, float playerX, float playerY){
        if(Main.gamestate == 2) visible = false;
        else visible = true;
        
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            if(selectedQuest < quests.length-1){
                selectedQuest++;
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            if(selectedQuest > 0){
                selectedQuest--;
            }
        }
        
        if(visible){
            float boxX = playerX + Main.CAMERA_WIDTH/2 - 150;
            float boxY = playerY + Main.CAMERA_HEIGHT/2 - 250;
            float boxWidth = 140;
            float boxHeight = 240;
            
            renderer.setProjectionMatrix(batch.getProjectionMatrix());
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            renderer.begin(ShapeRenderer.ShapeType.Filled);
            renderer.setColor(0.1f, 0.1f, 0.1f, 0.5f);
            renderer.rect(boxX, boxY, boxWidth, boxHeight);
            renderer.end();
            batch.begin();
            font.draw(batch, quests[selectedQuest].getQuestName(), boxX + 10, boxY + boxHeight - getTextHeight("A") - 10);
            if(quests[selectedQuest] instanceof InformationQuest){
                InformationQuest quest = (InformationQuest)quests[selectedQuest];
                font.getData().setScale(0.8f);
                for(int i = 0; i < quest.getInformationNpcId().length; i++){
                    font.draw(batch, "MAP: " + quest.getInformationNpcMapId()[i] + " NPC: " 
                        + quest.getInformationNpcId()[i], boxX + 10, boxY + boxHeight - getTextHeight("A") - 10 - ((i+1) * (getTextHeight("A") + 10)));
                }
                font.getData().setScale(1f);
            }
            batch.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
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
