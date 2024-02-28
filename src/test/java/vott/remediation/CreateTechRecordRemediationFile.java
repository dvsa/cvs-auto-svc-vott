package vott.remediation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import vott.auth.GrantType;
import vott.auth.OAuthVersion;
import vott.auth.TokenService;
import vott.models.dto.seeddata.AdrRemediationClassGenerator;
import vott.models.dto.seeddata.TechRecordHgvCompleteGenerator;
import vott.api.TechnicalRecordsV3;
import vott.models.dto.techrecordsv3.AdrRemediationClass;
import vott.models.dto.techrecordsv3.TechRecordHgvComplete;
import vott.updatestore.SharedUtilities;

public class CreateTechRecordRemediationFile {

    private static List<String> remediationFileContents = new ArrayList<>();
    private final TechRecordHgvCompleteGenerator hgvTechRecordGen = new TechRecordHgvCompleteGenerator(
            new TechRecordHgvComplete());
    private final AdrRemediationClassGenerator adrDataGen = new AdrRemediationClassGenerator(new AdrRemediationClass());
    private final TokenService v1ImplicitTokens = new TokenService(OAuthVersion.V1, GrantType.IMPLICIT);
    private final SharedUtilities sharedUtils = new SharedUtilities();
    private static final String PAYLOAD_PATH = "src/main/resources/payloads/";
    private AdrRemediationClass adrData;

    @Before
    public void setup() {
        TechRecordHgvComplete techRecord = createHgvTechRecord(
                PAYLOAD_PATH + "TechRecordsV3/HGV_Tech_record_No_ADR.json");
        adrData = adrDataGen.createTechRecordFromJsonFile(PAYLOAD_PATH + "TechRecordsV3/ADR_fields_only.json");
        adrData.setSystem_Number(techRecord.getSystemNumber());
        adrData.setCreated_Timestamp(techRecord.getCreatedTimestamp());
    }

    @Test
    public void allAdrFieldsCompleted() {

        // amend data as required
        adrData.setTechRecordAdrDetailsApplicantDetailsTown("new town");
        addAdrDataToList();
    }

    private TechRecordHgvComplete createHgvTechRecord(String filePath) {

        TechRecordHgvComplete techRecord = hgvTechRecordGen
                .createTechRecordFromJsonFile(filePath);
        hgvTechRecordGen.randomizeHgvUniqueValues(techRecord);
        Map<String, String> response = TechnicalRecordsV3.postTechnicalRecordV3ObjectResponse(techRecord,
                v1ImplicitTokens.getBearerToken());
        sharedUtils.checkTechRecordPostOutcome(response);
        String techRecordResponseBody = response.get(TechnicalRecordsV3.RESPONSE_BODY_KEY);
        TechRecordHgvComplete techRecordReturned = hgvTechRecordGen
                .createTechRecordFromJsonString(techRecordResponseBody);
        System.out.println("created tech record: " + techRecordReturned.getSystemNumber() + " "
                + techRecordReturned.getCreatedTimestamp());
        return techRecordReturned;
    }

    private void addAdrDataToList() {
        String adrDataAsJson = adrDataGen.createJsonStringFromTechRecord(adrData);
        remediationFileContents.add(adrDataAsJson);
    }

    @AfterClass
    public static void teardown() {

        // TODO string to JSON file
        String output = String.join(System.lineSeparator(), remediationFileContents);
        System.out.println(output);
    }
}
