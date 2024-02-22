package vott.remediation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import vott.auth.GrantType;
import vott.auth.OAuthVersion;
import vott.auth.TokenService;
import vott.models.dto.seeddata.TechRecordHgvCompleteGenerator;
import vott.api.TechnicalRecordsV3;
import vott.models.dto.techrecordsv3.TechRecordHgvComplete;
import vott.updatestore.SharedUtilities;

public class SeedTechRecordData {

        private List<String> remediationFileContents = new ArrayList<>();
        private String payloadPath = "src/main/resources/payloads/";
        private TechRecordHgvCompleteGenerator hgvTechRecordGen = new TechRecordHgvCompleteGenerator(
                        new TechRecordHgvComplete());
        private TokenService v1ImplicitTokens = new TokenService(OAuthVersion.V1, GrantType.IMPLICIT);
        private SharedUtilities sharedUtils = new SharedUtilities();

        @Before
        public void setup() {
        }

        // TODO add in link to Jira ticket / Zephyr Ref
        @Test
        public void addADRtoTechRecord() {

                TechRecordHgvComplete techRecordCreated = createHgvTechRecord(
                                payloadPath + "TechRecordsV3/HGV_Tech_record_No_ADR.json");
                // TODO create class to represent only ADR JSON attributes and use this instead
                // of TechRecord...Complete
                TechRecordHgvComplete adrDataToPatch = hgvTechRecordGen
                                .createTechRecordFromJsonFile(payloadPath + "TechRecordsV3/ADR_fields_only.json");
                adrDataToPatch.setSystem_Number(techRecordCreated.getSystemNumber());
                adrDataToPatch.setCreated_Timestamp(techRecordCreated.getCreatedTimestamp());
                String remediationInput = hgvTechRecordGen.createJsonStringFromTechRecord(adrDataToPatch);
                remediationFileContents.add(remediationInput);
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

        // print contents of to be remediation file, could write to file ready to be
        // uploaded to S3
        @After
        public void teardown() {
                System.out.println(remediationFileContents.toString());
        }
}
