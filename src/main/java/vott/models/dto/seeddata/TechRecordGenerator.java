package vott.models.dto.seeddata;

import com.google.gson.Gson;
import vott.json.GsonInstance;
import vott.models.dto.techrecordsv3.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TechRecordGenerator {

    public TechRecordHgvComplete createCompleteHGVFromJson(String jsonFilePath) {
        Gson gson = GsonInstance.get();
        try {

            return gson.fromJson(
                    Files.newBufferedReader(Paths.get(jsonFilePath)),
                    TechRecordHgvComplete.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public TechRecordPsvComplete createCompletePSVFromJson(String jsonFilePath) {
        Gson gson = GsonInstance.get();
        try {

            return gson.fromJson(
                    Files.newBufferedReader(Paths.get(jsonFilePath)),
                    TechRecordPsvComplete.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TechRecordTrlComplete createCompleteTrlFromJson(String jsonFilePath) {
        Gson gson = GsonInstance.get();
        try {

            return gson.fromJson(
                    Files.newBufferedReader(Paths.get(jsonFilePath)),
                    TechRecordTrlComplete.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TechRecordCarComplete createCompleteCarFromJson(String jsonFilePath) {
        Gson gson = GsonInstance.get();
        try {

            return gson.fromJson(
                    Files.newBufferedReader(Paths.get(jsonFilePath)),
                    TechRecordCarComplete.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TechRecordLgvComplete createCompleteLgvFromJson(String jsonFilePath) {
        Gson gson = GsonInstance.get();
        try {

            return gson.fromJson(
                    Files.newBufferedReader(Paths.get(jsonFilePath)),
                    TechRecordLgvComplete.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TechRecordMotorcycleComplete createCompleteMotorcycleFromJson(String jsonFilePath) {
        Gson gson = GsonInstance.get();
        try {

            return gson.fromJson(
                    Files.newBufferedReader(Paths.get(jsonFilePath)),
                    TechRecordMotorcycleComplete.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TechRecordSmallTrlComplete createCompleteSmallTrlFromJson(String jsonFilePath) {
        Gson gson = GsonInstance.get();
        try {

            return gson.fromJson(
                    Files.newBufferedReader(Paths.get(jsonFilePath)),
                    TechRecordSmallTrlComplete.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
