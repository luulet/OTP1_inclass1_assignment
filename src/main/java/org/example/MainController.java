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
    private final UnitDAO unitDao = new UnitDAO();
    private ResourceBundle bundle;
    private UnitDAO.Unit celsius;

    @FXML private ComboBox<String> languageBox;
    @FXML private ComboBox<UnitDAO.Unit> unitBox;
    @FXML private TextField input;
    @FXML private Label result;
    @FXML private Label description;
    @FXML private ListView<String> history;

    @FXML
    private void initialize() {
        languageBox.getItems().addAll(App.LANGUAGES);
        languageBox.setValue(App.currentLanguage());
        try {
            String lang = App.currentCode();
            celsius = unitDao.findByKind("temperature", lang).get(0);
            unitBox.getItems().addAll(unitDao.findByKind("temperature", lang).subList(1, 3));
            unitBox.setValue(unitBox.getItems().get(0));
            description.setText(unitBox.getValue().description());
            unitBox.setOnAction(e -> {
                if (unitBox.getValue() != null) {
                    description.setText(unitBox.getValue().description());
                }
            });
        } catch (Exception e) {
            description.setText(e.getMessage());
        }
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
        try {
            history.getItems().setAll(dao.findLast5(App.currentCode()));
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
            double value = Double.parseDouble(input.getText());
            UnitDAO.Unit target = unitBox.getValue();
            double converted = target.key().equals("fahrenheit")
                    ? converter.celsiusToFahrenheit(value)
                    : converter.celsiusToKelvin(value);
            result.setText(value + " " + celsius.name() + " = " + converted + " " + target.name());
            dao.save(celsius.id(), target.id(), value, converted, App.currentCode());
            history.getItems().setAll(dao.findLast5(App.currentCode()));
        } catch (NumberFormatException e) {
            result.setText(bundle.getString("invalid"));
        } catch (Exception e) {
            result.setText(bundle.getString("error") + ": " + e.getMessage());
        }
    }
}
