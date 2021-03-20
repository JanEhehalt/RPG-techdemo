package com.trs.main.worldobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 *
 * @author jonathan
 */

public class AnimatedSprite {
	private int[] rowlengths = {7, 7, 7, 7, 8, 8, 8, 8, 9, 9, 9, 9, 6, 6, 6, 12, 12, 12, 12, 6};
	
    private Sprite sprite;
    private TextureRegion[][] texture;
    private int frame;
    private int row;
    private float delta;
    
    public AnimatedSprite(Texture tx, int tileWidth, int tileHeight, boolean isPlayer){
        texture = TextureRegion.split(tx, tileWidth, tileHeight);
        sprite = new Sprite();
        
        row = (int) (Math.random()*texture.length);
        frame = (int) (Math.random()*texture[row].length);
        
        if(!isPlayer) {
            rowlengths = new int[tx.getHeight() / tileHeight];
        	for(int i = 0; i < rowlengths.length; i++) {
        		rowlengths[i] = texture[i].length;
        	}
        }
        
        sprite = new Sprite(texture[getRow()][getFrame()]);
    }

    public void updateAnimation(float delta){
    	this.delta += delta;
    	
    	if(this.delta >= 0.1f) {
    		this.delta = 0;
    		if(getFrame() >= rowlengths[getRow()] - 1){
                setFrame(0);
            }
            else{
                setFrame(getFrame() + 1);
            }
            
            sprite.setRegion(texture[getRow()][getFrame()]);
    	}
    }
    
    public void draw(Batch batch) {
        sprite.draw(batch);
    }
    
    /**
     * @return the sprite
     */
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * @param sprite the sprite to set
     */
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
    
    public void setSpritePosition(int xPos, int yPos){
        sprite.setPosition(xPos, yPos);
    }

    /**
     * @return the texture
     */
    public TextureRegion[][] getTexture() {
        return texture;
    }

    /**
     * @param texture the texture to set
     */
    public void setTexture(TextureRegion[][] texture) {
        this.texture = texture;
    }

    /**
     * @return the frame
     */
    public int getFrame() {
        return frame;
    }

    /**
     * @param frame the frame to set
     */
    public void setFrame(int frame) {
        this.frame = frame;
    }

    /**
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * @param row the row to set
     */
    public void setRow(int row) {
        this.row = row;
    }
}
