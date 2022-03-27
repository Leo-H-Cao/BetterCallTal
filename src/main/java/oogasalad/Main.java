package oogasalad;


import javafx.stage.Stage;
import oogasalad.Frontend.MainView;
import javafx.application.Application;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application {
    /**
     * A method to test (and a joke :).
     */
    public double getVersion () {
        return 0.001;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainView viewController = new MainView(primaryStage);
    }
}
