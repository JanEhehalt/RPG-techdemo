package com.trs.main;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MapContainer {
	Stage stage;
	OrthographicCamera camera;
	TmxMapLoader tmx;
	TiledMap map;
	OrthogonalTiledMapRenderer tmr;
	ArrayList<Door> doors = new ArrayList<>();
	
	public MapContainer(float CAMERA_WIDTH, float CAMERA_HEIGHT, TiledMap map) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
        camera.update();
        this.map = map;
        stage = new Stage(new FitViewport(CAMERA_WIDTH, CAMERA_HEIGHT, camera));
        
    	for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            MapProperties props = object.getProperties();
            //Door door = new Door(props.get("id"), props.get("destinationMap"), props.get("destinationDoor"), rect);
            
            //System.out.println("ID: " + props.get("ID", Integer.class));
            //System.out.println("ID: " + props.get("y"));
        }
	}
	
}
