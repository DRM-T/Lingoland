package app.tuyet_chi_giang.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import okhttp3.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TranslateController implements Initializable {
    @FXML
    TextArea enText, viText;

    @FXML
    Button vietnam, english, korea, japan, china, france;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        enText.setEditable(true);
        setButton(vietnam);
        setButton(english);
        setButton(france);
        setButton(china);
        setButton(korea);
        setButton(japan);

//        enText.setOnKeyTyped(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent keyEvent) {
//                String target = enText.getText();
//                String explain = enTrantoVi(target);
//                viText.setText(explain);
//            }
//        });
    }


    private void setButton(Button a) {
        a.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String id = code(a.getId());
                if (id != null) {
                    String explain = enTrantoVi(enText.getText(), id);
                    System.out.println(enText.getText() + " " + explain);
                    viText.setText(explain);
                }

            }
        });
    }

    public String code(String language) {
        switch (language) {
            case "english": return "en";
            case "vietnam": return "vi";
            case "china": return "zh";
            case "france": return "fr";
            case "korea": return "ko";
            default:
                return null;
        }
    }

    public String enTrantoVi(String target, String id) {
        try
        {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "[\r\n    {\r\n        \"Text\": \"" + target + "\"\r\n    }\r\n]");
            Request request = new Request.Builder()
                    .url("https://microsoft-translator-text.p.rapidapi.com/translate?to%5B0%5D="+ id + "&api-version=3.0&profanityAction=NoAction&textType=plain")
                    .post(body)
                    .addHeader("content-type", "application/json")
                    .addHeader("X-RapidAPI-Key", "a9a11a045cmshbfa60b360b7c3e3p1e01f5jsnb43154a885f6")
                    .addHeader("X-RapidAPI-Host", "microsoft-translator-text.p.rapidapi.com")
                    .build();

            Response response = client.newCall(request).execute();
            String res = response.body().string();
            return res.substring(76, res.length() - 15);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }



    public static void main(String[] args) {
        try
        {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "[\r\n    {\r\n        \"Text\": \"I would really like to drive your car around the block a few times.\"\r\n    }\r\n]");
            Request request = new Request.Builder()
                    .url("https://microsoft-translator-text.p.rapidapi.com/translate?to%5B0%5D=vi&api-version=3.0&profanityAction=NoAction&textType=plain")
                    .post(body)
                    .addHeader("content-type", "application/json")
                    .addHeader("X-RapidAPI-Key", "a9a11a045cmshbfa60b360b7c3e3p1e01f5jsnb43154a885f6")
                    .addHeader("X-RapidAPI-Host", "microsoft-translator-text.p.rapidapi.com")
                    .build();

            Response response = client.newCall(request).execute();
            String res = response.body().string();
            System.out.println(res.substring(76, res.length() - 15));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
