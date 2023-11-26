module app.tuyet_chi_giang {
    requires javafx.controls;
    requires javafx.fxml;
    requires freetts;
    requires unirest.java;
    requires okhttp3;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.media;

    opens app.tuyet_chi_giang to javafx.fxml;
    exports app.tuyet_chi_giang;
    exports app.tuyet_chi_giang.controllers;
    opens app.tuyet_chi_giang.controllers to javafx.fxml;
}