/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trs.main.view.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.trs.main.Dialogue;
import com.trs.main.Main;
import java.util.ArrayList;

/**
 *
 * @author Jan
 */
public class Textbox extends Actor{
    
    final Color TextColor = Color.WHITE;
    final Color SelectedColor = Color.FIREBRICK;
    
    BitmapFont font;
    Rectangle r;
    float printChar;
    
    float textSpeed = 0.8f;
    //ArrayList<String> splitted;
    
    int state; // 0: drawing    1: waiting for input    2: finished
    
    int selectedAsw = 0;
    
    String[] ans;
    
    float textHeight;
    
    String toPrint;
    
    
    Texture txt = new Texture("txt.png");
    Texture txtTop = new Texture("txt_top.png");
    Texture txtBot = new Texture("txt_bot.png");
    
    public static Matrix4 m;
    
    Batch batch = new SpriteBatch();
    
    public Textbox(String toPrint, String[] ans) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fontData/font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 21;
        font = generator.generateFont(parameter);
        generator.dispose();
        font.setColor(Color.BLACK);
        
        textHeight = getTextHeight(font,"A");
        printChar = 0;
        this.ans = ans;
        setName("textbox");
        
        setWidth(Main.CAMERA_WIDTH - 40);
        r = new Rectangle(20, 20, Main.CAMERA_WIDTH-40, 0);
        setBounds(r.getX(), r.getY(), r.getWidth(), r.getHeight());
        
        this.toPrint = toPrint;
        state = 0;
    }
    
    public void update(Dialogue d) {
    	//this.splitted = getSplitted(d.question, (int) Main.CAMERA_WIDTH / 2);
        this.toPrint = d.question;
    	this.ans = d.ans;
    	
        //float height = this.splitted.size() * 1.2f * textHeight + (this.ans.length+1) * 1.2f * textHeight;
        r = new Rectangle(getX(), getY(), getWidth(), 0);
        setBounds(r.getX(), r.getY(), r.getWidth(), r.getHeight());
        this.state = 0;
        printChar = 0;
        selectedAsw = 0;
    }

    @Override
    public void act(float delta) {
        
        if(state == 1){
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
                state = 2;
                System.out.println(ans[selectedAsw]);
            }
        }
        else if(state == 2){
            state = 3;
        }
        else{
            if(printChar >= toPrint.length()){
                state = 1;
            }
            else{
                printChar+=textSpeed;
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
                state = 1;
                printChar = toPrint.length();
            }
        }

        super.act(delta);
    }
    
    
    
    @Override
    protected void positionChanged() {
        
    }
    
    

    @Override
    public void draw(Batch camBatch, float parentAlpha) {
        
        
        camBatch.end();
        
        batch.setProjectionMatrix(m);
        batch.begin();
        
        int alignment = -1;
        
        font.setColor(Color.CLEAR);
        float height = font.draw(batch, toPrint.substring(0, (int)printChar), getX()+2, getY(), getWidth(), alignment, true).height;
        float theight = height;
        float textHeight = getTextHeight(font, "A");
        if(state == 1){
            for(String s : ans){
                height +=  textHeight + 5;
            }
        }
        setHeight(height);
        font.setColor(Color.BLACK);
        
        
        int amount = 1+(int)(getHeight()/txt.getHeight());
        
        batch.draw(txtTop, getX(), getY() + amount*txt.getHeight());
        for(int i = 0; i < amount; i++){
            batch.draw(txt, getX(), getY() + txt.getHeight()*i);
        }
        batch.draw(txtBot, getX(), getY() - txtBot.getHeight());
        
        font.setColor(TextColor);
        font.draw(batch, toPrint.substring(0, (int)printChar), getX()+6, getY()+getHeight(), getWidth()-8, alignment, true);
        
        if(state == 1){
            for(int i = 0; i < ans.length; i++){
                if(selectedAsw == i){
                    font.setColor(SelectedColor);
                }
                else{
                    font.setColor(TextColor);
                }
                font.draw(batch, ans[i], getX()+20, (getY()+getHeight()-5) -theight- (i)*(textHeight+5), getWidth(), alignment, true);
                
            }
        }
        batch.end();
        camBatch.begin();
        
        super.draw(batch, parentAlpha);
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
    public int getState(){
        return state;
    }
    public int getSelectedAsw(){
        return selectedAsw;
    }
}
