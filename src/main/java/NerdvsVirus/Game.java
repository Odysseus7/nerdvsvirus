package NerdvsVirus;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.input.KeyCode;

import NerdvsVirus.MainMenu.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static NerdvsVirus.NerdType.*;
public class Game extends GameApplication {

    @Override
    protected void initSettings(GameSettings gameSettings) {
        //hier wordt de grote van van het scherm belaalt op basis van de groote van de tegels
        gameSettings.setWidth(15 * 70);
        gameSettings.setHeight(10 * 70);
        gameSettings.setTitle("Nerds VS Virus");

        gameSettings.setMainMenuEnabled(true);
        gameSettings.setSceneFactory(new SceneFactory() {
            @Override
            public FXGLMenu newMainMenu() {
                return new MainMenu();
            }
        });
    }

    private Entity player;

    @Override
    protected void initInput(){
        getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                player.getComponent(AnimationComponent.class).left();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(AnimationComponent.class).stop();
            }

        }, KeyCode.A);
        getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                player.getComponent(AnimationComponent.class).right();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(AnimationComponent.class).stop();
            }

        }, KeyCode.D);
        getInput().addAction(new UserAction("Jump") {
            @Override
            protected void onActionBegin() {
                player.getComponent(AnimationComponent.class).jump();
            }
        }, KeyCode.W);

//        getInput().addAction(new UserAction("Use") {
//            @Override
//            protected void onActionBegin() {
//                getGameWorld().getEntitiesByType(BUTTON)
//                        .stream()
//                        .filter(btn -> btn.hasComponent(CollidableComponent.class) && player.isColliding(btn))
//                        .forEach(btn ->{
//                            int doorx = btn.getInt("doorX");
//                            int doory = btn.getInt("doorY");
//
//                            spawn("door", new SpawnData(doorx, doory).put("width", 70).put( "height", 100));
//                        });
//            }
//        }, KeyCode.E);

    }


    @Override
    protected void initGame(){
        getGameWorld().addEntityFactory(new NerdFactory());
        FXGL.setLevelFromMap("NerdStart.tmx");

        spawn("background");
        //set game
        player = getGameWorld().spawn("player", 50, 50);
        //Set camera volgen player
        getGameScene().getViewport().setBounds(-1500, 0, 3000, getAppHeight());
        getGameScene().getViewport().bindToEntity(player, getAppWidth() /2, getAppHeight()/ 2) ;

        getGameWorld().spawn("enemy", 470, 50);

    }
    @Override
    protected void initPhysics(){
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(PLAYER, SPUIT) {
            @Override
            protected void onCollisionBegin(Entity player, Entity spuit) {
                spuit.removeFromWorld();
            }
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}
