package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class App extends Application {

    public static final List<String> LANGUAGES =
            List.of("English", "Suomi", "Svenska", "日本語", "العربية");
    private static final List<String> CODES = List.of("en", "fi", "sv", "ja", "ar");

    private static Stage stage;
    private static String language = "English";

    public static String currentLanguage() {
        return language;
    }

    public static void setLanguage(String name) {
        language = name;
        load();
    }

    private static void load() {
        try {
            Locale locale = Locale.forLanguageTag(CODES.get(LANGUAGES.indexOf(language)));
            ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/main.fxml"), bundle);
            Parent root = loader.load();
            ((MainController) loader.getController()).setBundle(bundle);
            root.setNodeOrientation(locale.getLanguage().equals("ar")
                    ? NodeOrientation.RIGHT_TO_LEFT : NodeOrientation.LEFT_TO_RIGHT);
            stage.setTitle(bundle.getString("title"));
            stage.setScene(new Scene(root, 350, 450));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        load();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
