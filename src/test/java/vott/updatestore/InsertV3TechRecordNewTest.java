package vott.updatestore;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import vott.api.TechnicalRecordsNewV3;
import vott.auth.GrantType;
import vott.auth.OAuthVersion;
import vott.auth.TokenService;
import vott.json.GsonInstance;
import vott.models.dto.seeddata.TechRecordGenerator;
import vott.models.dto.techrecords.TechRecordPOSTV3;
import vott.models.dto.techrecordsv3.TechRecordHgvComplete;
import vott.models.dto.techrecordsv3.TechRecordV3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class InsertV3TechRecordNewTest {
    private TokenService v1ImplicitTokens;
    private String payloadPath;
    private TechRecordGenerator trg;
    
    
    @Before
    public void setUp() throws Exception {
        v1ImplicitTokens = new TokenService(OAuthVersion.V1, GrantType.IMPLICIT);
        payloadPath = "src/main/resources/payloads/";
    }

    @Test
    public void createHGVTechRecordADROnlyTest() {
        trg = new TechRecordGenerator();
        TechRecordHgvComplete actualTechRecordHgv = trg.createCompleteHGVFromJson(payloadPath + "/TechRecordsV3/HGV_2_Axle_Tech_Record_ADR.json");
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
        String expectedTankDetails_tankCode = "Special Provisions";
        Assert.assertEquals(expectedTankDetails_tankCode, actualTankDetails_tankCode);

        //TankDetails_tankManufacturer
        String actualTankDetails_tankManufacturer = actualTechRecordHgv.getTechRecordAdrDetailsTankTankDetailsTankManufacturer();
        String expectedTankDetails_tankManufacturer = "Special Provisions";
        Assert.assertEquals(expectedTankDetails_tankManufacturer, actualTankDetails_tankManufacturer);

        //TankDetails_tankManufacturerSerialNo
        String actualTankDetails_tankManufacturerSerialNo = actualTechRecordHgv.getTechRecordAdrDetailsTankTankDetailsTankManufacturerSerialNo();
        String expectedTankDetails_tankManufacturerSerialNo = "Special Provisions";
        Assert.assertEquals(expectedTankDetails_tankManufacturerSerialNo, actualTankDetails_tankManufacturerSerialNo);

        //TankDetails_tankStatement_productList
        String actualTankDetails_tankStatement_productList = actualTechRecordHgv.getTechRecordAdrDetailsTankTankDetailsTankStatementProductList();
        String expectedTankDetails_tankStatement_productList = "Special Provisions";
        Assert.assertEquals(expectedTankDetails_tankStatement_productList, actualTankDetails_tankStatement_productList);

        //TankDetails_tankStatement_productListRefNo
        String actualTankDetails_tankStatement_productListRefNo = actualTechRecordHgv.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo();
        String expectedTankDetails_tankStatement_productListRefNo = "Special Provisions";
        Assert.assertEquals(expectedTankDetails_tankStatement_productListRefNo, actualTankDetails_tankStatement_productListRefNo);

        //TankDetails_tankStatement_select
        String actualTankDetails_tankStatement_select = actualTechRecordHgv.getTechRecordAdrDetailsTankTankDetailsTankStatementSelect();
        String expectedTankDetails_tankStatement_select = "Special Provisions";
        Assert.assertEquals(expectedTankDetails_tankStatement_select, actualTankDetails_tankStatement_select);

        //TankDetails_tankStatement_statement
        String actualTankDetails_tankStatement_statement = actualTechRecordHgv.getTechRecordAdrDetailsTankTankDetailsTankStatementStatement();
        String expectedTankDetails_tankStatement_statement = "Special Provisions";
        Assert.assertEquals(expectedTankDetails_tankStatement_statement, actualTankDetails_tankStatement_statement);

        //TankDetails_tankStatement_substancesPermitted
        String actualTankDetails_tankStatement_substancesPermitted = actualTechRecordHgv.getTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted();
        String expectedTankDetails_tankStatement_substancesPermitted = "Special Provisions";
        Assert.assertEquals(expectedTankDetails_tankStatement_substancesPermitted, actualTankDetails_tankStatement_substancesPermitted);

        //TankDetails_tankTypeAppNo
        String actualTankDetails_tankTypeAppNo = actualTechRecordHgv.getTechRecordAdrDetailsTankTankDetailsTankTypeAppNo();
        String expectedTankDetails_tankTypeAppNo = "Special Provisions";
        Assert.assertEquals(expectedTankDetails_tankTypeAppNo, actualTankDetails_tankTypeAppNo);

        //TankDetails_tc2Details_tc2IntermediateApprovalNo
        String actualTankDetails_tc2Details_tc2IntermediateApprovalNo = actualTechRecordHgv.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo();
        String expectedTankDetails_tc2Details_tc2IntermediateApprovalNo = "Special Provisions";
        Assert.assertEquals(expectedTankDetails_tc2Details_tc2IntermediateApprovalNo, actualTankDetails_tc2Details_tc2IntermediateApprovalNo);

        //TankDetails_tc2Details_tc2IntermediateExpiryDate
        String actualTankDetails_tc2Details_tc2IntermediateExpiryDate = actualTechRecordHgv.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate();
        String expectedTankDetails_tc2Details_tc2IntermediateExpiryDate = "Special Provisions";
        Assert.assertEquals(expectedTankDetails_tc2Details_tc2IntermediateExpiryDate, actualTankDetails_tc2Details_tc2IntermediateExpiryDate);

        //TankDetails_tc2Details_tc2Type
        String actualTankDetails_tc2Details_tc2Type = actualTechRecordHgv.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type();
        String expectedTankDetails_tc2Details_tc2Type = "Special Provisions";
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
        String actualManufactureYear = actualTechRecordHgv.getTechRecordManufactureYear();
        String expectedManufactureYear = null;
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
    public void createPSVTechRecordTest() {
        trg = new TechRecordGenerator();
        TechRecordV3 techRecordPsv = trg.createCompletePSVFromJson(payloadPath + "/TechRecordsV3/PSV_Large_Tech_Record_Annual_Test_m2.json");
    }
    @Test
    public void postTechRecordTest() {
        trg = new TechRecordGenerator();

        //HGV Posts correctly
        int expected_StatusCode = 201;
        TechRecordV3 techRecordHgv = trg.createCompleteHGVFromJson(payloadPath + "/TechRecordsV3/HGV_2_Axel_Tech_Record_Annual_Test_Multiple_Test_Types.json");
        int HGV_StatusCode = postTechRecord(techRecordHgv);
        Assert.assertEquals(HGV_StatusCode, expected_StatusCode);

        //PSV Posts correctly
        TechRecordV3 techRecordPsv = trg.createCompletePSVFromJson(payloadPath + "/TechRecordsV3/PSV_Large_Tech_Record_Annual_Test_m2.json");
        int PSV_StatusCode = postTechRecord(techRecordPsv);
        Assert.assertEquals(PSV_StatusCode, expected_StatusCode);
    }
    public int postTechRecord(TechRecordV3 techRecord)
    {
        int statusCode = TechnicalRecordsNewV3.postTechnicalRecordV3(techRecord, v1ImplicitTokens.getBearerToken());
        return statusCode;
    }
}
