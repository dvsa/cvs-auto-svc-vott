package vott.database;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import vott.config.VottConfiguration;
import vott.database.connection.ConnectionFactory;
import vott.models.dao.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TechnicalRecordRepositoryTest {

    private List<Integer> deleteOnExit;
    private Integer vehiclePK;
    private Integer vehicle2PK;
    private Integer makeModelPK;
    private Integer identityPK;
    private Integer contactDetailsPK;
    private Integer vehicleClassPK;

    private TechnicalRecordRepository technicalRecordRepository;
    private VehicleRepository vehicleRepository;
    private MakeModelRepository makeModelRepository;
    private IdentityRepository identityRepository;
    private ContactDetailsRepository contactDetailsRepository;
    private VehicleClassRepository vehicleClassRepository;

    @Before
    public void setUp() {
        ConnectionFactory connectionFactory = new ConnectionFactory(
                VottConfiguration.local()
        );

        technicalRecordRepository = new TechnicalRecordRepository(connectionFactory);

        vehicleRepository = new VehicleRepository(connectionFactory);
        vehiclePK = vehicleRepository.fullUpsert(newTestVehicle());

        makeModelRepository = new MakeModelRepository(connectionFactory);
        makeModelPK = makeModelRepository.partialUpsert(newTestMakeModel());

        identityRepository = new IdentityRepository(connectionFactory);
        identityPK = identityRepository.partialUpsert(newTestIdentity());

        contactDetailsRepository = new ContactDetailsRepository(connectionFactory);
        contactDetailsPK = contactDetailsRepository.partialUpsert(newTestContactDetails());

        vehicleClassRepository = new VehicleClassRepository(connectionFactory);
        vehicleClassPK = vehicleClassRepository.partialUpsert(newTestVehicleClass());

        deleteOnExit = new ArrayList<>();
    }

    @After
    public void tearDown() {
        for (int primaryKey : deleteOnExit) {
            technicalRecordRepository.delete(primaryKey);
        }

        vehicleRepository.delete(vehiclePK);
        if (vehicle2PK != null){
            vehicleRepository.delete(vehicle2PK);
        }
        makeModelRepository.delete(makeModelPK);
        identityRepository.delete(identityPK);
        contactDetailsRepository.delete(contactDetailsPK);
        vehicleClassRepository.delete(vehicleClassPK);
    }

    @Test
    public void upsertingIdenticalDataReturnsSamePk() {
        int primaryKey1 = technicalRecordRepository.fullUpsert(newTestTechnicalRecord());
        int primaryKey2 = technicalRecordRepository.fullUpsert(newTestTechnicalRecord());

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }

    @Test
    public void upsertingDifferentVehicleIDValueReturnsDifferentPk() {
        TechnicalRecord tr1 = newTestTechnicalRecord();

        TechnicalRecord tr2 = newTestTechnicalRecord();
        Vehicle vehicle2 = newTestVehicle();
        vehicle2.setVin("Auto VIN");
        vehicle2PK = vehicleRepository.fullUpsert(vehicle2);
        tr2.setVehicleID(String.valueOf(vehicle2PK));

        int primaryKey1 = technicalRecordRepository.fullUpsert(tr1);
        int primaryKey2 = technicalRecordRepository.fullUpsert(tr2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    @Test
    public void upsertingDifferentCreatedAtValueReturnsDifferentPk() {
        TechnicalRecord tr1 = newTestTechnicalRecord();

        TechnicalRecord tr2 = newTestTechnicalRecord();
        tr2.setCreatedAt("2021-12-31 00:00:00");

        int primaryKey1 = technicalRecordRepository.fullUpsert(tr1);
        int primaryKey2 = technicalRecordRepository.fullUpsert(tr2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertNotEquals(primaryKey1, primaryKey2);
    }

    @Test
    public void upsertingIdenticalIndexValuesReturnsSamePk() {
        TechnicalRecord tr1 = newTestTechnicalRecord();

        TechnicalRecord tr2 = newTestTechnicalRecord();
        tr2.setRecordCompleteness("ABCD");

        int primaryKey1 = technicalRecordRepository.fullUpsert(tr1);
        int primaryKey2 = technicalRecordRepository.fullUpsert(tr2);

        deleteOnExit.add(primaryKey1);
        deleteOnExit.add(primaryKey2);

        assertEquals(primaryKey1, primaryKey2);
    }

    private Vehicle newTestVehicle() {
        Vehicle vehicle = new Vehicle();

        vehicle.setSystemNumber("SYSTEM-NUMBER");
        vehicle.setVin("Test VIN");
        vehicle.setVrm_trm("999999999");
        vehicle.setTrailerID("88888888");

        return vehicle;
    }

    private MakeModel newTestMakeModel() {
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

    private Identity newTestIdentity() {
        Identity identity = new Identity();

        identity.setIdentityID("55555");
        identity.setName("Test Name");

        return identity;
    }

    private ContactDetails newTestContactDetails() {
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

    private VehicleClass newTestVehicleClass() {
        VehicleClass vc = new VehicleClass();

        vc.setCode("1");
        vc.setDescription("Test Description");
        vc.setVehicleType("Test Type");
        vc.setVehicleSize("55555");
        vc.setVehicleConfiguration("Test Configuration");
        vc.setEuVehicleCategory("ABC");

        return vc;
    }

    private TechnicalRecord newTestTechnicalRecord() {
        TechnicalRecord tr = new TechnicalRecord();

        tr.setVehicleID(String.valueOf(vehiclePK));
        tr.setRecordCompleteness("Complete");
        tr.setCreatedAt("2021-01-01 00:00:00");
        tr.setLastUpdatedAt("2021-01-01 00:00:00");
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
        tr.setFrontAxleTo5thWheelCouplingMin("45");
        tr.setFrontAxleTo5thWheelCouplingMax("65");
        tr.setFrontAxleToRearAxle("15");
        tr.setRearAxleToRearTrl("25");
        tr.setCouplingCenterToRearAxleMin("25");
        tr.setCouplingCenterToRearAxleMax("85");
        tr.setCouplingCenterToRearAxleMin("25");
        tr.setCouplingCenterToRearAxleMax("85");
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
}
