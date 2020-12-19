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
    
    int state; // 0: drawing    1: waiting for input    2: finished
    
    int selectedAsw = 0;
    
    String asw1;
    String asw2;
    
    public Textbox(String toPrint, String asw1, String asw2) {
        printLine = 0;
        printChar = 0;
        this.asw1 = asw1;
        this.asw2 = asw2;
        setName("textbox");
        font = new BitmapFont();
        r = new Rectangle(50, 50, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/4);
        setBounds(r.getX(), r.getY(), r.getWidth(), r.getHeight());
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 21;
        font = generator.generateFont(parameter);
        generator.dispose();
        font.setColor(Color.BLACK);
        state = 0;
        splitted = getSplitted(toPrint, (int)getWidth());
        for(String s : splitted){
            System.out.println(s);
        }
        
    }

    @Override
    public void act(float delta) {
        if(state == 1){
            if(selectedAsw == 0){
                if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
                    selectedAsw = 1;
                }
            }
            else if(selectedAsw == 1){
                if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
                    selectedAsw = 0;
                }
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
                state = 2;
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
        }
        super.act(delta);
    }
    
    
    
    @Override
    protected void positionChanged() {
        
    }
    
    

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(state == 0){
            for(int i = 0; i < splitted.size(); i++){
                if(i == printLine){
                    font.draw(batch, splitted.get(i).substring(0, printChar), 0, getX() + getHeight()-i*1.2f*getTextHeight("A"));
                }
                else if(i < printLine){
                    font.draw(batch, splitted.get(i), 0, getX() + getHeight()-i*1.2f*getTextHeight("A"));
                }
            }
        }
        else{
            for(int i = 0; i <= printLine; i++){
                    font.draw(batch, splitted.get(i), 0, getX() + getHeight()-i*1.2f*getTextHeight("A"));
            }
            if(selectedAsw == 0){
                font.setColor(Color.RED);
            }
            font.draw(batch, asw1, 0.2f * r.getWidth(), getX() + getHeight() - splitted.size() * 1.2f * getTextHeight("A"));
            font.setColor(Color.BLACK);
            if(selectedAsw == 1){
                font.setColor(Color.RED);
            }
            font.draw(batch, asw2, 0.6f * r.getWidth(), getX() + getHeight() - splitted.size() * 1.2f * getTextHeight("A"));
            font.setColor(Color.BLACK);
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
                if(getTextWidth(string)+getTextWidth(s) > maxLength){
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
