import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MapDemoApp extends Application {
    private HashMapExample<String, String> map;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        map = new HashMapExample<>();

        VBox root = new VBox(10);

        Button putButton = new Button("Добавить элемент");
        Button getButton = new Button("Получить элемент");
        Button containsKeyButton = new Button("Проверить ключ");
        Button containsValueButton = new Button("Проверить значение");
        Button removeButton = new Button("Удалить элемент");
        Button clearButton = new Button("Очистить карту");

        TextField keyField = new TextField();
        keyField.setPromptText("Введите ключ");

        TextField valueField = new TextField();
        valueField.setPromptText("Введите значение");

        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);

        putButton.setOnAction(e -> {
            String key = keyField.getText();
            String value = valueField.getText();
            map.put(key, value);
            resultArea.appendText("Добавлено: " + key + " -> " + value + "\n");
            keyField.clear();
            valueField.clear();
        });

        getButton.setOnAction(e -> {
            String key = keyField.getText();
            String value = map.get(key);
            resultArea.appendText("Получено: " + key + " -> " + (value != null ? value : "не найдено") + "\n");
            keyField.clear();
        });

        containsKeyButton.setOnAction(e -> {
            String key = keyField.getText();
            boolean contains = map.containsKey(key);
            resultArea.appendText("Содержит ли ключ '" + key + "': " + contains + "\n");
            keyField.clear();
        });

        containsValueButton.setOnAction(e -> {
            String value = valueField.getText();
            boolean contains = map.containsValue(value);
            resultArea.appendText("Содержит ли значение '" + value + "': " + contains + "\n");
            valueField.clear();
        });

        removeButton.setOnAction(e -> {
            String key = keyField.getText();
            String value = map.remove(key);
            resultArea.appendText("Удалено: " + key + " -> " + (value != null ? value : "не найдено") + "\n");
            keyField.clear();
        });

        clearButton.setOnAction(e -> {
            map.clear();
            resultArea.appendText("Карта очищена.\n");
        });

        root.getChildren().addAll(
                keyField, valueField,
                putButton, getButton, containsKeyButton, containsValueButton,
                removeButton, clearButton, resultArea
        );

        Scene scene = new Scene(root, 1000, 500);
        primaryStage.setTitle("Карта");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

