package vott.updatestore;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import vott.api.TechnicalRecordsV3;
import vott.auth.GrantType;
import vott.auth.OAuthVersion;
import vott.auth.TokenService;
import vott.models.dto.seeddata.TechRecordHgvCompleteGenerator;
import vott.models.dto.seeddata.TechRecordPsvCompleteGenerator;
import vott.models.dto.seeddata.TechRecordTrlCompleteGenerator;
import vott.models.dto.techrecordsv3.*;

import java.util.ArrayList;
import java.util.List;

public class InsertV3TechRecordTest {
    private TokenService v1ImplicitTokens;
    private String payloadPath;


    @Before
    public void setUp() throws Exception {
        v1ImplicitTokens = new TokenService(OAuthVersion.V1, GrantType.IMPLICIT);
        payloadPath = "src/main/resources/payloads/";
    }

    @Test
    public void createHGVTechRecordADROnlyTest() {
        TechRecordHgvCompleteGenerator hgv_trg = new TechRecordHgvCompleteGenerator(new TechRecordHgvComplete());

        TechRecordHgvComplete actualTechRecordHgv = hgv_trg.createTechRecordFromJsonFile(payloadPath + "/TechRecordsV3/HGV_2_Axle_Tech_Record_ADR.json");
        //ADR Fields
        //ApprovalNo
        String actualAdrDetailsAdrTypeApprovalNo = actualTechRecordHgv.getTechRecordAdrDetailsAdrTypeApprovalNo();
        String expectedAdrDetailsAdrTypeApprovalNo = "1234";
        Assert.assertEquals(expectedAdrDetailsAdrTypeApprovalNo, actualAdrDetailsAdrTypeApprovalNo);

        //applicantDetails_city
        String actualAdrDetailsApplicantDetails_city = actualTechRecordHgv.getTechRecordAdrDetailsApplicantDetailsCity();
        String expectedAdrDetailsApplicantDetails_city = "test";
        Assert.assertEquals(expectedAdrDetailsApplicantDetails_city, actualAdrDetailsApplicantDetails_city);

        //applicantDetails_name
        String actualAdrDetailsApplicantDetails_name = actualTechRecordHgv.getTechRecordAdrDetailsApplicantDetailsName();
        String expectedAdrDetailsApplicantDetails_name = "test user";
        Assert.assertEquals(expectedAdrDetailsApplicantDetails_name, actualAdrDetailsApplicantDetails_name);

        //applicantDetails_postcode
        String actualAdrDetailsApplicantDetails_postcode = actualTechRecordHgv.getTechRecordAdrDetailsApplicantDetailsPostcode();
        String expectedAdrDetailsApplicantDetails_postcode = "123456";
        Assert.assertEquals(expectedAdrDetailsApplicantDetails_postcode, actualAdrDetailsApplicantDetails_postcode);

        //applicantDetails_street
        String actualAdrDetailsApplicantDetails_street = actualTechRecordHgv.getTechRecordAdrDetailsApplicantDetailsStreet();
        String expectedAdrDetailsApplicantDetails_street = "123";
        Assert.assertEquals(expectedAdrDetailsApplicantDetails_street, actualAdrDetailsApplicantDetails_street);

        //applicantDetails_town
        String actualAdrDetailsApplicantDetails_town = actualTechRecordHgv.getTechRecordAdrDetailsApplicantDetailsTown();
        String expectedAdrDetailsApplicantDetails_town = "test";
        Assert.assertEquals(expectedAdrDetailsApplicantDetails_town, actualAdrDetailsApplicantDetails_town);

        //batteryListNumber
        String actualBatteryListNumber = actualTechRecordHgv.getTechRecordAdrDetailsBatteryListNumber();
        String expectedBatteryListNumber = null;
        Assert.assertEquals(expectedBatteryListNumber, actualBatteryListNumber);

        //brakeDeclarationIssuer
        String actualBrakeDeclarationIssuer = actualTechRecordHgv.getTechRecordAdrDetailsBrakeDeclarationIssuer();
        String expectedBrakeDeclarationIssuer = "test";
        Assert.assertEquals(expectedBrakeDeclarationIssuer, actualBrakeDeclarationIssuer);

        //brakeEndurance
        boolean actualBrakeEndurance = actualTechRecordHgv.getTechRecordAdrDetailsBrakeEndurance();
        boolean expectedBrakeEndurance = true;
        Assert.assertEquals(expectedBrakeEndurance, actualBrakeEndurance);

        //CompatibilityGroupJ
        String actualCompatibilityGroupJ = actualTechRecordHgv.getTechRecordAdrDetailsCompatibilityGroupJ();
        String expectedCompatibilityGroupJ = "I";
        Assert.assertEquals(expectedCompatibilityGroupJ, actualCompatibilityGroupJ);

        //DangerousGoods
        Boolean actualDangerousGoods = actualTechRecordHgv.getTechRecordAdrDetailsDangerousGoods();
        Boolean expectedDangerousGoods = true;
        Assert.assertEquals(expectedDangerousGoods, actualDangerousGoods);

        //DeclarationsSeen
        Boolean actualDeclarationsSeen = actualTechRecordHgv.getTechRecordAdrDetailsDeclarationsSeen();
        Boolean expectedDeclarationsSeen = false;
        Assert.assertEquals(expectedDeclarationsSeen, actualDeclarationsSeen);

        //TankDetails_specialProvisions
        String actualTankDetails_specialProvisions = actualTechRecordHgv.getTechRecordAdrDetailsTankTankDetailsSpecialProvisions();
        String expectedTankDetails_specialProvisions = "Special Provisions";
        Assert.assertEquals(expectedTankDetails_specialProvisions, actualTankDetails_specialProvisions);

        //TankDetails_tankCode
        String actualTankDetails_tankCode = actualTechRecordHgv.getTechRecordAdrDetailsTankTankDetailsTankCode();
        String expectedTankDetails_tankCode = "324";
        Assert.assertEquals(expectedTankDetails_tankCode, actualTankDetails_tankCode);

        //TankDetails_tankManufacturer
        String actualTankDetails_tankManufacturer = actualTechRecordHgv.getTechRecordAdrDetailsTankTankDetailsTankManufacturer();
        String expectedTankDetails_tankManufacturer = "asdf";
        Assert.assertEquals(expectedTankDetails_tankManufacturer, actualTankDetails_tankManufacturer);

        //TankDetails_tankManufacturerSerialNo
        String actualTankDetails_tankManufacturerSerialNo = actualTechRecordHgv.getTechRecordAdrDetailsTankTankDetailsTankManufacturerSerialNo();
        String expectedTankDetails_tankManufacturerSerialNo = "234";
        Assert.assertEquals(expectedTankDetails_tankManufacturerSerialNo, actualTankDetails_tankManufacturerSerialNo);

        //TankDetails_tankStatement_productList
        String actualTankDetails_tankStatement_productList = actualTechRecordHgv.getTechRecordAdrDetailsTankTankDetailsTankStatementProductList();
        String expectedTankDetails_tankStatement_productList = "details ";
        Assert.assertEquals(expectedTankDetails_tankStatement_productList, actualTankDetails_tankStatement_productList);

        //TankDetails_tankStatement_productListRefNo
        String actualTankDetails_tankStatement_productListRefNo = actualTechRecordHgv.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo();
        String expectedTankDetails_tankStatement_productListRefNo = "123";
        Assert.assertEquals(expectedTankDetails_tankStatement_productListRefNo, actualTankDetails_tankStatement_productListRefNo);

        //TankDetails_tankStatement_select
        String actualTankDetails_tankStatement_select = actualTechRecordHgv.getTechRecordAdrDetailsTankTankDetailsTankStatementSelect();
        String expectedTankDetails_tankStatement_select = "Product list";
        Assert.assertEquals(expectedTankDetails_tankStatement_select, actualTankDetails_tankStatement_select);

        //TankDetails_tankStatement_statement
        String actualTankDetails_tankStatement_statement = actualTechRecordHgv.getTechRecordAdrDetailsTankTankDetailsTankStatementStatement();
        String expectedTankDetails_tankStatement_statement = null;
        Assert.assertEquals(expectedTankDetails_tankStatement_statement, actualTankDetails_tankStatement_statement);

        //TankDetails_tankStatement_substancesPermitted
        String actualTankDetails_tankStatement_substancesPermitted = actualTechRecordHgv.getTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted();
        String expectedTankDetails_tankStatement_substancesPermitted = "Substances (Class UN number and if necessary packing group and proper shipping name) may be carried";
        Assert.assertEquals(expectedTankDetails_tankStatement_substancesPermitted, actualTankDetails_tankStatement_substancesPermitted);

        //TankDetails_tankTypeAppNo
        String actualTankDetails_tankTypeAppNo = actualTechRecordHgv.getTechRecordAdrDetailsTankTankDetailsTankTypeAppNo();
        String expectedTankDetails_tankTypeAppNo = "234";
        Assert.assertEquals(expectedTankDetails_tankTypeAppNo, actualTankDetails_tankTypeAppNo);

        //TankDetails_tc2Details_tc2IntermediateApprovalNo
        String actualTankDetails_tc2Details_tc2IntermediateApprovalNo = actualTechRecordHgv.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo();
        String expectedTankDetails_tc2Details_tc2IntermediateApprovalNo = "123";
        Assert.assertEquals(expectedTankDetails_tc2Details_tc2IntermediateApprovalNo, actualTankDetails_tc2Details_tc2IntermediateApprovalNo);

        //TankDetails_tc2Details_tc2IntermediateExpiryDate
        String actualTankDetails_tc2Details_tc2IntermediateExpiryDate = actualTechRecordHgv.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate();
        String expectedTankDetails_tc2Details_tc2IntermediateExpiryDate = "2023-03-23";
        Assert.assertEquals(expectedTankDetails_tc2Details_tc2IntermediateExpiryDate, actualTankDetails_tc2Details_tc2IntermediateExpiryDate);

        //TankDetails_tc2Details_tc2Type
        String actualTankDetails_tc2Details_tc2Type = actualTechRecordHgv.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type();
        String expectedTankDetails_tc2Details_tc2Type = "initial";
        Assert.assertEquals(expectedTankDetails_tc2Details_tc2Type, actualTankDetails_tc2Details_tc2Type);

        //TankDetails_yearOfManufacture
        int actualTankDetails_yearOfManufacture = actualTechRecordHgv.getTechRecordAdrDetailsTankTankDetailsYearOfManufacture();
        int expectedTankDetails_yearOfManufacture = 2023;
        Assert.assertEquals(expectedTankDetails_yearOfManufacture, actualTankDetails_yearOfManufacture);

        //VehicleDetails_approvalDate
        String actualVehicleDetails_approvalDate = actualTechRecordHgv.getTechRecordAdrDetailsVehicleDetailsApprovalDate();
        String expectedVehicleDetails_approvalDate = "2023-03-12";
        Assert.assertEquals(expectedVehicleDetails_approvalDate, actualVehicleDetails_approvalDate);

        //VehicleDetails_type
        String actualVehicleDetails_type = actualTechRecordHgv.getTechRecordAdrDetailsVehicleDetailsType();
        String expectedVehicleDetails_type = "Rigid tank";
        Assert.assertEquals(expectedVehicleDetails_type, actualVehicleDetails_type);

        //Weight
        Double actualVehicleWeight = actualTechRecordHgv.getTechRecordAdrDetailsWeight();
        Double expectedVehicleWeight = 123.0;
        Assert.assertEquals(expectedVehicleWeight, actualVehicleWeight);

        //AdditionalExaminerNotes
        List<TechRecordAdrDetailsAdditionalExaminerNote> actualAdditionalExaminerNotes = actualTechRecordHgv.getTechRecordAdrDetailsAdditionalExaminerNotes();

        List<TechRecordAdrDetailsAdditionalExaminerNote> expectedAdditionalExaminerNotes = new ArrayList<>();
        TechRecordAdrDetailsAdditionalExaminerNote expectedAdditionalExaminerNote = new TechRecordAdrDetailsAdditionalExaminerNote("2024-01-17", "SA_Shivangi Das", "Test notes");
        expectedAdditionalExaminerNotes.add(expectedAdditionalExaminerNote);

        Assert.assertTrue(actualAdditionalExaminerNotes.equals(expectedAdditionalExaminerNotes));

        //AdditionalNotesNumber
        List<String> actualAdditionalNotesNumber = actualTechRecordHgv.getTechRecordAdrDetailsAdditionalNotesNumber();

        List<String> expectedAdditionalNotesNumber = new ArrayList<>();
        expectedAdditionalNotesNumber.add("1");
        expectedAdditionalNotesNumber.add("2");

        Assert.assertEquals(expectedAdditionalNotesNumber, actualAdditionalNotesNumber);

        //MemosApply
        List<String> actualMemosApply = actualTechRecordHgv.getTechRecordAdrDetailsMemosApply();

        List<String> expectedMemosApply = new ArrayList<>();
        expectedMemosApply.add("07/09 3mth leak ext ");

        Assert.assertEquals(expectedMemosApply, actualMemosApply);

        //PermittedDangerousGoods
        List<String> actualPermittedDangerousGoods = actualTechRecordHgv.getTechRecordAdrDetailsPermittedDangerousGoods();

        List<String> expectedPermittedDangerousGoods = new ArrayList<>();
        expectedPermittedDangerousGoods.add("Explosives (type 2)");
        expectedPermittedDangerousGoods.add("AT");

        Assert.assertEquals(expectedPermittedDangerousGoods, actualPermittedDangerousGoods);

        //TankDetails_tankStatement_productListUnNo
        List<String> actualTankDetails_tankStatement_productListUnNo = actualTechRecordHgv.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo();

        List<String> expectedTankDetails_tankStatement_productListUnNo = new ArrayList<>();
        expectedTankDetails_tankStatement_productListUnNo.add("112343");
        expectedTankDetails_tankStatement_productListUnNo.add("123432");

        Assert.assertEquals(expectedTankDetails_tankStatement_productListUnNo, actualTankDetails_tankStatement_productListUnNo);

        //TankDetails_tc3Details
        List<TechRecordAdrDetailsTankTankDetailsTc3Detail> actualTankDetails_tc3Details = actualTechRecordHgv.getTechRecordAdrDetailsTankTankDetailsTc3Details();

        List<TechRecordAdrDetailsTankTankDetailsTc3Detail> expectedTankDetails_tc3Details = new ArrayList<>();
        TechRecordAdrDetailsTankTankDetailsTc3Detail expectedTankDetails_tc3Detail = new TechRecordAdrDetailsTankTankDetailsTc3Detail("intermediate", "12345", "2023-04-24");
        expectedTankDetails_tc3Details.add(expectedTankDetails_tc3Detail);

        Assert.assertTrue(expectedTankDetails_tc3Details.equals(actualTankDetails_tc3Details));

        //Axles
        List<TechRecordAxle> actualAxles = actualTechRecordHgv.getTechRecordAxles();

        List<TechRecordAxle> expectedAxles = new ArrayList<>();
        TechRecordAxle expectedAxle1 = new TechRecordAxle(false, 1, 123, 12, 23, 23, "175-14C", "-", "single", 99);
        TechRecordAxle expectedAxle2 = new TechRecordAxle(false, 2, 324, 234, 324, 34, "195-14C", "8", "double", 104);
        expectedAxles.add(expectedAxle1);
        expectedAxles.add(expectedAxle2);

        Assert.assertTrue(expectedAxles.equals(actualAxles));

        //-_--_--_--_--_--_--_--_--_--_--_--_--_--_--_--_--_--_--_--_--_--_--_--_--_--_--_--_--_--_--_--_--_--_--_--_-
        //Required Fields
        //techRecord_reasonForCreation
        String actualReasonForCreation = actualTechRecordHgv.getTechRecordReasonForCreation();
        String expectedReasonForCreation = "test";
        Assert.assertEquals(expectedReasonForCreation, actualReasonForCreation);

//        techRecord_statusCode
        String actualStatusCode = actualTechRecordHgv.getTechRecordStatusCode().toString();
        String expectedStatusCode = "provisional";
        Assert.assertEquals(expectedStatusCode, actualStatusCode);

//        vin
        String actualVin = actualTechRecordHgv.getVin();
        String expectedVin = "ADRTEST";
        Assert.assertEquals(expectedVin, actualVin);

//        techRecord_vehicleConfiguration
        String actualVehicleConfiguration = actualTechRecordHgv.getTechRecordVehicleConfiguration();
        String expectedVehicleConfiguration = "rigid";
        Assert.assertEquals(expectedVehicleConfiguration, actualVehicleConfiguration);

//        techRecord_vehicleClass_description
        String actualVehicleClass_description = actualTechRecordHgv.getTechRecordVehicleClassDescription();
        String expectedVehicleClass_description = "heavy goods vehicle";
        Assert.assertEquals(expectedVehicleClass_description, actualVehicleClass_description);

//        techRecord_approvalType
        String actualApprovalType = actualTechRecordHgv.getTechRecordApprovalType();
        String expectedApprovalType = "ECTA";
        Assert.assertEquals(expectedApprovalType, actualApprovalType);

//        techRecord_manufactureYear
        Integer actualManufactureYear = actualTechRecordHgv.getTechRecordManufactureYear();
        Integer expectedManufactureYear = 2023;
        Assert.assertEquals(expectedManufactureYear, actualManufactureYear);
//        techRecord_bodyType_code
//        techRecord_bodyType_description
//        techRecord_grossGbWeight
//        techRecord_grossDesignWeight
//        techRecord_brakes_dtpNumber
//        techRecord_euVehicleCategory
//        techRecord_axles
//        techRecord_euroStandard
//        techRecord_regnDate
//        techRecord_speedLimiterMrk
//        techRecord_tachoExemptMrk
//        techRecord_fuelPropulsionSystem
//        techRecord_make
//        techRecord_model
//        techRecord_trainGbWeight
//        techRecord_maxTrainGbWeight
//        techRecord_tyreUseCode
//        techRecord_dimensions_length
//        techRecord_dimensions_width
//        techRecord_frontAxleTo5thWheelMin
//        techRecord_frontAxleTo5thWheelMax
//        techRecord_frontAxleToRearAxle
//        techRecord_notes
//        techRecord_roadFriendly
//        techRecord_drawbarCouplingFitted
//        techRecord_offRoad
//        techRecord_applicantDetails_name
//        techRecord_applicantDetails_address1
//        techRecord_applicantDetails_address2
//        techRecord_applicantDetails_postTown

    }
    @Test
    public void postTechRecordTest() {
        TechRecordHgvCompleteGenerator hgv_trg = new TechRecordHgvCompleteGenerator(new TechRecordHgvComplete());
        TechRecordPsvCompleteGenerator psv_trg = new TechRecordPsvCompleteGenerator(new TechRecordPsvComplete());
        TechRecordTrlCompleteGenerator trl_trg = new TechRecordTrlCompleteGenerator(new TechRecordTrlComplete());

        int expected_StatusCode = 201;

        //HGV Posts correctly
        TechRecordHgvComplete techRecordHgv = hgv_trg.createTechRecordFromJsonFile(payloadPath + "/TechRecordsV3/HGV_2_Axle_Tech_Record_ADR.json");
        int HGV_Object_StatusCode = postTechRecordObject(techRecordHgv);
        Assert.assertEquals(HGV_Object_StatusCode, expected_StatusCode);

        //Method not currently in use
        /*
        String techRecordHgvString = hgv_trg.createJsonStringFromTechRecord(techRecordHgvObject);
        int HGV_String_StatusCode = postTechRecordString(techRecordHgvString);
        Assert.assertEquals(HGV_String_StatusCode, expected_StatusCode);
         */

        //PSV Posts correctly
        TechRecordPsvComplete techRecordPsv = psv_trg.createTechRecordFromJsonFile(payloadPath + "/TechRecordsV3/PSV_Large_Tech_Record_Annual_Test_m2.json");
        int PSV_StatusCode = postTechRecordObject(techRecordPsv);
        Assert.assertEquals(PSV_StatusCode, expected_StatusCode);

        //TRL Posts correctly
        TechRecordTrlComplete techRecordTrl = trl_trg.createTechRecordFromJsonFile(payloadPath + "/TestInsertionTimeRecords/TechRecords/TRL_1_Axle_Tech_Record_Annual_Test_40.json");
        int TRL_StatusCode = postTechRecordObject(techRecordTrl);
        Assert.assertEquals(TRL_StatusCode, expected_StatusCode);
    }
    public int postTechRecordObject(TechRecordV3 techRecord)
    {
        int statusCode = TechnicalRecordsV3.postTechnicalRecordV3Object(techRecord, v1ImplicitTokens.getBearerToken());
        return statusCode;
    }

    // Below current is not in use but is left in case it is used in future
    /*
    public int postTechRecordString(String techRecordString)
    {
        int statusCode = TechnicalRecordsNewV3.postTechnicalRecordV3String(techRecordString, v1ImplicitTokens.getBearerToken());
        return statusCode;
    }
     */
    //Comment another update
}
