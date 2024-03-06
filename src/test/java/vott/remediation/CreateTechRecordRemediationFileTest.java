package vott.remediation;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import vott.auth.GrantType;
import vott.auth.OAuthVersion;
import vott.auth.TokenService;
import vott.models.dto.seeddata.AdrRemediationClassGenerator;
import vott.models.dto.seeddata.AdrSeedData;
import vott.models.dto.seeddata.TechRecordHgvCompleteGenerator;
import vott.api.TechnicalRecordsV3;
import vott.models.dto.techrecordsv3.*;
import vott.updatestore.SharedUtilities;

public class CreateTechRecordRemediationFileTest {

    private static List<String> remediationFileContents = new ArrayList<>();
    private final TechRecordHgvCompleteGenerator hgvTechRecordGen = new TechRecordHgvCompleteGenerator(
            new TechRecordHgvComplete());
    private final AdrRemediationClassGenerator adrDataGen = new AdrRemediationClassGenerator(new AdrRemediationClass());
    private final TokenService v1ImplicitTokens = new TokenService(OAuthVersion.V1, GrantType.IMPLICIT);
    private final SharedUtilities sharedUtils = new SharedUtilities();
    private static final String PAYLOAD_PATH = "src/main/resources/payloads/";
    private AdrSeedData adrSeedData;
    private AdrRemediationClass adrDataToPatch;
    private static final String HGV_TECH_RECORD_PATH = PAYLOAD_PATH + "TechRecordsV3/HGV_Tech_record_No_ADR.json";

    @Before
    public void setup() {
        // Runs before each test
        // Create and post HGV tech record, and create template ADR data
        TechRecordHgvComplete techRecord = createHgvTechRecord(HGV_TECH_RECORD_PATH);
        String systemNumber = techRecord.getSystemNumber();
        String createdTimestamp = techRecord.getCreatedTimestamp();
        adrSeedData = new AdrSeedData(systemNumber, createdTimestamp);
        adrDataToPatch = adrSeedData.minimumAdrDataNotTank();
    }

    @Test
    public void allApplicantDetailsCompleted() {
        // All applicant details completed
        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsTown("new town");
        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsPostcode("POST-CODE1");
        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsName("new name");
        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsStreet("new street");
        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsCity("new city");
        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Rigid skeletal");
        addAdrDataToList();
    }

    @Test
    public void allApplicantDetailsNull() {
        // All applicant details null
        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsTown(null);
        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsPostcode(null);
        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsName(null);
        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsStreet(null);
        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsCity(null);
        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Full drawbar box body");
        addAdrDataToList();
    }

    @Test
    public void onePermittedDangerousGoods() {
        //One permitted dangerous goods (not explosives type 2 or 3)
        List<String> permittedDangerousGoodsList = List.of("FP <61 (FL)");
        adrDataToPatch.setTechRecordAdrDetailsPermittedDangerousGoods(permittedDangerousGoodsList);
        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Full drawbar sheeted load");
        addAdrDataToList();
    }

    @Test
    public void twoPermittedDangerousGoods() {
        //Two permitted dangerous goods (not explosives type 2 or 3)
        List<String> permittedDangerousGoodsList = Arrays.asList("FP <61 (FL)", "AT");
        adrDataToPatch.setTechRecordAdrDetailsPermittedDangerousGoods(permittedDangerousGoodsList);
        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Full drawbar skeletal");
        addAdrDataToList();
    }

    @Test
    public void allPermittedDangerousGoods() {
        //Two permitted dangerous goods (not explosives type 2 or 3)
        List<String> permittedDangerousGoodsList = List.of(
                "FP <61 (FL)",
                "AT",
                "Class 5.1 Hydrogen Peroxide (OX)",
                "MEMU",
                "Carbon Disulphide",
                "Hydrogen",
                "Explosives (type 2)",
                "Explosives (type 3)");
        adrDataToPatch.setTechRecordAdrDetailsPermittedDangerousGoods(permittedDangerousGoodsList);
        adrDataToPatch.setTechRecordAdrDetailsCompatibilityGroupJ("E");
        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Centre axle box body");
        addAdrDataToList();
    }

    @Test
    public void explosivesType2PermittedDangerousGoods_includeCompatibilityGroupJ() {
        //Explosives Type 2 means Compatibility Group J is populated
        List<String> permittedDangerousGoodsList = List.of("Explosives (type 2)");
        adrDataToPatch.setTechRecordAdrDetailsPermittedDangerousGoods(permittedDangerousGoodsList);
        adrDataToPatch.setTechRecordAdrDetailsCompatibilityGroupJ("I");
        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Centre axle sheeted load");
        addAdrDataToList();
    }

    @Test
    public void explosivesType3PermittedDangerousGoods_excludeCompatibilityGroupJ() {
        //Explosives Type 3 means Compatibility Group J is populated
        List<String> permittedDangerousGoodsList = List.of("Explosives (type 3)");
        adrDataToPatch.setTechRecordAdrDetailsPermittedDangerousGoods(permittedDangerousGoodsList);
        adrDataToPatch.setTechRecordAdrDetailsCompatibilityGroupJ("E");
        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Centre axle skeletal");
        addAdrDataToList();
    }

    @Test
    public void oneAdditionalNotesNumber() {
        //One guidance notes
        List<String> additionalNotesNumberList = List.of("1");
        adrDataToPatch.setTechRecordAdrDetailsAdditionalNotesNumber(additionalNotesNumberList);
        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Semi trailer box body");
        addAdrDataToList();
    }

    @Test
    public void twoAdditionalNotesNumber() {
        //Two guidance notes
        List<String> additionalNotesNumberList = List.of("1", "1A");
        adrDataToPatch.setTechRecordAdrDetailsAdditionalNotesNumber(additionalNotesNumberList);
        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Semi trailer sheeted load");
        addAdrDataToList();
    }

    @Test
    public void allAdditionalNotesNumber() {
        //All guidance notes
        List<String> additionalNotesNumberList = List.of(
                "1",
                "1A",
                "2",
                "3",
                "V1B",
                "T1B");
        adrDataToPatch.setTechRecordAdrDetailsAdditionalNotesNumber(additionalNotesNumberList);

        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Semi trailer skeletal");

        addAdrDataToList();
    }

    @Test
    public void approvalNumberCompleted() {
        //approvalNumber completed
        adrDataToPatch.setTechRecordAdrDetailsAdrTypeApprovalNo("adrTypeApprovalNo_1");
        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Rigid box body");
        addAdrDataToList();
    }

    @Test
    public void noApprovalNumber() {
        //No ADR Type approvalNumber
        adrDataToPatch.setTechRecordAdrDetailsAdrTypeApprovalNo(null);
        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Rigid sheeted load");
        addAdrDataToList();
    }

    @Test
    public void substancesPermittedTankCode() {
        // Substances permitted is "Substances permitted under the tank code and any
        // special provisions specified in 9 may be carried"
        adrDataToPatch = adrSeedData.minimumAdrDataForTankOrBattery();
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted(
                "Substances permitted under the tank code and any special provisions specified in 9 may be carried");
        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Rigid tank");
        addAdrDataToList();
    }

    @Test
    public void substancesPermittedUnNumber() {
        // Substances permitted is "Substances (Class UN number and if necessary packing
        // group and proper shipping name) may be carried"
        adrDataToPatch = adrSeedData.minimumAdrDataForTankOrBattery();
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted(
                "Substances (Class UN number and if necessary packing group and proper shipping name) may be carried");
        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Full drawbar tank");
        addAdrDataToList();
    }

    @Test
    public void statementSelectedAndProvided() {
        // TankStatement.Statement is selected and provided
        adrDataToPatch = adrSeedData.minimumAdrDataForTankOrBattery();
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementSelect("Statement");
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementStatement("statement_1");
        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Centre axle tank");
        addAdrDataToList();
    }

    @Test
    public void productListRefNoCompleteNoListUnNo() {
        // productListRefNo is completed, and there is no productListUnNo
        adrDataToPatch = adrSeedData.minimumAdrDataForTankOrBattery();
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementSelect("Product list");
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo(null);
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo("123456");
        // TODO might need to convert this to a TRL model for below to work
        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Semi trailer tank");
        addAdrDataToList();
    }

    @Test
    public void productListUnNo_noProductListRefNo_productListCompleted_specialProvisionsCompleted() {
        // One productListUnNo, no productListRefNo, additionalDetailsProductList
        // completed, specialProvisionsCompleted
        adrDataToPatch = adrSeedData.minimumAdrDataForTankOrBattery();
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementSelect("Product list");
        List<String> productListUnNoList = List.of("123123");
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo(null);
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo(productListUnNoList);
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementProductList("productList_1");
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsSpecialProvisions("specialProvisions_1");
        addAdrDataToList();
    }

    @Test
    public void twoProductListUnNo_noProductListRefNo_noProductList_noSpecialProvisions() {
        // Two productListUnNo, no productListRefNo, no additionalDetailsProductList, no
        // specialProvisions
        adrDataToPatch = adrSeedData.minimumAdrDataForTankOrBattery();
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementSelect("Product list");
        List<String> productListUnNoList = List.of(
                "123123",
                "456456");
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo(productListUnNoList);
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementProductList(null);
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsSpecialProvisions(null);
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo(null);
        addAdrDataToList();
    }

    @Test
    public void tc2Details_noTc3Details_noMemosApply() {
        //Tc2 Details - No Tc3 Details, no memosApply fields
        adrDataToPatch = adrSeedData.minimumAdrDataForTankOrBattery();
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo("12345");
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate("2024-06-01");
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type("initial");
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTc3Details(null);
        adrDataToPatch.setTechRecordAdrDetailsMemosApply(null);
        addAdrDataToList();
    }

    @Test
    public void tc2Details_oneTc3Details_typeIntermediate() {
        //Tc2 Details - One Tc3 Details with tc3Type intermediate
        adrDataToPatch = adrSeedData.minimumAdrDataForTankOrBattery();
        TechRecordAdrDetailsTankTankDetailsTc3Detail tc3DetailsIntermediate = new TechRecordAdrDetailsTankTankDetailsTc3Detail(
                "intermediate", "13579", "2024-06-12");
        List<TechRecordAdrDetailsTankTankDetailsTc3Detail> tc3DetailList = new ArrayList<>();
        tc3DetailList.add(tc3DetailsIntermediate);
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTc3Details(tc3DetailList);
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo("12345");
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate("2024-06-01");
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type("initial");

        addAdrDataToList();
    }

    @Test
    public void tc2Details_twoTc3Details_typePeriodicExceptional() {
        //Tc2 Details - Two Tc3 Details with tc3Type periodic and exceptional
        adrDataToPatch = adrSeedData.minimumAdrDataForTankOrBattery();
        TechRecordAdrDetailsTankTankDetailsTc3Detail tc3DetailsPeriodic = new TechRecordAdrDetailsTankTankDetailsTc3Detail(
                "periodic", "12345", "2024-01-01");
        TechRecordAdrDetailsTankTankDetailsTc3Detail tc3DetailsExceptional = new TechRecordAdrDetailsTankTankDetailsTc3Detail(
                "exceptional", "67890", "2024-02-02");
        List<TechRecordAdrDetailsTankTankDetailsTc3Detail> tc3DetailList = new ArrayList<>();
        tc3DetailList.add(tc3DetailsPeriodic);
        tc3DetailList.add(tc3DetailsExceptional);
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTc3Details(tc3DetailList);
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo("12345");
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate("2024-06-01");
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type("initial");
        addAdrDataToList();
    }

    @Test
    public void oneMemosApply() {
        //One memosApply field
        adrDataToPatch = adrSeedData.minimumAdrDataForTankOrBattery();
        List<String> memosApplyList = List.of("07/09 3mth leak ext");
        adrDataToPatch.setTechRecordAdrDetailsMemosApply(memosApplyList);
        addAdrDataToList();
    }

    @Test
    public void m145StatementTrue() {
        //M145Statement True
        adrDataToPatch = adrSeedData.minimumAdrDataForTankOrBattery();
        adrDataToPatch.setTechRecordAdrDetailsM145Statement(true);
        addAdrDataToList();
    }

    @Test
    public void m145StatementFalse() {
        //M145Statement False
        adrDataToPatch = adrSeedData.minimumAdrDataForTankOrBattery();
        adrDataToPatch.setTechRecordAdrDetailsM145Statement(false);
        addAdrDataToList();
    }

    @Test
    public void listStatementApplicableTrue_batteryListNumberComplete() {
        // listStatementApplicable true, batteryListNumber completed
        adrDataToPatch = adrSeedData.minimumAdrDataForTankOrBattery();
        adrDataToPatch.setTechRecordAdrDetailsListStatementApplicable(true);
        adrDataToPatch.setTechRecordAdrDetailsBatteryListNumber("BATTERY1");
        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Rigid battery");
        addAdrDataToList();
    }

    @Test
    public void listStatementApplicableFalse() {
        // listStatementApplicable false
        adrDataToPatch = adrSeedData.minimumAdrDataForTankOrBattery();
        adrDataToPatch.setTechRecordAdrDetailsListStatementApplicable(false);
        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Full drawbar battery");
        addAdrDataToList();
    }

    @Test
    public void brakeDeclarationsSeenTrue_brakeEnduranceTrue_declarationsSeenTrue_newCertificateRequestedFalse() {
        // brakeDeclarationsSeen true, brakeEndurance True, declarationsSeen True,
        // newCertificateRequested False
        adrDataToPatch = adrSeedData.minimumAdrDataForTankOrBattery();
        adrDataToPatch.setTechRecordAdrDetailsBrakeDeclarationsSeen(true);
        adrDataToPatch.setTechRecordAdrDetailsBrakeEndurance(true);
        adrDataToPatch.setTechRecordAdrDetailsWeight(75.00);
        adrDataToPatch.setTechRecordAdrDetailsDeclarationsSeen(true);
        adrDataToPatch.setTechRecordAdrDetailsNewCertificateRequested(false);
        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Centre axle battery");
        addAdrDataToList();
    }

    //TODO - Make this a TRL record?
    //@Test
    public void brakeDeclarationsSeenTrue_brakeEnduranceFalse_declarationsSeenFalse_newCertificateRequestedTrue() {
        //brakeDeclarationsSeen true, brakeEndurance False, declarationsSeen False, newCertificateRequested True
        adrDataToPatch = adrSeedData.minimumAdrDataForTankOrBattery();
        adrDataToPatch.setTechRecordAdrDetailsBrakeDeclarationsSeen(true);
        adrDataToPatch.setTechRecordAdrDetailsBrakeEndurance(false);
        adrDataToPatch.setTechRecordAdrDetailsDeclarationsSeen(false);
        adrDataToPatch.setTechRecordAdrDetailsNewCertificateRequested(true);
        // techRecordHgv.setTechRecordVehicleType("trl");
        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Semi trailer battery");
        addAdrDataToList();
    }

    @Test
    public void noAdditionalExaminerNotes() {
        //no additionalExaminersNotes
        adrDataToPatch.setTechRecordAdrDetailsAdditionalExaminerNotes(null);
        adrDataToPatch.setTechRecordAdrDetailsVehicleDetailsType("Artic tractor");
        addAdrDataToList();
    }

    @Test
    public void oneAdditionalExaminerNotes_adrCertificateNotesCompleted() {
        //One additionalExaminersNotes, adrCertificateNotes completed
        List<TechRecordAdrDetailsAdditionalExaminerNote> additionalExaminerNoteList = new ArrayList<>();
        TechRecordAdrDetailsAdditionalExaminerNote adrDetailsAdditionalExaminerNote = new TechRecordAdrDetailsAdditionalExaminerNote(
                "2023-05-30", "additionalExaminerNotes_lastUpdatedBy_1", "additionalExaminerNotes_note_1");
        additionalExaminerNoteList.add(adrDetailsAdditionalExaminerNote);
        adrDataToPatch.setTechRecordAdrDetailsAdditionalExaminerNotes(additionalExaminerNoteList);
        adrDataToPatch.setTechRecordAdrDetailsAdrCertificateNotes("adrCertificateNotes_1");
        addAdrDataToList();
    }

    @Test
    public void twoAdditionalExaminerNotes_noAdrCertificateNotes() {
        //two additionalExaminerNotes, no adrCertificateNotes
        List<TechRecordAdrDetailsAdditionalExaminerNote> additionalExaminerNoteList = new ArrayList<>();
        TechRecordAdrDetailsAdditionalExaminerNote adrDetailsAdditionalExaminerNote1 = new TechRecordAdrDetailsAdditionalExaminerNote(
                "2023-01-01", "additionalExaminerNotes_lastUpdatedBy_1", "additionalExaminerNotes_note_1");
        TechRecordAdrDetailsAdditionalExaminerNote adrDetailsAdditionalExaminerNote2 = new TechRecordAdrDetailsAdditionalExaminerNote(
                "2023-02-02", "additionalExaminerNotes_lastUpdatedBy_2", "additionalExaminerNotes_note_2");
        additionalExaminerNoteList.add(adrDetailsAdditionalExaminerNote1);
        additionalExaminerNoteList.add(adrDetailsAdditionalExaminerNote2);
        adrDataToPatch.setTechRecordAdrDetailsAdditionalExaminerNotes(additionalExaminerNoteList);
        adrDataToPatch.setTechRecordAdrDetailsAdrCertificateNotes(null);
        addAdrDataToList();
    }

    @Test
    public void tankStatementAllOptionalFields() {
        adrDataToPatch = adrSeedData.vehicleTypeTankStatement();
        addAdrDataToList();
    }

    @Test
    public void tankProductListRefNoAllOptionalFields() {
        adrDataToPatch = adrSeedData.vehicleTypeTankProductListWithRefNo();
        addAdrDataToList();
    }

    @Test
    public void tankProductListUnNoAllOptionalFields() {
        adrDataToPatch = adrSeedData.vehicleTypeTankProductListWithUnNoList();
        addAdrDataToList();
    }

    @Test
    public void batteryListAllOptionalFields() {
        adrDataToPatch = adrSeedData.vehicleTypeWithBatteryAdrDetails();
        addAdrDataToList();
    }

    @Test
    public void notTankOrBatteryAllOptionalFields() {
        adrDataToPatch = adrSeedData.vehicleTypeNotTankOrBattery();
        addAdrDataToList();
    }

    @Test
    public void notATankOrBatteryMinimumData() {
        adrDataToPatch = adrSeedData.minimumAdrDataNotTank();
        addAdrDataToList();
    }

    @Test
    public void tankMinimumData() {
        adrDataToPatch = adrSeedData.minimumAdrDataForTankOrBattery();
        addAdrDataToList();
    }

    @Test
    public void noproductListRefOrUnNo() {
        adrDataToPatch = adrSeedData.minimumAdrDataForTankOrBattery();
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementSelect("Product list");
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo(null);
        adrDataToPatch
                .setTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo(null);
        addAdrDataToList();
    }

    // adrPassCertificateDetails does not exist in source for migration

    // @Test
    // TODO Currently disabled for testing purposes
    public void onePassCertificateDetails_certificateTypePass() {
        //one passCertificateDetails, certificateType PASS
        List<TechRecordAdrPassCertificateDetail> passCertificateDetailList = new ArrayList<>();
        TechRecordAdrPassCertificateDetail passCertificateDetail = new TechRecordAdrPassCertificateDetail(
                "CREATED-BY-NAME-01", "PASS", "2023-04-01T01:49:00.055Z", "CERTIFICATE-ID-1");
        passCertificateDetailList.add(passCertificateDetail);
        adrDataToPatch.setTechRecordAdrPassCertificateDetails(passCertificateDetailList);
        addAdrDataToList();
    }

    // @Test
    // TODO Currently disabled for testing purposes
    public void twoPassCertificateDetails_certificateTypePassReplacement() {
        //two passCertificateDetails, certificateType PASS and REPLACEMENT
        List<TechRecordAdrPassCertificateDetail> passCertificateDetailList = new ArrayList<>();
        TechRecordAdrPassCertificateDetail passCertificateDetail1 = new TechRecordAdrPassCertificateDetail(
                "CREATED-BY-NAME-01", "PASS", "2023-04-01T01:49:00.055Z", "CERTIFICATE-ID-1");
        TechRecordAdrPassCertificateDetail passCertificateDetail2 = new TechRecordAdrPassCertificateDetail(
                "CREATED-BY-NAME-02", "REPLACEMENT", "2023-05-02T02:59:00.066Z", "CERTIFICATE-ID-2");
        passCertificateDetailList.add(passCertificateDetail1);
        passCertificateDetailList.add(passCertificateDetail2);
        adrDataToPatch.setTechRecordAdrPassCertificateDetails(passCertificateDetailList);
        addAdrDataToList();
    }

    //Below tests make sure that objects can have the maximum allowed string length
    //This is a happy path test, unhappy path tests will be in CVS-Data-Remediation model tests
    @Test
    public void checkMaxLengthStringConstraintsHappyPath () {
        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsName(randomStringGenerator(150));
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsApplicantDetailsName().length(), 150);

        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsStreet(randomStringGenerator(150));
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsApplicantDetailsStreet().length(), 150);

        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsTown(randomStringGenerator(100));
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsApplicantDetailsTown().length(), 100);

        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsCity(randomStringGenerator(100));
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsApplicantDetailsCity().length(), 100);

        adrDataToPatch.setTechRecordAdrDetailsApplicantDetailsPostcode(randomStringGenerator(25));
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsApplicantDetailsPostcode().length(), 25);

        adrDataToPatch.setTechRecordAdrDetailsAdrTypeApprovalNo(randomStringGenerator(40));
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsAdrTypeApprovalNo().length(), 40);

        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankManufacturer(randomStringGenerator(70));
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankManufacturer().length(), 70);

        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankManufacturerSerialNo(randomStringGenerator(50));
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankManufacturerSerialNo().length(), 50);

        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankTypeAppNo(randomStringGenerator(65));
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankTypeAppNo().length(), 65);

        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankCode(randomStringGenerator(30));
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankCode().length(), 30);

        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementStatement(randomStringGenerator(1500));
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementStatement().length(), 1500);

        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo(randomStringGenerator(1500));
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo().length(), 1500);

        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementProductList(randomStringGenerator(1500));
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementProductList().length(), 1500);

        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsSpecialProvisions(randomStringGenerator(1024));
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsSpecialProvisions().length(), 1024);

        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo(randomStringGenerator(70));
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo().length(), 70);

        adrDataToPatch.setTechRecordAdrDetailsBatteryListNumber(randomStringGenerator(8));
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsBatteryListNumber().length(), 8);

        adrDataToPatch.setTechRecordAdrDetailsBrakeDeclarationIssuer(randomStringGenerator(500));
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsBrakeDeclarationIssuer().length(), 500);

        adrDataToPatch.setTechRecordAdrDetailsAdrCertificateNotes(randomStringGenerator(1500));
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsAdrCertificateNotes().length(), 1500);

        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsYearOfManufacture(randomIntGenerator(4));
        Assert.assertEquals((adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsYearOfManufacture()).toString().length(), 4);

        adrDataToPatch.setTechRecordAdrDetailsWeight(Double.valueOf(randomIntGenerator(8)));
        double weightDouble = adrDataToPatch.getTechRecordAdrDetailsWeight();
        int weightInt = (int) weightDouble;
        String weightAsString = Integer.toString(weightInt);
        Assert.assertEquals(weightAsString.length(), 8);

        List<String> productListUnNoList = new ArrayList<>();
        String productListUnNoString = randomStringGenerator(1500);
        productListUnNoList.add(productListUnNoString);
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo(productListUnNoList);
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo().get(0).length(), 1500);

        List<TechRecordAdrDetailsAdditionalExaminerNote> additionalExaminersNotesList = new ArrayList<>();
        TechRecordAdrDetailsAdditionalExaminerNote additionalExaminerNote = new TechRecordAdrDetailsAdditionalExaminerNote("2024-03-06", "examinerUpdater", randomStringGenerator(1500));
        additionalExaminersNotesList.add(additionalExaminerNote);
        adrDataToPatch.setTechRecordAdrDetailsAdditionalExaminerNotes(additionalExaminersNotesList);
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsAdditionalExaminerNotes().get(0).getNote().length(), 1500);

        List<TechRecordAdrDetailsTankTankDetailsTc3Detail> tc3DetailList = new ArrayList<>();
        TechRecordAdrDetailsTankTankDetailsTc3Detail tc3DetailObject = new TechRecordAdrDetailsTankTankDetailsTc3Detail("intermediate", randomStringGenerator(75), "2024-06-01");
        tc3DetailList.add(tc3DetailObject);
        adrDataToPatch.setTechRecordAdrDetailsTankTankDetailsTc3Details(tc3DetailList);
        Assert.assertEquals(adrDataToPatch.getTechRecordAdrDetailsTankTankDetailsTc3Details().get(0).getTc3PeriodicNumber().length(), 75);
    }

    private TechRecordHgvComplete createHgvTechRecord(String filePath) {
        //Create and post the HGV Tech record
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

    private String randomStringGenerator(int len) {
        //Generates a string of
        String ALLOWED_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(ALLOWED_CHARACTERS.charAt(rnd.nextInt(ALLOWED_CHARACTERS.length())));
        }
        return sb.toString();
    }

    private int randomIntGenerator(int len) {
        //Generates a random int of set digit length
        //Cannot start with a 0
        String ALLOWED_CHARACTERS = "0123456789";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            int randomInt = rnd.nextInt(ALLOWED_CHARACTERS.length());
            if (i == 0) {
                while (randomInt == 0) {
                    randomInt = rnd.nextInt(ALLOWED_CHARACTERS.length());
                }
            }
            sb.append(ALLOWED_CHARACTERS.charAt(randomInt));
        }
        return Integer.parseInt(sb.toString());
    }

    private void addAdrDataToList() {
        //Adds each ADR data block to a list to be processed
        String adrDataAsJson = adrDataGen.createJsonStringFromTechRecord(adrDataToPatch);
        remediationFileContents.add(adrDataAsJson);
    }

    @AfterClass
    public static void teardown() {
        // TODO string to JSON file
        String output = String.join(System.lineSeparator(), remediationFileContents);
        System.out.println(output);
    }
}
