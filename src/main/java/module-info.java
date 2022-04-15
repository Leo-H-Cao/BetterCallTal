open module oogasalad {
    // list all imported class packages since they are dependencies
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.media;
    requires javafx.web;
    requires org.json;
    requires org.apache.logging.log4j;
    requires java.scripting;
    requires com.fasterxml.jackson.databind;

    // allow other classes to access listed packages in your project
    exports oogasalad;
}
