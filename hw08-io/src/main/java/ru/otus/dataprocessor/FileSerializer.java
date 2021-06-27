package ru.otus.dataprocessor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            final String replace = String.valueOf(data).replace("=", ":").replace(" ", "");
            bufferedWriter.write(replace);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
