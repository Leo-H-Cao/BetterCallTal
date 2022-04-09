open module oogasalad {
    // list all imported class packages since they are dependencies
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires org.json;
    requires org.apache.logging.log4j;

    // allow other classes to access listed packages in your project
    exports oogasalad;
}
