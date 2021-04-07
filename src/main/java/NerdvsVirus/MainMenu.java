package NerdvsVirus;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.InputStream;


public class MainMenu extends FXGLMenu {

    public MainMenu() {
        super(MenuType.MAIN_MENU);

        var startButton = new StartButton("Start new game", this::fireNewGame);
        startButton.setTranslateX(FXGL.getAppWidth() / 2 - 200 / 2);
        startButton.setTranslateY(FXGL.getAppHeight() / 2 - 40 / 2);

        getContentRoot().getChildren().add(startButton);
    }


    private static class StartButton extends StackPane {
        public StartButton(String name, Runnable action) {

            var bg = new Rectangle(200, 40);
            bg.setStroke(Color.WHITE);

            var text = FXGL.getUIFactoryService().newText(name, Color.WHITE, 18);

            bg.fillProperty().bind(
                    Bindings.when(hoverProperty()).then(Color.WHITE).otherwise(Color.BLACK)
            );

            text.fillProperty().bind(
                    Bindings.when(hoverProperty()).then(Color.BLACK).otherwise(Color.WHITE)
            );

            setOnMouseClicked(e -> action.run());

            getChildren().addAll(bg, text);
        }
    }
}
