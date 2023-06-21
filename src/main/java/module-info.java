module hxasjc.pokemongame {
    requires javafx.controls;
    requires javafx.fxml;


    opens hxasjc.pokemongame to javafx.fxml;
    exports hxasjc.pokemongame;
    opens hxasjc.pokemongame.components to javafx.fxml;
    exports hxasjc.pokemongame.components;
}