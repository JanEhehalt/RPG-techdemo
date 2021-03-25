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
import com.badlogic.gdx.math.Matrix4;
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
    
    Batch batch = new SpriteBatch();
    
    public QuestWindow(Matrix4 m){
        renderer = new ShapeRenderer();
        visible = true;
        selectedQuest = 0;
        
        Matrix4 uiMatrix = m.cpy();
        uiMatrix.setToOrtho2D(0, 0, Main.CAMERA_WIDTH, Main.CAMERA_HEIGHT);
        batch.setProjectionMatrix(uiMatrix);
        
        
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fontData/font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 21;
        font = generator.generateFont(parameter);
        generator.dispose();
        font.setColor(Color.WHITE);
    }
    
    public void draw(Quest[] quests){
        
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
        
        
        float boxX = Main.CAMERA_WIDTH - 200;
        float boxY = Main.CAMERA_HEIGHT - 250;
        float boxWidth = 180;
        float boxHeight = 230;
        
        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        
        if(Main.debugModeActive){
            visible = false;
        }
        
        if(visible && Main.gamestate != 2 && Main.gamestate != 1){
            if(visiblePerc < 1){
                visiblePerc += animationSpeed;
                Gdx.gl.glEnable(GL20.GL_BLEND);
                Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                renderer.begin(ShapeRenderer.ShapeType.Filled);
                renderer.setColor(0.1f, 0.1f, 0.1f, 0.5f);
                renderer.rect((float)(boxX + boxWidth-(boxWidth * visiblePerc)), (float)(boxY + boxHeight-(boxHeight * visiblePerc)), (float)(boxWidth * visiblePerc), (float)(boxHeight * visiblePerc));
                renderer.end();
                Gdx.gl.glDisable(GL20.GL_BLEND);
                renderer.begin(ShapeRenderer.ShapeType.Line);
                renderer.setColor(Color.BLACK);
                renderer.rect((float)(boxX + boxWidth-(boxWidth * visiblePerc)), (float)(boxY + boxHeight-(boxHeight * visiblePerc)), (float)(boxWidth * visiblePerc), (float)(boxHeight * visiblePerc));
                renderer.end();
                UIDrawer.drawCharBox(batch, font, boxX + boxWidth - 16   , boxY + boxHeight - 16, 32, "^");
            }
            else{
                Gdx.gl.glEnable(GL20.GL_BLEND);
                Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                renderer.begin(ShapeRenderer.ShapeType.Filled);
                renderer.setColor(0.1f, 0.1f, 0.1f, 0.5f);
                renderer.rect(boxX, boxY, boxWidth, boxHeight);
                renderer.end();
                Gdx.gl.glDisable(GL20.GL_BLEND);
                renderer.begin(ShapeRenderer.ShapeType.Line);
                renderer.setColor(Color.BLACK);
                renderer.rect((float)(boxX + boxWidth-(boxWidth * visiblePerc)), (float)(boxY + boxHeight-(boxHeight * visiblePerc)), (float)(boxWidth * visiblePerc), (float)(boxHeight * visiblePerc));
                renderer.end();
                Gdx.gl.glEnable(GL20.GL_BLEND);
                Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);


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
            
            if(Gdx.input.isKeyJustPressed(Input.Keys.UP) && Main.gamestate != -1){
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
                renderer.begin(ShapeRenderer.ShapeType.Line);
                renderer.setColor(Color.BLACK);
                renderer.rect((float)(boxX + boxWidth-(boxWidth * visiblePerc)), (float)(boxY + boxHeight-(boxHeight * visiblePerc)), (float)(boxWidth * visiblePerc), (float)(boxHeight * visiblePerc));
                renderer.end();
               UIDrawer.drawCharBox(batch, font, boxX + boxWidth - 16   , boxY + boxHeight - 16, 32, "V");
            }
            else{
               UIDrawer.drawCharBox(batch, font, boxX + boxWidth - 16   , boxY + boxHeight - 16, 32, "V");
               UIDrawer.drawIcon(batch, boxX + boxWidth - 16   , boxY + boxHeight - 16, 0);
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && Main.gamestate != -1){
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
