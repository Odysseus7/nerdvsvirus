package NerdvsVirus;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.Bindings;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.*;


public class MainMenu extends FXGLMenu {
    public static String name;

    private void inputName() {
        TextField textField = new TextField();
        Text intro = new Text("Wat is jouw naam?");

        intro.setTranslateX(FXGL.getAppWidth() / 2.0 - 75);
        intro.setTranslateY(FXGL.getAppHeight() / 2.0 - 70);
        intro.setFont(Font.font("Arial", 20));

        textField.setTranslateX(FXGL.getAppWidth() / 2.0 - 70);
        textField.setTranslateY(FXGL.getAppHeight() / 2.0 - 30);

        Button button = new Button("speel", "#3498db", "#2980b9", this::fireNewGame);
        button.setTranslateX(FXGL.getAppWidth() / 2.0 - 55);
        button.setTranslateY(FXGL.getAppHeight() / 2.0 + 30);

        button.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> name = textField.getText());

        getContentRoot().getChildren().clear();
        getContentRoot().getChildren().addAll(intro, textField, button);
    }

    public MainMenu() {
        super(MenuType.MAIN_MENU);
        getContentRoot().getStylesheets().add(getClass().getResource("../css/styles.css").toExternalForm());

        Button startButton = new Button("Play", "#b0d876", "#90b855", this::inputName);
        Button scoreButton = new Button("Score", "#3498db", "#2980b9", () -> {});
        Button exitButton = new Button("Exit", "#d65c5b", "#b54948", () -> System.exit(0));

        InputStream input = getClass().getResourceAsStream("../assets/textures/Start/logo.png");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);

        imageView.setTranslateX(FXGL.getAppWidth() / 2.0  - 400.0 / 2);
        imageView.setTranslateY(FXGL.getAppHeight() / 2.0  - 210);

        startButton.setTranslateX(FXGL.getAppWidth() / 2.0  - 125.0  / 2);
        startButton.setTranslateY(FXGL.getAppHeight() / 2.0  - 35);

        scoreButton.setTranslateX(FXGL.getAppWidth() / 2.0  - 125.0 / 2);
        scoreButton.setTranslateY(FXGL.getAppHeight() / 2.0  + 30);

        exitButton.setTranslateX(FXGL.getAppWidth() / 2.0  - 125.0 / 2);
        exitButton.setTranslateY(FXGL.getAppHeight() / 2.0  + 95);

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

            setOnMouseClicked(e -> action.run());
        }
    }
}
