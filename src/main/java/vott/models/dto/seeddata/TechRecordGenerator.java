package vott.models.dto.seeddata;

import com.google.gson.Gson;
import vott.json.GsonInstance;
import vott.models.dto.techrecordsv3.TechRecordHgvComplete;
import vott.models.dto.techrecordsv3.TechRecordPsvComplete;

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

}
