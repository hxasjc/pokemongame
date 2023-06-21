module hxasjc.pokemongame {
    requires javafx.controls;
    requires javafx.fxml;


    opens hxasjc.pokemongame to javafx.fxml;
    exports hxasjc.pokemongame;
    exports hxasjc.pokemongame.demo;
    opens hxasjc.pokemongame.demo to javafx.fxml;
    opens hxasjc.pokemongame.components to javafx.fxml;
    exports hxasjc.pokemongame.components;
}