package com.trs.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main extends ApplicationAdapter implements InputProcessor{
    
    /**
     * TODO: set final Value for World
     */
	
        OrthographicCamera camera;
        Stage stage;
        Rectangle viewportCheck;
        ShapeRenderer renderer;
        
    
	@Override
	public void create () {
            renderer = new ShapeRenderer();
            viewportCheck = new Rectangle(0, 0, 800f, 800f);
            camera = new OrthographicCamera();
            camera.setToOrtho(false, 800f, 800f);
            camera.update();
            stage = new Stage(new FitViewport(800f,800f, camera));
            Player p = new Player();
            stage.addActor(p);
            stage.setKeyboardFocus(p);
            renderer.setProjectionMatrix(camera.combined);
            Gdx.input.setInputProcessor(stage);
        }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
        super.resize(width, height); //To change body of generated methods, choose Tools | Templates.
    }
    
        

	@Override
	public void render () {
		Gdx.gl.glClearColor(1f, (20f/255f), (147f/255f), 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                renderer.begin(ShapeRenderer.ShapeType.Filled);
                renderer.setColor(Color.WHITE);
                renderer.rect(viewportCheck.getX(), viewportCheck.getY(), viewportCheck.getWidth(), viewportCheck.getHeight());
                renderer.setColor(Color.BLACK);
                Vector2 mouseRel = stage.getViewport().unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
                renderer.circle(mouseRel.x,mouseRel.y, 20);
                renderer.end();
                
                
                stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	
	@Override
	public void dispose () {
	}

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float f, float f1) {
        return false;
    }
}
