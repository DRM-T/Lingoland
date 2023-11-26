

package app.tuyet_chi_giang.controllers;



import app.tuyet_chi_giang.alerts.Alerts;
import app.tuyet_chi_giang.dictionaryCommandline.DictionaryManagement;
import app.tuyet_chi_giang.dictionaryCommandline.Word;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    private DictionaryManagement dictionaryManagement = new DictionaryManagement();

    private final String path = "src/main/resources/utils/dictionaries.txt";

    private ObservableList<String> listOb;

    @FXML
    private Button edit, sound, delete, save;

    Alerts alerts = new Alerts();
    @FXML
    private Label result, notFound;

    @FXML
    private TextArea explain;

    @FXML
    private TextField searchBar, target, quantityOfResult;

    @FXML
    private ListView<String> listResults;

    private List<String> listString;

    private List<Integer> listIndex;

    private int indexTarget;

    private String oldExplain;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dictionaryManagement.insertFromFile(path);
        target.setEditable(false);
        explain.setEditable(false);
        save.setVisible(false);
        indexTarget = -1;

        searchBar.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String keyWord = searchBar.getText();
                if (!keyWord.isEmpty()) {
                    showListResult(keyWord);
                }
            }
        });
//        searchBar.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                String keyWord = searchBar.getText();
//                if (!keyWord.isEmpty()) {
//                    showListResult(keyWord);
//                }
//            }
//        });
    }

    // hiện danh sách kết quả
    @FXML
    public void showListResult(String keyWord) {
        listOb = dictionaryManagement.lookupWord(keyWord);
        listIndex = dictionaryManagement.search(keyWord);
        int n = listIndex.size();
        if (n == 0) {
            quantityOfResult.setText("0 kết quả liên quan");
            listResults.setItems(listOb);
        } else {
            quantityOfResult.setText(Math.min(20, n) + " trong " + n + " kết quả");
        }
        if (listIndex.isEmpty()) {
            notFound.setVisible(true);
        } else {
            listResults.setItems(listOb);
            if (this.notFound != null) {
                this.notFound.setVisible(true); // Hoặc bất kỳ thao tác nào khác cần thiết
            }
        }
    }

    @FXML
    private void chooseTargetByClick(MouseEvent arg0) {
        String getWordByClick = listResults.getSelectionModel().getSelectedItem().trim();
        //int indexRes = -1;
        System.out.println(getWordByClick);
        for (int i = 0; i < listIndex.size(); i++) {
            Word a = dictionaryManagement.getWord(listIndex.get(i));
            if (a.getWord_target().equals(getWordByClick)) {
                target.setText(getWordByClick);
                target.setEditable(false);
                explain.setText(a.getWord_explain());
                explain.setEditable(false);
                save.setVisible(false);
                indexTarget = listIndex.get(i);
                break;
            }
        }
    }


    @FXML
    private void editExplain() {
        alerts.showAlertInfo("Information", "Hiện tại bạn có thể chỉnh sửa từ này!");
        oldExplain = explain.getText();
        explain.setEditable(true);
        save.setVisible(true);
    }

    @FXML
    private void saveEdit() {
        String newExplain = explain.getText().trim();
        if (newExplain.equals(oldExplain)) {
            alerts.showAlertInfo("Information", "Bạn chưa thay đổi từ này!");
            return;
        }
        if (newExplain.equals("")) {
            alerts.showAlertInfo("Information", "Không được để trống!");
            return;
        }
        Alert selectionAlert = alerts.alertConfirmation(
                "Sửa từ",
                "Bạn chắc chắn muốn cập nhật từ này chứ?"
        );
        selectionAlert.getButtonTypes().setAll(
                new ButtonType("Lưu"),
                ButtonType.CANCEL
        );
        Optional<ButtonType> selection = selectionAlert.showAndWait();

        if (!selection.isPresent()) {
            return;
        }
        if (selection.get() == ButtonType.CANCEL) {
            alerts.showAlertInfo("Information", "Thao tác không được sử dụng.");
            return;
        }
        Word a = new Word(target.getText(), newExplain);
        dictionaryManagement.replace(indexTarget, a, path);
        alerts.showAlertInfo("Information", "Cập nhật thành công " + target.getText());
    }

    @FXML
    private void handleClickSoundBtn() {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        Voice voice = VoiceManager.getInstance().getVoice("kevin16");
        if (voice != null) {
            voice.allocate();
            voice.speak(target.getText());
        } else throw new IllegalStateException("Cannot find voice: kevin16");
    }

    @FXML
    private void deleteWord() {
        if (indexTarget == -1) {
            return;
        }
        Alert selectionAlert = alerts.alertConfirmation(
                "Xóa từ",
                "Bạn chắc chắn muốn xóa từ này chứ?"
        );
        selectionAlert.getButtonTypes().setAll(
                new ButtonType("Xóa"),
                ButtonType.CANCEL
        );
        Optional<ButtonType> selection = selectionAlert.showAndWait();

        if (!selection.isPresent()) {
            return;
        }
        if (selection.get() == ButtonType.CANCEL) {
            alerts.showAlertInfo("Information", "Thao tác không được sử dụng.");
            return;
        }
        dictionaryManagement.delete(indexTarget, path);
        alerts.showAlertInfo("Information", "Xóa thành công " + target.getText());
        System.out.println("Xoa thanh cong: " + target.getText());
    }

    public static void main(String[] args) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        VoiceManager voiceManager = VoiceManager.getInstance();
        Voice[] voices = voiceManager.getVoices();

        for (Voice voice : voices) {
            System.out.println("Name: " + voice.getName());
            System.out.println("Description: " + voice.getDescription());
            System.out.println();
        }
    }

}