package vott.remediation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import vott.auth.GrantType;
import vott.auth.OAuthVersion;
import vott.auth.TokenService;
import vott.models.dto.seeddata.TechRecordHgvCompleteGenerator;
import vott.api.TechnicalRecordsV3;
import vott.models.dto.techrecordsv3.TechRecordAdrDetailsTankTankDetailsTc3Detail;
import vott.models.dto.techrecordsv3.TechRecordHgvComplete;
import vott.updatestore.SharedUtilities;


public class SeedTechRecordDataTest {

    private final List<String> remediationFileContents = new ArrayList<>();
    private final TechRecordHgvCompleteGenerator hgvTechRecordGen = new TechRecordHgvCompleteGenerator(
                    new TechRecordHgvComplete());
    private final TokenService v1ImplicitTokens = new TokenService(OAuthVersion.V1, GrantType.IMPLICIT);
    private final SharedUtilities sharedUtils = new SharedUtilities();
    private String payloadPath;
    private TechRecordHgvComplete techRecord;
    private TechRecordHgvComplete adrDataToPatch;
    private TechRecordHgvComplete adrPatchedData;

    public void baseSetup() {
        this.payloadPath = "src/main/resources/payloads/";
        createBaseRecordAndAdrData();
        postPatchGetAdrRecord();
    }

    // TODO add in link to Jira ticket / Zephyr Ref

    // Helper functions
    public void createBaseRecordAndAdrData() {
        this.payloadPath = "src/main/resources/payloads/";
        this.techRecord = createHgvTechRecord(payloadPath + "TechRecordsV3/HGV_Tech_record_No_ADR.json");
        this.adrDataToPatch = hgvTechRecordGen.createTechRecordFromJsonFile(payloadPath + "TechRecordsV3/ADR_fields_only.json");
    }

    public void postPatchGetAdrRecord(){
        Map<String,String> outcomeUpdate = TechnicalRecordsV3.updateTechnicalRecord(adrDataToPatch, v1ImplicitTokens.getBearerToken(), techRecord.getSystemNumber(), techRecord.getCreatedTimestamp());
        TechRecordHgvComplete techRecordPostOutcome = hgvTechRecordGen
                .createTechRecordFromJsonString(outcomeUpdate.get("responseBody"));
        String outcomeSystemNumber = techRecordPostOutcome.getSystemNumber();
        String outcomeCreatedTimestamp = techRecordPostOutcome.getCreatedTimestamp();

        Map<String,String> outcomeGet = TechnicalRecordsV3.getTechnicalRecord(v1ImplicitTokens.getBearerToken(), outcomeSystemNumber, outcomeCreatedTimestamp);
        String outcomeResponseBody = outcomeGet.get("responseBody");
        this.adrPatchedData = hgvTechRecordGen.createTechRecordFromJsonString(outcomeResponseBody);
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

    // Tests
    @Test
    public void adrReturnedPayloadTechRecordChecks() {
        //Assert base tech record is the same as what we get back
        baseSetup();

        Assert.assertEquals(techRecord.getSystemNumber(), adrPatchedData.getSystemNumber());
        //TODO Should we have to convert this to uppercase?
        Assert.assertEquals(techRecord.getVin().toUpperCase(), adrPatchedData.getVin());
        Assert.assertEquals(techRecord.getPrimaryVrm(), adrPatchedData.getPrimaryVrm());
    }

    @Test
    public void adrReturnedPayloadApplicantDetailsChecks() {
        //Applicant details completed
        baseSetup();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsApplicantDetailsCity(), adrPatchedData.getTechRecordAdrDetailsApplicantDetailsCity());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsApplicantDetailsName(), adrPatchedData.getTechRecordAdrDetailsApplicantDetailsName());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsApplicantDetailsPostcode(), adrPatchedData.getTechRecordAdrDetailsApplicantDetailsPostcode());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsApplicantDetailsStreet(), adrPatchedData.getTechRecordAdrDetailsApplicantDetailsStreet());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsApplicantDetailsTown(), adrPatchedData.getTechRecordAdrDetailsApplicantDetailsTown());
    }

    @Test
    public void adrNoApplicantDetails() {
        //All ApplicantDetails fields are null
        createBaseRecordAndAdrData();
        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsCity(null);
        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsName(null);
        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsPostcode(null);
        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsStreet(null);
        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsTown(null);
        postPatchGetAdrRecord();

        Assert.assertNull(adrPatchedData.getTechRecordAdrDetailsApplicantDetailsCity());
        Assert.assertNull(adrPatchedData.getTechRecordAdrDetailsApplicantDetailsName());
        Assert.assertNull(adrPatchedData.getTechRecordAdrDetailsApplicantDetailsPostcode());
        Assert.assertNull(adrPatchedData.getTechRecordAdrDetailsApplicantDetailsStreet());
    }

    @Test
    public void adrOnePermittedDangerousGoods() {
        //One permitted dangerous goods (not explosives type 2 or 3)

        baseSetup();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsPermittedDangerousGoods(), adrPatchedData.getTechRecordAdrDetailsPermittedDangerousGoods());
    }

    @Test
    public void adrTwoPermittedDangerousGoods() {
        //Two permitted dangerous goods (not explosives type 2 or 3)

        createBaseRecordAndAdrData();
        List<String> permittedDangerousGoodsList = Arrays.asList("FP <61 (FL)", "AT");
        adrDataToPatch.setTechRecordAdrDetailsPermittedDangerousGoods(permittedDangerousGoodsList);
        postPatchGetAdrRecord();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsPermittedDangerousGoods(), adrPatchedData.getTechRecordAdrDetailsPermittedDangerousGoods());
    }

    @Test
    public void adrAllPermittedDangerousGoods() {
        //Two permitted dangerous goods (not explosives type 2 or 3)

        createBaseRecordAndAdrData();
        List<String> permittedDangerousGoodsList = List.of(
                "FP <61 (FL)",
                "AT",
                "Class 5.1 Hydrogen Peroxide (OX)",
                "MEMU",
                "Carbon Disulphide",
                "Hydrogen",
                "Explosives (type 2)",
                "Explosives (type 3)"
        );
        adrDataToPatch.setTechRecordAdrDetailsPermittedDangerousGoods(permittedDangerousGoodsList);
        postPatchGetAdrRecord();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsPermittedDangerousGoods(), adrPatchedData.getTechRecordAdrDetailsPermittedDangerousGoods());
    }

    @Test
    public void adrExplosivesType2PermittedDangerousGoods() {
        //Explosives Type 2 means Compatibility Group J is populated

        createBaseRecordAndAdrData();
        List<String> permittedDangerousGoodsList = List.of("Explosives (type 2)");
        adrDataToPatch.setTechRecordAdrDetailsPermittedDangerousGoods(permittedDangerousGoodsList);
        adrDataToPatch.setTechRecordAdrDetailsCompatibilityGroupJ("I");
        postPatchGetAdrRecord();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsPermittedDangerousGoods(), adrPatchedData.getTechRecordAdrDetailsPermittedDangerousGoods());
        Assert.assertNotNull(adrDataToPatch.getTechRecordAdrDetailsCompatibilityGroupJ());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsCompatibilityGroupJ(), adrPatchedData.getTechRecordAdrDetailsCompatibilityGroupJ());
    }

    @Test
    public void adrExplosivesType3PermittedDangerousGoods() {
        //Explosives Type 2 means Compatibility Group J is populated
        createBaseRecordAndAdrData();
        List<String> permittedDangerousGoodsList = List.of("Explosives (type 3)");
        adrDataToPatch.setTechRecordAdrDetailsPermittedDangerousGoods(permittedDangerousGoodsList);
        adrDataToPatch.setTechRecordAdrDetailsCompatibilityGroupJ("E");
        postPatchGetAdrRecord();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsPermittedDangerousGoods(), adrPatchedData.getTechRecordAdrDetailsPermittedDangerousGoods());
        Assert.assertNotNull(adrDataToPatch.getTechRecordAdrDetailsCompatibilityGroupJ());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsCompatibilityGroupJ(), adrPatchedData.getTechRecordAdrDetailsCompatibilityGroupJ());
    }

    @Test
    public void adrOneAdditionalNotesNumber() {
        //One guidance notes
        baseSetup();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsAdditionalNotesNumber(), adrPatchedData.getTechRecordAdrDetailsAdditionalNotesNumber());
    }

    @Test
    public void adrTwoAdditionalNotesNumber() {
        //Two guidance notes
        createBaseRecordAndAdrData();
        List<String> additionalNotesNumberList = List.of("1", "1A");
        adrDataToPatch.setTechRecordAdrDetailsAdditionalNotesNumber(additionalNotesNumberList);
        postPatchGetAdrRecord();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsAdditionalNotesNumber(), adrPatchedData.getTechRecordAdrDetailsAdditionalNotesNumber());
    }

    @Test
    public void adrAllAdditionalNotesNumber() {
        //All guidance notes
        createBaseRecordAndAdrData();
        List<String> additionalNotesNumberList = List.of(
                "1",
                "1A",
                "2",
                "3",
                "V1B",
                "T1B"
        );
        adrDataToPatch.setTechRecordAdrDetailsAdditionalNotesNumber(additionalNotesNumberList);
        postPatchGetAdrRecord();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsAdditionalNotesNumber(), adrPatchedData.getTechRecordAdrDetailsAdditionalNotesNumber());
    }

    @Test
    public void adrTypeApprovalNumber() {
        //ADR Type Approval Number completed
        baseSetup();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsAdrTypeApprovalNo(), adrPatchedData.getTechRecordAdrDetailsAdrTypeApprovalNo());
    }

    @Test
    public void adrNoTypeApprovalNumber() {
        //ADR Type Approval Number completed
        createBaseRecordAndAdrData();
        adrDataToPatch.setTechRecordAdrDetailsAdrTypeApprovalNo(null);
        postPatchGetAdrRecord();

        Assert.assertNull(adrPatchedData.getTechRecordAdrDetailsAdrTypeApprovalNo());
    }

    @Test
    public void adrSubstancesPermittedTankCode() {
        //Substances permitted is "Substances permitted under the tank code and any special provisions specified in 9 may be carried"
        baseSetup();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted());
    }

    @Test
    public void adrSubstancesPermittedUnNumber() {
        //Substances permitted is "Substances (Class UN number and if necessary packing group and proper shipping name) may be carried"
        createBaseRecordAndAdrData();
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted("Substances (Class UN number and if necessary packing group and proper shipping name) may be carried");
        postPatchGetAdrRecord();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted());
    }

    @Test
    public void adrStatementSelectedAndProvided() {
        //TankStatement.Statement is selected and provided
        createBaseRecordAndAdrData();
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementSelect("Statement");
        postPatchGetAdrRecord();

        Assert.assertEquals("Statement", adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTankStatementSelect());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementStatement(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTankStatementStatement());
    }

    @Test
    public void adrProductListRefNoCompleteNoListUnNo() {
        //productListRefNo is completed, and there is no productListUnNo
        createBaseRecordAndAdrData();
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo(null);
        postPatchGetAdrRecord();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo());
    }

    @Test
    public void adr_oneProductListUnNo_noProductListRefNo_productListCompleted_specialProvisionsCompleted() {
        //One productListUnNo, no productListRefNo, additionalDetailsProductList completed, specialProvisionsCompleted
        createBaseRecordAndAdrData();
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo(null);
        postPatchGetAdrRecord();

        Assert.assertNull(adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo());
        Assert.assertNotNull(adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTankStatementProductList());
        Assert.assertNotNull(adrPatchedData.getTechRecordAdrDetailsTankTankDetailsSpecialProvisions());

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementProductList(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTankStatementProductList());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsSpecialProvisions(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsSpecialProvisions());
    }

    @Test
    public void adr_twoProductListUnNo_noProductListRefNo_noProductList_noSpecialProvisions() {
        //Two productListUnNo, no productListRefNo, additionalDetailsProductList completed, specialProvisionsCompleted
        createBaseRecordAndAdrData();
        List<String> productListUnNoList = List.of(
                "123123",
                "456456"
                );
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo(productListUnNoList);
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo(null);
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementProductList(null);
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsSpecialProvisions(null);
        postPatchGetAdrRecord();

        Assert.assertNull(adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo());
        Assert.assertNull(adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTankStatementProductList());
        Assert.assertNull(adrPatchedData.getTechRecordAdrDetailsTankTankDetailsSpecialProvisions());

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementProductList(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTankStatementProductList());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsSpecialProvisions(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsSpecialProvisions());
    }

    @Test
    public void adrTc2Details_noTc3Details_noMemosApply() {
        createBaseRecordAndAdrData();
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTc3Details(null);
        adrDataToPatch.setTechRecordAdrDetailsMemosApply(null);
        postPatchGetAdrRecord();

        Assert.assertNull(adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTc3Details());
        Assert.assertNull(adrPatchedData.getTechRecordAdrDetailsMemosApply());

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTc3Details(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTc3Details());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsMemosApply(), adrPatchedData.getTechRecordAdrDetailsMemosApply());
    }

    @Test
    public void adrTc2Details_oneTc3Details_typeIntermediate() {
        baseSetup();

        Assert.assertNotNull(adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTc3Details());
        Assert.assertNotNull(adrPatchedData.getTechRecordAdrDetailsMemosApply());

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTc3Details(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTc3Details());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsMemosApply(), adrPatchedData.getTechRecordAdrDetailsMemosApply());
    }

    @Test
    public void adrTc2Details_twoTc3Details_typePeriodicExceptional() {
        createBaseRecordAndAdrData();
        TechRecordAdrDetailsTankTankDetailsTc3Detail tc3DetailsPeriodic = new TechRecordAdrDetailsTankTankDetailsTc3Detail("periodic","12345","2024-01-01");
        TechRecordAdrDetailsTankTankDetailsTc3Detail tc3DetailsExceptional = new TechRecordAdrDetailsTankTankDetailsTc3Detail("exceptional","67890","2024-02-02");
        List<TechRecordAdrDetailsTankTankDetailsTc3Detail> tc3DetailList = new ArrayList<>();
        tc3DetailList.add(tc3DetailsPeriodic);
        tc3DetailList.add(tc3DetailsExceptional);
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTc3Details(tc3DetailList);
        postPatchGetAdrRecord();

        Assert.assertNotNull(adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTc3Details());
        Assert.assertNotNull(adrPatchedData.getTechRecordAdrDetailsMemosApply());

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTc3Details(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTc3Details());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsMemosApply(), adrPatchedData.getTechRecordAdrDetailsMemosApply());
    }

    // print contents of to be remediation file, could write to file ready to be
    // uploaded to S3
    @After
    public void teardown() {
        String output = String.join(System.lineSeparator(), remediationFileContents);
        System.out.println(output);
    }
}
