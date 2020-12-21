package com.trs.main;

import com.badlogic.gdx.math.Rectangle;

public class Door{
	public int id;
	public int exit;
	public int destinationMap;
	public int destinationDoor;
	Rectangle rect;
	
	public Door(int id, int exit, int destinationMap, int destinationDoor, Rectangle rect) {
		this.id = id;
		this.exit = exit;
		this.destinationMap = destinationMap;
		this.destinationDoor = destinationDoor;
		this.rect = rect;
	}
}
