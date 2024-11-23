module project.running_tony {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;

    opens Running_tony;
    exports Running_tony;
    exports Entities;
    exports Tiles;
}