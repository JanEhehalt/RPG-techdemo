package com.trs.main;

public abstract class FightObject {
	protected AnimatedSprite sprite;
	protected Stats stats;
	protected int id;
	
	public FightObject(AnimatedSprite sprite, Stats stats, int id) {
		this.sprite = sprite;
		this.stats = stats;
		this.id = id;
	}
}
