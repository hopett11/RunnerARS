import javafx.geometry.Rectangle2D;

public class AnimatedThing extends StaticThing{
    int attitude;
    Rectangle2D hitbox;

    public AnimatedThing(String fileName){
        super(fileName);

    }

    public void setViewXY(double x, double y, double w, double h){
        this.getImageView().setViewport(new Rectangle2D(x,y,w,h));
    }

    public void setX(double x){
        this.getImageView().setX(x);
    }

    public void setY(double y){
        this.getImageView().setY(y);
    }

    public double getX(){
        return(this.getImageView().getX());

    }

    public double getY(){
        return(this.getImageView().getY());
    }

}
