package NerdvsVirus;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.Texture;
import javafx.animation.Animation;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;

import NerdvsVirus.MainMenu.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.getSettings;
import static com.almasb.fxgl.dsl.FXGL.geti;
import static com.almasb.fxgl.dsl.FXGL.inc;
import static com.almasb.fxgl.dsl.FXGL.loopBGM;
import static com.almasb.fxgl.dsl.FXGL.runOnce;
import static com.almasb.fxgl.dsl.FXGL.showMessage;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static NerdvsVirus.NerdType.*;
public class Game extends GameApplication {
    private static final int MAX_LEVEL = 3;
    private static final int STARTING_LEVEL = 0;
    private List<Texture> lives = new ArrayList<>();
    @Override
    protected void initSettings(GameSettings gameSettings) {
        //hier wordt de grote van van het scherm belaalt op basis van de groote van de tegels
        gameSettings.setWidth(15 * 70);
        gameSettings.setHeight(10 * 70);
        gameSettings.setTitle("Nerds VS Virus");

        gameSettings.setMainMenuEnabled(true);
        gameSettings.setSceneFactory(new SceneFactory() {
            @NotNull
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
    protected void initGameVars(Map<String, Object> vars){
        //maakt variablen aan voor het veranderen van levels
        vars.put("level", STARTING_LEVEL);
        vars.put("levelTime", 0.0);
        vars.put("score", 0);
        vars.put("leven", 5);
    }

    @Override
    protected void initGame(){
        getGameState().<Integer>addListener("leven", (prev, now) ->{
            if (now == 0){
                //moet aangepast worden zodat bij game over terug gaat naar game menu
                getDisplay().showMessageBox("Game over", () -> { getGameController().gotoMainMenu();});
            }
        });
        //maakt werelden aan via de classe NerdFactory
        getGameWorld().addEntityFactory(new NerdFactory());
        addLive();
        player = null;
        nextLevel();

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
                inc("leven", +1);
                addLive();
                play("life.wav");
                spuit.removeFromWorld();
            }
        });
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(PLAYER, ENEMY) {
            @Override
            protected void onCollisionBegin(Entity player, Entity enemy) {
                inc("leven", -1);
                removeLive();
                play("impact.wav");
//                getGameWorld().getEntitiesCopy().forEach(Entity::removeFromWorld);

                System.out.println(geti("leven"));
            }
        });
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(PLAYER, DOOR) {
            @Override
            protected void onCollisionBegin(Entity player, Entity spuit) {
                getDisplay().showMessageBox("Level Complete", () ->{
                    System.out.println("Dialog closed!");
                    nextLevel();
                });
            }
        });
    }
    @Override
    protected void initUI(){
                FXGL.addVarText("leven", 20, 20);
    }

    public void addLive(){
        int numlifes = lives.size();
        Texture texture = getAssetLoader().loadTexture("hart.png", 30, 30);
        texture.setTranslateX(20 + 32 * numlifes);
        texture.setTranslateY(40);
        lives.add(texture);
        FXGL.getGameScene().addUINode(texture);
    }
    public void removeLive(){
        Texture t = lives.get(lives.size() - 1);
        getGameScene().removeUINode(t);
    }
    private void nextLevel(){
        if (geti("level") == MAX_LEVEL) {
            showMessage("Je hebt het virus verslagen");
            return;
        }
        inc("level", +1);

        setLevel(geti("level"));
    }

    //zorgt er voor dat levels door gaan naar volgende level
    private void setLevel(int levelNum){
        if (player != null){
            player.getComponent(PhysicsComponent.class).overwritePosition(new Point2D(50, 50));
            player.setZIndex(Integer.MAX_VALUE);
        }
        spawn("background"+geti("level"));
        Level level = FXGL.setLevelFromMap("NerdStart" + levelNum + ".tmx");

    }

    @Override
    protected void onPreInit() {
        getSettings().setGlobalMusicVolume(0.10);
        loopBGM("background.wav");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
