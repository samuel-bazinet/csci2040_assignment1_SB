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

        Train.getProbabilitySpamGivenHam();

    }

}