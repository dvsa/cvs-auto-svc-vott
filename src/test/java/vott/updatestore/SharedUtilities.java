package vott.updatestore;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.runner.RunWith;
import vott.config.VottConfiguration;
import vott.database.VehicleRepository;
import vott.database.connection.ConnectionFactory;
import vott.database.sqlgeneration.SqlGenerator;
import vott.e2e.FieldGenerator;
import vott.json.GsonInstance;
import vott.models.dto.techrecords.TechRecordPOST;
import vott.models.dto.testresults.CompleteTestResults;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@RunWith(SerenityRunner.class)
public class SharedUtilities {

    @SneakyThrows(IOException.class)
    public static CompleteTestResults readTestResult(String path) {
        Gson gson = GsonInstance.get();
        return gson.fromJson(
                Files.newBufferedReader(Paths.get(path)),
                CompleteTestResults.class
        );
    }

    public static String convertBooleanToStringNumericBoolean(Boolean bool){
        String stringBoolean = bool.toString();
        String numericStringBoolean = "";
        switch (stringBoolean) {
            case "true":
                numericStringBoolean = "1";
                break;
            case "false":
                numericStringBoolean = "0";
                break;
            default:
                numericStringBoolean = "null";
                break;
        }
        return numericStringBoolean;
    }
}
