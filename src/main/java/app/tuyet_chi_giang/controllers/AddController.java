package app.tuyet_chi_giang.controllers;

import app.tuyet_chi_giang.alerts.Alerts;
import app.tuyet_chi_giang.dictionaryCommandline.DictionaryManagement;
import app.tuyet_chi_giang.dictionaryCommandline.Word;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddController extends controller {
    private final DictionaryManagement dictionaryManagement = new DictionaryManagement();
    private final String path = "src/main/resources/utils/dictionaries.txt";
    private final Alerts alerts = new Alerts();

    @FXML
    private Button addBtn;

    @FXML
    private TextField wordTargetInput;

    @FXML
    private TextArea explanationInput;

    @FXML
    private Label successAlert;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dictionaryManagement.insertFromFile(path);
        addBtn.setDisable(true);

        wordTargetInput.textProperty().addListener((observable, oldValue, newValue) -> handleInput());
        explanationInput.textProperty().addListener((observable, oldValue, newValue) -> handleInput());
        successAlert.setVisible(false);
    }

    private void handleInput() {
        String explanation = explanationInput.getText().trim();
        String wordTarget = wordTargetInput.getText().trim();

        if (explanation.isEmpty() || wordTarget.isEmpty()) {
            addBtn.setDisable(true);
        } else {
            addBtn.setDisable(false);
        }
    }

    @FXML
    private void handleClickAddBtn() {
        String englishWord = wordTargetInput.getText().trim();
        String meaning = explanationInput.getText().trim();

        Alert alertConfirmation = alerts.alertConfirmation("Thêm từ", "Bạn chắc chắn muốn thêm từ này?");
        Optional<ButtonType> option = alertConfirmation.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK) {
            Word word = new Word(englishWord, meaning);
            int checkContains = dictionaryManagement.checkContains(word);

            if (checkContains != -1) {
                Alert selectionAlert = alerts.alertConfirmation(
                        "Từ này đã tồn tại",
                        "Thay thế hoặc bổ sung nghĩa mới cho từ đã có."
                );
                selectionAlert.getButtonTypes().setAll(
                        new ButtonType("Thay thế"),
                        new ButtonType("Bổ sung"),
                        ButtonType.CANCEL
                );

                Optional<ButtonType> selection = selectionAlert.showAndWait();

                if (selection.isPresent()) {
                    if (selection.get() == ButtonType.CANCEL) {
                        alerts.showAlertInfo("Information", "Thay đổi không được chấp nhận.");
                    } else {
                        if (selection.get().getText().equals("Thay thế")) {
                            dictionaryManagement.replace(checkContains, word, path);
                        } else {
                            dictionaryManagement.insert(checkContains, word);
                        }
                        dictionaryManagement.exportToFile(path);
                        showSuccessAlert();
                    }
                }
            } else {
                dictionaryManagement.add(word);
                dictionaryManagement.exportToFile(path);
                showSuccessAlert();
            }

            addBtn.setDisable(true);
            resetInput();
        } else {
            alerts.showAlertInfo("Information", "Thay đổi không được chấp nhận.");
        }
    }

    private void resetInput() {
        wordTargetInput.setText("");
        explanationInput.setText("");
    }

    private void showSuccessAlert() {
        successAlert.setVisible(true);
        dictionaryManagement.setTimeout(() -> successAlert.setVisible(false), 1500);
    }
}
