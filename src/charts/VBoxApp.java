/* ....Show License.... */
package charts;
 
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
 
/**
 * A simple example of a VBox layout.
 */
public class VBoxApp extends Application {
 
    public Parent createVBoxContent() {
        CheckBox cb1 = new CheckBox("Breakfast");
        CheckBox cb2 = new CheckBox("Lunch");
        CheckBox cb3 = new CheckBox("Dinner");
 
        VBox vboxMeals = new VBox(5);
        vboxMeals.getChildren().addAll(cb1, cb2, cb3);
 
        Label label = new Label("Select one or more meals:");
        VBox vboxOuter = new VBox(10);
 
        vboxOuter.getChildren().addAll(label, vboxMeals);
        vboxOuter.setAlignment(Pos.CENTER_LEFT);
 
        return vboxOuter;
    }
 
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createVBoxContent()));
        primaryStage.show();
    }
 
    /**
     * Java main for when running without JavaFX launcher
     */
    public static void main(String[] args) {
        launch(args);
    }
}