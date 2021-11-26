import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class StaticThing {
    protected double x;
    protected double y;
    protected ImageView sprite;

    public StaticThing(String fileName) {
        Image spriteSheet = new Image("/ARSart/"+fileName);
        this.sprite = new ImageView(spriteSheet);


    }

    public ImageView getImageView(){
        return sprite;
    }

}
