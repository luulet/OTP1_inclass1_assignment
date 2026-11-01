package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ResourceBundle;

public class MainController {

    private final TemperatureConverter converter = new TemperatureConverter();
    private final TempRecordDAO dao = new TempRecordDAO();
    private ResourceBundle bundle;

    @FXML private ComboBox<String> languageBox;
    @FXML private ComboBox<String> unitBox;
    @FXML private TextField input;
    @FXML private Label result;
    @FXML private ListView<String> history;

    @FXML
    private void initialize() {
        languageBox.getItems().addAll(App.LANGUAGES);
        languageBox.setValue(App.currentLanguage());
        unitBox.getItems().addAll("Fahrenheit", "Kelvin");
        unitBox.setValue("Fahrenheit");
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
        try {
            history.getItems().setAll(dao.findAll());
        } catch (Exception e) {
            result.setText(bundle.getString("error"));
        }
    }

    @FXML
    private void onLanguage() {
        App.setLanguage(languageBox.getValue());
    }

    @FXML
    private void onConvert() {
        try {
            double celsius = Double.parseDouble(input.getText());
            boolean fahrenheit = unitBox.getValue().equals("Fahrenheit");
            double converted = fahrenheit
                    ? converter.celsiusToFahrenheit(celsius)
                    : converter.celsiusToKelvin(celsius);
            result.setText(converted + " " + (fahrenheit ? "F" : "K"));
            dao.save(fahrenheit ? 1 : 2, converted, celsius);
            history.getItems().setAll(dao.findAll());
        } catch (NumberFormatException e) {
            result.setText(bundle.getString("invalid"));
        } catch (Exception e) {
            result.setText(bundle.getString("error") + ": " + e.getMessage());
        }
    }
}
