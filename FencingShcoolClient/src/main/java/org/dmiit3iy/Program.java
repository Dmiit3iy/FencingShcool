package org.dmiit3iy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.prefs.Preferences;

/**
 * JavaFX App
 */
public class Program extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        if (isAuthorization()){
            scene = new Scene(loadFXML("main"), 640, 480);
        } else {
            scene = new Scene(loadFXML("authorization"), 640, 480);
        }

        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Program.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void showMessage(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Метод для проверки авторизации
     * @return
     */
    public static boolean isAuthorization() {
        Preferences userlog = Preferences.userRoot();
        return userlog.getBoolean("authorization", false);
    }

}