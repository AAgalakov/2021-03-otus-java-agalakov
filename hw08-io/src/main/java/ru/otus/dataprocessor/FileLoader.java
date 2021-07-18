package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonValue;
import java.util.List;
import java.util.stream.Collectors;

public class FileLoader implements Loader {

    private final String fineName;

    public FileLoader(String fileName) {
        this.fineName = fileName;
    }

    @Override
    public List<Measurement> load() {
        List<Measurement> list;
        try (var jsonReader = Json.createReader(Measurement.class.getClassLoader().getResourceAsStream(fineName))) {
            final JsonArray jsonValues = jsonReader.readArray();
            list = jsonValues.getValuesAs(JsonValue::asJsonObject)
                             .stream()
                             .map(jsonObject ->
                                     new Measurement(
                                             jsonObject.get("name").toString().replace("\"", ""),
                                             Double.parseDouble(jsonObject.get("value").toString())
                                     ))
                             .collect(Collectors.toList());
        }
        return list;
    }
}
