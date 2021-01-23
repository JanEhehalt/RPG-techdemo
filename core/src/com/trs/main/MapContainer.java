package com.trs.main;

import com.trs.main.worldobjects.MapCollisionObject;
import com.trs.main.worldobjects.Player;
import com.trs.main.worldobjects.Hostile;
import com.trs.main.worldobjects.InteractionObject;
import com.trs.main.worldobjects.MovingNpc;
import com.trs.main.fightscreen.Enemy;
import com.trs.main.fightscreen.FightPlayer;
import com.trs.main.fightscreen.FightObject;
import com.trs.main.fightscreen.FightScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
 *          id
 *          texture
 * Layer 8: NpcRects
 *          id
 *          texture
 * 
 * @author Jan
 */

public class MapContainer {
        
    private Stage stage;
    private OrthographicCamera camera;
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Door[] doors;
    private Door collidingDoor;
    
    private FightScreen fs;
    
    private TransitionScreen  t;
        
    private int[] layersBelowPlayer = {0, 1, 2};
    private int[] layersAbovePlayer = {3, 4};
	
        // TODO: Value which shows from which door the player is coming?
    public MapContainer(float CAMERA_WIDTH, float CAMERA_HEIGHT, Player p, String mapString, int inDoor, int mapId) {
        // CREATION OF STAGE
        camera = new OrthographicCamera();
        camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
        camera.update();
        stage = new Stage(new FitViewport(CAMERA_WIDTH, CAMERA_HEIGHT, getCamera()));
        Gdx.input.setInputProcessor(stage);
        
        //TRANSITION SCREEN
        t = new TransitionScreen(0.01f);
        
        
        
        //CREATION OF TILEDMAP
        maploader = new TmxMapLoader();
        map = maploader.load(mapString);
        renderer = new OrthogonalTiledMapRenderer(getMap());
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
            String texture = props.get("texture", String.class);
            
            stage.addActor(new MovingNpc(rect, rect.getX() + (float)(Math.random()*(rect.getWidth()-64)), rect.getY()+(float)(Math.random()*(rect.getHeight()-64)), id, mapId, texture));
        }
        
        // adding the InteractionObjects
        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            MapProperties props = object.getProperties();
            
            int id = props.get("id", Integer.class);
            String texture = props.get("texture", String.class);
            
            if(texture.equals("-")){
                stage.addActor(new InteractionObject(rect, rect.getX(), rect.getY(), mapId, id));
            }
            else{
                stage.addActor(new InteractionObject(rect, rect.getX(), rect.getY(), mapId, id, texture));
            }
            
            stage.addActor(new MapCollisionObject((int) rect.x, (int) rect.y, (int) rect.width, (int) rect.height));
        }
        
    	doors = new Door[tempDoors.size()];
    	for(int i = 0; i < doors.length; i++) {
    		doors[i] = tempDoors.get(i);
    		if(doors[i].id == inDoor) {
    			int facing = doors[i].exit;
    			System.out.println(i + " " + inDoor);
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
    	
    	p.setMovementX(0);
    	p.setMovementY(0);
   
        stage.addActor(p);
        
        stage.addActor(new Hostile(200, 200, 0, new Stats(), "sprite.png", false));
        stage.addActor(new Hostile(265, 200, 1, new Stats(), "sprite.png", true));
    }
        
    public void render(float f){
        if(Gdx.input.isKeyJustPressed(Input.Keys.TAB)){
            if(Main.gamestate == 0){
                Main.gamestate = 2;

                // CREATING MAP COLLISION OBJECTS
                ArrayList<Rectangle> mapRectsTemp = new ArrayList<>();
                for(Actor a : getStage().getActors()){
                    if(a instanceof MapCollisionObject){
                        mapRectsTemp.add(((MapCollisionObject)a).getR());
                    }
                }
                Rectangle[] rects = new Rectangle[mapRectsTemp.size()];
                for(int i = 0; i< mapRectsTemp.size(); i++){
                    rects[i] = mapRectsTemp.get(i);
                }

                // CREATING FightObject Array
                // Temporarily only Player
                ArrayList<FightObject> tempObjects = new ArrayList<>();
                tempObjects.add(new FightPlayer(getPlayer().getX(),getPlayer().getY(), getPlayer().getPlayerSprite(), getPlayer().getStats(), 0));
                
                for(Actor a : getStage().getActors()) {
                	if(a instanceof Hostile) {
                		if(((Hostile) a).getMovementState() > 0) {
                			((Hostile) a).setMovementState(2);
                			Enemy e = new Enemy(a.getX(), a.getY(), ((Hostile) a).getSprite(), ((Hostile) a).getStats(), ((Hostile) a).getId(), ((Hostile) a).isIsMelee());
                    		tempObjects.add(e);
                		}
                	}
                }
                
                FightObject[] fightObjects = new FightObject[tempObjects.size()];
                for(int i = 0; i< tempObjects.size(); i++){
                    fightObjects[i] = tempObjects.get(i);
                }

                setFs(new FightScreen(getStage().getBatch(), fightObjects, rects, getPlayer().getX()+32, getPlayer().getY()+32));
            }
            else if(Main.gamestate == 2){
                Main.gamestate = 0;
                getFs().nuke();
                getFs().setState(3);
            }
        }
        
        getRenderer().setView((OrthographicCamera)getStage().getCamera());
        
        getRenderer().render(getLayersBelowPlayer());
        
        if(Main.gamestate == 0 || Main.gamestate == 1) {
            Actor[] old = getStage().getActors().toArray();
            getStage().clear();
            for(Actor a : sort(old)){
                getStage().addActor(a);
            }
            for(Actor a : getStage().getActors()) {
                if(a instanceof Player) {
                    Rectangle rect = ((Player) a).getCollisionRect();
                    
                    for(Door d : getDoors()) {
                        if(Intersector.overlaps(rect, d.rect)) {
                            setCollidingDoor(d);
                            break;
                        }
                	}
                }
            }
            
            getStage().act(f);
            getStage().draw();
        }
        
        if(Main.gamestate == 2){
        	if(getFs() == null) {
        		// CREATING MAP COLLISION OBJECTS
                ArrayList<Rectangle> mapRectsTemp = new ArrayList<>();
                for(Actor a : getStage().getActors()){
                    if(a instanceof MapCollisionObject){
                        mapRectsTemp.add(((MapCollisionObject)a).getR());
                    }
                }
                Rectangle[] rects = new Rectangle[mapRectsTemp.size()];
                for(int i = 0; i< mapRectsTemp.size(); i++){
                    rects[i] = mapRectsTemp.get(i);
                }

                // CREATING FightObject Array
                // Temporarily only Player
                ArrayList<FightObject> tempObjects = new ArrayList<>();
                tempObjects.add(new FightPlayer(getPlayer().getX(),getPlayer().getY(), getPlayer().getPlayerSprite(), getPlayer().getStats(), 0));
                
                for(Actor a : getStage().getActors()) {
                	if(a instanceof Hostile) {
                		Enemy e = new Enemy(a.getX(), a.getY(), ((Hostile) a).getSprite(), ((Hostile) a).getStats(), ((Hostile) a).getId(), ((Hostile) a).isIsMelee());
                		tempObjects.add(e);
                	}
                }
                
                FightObject[] fightObjects = new FightObject[tempObjects.size()];
                for(int i = 0; i< tempObjects.size(); i++){
                    fightObjects[i] = tempObjects.get(i);
                }

                setFs(new FightScreen(getStage().getBatch(), fightObjects, rects, getPlayer().getX()+32, getPlayer().getY()+32));
        	}
        	
            if(getFs().getState() == 3){
                for(FightObject object : getFs().getObjects()){
                    if(object instanceof FightPlayer){
                        
                        getPlayer().setX(object.getX());
                        getPlayer().setY(object.getY());
                        getPlayer().setStats(object.getStats());
                        
                    }
                    else{
                        for(int i = getStage().getActors().size-1; i >= 0; i--){
                            if(getStage().getActors().get(i) instanceof Hostile){
                                if(((Hostile)getStage().getActors().get(i)).getId() == object.getId()){
                                    if(object.getStats().getHp() <= 0){
                                        getStage().getActors().removeIndex(i);
                                    }
                                    else{
                                        getStage().getActors().get(i).setPosition(object.getX(), object.getY());
                                        ((Hostile)getStage().getActors().get(i)).setStats(object.getStats());
                                    }
                                }
                            }
                        }
                        
                    }
                }
                
                setFs(null);
                Main.gamestate = 0;
            }
            else{
                getFs().act(f);
                getFs().draw();
            }
        }
        
        getRenderer().render(getLayersAbovePlayer());
        
        for(Actor a : getStage().getActors()){
            if(a instanceof Textbox){
                getStage().getBatch().begin();
                a.draw(getStage().getBatch(), f);
                getStage().getBatch().end();
            }
        }
        
        if(Main.gamestate == 1) {
            Textbox t = null;
            for(Actor a : getStage().getActors()){
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
        
        if(getT() != null){
            getT().draw(getStage().getBatch(), getStage().getCamera().position.x, getStage().getCamera().position.y, getStage().getCamera().combined);
            if(getT().opacity == 0){
                setT(null);
            }
        }
    }
    
    public void resize(int width, int height){
        getStage().getViewport().update(width, height, false);
    }
    
    public Player getPlayer(){
        for(Actor a : getStage().getActors()){
            if(a instanceof Player){
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

    /**
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * @return the camera
     */
    public OrthographicCamera getCamera() {
        return camera;
    }

    /**
     * @param camera the camera to set
     */
    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    /**
     * @return the maploader
     */
    public TmxMapLoader getMaploader() {
        return maploader;
    }

    /**
     * @param maploader the maploader to set
     */
    public void setMaploader(TmxMapLoader maploader) {
        this.maploader = maploader;
    }

    /**
     * @return the map
     */
    public TiledMap getMap() {
        return map;
    }

    /**
     * @param map the map to set
     */
    public void setMap(TiledMap map) {
        this.map = map;
    }

    /**
     * @return the renderer
     */
    public OrthogonalTiledMapRenderer getRenderer() {
        return renderer;
    }

    /**
     * @param renderer the renderer to set
     */
    public void setRenderer(OrthogonalTiledMapRenderer renderer) {
        this.renderer = renderer;
    }

    /**
     * @return the doors
     */
    public Door[] getDoors() {
        return doors;
    }

    /**
     * @param doors the doors to set
     */
    public void setDoors(Door[] doors) {
        this.doors = doors;
    }

    /**
     * @return the collidingDoor
     */
    public Door getCollidingDoor() {
        return collidingDoor;
    }

    /**
     * @param collidingDoor the collidingDoor to set
     */
    public void setCollidingDoor(Door collidingDoor) {
        this.collidingDoor = collidingDoor;
    }

    /**
     * @return the fs
     */
    public FightScreen getFs() {
        return fs;
    }

    /**
     * @param fs the fs to set
     */
    public void setFs(FightScreen fs) {
        this.fs = fs;
    }

    /**
     * @return the t
     */
    public TransitionScreen getT() {
        return t;
    }

    /**
     * @param t the t to set
     */
    public void setT(TransitionScreen t) {
        this.t = t;
    }

    /**
     * @return the layersBelowPlayer
     */
    public int[] getLayersBelowPlayer() {
        return layersBelowPlayer;
    }

    /**
     * @param layersBelowPlayer the layersBelowPlayer to set
     */
    public void setLayersBelowPlayer(int[] layersBelowPlayer) {
        this.layersBelowPlayer = layersBelowPlayer;
    }

    /**
     * @return the layersAbovePlayer
     */
    public int[] getLayersAbovePlayer() {
        return layersAbovePlayer;
    }

    /**
     * @param layersAbovePlayer the layersAbovePlayer to set
     */
    public void setLayersAbovePlayer(int[] layersAbovePlayer) {
        this.layersAbovePlayer = layersAbovePlayer;
    }
    
    
	
    
}
