package hxasjc.pokemongame;

import hxasjc.pokemongame.components.HealthBar;
import hxasjc.pokemongame.components.QuadSelectionPane;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

public class PokemonBattle implements IKeyInputHandler {
    private static final int GRADIENT_COUNT = 60;
    private final Rectangle[] gradientRects = new Rectangle[GRADIENT_COUNT];

    @FXML
    public AnchorPane background;

    @FXML
    public AnchorPane gradientPane;

    @FXML
    public Ellipse opponentBase;

    @FXML
    public Ellipse playerBase;

    @FXML
    public Label textArea;

    HealthBar playerHealth;
    HealthBar opponentHealth;

    QuadSelectionPane selectionArea;

    public void init() {
        placeGradient();

        /*opponentBase.relocate(515, 240);
        playerBase.relocate(20, 405);*/

        playerHealth = new HealthBar(560, 395, 380, 20);
        background.getChildren().add(playerHealth);
        playerHealth.setVisible(true);
        playerHealth.toFront();

        opponentHealth = new HealthBar(80, 110, 380, 20);
        background.getChildren().add(opponentHealth);
        opponentHealth.setVisible(true);
        opponentHealth.toFront();

        selectionArea = new QuadSelectionPane(655, 435);
        background.getChildren().add(selectionArea);
        selectionArea.setVisible(true);
        selectionArea.toFront();
        selectionArea.applyText(new String[]{"Fight", "Swap", "Heal", "Revive"});
        selectionArea.setActive(true);
    }

    private void placeGradient() {
        int colourR = 100;
        int colourG = 180;
        int colourB = 245;

        int colourROrig = colourR;
        int colourGOrig = colourG;
        int colourBOrig = colourB;

        int xPosBg = 0;
        int yPosBg = 3;
        int xSizeBg = 1000;
        int ySizeBg = 7;

        int spaceBetween = 3;

        for (int i = 0; i < GRADIENT_COUNT; i++) {
            colourR += (255 - colourROrig) / GRADIENT_COUNT + 1;
            colourG += (255 - colourGOrig) / GRADIENT_COUNT + 1;
            colourB += (255 - colourBOrig) / GRADIENT_COUNT + 1;

            Rectangle rectangle = new Rectangle(
                    xPosBg,
                    yPosBg,
                    xSizeBg,
                    ySizeBg
            );
            yPosBg = yPosBg + ySizeBg + spaceBetween;
            rectangle.setFill(
                    Color.rgb(
                            Math.min(colourR, 255),
                            Math.min(colourG, 255),
                            Math.min(colourB, 255)
                    )
            );
            gradientPane.getChildren().add(rectangle);
            rectangle.toFront();
            rectangle.setVisible(true);
            gradientRects[i] = rectangle;
        }
    }

    @Override
    public void acceptEvent(KeyEvent event) {
        selectionArea.acceptEvent(event);
    }
}
