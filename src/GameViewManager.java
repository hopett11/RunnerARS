import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.ArrayList;

//GameScene du sujet

public class GameViewManager {
    private static final double GameHeight = 300;
    private static final double GameWidth = 600;
    private Camera myCamera;
    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    private Stage menuStage;
    private Hero myhero;
    private AnimatedThing newbullet;
    private AnimationTimer gameTimer;
    private int reloadTimer=0;
    final int reload = 4000;
    private String BACKGROUND_IMAGE;
    private StaticThing backgroundLeft;
    private StaticThing backgroundRight;
    final ArrayList<Foe> foelist = new ArrayList<>();
    private boolean hit;
    final ArrayList<StaticThing> hearts  = new ArrayList<>();
    private StaticThing iconbullet;
    private int lifesleft = 3;
    private double coefficient=1;
    private boolean generatefoes=false;
    private boolean shootmode=false;
    private boolean gamepaused;
    private RunnerPauseScene pauseSubscene;


    public GameViewManager() {
        initializeStage();
        createKeyListener();
    }

    private void initializeStage() {
        gamePane = new AnchorPane();
        myCamera = new Camera(0, 0);
        gameScene = new Scene(gamePane, GameWidth, GameHeight, true);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
    }


    private void createKeyListener() {

        gameScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                myhero.attitude = 1;
                myhero.isrealspacePressed = true;

            }
            if ((event.getCode() == KeyCode.ENTER) && reloadTimer == 0 && myhero.getY()>=150) {
                myhero.attitude = 3;
                shootmode=true;
                newbullet.getImageView().setX(135);
                reloadTimer = reload;
            }
            if (event.getCode() == KeyCode.ESCAPE) {
                gamepaused=true;
                pauseSubscene.moveSubScene();
                pauseSubscene.updateScore(myCamera.getX());
            }
        });

        gameScene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                if (myhero.getY() == 2500) {
                    myhero.attitude = 0;
                    myhero.isrealspacePressed = false;
                }
            }
        });


    }

    private void createGameLoop() {
        gameTimer = new AnimationTimer() {

            @Override
            public void handle(long time) {
                if(!gamepaused) {
                    myCamera.moveCamera(backgroundLeft,backgroundRight,time, myhero);
                    myhero.update(time);
                    moveHero();
                    hit = checkCollision();
                    checkLose(hit);
                    checkGenerateFoes();
                    moveFoes();
                    if (shootmode) {
                        moveBullet();
                    }
                    checkDestruction();
                }
                else {
                    myCamera.moveCamera(backgroundLeft,backgroundRight,time, myhero);
                    destroyFoes();
                }
                if(getGoMenu()){
                    prepareClose();
                }

                }
            };


        myCamera.setTimeLastUpdate(System.nanoTime());
        myhero.setTimeLastUpdate(System.nanoTime());
        gameTimer.start();
    }

    private void moveHero() {
        coefficient=(1.00015)*coefficient;
        myhero.setX(myhero.getX()+4*coefficient);
        if(myhero.getInvincibility()!=0){
            myhero.setInvincibility(myhero.getInvincibility()-10);
        }
        if(reloadTimer!=0){
            reloadTimer-=10;
            iconbullet.getImageView().setX(650);
        }
        else{
            iconbullet.getImageView().setX(540);
        }
        myhero.updateOuch();

    }

    private void moveFoes(){
        for(Foe foe : foelist) {
            foe.setX(foe.getX() - 4*coefficient);
            foe.update(myhero);
            foe.updateX(generatefoes);
        }
    }

    private void destroyFoes(){
        for(Foe foe : foelist) {
            foe.getImageView().setY(-200);
        }
    }

    private void moveBullet(){
        double x=newbullet.getImageView().getX();
        if(x<650) {
            if(x<140){
                newbullet.getImageView().setX(x + 0.8);
            }
            else {
                newbullet.getImageView().setX(x + 8);
            }
        }
        else{
            shootmode=false;
            newbullet.getImageView().setX(-200);
        }
        newbullet.setY(330-myhero.getY());
        //if (x)
    }

    private void checkGenerateFoes(){
        if ((myCamera.x) >2000){
            generatefoes=true;
        }
        for (Foe foe : foelist){
            if(foe.getX()>-100){
                generatefoes=false;
            }
        }
    }

    private boolean checkCollision(){
        for(Foe foe : foelist){
            boolean hitfoe = myhero.getHitBox().intersects(foe.getHitBox());
            if (hitfoe){
                return true;}
        }
        return false;
    }

    private void checkDestruction() {
        for (Foe foe : foelist) {
            Rectangle2D hitbox = new Rectangle2D(
                    newbullet.getImageView().getX(),
                    newbullet.getImageView().getY(),
                    newbullet.getImageView().getViewport().getWidth() / 2,
                    newbullet.getImageView().getViewport().getHeight() / 2);

            boolean hitfoe = hitbox.intersects(foe.getHitBox());
            if (hitfoe) {
                foe.setMode(1);
            }
        }
    }

    private void checkLose(boolean hitlose){
        if (hitlose &myhero.getInvincibility()==0){
                hearts.get(3-lifesleft).getImageView().setImage(null);
                lifesleft-=1;
                myhero.setInvincibility(1000);
            }
        if(lifesleft==0){
            gamepaused=true;
            pauseSubscene.moveSubScene();
            pauseSubscene.updateScore(myCamera.getX());

        }
        }


    public void createNewGame(Stage menuStage, String herocolour, String mapstring) {

        this.menuStage = menuStage;
        this.menuStage.hide();
        BACKGROUND_IMAGE = mapstring;
        createBackground();
        myhero = new Hero(herocolour);
        myhero.createrun();

        myhero.createrunshoot();
        myhero.attitude = 0;

        myhero.setViewXY(20,0,60,100);
        myhero.setX(800);
        myhero.setY(150);
        gamePane.getChildren().add(myhero.getImageView());
        gamePane.getChildren().add(myhero.ouch.getImageView());
        createGameLoop();

        Foe foe1 = new Foe();
        Foe foe2 = new Foe();
        Foe foe3 = new Foe();
        Foe foe4 = new Foe();
        foelist.add(foe1);
        foelist.add(foe2);
        foelist.add(foe3);
        foelist.add(foe4);
        foe1.getImageView().setFitWidth(102);
        foe1.getImageView().setFitHeight(110);
        foe2.getImageView().setFitWidth(102);
        foe2.getImageView().setFitHeight(110);
        foe3.getImageView().setFitWidth(102);
        foe3.getImageView().setFitHeight(110);
        foe4.getImageView().setFitWidth(102);
        foe4.getImageView().setFitHeight(110);

        gamePane.getChildren().add(foe1.getImageView());
        gamePane.getChildren().add(foe2.getImageView());
        gamePane.getChildren().add(foe3.getImageView());
        gamePane.getChildren().add(foe4.getImageView());
        foe1.setX(-200);
        foe1.setY(150);
        foe2.setX(-200);
        foe2.setY(150);
        foe3.setX(-200);
        foe3.setY(150);
        foe4.setX(-200);
        foe4.setY(150);


        newbullet = new AnimatedThing("heros.png");
        newbullet.setViewXY(530, 365,30,30);
        newbullet.getImageView().setX(-200);
        newbullet.getImageView().setY(180);
        gamePane.getChildren().add(newbullet.getImageView());


        gameStage.show();
    }

    private void createBackground() {
        backgroundLeft = new StaticThing(BACKGROUND_IMAGE);
        backgroundLeft.getImageView().setX(0);
        backgroundLeft.getImageView().setY(-100);
        backgroundRight = new StaticThing(BACKGROUND_IMAGE);
        backgroundRight.getImageView().setX(600);
        backgroundRight.getImageView().setY(-100);
        backgroundRight.getImageView().setViewport(new Rectangle2D(0, 0,800,400));

        iconbullet = new StaticThing("bullet.png");
        iconbullet.getImageView().setX(540);
        iconbullet.getImageView().setY(40);

        StaticThing heart1 = new StaticThing("heartv2.png");
        StaticThing heart2 = new StaticThing("heartv2.png");
        StaticThing heart3 = new StaticThing("heartv2.png");
        hearts.add(heart1);
        hearts.add(heart2);
        hearts.add(heart3);

        heart1.getImageView().setX(500);
        heart1.getImageView().setY(10);
        heart2.getImageView().setX(530);
        heart2.getImageView().setY(10);
        heart3.getImageView().setX(560);
        heart3.getImageView().setY(10);

        heart1.getImageView().setFitHeight(30);
        heart2.getImageView().setFitHeight(30);
        heart3.getImageView().setFitHeight(30);

        heart1.getImageView().setFitWidth(30);
        heart2.getImageView().setFitWidth(30);
        heart3.getImageView().setFitWidth(30);


        gamePane.getChildren().add(backgroundLeft.getImageView());
        gamePane.getChildren().add(backgroundRight.getImageView());

        gamePane.getChildren().add(heart1.getImageView());
        gamePane.getChildren().add(heart2.getImageView());
        gamePane.getChildren().add(heart3.getImageView());
        gamePane.getChildren().add(iconbullet.getImageView());
        createPauseSubscene();
    }


    private void createPauseSubscene() {
        pauseSubscene = new RunnerPauseScene();
        gamePane.getChildren().add(pauseSubscene);
        pauseSubscene.createButtonToExit(gameStage,menuStage);

    }


    public boolean getGoMenu(){
        return pauseSubscene.getGoMenu();
    }

    public void prepareClose(){
        menuStage.show();
        gameStage.close();
        gameTimer.stop();
    }
}

