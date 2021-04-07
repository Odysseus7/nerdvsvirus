package NerdvsVirus;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
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
        }, KeyCode.A);
        getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                player.getComponent(AnimationComponent.class).right();
            }
        }, KeyCode.D);
        getInput().addAction(new UserAction("Jump") {
            @Override
            protected void onAction() {
                player.getComponent(AnimationComponent.class).jump();
            }
        }, KeyCode.W);
    }


    @Override
    protected void initGame(){
        getGameWorld().addEntityFactory(new NerdFactory());
        FXGL.setLevelFromMap("NerdStart.tmx");
        spawn("background");
        player = getGameWorld().spawn("player", 50, 50);
        getGameScene().getViewport().setBounds(-1500, 0, 1500, getAppHeight());
        getGameScene().getViewport().bindToEntity(player, getAppWidth() /2, getAppHeight()/ 2) ;
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
        System.out.println("HALLO");
    }
}
