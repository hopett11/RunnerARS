import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

import java.util.Random;

public class Foe extends AnimatedThing{

    private int mode;

    public Foe(){
        super("cactus.png");
    }

    public void update(Hero hero){
        this.setY(300-hero.getY()+55*mode);
        if (mode==1){
            getImageView().setImage(new Image("/ARSart/cactusroto.png"));
            getImageView().setFitHeight(55);
        }
    }


    public void updateX(boolean generatefoes) {
        Random rand = new Random();
        if (generatefoes) {
            this.setX((3+rand.nextInt(12 ))*200);
            getImageView().setImage(new Image("/ARSart/cactus.png"));
            getImageView().setFitHeight(110);
            mode=0;
        }
    }

    public Rectangle2D getHitBox(){
        hitbox = new Rectangle2D(getImageView().getX(),getImageView().getY(),getImageView().getFitWidth()/2,getImageView().getFitHeight()/2);
        //getImageView().setViewport(hitbox);
        return(hitbox);

    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
