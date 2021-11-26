import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RunnerPauseScene extends SubScene {

    final String BACKGROUND_IMAGE = "/resources/fondo.png";
    private final String FONT_PATH = "/resources/alagard.ttf";

    private boolean isHidden;
    private double score = 0;


    public RunnerPauseScene() {
        super(new AnchorPane(), 400, 200);
        prefWidth(400);
        prefHeight(200);

        BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, 400, 200, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);

        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.setBackground(new Background(image));

        isHidden = true;

        setLayoutX(125);
        setLayoutY(300);

        getPane().getChildren().add(createButtonToMenu());
        createPointsText();

        Label myText = new Label("GAME OVER");
        myText.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 50));
        myText.setStyle("-fx-text-fill: white;");
        myText.setLayoutX(70);
        myText.setLayoutY(0);
        getPane().getChildren().add(myText);


    }

    private RunnerButton createButtonToMenu() {
        RunnerButton startButton = new RunnerButton("Menu");
        startButton.setLayoutX(200);
        startButton.setLayoutY(70);


        startButton.setOnAction(event -> {


        });

        return startButton;
    }

    public void createButtonToExit(Stage gameStage, Stage menuStage) {
        RunnerButton startButton = new RunnerButton("Exit");
        startButton.setLayoutX(0);
        startButton.setLayoutY(70);


        startButton.setOnAction(event -> {
            gameStage.close();
            menuStage.close();

        });
        getPane().getChildren().add(startButton);
    }

    public void createPointsText() {
        Label pointsText = new Label("SCORE: " + score);
        pointsText.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 20));
        pointsText.setStyle("-fx-text-fill: white;");
        pointsText.setLayoutX(20);
        pointsText.setLayoutY(150);
        getPane().getChildren().add(pointsText);
    }

    public void updateScore(double newscore){
        score=newscore;
        System.out.println(score);
    }

    public void moveSubScene() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(this);

        if (isHidden) {

            transition.setToY(-250);
            isHidden = false;

        } else {

            transition.setToY(0);
            isHidden = true;
        }


        transition.play();
    }

    public AnchorPane getPane() {
        return (AnchorPane) this.getRoot();
    }
}
