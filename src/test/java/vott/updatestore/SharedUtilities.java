package vott.updatestore;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import vott.api.TechnicalRecordsV3;
import vott.api.TestResultAPI;
import vott.json.GsonInstance;
import vott.models.dto.seeddata.TechRecordHgvCompleteGenerator;
import vott.models.dto.techrecords.TechRecordPOST;
import vott.models.dto.techrecordsv3.TechRecordHgvComplete;
import vott.models.dto.techrecordsv3.TechRecordV3;
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

    private final Gson gson = GsonInstance.get();

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

    public TechRecordHgvComplete loadTechRecord(String fileName) {
        TechRecordHgvCompleteGenerator hgv_trg = new TechRecordHgvCompleteGenerator(new TechRecordHgvComplete());
        TechRecordHgvComplete techRecordNotRandomised = hgv_trg.createTechRecordFromJsonFile(fileName);
        TechRecordHgvComplete techRecord = hgv_trg.randomizeHgvUniqueValues(techRecordNotRandomised);

        return techRecord;
    }

    public CompleteTestResults loadTestResults(TechRecordHgvComplete techRecord, String fileName) {
        return matchKeys(techRecord, readTestResult(fileName));
    }

    public String convertBooleanToStringNumericBoolean(Boolean bool) {
        String stringBoolean = bool.toString();
        String numericStringBoolean = switch (stringBoolean) {
            case "true" -> "1";
            case "false" -> "0";
            default -> "null";
        };
        return numericStringBoolean;
    }

    // --------------------------------------
    private CompleteTestResults matchKeys(TechRecordHgvComplete techRecord, CompleteTestResults testResult) {
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

    //implemented separately in case test result API is different to tech record
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

    /**
     * This method posts both a tech record and a test result, and then checks that they return a 201 response
     * If they do not return a 201 response, the status code and the response body will be returned for debugging
     * @param techRecord a V3 tech record
     * @param testResult a test result
     * @param token a v1 or v2 implicit token
     */
    public void postAndValidateTechRecordTestResultResponse(TechRecordV3 techRecord, CompleteTestResults testResult, String token) {
        Map<String, String> techRecordResponse;
        techRecordResponse = TechnicalRecordsV3.postTechnicalRecordV3ObjectResponse(techRecord, token);
        checkTechRecordPostOutcome(techRecordResponse);

        Map<String, String> testResultResponse;
        testResultResponse = TestResultAPI.postTestResultReturnResult(testResult, token);
        checkTestResultPostOutcome(testResultResponse);
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
