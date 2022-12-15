package Controller;

import Model.Question;
import Model.SysData;
import Utils.Difficulty;
import javafx.animation.PathTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class QuestionsController implements Initializable {

    static Question ToRead;

    @FXML
    private ComboBox<Difficulty> difficulty = new ComboBox<Difficulty>();

    @FXML
    private Button addQuestionButton;

    @FXML
    private Button backButton;

    @FXML
    private Button deleteQuestionButton;

    @FXML
    private Button updateQuestionButton;

    @FXML
    private ImageView image;

    @FXML
    private ListView<Question> list = new ListView<Question>();

    public ListView<Question> getList() {
        return list;
    }

    public void setList(ListView<Question> list) {
        this.list = list;
    }

    static Question updatedQ;

    private HashMap<Difficulty, ArrayList<Question>> questions;
    SysData sysData = SysData.getInstance();
    private ArrayList<Question> level = new ArrayList<Question>();

    @FXML
    void addQuestion(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../View/AddQuestion.fxml"));
            root.setStyle("-fx-background-image: url('Images/backgroundWallpaper.jpeg');" + "-fx-background-size:cover");
            Scene customerScene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(customerScene);
            window.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("FXML");
            alert.setHeaderText("Load failure");
            alert.setContentText("Failed to load the FXML file.");
            alert.showAndWait();
        }
    }

    @FXML
    void back(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../View/HomeScreen.fxml"));
            root.setStyle("-fx-background-image: url('Images/backgroundWallpaper.jpeg');" + "-fx-background-size:cover");
            Scene customerScene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(customerScene);
            window.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("FXML");
            alert.setHeaderText("Load failure");
            alert.setContentText("Failed to load the FXML file.");
            alert.showAndWait();
        }
    }

    @FXML
    void deleteQuestion(ActionEvent event) {

        Question q = list.getSelectionModel().getSelectedItem();
        if(!list.getItems().isEmpty() && q != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove Question");
            alert.setHeaderText("Remove Question?");
            alert.setContentText("Are you sure you want to remove this question?");
            alert.showAndWait();
            if(alert.getResult().equals(ButtonType.OK)) {
                list.getItems().remove(q);
                SysData.getInstance().removeQuestion(q);
                SysData.getInstance().observableMethod();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No question selected");
            alert.setHeaderText("Select Question");
            alert.setContentText("Please select a question to delete.");
            alert.showAndWait();
        }
    }

    @FXML
    @SuppressWarnings("unlikely-arg-type")
    void updateQuestion(ActionEvent event) throws IOException {
        updatedQ = list.getSelectionModel().getSelectedItem();
        if(updatedQ == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Question");
            alert.setContentText("You must select a question first");
            alert.show();
            return;
        }
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../View/UpdateQuestion.fxml"));
            root.setStyle("-fx-background-image: url('Images/backgroundWallpaper.jpeg');" + "-fx-background-size:cover");
            Scene customerScene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(customerScene);
            window.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("FXML");
            alert.setHeaderText("Load failure");
            alert.setContentText("Failed to load the FXML file.");
            alert.showAndWait();
        }
    }
    public void closeWindow() {
        ((Stage) backButton.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Difficulty> currentList = FXCollections.observableArrayList(Difficulty.values());
        difficulty.setItems(currentList);

        SysData.getInstance().loadQuestions("src/JSON/QuestionsFormat.txt");

        questions = sysData.getQuestions();
        for(Difficulty d : questions.keySet()) {
            for(Question q : questions.get(d)) {
                if(!level.contains(q))
                    level.add(q);
            }
        }
        ObservableList<Question> question = FXCollections.observableArrayList(level);
        list.setItems(question);
        
 /*       Line l = new Line(3,50, 150, 50);

        PathTransition transition = new PathTransition();
        transition.setNode(image);
        transition.setDuration(Duration.seconds(2));
        transition.setPath(l);
        transition.setCycleCount(PathTransition.INDEFINITE);
        transition.setAutoReverse(true);
        transition.play();*/
    }
}
