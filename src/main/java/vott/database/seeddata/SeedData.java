package vott.database.seeddata;

import vott.models.dao.*;

public class SeedData {
    public static CustomDefect newTestCustomDefect(int testResultPK) {
        CustomDefect cd = new CustomDefect();

        cd.setTestResultID(String.valueOf(testResultPK));
        cd.setReferenceNumber("444444");
        cd.setDefectName("Test Custom Defect");
        cd.setDefectNotes("Test Custom Defect Notes");

        return cd;
    }

    public static vott.models.dao.Vehicle newTestVehicle() {
        vott.models.dao.Vehicle vehicle = new vott.models.dao.Vehicle();

        vehicle.setSystemNumber("SYSTEM-NUMBER");
        vehicle.setVin("A12345");
        vehicle.setVrm_trm("999999999");
        vehicle.setTrailerID("88888888");

        return vehicle;
    }

    public static FuelEmission newTestFuelEmission() {
        FuelEmission fe = new FuelEmission();

        fe.setModTypeCode("a");
        fe.setDescription("Test Description");
        fe.setEmissionStandard("Test Standard");
        fe.setFuelType("Petrol");

        return fe;
    }

    public static TestStation newTestTestStation() {
        TestStation ts = new TestStation();

        ts.setPNumber("987654321");
        ts.setName("Test Test Station");
        ts.setType("Test");

        return ts;
    }

    public static Tester newTestTester() {
        Tester tester = new Tester();

        tester.setStaffID("1");
        tester.setName("Auto Test");
        tester.setEmailAddress("auto@test.com");

        return tester;
    }

    public static VehicleClass newTestVehicleClass() {
        VehicleClass vc = new VehicleClass();

        vc.setCode("1");
        vc.setDescription("Test Description");
        vc.setVehicleType("Test Type");
        vc.setVehicleSize("55555");
        vc.setVehicleConfiguration("Test Configuration");
        vc.setEuVehicleCategory("ABC");

        return vc;
    }

    public static TestType newTestTestType() {
        TestType tt = new TestType();

        tt.setTestTypeClassification("Test Test Type");
        tt.setTestTypeName("Test Name");

        return tt;
    }

    public static Preparer newTestPreparer() {
        Preparer preparer = new Preparer();

        preparer.setPreparerID("1");
        preparer.setName("Test Name");

        return preparer;
    }

    public static Identity newTestIdentity() {
        Identity identity = new Identity();

        identity.setIdentityID("55555");
        identity.setName("Test Name");

        return identity;
    }

    public static vott.models.dao.TestResult newTestTestResult(int vehiclePK, int fuelEmissionPK, int testStationPK, int testerPK, int preparerPK, int vehicleClassPK, int testTypePK, int identityPK) {
        vott.models.dao.TestResult tr = new vott.models.dao.TestResult();

        tr.setVehicleID(String.valueOf(vehiclePK));
        tr.setFuelEmissionID(String.valueOf(fuelEmissionPK));
        tr.setTestStationID(String.valueOf(testStationPK));
        tr.setTesterID(String.valueOf(testerPK));
        tr.setPreparerID(String.valueOf(preparerPK));
        tr.setVehicleClassID(String.valueOf(vehicleClassPK));
        tr.setTestTypeID(String.valueOf(testTypePK));
        tr.setTestStatus("Test Pass");
        tr.setReasonForCancellation("Automation Test Run");
        tr.setNumberOfSeats("3");
        tr.setOdometerReading("900");
        tr.setOdometerReadingUnits("Test Units");
        tr.setCountryOfRegistration("Test Country");
        tr.setNoOfAxles("4");
        tr.setRegnDate("2100-12-31");
        tr.setFirstUseDate("2100-12-31");
        tr.setCreatedAt("2021-01-01 00:00:00.000000");
        tr.setLastUpdatedAt("2021-01-01 00:00:00.000000");
        tr.setTestCode("111");
        tr.setTestNumber("A111B222");
        tr.setCertificateNumber("A111B222");
        tr.setSecondaryCertificateNumber("A111B222");
        tr.setTestExpiryDate("2022-01-01");
        tr.setTestAnniversaryDate("2022-01-01");
        tr.setTestTypeStartTimestamp("2022-01-01 00:00:00.000000");
        tr.setTestTypeEndTimestamp("2022-01-01 00:00:00.000000");
        tr.setNumberOfSeatbeltsFitted("2");
        tr.setLastSeatbeltInstallationCheckDate("2022-01-01");
        tr.setSeatbeltInstallationCheckDate("1");
        tr.setTestResult("Auto Test");
        tr.setReasonForAbandoning("Test Automation Run");
        tr.setAdditionalNotesRecorded("Additional Test Notes");
        tr.setAdditionalCommentsForAbandon("Additional Test Comments");
        tr.setParticulateTrapFitted("Particulate Test");
        tr.setParticulateTrapSerialNumber("ABC123");
        tr.setModificationTypeUsed("Test Modification");
        tr.setSmokeTestKLimitApplied("Smoke Test");
        tr.setCreatedByID(String.valueOf(identityPK));
        tr.setLastUpdatedByID(String.valueOf(identityPK));

        return tr;
    }

    public static Defect newTestDefect() {
        Defect defect = new Defect();

        defect.setImNumber("123");
        defect.setImDescription("Test IM Description");
        defect.setItemNumber("5555");
        defect.setItemDescription("Test Item Description");
        defect.setDeficiencyRef("Test Reference");
        defect.setDeficiencyID("1");
        defect.setDeficiencySubID("444");
        defect.setDeficiencyCategory("Category");
        defect.setDeficiencyText("Test Test");
        defect.setStdForProhibition("1");

        return defect;
    }

    public static Location newTestLocation() {
        Location location = new Location();

        location.setVertical("TestV");
        location.setHorizontal("TestH");
        location.setLateral("TestLat");
        location.setLongitudinal("TestL");
        location.setRowNumber("10");
        location.setSeatNumber("20");
        location.setAxleNumber("30");

        return location;
    }

    public static TestDefect newTestTestDefect(int testResultPK, int defectPK, int locationPK) {
        TestDefect td = new TestDefect();

        td.setTestResultID(String.valueOf(testResultPK));
        td.setDefectID(String.valueOf(defectPK));
        td.setLocationID(String.valueOf(locationPK));
        td.setNotes("Test Notes");
        td.setPrs("1");
        td.setProhibitionIssued("1");

        return td;
    }

    public static MakeModel newTestMakeModel() {
        MakeModel mm = new MakeModel();

        mm.setMake("Test Make");
        mm.setModel("Test Model");
        mm.setChassisMake("Test Chassis Make");
        mm.setChassisModel("Test Chassis Model");
        mm.setBodyMake("Test Body Make");
        mm.setBodyModel("Test Body Model");
        mm.setModelLiteral("Test Model Literal");
        mm.setBodyTypeCode("1");
        mm.setBodyTypeDescription("Test Description");
        mm.setFuelPropulsionSystem("Test Fuel");
        mm.setDtpCode("888888");

        return mm;
    }

    public static ContactDetails newTestContactDetails() {
        ContactDetails cd = new ContactDetails();

        cd.setName("Test Name");
        cd.setAddress1("Test Address 1");
        cd.setAddress2("Test Address 2");
        cd.setPostTown("Test Post Town");
        cd.setAddress3("Test Address 3");
        cd.setEmailAddress("TestEmailAddress");
        cd.setTelephoneNumber("8888888");
        cd.setFaxNumber("99999999");

        return cd;
    }

    public static vott.models.dao.TechnicalRecord newTestTechnicalRecord(int vehiclePK, int makeModelPK, int vehicleClassPK, int contactDetailsPK, int identityPK) {
        vott.models.dao.TechnicalRecord tr = new vott.models.dao.TechnicalRecord();

        tr.setVehicleID(String.valueOf(vehiclePK));
        tr.setRecordCompleteness("Complete");
        tr.setCreatedAt("2021-01-01 00:00:00.000000");
        tr.setLastUpdatedAt("2021-01-01 00:00:00.000000");
        tr.setMakeModelID(String.valueOf(makeModelPK));
        tr.setFunctionCode("A");
        tr.setOffRoad("1");
        tr.setNumberOfWheelsDriven("4");
        tr.setEmissionsLimit("Test Emission Limit");
        tr.setDepartmentalVehicleMarker("1");
        tr.setAlterationMarker("1");
        tr.setVehicleClassID(String.valueOf(vehicleClassPK));
        tr.setVariantVersionNumber("Test Variant Number");
        tr.setGrossEecWeight("1200");
        tr.setTrainEecWeight("1400");
        tr.setMaxTrainEecWeight("1400");
        tr.setApplicantDetailID(String.valueOf(contactDetailsPK));
        tr.setPurchaserDetailID(String.valueOf(contactDetailsPK));
        tr.setManufacturerDetailID(String.valueOf(contactDetailsPK));
        tr.setManufactureYear("2021");
        tr.setRegnDate("2021-01-01");
        tr.setFirstUseDate("2021-01-01");
        tr.setCoifDate("2021-01-01");
        tr.setNtaNumber("NTA Number");
        tr.setCoifSerialNumber("55555");
        tr.setCoifCertifierName("88888");
        tr.setApprovalType("111");
        tr.setApprovalTypeNumber("ABC11111");
        tr.setVariantNumber("Test Variant");
        tr.setConversionRefNo("10");
        tr.setSeatsLowerDeck("2");
        tr.setSeatsUpperDeck("3");
        tr.setStandingCapacity("15");
        tr.setSpeedRestriction("60");
        tr.setSpeedLimiterMrk("1");
        tr.setTachoExemptMrk("1");
        tr.setDispensations("Test Dispensations");
        tr.setRemarks("Automation Test Remarks");
        tr.setReasonForCreation("Automation Test ");
        tr.setStatusCode("B987");
        tr.setUnladenWeight("1400");
        tr.setGrossKerbWeight("1400");
        tr.setGrossLadenWeight("1400");
        tr.setGrossGbWeight("1400");
        tr.setGrossDesignWeight("1400");
        tr.setTrainGbWeight("1400");
        tr.setTrainDesignWeight("1400");
        tr.setMaxTrainGbWeight("1400");
        tr.setMaxTrainDesignWeight("1400");
        tr.setMaxLoadOnCoupling("1400");
        tr.setFrameDescription("Test Automation");
        tr.setTyreUseCode("A1");
        tr.setRoadFriendly("1");
        tr.setDrawbarCouplingFitted("1");
        tr.setEuroStandard("Y555");
        tr.setSuspensionType("Y");
        tr.setCouplingType("B");
        tr.setLength("100");
        tr.setHeight("50");
        tr.setWidth("50");
        tr.setFrontAxleTo5thWheelCouplingMin("55");
        tr.setFrontAxleTo5thWheelCouplingMax("65");
        tr.setFrontAxleTo5thWheelMin("45");
        tr.setFrontAxleTo5thWheelMax("65");
        tr.setFrontAxleToRearAxle("15");
        tr.setRearAxleToRearTrl("25");
        tr.setCouplingCenterToRearAxleMin("25");
        tr.setCouplingCenterToRearAxleMax("85");
        tr.setCouplingCenterToRearTrlMin("25");
        tr.setCouplingCenterToRearTrlMax("85");
        tr.setCentreOfRearmostAxleToRearOfTrl("25");
        tr.setNotes("Test Notes");
        tr.setPurchaserNotes("Purchaser Notes");
        tr.setManufacturerNotes("Manufactuer Notes");
        tr.setNoOfAxles("3");
        tr.setBrakeCode("XXXXX");
        tr.setBrakes_dtpNumber("DTP111");
        tr.setBrakes_loadSensingValve("1");
        tr.setBrakes_antilockBrakingSystem("1");
        tr.setCreatedByID(String.valueOf(identityPK));
        tr.setLastUpdatedByID(String.valueOf(identityPK));
        tr.setUpdateType("AutoTest");
        tr.setNumberOfSeatbelts("3");
        tr.setSeatbeltInstallationApprovalDate("2021-01-01");

        return tr;
    }

    public static PSVBrakes newTestPSVBrakes(int technicalRecordPK) {
        PSVBrakes psv = new PSVBrakes();

        psv.setTechnicalRecordID(String.valueOf(technicalRecordPK));
        psv.setBrakeCodeOriginal("222");
        psv.setBrakeCode("Test");
        psv.setDataTrBrakeOne("Test Data");
        psv.setDataTrBrakeTwo("Test Data");
        psv.setDataTrBrakeThree("Test Data");
        psv.setRetarderBrakeOne("Test Data");
        psv.setRetarderBrakeTwo("Test Data");
        psv.setServiceBrakeForceA("11");
        psv.setSecondaryBrakeForceA("22");
        psv.setParkingBrakeForceA("33");
        psv.setServiceBrakeForceB("44");
        psv.setSecondaryBrakeForceB("55");
        psv.setParkingBrakeForceB("66");

        return psv;
    }

    public static Axles newTestAxles(int technicalRecordPK, int tyrePK) {
        Axles axles = new Axles();

        axles.setTechnicalRecordID(String.valueOf(technicalRecordPK));
        axles.setTyreID(String.valueOf(tyrePK));
        axles.setAxleNumber("222");
        axles.setParkingBrakeMrk("1");
        axles.setKerbWeight("1200");
        axles.setLadenWeight("1500");
        axles.setGbWeight("1200");
        axles.setEecWeight("1500");
        axles.setDesignWeight("1200");
        axles.setBrakeActuator("10");
        axles.setLeverLength("10");
        axles.setSpringBrakeParking("1");

        return axles;
    }

    public static Tyre newTestTyre() {
        Tyre tyre = new Tyre();

        tyre.setTyreSize("456");
        tyre.setPlyRating("10");
        tyre.setFitmentCode("55555");
        tyre.setDataTrAxles("Test Data");
        tyre.setSpeedCategorySymbol("1");
        tyre.setTyreCode("88888");

        return tyre;
    }

    public static AxleSpacing newTestAxleSpacing(int technicalRecordPK) {
        AxleSpacing as = new AxleSpacing();

        as.setTechnicalRecordID(String.valueOf(technicalRecordPK));
        as.setAxles("Test");
        as.setValue("120");

        return as;
    }

    public static Plate newTestPlate(int technicalRecordPK) {
        Plate plate = new Plate();
        plate.setTechnicalRecordID(String.valueOf(technicalRecordPK));
        plate.setPlateSerialNumber("666666");
        plate.setPlateIssueDate("2100-12-31");
        plate.setPlateReasonForIssue("Test Reason");
        plate.setPlateIssuer("Auto Test");

        return plate;
    }

    public static Microfilm newTestMicrofilm(int technicalRecordPK) {
        Microfilm mf = new Microfilm();

        mf.setTechnicalRecordID(String.valueOf(technicalRecordPK));
        mf.setMicrofilmDocumentType("Test Document Type");
        mf.setMicrofilmRollNumber("8888");
        mf.setMicrofilmSerialNumber("1234");

        return mf;
    }

    public static VehicleSubclass newTestVehicleSubclass(int vehicleClassPK) {
        VehicleSubclass vs = new VehicleSubclass();

        vs.setVehicleClassID(String.valueOf(vehicleClassPK));
        vs.setSubclass("z");

        return vs;
    }

}
