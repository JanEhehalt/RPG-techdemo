    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trs.main.view.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    
    final double animationSpeed = 0.05;
    
    BitmapFont font;
    ShapeRenderer renderer;
    boolean visible;
    int selectedQuest;
    
    double visiblePerc;
    
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
        
        
        float boxX = playerX + Main.CAMERA_WIDTH/2 - 200;
        float boxY = playerY + Main.CAMERA_HEIGHT/2 - 0.4f*Main.CAMERA_HEIGHT - 30;
        float boxWidth = 0.2f*Main.CAMERA_WIDTH;
        float boxHeight = 0.4f*Main.CAMERA_HEIGHT;
        
        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        
        if(visible && Main.gamestate != 2){
            if(visiblePerc < 1){
                visiblePerc += animationSpeed;
                Gdx.gl.glEnable(GL20.GL_BLEND);
                Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                renderer.begin(ShapeRenderer.ShapeType.Filled);
                renderer.setColor(0.1f, 0.1f, 0.1f, 0.5f);
                renderer.rect((float)(boxX + boxWidth-(boxWidth * visiblePerc)), (float)(boxY + boxHeight-(boxHeight * visiblePerc)), (float)(boxWidth * visiblePerc), (float)(boxHeight * visiblePerc));
                renderer.end();
                Gdx.gl.glDisable(GL20.GL_BLEND);
                UIDrawer.drawCharBox(batch, font, boxX + boxWidth - 16   , boxY + boxHeight - 16, 32, "^");
            }
            else{
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
                    font.getData().setScale(0.6f);
                    for(int i = 0; i < quest.getInformationNpcId().length; i++){
                        if(quest.getTalked()[i]){
                            font.draw(batch, "MAP: " + quest.getInformationNpcMapId()[i] + " NPC: " 
                            + quest.getInformationNpcId()[i] +" true", boxX + 10, boxY + boxHeight - getTextHeight("A") - 15 - ((i+1) * (getTextHeight("A") + 15)));
                        }
                        else{
                            font.draw(batch, "MAP: " + quest.getInformationNpcMapId()[i] + " NPC: " 
                            + quest.getInformationNpcId()[i] +" false", boxX + 10, boxY + boxHeight - getTextHeight("A") - 15 - ((i+1) * (getTextHeight("A") + 15)));
                        }
                    }
                    font.getData().setScale(1f);
                }
                batch.end();

                if(selectedQuest > 0){
                    UIDrawer.drawCharBox(batch, font, boxX - 16              , boxY + boxHeight/2 - 16, 32, "<");
                }
                if(selectedQuest < quests.length-1){
                    UIDrawer.drawCharBox(batch, font, boxX + boxWidth - 16   , boxY + boxHeight/2 - 16, 32, ">");
                }

                UIDrawer.drawCharBox(batch, font, boxX + boxWidth - 16   , boxY + boxHeight - 16, 32, "^");
                UIDrawer.drawIcon(batch, boxX + boxWidth/2, boxY + boxHeight, 0);

                Gdx.gl.glDisable(GL20.GL_BLEND);
            }
            
            if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
                visible = false;
            }
            
        }
        else{
             if(visiblePerc > 0){
                visiblePerc -= animationSpeed;
                Gdx.gl.glEnable(GL20.GL_BLEND);
                Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                renderer.begin(ShapeRenderer.ShapeType.Filled);
                renderer.setColor(0.1f, 0.1f, 0.1f, 0.5f);
                renderer.rect((float)(boxX + boxWidth-(boxWidth * visiblePerc)), (float)(boxY + boxHeight-(boxHeight * visiblePerc)),(float)(boxWidth * visiblePerc), (float)(boxHeight * visiblePerc));
                renderer.end();
                Gdx.gl.glDisable(GL20.GL_BLEND);
               UIDrawer.drawCharBox(batch, font, boxX + boxWidth - 16   , boxY + boxHeight - 16, 32, "V");
            }
            else{
               UIDrawer.drawCharBox(batch, font, boxX + boxWidth - 16   , boxY + boxHeight - 16, 32, "V");
               UIDrawer.drawIcon(batch, boxX + boxWidth - 16   , boxY + boxHeight - 16, 0);
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
                visible = true;
            }
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
