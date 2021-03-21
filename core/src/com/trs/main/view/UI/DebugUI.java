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
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
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

    public DebugUI(Matrix4 m) {
        
        Matrix4 uiMatrix = m.cpy();
        uiMatrix.setToOrtho2D(0, 0, Main.CAMERA_WIDTH, Main.CAMERA_HEIGHT);
        batch.setProjectionMatrix(uiMatrix);
        
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fontData/font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 21;
        font = generator.generateFont(parameter);
        generator.dispose();
        font.getData().setScale(0.5f);
        font.setColor(Color.WHITE);
    }
    
    
    
    
    public void draw(int entityAmount, Vector2 camPos){
        float textHeight = getTextHeight(font, "A")+5;
        
        ArrayList<String> strings = new ArrayList<>();
        strings.add("DeltaTime: "+Gdx.graphics.getDeltaTime());
        strings.add("FPS: "+(int)(Gdx.graphics.getFramesPerSecond()));
        strings.add("Entities: "+entityAmount);
        strings.add("CamX: "+camPos.x);
        strings.add("CamY: "+camPos.y);
        strings.add("Gamestate: "+Main.gamestate);
        strings.add(" ");
        if(Main.gamestate == -1){
        strings.add("DebugMode: PlayerCam");
        strings.add("Player input enabled");
        strings.add(" ");
        }
        else if(Main.gamestate == -2){
        strings.add("DebugMode: FreeCam");
        strings.add("Player input disabled");
        strings.add("press [SPACE] for Actors to act");
        }
        else{
        strings.add("Gamestate "+Main.gamestate+" not supported");
        }
        strings.add("press [F1] to switch Gamestate");
        
        float width = 0;
        for(String s : strings){
            float textWidth = getTextWidth(font, s);
            if(textWidth > width){
                width = textWidth;
            }
        }
        
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.1f, 0.1f, 0.1f, 0.8f);
        shapeRenderer.rect(5, Main.CAMERA_HEIGHT - (strings.size()+1)*textHeight-5, width+10,(strings.size()+1)*textHeight);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(5, Main.CAMERA_HEIGHT - (strings.size()+1)*textHeight-5, width+10,(strings.size()+1)*textHeight);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.line(Main.CAMERA_WIDTH/2-3, Main.CAMERA_HEIGHT/2, Main.CAMERA_WIDTH/2+3, Main.CAMERA_HEIGHT/2);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.line(Main.CAMERA_WIDTH/2, Main.CAMERA_HEIGHT/2-3, Main.CAMERA_WIDTH/2, Main.CAMERA_HEIGHT/2+3);
        shapeRenderer.end();
        
        
        batch.begin();
        // DRAW  FRAMETIME
        font.setColor(Color.RED);
        
        for(int i = 1; i < strings.size()+1; i++){
            font.draw(batch, strings.get(i-1), 10, Main.CAMERA_HEIGHT-textHeight*i);
        }
        
        
        batch.end();
        
    }
    public void resize(){
        //batch = new SpriteBatch();
        //shapeRenderer = new ShapeRenderer();
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
