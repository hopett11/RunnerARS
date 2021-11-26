
public class Camera {
    public double x;
    private double y;
    private long timeLastUpdate;
    private long timeNow;
    public double speedNowx;
    public double accelerationNowx;
    public double speedNowy;
    public double accelerationNowy;
    private long elapsedNanoSeconds;
    private double elapsedSeconds;
    final double k;
    final double m;
    final double f;

    double dx=0;
    double dx_1=0;
    double dx_2=0;
    double dx_3=0;
    double dx_4=0;

    public Camera(double x,double y) {
        this.x = x;
        this.y = y;
        k=40;
        m=8;
        f=12;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public void moveCamera(StaticThing backgroundLeft, StaticThing backgroundRight,long time, Hero hero) {
        timeNow = time;
        backgroundLeft.getImageView().setX(-x%800);
        backgroundRight.getImageView().setX(800-x%800);
        backgroundRight.getImageView().setY(52-hero.getY());
        backgroundLeft.getImageView().setY(52-hero.getY());
        evaluateAcceleration(hero.getX(),hero.getY());
        evaluatePosition(hero);
        evaluateVitesse(hero);
        //x+=5;
        timeLastUpdate = timeNow;
    }

    private void evaluatePosition(Hero hero){
        elapsedNanoSeconds = timeNow - timeLastUpdate;
        elapsedSeconds = elapsedNanoSeconds / 1_000_000_000.0;
        dx=(speedNowx*elapsedSeconds+dx_1+dx_2+dx_3+dx_4)/5;
        x+=dx;
        hero.setX(hero.getX()-speedNowx*elapsedSeconds);
        dx_4=dx_3;
        dx_3=dx_2;
        dx_2=dx_1;
        dx_1=dx;
    }

    private void evaluateVitesse(Hero hero){
        elapsedNanoSeconds = timeNow - timeLastUpdate;
        elapsedSeconds = elapsedNanoSeconds / 1_000_000_000.0;
        speedNowx += accelerationNowx*elapsedSeconds;
        if((this.getY()==0 && hero.isrealspacePressed)||this.getY()<0) {
            speedNowy += accelerationNowy * elapsedSeconds;
        }
        else{
                speedNowy=0;
                y=0;
            }

    }

    private void evaluateAcceleration(double xhero, double yhero){
        accelerationNowx= k*(xhero)/m - f*speedNowx/m;
        accelerationNowy = -5*k * (this.y - yhero + 150) /m - 2*f * speedNowy / m;
    }

    public void setTimeLastUpdate(long time){
        timeLastUpdate = time;
    }

    @Override
    public String toString(){
        return x+","+y;
    }
}
