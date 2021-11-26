import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ViewManager {

    final AnchorPane mainPane;
    final Scene mainScene;

    List<RunnerButton> menuButtons;

    final Stage mainStage;

    final int HEIGHT = 600;
    final int WIDTH = 650;
    final int MENU_BUTTON_START_X = 20;
    final int MENU_BUTTON_START_Y = 130;

    private ArrayList<HeroPicker> HerosList;
    private ArrayList<LevelPicker> MapPickerList;
    final ArrayList<HeroColor> Colorlist;
    final ArrayList<String> Maplist;
    private final String FONT_PATH = "/resources/alagard.ttf";

    private HeroColor choosenHero;
    private String choosenLevel = "desert.png";
    private RunnerSubScene HeroChooserScene;
    private RunnerSubScene creditsSubscene;
    private RunnerSubScene sceneToHide;
    private RunnerSubScene helpSubscene;
    private RunnerSubScene levelSubscene;


    public ViewManager() {
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT );
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        //CreateButtons();
        Colorlist = new ArrayList<>();
        Colorlist.add(new HeroColor("herosbleu"));
        Colorlist.add(new HeroColor("herosrouge"));
        Maplist =  new ArrayList<>();
        Maplist.add("desert.png");
        Maplist.add("swamp.png");
        createSubScenes();
        createLogo();
        CreateButtons();
        createBackground();
        //mainStage.setScene(HeroChooserScene);

    }

    private void createLogo() {
        ImageView logo = new ImageView("/resources/logov3.png");
        logo.setLayoutX(0);
        logo.setLayoutY(-20);

        logo.setOnMouseEntered(event -> logo.setEffect(new DropShadow()));

        logo.setOnMouseExited(event -> logo.setEffect(null));

        mainPane.getChildren().add(logo);

    }

    private void createBackground() {
        Image backgroundImage = new Image("C:\\Users\\ARS\\Documents\\2A\\INFO\\Java\\RunnerOldARS\\src\\img\\fondov2.jpg", 1200, 600, false, false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(background));
    }


    private RunnerButton createButtonToStart() {
        RunnerButton startButton = new RunnerButton("START");
        startButton.setLayoutX(350);
        startButton.setLayoutY(200);


        startButton.setOnAction(event -> {
                GameViewManager gameManager = new GameViewManager();
                gameManager.createNewGame(mainStage,choosenHero.getColour(),choosenLevel);

        });

        return startButton;
    }

    private HBox createHerosToChoose() {
        HBox box = new HBox();
        box.setSpacing(60);
        HerosList = new ArrayList<>();
        for (HeroColor herocolor : Colorlist) {
            HeroPicker heroToPick = new HeroPicker(herocolor);
            HerosList.add(heroToPick);
            box.getChildren().add(heroToPick);
            heroToPick.setOnMouseClicked(event -> {
                for (HeroPicker heropick : HerosList) {
                    heropick.setIsCircleChoosen(false);
                }
                heroToPick.setIsCircleChoosen(true);
                choosenHero = heroToPick.getHero();

            });
        }

        box.setLayoutX(300 - (118*2));
        box.setLayoutY(100);
        return box;
    }

    private HBox createLevelToChoose() {
        HBox levelbox = new HBox();
        levelbox.setSpacing(20);

        MapPickerList = new ArrayList<>();
        for (String mapstring : Maplist) {
            LevelPicker MapToPick = new LevelPicker(mapstring);
            MapPickerList.add(MapToPick);
            levelbox.getChildren().add(MapToPick);
            MapToPick.setOnMouseClicked(event -> {
                for (LevelPicker level : MapPickerList) {
                    level.setIsCircleChoosen(false);
                }
                MapToPick.setIsCircleChoosen(true);
                choosenLevel = MapToPick.getMap();

            });
        }

        levelbox.setLayoutX(30);
        levelbox.setLayoutY(90);
        return levelbox;
    }

    private void showSubScene(RunnerSubScene subScene) {
        if (sceneToHide != null) {
            sceneToHide.moveSubScene();
        }

        subScene.moveSubScene();
        sceneToHide = subScene;
    }


    private void AddMenuButtons(RunnerButton button) {
        button.setLayoutX(MENU_BUTTON_START_X + Math.floor((menuButtons.size()+1)/(float)2)*205);
        button.setLayoutY(MENU_BUTTON_START_Y + menuButtons.size()%2 * 70);
        menuButtons.add(button);
        mainPane.getChildren().add(button);
    }



    private void CreateButtons() {
        createStartButton();
        createLevelsButton();
        createHelpButton();
        createCreditsButton();
        createExitButton();
    }

    private void createStartButton() {
        RunnerButton startButton = new RunnerButton("PLAY");
        AddMenuButtons(startButton);

        startButton.setOnAction(event -> showSubScene(HeroChooserScene));
    }

    private void createLevelsButton() {
        RunnerButton levelsButton = new RunnerButton("LEVELS");
        AddMenuButtons(levelsButton);

        levelsButton.setOnAction(event -> showSubScene(levelSubscene));
    }

    private void createHelpButton() {
        RunnerButton helpButton = new RunnerButton("HELP");
        AddMenuButtons(helpButton);

        helpButton.setOnAction(event -> showSubScene(helpSubscene));
    }

    private void createCreditsButton() {

        RunnerButton creditsButton = new RunnerButton("CREDITS");
        AddMenuButtons(creditsButton);

        creditsButton.setOnAction(event -> showSubScene(creditsSubscene));
    }

    private void createExitButton() {
        RunnerButton exitButton = new RunnerButton("EXIT");
        AddMenuButtons(exitButton);

        exitButton.setOnAction(event -> mainStage.close());

    }

    private void createSubScenes() {

        creditsSubscene = new RunnerSubScene();
        mainPane.getChildren().add(creditsSubscene);
        levelSubscene = new RunnerSubScene();
        mainPane.getChildren().add(levelSubscene);


        createHeroChooserSubScene();
        createHelpSubscene();
        createCreditsSubscene();
        createLevelSubscene();

    }

    private void createHeroChooserSubScene() {
        HeroChooserScene = new RunnerSubScene();
        //HeroChooserScene = new RunnerSubScene();
        mainPane.getChildren().add(HeroChooserScene);

        Label chooseHeroLabel = new Label("CHOOSE YOUR HERO");
        chooseHeroLabel.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 40));
        chooseHeroLabel.setStyle("-fx-text-fill: white;");
        chooseHeroLabel.setLayoutX(110);
        chooseHeroLabel.setLayoutY(25);
        HeroChooserScene.getPane().getChildren().add(chooseHeroLabel);
        HeroChooserScene.getPane().getChildren().add(createHerosToChoose());
        HeroChooserScene.getPane().getChildren().add(createButtonToStart());
    }

    private void createHelpSubscene() {
        helpSubscene = new RunnerSubScene();
        mainPane.getChildren().add(helpSubscene);
        String newLine = System.getProperty("line.separator");
        Label helpText = new Label("In this game you must avoid collision with a cactus."+newLine+newLine+"For that you must either jump (SPACE) or shoot (ENTER)."+newLine+"Keep in mind that shooting has cooldown."+newLine+newLine+"If you touch one cactus you will lose one life."+newLine+"When you lose 3 lifes you will lose the game. Have fun!");
        helpText.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 21));
        helpText.setStyle("-fx-text-fill: white;");
        helpText.setLayoutX(25);
        helpText.setLayoutY(50);
        helpSubscene.getPane().getChildren().add(helpText);
    }

    private void createCreditsSubscene() {
        creditsSubscene = new RunnerSubScene();
        mainPane.getChildren().add(creditsSubscene);
        String newLine = System.getProperty("line.separator");
        ImageView ENSEAlogo = new ImageView("/img/Logo_ENSEAv4.png");
        ENSEAlogo.setLayoutX(460);
        ENSEAlogo.setLayoutY(80);
        Label creditsText = new Label("Game Design: Alvaro RODRIGUEZ SANCHEZ"+newLine+newLine+"Company: Project as a ENSEA student"+newLine+newLine+"Graphics: Taken from opengameart.org (Copyright Free)");
        creditsText.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 21));
        creditsText.setStyle("-fx-text-fill: white;");
        creditsText.setLayoutX(25);
        creditsText.setLayoutY(80);
        creditsSubscene.getPane().getChildren().add(creditsText);
        creditsSubscene.getPane().getChildren().add(ENSEAlogo);
    }

    private void createLevelSubscene() {
        levelSubscene = new RunnerSubScene();
        mainPane.getChildren().add(levelSubscene);
        Label chooseLevel = new Label("CHOOSE A MAP");
        chooseLevel.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 40));
        chooseLevel.setStyle("-fx-text-fill: white;");
        chooseLevel.setLayoutX(160);
        chooseLevel.setLayoutY(25);
        levelSubscene.getPane().getChildren().add(chooseLevel);
        levelSubscene.getPane().getChildren().add(createLevelToChoose());

    }

    public Stage getPrimaryStage() {
        return mainStage;
    }

}
