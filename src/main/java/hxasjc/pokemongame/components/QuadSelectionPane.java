package hxasjc.pokemongame.components;

import hxasjc.pokemongame.Coordinate;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class QuadSelectionPane extends AnchorPane {
    private static final Map<SelectedCorner, Coordinate> ARROW_POSITION_MAP = Map.ofEntries(
            Map.entry(SelectedCorner.TOP_LEFT, new Coordinate(0, 27))
    );

    private final double x;
    private final double y;

    private boolean isActive = true;

    private int[][] selectionArray = new int[2][2];

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

        root.requestFocus();
        root.setOnKeyTyped(event -> {
            System.out.println("hi");
            System.out.println(event.getCode());
        });
    }

    public SelectedCorner getSelectedCorner() {
        if (selectionArray[0][0] == 2) {
            return SelectedCorner.TOP_LEFT;
        } else if (selectionArray[0][1] == 2) {
            return SelectedCorner.TOP_RIGHT;
        } else if (selectionArray[1][0] == 2) {
            return SelectedCorner.BOTTOM_LEFT;
        } else if (selectionArray[1][1] == 2) {
            return SelectedCorner.BOTTOM_RIGHT;
        }
        throw new IllegalStateException("Invalid selection array (%s)".formatted(Arrays.toString(selectionArray)));
    }

    protected void moveArrow() {
        Coordinate coordinate = ARROW_POSITION_MAP.get(getSelectedCorner());
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
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public enum SelectedCorner {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT
    }
}
