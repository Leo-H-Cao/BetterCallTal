open module oogasalad {
    // list all imported class packages since they are dependencies
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.media;
    requires javafx.web;

    // allow other classes to access listed packages in your project
    exports oogasalad;
    exports oogasalad.Frontend;
    exports oogasalad.Server;
    exports oogasalad.Editor;
}
