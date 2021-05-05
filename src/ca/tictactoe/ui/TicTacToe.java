package ca.tictactoe.ui;

import ca.tictactoe.game.TicTacToeController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Main class for the JavaFX Application of Tic Tac Toe game.
 * Responsibility for creating all our views and elements of the game.
 * Has methods to change images or text on the screen that the controller can invoke.
 */
public class TicTacToe extends Application {

    private GridPane gridPane;
    private Label label;
    private Button button;
    private TicTacToeController myController = new TicTacToeController();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Tic Tac Toe");

        button = new Button("New Game");
        button.setStyle("-fx-background-color: rgba(77,77,77,0.75);" +
                "-fx-background-radius: 10;" +
                "-fx-font-size: 15;" +
                "-fx-text-fill: white; ");
        button.setOnAction(actionEvent -> myController.resetGame());

        gridPane = initGridPane();
        label = new Label(String.format("It is %s's turn!", myController.getCurrentPlayer()));
        label.setStyle("-fx-background-color: rgba(38,38,58,0.75); " +
                "-fx-background-radius: 20;" +
                "-fx-text-fill: white; " +
                "-fx-font-size: 20;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 5 10 5 10");

        VBox vbox = new VBox(gridPane, label, button);
        Image image = new Image("file:img/sky2.png");
        vbox.setBackground(new Background(new BackgroundImage(image,null,null,null,null)));
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(50));

        myController.setGameUI(this);

        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void resetPictures() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                changeGridImage("", i, j);
            }
        }
    }

    private GridPane initGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setOnMouseClicked(mouseEvent -> myController.clickGrid(gridPane, mouseEvent));
        gridPane.setStyle("-fx-background-color: rgba(38,38,58,0.75); -fx-background-radius: 20");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                gridPane.add(getBgImage(), i, j);
            }
        }
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(20));
        return gridPane;
    }

    public void changeGridImage(String character, int row, int col) {
        if (character.equals(myController.getxPlayerName())) {
            gridPane.add(getXImage(), col, row);
        } else if (character.equals(myController.getoPlayerName())) {
            gridPane.add(getOImage(), col, row);
        } else if (character.isEmpty()){
            gridPane.add(getBgImage(), col, row);
        }
    }

    public void changeLabelText(String text) {
        label.setText(text);
    }

    private ImageView getXImage() {
        Image imageX = new Image("file:img/sonic.png");
        ImageView xImageView = new ImageView(imageX);
        xImageView.setPreserveRatio(true);
        xImageView.setFitWidth(100);
        roundImage(xImageView);
        return xImageView;
    }

    private ImageView getOImage() {
        Image imageO = new Image("file:img/knuckles.png");
        ImageView oImageView = new ImageView(imageO);
        oImageView.setPreserveRatio(true);
        oImageView.setFitWidth(100);
        roundImage(oImageView);
        return oImageView;
    }


    private ImageView getBgImage() {
        Image imageBg = new Image("file:img/bg.png");
        ImageView bgImageView = new ImageView(imageBg);
        bgImageView.setPreserveRatio(true);
        bgImageView.setFitWidth(100);
        roundImage(bgImageView);
        return bgImageView;
    }

    private void roundImage(ImageView imageView) {
        Rectangle rectangle = new Rectangle(imageView.getFitWidth(), imageView.getFitWidth());
        rectangle.setArcWidth(10);
        rectangle.setArcHeight(10);
        imageView.setClip(rectangle);
    }
}
