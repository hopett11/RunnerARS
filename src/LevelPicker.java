import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class LevelPicker extends VBox {

    final ImageView circleImage;
    final ImageView heroImage;

    final String circleNotChoosen = "C:\\Users\\ARS\\Documents\\2A\\INFO\\Java\\RunnerOldARS\\src\\img\\grey_circle.png";
    final String circleChoosen = "C:\\Users\\ARS\\Documents\\2A\\INFO\\Java\\RunnerOldARS\\src\\img\\red_choosen.png";

    final String mapString;

    private boolean isCircleChoosen;


    public LevelPicker(String mapstring) {
        circleImage = new ImageView(circleNotChoosen);
        heroImage = new ImageView("img/logo"+mapstring);
        //heroImage.setViewport(new Rectangle2D(0,200,250,130));
        isCircleChoosen = false;
        this.mapString=mapstring;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.getChildren().add(circleImage);
        this.getChildren().add(heroImage);

    }

    public String getMap() {
        return mapString;
    }

    public void setIsCircleChoosen(boolean isCircleChoosen) {
        this.isCircleChoosen = isCircleChoosen;
        String imageToSet = this.isCircleChoosen ? circleChoosen : circleNotChoosen;
        circleImage.setImage(new Image(imageToSet));
    }
}
