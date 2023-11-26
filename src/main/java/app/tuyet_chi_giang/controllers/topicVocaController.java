package app.tuyet_chi_giang.controllers;

import app.tuyet_chi_giang.dictionaryCommandline.DictionaryManagement;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class topicVocaController {

    private DictionaryManagement dictionaryManagement = new DictionaryManagement();

    @FXML
    private FlowPane imageFlowPane;

    private Map<VBox, String> vboxSoundMap = new HashMap<>();

    @FXML
    private Button fruit, animal, body, job, transport;

    @FXML
    public void initialize() {
        setButton(fruit);
        setButton(animal);
        setButton(job);
        setButton(body);
        setButton(transport);
    }

    @FXML
    private void setButton(Button a) {
        a.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String topic = a.getId();
                loadTopic(topic);
            }
        });
    }
    @FXML
    private void loadTopic(String id) {
        imageFlowPane.getChildren().clear();
        vboxSoundMap.clear();

        String path = "src/main/resources/utils/5topic/" + id + ".txt";
        dictionaryManagement.insertFromFile(path);
        String folder = "src/main/resources/utils/image_5topic/image_" + id;
        File directory = new File(folder);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && isImageFile(file.getName())) {
                    ImageView imageView = new ImageView(new Image(file.toURI().toString()));
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);
                    String name = file.getName();
                    name = name.substring(4, name.length() - 4).trim();

                    Label imageName = new Label(name);
                    imageName.getStyleClass().add("label_voca_target");
                    imageName.setWrapText(true);

                    Label imageExplain = new Label(dictionaryManagement.mean(name));
                    imageExplain.getStyleClass().add("label_voca_explain");
                    imageExplain.setWrapText(true);

                    VBox imageBox = new VBox(imageView, imageName, imageExplain);
                    imageBox.setSpacing(3);

                    imageFlowPane.getChildren().add(imageBox);

                    vboxSoundMap.put(imageBox, name);

                    imageBox.setOnMouseClicked(this::playSound);
                }
            }
        }
    }

    @FXML
    private boolean isImageFile(String fileName) {
        String[] allowedExtensions = {"jpg", "jpeg", "png", "gif"};
        for (String extension : allowedExtensions) {
            if (fileName.toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    @FXML
    private void playSound(MouseEvent event) {
        VBox clickedVBox = (VBox) event.getSource();
        String soundFileName = vboxSoundMap.get(clickedVBox);

        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        Voice voice = VoiceManager.getInstance().getVoice("kevin16");
        if (voice != null) {
            voice.allocate();
            voice.speak(soundFileName);
        } else {
            throw new IllegalStateException("Cannot find voice: kevin16");
        }
    }
}