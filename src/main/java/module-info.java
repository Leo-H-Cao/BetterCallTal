open module oogasalad {
    // list all imported class packages since they are dependencies
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.media;
    requires javafx.web;
    requires java.scripting;
    requires org.json;
    requires org.apache.logging.log4j;
    requires com.fasterxml.jackson.databind;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.web;
    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires spring.webmvc;
    requires org.apache.tomcat.embed.core;
    requires java.net.http;
  requires com.fasterxml.jackson.datatype.jsr310;
    requires java.desktop;
    requires javafx.swing;

// allow other classes to access listed packages in your project
    exports oogasalad;
}
