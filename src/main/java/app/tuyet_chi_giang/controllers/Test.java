package app.tuyet_chi_giang.controllers;

import app.tuyet_chi_giang.dictionaryCommandline.DictionaryManagement;
import app.tuyet_chi_giang.dictionaryCommandline.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Test implements Initializable {
    private DictionaryManagement dictionaryManagement = new DictionaryManagement();

    @FXML
    private Button fruit, animal, body, job, transport;

    @FXML
    private Button done1, done2, done3, done4, done5;

    @FXML
    private Button check1, check2, check3, check4, check5;

    private List<Word> wordList = new ArrayList<>();

    @FXML
    ObservableList<TextField> targetList, explainList;

    @FXML
    private TextField target1, target2, target3, target4, target5;

    @FXML
    private TextField suggest;

    @FXML
    private TextField explain1, explain2, explain3, explain4, explain5;

    @FXML
    private ImageView image1, image2, image3, image4, image5;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hidden();

        target1.setEditable(false);
        target2.setEditable(false);
        target3.setEditable(false);
        target4.setEditable(false);
        target5.setEditable(false);

        setButton(fruit);
        setButton(body);
        setButton(animal);
        setButton(job);
        setButton(transport);
    }

    @FXML
    public void loadExplain() {
        explain1.setText(wordList.get(0).getWord_explain());
        explain2.setText(wordList.get(1).getWord_explain());
        explain3.setText(wordList.get(2).getWord_explain());
        explain4.setText(wordList.get(3).getWord_explain());
        explain5.setText(wordList.get(4).getWord_explain());
    }

    @FXML
    private void loadImage(ImageView image, String target, String id) {
        String path = "src/main/resources/utils/image_5topic/image_" + id + "/img_" + target;
        if (id.equals("fruit") || id.equals("animal")) {
            path += ".jpg";
        } else {
            path += ".png";
        }

        Image png = new Image("file:" + path);
        System.out.println(path);
        image.setImage(png);
    }
    private void setButton(Button a) {
        a.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String topic = a.getId();
                System.out.println(topic);
                target1.setEditable(true);
                target2.setEditable(true);
                target3.setEditable(true);
                target4.setEditable(true);
                target5.setEditable(true);


                loadTopic(topic);
                loadExplain();
                setTextFieldTarget(target1, done1, check1, wordList.get(0).getWord_target());
                setTextFieldTarget(target2, done2, check2, wordList.get(1).getWord_target());
                setTextFieldTarget(target3, done3, check3, wordList.get(2).getWord_target());
                setTextFieldTarget(target4, done4, check4, wordList.get(3).getWord_target());
                setTextFieldTarget(target5, done5, check5, wordList.get(4).getWord_target());
            }
        });
    }

    @FXML
    private void hidden() {
        done1.setVisible(false);
        done2.setVisible(false);
        done3.setVisible(false);
        done4.setVisible(false);
        done5.setVisible(false);

        check1.setVisible(false);
        check2.setVisible(false);
        check3.setVisible(false);
        check4.setVisible(false);
        check5.setVisible(false);

        explain1.setEditable(false);
        explain2.setEditable(false);
        explain3.setEditable(false);
        explain4.setEditable(false);
        explain5.setEditable(false);
    }
    @FXML
    private void loadTopic(String id) {
        hidden();
        String path = "src/main/resources/utils/5topic/" + id + ".txt";
        dictionaryManagement = new DictionaryManagement();
        dictionaryManagement.insertFromFile(path);
        wordList = dictionaryManagement.random5word();
        loadImage(image1, wordList.get(0).getWord_target(), id);
        loadImage(image2, wordList.get(1).getWord_target(), id);
        loadImage(image3, wordList.get(2).getWord_target(), id);
        loadImage(image4, wordList.get(3).getWord_target(), id);
        loadImage(image5, wordList.get(4).getWord_target(), id);

    }

    public void startTest() {

    }

    @FXML
    public void setTextFieldTarget(TextField target, Button done, Button check, String en) {
        target.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (target.getText() != null && !target.getText().isEmpty()) {
                    check.setVisible(true);
                    check.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            if (target.getText().equals(en)) {
                                suggest.setText("Yeah!!!");
                                done.setVisible(true);
                                target.setEditable(false);
                                check.setVisible(false);
                            } else {
                                suggest.setText(compare(target.getText(), en));
                            }
                        }
                    });
                } else {
                    done.setVisible(false);
                    check.setVisible(false);
                }
            }
        });
    }

    private String compare(String target, String resTarget) {
        String res = "";
        for (int i = 0; i < resTarget.length(); i++) {
            if (i>=target.length()) {
                res += "-";
            } else if (target.charAt(i) == resTarget.charAt(i)) {
                res+=target.charAt(i);
            } else
                res+="-";
        }
        return res;
    }
}