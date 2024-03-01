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
import vott.models.dto.seeddata.TechRecordTrlCompleteGenerator;
import vott.models.dto.techrecordsv3.*;
import vott.updatestore.SharedUtilities;


public class SeedTechRecordDataTest {

    private final List<String> remediationFileContents = new ArrayList<>();
    private final TechRecordHgvCompleteGenerator hgvTechRecordGen = new TechRecordHgvCompleteGenerator(
                    new TechRecordHgvComplete());

    private final TechRecordTrlCompleteGenerator trlTechRecordGen = new TechRecordTrlCompleteGenerator(new TechRecordTrlComplete());
    private final AdrRemediationClassGenerator adrRemediationClassGen = new AdrRemediationClassGenerator(new AdrRemediationClass());
    private final TokenService v1ImplicitTokens = new TokenService(OAuthVersion.V1, GrantType.IMPLICIT);
    private final SharedUtilities sharedUtils = new SharedUtilities();
    private String payloadPath;
    private TechRecordHgvComplete techRecordHgv;
    private TechRecordTrlComplete techRecordTrl;
    private AdrRemediationClass adrDataToPatch;
    private TechRecordHgvComplete adrPatchedDataHgv;
    private TechRecordTrlComplete adrPatchedDataTrl;

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
        this.techRecordHgv = createHgvTechRecord(payloadPath + "TechRecordsV3/HGV_Tech_record_No_ADR.json");
        this.adrDataToPatch = adrRemediationClassGen.createTechRecordFromJsonFile(payloadPath + "TechRecordsV3/ADR_fields_only.json");
    }

    public void postPatchGetAdrRecordHgv(){
        //Part 2, use when you need to alter the tech record and/or adr details
        Map<String,String> outcomeUpdate = TechnicalRecordsV3.updateTechnicalRecord(adrDataToPatch, v1ImplicitTokens.getBearerToken(), techRecordHgv.getSystemNumber(), techRecordHgv.getCreatedTimestamp());
        sharedUtils.checkTechRecordPostOutcome(outcomeUpdate);
        TechRecordHgvComplete techRecordPostOutcome = hgvTechRecordGen
                .createTechRecordFromJsonString(outcomeUpdate.get("responseBody"));
        String outcomeSystemNumber = techRecordPostOutcome.getSystemNumber();
        String outcomeCreatedTimestamp = techRecordPostOutcome.getCreatedTimestamp();

        Map<String,String> outcomeGet = TechnicalRecordsV3.getTechnicalRecord(v1ImplicitTokens.getBearerToken(), outcomeSystemNumber, outcomeCreatedTimestamp);
        sharedUtils.checkTechRecordPostOutcome(outcomeGet);
        String outcomeResponseBody = outcomeGet.get("responseBody");
        this.adrPatchedDataHgv = hgvTechRecordGen.createTechRecordFromJsonString(outcomeResponseBody);
    }

    public void postPatchGetAdrRecordTrl(){
        //Part 2, use when you need to alter the tech record and/or adr details
        Map<String,String> outcomeUpdate = TechnicalRecordsV3.updateTechnicalRecord(adrDataToPatch, v1ImplicitTokens.getBearerToken(), techRecordTrl.getSystemNumber(), techRecordTrl.getCreatedTimestamp());
        sharedUtils.checkTechRecordPostOutcome(outcomeUpdate);
        TechRecordTrlComplete techRecordPostOutcome = trlTechRecordGen
                .createTechRecordFromJsonString(outcomeUpdate.get("responseBody"));
        String outcomeSystemNumber = techRecordPostOutcome.getSystemNumber();
        String outcomeCreatedTimestamp = techRecordPostOutcome.getCreatedTimestamp();

        Map<String,String> outcomeGet = TechnicalRecordsV3.getTechnicalRecord(v1ImplicitTokens.getBearerToken(), outcomeSystemNumber, outcomeCreatedTimestamp);
        sharedUtils.checkTechRecordPostOutcome(outcomeGet);
        String outcomeResponseBody = outcomeGet.get("responseBody");
        this.adrPatchedDataTrl = trlTechRecordGen.createTechRecordFromJsonString(outcomeResponseBody);
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

    private TechRecordTrlComplete createTrlTechRecord(String filePath) {

        TechRecordTrlComplete techRecord = trlTechRecordGen.createTechRecordFromJsonFile(filePath);
        trlTechRecordGen.randomizeTrlUniqueValues(techRecord);
        techRecord.setTechRecordVehicleType("trl");
        techRecord.setTechRecordVehicleClassDescription("trailer");
        Map<String, String> response = TechnicalRecordsV3.postTechnicalRecordV3ObjectResponse(techRecord,
                v1ImplicitTokens.getBearerToken());
        sharedUtils.checkTechRecordPostOutcome(response);
        String techRecordResponseBody = response.get(TechnicalRecordsV3.RESPONSE_BODY_KEY);
        TechRecordTrlComplete techRecordReturned = trlTechRecordGen
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
        postPatchGetAdrRecordHgv();

        Assert.assertEquals(techRecordHgv.getSystemNumber(), adrPatchedDataHgv.getSystemNumber());
        //TODO Should we have to convert this to uppercase?
        Assert.assertEquals(techRecordHgv.getVin(), adrPatchedDataHgv.getVin());
        Assert.assertEquals(techRecordHgv.getPrimaryVrm(), adrPatchedDataHgv.getPrimaryVrm());
    }

    @Test
    public void adrApplicantDetailsChecks() {
        //Applicant details completed
        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsCity("applicantDetailsCITY_1");
        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsName("applicantDetails_Name_1");
        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsPostcode("POST-CODE1");
        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsStreet("applicantDetailsSTREET");
        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsTown("applicantDetailsTOWN_1");

        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Rigid skeletal");
        postPatchGetAdrRecordHgv();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsApplicantDetailsCity(), adrPatchedDataHgv.getTechRecordAdrDetailsApplicantDetailsCity());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsApplicantDetailsName(), adrPatchedDataHgv.getTechRecordAdrDetailsApplicantDetailsName());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsApplicantDetailsPostcode(), adrPatchedDataHgv.getTechRecordAdrDetailsApplicantDetailsPostcode());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsApplicantDetailsStreet(), adrPatchedDataHgv.getTechRecordAdrDetailsApplicantDetailsStreet());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsApplicantDetailsTown(), adrPatchedDataHgv.getTechRecordAdrDetailsApplicantDetailsTown());

        Assert.assertEquals("Rigid skeletal", adrPatchedDataHgv.getTechRecordAdrDetailsVehicleDetailsType());
    }

    @Test
    public void adrNoApplicantDetails() {
        //All ApplicantDetails fields are null
        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsCity(null);
        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsName(null);
        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsPostcode(null);
        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsStreet(null);
        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsTown(null);

        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Full drawbar box body");

        postPatchGetAdrRecordHgv();

        Assert.assertNull(adrPatchedDataHgv.getTechRecordAdrDetailsApplicantDetailsCity());
        Assert.assertNull(adrPatchedDataHgv.getTechRecordAdrDetailsApplicantDetailsName());
        Assert.assertNull(adrPatchedDataHgv.getTechRecordAdrDetailsApplicantDetailsPostcode());
        Assert.assertNull(adrPatchedDataHgv.getTechRecordAdrDetailsApplicantDetailsStreet());

        Assert.assertEquals("Full drawbar box body", adrPatchedDataHgv.getTechRecordAdrDetailsVehicleDetailsType());
    }

    @Test
    public void adrOnePermittedDangerousGoods() {
        //One permitted dangerous goods (not explosives type 2 or 3)
        List<String> permittedDangerousGoodsList = List.of("FP <61 (FL)");
        adrDataToPatch.setTechRecordAdrDetailsPermittedDangerousGoods(permittedDangerousGoodsList);

        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Full drawbar sheeted load");

        postPatchGetAdrRecordHgv();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsPermittedDangerousGoods(), adrPatchedDataHgv.getTechRecordAdrDetailsPermittedDangerousGoods());

        Assert.assertEquals("Full drawbar sheeted load", adrPatchedDataHgv.getTechRecordAdrDetailsVehicleDetailsType());
    }

    @Test
    public void adrTwoPermittedDangerousGoods() {
        //Two permitted dangerous goods (not explosives type 2 or 3)
        List<String> permittedDangerousGoodsList = Arrays.asList("FP <61 (FL)", "AT");
        adrDataToPatch.setTechRecordAdrDetailsPermittedDangerousGoods(permittedDangerousGoodsList);

        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Full drawbar skeletal");

        postPatchGetAdrRecordHgv();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsPermittedDangerousGoods(), adrPatchedDataHgv.getTechRecordAdrDetailsPermittedDangerousGoods());

        Assert.assertEquals("Full drawbar skeletal", adrPatchedDataHgv.getTechRecordAdrDetailsVehicleDetailsType());
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

        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Centre axle box body");

        postPatchGetAdrRecordHgv();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsPermittedDangerousGoods(), adrPatchedDataHgv.getTechRecordAdrDetailsPermittedDangerousGoods());

        Assert.assertEquals("Centre axle box body", adrPatchedDataHgv.getTechRecordAdrDetailsVehicleDetailsType());
    }

    @Test
    public void adrExplosivesType2PermittedDangerousGoods() {
        //Explosives Type 2 means Compatibility Group J is populated
        List<String> permittedDangerousGoodsList = List.of("Explosives (type 2)");
        adrDataToPatch.setTechRecordAdrDetailsPermittedDangerousGoods(permittedDangerousGoodsList);
        adrDataToPatch.setTechRecordAdrDetailsCompatibilityGroupJ("I");

        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Centre axle sheeted load");

        postPatchGetAdrRecordHgv();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsPermittedDangerousGoods(), adrPatchedDataHgv.getTechRecordAdrDetailsPermittedDangerousGoods());
        Assert.assertNotNull(adrDataToPatch.getTechRecordAdrDetailsCompatibilityGroupJ());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsCompatibilityGroupJ(), adrPatchedDataHgv.getTechRecordAdrDetailsCompatibilityGroupJ());

        Assert.assertEquals("Centre axle sheeted load", adrPatchedDataHgv.getTechRecordAdrDetailsVehicleDetailsType());
    }

    @Test
    public void adrExplosivesType3PermittedDangerousGoods() {
        //Explosives Type 3 means Compatibility Group J is populated
        List<String> permittedDangerousGoodsList = List.of("Explosives (type 3)");
        adrDataToPatch.setTechRecordAdrDetailsPermittedDangerousGoods(permittedDangerousGoodsList);
        adrDataToPatch.setTechRecordAdrDetailsCompatibilityGroupJ("E");

        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Centre axle skeletal");

        postPatchGetAdrRecordHgv();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsPermittedDangerousGoods(), adrPatchedDataHgv.getTechRecordAdrDetailsPermittedDangerousGoods());
        Assert.assertNotNull(adrDataToPatch.getTechRecordAdrDetailsCompatibilityGroupJ());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsCompatibilityGroupJ(), adrPatchedDataHgv.getTechRecordAdrDetailsCompatibilityGroupJ());

        Assert.assertEquals("Centre axle skeletal", adrPatchedDataHgv.getTechRecordAdrDetailsVehicleDetailsType());
    }

    @Test
    public void adrOneAdditionalNotesNumber() {
        //One guidance notes
        List<String> additionalNotesNumberList = List.of("1");
        adrDataToPatch.setTechRecordAdrDetailsAdditionalNotesNumber(additionalNotesNumberList);

        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Semi trailer box body");

        postPatchGetAdrRecordHgv();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsAdditionalNotesNumber(), adrPatchedDataHgv.getTechRecordAdrDetailsAdditionalNotesNumber());

        Assert.assertEquals("Semi trailer box body", adrPatchedDataHgv.getTechRecordAdrDetailsVehicleDetailsType());
    }

    @Test
    public void adrTwoAdditionalNotesNumber() {
        //Two guidance notes
        List<String> additionalNotesNumberList = List.of("1", "1A");
        adrDataToPatch.setTechRecordAdrDetailsAdditionalNotesNumber(additionalNotesNumberList);

        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Semi trailer sheeted load");

        postPatchGetAdrRecordHgv();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsAdditionalNotesNumber(), adrPatchedDataHgv.getTechRecordAdrDetailsAdditionalNotesNumber());

        Assert.assertEquals("Semi trailer sheeted load", adrPatchedDataHgv.getTechRecordAdrDetailsVehicleDetailsType());
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

        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Semi trailer skeletal");

        postPatchGetAdrRecordHgv();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsAdditionalNotesNumber(), adrPatchedDataHgv.getTechRecordAdrDetailsAdditionalNotesNumber());

        Assert.assertEquals("Semi trailer skeletal", adrPatchedDataHgv.getTechRecordAdrDetailsVehicleDetailsType());
    }

    @Test
    public void adrTypeApprovalNumber() {
        //ADR Type Approval Number completed
        adrDataToPatch.setTechRecordAdrDetailsAdrTypeApprovalNo("adrTypeApprovalNo_1");

        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Rigid box body");

        postPatchGetAdrRecordHgv();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsAdrTypeApprovalNo(), adrPatchedDataHgv.getTechRecordAdrDetailsAdrTypeApprovalNo());

        Assert.assertEquals("Rigid box body", adrPatchedDataHgv.getTechRecordAdrDetailsVehicleDetailsType());
    }

    @Test
    public void adrNoTypeApprovalNumber() {
        //No ADR Type Approval Number
        adrDataToPatch.setTechRecordAdrDetailsAdrTypeApprovalNo(null);

        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Rigid sheeted load");

        postPatchGetAdrRecordHgv();

        Assert.assertNull(adrPatchedDataHgv.getTechRecordAdrDetailsAdrTypeApprovalNo());

        Assert.assertEquals("Rigid sheeted load", adrPatchedDataHgv.getTechRecordAdrDetailsVehicleDetailsType());

    }

    @Test
    public void adrSubstancesPermittedTankCode() {
        //Substances permitted is "Substances permitted under the tank code and any special provisions specified in 9 may be carried"
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted("Substances permitted under the tank code and any special provisions specified in 9 may be carried");

        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Rigid tank");

        postPatchGetAdrRecordHgv();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted(), adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted());

        Assert.assertEquals("Rigid tank", adrPatchedDataHgv.getTechRecordAdrDetailsVehicleDetailsType());
    }

    @Test
    public void adrSubstancesPermittedUnNumber() {
        //Substances permitted is "Substances (Class UN number and if necessary packing group and proper shipping name) may be carried"
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted("Substances (Class UN number and if necessary packing group and proper shipping name) may be carried");

        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Full drawbar tank");

        postPatchGetAdrRecordHgv();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted(), adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted());

        Assert.assertEquals("Full drawbar tank", adrPatchedDataHgv.getTechRecordAdrDetailsVehicleDetailsType());
    }

    @Test
    public void adrStatementSelectedAndProvided() {
        //TankStatement.Statement is selected and provided
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementSelect("Statement");
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementStatement("statement_1");

        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Centre axle tank");

        postPatchGetAdrRecordHgv();

        Assert.assertEquals("Statement", adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsTankStatementSelect());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementStatement(), adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsTankStatementStatement());

        Assert.assertEquals("Centre axle tank", adrPatchedDataHgv.getTechRecordAdrDetailsVehicleDetailsType());
    }

    @Test
    public void adrProductListRefNoCompleteNoListUnNo() {
        //productListRefNo is completed, and there is no productListUnNo
        this.techRecordTrl = createTrlTechRecord(payloadPath + "TechRecordsV3/HGV_Tech_record_No_ADR.json");
        //Makes a trl record, TODO currently uses HGV attributes, change to TRL attributes

        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo(null);
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo("123456");

        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Semi trailer tank");

        postPatchGetAdrRecordTrl();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo(), adrPatchedDataTrl.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo(), adrPatchedDataTrl.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo());

        Assert.assertEquals("trl", adrPatchedDataTrl.getTechRecordVehicleType());
        Assert.assertEquals("trailer", adrPatchedDataTrl.getTechRecordVehicleClassDescription());
        Assert.assertEquals("Semi trailer tank", adrPatchedDataTrl.getTechRecordAdrDetailsVehicleDetailsType());
    }

    @Test
    public void adrOneProductListUnNo_noProductListRefNo_productListCompleted_specialProvisionsCompleted() {
        //One productListUnNo, no productListRefNo, additionalDetailsProductList completed, specialProvisionsCompleted
        List<String> productListUnNoList = List.of("123123");
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo(null);
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo(productListUnNoList);
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementProductList("productList_1");
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsSpecialProvisions("specialProvisions_1");
        postPatchGetAdrRecordHgv();

        Assert.assertNull(adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo());
        Assert.assertNotNull(adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsTankStatementProductList());
        Assert.assertNotNull(adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsSpecialProvisions());

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo(), adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo(), adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementProductList(), adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsTankStatementProductList());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsSpecialProvisions(), adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsSpecialProvisions());
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
        postPatchGetAdrRecordHgv();

        Assert.assertNull(adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo());
        Assert.assertNull(adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsSpecialProvisions());
        Assert.assertNull(adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsTankStatementProductList());

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo(), adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo(), adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementProductList(), adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsTankStatementProductList());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsSpecialProvisions(), adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsSpecialProvisions());
    }

    @Test
    public void adrTc2Details_noTc3Details_noMemosApply() {
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo("12345");
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate("2024-06-01");
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type("initial");
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTc3Details(null);
        adrDataToPatch.setTechRecordAdrDetailsMemosApply(null);
        postPatchGetAdrRecordHgv();

        Assert.assertNull(adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsTc3Details());
        Assert.assertNull(adrPatchedDataHgv.getTechRecordAdrDetailsMemosApply());

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type(), adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo(), adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate(), adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTc3Details(), adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsTc3Details());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsMemosApply(), adrPatchedDataHgv.getTechRecordAdrDetailsMemosApply());
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
        postPatchGetAdrRecordHgv();

        Assert.assertNotNull(adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsTc3Details());
        Assert.assertNotNull(adrPatchedDataHgv.getTechRecordAdrDetailsMemosApply());

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type(), adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo(), adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate(), adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTc3Details(), adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsTc3Details());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsMemosApply(), adrPatchedDataHgv.getTechRecordAdrDetailsMemosApply());
    }

    @Test
    public void adrTc2Details_twoTc3Details_typePeriodicExceptional() {
        TechRecordAdrDetailsTankTankDetailsTc3Detail tc3DetailsPeriodic = new TechRecordAdrDetailsTankTankDetailsTc3Detail("periodic","12345","2024-01-01");
        TechRecordAdrDetailsTankTankDetailsTc3Detail tc3DetailsExceptional = new TechRecordAdrDetailsTankTankDetailsTc3Detail("exceptional","67890","2024-02-02");
        List<TechRecordAdrDetailsTankTankDetailsTc3Detail> tc3DetailList = new ArrayList<>();
        tc3DetailList.add(tc3DetailsPeriodic);
        tc3DetailList.add(tc3DetailsExceptional);
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTc3Details(tc3DetailList);
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo("12345");
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate("2024-06-01");
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type("initial");
        postPatchGetAdrRecordHgv();

        Assert.assertNotNull(adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsTc3Details());
        Assert.assertNotNull(adrPatchedDataHgv.getTechRecordAdrDetailsMemosApply());

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type(), adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo(), adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate(), adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTc3Details(), adrPatchedDataHgv.getTechRecordAdrDetailsTankTankDetailsTc3Details());
    }

    @Test
    public void adrOneMemosApply() {
        List<String> memosApplyList = List.of("07/09 3mth leak ext");
        adrDataToPatch.setTechRecordAdrDetailsMemosApply(memosApplyList);
        postPatchGetAdrRecordHgv();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsMemosApply(), adrPatchedDataHgv.getTechRecordAdrDetailsMemosApply());
    }

    @Test
    public void adrM145StatementTrue() {
        adrDataToPatch.setTechRecordAdrDetailsM145Statement(true);
        postPatchGetAdrRecordHgv();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsM145Statement(), adrPatchedDataHgv.getTechRecordAdrDetailsM145Statement());
    }

    @Test
    public void adrM145StatementFalse() {
        adrDataToPatch.setTechRecordAdrDetailsM145Statement(false);
        postPatchGetAdrRecordHgv();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsM145Statement(), adrPatchedDataHgv.getTechRecordAdrDetailsM145Statement());
    }

    @Test
    public void adrListStatementApplicableTrue_batteryListNumberComplete() {
        //listStatementApplicable true, batteryListNumber completed
        adrDataToPatch.setTechRecordAdrDetailsListStatementApplicable(true);
        adrDataToPatch.setTechRecordAdrDetailsBatteryListNumber("BATTERY1");
        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Rigid battery");
        postPatchGetAdrRecordHgv();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsListStatementApplicable(), adrPatchedDataHgv.getTechRecordAdrDetailsListStatementApplicable());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsBatteryListNumber(), adrPatchedDataHgv.getTechRecordAdrDetailsBatteryListNumber());
        Assert.assertEquals("Rigid battery", adrPatchedDataHgv.getTechRecordAdrDetailsVehicleDetailsType());
    }

    @Test
    public void adrListStatementApplicableFalse() {
        //listStatementApplicable false
        adrDataToPatch.setTechRecordAdrDetailsListStatementApplicable(false);
        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Full drawbar battery");
        postPatchGetAdrRecordHgv();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsListStatementApplicable(), adrPatchedDataHgv.getTechRecordAdrDetailsListStatementApplicable());
        Assert.assertEquals("Full drawbar battery", adrPatchedDataHgv.getTechRecordAdrDetailsVehicleDetailsType());
    }

    @Test
    public void adrBrakeDeclarationsSeenTrue_brakeEnduranceTrue_declarationsSeenTrue_newCertificateRequestedFalse() {
        //brakeDeclarationsSeen true, brakeEndurance True, declarationsSeen True, newCertificateRequested False
        adrDataToPatch.setTechRecordAdrDetailsBrakeDeclarationsSeen(true);
        adrDataToPatch.setTechRecordAdrDetailsBrakeEndurance(true);
        adrDataToPatch.setTechRecordAdrDetailsDeclarationsSeen(true);
        adrDataToPatch.setTechRecordAdrDetailsNewCertificateRequested(false);
        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Centre axle battery");
        postPatchGetAdrRecordHgv();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsBrakeDeclarationsSeen(), adrPatchedDataHgv.getTechRecordAdrDetailsBrakeDeclarationsSeen());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsBrakeEndurance(), adrPatchedDataHgv.getTechRecordAdrDetailsBrakeEndurance());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsDeclarationsSeen(), adrPatchedDataHgv.getTechRecordAdrDetailsDeclarationsSeen());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsNewCertificateRequested(), adrPatchedDataHgv.getTechRecordAdrDetailsNewCertificateRequested());
        Assert.assertEquals("Centre axle battery", adrPatchedDataHgv.getTechRecordAdrDetailsVehicleDetailsType());
    }

    @Test
    public void adrBrakeDeclarationsSeenTrue_brakeEnduranceFalse_declarationsSeenFalse_newCertificateRequestedTrue() {
        //brakeDeclarationsSeen true, brakeEndurance False, declarationsSeen False, newCertificateRequested True
        adrDataToPatch.setTechRecordAdrDetailsBrakeDeclarationsSeen(true);
        adrDataToPatch.setTechRecordAdrDetailsBrakeEndurance(false);
        adrDataToPatch.setTechRecordAdrDetailsDeclarationsSeen(false);
        adrDataToPatch.setTechRecordAdrDetailsNewCertificateRequested(true);
        techRecordHgv.setTechRecordVehicleType("trl");
        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Semi trailer battery");
        postPatchGetAdrRecordHgv();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsBrakeDeclarationsSeen(), adrPatchedDataHgv.getTechRecordAdrDetailsBrakeDeclarationsSeen());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsBrakeEndurance(), adrPatchedDataHgv.getTechRecordAdrDetailsBrakeEndurance());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsDeclarationsSeen(), adrPatchedDataHgv.getTechRecordAdrDetailsDeclarationsSeen());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsNewCertificateRequested(), adrPatchedDataHgv.getTechRecordAdrDetailsNewCertificateRequested());
        Assert.assertEquals("trl", techRecordHgv.getTechRecordVehicleType());
        Assert.assertEquals("Semi trailer battery", adrPatchedDataHgv.getTechRecordAdrDetailsVehicleDetailsType());
    }

    @Test
    public void adrNoAdditionalExaminerNotes() {
        adrDataToPatch.setTechRecordAdrDetailsAdditionalExaminerNotes(null);
        techRecordHgv.setTechRecordVehicleType("hgv");
        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Artic tractor");
        postPatchGetAdrRecordHgv();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsAdditionalExaminerNotes(), adrPatchedDataHgv.getTechRecordAdrDetailsAdditionalExaminerNotes());
        Assert.assertEquals("hgv", techRecordHgv.getTechRecordVehicleType());
        Assert.assertEquals("Artic tractor", adrPatchedDataHgv.getTechRecordAdrDetailsVehicleDetailsType());
    }

    @Test
    public void adrOneAdditionalExaminerNotes_adrCertificateNotesCompleted() {
        List<TechRecordAdrDetailsAdditionalExaminerNote> additionalExaminerNoteList = new ArrayList<>();
        TechRecordAdrDetailsAdditionalExaminerNote adrDetailsAdditionalExaminerNote = new TechRecordAdrDetailsAdditionalExaminerNote("2023-05-30", "additionalExaminerNotes_lastUpdatedBy_1", "additionalExaminerNotes_note_1");
        additionalExaminerNoteList.add(adrDetailsAdditionalExaminerNote);
        adrDataToPatch.setTechRecordAdrDetailsAdditionalExaminerNotes(additionalExaminerNoteList);
        adrDataToPatch.setTechRecordAdrDetailsAdrCertificateNotes("adrCertificateNotes_1");
        postPatchGetAdrRecordHgv();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsAdditionalExaminerNotes(), adrPatchedDataHgv.getTechRecordAdrDetailsAdditionalExaminerNotes());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsAdrCertificateNotes(), adrPatchedDataHgv.getTechRecordAdrDetailsAdrCertificateNotes());
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
        postPatchGetAdrRecordHgv();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsAdditionalExaminerNotes(), adrPatchedDataHgv.getTechRecordAdrDetailsAdditionalExaminerNotes());
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsAdrCertificateNotes(), adrPatchedDataHgv.getTechRecordAdrDetailsAdrCertificateNotes());
    }

    @Test
    public void adrOnePassCertificateDetails_certificateTypePass() {
        List<TechRecordAdrPassCertificateDetail> passCertificateDetailList = new ArrayList<>();
        TechRecordAdrPassCertificateDetail passCertificateDetail = new TechRecordAdrPassCertificateDetail("CREATED-BY-NAME-01", "PASS", "2023-04-01T01:49:00.055Z", "CERTIFICATE-ID-1");
        passCertificateDetailList.add(passCertificateDetail);
        adrDataToPatch.setTechRecordAdrPassCertificateDetails(passCertificateDetailList);
        postPatchGetAdrRecordHgv();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrPassCertificateDetails(), adrPatchedDataHgv.getTechRecordAdrPassCertificateDetails());
    }

    @Test
    public void adrTwoPassCertificateDetails_certificateTypePassReplacement() {
        List<TechRecordAdrPassCertificateDetail> passCertificateDetailList = new ArrayList<>();
        TechRecordAdrPassCertificateDetail passCertificateDetail1 = new TechRecordAdrPassCertificateDetail("CREATED-BY-NAME-01", "PASS", "2023-04-01T01:49:00.055Z", "CERTIFICATE-ID-1");
        TechRecordAdrPassCertificateDetail passCertificateDetail2 = new TechRecordAdrPassCertificateDetail("CREATED-BY-NAME-02", "REPLACEMENT", "2023-05-02T02:59:00.066Z", "CERTIFICATE-ID-2");
        passCertificateDetailList.add(passCertificateDetail1);
        passCertificateDetailList.add(passCertificateDetail2);
        adrDataToPatch.setTechRecordAdrPassCertificateDetails(passCertificateDetailList);
        postPatchGetAdrRecordHgv();

        Assert.assertEquals(adrDataToPatch.getTechRecordAdrPassCertificateDetails(), adrPatchedDataHgv.getTechRecordAdrPassCertificateDetails());
    }

    // print contents of to be remediation file, could write to file ready to be
    // uploaded to S3
    @After
    public void teardown() {
        String output = String.join(System.lineSeparator(), remediationFileContents);
        System.out.println(output);
    }
}
