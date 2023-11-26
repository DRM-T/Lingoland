package app.tuyet_chi_giang.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private Button exit, add, search, game, setting, test, aboutUs, translate, chill;
    @FXML
    private AnchorPane container;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setButton(search);
        setButton(translate);
        setButton(add);
        setButton(game);
        setButton(chill);
        setButton(test);
        setButton(setting);
        setButton(aboutUs);

        // giao dien luon dung luc khoi tao
        showComponent("/Views/search.fxml");

        exit.setOnMouseClicked(e -> System.exit(0));
    }
    @FXML
    private void setButton(Button a) {
        a.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String path = "/Views/" + a.getId() + ".fxml";
                showComponent(path);
            }
        });
    }
    @FXML
    private void showComponent(String path) {
        try {// load path vao anchorpane
            AnchorPane component = FXMLLoader.load(getClass().getResource(path));
            container.getChildren().clear(); // xoa children cua container - pane.. ben trong
            container.getChildren().add(component); // them a..pane moi vao
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
