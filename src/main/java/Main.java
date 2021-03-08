import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public static void main(String[] args) {

        System.out.println(Test.calculateProbabilitySpam(System.getProperty("user.dir") + "/data/test/spam/00002.9438920e9a55591b18e60d1ed37d992b"));

    }

}