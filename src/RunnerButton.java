import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;

public class RunnerButton extends Button {

    final String FONT_PATH = "/ARSart/alagard.ttf";
    final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-text-fill: white; -fx-background-image: url('/ARSart/buttonStock1dv4.png');";
    final String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-text-fill: white; -fx-background-image: url('/ARSart/buttonStock1v4.png');";
    final String BUTTON_ENTERED_STYLE = "-fx-background-color: transparent; -fx-text-fill: white; -fx-background-image: url('/ARSart/buttonStock1hv4.png');";

    public RunnerButton(String text) {
        setText(text);
        setButtonFont();
        setPrefWidth(200);
        setPrefHeight(60);
        setStyle(BUTTON_FREE_STYLE);
        initializeButtonListeners();

    }

    private void setButtonFont() {

        setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 23));

    }

    private void setButtonPressedStyle() {
        setStyle(BUTTON_PRESSED_STYLE);
        //setPrefHeight(45);
        setLayoutY(getLayoutY() + 4);

    }

    private void setButtonReleasedStyle() {
        setStyle(BUTTON_FREE_STYLE);
        //setPrefHeight(49);
        setLayoutY(getLayoutY() - 4);

    }

    private void initializeButtonListeners() {

        setOnMousePressed(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                setButtonPressedStyle();
            }

        });

        setOnMouseReleased(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                setButtonReleasedStyle();
            }

        });

        setOnMouseEntered(event -> {
            setEffect(new DropShadow());
            setStyle(BUTTON_ENTERED_STYLE);

        });

        setOnMouseExited(event -> {
            setEffect(null);
            setStyle(BUTTON_FREE_STYLE);

        });
    }
}