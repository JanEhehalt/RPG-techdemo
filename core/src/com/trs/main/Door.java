package com.trs.main;

import com.badlogic.gdx.math.Rectangle;

public class Door{
	int id;
	int destinationMap;
	int destinationDoor;
	Rectangle rect;
	
	public Door(int id, int destinationMap, int destinationDoor, Rectangle rect) {
		this.id = id;
		this.destinationMap = destinationMap;
		this.destinationDoor = destinationDoor;
		this.rect = rect;
	}
}
