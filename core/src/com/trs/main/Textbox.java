/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trs.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import java.util.ArrayList;

/**
 *
 * @author Jan
 */
public class Textbox extends Actor{
    
    BitmapFont font;
    Rectangle r;
    int printLine;
    int printChar;
    ArrayList<String> splitted;
    String toPring;
    
    ShapeRenderer renderer;
    
    int state; // 0: drawing    1: waiting for input    2: finished
    
    int selectedAsw = 0;
    
    String[] ans;
    
    float textHeight;
    
    public Textbox(String toPrint, String[] ans, float xPos, float yPos) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 21;
        font = generator.generateFont(parameter);
        generator.dispose();
        font.setColor(Color.BLACK);
        
        renderer = new ShapeRenderer();
        textHeight = getTextHeight("A");
        printLine = 0;
        printChar = 0;
        this.ans = ans;
        setName("textbox");
        font = new BitmapFont();
        splitted = getSplitted(toPrint, (int)(Main.CAMERA_WIDTH/2));
        
        // CALCULATE NEEDED HEIGHT
        float height = splitted.size() * 1.2f * textHeight + (ans.length+1) * 1.2f * textHeight;
        
        r = new Rectangle(xPos - Main.CAMERA_WIDTH/2 + 20, yPos - Main.CAMERA_HEIGHT/2 + 20, Main.CAMERA_WIDTH/2, height);
        setBounds(r.getX(), r.getY(), r.getWidth(), r.getHeight());
        
        state = 0;
    }
    
    public Textbox(Textbox t, float xPos, float yPos){
        font = new BitmapFont();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 21;
        font = generator.generateFont(parameter);
        generator.dispose();
        font.setColor(Color.BLACK);
        
        textHeight = getTextHeight("A");
        this.splitted = t.splitted;
        this.ans = t.ans;
        System.out.println(splitted.size());
        float height = this.splitted.size() * 1.2f * textHeight + (this.ans.length+2) * 1.2f * textHeight;
        r = new Rectangle(xPos - Main.CAMERA_WIDTH/2 + 20, yPos - Main.CAMERA_HEIGHT/2 + 20, Main.CAMERA_WIDTH - 40, height);
        setBounds(r.getX(), r.getY(), r.getWidth(), r.getHeight());
        
        renderer = new ShapeRenderer();
        printLine = 0;
        printChar = 0;
        setName("textbox");
        
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
        else{
            if(printChar >= splitted.get(printLine).length()){
                if(splitted.size()-1 <= printLine){
                    state = 1;
                }
                else{
                    printLine++;
                    printChar = 0;
                }
            }
            else{
                printChar++;
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
                state = 1;
                printLine = splitted.size()-1;
            }
        }

        super.act(delta);
    }
    
    
    
    @Override
    protected void positionChanged() {
        
    }
    
    

    @Override
    public void draw(Batch batch, float parentAlpha) {
    	font.setColor(Color.BLACK);
        batch.end();
        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.BLUE);
    	renderer.rect(getX(), getY(), getWidth(), getHeight());
        renderer.end();
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.RED);
    	renderer.rect(getX(), getY(), getWidth(), getHeight());
        renderer.end();
        batch.begin();
        if(state == 0){
            for(int i = 0; i < splitted.size(); i++){
                if(i == printLine){
                    font.draw(batch, splitted.get(i).substring(0, printChar), getX()+5, getY() + getHeight()-i*1.2f*textHeight - 5);
                }
                else if(i < printLine){
                    font.draw(batch, splitted.get(i), getX()+5, getY() + getHeight()-i*1.2f*textHeight - 5);
                }
            }
        }
        else{
            for(int i = 0; i <= printLine; i++){
                    font.draw(batch, splitted.get(i), getX()+5, getY() + getHeight()-i*1.2f*textHeight - 5);
            }
            
            for(int i = 0; i < ans.length; i++) {
            	if(selectedAsw == i) {
            		font.setColor(Color.RED);
            	}
            	else {
            		font.setColor(Color.BLACK);
            	}
            	
            	font.draw(batch, ans[i], getX()+5, getY() + getHeight() - ((splitted.size() + i + 1) * 1.2f * textHeight - 5));
            }
        }
        super.draw(batch, parentAlpha);
    }
    
    public ArrayList<String> getSplitted(String toSplit, int maxLength){
        ArrayList<String> words = new ArrayList<>();
        int tail = 0;
        for(int head = 0; head < toSplit.length(); head++){
            if(toSplit.charAt(head) == ' '){
                words.add(toSplit.substring(tail, head+1));
                head++;
                tail=head;
            }  
        }
        words.add(toSplit.substring(tail, toSplit.length()));
        
        ArrayList<String> toReturn = new ArrayList<>();
        String string = new String();
        for(String s : words){
            if(getTextWidth(string)+getTextWidth(s) >= maxLength){
                toReturn.add(string);
                string = new String();
                string += s;
            }
            else if(getTextWidth(string)+getTextWidth(s) < maxLength){
                string += s;
            }
        }
        toReturn.add(string);
        
        for(String s : toReturn){
            //System.out.println("-"+s+"-");
        }
        
        return toReturn;
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
    public int getState(){
        return state;
    }
    public int getSelectedAsw(){
        return selectedAsw;
    }
}
