package app.tuyet_chi_giang.controllers;

import app.tuyet_chi_giang.dictionaryCommandline.DictionaryManagement;
import app.tuyet_chi_giang.dictionaryCommandline.Word;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

public class Game implements Initializable {
    @FXML
    private GridPane gridPane;

    @FXML
    private Label winLabel;

    private final int rows = 4;
    private final int columns = 6;
    private HashMap<String, ImageView> imageViewsMap = new HashMap<>();
    private ImageView firstFlippedImageView;
    private String firstFlippedImageId;
    private boolean processingFlips = false;

    private int win = 4 * 6;

    @FXML
    private Button button1, button2, button3;

    private double light = 0, light2 = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        winLabel.setVisible(false);
        button1.setOnAction(this::handleButton1);
        button2.setOnAction(this::handleButton2);
        button3.setOnAction(this::handleButton3);
    }

    @FXML
    private void handleButton1(ActionEvent event) {
        light = 0.5;
        light2 = 0;
        updateGame();
    }

    @FXML
    private void handleButton2(ActionEvent event) {
        light = 0;
        light2 = 0.5;
        updateGame();
    }

    @FXML
    private void handleButton3(ActionEvent event) {
        light = 0;
        light2 = 0;
        updateGame();
    }

    private void updateGame() {
        gridPane.getChildren().clear();
        imageViewsMap.clear();
        loadImages();
        initializeGame();
    }

    private void loadImages() {
        winLabel.setVisible(false);
        DictionaryManagement dictionaryManagement = new DictionaryManagement();
        dictionaryManagement.insertFromFile("src/main/resources/utils/game.txt");
        List<Word> wordList = dictionaryManagement.randomNword(12);

        for (int i = 0; i < 12; i++) {
            String target = wordList.get(i).getWord_target();
            String imageFilePath = "file:src/main/resources/utils/game_image/img_" + target + ".png";
            Image image = new Image(imageFilePath);
            ImageView imageView = createImageView(image);
            imageView.setOpacity(light);
            imageView.setOnMouseClicked(event -> handleFlip(imageView, target));
            imageViewsMap.put(target, imageView);
        }

        for (int i = 0; i < 12; i++) {
            String target = wordList.get(i).getWord_target();
            String imageFilePath = "file:src/main/resources/utils/image_text/" + target + ".png";
            Image image = new Image(imageFilePath);
            ImageView imageView = createImageView(image);
            imageView.setOpacity(light);
            imageView.setOnMouseClicked(event -> handleFlip(imageView, target));
            imageViewsMap.put(target + "text", imageView);
        }

        List<Map.Entry<String, ImageView>> imageViewList = new ArrayList<>(imageViewsMap.entrySet());
        Collections.shuffle(imageViewList);
        imageViewsMap.clear();
        for (Map.Entry<String, ImageView> entry : imageViewList) {
            imageViewsMap.put(entry.getKey(), entry.getValue());
        }
    }

    private ImageView createImageView(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(95);
        imageView.setFitHeight(95);
        imageView.setOpacity(light);
        return imageView;
    }

    private void initializeGame() {
        ArrayList<String> imageNames = new ArrayList<>(imageViewsMap.keySet());
        Collections.shuffle(imageNames);

        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (index >= imageNames.size()) {
                    break;
                }

                String imageName = imageNames.get(index);
                ImageView imageView = imageViewsMap.get(imageName);
                gridPane.add(imageView, j, i);
                index++;
            }
        }
    }

    private void handleFlip(ImageView imageView, String imageId) {
        if (!processingFlips) {
            imageView.setOpacity(1);
            if (firstFlippedImageView == null) {
                firstFlippedImageView = imageView;
                firstFlippedImageId = imageId;
            } else {
                if (firstFlippedImageId.equals(imageId) && firstFlippedImageView != imageView) {
                    removeMatchedImages(firstFlippedImageView, imageView);
                } else {
                    flipBackImages(firstFlippedImageView, imageView);
                }
                if (light2 != 0) {
                    light = light2;
                }
                firstFlippedImageView.setOpacity(light);
                firstFlippedImageView = null;
                firstFlippedImageId = null;
            }
            System.out.println(imageId);
        }
    }

    private void removeMatchedImages(ImageView imageView1, ImageView imageView2) {
        gridPane.getChildren().removeAll(imageView1, imageView2);
        win -= 2;
        if (win == 0) {
            winLabel.setVisible(true);
        }
    }

    private void flipBackImages(ImageView imageView1, ImageView imageView2) {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        pause.setOnFinished(event -> {
            imageView1.setOpacity(light);
            imageView2.setOpacity(light);
            processingFlips = false;
        });
        pause.play();
        processingFlips = true;
    }
}
