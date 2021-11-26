import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

public class Hero extends AnimatedThing{

    private Timeline timeline;
    private Timeline runshoottimeline;
    private int invincibility=0;
    private long timeLastUpdate;
    private long timeNow;
    public double speedNowy;
    public double accelerationNowy;
    private long elapsedNanoSeconds;
    private double elapsedSeconds;
    final double g=800;
    private double accelerationjumpy=0;
    public boolean isrealspacePressed;
    public AnimatedThing ouch;

    public Hero(String herocolour){
        super(herocolour+".png");
        accelerationNowy=0;
        speedNowy=0;
        ouch = new AnimatedThing("ouch.png");
        ouch.getImageView().setFitHeight(48);
        ouch.getImageView().setFitWidth(96);
    }

    public void createrun(){
        this.timeline = new Timeline(
                new KeyFrame(Duration.millis(100), t -> setViewXY(20, 0,60,100)),
                new KeyFrame(Duration.millis(200), t -> setViewXY(95, 0,70,100)),
                new KeyFrame(Duration.millis(300), t -> setViewXY(170, 10,85,90)),
                new KeyFrame(Duration.millis(400), t -> setViewXY(270, 10,65,90)),
                new KeyFrame(Duration.millis(500), t -> setViewXY(345, 0,70,100)),
                new KeyFrame(Duration.millis(600), t -> setViewXY(425,15,80,85)));
        timeline.setCycleCount(Animation.INDEFINITE);

    }

    public void createjumpup(){
        accelerationjumpy=-320;
    }

    public void createrunshoot(){
        runshoottimeline = new Timeline(
                new KeyFrame(Duration.millis(100), t -> setViewXY(10, 335,70,90)),
                new KeyFrame(Duration.millis(200), t -> setViewXY(80, 325,80,100)),
                new KeyFrame(Duration.millis(300), t -> setViewXY(160, 340,80,85)),
                new KeyFrame(Duration.millis(400), t -> setViewXY(255, 335,70,90)),
                new KeyFrame(Duration.millis(500), t -> setViewXY(340, 330,75,95)),
                new KeyFrame(Duration.millis(500), t -> setViewXY(425, 340,80,90)),
                new KeyFrame(Duration.millis(600), t -> setViewXY(425,15,80,85)));
        runshoottimeline.setOnFinished(t -> attitude=0);

    }

    public int getInvincibility(){
        return invincibility;
    }

    public void setInvincibility(int data){
        invincibility=data;
    }

    public void update(long time){
        if(attitude==0) {
            timeline.play();
        }
        if(attitude==1) {
            timeline.stop();
            createjumpup();
            attitude=0;
        }
        if(attitude==3){
            timeline.stop();
            runshoottimeline.play();
        }
        timeNow = time;
        evaluateAccelerationy();
        evaluateVitessey();
        evaluatepositiony();
        timeLastUpdate = timeNow;
        if (getY()<150){
            if (speedNowy<0){
                setViewXY(20, 160,60,105);
            }
            else{
                setViewXY(95, 160,70,105);
            }
        }
        accelerationjumpy=0;


    }

    private void evaluatepositiony(){
        elapsedNanoSeconds = timeNow - timeLastUpdate;
        elapsedSeconds = elapsedNanoSeconds / 1_000_000_000.0;
        y += speedNowy*elapsedSeconds;
        if((getY() + speedNowy * elapsedSeconds)>150) {
            setY(150);
        }
        else{
            setY(getY() + speedNowy * elapsedSeconds);
        }
    }

    private void evaluateVitessey(){
        elapsedNanoSeconds = timeNow - timeLastUpdate;
        elapsedSeconds = elapsedNanoSeconds / 1_000_000_000.0;
        if(getY()<150) {
            speedNowy += accelerationNowy * elapsedSeconds;
        }
        else{
            speedNowy=accelerationjumpy;
        }
    }

    private void evaluateAccelerationy(){
        if(getY()<150){
            accelerationNowy= g;
        }
    }

    public void setTimeLastUpdate(long time){
        timeLastUpdate = time;
    }

    public void updateOuch(){
        if (invincibility!=0){
            ouch.setX(getX()+20);
            ouch.setY(getY()-50);
        }
        else{
            ouch.setY(400);
        }
    }

    public Rectangle2D getHitBox(){
        hitbox = new Rectangle2D(getImageView().getX(),getImageView().getY(),getImageView().getViewport().getWidth()/2,getImageView().getViewport().getHeight()/2);
        //getImageView().setViewport(hitbox);
        return(hitbox);
    }

}
