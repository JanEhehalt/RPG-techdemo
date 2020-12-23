package com.trs.main;

import com.badlogic.gdx.Gdx;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * 
 * Layer 0: tilelayer under Player 0
 * Layer 1: tilelayer under Player 1
 * Layer 2: tilelayer under Player 2
 * Layer 3: tilelayer above Player 0
 * Layer 4: tilelayer above Player 1
 * Layer 5: CollisionRects
 * Layer 6: DoorRects
 *          destinationDoor
 *          destinationMap 
 *          exit
 *          id
 * Layer 7: InteractionObjects
 * Layer 8: NpcRects
 * 
 * @author Jan
 */

public class MapContainer {
        
	Stage stage;
	OrthographicCamera camera;
	TmxMapLoader maploader;
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
	Door[] doors;
	public Door collidingDoor;
        
    final int[] layersBelowPlayer = {0, 1, 2};
    final int[] layersAbovePlayer = {3, 4};
	
        // TODO: Value which shows from which door the player is coming?
    public MapContainer(float CAMERA_WIDTH, float CAMERA_HEIGHT, Player p, String mapString, int inDoor) {
        // CREATION OF STAGE
        camera = new OrthographicCamera();
        camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
        camera.update();
        stage = new Stage(new FitViewport(CAMERA_WIDTH, CAMERA_HEIGHT, camera));
        Gdx.input.setInputProcessor(stage);
        
        
        //CREATION OF TILEDMAP
        maploader = new TmxMapLoader();
        map = maploader.load(mapString);
        renderer = new OrthogonalTiledMapRenderer(map);
        renderer.setView((OrthographicCamera)stage.getCamera());
        stage.getCamera().update();
        
        // adding MapObjects to the Stage
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            stage.addActor(new MapCollisionObject((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight()));
        }
        
        // adding the door links
        ArrayList<Door> tempDoors = new ArrayList<>();
    	for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            MapProperties props = object.getProperties();
            
            int id = props.get("id", Integer.class);
            int exit = props.get("exit", Integer.class);
            int destinationMap = props.get("destinationMap", Integer.class);
            int destinationDoor = props.get("destinationDoor", Integer.class);
            
            Door door = new Door(id, exit, destinationMap, destinationDoor, rect);
            tempDoors.add(door);
        }
    	
        // adding the Npcs
        for(MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            MapProperties props = object.getProperties();
            
            int id = props.get("id", Integer.class);
            
            stage.addActor(new MovingNpc(rect, rect.getX() + (float)(Math.random()*rect.getWidth()), rect.getY()+(float)(Math.random()*rect.getHeight()), id));
        }
        
        // adding the InteractionObjects
        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            MapProperties props = object.getProperties();
            
            int id = props.get("id", Integer.class);
            String texture = props.get("texture", String.class);
            
            stage.addActor(new InteractionObject(rect, rect.getX(), rect.getY(), id, texture));
            stage.addActor(new MapCollisionObject((int) rect.x, (int) rect.y, (int) rect.width, (int) rect.height));
        }
        
    	doors = new Door[tempDoors.size()];
    	for(int i = 0; i < doors.length; i++) {
    		doors[i] = tempDoors.get(i);
    		if(i == inDoor) {
    			int facing = doors[i].exit;
    			
    			switch(facing) {
    			case 0:
    				p.setPosition(doors[i].rect.x, doors[i].rect.y + doors[i].rect.height);
    				break;
    			case 1:
    				p.setPosition(doors[i].rect.x - p.getWidth(), doors[i].rect.y);
    				break;
    			case 2:
    				p.setPosition(doors[i].rect.x, doors[i].rect.y - p.getHeight());
    				break;
    			default:
    				p.setPosition(doors[i].rect.x + doors[i].rect.width, doors[i].rect.y);
    				break;
    			}
    		}
    	}
    	
    	p.movementX = 0;
    	p.movementY = 0;
   
        stage.addActor(p);
    }
        
    public void render(float f){
        renderer.setView((OrthographicCamera)stage.getCamera());
        
        renderer.render(layersBelowPlayer);
        
        Actor[] old = stage.getActors().toArray();
        stage.clear();
        for(Actor a : sort(old)){
            stage.addActor(a);
        }
        for(Actor a : stage.getActors()) {
            if(a instanceof Player) {
            	Rectangle rect = ((Player) a).collisionRect;
            	
            	for(Door d : doors) {
            		if(Intersector.overlaps(rect, d.rect)) {
            			collidingDoor = d;
            			break;
            		}
            	}
            }
        }
        
        stage.act(f);
        stage.draw();
        
        renderer.render(layersAbovePlayer);
        
        for(Actor a : stage.getActors()){
            if(a instanceof Textbox){
                stage.getBatch().begin();
                a.draw(stage.getBatch(), f);
                stage.getBatch().end();
            }
        }
        
        if(Main.gamestate == 1) {
            Textbox t = null;
            for(Actor a : stage.getActors()){
                if(a instanceof Textbox){
                    t = (Textbox)a;
                    if(t.getState() == 3){
                        a.remove();
                        Main.gamestate = 0;
                        t.getSelectedAsw(); // DO STUFF NICENICE
                    }
                }
            }
        }
        
        // center camera
        for(Actor a : stage.getActors()){
            if(a instanceof Player){
                stage.getCamera().position.set((a.getX()+a.getWidth()/2), (a.getY()+a.getHeight()/2), 0);
                stage.getCamera().update();
                break;
            }
        }
    }
    
    public void resize(int width, int height){
        stage.getViewport().update(width, height, false);
    }
    
    public Player getPlayer(){
        for(Actor a : stage.getActors()){
            if(a.getName().equals("player")){
                return (Player)a;
            }
        }
        System.out.println("OLD MAP DIDNT FIND PLAYER");
        return null;
    }
    
    public Actor[] sort(Actor[] unsorted){
        // TODO: Textboxes to the end of the Array to draw them last
        for(int j = 0; j < unsorted.length-1; j++){
            for(int i = unsorted.length-1; i >= 0; i--){
                if(i > 0 && unsorted[i].getY() > unsorted[i-1].getY()){
                    Actor temp = unsorted[i-1];
                    unsorted[i-1] = unsorted[i];
                    unsorted[i] = temp;
                }
            }
        }
        
        return unsorted;
    }
	
    
}
