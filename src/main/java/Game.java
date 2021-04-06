import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGLForKtKt.inc;

public class Game extends GameApplication {

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(1200);
        gameSettings.setHeight(800);
        gameSettings.setTitle("Nerds VS Virus");
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
//        Text textPixels = new Text();
//        textPixels.setTranslateX(50); // x = 50
//        textPixels.setTranslateY(100); // y = 100
//        textPixels.textProperty().bind(FXGL.getWorldProperties().intProperty("pixelsMoved").asString());
//        FXGL.getGameScene().addUINode(textPixels);
//
//        var spriteTexture = FXGL.getAssetLoader().loadTexture("sprite.png");
//        spriteTexture.setTranslateX(50);
//        spriteTexture.setTranslateY(100);
//        FXGL.getGameScene().addUINode(spriteTexture);

    }

    @Override
    protected void initGameVars(Map<String, Object> vars){
        vars.put("pixelsMoved", 0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
