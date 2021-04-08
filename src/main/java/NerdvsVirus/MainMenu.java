package NerdvsVirus;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.*;


public class MainMenu extends FXGLMenu {
    public MainMenu() {
        super(MenuType.MAIN_MENU);
        getContentRoot().getStylesheets().add(getClass().getResource("../css/styles.css").toExternalForm());

        Button startButton = new Button("Play", "#b0d876", "#90b855", this::fireNewGame);
        Button scoreButton = new Button("Score", "#3498db", "#2980b9", () -> {});
        Button exitButton = new Button("Exit", "#d65c5b", "#b54948", () -> {System.exit(0);});


        InputStream input = getClass().getResourceAsStream("../assets/textures/Start/logo.png");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);

        imageView.setTranslateX(FXGL.getAppWidth() / 2 - 447 / 2);
        imageView.setTranslateY(FXGL.getAppHeight() / 2 - 210);


        startButton.setTranslateX(FXGL.getAppWidth() / 2 - 125 / 2);
        startButton.setTranslateY(FXGL.getAppHeight() / 2 - 35);

        scoreButton.setTranslateX(FXGL.getAppWidth() / 2 - 125 / 2);
        scoreButton.setTranslateY(FXGL.getAppHeight() / 2 + 30);

        exitButton.setTranslateX(FXGL.getAppWidth() / 2 - 125 / 2);
        exitButton.setTranslateY(FXGL.getAppHeight() / 2 + 95);

        getContentRoot().setPrefWidth(1200);
        getContentRoot().setPrefHeight(800);
        getContentRoot().setId("menu");
        getContentRoot().getChildren().addAll(imageView, startButton, scoreButton, exitButton);
    }


    private static class Button extends StackPane {
        public Button(String name, String color, String hoverColor, Runnable action) {
            name = name.toUpperCase();
            var bg = new Rectangle(125, 40);
            bg.setId("start");
            bg.setFill(Color.web(color));
            bg.setStroke(Color.web("#0d1c42"));
            bg.setStyle("-fx-stroke-width: 3");
            bg.setArcWidth(15);
            bg.setArcHeight(15);

            InputStream fontStream = getClass().getResourceAsStream("../assets/fonts/RetroGaming.ttf");
            try {
                Font bgFont = Font.loadFont(fontStream, 20);
                fontStream.close();

                Text text = new Text(name);

                text.setFont(bgFont);


                getChildren().addAll(bg, text);
            } catch (IOException ioe){
                System.out.println(ioe.getMessage());
            }

            bg.fillProperty().bind(
                    Bindings.when(hoverProperty()).then(Color.web(hoverColor)).otherwise(Color.web(color))
            );
//
//            text.fillProperty().bind(
//                    Bindings.when(hoverProperty()).then(Color.BLACK).otherwise(Color.WHITE)
//            );

            setOnMouseClicked(e -> action.run());

        }
    }
}
