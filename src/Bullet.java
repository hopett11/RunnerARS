import javafx.animation.TranslateTransition;
import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.util.Duration;

public class Bullet extends AnimatedThing{
    
    private Node mynode;
    private  boolean isHidden;

    public Bullet(){
        super("heros.png");
        setViewXY(530, 365,30,30);
    }

    public void moveBullet() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(1));
        transition.setNode(this.getImageView());
        transition.setFromX(-540);
        transition.setToX(0);

        TranslateTransition preparetransition = new TranslateTransition();
        preparetransition.setDuration(Duration.seconds(0.3));
        preparetransition.setNode(this.getImageView());
        preparetransition.setFromX(0);
        preparetransition.setToX(1);
        preparetransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                transition.play();
            }
                                        });



        preparetransition.play();

        //getImageView().setX();
    }

    public void setNode(Node node){
        mynode=node;
    }

    public Rectangle2D getHitBox(){
        hitbox = new Rectangle2D(getImageView().getX(),getImageView().getY(),getImageView().getViewport().getWidth()/2,getImageView().getViewport().getHeight()/2);
        //getImageView().setViewport(hitbox);
        return(hitbox);
    }

}
