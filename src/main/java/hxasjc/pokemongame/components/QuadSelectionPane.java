package hxasjc.pokemongame.components;

import hxasjc.pokemongame.Coordinate;
import hxasjc.pokemongame.IKeyInputHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class QuadSelectionPane extends AnchorPane implements IKeyInputHandler {
    private static final Map<SelectedCorner, Coordinate> ARROW_POSITION_MAP = Map.ofEntries(
            Map.entry(SelectedCorner.TOP_LEFT, new Coordinate(0, 27)),
            Map.entry(SelectedCorner.TOP_RIGHT, new Coordinate(150, 27)),
            Map.entry(SelectedCorner.BOTTOM_LEFT, new Coordinate(0, 107)),
            Map.entry(SelectedCorner.BOTTOM_RIGHT, new Coordinate(150, 107))
    );

    private final double x;
    private final double y;

    private boolean isActive = false;

    private int[][] selectionArray = new int[2][2];

    private final Map<SelectedCorner, Label> CORNER_LABEL_MAP;

    @FXML
    private AnchorPane root;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private Label label4;

    @FXML
    private ImageView arrow;

    public QuadSelectionPane(double x, double y) {
        this.x = x;
        this.y = y;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("quad-selection.fxml"));
        loader.setRoot(this);
        loader.setController(QuadSelectionPane.this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        root.relocate(x, y);
        selectCorner(SelectedCorner.TOP_LEFT);

        CORNER_LABEL_MAP = Map.ofEntries(
                Map.entry(SelectedCorner.TOP_LEFT, label1),
                Map.entry(SelectedCorner.TOP_RIGHT, label2),
                Map.entry(SelectedCorner.BOTTOM_LEFT, label3),
                Map.entry(SelectedCorner.BOTTOM_RIGHT, label4)
        );
    }

    public SelectedCorner getSelectedCorner() {
        enforceSelectionArrayLimits();

        if (selectionArray[0][0] == 2) {
            return SelectedCorner.TOP_LEFT;
        } else if (selectionArray[0][1] == 2) {
            return SelectedCorner.TOP_RIGHT;
        } else if (selectionArray[1][0] == 2) {
            return SelectedCorner.BOTTOM_LEFT;
        } else if (selectionArray[1][1] == 2) {
            return SelectedCorner.BOTTOM_RIGHT;
        }
        throw new IllegalStateException("Invalid selection array (%s)".formatted(selectionArrayToString()));
    }

    protected void moveArrow() {
        Coordinate coordinate = ARROW_POSITION_MAP.get(getSelectedCorner());
        arrow.relocate(coordinate.x(), coordinate.y());
    }

    public void applyText(String[] strings) {
        label1.setText(strings[0]);
        label2.setText(strings[1]);
        label3.setText(strings[2]);
        label4.setText(strings[3]);
    }

    public void selectCorner(SelectedCorner corner) {
        switch (corner) {
            case TOP_LEFT -> selectionArray = new int[][]{{2, 1}, {1, 0}};
            default -> throw new IllegalStateException();
        }

        moveArrow();
    }

    public String getTextAtCorner(SelectedCorner corner) {
        return CORNER_LABEL_MAP.get(corner).getText();
    }

    public String getTextAtSelectedCorner() {
        return getTextAtCorner(getSelectedCorner());
    }

    @Override
    public void acceptEvent(KeyEvent event) {
        if (event.getEventType().equals(KeyEvent.KEY_PRESSED)) {
            if (isActive) {
                switch (event.getCode()) {
                    case DOWN -> {
                        if (!(
                                selectionArray[0][0] == 0 ||
                                        selectionArray[0][1] == 0 ||
                                        selectionArray[1][0] == 2 ||
                                        selectionArray[1][1] == 2
                                )) {
                            selectionArray[0][0] -= 1;
                            selectionArray[0][1] -= 1;
                            selectionArray[1][0] += 1;
                            selectionArray[1][1] += 1;
                        }
                    }
                    case UP -> {
                        if (!(
                                selectionArray[0][0] == 2 ||
                                        selectionArray[0][1] == 2 ||
                                        selectionArray[1][0] == 0 ||
                                        selectionArray[1][1] == 0
                                )) {
                            selectionArray[0][0] += 1;
                            selectionArray[0][1] += 1;
                            selectionArray[1][0] -= 1;
                            selectionArray[1][1] -= 1;
                        }
                    }
                    case LEFT -> {
                        if (!(
                                selectionArray[0][0] == 2 ||
                                        selectionArray[0][1] == 0 ||
                                        selectionArray[1][0] == 2 ||
                                        selectionArray[1][1] == 0
                        )) {
                            selectionArray[0][0] += 1;
                            selectionArray[0][1] -= 1;
                            selectionArray[1][0] += 1;
                            selectionArray[1][1] -= 1;
                        }
                    }
                    case RIGHT -> {
                        if (!(
                                selectionArray[0][0] == 0 ||
                                        selectionArray[0][1] == 2 ||
                                        selectionArray[1][0] == 0 ||
                                        selectionArray[1][1] == 2
                        )) {
                            selectionArray[0][0] -= 1;
                            selectionArray[0][1] += 1;
                            selectionArray[1][0] -= 1;
                            selectionArray[1][1] += 1;
                        }
                    }
                }

                moveArrow();
            }
        }
    }

    public String selectionArrayToString() {
        String[] arr = new String[] {Arrays.toString(selectionArray[0]), Arrays.toString(selectionArray[1])};
        return Arrays.toString(arr);
    }

    protected void enforceSelectionArrayLimits() {
        for (int i = 0; i < selectionArray.length; i++) {
            for (int j = 0; j < selectionArray[i].length; j++) {
                int orig = selectionArray[i][j];
                selectionArray[i][j] = Math.max(0, Math.min(selectionArray[i][j], 2));
                if (orig != selectionArray[i][j]) {
                    System.out.printf("enforce %s to %s%n", orig, selectionArray[i][j]);
                }
            }
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void toFront1() {
        root.toFront();
    }

    public void toBack1() {
        root.toBack();
    }

    public enum SelectedCorner {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT
    }
}
