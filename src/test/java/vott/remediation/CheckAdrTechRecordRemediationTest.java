package vott.remediation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import vott.auth.GrantType;
import vott.auth.OAuthVersion;
import vott.auth.TokenService;
import vott.models.dto.seeddata.AdrRemediationClassGenerator;
import vott.api.TechnicalRecordsV3;
import vott.models.dto.techrecordsv3.AdrRemediationClass;
import vott.models.dto.techrecordsv3.TechRecordSearchResponse;
import vott.updatestore.SharedUtilities;

public class CheckAdrTechRecordRemediationTest {

    private final AdrRemediationClassGenerator adrDataGen = new AdrRemediationClassGenerator(new AdrRemediationClass());
    private final TokenService v1ImplicitTokens = new TokenService(OAuthVersion.V1, GrantType.IMPLICIT);
    private static final String PAYLOAD_PATH = "src/main/resources/payloads/";

    @Test
    public void checkAdrDataRemediated() {

        Path path = Paths.get(PAYLOAD_PATH + "Remediation/AdrTechRecordUpdates/example.jsonl");

        try {
            List<String> contents = Files.readAllLines(path);

            for (String content : contents) {
                AdrRemediationClass adrData = adrDataGen.createTechRecordFromJsonString(content);

                String systemNumber = adrData.getSystem_Number();

                Map<String, String> techRecordList = TechnicalRecordsV3
                        .searchForTechnicalRecord(v1ImplicitTokens.getBearerToken(), systemNumber);

                String response = techRecordList.get(TechnicalRecordsV3.RESPONSE_BODY_KEY);

                List<TechRecordSearchResponse> responseList = SharedUtilities.createSearchResponseFromString(response);

                for (TechRecordSearchResponse techRecordResponse : responseList) {

                    String statusCode = techRecordResponse.getTechRecord_statusCode();

                    if (statusCode.equalsIgnoreCase("current")) {
                        String timestamp = techRecordResponse.getCreatedTimestamp();

                        Map<String, String> responseMap = TechnicalRecordsV3
                                .getTechnicalRecord(v1ImplicitTokens.getBearerToken(), systemNumber, timestamp);

                        String responseBody = responseMap.get(TechnicalRecordsV3.RESPONSE_BODY_KEY);

                        AdrRemediationClass adrDataReturned = adrDataGen.createTechRecordFromJsonString(responseBody);

                        Assert.assertEquals(
                                String.join(
                                        "something different: expected: " + System.lineSeparator() + adrData.toString()
                                                + System.lineSeparator() + " actual: " + adrDataReturned.toString()),
                                adrData, adrDataReturned);
                        break;
                    }
                }
            }
        }

        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
