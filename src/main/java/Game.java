import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;

import java.util.Map;

public class Game extends GameApplication {

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(1200);
        gameSettings.setHeight(800);
        gameSettings.setTitle("Nerds VS Virus");
    }

    @Override
    protected void initGame(){

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
