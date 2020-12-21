package com.trs.main;

import com.badlogic.gdx.Gdx;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MapContainer {
        
    
	Stage stage;
	OrthographicCamera camera;
	TmxMapLoader maploader;
        TiledMap map;
        OrthogonalTiledMapRenderer renderer;
	ArrayList<Door> doors = new ArrayList<>();
	
        // TODO: Value which shows from which door the player is coming?
    public MapContainer(float CAMERA_WIDTH, float CAMERA_HEIGHT, Player p, String mapString) {
        // CREATION OF STAGE
        camera = new OrthographicCamera();
        camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
        camera.update();
        stage = new Stage(new FitViewport(CAMERA_WIDTH, CAMERA_HEIGHT, camera));
        Gdx.input.setInputProcessor(stage);
        
        // set position according to the door the player is coming from
        p.setPosition(64, 64);
        
        stage.addActor(p);
        stage.addActor(new MovingNpc(new Rectangle(20,20,400,400), 64, 64));
        
        //CREATION OF TILEDMAP
        maploader = new TmxMapLoader();
        map = maploader.load(mapString);
        renderer = new OrthogonalTiledMapRenderer(map);
        renderer.setView((OrthographicCamera)stage.getCamera());
        stage.getCamera().update();
        
        // adding MapObjects to the Stage
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            stage.addActor(new MapCollisionObject((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight()));
        }
        
        // adding the door links
    	for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            MapProperties props = object.getProperties();
            //Door door = new Door(props.get("id"), props.get("destinationMap"), props.get("destinationDoor"), rect);
            
            //System.out.println("ID: " + props.get("ID", Integer.class));
            //System.out.println("ID: " + props.get("y"));
        }
    }
        
    public void render(float f){
        renderer.setView((OrthographicCamera)stage.getCamera());
        renderer.render();
        
        stage.act(f);
        stage.draw();
        if(Main.gamestate == 1) {
            Textbox t = null;
            for(Actor a : stage.getActors()){
                if(a.getName().equals("textbox")){
                    t = (Textbox)a;
                    if(t.getState() == 2){
                        a.remove();
                        Main.gamestate = 0;
                        t.getSelectedAsw(); // DO STUFF NICENICE
                    }
                }
            }
        }
        
        // center camera
        for(Actor a : stage.getActors()){
            if(a.getName().equals("player")){
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
	
}
