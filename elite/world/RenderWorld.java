package com.elite.world;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/*
@author Minhal Khan
*/
public class RenderWorld implements Screen{

    //**Map Rendering**//
    protected TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    protected OrthographicCamera camera;
    Vector3 mouse_position = new Vector3(0,0,0);
    private FitViewport viewport;
    private TextureRegion backgroundTexture;
    private BitmapFont timeText = new BitmapFont();

    public SpriteBatch spriteBatch = new SpriteBatch();

    private Box2DDebugRenderer debugRenderer;
    /*
    //pause screen
	private Window pause;
	private TextButton pauseButton;
	private Stage stage;
	private Skin menuSkin, skin2;
*/

    //**Called by Zombie Wars create method**/
    /** Called when this screen becomes the current screen for a {@link Game}. */
    public void show (){
        debugRenderer = new Box2DDebugRenderer();
        System.out.println("Showing world");
        backgroundTexture = new TextureRegion(new Texture("maps/BG.png"), 0, 0, WorldAttributes.WORLD_WIDTH, WorldAttributes.WORLD_HEIGHT);
        map =  new TmxMapLoader().load("maps/finalmap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        viewport = new FitViewport(WorldAttributes.WORLD_WIDTH, WorldAttributes.WORLD_HEIGHT, camera);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        viewport.getCamera().position.set(w, h, 0);
        camera.update();


        //pause screen
		/*stage = new Stage(viewport);
		menuSkin = new  Skin(Gdx.files.internal("menu_skin/menuButton.json"));
		skin2 = new Skin(Gdx.files.internal("setting_skin/glassy-ui.json"));

		pause = new Window("Pause Screen", skin2);

		pauseButton = new TextButton("pause", menuSkin, "carterone");
		pauseButton.setPosition(0, 0);
		pauseButton.setWidth(200f);
		pauseButton.setHeight(80);
		
		TextButton ResumeButton = new TextButton("Resume", menuSkin, "carterone");
		
		
		pauseButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				pause.setVisible(true);			
			}
		});
		
	
		ResumeButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				pause.setVisible(false);			
			}
		});
		
		pause.add(ResumeButton).row();
		pause.add(new TextButton("Setting", menuSkin, "carterone")).row();
		pause.add(new TextButton("Exit", menuSkin, "carterone")).row();
		pause.setSize(400, 500);

		pause.setPosition(stage.getWidth()/2 - pause.getWidth()/2, stage.getHeight()/2 - pause.getHeight()/2);
		pause.setMovable(false);
		pause.setVisible(false);		
		
		stage.addActor(pauseButton);
		stage.addActor(pause);
*/
    }

    //Main Loop
    /** Called when the screen should render itself.
     * @param delta The time in seconds since the last render. */
    public void render(float delta){

        //Gdx.gl.glClearColor(0, 0, 0, 1);
        //Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        //System.out.println("Rendering world");
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //spriteBatch.begin();
        //spriteBatch.draw(backgroundTexture,0,0);
        //spriteBatch.end();
        camera.update();
        renderer.setView(camera);
        renderer.render();
        //debugRenderer.setDrawBodies(true);
        //debugRenderer.setDrawContacts(false);
        //debugRenderer.render(SpriteRenderer.world, camera.combined);



        mouse_position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mouse_position);

        //pause screen
    //    if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
    	//		pause.setVisible(true);		

   //     }
    //    stage.draw();


    }

    public void resize(int width, int height){
        viewport.update(width, height, true);
        camera.update();
        
    }

    public void pause (){



    }

    public void resume (){



    }

    /** Called when this screen is no longer the current screen for a {@link Game}. */
    public void hide (){
        dispose();
    }

    /** Called when this screen should release all resources. */
    public void dispose(){
        map.dispose();
        renderer.dispose();
   
    }



}
