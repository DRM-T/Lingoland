package app.tuyet_chi_giang.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChillController implements Initializable {

    public ListView<File> videoListView, videoListView1, videoListView2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String video1 = "src/main/resources/utils/vdeoDoc";
        String video2 = "src/main/resources/utils/videoNgang1";
        String video3 = "src/main/resources/utils/videoNgang2";
        showListVideo(video1, videoListView, 250, 400);
        showListVideo(video2, videoListView1, 420, 200);
        showListVideo(video3, videoListView2, 420, 200);
    }

    @FXML
    public void showListVideo(String videoFolderPath, ListView<File> videoListView, int width, int height) {
        //String videoFolderPath = "E:/Dictionary/OOP_Dictionary/Tuyet_Chi_Giang/src/main/resources/utils/video";

        File videoFolder = new File(videoFolderPath);
        List<File> videoFiles = new ArrayList<>();

        if (videoFolder.isDirectory()) {
            File[] files = videoFolder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        videoFiles.add(file);
                    }
                }
            }
        }

        videoListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(File file, boolean empty) {

                super.updateItem(file, empty);
                if (empty || file == null) {
                    setGraphic(null);
                    return;
                }

                String videoPath = file.toURI().toString();
                Media media = new Media(videoPath);
                MediaPlayer mediaPlayer = new MediaPlayer(media); // Khởi tạo mediaPlayer ở đây

                MediaView mediaView = new MediaView(mediaPlayer);
                mediaView.setFitWidth(width);
                mediaView.setFitHeight(height);
                setGraphic(mediaView);

                // Xử lý sự kiện khi nhấp vào vùng video
                setOnMouseClicked(event -> {
                    if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                        mediaPlayer.pause(); // Nếu video đang phát thì dừng lại
                    } else {
                        mediaPlayer.play(); // Nếu không, bắt đầu phát video
                    }
                });

                // Xử lý sự kiện khi di chuyển ra khỏi vùng video
                setOnMouseExited(event -> {
                    mediaPlayer.pause(); // Dừng video khi di chuyển ra khỏi vùng video
                });
            }
        });

        videoListView.getItems().addAll(videoFiles);
    }
}
