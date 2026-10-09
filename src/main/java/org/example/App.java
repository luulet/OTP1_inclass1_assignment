package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private final TemperatureConverter converter = new TemperatureConverter();
    private final TempRecordDAO dao = new TempRecordDAO();

    @Override
    public void start(Stage stage) {
        ComboBox<String> unit = new ComboBox<>();
        unit.getItems().addAll("Fahrenheit", "Kelvin");
        unit.setValue("Fahrenheit");
        TextField input = new TextField();
        input.setPromptText("Temperature");
        Button convert = new Button("Convert to Celsius");
        Label result = new Label();
        ListView<String> history = new ListView<>();

        convert.setOnAction(e -> {
            try {
                double value = Double.parseDouble(input.getText());
                boolean fahrenheit = unit.getValue().equals("Fahrenheit");
                double celsius = fahrenheit
                        ? converter.fahrenheitToCelsius(value)
                        : converter.kelvinToCelsius(value);
                result.setText(celsius + " C");
                dao.save(fahrenheit ? 1 : 2, value, celsius);
                history.getItems().setAll(dao.findAll());
            } catch (NumberFormatException ex) {
                result.setText("Enter a number");
            } catch (Exception ex) {
                result.setText("Database error: " + ex.getMessage());
            }
        });

        VBox root = new VBox(10, unit, input, convert, result, history);
        root.setPadding(new javafx.geometry.Insets(10));
        stage.setScene(new Scene(root, 350, 400));
        stage.setTitle("Temperature Converter");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
