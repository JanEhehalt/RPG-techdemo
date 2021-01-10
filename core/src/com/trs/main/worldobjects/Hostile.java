package com.trs.main.worldobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.trs.main.Main;
import com.trs.main.StaticMath;
import com.trs.main.Stats;

public class Hostile extends Actor {
	
	private int id;
	private Stats stats;
	private AnimatedSprite sprite;
	private Rectangle collisionRect;
	private Circle attackCircle;
	private Circle attentionCircle;
	private boolean isMelee;
	
	// 0: normal movement, 1: locked onto Player, 2: attacking
	private int movementState;
	
	public Hostile(float xPos, float yPos, int id, Stats stats, String texture, boolean isMelee) {
		
		this.id = id;
		this.stats = stats;
		this.isMelee = isMelee;
		
		Texture tx = new Texture(Gdx.files.internal("textureData/sprites/" + texture));
		sprite = new AnimatedSprite(tx, 64, 64, true);
		
		collisionRect = new Rectangle(xPos + 16, yPos, 32, 16);
		attackCircle = new Circle(xPos + 16, yPos, 100f);
		attentionCircle = new Circle(xPos + 16, yPos, 300f);
		
		movementState = 0;
		
		setX(xPos);
		setY(yPos);
	}
	
	@Override
	public void act(float deltatime) {
		getSprite().updateAnimation(deltatime);
		
		if(getMovementState() == 0) {
			for(Actor a : getStage().getActors()) {
				if(a instanceof Player) {
					if(Intersector.overlaps(getAttentionCircle(), ((Player) a).getCollisionRect())) {
						setMovementState(1);
					}
				}
			}
		}
		
		if(getMovementState() == 1) {
			for(Actor a : getStage().getActors()) {
				if(a instanceof Player) {
					if(Intersector.overlaps(getAttackCircle(), ((Player) a).getCollisionRect())) {
						setMovementState(2);
					}
					if(!Intersector.overlaps(attentionCircle, ((Player) a).getCollisionRect())) {
						setMovementState(0);
					}
					

					Vector2 POI = new Vector2(a.getX(), a.getY());
		            float speed = 2;
		            
		            Vector2 movement = new Vector2(speed,0);
		            movement.setAngleRad(StaticMath.calculateAngle(getX(), getY(), POI.x, POI.y));
		            int facing;
		            if(movement.angleDeg() < 135 && movement.angleDeg() >= 45) {
		                    facing = 0;
		            }
		            else if(movement.angleDeg() >= 135 && movement.angleDeg() < 225) {
		                    facing = 1;
		            }
		            else if(movement.angleDeg() >= 225 && movement.angleDeg() < 315) {
		                    facing = 2;
		            }
		            else {
		                    facing = 3;
		            }
		            
		            if(StaticMath.calculateDistance(getX(), getY(), POI.x, POI.y) < 1f) {
		                movement.x = 0;
		                movement.y = 0;
		            }       
		            
		            setX(getX() + movement.x);
		            setY(getY() + movement.y);
		            
		            int animationRow = 0;
		            if(movement.x != 0 || movement.y != 0) {
		                    animationRow = 8;
		            }
		            
		            getSprite().setRow(animationRow + facing);
				}
			}
		}
		
		if(getMovementState() == 2) {
			Main.gamestate = 2;
			//System.out.println("Kämpfe mit mir du Spast!");
		}
	}
	
	@Override
	public void draw(Batch batch, float deltatime) {
		getSprite().draw(batch);
	}
	
	@Override
    protected void positionChanged() {
        getSprite().setSpritePosition((int)getX(), (int)getY());
        setCollisionRect(new Rectangle(getX() + 16, getY(), 32, 16));
        setAttackCircle(new Circle(getX() + 16, getY(), 100f));
		setAttentionCircle(new Circle(getX() + 16, getY(), 300f));
        super.positionChanged(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the stats
     */
    public Stats getStats() {
        return stats;
    }

    /**
     * @param stats the stats to set
     */
    public void setStats(Stats stats) {
        this.stats = stats;
    }

    /**
     * @return the sprite
     */
    public AnimatedSprite getSprite() {
        return sprite;
    }

    /**
     * @param sprite the sprite to set
     */
    public void setSprite(AnimatedSprite sprite) {
        this.sprite = sprite;
    }

    /**
     * @return the collisionRect
     */
    public Rectangle getCollisionRect() {
        return collisionRect;
    }

    /**
     * @param collisionRect the collisionRect to set
     */
    public void setCollisionRect(Rectangle collisionRect) {
        this.collisionRect = collisionRect;
    }

    /**
     * @return the attackCircle
     */
    public Circle getAttackCircle() {
        return attackCircle;
    }

    /**
     * @param attackCircle the attackCircle to set
     */
    public void setAttackCircle(Circle attackCircle) {
        this.attackCircle = attackCircle;
    }

    /**
     * @return the attentionCircle
     */
    public Circle getAttentionCircle() {
        return attentionCircle;
    }

    /**
     * @param attentionCircle the attentionCircle to set
     */
    public void setAttentionCircle(Circle attentionCircle) {
        this.attentionCircle = attentionCircle;
    }

    /**
     * @return the isMelee
     */
    public boolean isIsMelee() {
        return isMelee;
    }

    /**
     * @param isMelee the isMelee to set
     */
    public void setIsMelee(boolean isMelee) {
        this.isMelee = isMelee;
    }

    /**
     * @return the movementState
     */
    public int getMovementState() {
        return movementState;
    }

    /**
     * @param movementState the movementState to set
     */
    public void setMovementState(int movementState) {
        this.movementState = movementState;
    }
    
    
}
