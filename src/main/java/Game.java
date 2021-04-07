package NerdGame;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;

import NerdGame.NerdType.*;
import NerdGame.NerdFactory.*;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

public class Game extends GameApplication {
    private Entity player;
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(1280);
        gameSettings.setHeight(720);
        gameSettings.setTitle("Nerds VS Virus");
    }


    @Override
    protected void initGame(){
        getGameWorld().addEntityFactory(new NerdFactory());
        spawn("background");
        // create player here
    }

    @Override
    protected void initInput(){

    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initGameVars(Map<String, Object> vars){

    }

    public static void main(String[] args) {
        launch(args);
    }
}
