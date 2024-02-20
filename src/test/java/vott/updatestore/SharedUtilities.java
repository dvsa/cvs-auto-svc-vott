package vott.updatestore;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import vott.api.TechnicalRecordsV3;
import vott.config.VottConfiguration;
import vott.database.VehicleRepository;
import vott.database.connection.ConnectionFactory;
import vott.database.sqlgeneration.SqlGenerator;
import vott.e2e.FieldGenerator;
import vott.json.GsonInstance;
import vott.models.dto.techrecords.TechRecordPOST;
import vott.models.dto.testresults.CompleteTestResults;
import vott.models.dto.testresults.TestTypes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;
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
                CompleteTestResults.class);
    }

    @SneakyThrows(IOException.class)
    public TechRecordPOST readTechRecord(String path) {
        return gson.fromJson(
                Files.newBufferedReader(Paths.get(path)),
                TechRecordPOST.class);
    }

    public TechRecordPOST loadTechRecord(String fileName) {
        return randomizeKeys(readTechRecord(fileName));
    }

    public CompleteTestResults loadTestResults(TechRecordPOST techRecord, String fileName) {
        return matchKeys(techRecord, readTestResult(fileName));
    }

    public String convertBooleanToStringNumericBoolean(Boolean bool) {
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

    // --------------------------------------
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
        // test result ID is not kept on enquiry-service retrievals: need a way to
        // uniquely identify within test suite
        testResult.setTesterName(UUID.randomUUID().toString());
        testResult.setVin(techRecord.getVin());
        testResult.setVrm(techRecord.getPrimaryVrm());
        return testResult;
    }

    public void checkTechRecordPostOutcome(Map<String, String> response) {

        String responseCode = response.get(TechnicalRecordsV3.STATUS_CODE_KEY);
        String responseBody = response.get(TechnicalRecordsV3.RESPONSE_BODY_KEY);

        if (Integer.parseInt(responseCode) != 201) {
            throw new AssertionError(String.format(
                    "Error posting to tech record service " +
                            "%n responseCode: " + responseCode +
                            "%n responseBody: " + responseBody));
        }

    }

    //implemented seperatly in case test result API is different to tech record
    public void checkTestResultPostOutcome(Map<String, String> response) {

        String responseCode = response.get(TechnicalRecordsV3.STATUS_CODE_KEY);
        String responseBody = response.get(TechnicalRecordsV3.RESPONSE_BODY_KEY);

        if (Integer.parseInt(responseCode) != 201) {
            throw new AssertionError(String.format(
                    "Error posting to tech record service " +
                            "%n responseCode: " + responseCode +
                            "%n responseBody: " + responseBody));
        }

    }

    public CompleteTestResults createTestResultBasedOnTechRecord(CompleteTestResults testResult, String systemNumber, String vin, String vrm)
    {
       
        testResult.setTestResultId(UUID.randomUUID().toString());
        testResult.setSystemNumber(systemNumber);
        testResult.setVin(vin);
        testResult.setVrm(vrm);
        TestTypes ts = testResult.getTestTypes();
        if(ts.size()>0)
        {
        OffsetDateTime datetime = OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC);
        ts.get(0).setTestTypeStartTimestamp(datetime);
        ts.get(0).setTestTypeEndTimestamp(datetime.plusMinutes(30));
        ts.get(0).setTestExpiryDate(datetime.plusYears(1));
        testResult.setTestTypes(ts);
        testResult.setTestStartTimestamp(datetime);
        testResult.setTestEndTimestamp(datetime);
    }
        return testResult;

    }

}
