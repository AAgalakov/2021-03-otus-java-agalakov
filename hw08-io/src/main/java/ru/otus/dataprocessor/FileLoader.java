package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonValue;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileLoader implements Loader {

    private final String fineName;

    public FileLoader(String fileName) {
        this.fineName = fileName;
    }

    @Override
    public List<Measurement> load() {
        List<Measurement> list = new ArrayList<>();
        try (var jsonReader = Json.createReader(Measurement.class.getClassLoader().getResourceAsStream(fineName))) {
            final JsonArray jsonValues = jsonReader.readArray();
            final List<Measurement> collect = jsonValues.getValuesAs(JsonValue::asJsonObject)
                    .stream()
                    .map(jsonObject -> new Measurement(jsonObject.get("name").toString(), Double.parseDouble(jsonObject.get("value").toString())))
                    .collect(Collectors.toList());
            list.addAll(collect);
        }
        return list;
    }
}
