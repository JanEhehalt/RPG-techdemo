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
    float printChar;
    
    float textSpeed = 0.8f;
    //ArrayList<String> splitted;
    
    ShapeRenderer renderer;
    
    int state; // 0: drawing    1: waiting for input    2: finished
    
    int selectedAsw = 0;
    
    String[] ans;
    
    float textHeight;
    
    String toPrint;
    
    public Textbox(String toPrint, String[] ans, float xPos, float yPos) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fontData/font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 21;
        font = generator.generateFont(parameter);
        generator.dispose();
        font.setColor(Color.BLACK);
        
        renderer = new ShapeRenderer();
        textHeight = getTextHeight(font,"A");
        printChar = 0;
        this.ans = ans;
        setName("textbox");
        font = new BitmapFont();
        
        setWidth(Main.CAMERA_WIDTH - 40);
        //splitted = getSplitted(toPrint, (int)(Main.CAMERA_WIDTH - 40));
        
        // CALCULATE NEEDED HEIGHT
        //float height = splitted.size() * 1.2f * textHeight + (ans.length+1) * 1.2f * textHeight;
        
        r = new Rectangle(xPos - Main.CAMERA_WIDTH/2 + 20, yPos - Main.CAMERA_HEIGHT/2 + 20, Main.CAMERA_WIDTH - 40, 0);
        setBounds(r.getX(), r.getY(), r.getWidth(), r.getHeight());
        
        this.toPrint = toPrint;
        state = 0;
    }
    
    public Textbox(Textbox t, float xPos, float yPos){
        font = new BitmapFont();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fontData/font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 21;
        font = generator.generateFont(parameter);
        generator.dispose();
        font.setColor(Color.BLACK);
        
        textHeight = getTextHeight(font,"A");
        this.ans = t.ans;
        this.r = t.r;
        this.r.setPosition(xPos - Main.CAMERA_WIDTH/2 + 20, yPos - Main.CAMERA_HEIGHT/2 + 20);
        setBounds(r.getX(), r.getY(), r.getWidth(), r.getHeight());
        
        renderer = new ShapeRenderer();
        printChar = 0;
        setName("textbox");
        this.toPrint = t.toPrint;
        
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
            }
        }

        super.act(delta);
    }
    
    
    
    @Override
    protected void positionChanged() {
        
    }
    
    

    @Override
    public void draw(Batch batch, float parentAlpha) {
        
        int alignment = -1;
        
        font.setColor(Color.CLEAR);
        float height = font.draw(batch, toPrint.substring(0, (int)printChar), getX()+2, getY(), getWidth(), alignment, true).height+5;
        float textHeight = height;
            for(String s : ans){
                height += getTextHeight(font, "A") + 10;
            }
        setHeight(height);
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
        
        
        font.draw(batch, toPrint.substring(0, (int)printChar), getX()+2, getY()+getHeight()-3, getWidth(), alignment, true);
        
        if(state == 1){
            for(int i = 0; i < ans.length; i++){
                if(selectedAsw == i){
                    font.setColor(Color.RED);
                }
                font.draw(batch, ans[i], getX()+20, (getY()+getHeight()-5) - (textHeight + i*(getTextHeight(font, "A")+5)) - 3, getWidth(), alignment, true);
                font.setColor(Color.BLACK);
            }
        }
        
        super.draw(batch, parentAlpha);
    }
    /*
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
            if(getTextWidth(font,string)+getTextWidth(font,s) >= getWidth()){
                toReturn.add(string);
                string = new String();
                string += s;
            }
            else if(getTextWidth(font,string)+getTextWidth(font,s) < getWidth()){
                string += s;
            }
        }
        toReturn.add(string);
        
            System.out.println(getWidth());
            System.out.println(maxLength);
        for(String s : toReturn){
            System.out.println("-"+s+"-"+getTextWidth(font,s));
        }
        
        return toReturn;
    }
*/
    
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
