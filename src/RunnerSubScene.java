import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.util.Duration;

public class RunnerSubScene extends SubScene{

    final String BACKGROUND_IMAGE = "/resources/fondo.png";

    private  boolean isHidden;


    public RunnerSubScene() {
        super(new AnchorPane(), 600, 400);
        prefWidth(600);
        prefHeight(300);

        BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, 600, 300, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);

        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.setBackground(new Background(image));

        isHidden = true ;

        setLayoutX(700);
        setLayoutY(280);

    }

    public void moveSubScene() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(this);

        if (isHidden) {

            transition.setToX(-676);
            isHidden = false;

        } else {

            transition.setToX(0);
            isHidden = true ;
        }


        transition.play();
    }

    public AnchorPane getPane() {
        return (AnchorPane) this.getRoot();
    }

}

