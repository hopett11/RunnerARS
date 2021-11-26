import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class HeroPicker extends VBox {

    final ImageView circleImage;
    final ImageView heroImage;

    final String circleNotChoosen = "/ARSart/grey_circle.png";
    final String circleChoosen = "/ARSart/red_choosen.png";

    final HeroColor herocolor;

    private boolean isCircleChoosen;


    public HeroPicker(HeroColor herocolor) {
        circleImage = new ImageView(circleNotChoosen);
        heroImage = new ImageView(herocolor.getImg());
        heroImage.setViewport(new Rectangle2D(20,0,60,100));
        this.herocolor = herocolor;
        isCircleChoosen = false;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.getChildren().add(circleImage);
        this.getChildren().add(heroImage);

    }

    public HeroColor getHero() {
        return herocolor;
    }


    public void setIsCircleChoosen(boolean isCircleChoosen) {
        this.isCircleChoosen = isCircleChoosen;
        String imageToSet = this.isCircleChoosen ? circleChoosen : circleNotChoosen;
        circleImage.setImage(new Image(imageToSet));
    }
}
