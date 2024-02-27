package vott.remediation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import vott.auth.GrantType;
import vott.auth.OAuthVersion;
import vott.auth.TokenService;
import vott.models.dto.seeddata.AdrRemediationClassGenerator;
import vott.models.dto.seeddata.TechRecordHgvCompleteGenerator;
import vott.api.TechnicalRecordsV3;
import vott.models.dto.techrecordsv3.*;
import vott.updatestore.SharedUtilities;


public class SeedTechRecordDataTest {

    private final List<String> remediationFileContents = new ArrayList<>();
    private final TechRecordHgvCompleteGenerator hgvTechRecordGen = new TechRecordHgvCompleteGenerator(
                    new TechRecordHgvComplete());
    private final AdrRemediationClassGenerator adrRemediationClassGen = new AdrRemediationClassGenerator(new AdrRemediationClass());
    private final TokenService v1ImplicitTokens = new TokenService(OAuthVersion.V1, GrantType.IMPLICIT);
    private final SharedUtilities sharedUtils = new SharedUtilities();
    private String payloadPath;
    private TechRecordHgvComplete techRecord;
    private AdrRemediationClass adrDataToPatch;
    private TechRecordHgvComplete adrPatchedData;

    @Before
    public void baseSetup() {
        //Used for when you have an unaltered tech record and ADR details
        createBaseRecordAndAdrData();
    }

    // TODO add in link to Jira ticket / Zephyr Ref

    // Helper functions
    public void createBaseRecordAndAdrData() {
        //Part 1, use when you need to alter the tech record and/or adr details
        this.payloadPath = "src/main/resources/payloads/";
        this.techRecord = createHgvTechRecord(payloadPath + "TechRecordsV3/HGV_Tech_record_No_ADR.json");
        this.adrDataToPatch = adrRemediationClassGen.createTechRecordFromJsonFile(payloadPath + "TechRecordsV3/ADR_fields_only.json");
    }

    public void postPatchGetAdrRecord(){
        //Part 2, use when you need to alter the tech record and/or adr details
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
    public void techRecordBaseFields() {
        //Assert base tech record is the same as what we get back
        //Fields aren't changed here because they are randomised within the @Before method
        postPatchGetAdrRecord();

        Assert.assertEquals(techRecord.getSystemNumber(), adrPatchedData.getSystemNumber());
        //TODO Should we have to convert this to uppercase?
        Assert.assertEquals(techRecord.getVin(), adrPatchedData.getVin());
        Assert.assertEquals(techRecord.getPrimaryVrm(), adrPatchedData.getPrimaryVrm());
    }

    @Test
    public void adrReturnedPayloadApplicantDetailsChecks() {
        //Applicant details completed
        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsCity("applicantDetailsCITY_1");
        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsName("applicantDetails_Name_1");
        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsPostcode("POST-CODE1");
        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsStreet("applicantDetailsSTREET");
        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsTown("applicantDetailsTOWN_1");
        postPatchGetAdrRecord();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsApplicantDetailsCity(), adrPatchedData.getTechRecordAdrDetailsApplicantDetailsCity());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsApplicantDetailsName(), adrPatchedData.getTechRecordAdrDetailsApplicantDetailsName());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsApplicantDetailsPostcode(), adrPatchedData.getTechRecordAdrDetailsApplicantDetailsPostcode());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsApplicantDetailsStreet(), adrPatchedData.getTechRecordAdrDetailsApplicantDetailsStreet());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsApplicantDetailsTown(), adrPatchedData.getTechRecordAdrDetailsApplicantDetailsTown());
    }

    @Test
    public void adrNoApplicantDetails() {
        //All ApplicantDetails fields are null
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
        List<String> permittedDangerousGoodsList = List.of("FP <61 (FL)");
        adrDataToPatch.setTechRecordAdrDetailsPermittedDangerousGoods(permittedDangerousGoodsList);
        postPatchGetAdrRecord();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsPermittedDangerousGoods(), adrPatchedData.getTechRecordAdrDetailsPermittedDangerousGoods());
    }

    @Test
    public void adrTwoPermittedDangerousGoods() {
        //Two permitted dangerous goods (not explosives type 2 or 3)
        List<String> permittedDangerousGoodsList = Arrays.asList("FP <61 (FL)", "AT");
        adrDataToPatch.setTechRecordAdrDetailsPermittedDangerousGoods(permittedDangerousGoodsList);
        postPatchGetAdrRecord();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsPermittedDangerousGoods(), adrPatchedData.getTechRecordAdrDetailsPermittedDangerousGoods());
    }

    @Test
    public void adrAllPermittedDangerousGoods() {
        //Two permitted dangerous goods (not explosives type 2 or 3)
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
        //Explosives Type 3 means Compatibility Group J is populated
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
        List<String> additionalNotesNumberList = List.of("1");
        adrDataToPatch.setTechRecordAdrDetailsAdditionalNotesNumber(additionalNotesNumberList);
        postPatchGetAdrRecord();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsAdditionalNotesNumber(), adrPatchedData.getTechRecordAdrDetailsAdditionalNotesNumber());
    }

    @Test
    public void adrTwoAdditionalNotesNumber() {
        //Two guidance notes
        List<String> additionalNotesNumberList = List.of("1", "1A");
        adrDataToPatch.setTechRecordAdrDetailsAdditionalNotesNumber(additionalNotesNumberList);
        postPatchGetAdrRecord();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsAdditionalNotesNumber(), adrPatchedData.getTechRecordAdrDetailsAdditionalNotesNumber());
    }

    @Test
    public void adrAllAdditionalNotesNumber() {
        //All guidance notes
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
        adrDataToPatch.setTechRecordAdrDetailsAdrTypeApprovalNo("adrTypeApprovalNo_1");
        postPatchGetAdrRecord();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsAdrTypeApprovalNo(), adrPatchedData.getTechRecordAdrDetailsAdrTypeApprovalNo());
    }

    @Test
    public void adrNoTypeApprovalNumber() {
        //No ADR Type Approval Number
        adrDataToPatch.setTechRecordAdrDetailsAdrTypeApprovalNo(null);
        postPatchGetAdrRecord();

        Assert.assertNull(adrPatchedData.getTechRecordAdrDetailsAdrTypeApprovalNo());
    }

    @Test
    public void adrSubstancesPermittedTankCode() {
        //Substances permitted is "Substances permitted under the tank code and any special provisions specified in 9 may be carried"
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted("Substances permitted under the tank code and any special provisions specified in 9 may be carried");
        postPatchGetAdrRecord();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted());
    }

    @Test
    public void adrSubstancesPermittedUnNumber() {
        //Substances permitted is "Substances (Class UN number and if necessary packing group and proper shipping name) may be carried"
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted("Substances (Class UN number and if necessary packing group and proper shipping name) may be carried");
        postPatchGetAdrRecord();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted());
    }

    @Test
    public void adrStatementSelectedAndProvided() {
        //TankStatement.Statement is selected and provided
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementSelect("Statement");
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementStatement("statement_1");
        postPatchGetAdrRecord();

        Assert.assertEquals("Statement", adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTankStatementSelect());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementStatement(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTankStatementStatement());
    }

    @Test
    public void adrProductListRefNoCompleteNoListUnNo() {
        //productListRefNo is completed, and there is no productListUnNo
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo(null);
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo("123456");
        postPatchGetAdrRecord();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo());
    }

    @Test
    public void adrOneProductListUnNo_noProductListRefNo_productListCompleted_specialProvisionsCompleted() {
        //One productListUnNo, no productListRefNo, additionalDetailsProductList completed, specialProvisionsCompleted
        List<String> productListUnNoList = List.of("123123");
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo(null);
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo(productListUnNoList);
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementProductList("productList_1");
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsSpecialProvisions("specialProvisions_1");
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
    public void adrTwoProductListUnNo_noProductListRefNo_noProductList_noSpecialProvisions() {
        //Two productListUnNo, no productListRefNo, no additionalDetailsProductList, no specialProvisions
        List<String> productListUnNoList = List.of(
                "123123",
                "456456"
                );
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo(productListUnNoList);
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementProductList(null);
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsSpecialProvisions(null);
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo(null);
        postPatchGetAdrRecord();

        Assert.assertNull(adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo());
        Assert.assertNull(adrPatchedData.getTechRecordAdrDetailsTankTankDetailsSpecialProvisions());
        Assert.assertNull(adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTankStatementProductList());

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementProductList(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsTankStatementProductList());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsSpecialProvisions(), adrPatchedData.getTechRecordAdrDetailsTankTankDetailsSpecialProvisions());
    }

    @Test
    public void adrTc2Details_noTc3Details_noMemosApply() {
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo("12345");
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate("2024-06-01");
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type("initial");
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
        TechRecordAdrDetailsTankTankDetailsTc3Detail tc3DetailsIntermediate = new TechRecordAdrDetailsTankTankDetailsTc3Detail("intermediate","13579","2024-06-12");
        List<TechRecordAdrDetailsTankTankDetailsTc3Detail> tc3DetailList = new ArrayList<>();
        tc3DetailList.add(tc3DetailsIntermediate);
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTc3Details(tc3DetailList);
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo("12345");
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate("2024-06-01");
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type("initial");
        postPatchGetAdrRecord();

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

    @Test
    public void adrOneMemosApply() {
        List<String> memosApplyList = List.of("07/09 3mth leak ext");
        adrDataToPatch.setTechRecordAdrDetailsMemosApply(memosApplyList);
        postPatchGetAdrRecord();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsMemosApply(), adrPatchedData.getTechRecordAdrDetailsMemosApply());
    }

    @Test
    public void adrM145StatementTrue() {
        adrDataToPatch.setTechRecordAdrDetailsM145Statement(true);
        postPatchGetAdrRecord();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsM145Statement(), adrPatchedData.getTechRecordAdrDetailsM145Statement());
    }

    @Test
    public void adrM145StatementFalse() {
        adrDataToPatch.setTechRecordAdrDetailsM145Statement(false);
        postPatchGetAdrRecord();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsM145Statement(), adrPatchedData.getTechRecordAdrDetailsM145Statement());
    }

    @Test
    public void adrListStatementApplicableTrue_batteryListNumberComplete() {
        //listStatementApplicable true, batteryListNumber completed
        adrDataToPatch.setTechRecordAdrDetailsListStatementApplicable(true);
        adrDataToPatch.setTechRecordAdrDetailsBatteryListNumber("BATTERY1");
        postPatchGetAdrRecord();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsListStatementApplicable(), adrPatchedData.getTechRecordAdrDetailsListStatementApplicable());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsBatteryListNumber(), adrPatchedData.getTechRecordAdrDetailsBatteryListNumber());
    }

    @Test
    public void adrListStatementApplicableFalse() {
        //listStatementApplicable false
        adrDataToPatch.setTechRecordAdrDetailsListStatementApplicable(false);
        postPatchGetAdrRecord();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsListStatementApplicable(), adrPatchedData.getTechRecordAdrDetailsListStatementApplicable());
    }

    @Test
    public void adrBrakeDeclarationsSeenTrue_brakeEnduranceTrue_declarationsSeenTrue_newCertificateRequestedFalse() {
        //brakeDeclarationsSeen true, brakeEndurance True, declarationsSeen True, newCertificateRequested False
        adrDataToPatch.setTechRecordAdrDetailsBrakeDeclarationsSeen(true);
        adrDataToPatch.setTechRecordAdrDetailsBrakeEndurance(true);
        adrDataToPatch.setTechRecordAdrDetailsDeclarationsSeen(true);
        adrDataToPatch.setTechRecordAdrDetailsNewCertificateRequested(false);
        postPatchGetAdrRecord();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsBrakeDeclarationsSeen(), adrPatchedData.getTechRecordAdrDetailsBrakeDeclarationsSeen());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsBrakeEndurance(), adrPatchedData.getTechRecordAdrDetailsBrakeEndurance());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsDeclarationsSeen(), adrPatchedData.getTechRecordAdrDetailsDeclarationsSeen());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsNewCertificateRequested(), adrPatchedData.getTechRecordAdrDetailsNewCertificateRequested());
    }

    @Test
    public void adrBrakeDeclarationsSeenTrue_brakeEnduranceFalse_declarationsSeenFalse_newCertificateRequestedTrue() {
        //brakeDeclarationsSeen true, brakeEndurance False, declarationsSeen False, newCertificateRequested True
        adrDataToPatch.setTechRecordAdrDetailsBrakeDeclarationsSeen(true);
        adrDataToPatch.setTechRecordAdrDetailsBrakeEndurance(false);
        adrDataToPatch.setTechRecordAdrDetailsDeclarationsSeen(false);
        adrDataToPatch.setTechRecordAdrDetailsNewCertificateRequested(true);
        techRecord.setTechRecordVehicleType("trl");
        postPatchGetAdrRecord();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsBrakeDeclarationsSeen(), adrPatchedData.getTechRecordAdrDetailsBrakeDeclarationsSeen());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsBrakeEndurance(), adrPatchedData.getTechRecordAdrDetailsBrakeEndurance());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsDeclarationsSeen(), adrPatchedData.getTechRecordAdrDetailsDeclarationsSeen());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsNewCertificateRequested(), adrPatchedData.getTechRecordAdrDetailsNewCertificateRequested());
        Assert.assertEquals("trl", techRecord.getTechRecordVehicleType());
    }

    @Test
    public void adrNoAdditionalExaminerNotes() {
        adrDataToPatch.setTechRecordAdrDetailsAdditionalExaminerNotes(null);
        postPatchGetAdrRecord();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsAdditionalExaminerNotes(), adrPatchedData.getTechRecordAdrDetailsAdditionalExaminerNotes());
    }

    @Test
    public void adrOneAdditionalExaminerNotes_adrCertificateNotesCompleted() {
        List<TechRecordAdrDetailsAdditionalExaminerNote> additionalExaminerNoteList = new ArrayList<>();
        TechRecordAdrDetailsAdditionalExaminerNote adrDetailsAdditionalExaminerNote = new TechRecordAdrDetailsAdditionalExaminerNote("2023-05-30", "additionalExaminerNotes_lastUpdatedBy_1", "additionalExaminerNotes_note_1");
        additionalExaminerNoteList.add(adrDetailsAdditionalExaminerNote);
        adrDataToPatch.setTechRecordAdrDetailsAdditionalExaminerNotes(additionalExaminerNoteList);
        adrDataToPatch.setTechRecordAdrDetailsAdrCertificateNotes("adrCertificateNotes_1");
        postPatchGetAdrRecord();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsAdditionalExaminerNotes(), adrPatchedData.getTechRecordAdrDetailsAdditionalExaminerNotes());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsAdrCertificateNotes(), adrPatchedData.getTechRecordAdrDetailsAdrCertificateNotes());
    }

    @Test
    public void adrTwoAdditionalExaminerNotes_noAdrCertificateNotes() {
        List<TechRecordAdrDetailsAdditionalExaminerNote> additionalExaminerNoteList = new ArrayList<>();
        TechRecordAdrDetailsAdditionalExaminerNote adrDetailsAdditionalExaminerNote1 = new TechRecordAdrDetailsAdditionalExaminerNote("2023-01-01", "additionalExaminerNotes_lastUpdatedBy_1", "additionalExaminerNotes_note_1");
        TechRecordAdrDetailsAdditionalExaminerNote adrDetailsAdditionalExaminerNote2 = new TechRecordAdrDetailsAdditionalExaminerNote("2023-02-02", "additionalExaminerNotes_lastUpdatedBy_2", "additionalExaminerNotes_note_2");
        additionalExaminerNoteList.add(adrDetailsAdditionalExaminerNote1);
        additionalExaminerNoteList.add(adrDetailsAdditionalExaminerNote2);
        adrDataToPatch.setTechRecordAdrDetailsAdditionalExaminerNotes(additionalExaminerNoteList);
        adrDataToPatch.setTechRecordAdrDetailsAdrCertificateNotes(null);
        postPatchGetAdrRecord();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsAdditionalExaminerNotes(), adrPatchedData.getTechRecordAdrDetailsAdditionalExaminerNotes());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsAdrCertificateNotes(), adrPatchedData.getTechRecordAdrDetailsAdrCertificateNotes());
    }

    @Test
    public void adrOnePassCertificateDetails_certificateTypePass() {
        List<TechRecordAdrPassCertificateDetail> passCertificateDetailList = new ArrayList<>();
        TechRecordAdrPassCertificateDetail passCertificateDetail = new TechRecordAdrPassCertificateDetail("CREATED-BY-NAME-01", "PASS", "2023-04-01T01:49:00.055Z", "CERTIFICATE-ID-1");
        passCertificateDetailList.add(passCertificateDetail);
        adrDataToPatch.setTechRecordAdrPassCertificateDetails(passCertificateDetailList);
        postPatchGetAdrRecord();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrPassCertificateDetails(), adrPatchedData.getTechRecordAdrPassCertificateDetails());
    }

    @Test
    public void adrTwoPassCertificateDetails_certificateTypePassReplacement() {
        List<TechRecordAdrPassCertificateDetail> passCertificateDetailList = new ArrayList<>();
        TechRecordAdrPassCertificateDetail passCertificateDetail1 = new TechRecordAdrPassCertificateDetail("CREATED-BY-NAME-01", "PASS", "2023-04-01T01:49:00.055Z", "CERTIFICATE-ID-1");
        TechRecordAdrPassCertificateDetail passCertificateDetail2 = new TechRecordAdrPassCertificateDetail("CREATED-BY-NAME-02", "REPLACEMENT", "2023-05-02T02:59:00.066Z", "CERTIFICATE-ID-2");
        passCertificateDetailList.add(passCertificateDetail1);
        passCertificateDetailList.add(passCertificateDetail2);
        adrDataToPatch.setTechRecordAdrPassCertificateDetails(passCertificateDetailList);
        postPatchGetAdrRecord();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrPassCertificateDetails(), adrPatchedData.getTechRecordAdrPassCertificateDetails());
    }

    // print contents of to be remediation file, could write to file ready to be
    // uploaded to S3
    @After
    public void teardown() {
        String output = String.join(System.lineSeparator(), remediationFileContents);
        System.out.println(output);
    }
}
