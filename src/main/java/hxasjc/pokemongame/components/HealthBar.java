package hxasjc.pokemongame.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class HealthBar extends AnchorPane {
    private double x;
    private double y;
    private double width;
    private double height;
    private double healthPercent;

    @FXML
    private Rectangle light;

    @FXML
    private Rectangle dark;

    public HealthBar(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("health-bar.fxml"));
        loader.setRoot(this);
        loader.setController(HealthBar.this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        initElements();
    }

    protected void initElements() {
        light.relocate(x, y);
        light.setHeight(height / 2);
        light.setWidth(width);
        light.setVisible(true);

        dark.relocate(x, y + (height / 2));
        dark.setHeight(height / 2);
        dark.setWidth(width);
        dark.setVisible(true);

        //getChildren().addAll(light, dark);
    }

    protected void updateHealth() {
        double newWidth = width * Math.max(Math.min(1, healthPercent), 0);
        light.setWidth(newWidth);
        dark.setWidth(newWidth);
    }

    public double getHealthPercent() {
        return healthPercent;
    }

    public void setHealthPercent(double healthPercent) {
        this.healthPercent = healthPercent;
        updateHealth();
    }
}
