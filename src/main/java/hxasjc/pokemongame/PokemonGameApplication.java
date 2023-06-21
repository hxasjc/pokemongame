package hxasjc.pokemongame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PokemonGameApplication extends Application {
    private PokemonBattle controller;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("pokemon-battle.fxml"));
        Scene scene = new Scene(loader.load());
        controller = loader.getController();
        primaryStage.setTitle("Pokemon Battle");
        primaryStage.setScene(scene);
        primaryStage.show();

        controller.init();
    }
}
