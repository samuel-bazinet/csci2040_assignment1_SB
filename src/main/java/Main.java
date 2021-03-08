import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.collections.*;
import java.util.*;
import java.io.*;

public class Main extends Application {

    private TableView<TestFile> table = new TableView<TestFile>();

    @Override
    public void start(Stage primaryStage) throws Exception {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File mainDirectory = directoryChooser.showDialog(primaryStage);

        BorderPane border = new BorderPane();
        GridPane grid = new GridPane();

        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setHgap(20);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        table.setEditable(true);

        ObservableList<TestFile> probabilities = getProbability(mainDirectory);

        TableColumn<TestFile, String> testFileNameCol = new TableColumn<>("File");
        testFileNameCol.setMinWidth(300);
        testFileNameCol.setCellValueFactory(
            new PropertyValueFactory<>("filename")
        );

        TableColumn<TestFile, String> testFileClassCol = new TableColumn<>("Actual Class");
        testFileClassCol.setMinWidth(60);
        testFileClassCol.setCellValueFactory(
            new PropertyValueFactory<>("actualClass")
        );

        TableColumn<TestFile, String> testFileProbabilityCol = new TableColumn<>("Spam Probability");
        testFileProbabilityCol.setMinWidth(200);
        testFileProbabilityCol.setCellValueFactory(
            new PropertyValueFactory<>("spamProbabilityRounded")
        );

        table.setItems(probabilities);

        table.getColumns().addAll(testFileNameCol, testFileClassCol, testFileProbabilityCol);

        border.setTop(table);

        Label labAccu = new Label("Accuracy");
        Label labPrec = new Label("Precision");
        
        TextField textAccu = new TextField();
        TextField textPrec = new TextField();

        int trueNeg = 0;
        int truePos = 0;
        int falseNeg = 0;
        int falsePos = 0;
        int numFiles = 0;

        for (TestFile file : probabilities) {
            numFiles++;
            if (file.getSpamProbability() < 0.5) {
                if (file.getActualClass() == "Ham") {
                    trueNeg++;
                } else {
                    falseNeg++;
                }
            } else {
                if (file.getActualClass() == "Ham") {
                    falsePos++;
                } else {
                    truePos ++;
                }
            }
        }

        double accuracy = ((double)(truePos+trueNeg)/numFiles);
        double precision = ((double)(truePos)/(falsePos+truePos));

        textAccu.setText(String.valueOf(accuracy));
        textPrec.setText(String.valueOf(precision));

        grid.add(labAccu, 0, 0);
        grid.add(textAccu, 1, 0);
        grid.add(labPrec, 0, 1);
        grid.add(textPrec, 1, 1);

        border.setCenter(grid);

        primaryStage.setScene(new Scene(border, 700, 600));

        primaryStage.show();

    }

    private static ObservableList<TestFile> getProbability(File mainDirectory) {
        ObservableList<TestFile> probabilities = FXCollections.observableArrayList();

        Map<String, Double> mapProbabilitySpamGivenHam = Train.getProbabilitySpamGivenHam(mainDirectory.getAbsolutePath());

        for (File file: new File(mainDirectory.getAbsolutePath() + "/test/ham").listFiles()) {
            probabilities.add(new TestFile(file.getName(), Test.calculateProbabilitySpam(file.getAbsolutePath(), mapProbabilitySpamGivenHam), "Ham"));
        }

        for (File file: new File(mainDirectory.getAbsolutePath() + "/test/spam").listFiles()) {
            probabilities.add(new TestFile(file.getName(), Test.calculateProbabilitySpam(file.getAbsolutePath(), mapProbabilitySpamGivenHam), "Spam"));
        }

        return probabilities;
        
    }

    public static void main(String[] args) {

        launch(args);
        //System.out.println(Test.calculateProbabilitySpam(System.getProperty("user.dir") + "/data/test/spam/00002.9438920e9a55591b18e60d1ed37d992b"));

    }

}