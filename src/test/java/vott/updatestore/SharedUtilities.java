package vott.updatestore;

import com.google.gson.Gson;
import lombok.SneakyThrows;
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

public class SharedUtilities {
    VottConfiguration configuration = VottConfiguration.local();
    ConnectionFactory connectionFactory = new ConnectionFactory(configuration);
    private FieldGenerator fieldGenerator = new FieldGenerator();
    private VehicleRepository vehicleRepository = new VehicleRepository(connectionFactory);
    private Gson gson = GsonInstance.get();


    @SneakyThrows(IOException.class)
    public CompleteTestResults readTestResult(String path) {
        return gson.fromJson(
                Files.newBufferedReader(Paths.get(path)),
                CompleteTestResults.class
        );
    }

    @SneakyThrows(IOException.class)
    public TechRecordPOST readTechRecord(String path) {
        return gson.fromJson(
                Files.newBufferedReader(Paths.get(path)),
                TechRecordPOST.class
        );
    }

    public TechRecordPOST loadTechRecord(String fileName) {
        return randomizeKeys(readTechRecord(fileName));
    }

    public CompleteTestResults loadTestResults(TechRecordPOST techRecord, String fileName) {
        return matchKeys(techRecord, readTestResult(fileName));
    }

    public String convertBooleanToStringNumericBoolean(Boolean bool){
        String stringBoolean = bool.toString();
        String numericStringBoolean;
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

    //--------------------------------------
    private TechRecordPOST randomizeKeys(TechRecordPOST techRecord) {
        String vin = fieldGenerator.randomVin();
        while (SqlGenerator.vehicleIsPresentInDatabase(vin, vehicleRepository).toString().equals("true")) {
            vin = fieldGenerator.randomVin();
        }
        techRecord.setVin(vin);
        techRecord.setPrimaryVrm(fieldGenerator.randomVrm());
        return techRecord;
    }

    private CompleteTestResults matchKeys(TechRecordPOST techRecord, CompleteTestResults testResult) {
        testResult.setTestResultId(UUID.randomUUID().toString());
        // test result ID is not kept on enquiry-service retrievals: need a way to uniquely identify within test suite
        testResult.setTesterName(UUID.randomUUID().toString());
        testResult.setVin(techRecord.getVin());
        testResult.setVrm(techRecord.getPrimaryVrm());
        return testResult;
    }
}



