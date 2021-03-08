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
import java.io.*;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
    
    public static void main(String[] args) {
        
        WordCounter spamWC = new WordCounter();
        WordCounter hamWC = new WordCounter();
        String localDir = System.getProperty("user.dir");
        spamWC.countWords(localDir + "/data/train/spam/", "outSpam");
        System.out.println(spamWC.getTotalFiles());
        hamWC.countWords(localDir + "/data/train/ham/", "outHam");
        System.out.println(hamWC.getTotalFiles());
        hamWC.countWords(localDir + "/data/train/ham2/", "outHam");
        System.out.println(hamWC.getTotalFiles());

    }

}