import com.almasb.fxgl.animation.Animation;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.core.util.EmptyRunnable;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.scene.CSS;
import com.almasb.fxgl.ui.FontType;
import javafx.beans.binding.Bindings;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGLForKtKt.inc;

public class Game extends GameApplication {

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(1200);
        gameSettings.setHeight(800);
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
    protected void initGame(){
        player = FXGL.entityBuilder()
                .at(300, 300)
                .view("/Game/sprite.png")
                .buildAndAttach();
    }

    @Override
    protected void initInput(){
        FXGL.onKey(KeyCode.D, () -> {
            player.translateX(5);
            inc("pixelsMoved", +5);
        });
        FXGL.onKey(KeyCode.A, () -> {
            player.translateX(-5);
            inc("pixelsMoved", -5);
        });

    }

    @Override
    protected void initUI() {


    }

    @Override
    protected void initGameVars(Map<String, Object> vars){
        vars.put("pixelsMoved", 0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
