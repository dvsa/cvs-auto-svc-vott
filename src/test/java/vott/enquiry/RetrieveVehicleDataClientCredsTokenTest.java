package vott.enquiry;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.serenitybdd.junit.runners.SerenityRunner;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Title;
import net.thucydides.core.annotations.WithTag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import vott.api.VehicleDataAPI;
import vott.auth.GrantType;
import vott.auth.OAuthVersion;
import vott.auth.TokenService;
import vott.config.VottConfiguration;
import vott.database.*;
import vott.database.connection.ConnectionFactory;
import vott.database.seeddata.SeedData;
import vott.database.sqlgeneration.SqlGenerator;
import vott.json.GsonInstance;
import vott.models.dao.*;
import vott.models.dto.enquiry.TechnicalRecord;
import vott.models.dto.enquiry.Vehicle;
import vott.database.VehicleRepository;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.with;
import static org.junit.Assert.assertEquals;

@RunWith(SerenityRunner.class)
public class RetrieveVehicleDataClientCredsTokenTest {

    // Variable + Constant Test Data Setup
    private String token;

    private String validVINNumber = "";
    private String validVehicleRegMark = "";

    //Test Data Variables
    private Integer vehiclePK;
    private Integer makeModelPK;
    private Integer identityPK;
    private Integer contactDetailsPK;
    private Integer vehicleClassPK;
    private Integer technicalRecordPK;
    private Integer psvBrakesPK;
    private Integer tyrePK;
    private Integer axlesPK;
    private Integer platePK;
    private Integer axleSpacingPK;

    private TechnicalRecordRepository technicalRecordRepository;
    private VehicleRepository vehicleRepository;
    private MakeModelRepository makeModelRepository;
    private IdentityRepository identityRepository;
    private ContactDetailsRepository contactDetailsRepository;
    private VehicleClassRepository vehicleClassRepository;
    private TyreRepository tyreRepository;
    private PSVBrakesRepository psvBrakesRepository;
    private AxlesRepository axlesRepository;
    private PlateRepository plateRepository;
    private AxleSpacingRepository axleSpacingRepository;

    vott.models.dao.Vehicle vehicleUpsert = SeedData.newTestVehicle();
    MakeModel mm = SeedData.newTestMakeModel();
    VehicleClass vc = SeedData.newTestVehicleClass();
    ContactDetails cd = SeedData.newTestContactDetails();
    Identity identity = SeedData.newTestIdentity();
    Tyre tyre = SeedData.newTestTyre();
    vott.models.dao.TechnicalRecord tr;
    PSVBrakes psv;
    Axles axles;
    Plate plate;
    AxleSpacing as;

    @Before
    public void Setup() {

        this.token = new TokenService(OAuthVersion.V2, GrantType.CLIENT_CREDENTIALS).getBearerToken();

        //Connect to DB
        ConnectionFactory connectionFactory = new ConnectionFactory(VottConfiguration.local());

        vehicleRepository = new VehicleRepository(connectionFactory);
        vehiclePK = vehicleRepository.fullUpsert(vehicleUpsert);

        makeModelRepository = new MakeModelRepository(connectionFactory);
        makeModelPK = makeModelRepository.partialUpsert(mm);

        identityRepository = new IdentityRepository(connectionFactory);
        identityPK = identityRepository.partialUpsert(identity);

        contactDetailsRepository = new ContactDetailsRepository(connectionFactory);
        contactDetailsPK = contactDetailsRepository.partialUpsert(cd);

        vehicleClassRepository = new VehicleClassRepository(connectionFactory);
        vehicleClassPK = vehicleClassRepository.partialUpsert(vc);

        technicalRecordRepository = new TechnicalRecordRepository(connectionFactory);
        tr = SeedData.newTestTechnicalRecord(vehiclePK, makeModelPK, vehicleClassPK, contactDetailsPK, identityPK);
        technicalRecordPK = technicalRecordRepository.fullUpsert(tr);

        psvBrakesRepository = new PSVBrakesRepository(connectionFactory);
        psv = SeedData.newTestPSVBrakes(technicalRecordPK);
        psvBrakesPK = psvBrakesRepository.fullUpsert(psv);

        tyreRepository = new TyreRepository(connectionFactory);
        tyrePK = tyreRepository.partialUpsert(tyre);

        axlesRepository = new AxlesRepository(connectionFactory);
        axles = SeedData.newTestAxles(technicalRecordPK, tyrePK);
        axlesPK = axlesRepository.fullUpsert(axles);

        plateRepository = new PlateRepository(connectionFactory);
        plate = SeedData.newTestPlate(technicalRecordPK);
        platePK = plateRepository.fullUpsert(plate);

        axleSpacingRepository = new AxleSpacingRepository(connectionFactory);
        as = SeedData.newTestAxleSpacing(technicalRecordPK);
        axleSpacingPK = axleSpacingRepository.fullUpsert(as);

        validVINNumber = vehicleUpsert.getVin();
        validVehicleRegMark = vehicleUpsert.getVrm_trm();

        with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.vehicleIsPresentInDatabase(validVINNumber, vehicleRepository));
        with().timeout(Duration.ofSeconds(30)).await().until(SqlGenerator.techRecordIsPresentInDatabase(String.valueOf(vehiclePK), technicalRecordRepository));
    }

    @After
    public void tearDown() {
        //Test Data Cleanup
        axleSpacingRepository.delete(axleSpacingPK);
        plateRepository.delete(platePK);
        axlesRepository.delete(axlesPK);
        tyreRepository.delete(tyrePK);
        psvBrakesRepository.delete(psvBrakesPK);
        technicalRecordRepository.delete(technicalRecordPK);
        vehicleRepository.delete(vehiclePK);
        makeModelRepository.delete(makeModelPK);
        identityRepository.delete(identityPK);
        contactDetailsRepository.delete(contactDetailsPK);
        vehicleClassRepository.delete(vehicleClassPK);
    }

    @WithTag("Vott")
    @Title ("VOTT-9 - AC1 - TC11 - Happy Path - Retrieve Vehicle Data Using Vin Test With A Client Credentials Token")
    @Test
    public void RetrieveVehicleDataUsingVinTest() throws InterruptedException {

        int tries = 0;
        int maxRetries = 20;
        int statusCode;
        Response response;

        do {
            response = VehicleDataAPI.getVehicleDataUsingVIN(validVINNumber, token);
            statusCode = response.statusCode();
            tries++;
            Thread.sleep(1000);
        } while (statusCode >= 400 && tries < maxRetries);

        assertEquals(200, statusCode);

        Gson gson = GsonInstance.get();

        Vehicle vehicle = gson.fromJson(response.asString(), Vehicle.class);

        TechnicalRecord technicalRecord = vehicle.getTechnicalrecords().get(0);

        assertThat(vehicle.getVin()).isEqualTo(vehicleUpsert.getVin());
        assertThat(vehicle.getVrmTrm()).isEqualTo(vehicleUpsert.getVrm_trm());
        assertThat(vehicle.getTrailerId()).isEqualTo(vehicleUpsert.getTrailerID());
        assertThat(vehicle.getSystemNumber()).isEqualTo(vehicleUpsert.getSystemNumber());
        assertThat(technicalRecord.getNotes()).isEqualTo(tr.getNotes());
        assertThat(technicalRecord.getWidth()).isEqualTo(Integer.parseInt(tr.getWidth()));
        assertThat(technicalRecord.getHeight()).isEqualTo(Integer.parseInt(tr.getHeight()));
        assertThat(technicalRecord.getLength()).isEqualTo(Integer.parseInt(tr.getLength()));
        assertThat(technicalRecord.isOffRoad()).isEqualTo(true);
        assertThat(technicalRecord.getRemarks()).isEqualTo(tr.getRemarks());
        assertThat(technicalRecord.getCoifDate()).isEqualTo(tr.getCoifDate());
        assertThat(technicalRecord.getRegnDate()).isEqualTo(tr.getRegnDate());
        assertThat(technicalRecord.getBrakeCode()).isEqualTo(tr.getBrakeCode());
        assertThat(technicalRecord.getCreatedAt()).isEqualTo(tr.getCreatedAt());
        assertThat(technicalRecord.getDtpNumber()).isEqualTo(tr.getBrakes_dtpNumber());
        assertThat(technicalRecord.getMakeModel().getMake()).isEqualTo(mm.getMake());
        assertThat(technicalRecord.getMakeModel().getModel()).isEqualTo(mm.getModel());
//        assertThat(technicalRecord.getMakeModel().get(0).getDtpCode()).isEqualTo(null); // todo not asserting against this as not in scope
        assertThat(technicalRecord.getMakeModel().getBodyMake()).isEqualTo(mm.getBodyMake());
        assertThat(technicalRecord.getMakeModel().getBodyModel()).isEqualTo(mm.getBodyModel());
        assertThat(technicalRecord.getMakeModel().getChassisMake()).isEqualTo(mm.getChassisMake());
        assertThat(technicalRecord.getMakeModel().getBodyTypeCode()).isEqualTo(mm.getBodyTypeCode());
        assertThat(technicalRecord.getMakeModel().getChassisModel()).isEqualTo(mm.getChassisModel());
        assertThat(technicalRecord.getMakeModel().getModelLiteral()).isEqualTo(mm.getModelLiteral());
        assertThat(technicalRecord.getMakeModel().getBodyTypeDescription()).isEqualTo(mm.getBodyTypeDescription());
        assertThat(technicalRecord.getMakeModel().getFuelPropulsionSystem()).isEqualTo(mm.getFuelPropulsionSystem());
        assertThat(technicalRecord.getNoOfAxles()).isEqualTo(Integer.parseInt(tr.getNoOfAxles()));
        assertThat(technicalRecord.getNtaNumber()).isEqualTo(tr.getNtaNumber());
        assertThat(technicalRecord.getStatusCode()).isEqualTo(tr.getStatusCode());
//        assertThat(technicalRecord.getUpdateType()).isEqualTo("techRecordUpdate"); // todo not asserting against this as not in scope
        assertThat(technicalRecord.getTyreUseCode()).isEqualTo(tr.getTyreUseCode());
        assertThat(technicalRecord.getApprovalType()).isEqualTo(tr.getApprovalType());
        assertThat(technicalRecord.getCouplingType()).isEqualTo(tr.getCouplingType());
        assertThat(technicalRecord.getEuroStandard()).isEqualTo(tr.getEuroStandard());
        assertThat(technicalRecord.getFirstUseDate()).isEqualTo(tr.getFirstUseDate());
        assertThat(technicalRecord.getFunctionCode()).isEqualTo(tr.getFunctionCode());
        assertThat(technicalRecord.isRoadFriendly()).isEqualTo(true);
        assertThat(technicalRecord.getVehicleClass().getCode()).isEqualTo(vc.getCode());
        assertThat(technicalRecord.getVehicleClass().getDescription()).isEqualTo(vc.getDescription());
        assertThat(technicalRecord.getVehicleClass().getVehicleSize()).isEqualTo(vc.getVehicleSize());
        assertThat(technicalRecord.getVehicleClass().getVehicleType()).isEqualTo(vc.getVehicleType());
        assertThat(technicalRecord.getVehicleClass().getEuVehicleCategory()).isEqualTo(vc.getEuVehicleCategory());
        assertThat(technicalRecord.getVehicleClass().getVehicleConfiguration()).isEqualTo(vc.getVehicleConfiguration());
        assertThat(technicalRecord.getDispensations()).isEqualTo(tr.getDispensations());
        assertThat(technicalRecord.getGrossGbWeight()).isEqualTo(Integer.parseInt(tr.getGrossGbWeight()));
        assertThat(technicalRecord.getLastUpdatedAt()).isEqualTo(tr.getLastUpdatedAt());
        assertThat(technicalRecord.getTrainGbWeight()).isEqualTo(Integer.parseInt(tr.getTrainGbWeight()));
        assertThat(technicalRecord.getUnladenWeight()).isEqualTo(Integer.parseInt(tr.getUnladenWeight()));
        assertThat(technicalRecord.getVariantNumber()).isEqualTo(tr.getVariantNumber());
        assertThat(technicalRecord.getEmissionsLimit()).isEqualTo(tr.getEmissionsLimit());
        assertThat(technicalRecord.getGrossEecWeight()).isEqualTo(Integer.parseInt(tr.getGrossEecWeight()));
        assertThat(technicalRecord.getPurchaserNotes()).isEqualTo(tr.getPurchaserNotes());
        assertThat(technicalRecord.getSeatsLowerDeck()).isEqualTo(Integer.parseInt(tr.getSeatsLowerDeck()));
        assertThat(technicalRecord.getSeatsUpperDeck()).isEqualTo(Integer.parseInt(tr.getSeatsUpperDeck()));
        assertThat(technicalRecord.getSuspensionType()).isEqualTo(tr.getSuspensionType());
        assertThat(technicalRecord.isTachoExemptMrk()).isEqualTo(true);
        assertThat(technicalRecord.getTrainEecWeight()).isEqualTo(Integer.parseInt(tr.getTrainEecWeight()));
        assertThat(technicalRecord.getConversionRefNo()).isEqualTo(tr.getConversionRefNo());
        assertThat(technicalRecord.getGrossKerbWeight()).isEqualTo(Integer.parseInt(tr.getGrossKerbWeight()));
        assertThat(technicalRecord.getManufactureYear()).isEqualTo(tr.getManufactureYear());
        assertThat(technicalRecord.isSpeedLimiterMrk()).isEqualTo(true);
        assertThat(technicalRecord.isAlterationMarker()).isEqualTo(true);
        assertThat(technicalRecord.getCoifSerialNumber()).isEqualTo(tr.getCoifSerialNumber());
        assertThat(technicalRecord.getFrameDescription()).isEqualTo(tr.getFrameDescription());
        assertThat(technicalRecord.getGrossLadenWeight()).isEqualTo(Integer.parseInt(tr.getGrossLadenWeight()));
        assertThat(technicalRecord.isLoadSensingValve()).isEqualTo(true);
        assertThat(technicalRecord.getMaxTrainGbWeight()).isEqualTo(Integer.parseInt(tr.getMaxTrainGbWeight()));
        assertThat(technicalRecord.getSpeedRestriction()).isEqualTo(Integer.parseInt(tr.getSpeedRestriction()));
        assertThat(technicalRecord.getStandingCapacity()).isEqualTo(Integer.parseInt(tr.getStandingCapacity()));
        assertThat(technicalRecord.getCoifCertifierName()).isEqualTo(tr.getCoifCertifierName());
        assertThat(technicalRecord.getGrossDesignWeight()).isEqualTo(Integer.parseInt(tr.getGrossDesignWeight()));
        assertThat(technicalRecord.getManufacturerNotes()).isEqualTo(tr.getManufacturerNotes());
        assertThat(technicalRecord.getMaxLoadOnCoupling()).isEqualTo(Integer.parseInt(tr.getMaxLoadOnCoupling()));
        assertThat(technicalRecord.getMaxTrainEecWeight()).isEqualTo(Integer.parseInt(tr.getMaxTrainEecWeight()));
        assertThat(technicalRecord.getNumberOfSeatbelts()).isEqualTo(tr.getNumberOfSeatbelts());
        assertThat(technicalRecord.getRearAxleToRearTrl()).isEqualTo(Integer.parseInt(tr.getRearAxleToRearTrl()));
        assertThat(technicalRecord.getReasonForCreation()).isEqualTo(tr.getReasonForCreation());
        assertThat(technicalRecord.getTrainDesignWeight()).isEqualTo(Integer.parseInt(tr.getTrainDesignWeight()));
        assertThat(technicalRecord.getApprovalTypeNumber()).isEqualTo(tr.getApprovalTypeNumber());
//        assertThat(technicalRecord.getRecordCompleteness()).isEqualTo(null); // todo not asserting against this as not in scope
        assertThat(technicalRecord.getFrontAxleToRearAxle()).isEqualTo(Integer.parseInt(tr.getFrontAxleToRearAxle()));
        assertThat(technicalRecord.getMaxTrainDesignWeight()).isEqualTo(Integer.parseInt(tr.getMaxTrainDesignWeight()));
        assertThat(technicalRecord.getNumberOfWheelsDriven()).isEqualTo(Integer.parseInt(tr.getNumberOfWheelsDriven()));
        assertThat(technicalRecord.getVariantVersionNumber()).isEqualTo(tr.getVariantVersionNumber());
        assertThat(technicalRecord.isAntilockBrakingSystem()).isEqualTo(true);
        assertThat(technicalRecord.isDrawbarCouplingFitted()).isEqualTo(true);
        assertThat(technicalRecord.getFrontAxleTo5thWheelMax()).isEqualTo(Integer.parseInt(tr.getFrontAxleTo5thWheelMax()));
        assertThat(technicalRecord.getFrontAxleTo5thWheelMin()).isEqualTo(Integer.parseInt(tr.getFrontAxleTo5thWheelMin()));
        assertThat(technicalRecord.isDepartmentalVehicleMarker()).isEqualTo(true);
        assertThat(technicalRecord.getCouplingCenterToRearTrlMax()).isEqualTo(Integer.parseInt(tr.getCouplingCenterToRearTrlMax()));
        assertThat(technicalRecord.getCouplingCenterToRearTrlMin()).isEqualTo(Integer.parseInt(tr.getCouplingCenterToRearTrlMin()));
        assertThat(technicalRecord.getCouplingCenterToRearAxleMax()).isEqualTo(Integer.parseInt(tr.getCouplingCenterToRearAxleMax()));
        assertThat(technicalRecord.getCouplingCenterToRearAxleMin()).isEqualTo(Integer.parseInt(tr.getCouplingCenterToRearAxleMin()));
        assertThat(technicalRecord.getFrontVehicleTo5thWheelCouplingMax()).isEqualTo(Integer.parseInt(tr.getFrontVehicleTo5thWheelCouplingMax()));
        assertThat(technicalRecord.getFrontVehicleTo5thWheelCouplingMin()).isEqualTo(Integer.parseInt(tr.getFrontVehicleTo5thWheelCouplingMin()));
        assertThat(technicalRecord.getCentreOfRearmostAxleToRearOfTrl()).isEqualTo(Integer.parseInt(tr.getCentreOfRearmostAxleToRearOfTrl()));
        assertThat(technicalRecord.getSeatbeltInstallationApprovalDate()).isEqualTo(tr.getSeatbeltInstallationApprovalDate());
        assertThat(technicalRecord.getPsvBrakes().get(0).getBrakeCode()).isEqualTo(psv.getBrakeCode());
        assertThat(technicalRecord.getPsvBrakes().get(0).getDataTrBrakeOne()).isEqualTo(psv.getDataTrBrakeOne());
        assertThat(technicalRecord.getPsvBrakes().get(0).getDataTrBrakeTwo()).isEqualTo(psv.getDataTrBrakeTwo());
        assertThat(technicalRecord.getPsvBrakes().get(0).getDataTrBrakeThree()).isEqualTo(psv.getDataTrBrakeThree());
        assertThat(technicalRecord.getPsvBrakes().get(0).getRetarderBrakeOne()).isEqualTo(psv.getRetarderBrakeOne());
        assertThat(technicalRecord.getPsvBrakes().get(0).getRetarderBrakeTwo()).isEqualTo(psv.getRetarderBrakeTwo());
        assertThat(technicalRecord.getPsvBrakes().get(0).getBrakeCodeOriginal()).isEqualTo(psv.getBrakeCodeOriginal());
        assertThat(technicalRecord.getPsvBrakes().get(0).getParkingBrakeForceA()).isEqualTo(Integer.parseInt(psv.getParkingBrakeForceA()));
        assertThat(technicalRecord.getPsvBrakes().get(0).getParkingBrakeForceB()).isEqualTo(Integer.parseInt(psv.getParkingBrakeForceB()));
        assertThat(technicalRecord.getPsvBrakes().get(0).getServiceBrakeForceA()).isEqualTo(Integer.parseInt(psv.getServiceBrakeForceA()));
        assertThat(technicalRecord.getPsvBrakes().get(0).getServiceBrakeForceB()).isEqualTo(Integer.parseInt(psv.getServiceBrakeForceB()));
        assertThat(technicalRecord.getPsvBrakes().get(0).getSecondaryBrakeForceA()).isEqualTo(Integer.parseInt(psv.getSecondaryBrakeForceA()));
        assertThat(technicalRecord.getPsvBrakes().get(0).getSecondaryBrakeForceB()).isEqualTo(Integer.parseInt(psv.getSecondaryBrakeForceB()));
        assertThat(technicalRecord.getAxles().get(0).getTyre().getTyreCode()).isEqualTo(Integer.parseInt(tyre.getTyreCode()));
        assertThat(technicalRecord.getAxles().get(0).getTyre().getTyreSize()).isEqualTo(tyre.getTyreSize());
        assertThat(technicalRecord.getAxles().get(0).getTyre().getPlyRating()).isEqualTo(tyre.getPlyRating());
        assertThat(technicalRecord.getAxles().get(0).getTyre().getDataTrAxles()).isEqualTo(tyre.getDataTrAxles());
        assertThat(technicalRecord.getAxles().get(0).getTyre().getFitmentCode()).isEqualTo(tyre.getFitmentCode());
        assertThat(technicalRecord.getAxles().get(0).getTyre().getSpeedCategorySymbol()).isEqualTo(tyre.getSpeedCategorySymbol());
        assertThat(technicalRecord.getAxles().get(0).getGbWeight()).isEqualTo(axles.getGbWeight());
        assertThat(technicalRecord.getAxles().get(0).getEecWeight()).isEqualTo(axles.getEecWeight());
        assertThat(technicalRecord.getAxles().get(0).getAxleNumber()).isEqualTo(axles.getAxleNumber());
        assertThat(technicalRecord.getAxles().get(0).getKerbWeight()).isEqualTo(axles.getKerbWeight());
        assertThat(technicalRecord.getAxles().get(0).getLadenWeight()).isEqualTo(axles.getLadenWeight());
        assertThat(technicalRecord.getAxles().get(0).getLeverLength()).isEqualTo(axles.getLeverLength());
        assertThat(technicalRecord.getAxles().get(0).getDesignWeight()).isEqualTo(axles.getDesignWeight());
        assertThat(technicalRecord.getAxles().get(0).getBrakeActuator()).isEqualTo(axles.getBrakeActuator());
        assertThat(technicalRecord.getAxles().get(0).isParkingBrakeMrk()).isEqualTo(true);
        assertThat(technicalRecord.getAxles().get(0).isSpringBrakeParking()).isEqualTo(true);
        assertThat(technicalRecord.getAxlespacing().get(0).getAxles()).isEqualTo(as.getAxles());
        assertThat(technicalRecord.getAxlespacing().get(0).getValue()).isEqualTo(Integer.parseInt(as.getValue()));
        assertThat(technicalRecord.getPlates().get(0).getPlateIssuer()).isEqualTo(plate.getPlateIssuer());
        assertThat(technicalRecord.getPlates().get(0).getPlateIssueDate()).isEqualTo(plate.getPlateIssueDate());
        assertThat(technicalRecord.getPlates().get(0).getPlateSerialNumber()).isEqualTo(plate.getPlateSerialNumber());
        assertThat(technicalRecord.getPlates().get(0).getPlateReasonForIssue()).isEqualTo(plate.getPlateReasonForIssue());
    }

    @WithTag("Vott")
    @Title("VOTT-9 - AC1 - TC12 - Happy Path - RetrieveVehicleDataUsingVrmTest")
    @Test
    public void RetrieveVehicleDataUsingVrmTest() throws InterruptedException {

        int tries = 0;
        int maxRetries = 20;
        int statusCode;
        Response response;

        do {
            response = VehicleDataAPI.getVehicleDataUsingVRM(validVehicleRegMark, token);
            statusCode = response.statusCode();
            tries++;
            Thread.sleep(1000);
        } while (statusCode >= 400 && tries < maxRetries);

        assertEquals(200, statusCode);

        Gson gson = new GsonBuilder().create();

        Vehicle vehicle = gson.fromJson(response.asString(), Vehicle.class);

        TechnicalRecord technicalRecord = vehicle.getTechnicalrecords().get(0);

        assertThat(vehicle.getVin()).isEqualTo(vehicleUpsert.getVin());
        assertThat(vehicle.getVrmTrm()).isEqualTo(vehicleUpsert.getVrm_trm());
        assertThat(vehicle.getTrailerId()).isEqualTo(vehicleUpsert.getTrailerID());
        assertThat(vehicle.getSystemNumber()).isEqualTo(vehicleUpsert.getSystemNumber());
        assertThat(technicalRecord.getNotes()).isEqualTo(tr.getNotes());
        assertThat(technicalRecord.getWidth()).isEqualTo(Integer.parseInt(tr.getWidth()));
        assertThat(technicalRecord.getHeight()).isEqualTo(Integer.parseInt(tr.getHeight()));
        assertThat(technicalRecord.getLength()).isEqualTo(Integer.parseInt(tr.getLength()));
        assertThat(technicalRecord.isOffRoad()).isEqualTo(true);
        assertThat(technicalRecord.getRemarks()).isEqualTo(tr.getRemarks());
        assertThat(technicalRecord.getCoifDate()).isEqualTo(tr.getCoifDate());
        assertThat(technicalRecord.getRegnDate()).isEqualTo(tr.getRegnDate());
        assertThat(technicalRecord.getBrakeCode()).isEqualTo(tr.getBrakeCode());
        assertThat(technicalRecord.getCreatedAt()).isEqualTo(tr.getCreatedAt());
        assertThat(technicalRecord.getDtpNumber()).isEqualTo(tr.getBrakes_dtpNumber());
        assertThat(technicalRecord.getMakeModel().getMake()).isEqualTo(mm.getMake());
        assertThat(technicalRecord.getMakeModel().getModel()).isEqualTo(mm.getModel());
//        assertThat(technicalRecord.getMakeModel().get(0).getDtpCode()).isEqualTo(null); // todo not asserting against this as not in scope
        assertThat(technicalRecord.getMakeModel().getBodyMake()).isEqualTo(mm.getBodyMake());
        assertThat(technicalRecord.getMakeModel().getBodyModel()).isEqualTo(mm.getBodyModel());
        assertThat(technicalRecord.getMakeModel().getChassisMake()).isEqualTo(mm.getChassisMake());
        assertThat(technicalRecord.getMakeModel().getBodyTypeCode()).isEqualTo(mm.getBodyTypeCode());
        assertThat(technicalRecord.getMakeModel().getChassisModel()).isEqualTo(mm.getChassisModel());
        assertThat(technicalRecord.getMakeModel().getModelLiteral()).isEqualTo(mm.getModelLiteral());
        assertThat(technicalRecord.getMakeModel().getBodyTypeDescription()).isEqualTo(mm.getBodyTypeDescription());
        assertThat(technicalRecord.getMakeModel().getFuelPropulsionSystem()).isEqualTo(mm.getFuelPropulsionSystem());
        assertThat(technicalRecord.getNoOfAxles()).isEqualTo(Integer.parseInt(tr.getNoOfAxles()));
        assertThat(technicalRecord.getNtaNumber()).isEqualTo(tr.getNtaNumber());
        assertThat(technicalRecord.getStatusCode()).isEqualTo(tr.getStatusCode());
//        assertThat(technicalRecord.getUpdateType()).isEqualTo("techRecordUpdate"); // todo not asserting against this as not in scope
        assertThat(technicalRecord.getTyreUseCode()).isEqualTo(tr.getTyreUseCode());
        assertThat(technicalRecord.getApprovalType()).isEqualTo(tr.getApprovalType());
        assertThat(technicalRecord.getCouplingType()).isEqualTo(tr.getCouplingType());
        assertThat(technicalRecord.getEuroStandard()).isEqualTo(tr.getEuroStandard());
        assertThat(technicalRecord.getFirstUseDate()).isEqualTo(tr.getFirstUseDate());
        assertThat(technicalRecord.getFunctionCode()).isEqualTo(tr.getFunctionCode());
        assertThat(technicalRecord.isRoadFriendly()).isEqualTo(true);
        assertThat(technicalRecord.getVehicleClass().getCode()).isEqualTo(vc.getCode());
        assertThat(technicalRecord.getVehicleClass().getDescription()).isEqualTo(vc.getDescription());
        assertThat(technicalRecord.getVehicleClass().getVehicleSize()).isEqualTo(vc.getVehicleSize());
        assertThat(technicalRecord.getVehicleClass().getVehicleType()).isEqualTo(vc.getVehicleType());
        assertThat(technicalRecord.getVehicleClass().getEuVehicleCategory()).isEqualTo(vc.getEuVehicleCategory());
        assertThat(technicalRecord.getVehicleClass().getVehicleConfiguration()).isEqualTo(vc.getVehicleConfiguration());
        assertThat(technicalRecord.getDispensations()).isEqualTo(tr.getDispensations());
        assertThat(technicalRecord.getGrossGbWeight()).isEqualTo(Integer.parseInt(tr.getGrossGbWeight()));
        assertThat(technicalRecord.getLastUpdatedAt()).isEqualTo(tr.getLastUpdatedAt());
        assertThat(technicalRecord.getTrainGbWeight()).isEqualTo(Integer.parseInt(tr.getTrainGbWeight()));
        assertThat(technicalRecord.getUnladenWeight()).isEqualTo(Integer.parseInt(tr.getUnladenWeight()));
        assertThat(technicalRecord.getVariantNumber()).isEqualTo(tr.getVariantNumber());
        assertThat(technicalRecord.getEmissionsLimit()).isEqualTo(tr.getEmissionsLimit());
        assertThat(technicalRecord.getGrossEecWeight()).isEqualTo(Integer.parseInt(tr.getGrossEecWeight()));
        assertThat(technicalRecord.getPurchaserNotes()).isEqualTo(tr.getPurchaserNotes());
        assertThat(technicalRecord.getSeatsLowerDeck()).isEqualTo(Integer.parseInt(tr.getSeatsLowerDeck()));
        assertThat(technicalRecord.getSeatsUpperDeck()).isEqualTo(Integer.parseInt(tr.getSeatsUpperDeck()));
        assertThat(technicalRecord.getSuspensionType()).isEqualTo(tr.getSuspensionType());
        assertThat(technicalRecord.isTachoExemptMrk()).isEqualTo(true);
        assertThat(technicalRecord.getTrainEecWeight()).isEqualTo(Integer.parseInt(tr.getTrainEecWeight()));
        assertThat(technicalRecord.getConversionRefNo()).isEqualTo(tr.getConversionRefNo());
        assertThat(technicalRecord.getGrossKerbWeight()).isEqualTo(Integer.parseInt(tr.getGrossKerbWeight()));
        assertThat(technicalRecord.getManufactureYear()).isEqualTo(tr.getManufactureYear());
        assertThat(technicalRecord.isSpeedLimiterMrk()).isEqualTo(true);
        assertThat(technicalRecord.isAlterationMarker()).isEqualTo(true);
        assertThat(technicalRecord.getCoifSerialNumber()).isEqualTo(tr.getCoifSerialNumber());
        assertThat(technicalRecord.getFrameDescription()).isEqualTo(tr.getFrameDescription());
        assertThat(technicalRecord.getGrossLadenWeight()).isEqualTo(Integer.parseInt(tr.getGrossLadenWeight()));
        assertThat(technicalRecord.isLoadSensingValve()).isEqualTo(true);
        assertThat(technicalRecord.getMaxTrainGbWeight()).isEqualTo(Integer.parseInt(tr.getMaxTrainGbWeight()));
        assertThat(technicalRecord.getSpeedRestriction()).isEqualTo(Integer.parseInt(tr.getSpeedRestriction()));
        assertThat(technicalRecord.getStandingCapacity()).isEqualTo(Integer.parseInt(tr.getStandingCapacity()));
        assertThat(technicalRecord.getCoifCertifierName()).isEqualTo(tr.getCoifCertifierName());
        assertThat(technicalRecord.getGrossDesignWeight()).isEqualTo(Integer.parseInt(tr.getGrossDesignWeight()));
        assertThat(technicalRecord.getManufacturerNotes()).isEqualTo(tr.getManufacturerNotes());
        assertThat(technicalRecord.getMaxLoadOnCoupling()).isEqualTo(Integer.parseInt(tr.getMaxLoadOnCoupling()));
        assertThat(technicalRecord.getMaxTrainEecWeight()).isEqualTo(Integer.parseInt(tr.getMaxTrainEecWeight()));
        assertThat(technicalRecord.getNumberOfSeatbelts()).isEqualTo(tr.getNumberOfSeatbelts());
        assertThat(technicalRecord.getRearAxleToRearTrl()).isEqualTo(Integer.parseInt(tr.getRearAxleToRearTrl()));
        assertThat(technicalRecord.getReasonForCreation()).isEqualTo(tr.getReasonForCreation());
        assertThat(technicalRecord.getTrainDesignWeight()).isEqualTo(Integer.parseInt(tr.getTrainDesignWeight()));
        assertThat(technicalRecord.getApprovalTypeNumber()).isEqualTo(tr.getApprovalTypeNumber());
//        assertThat(technicalRecord.getRecordCompleteness()).isEqualTo(null); // todo not asserting against this as not in scope
        assertThat(technicalRecord.getFrontAxleToRearAxle()).isEqualTo(Integer.parseInt(tr.getFrontAxleToRearAxle()));
        assertThat(technicalRecord.getMaxTrainDesignWeight()).isEqualTo(Integer.parseInt(tr.getMaxTrainDesignWeight()));
        assertThat(technicalRecord.getNumberOfWheelsDriven()).isEqualTo(Integer.parseInt(tr.getNumberOfWheelsDriven()));
        assertThat(technicalRecord.getVariantVersionNumber()).isEqualTo(tr.getVariantVersionNumber());
        assertThat(technicalRecord.isAntilockBrakingSystem()).isEqualTo(true);
        assertThat(technicalRecord.isDrawbarCouplingFitted()).isEqualTo(true);
        assertThat(technicalRecord.getFrontAxleTo5thWheelMax()).isEqualTo(Integer.parseInt(tr.getFrontAxleTo5thWheelMax()));
        assertThat(technicalRecord.getFrontAxleTo5thWheelMin()).isEqualTo(Integer.parseInt(tr.getFrontAxleTo5thWheelMin()));
        assertThat(technicalRecord.isDepartmentalVehicleMarker()).isEqualTo(true);
        assertThat(technicalRecord.getCouplingCenterToRearTrlMax()).isEqualTo(Integer.parseInt(tr.getCouplingCenterToRearTrlMax()));
        assertThat(technicalRecord.getCouplingCenterToRearTrlMin()).isEqualTo(Integer.parseInt(tr.getCouplingCenterToRearTrlMin()));
        assertThat(technicalRecord.getCouplingCenterToRearAxleMax()).isEqualTo(Integer.parseInt(tr.getCouplingCenterToRearAxleMax()));
        assertThat(technicalRecord.getCouplingCenterToRearAxleMin()).isEqualTo(Integer.parseInt(tr.getCouplingCenterToRearAxleMin()));
        assertThat(technicalRecord.getFrontVehicleTo5thWheelCouplingMax()).isEqualTo(Integer.parseInt(tr.getFrontVehicleTo5thWheelCouplingMax()));
        assertThat(technicalRecord.getFrontVehicleTo5thWheelCouplingMin()).isEqualTo(Integer.parseInt(tr.getFrontVehicleTo5thWheelCouplingMin()));
        assertThat(technicalRecord.getCentreOfRearmostAxleToRearOfTrl()).isEqualTo(Integer.parseInt(tr.getCentreOfRearmostAxleToRearOfTrl()));
        assertThat(technicalRecord.getSeatbeltInstallationApprovalDate()).isEqualTo(tr.getSeatbeltInstallationApprovalDate());
        assertThat(technicalRecord.getPsvBrakes().get(0).getBrakeCode()).isEqualTo(psv.getBrakeCode());
        assertThat(technicalRecord.getPsvBrakes().get(0).getDataTrBrakeOne()).isEqualTo(psv.getDataTrBrakeOne());
        assertThat(technicalRecord.getPsvBrakes().get(0).getDataTrBrakeTwo()).isEqualTo(psv.getDataTrBrakeTwo());
        assertThat(technicalRecord.getPsvBrakes().get(0).getDataTrBrakeThree()).isEqualTo(psv.getDataTrBrakeThree());
        assertThat(technicalRecord.getPsvBrakes().get(0).getRetarderBrakeOne()).isEqualTo(psv.getRetarderBrakeOne());
        assertThat(technicalRecord.getPsvBrakes().get(0).getRetarderBrakeTwo()).isEqualTo(psv.getRetarderBrakeTwo());
        assertThat(technicalRecord.getPsvBrakes().get(0).getBrakeCodeOriginal()).isEqualTo(psv.getBrakeCodeOriginal());
        assertThat(technicalRecord.getPsvBrakes().get(0).getParkingBrakeForceA()).isEqualTo(Integer.parseInt(psv.getParkingBrakeForceA()));
        assertThat(technicalRecord.getPsvBrakes().get(0).getParkingBrakeForceB()).isEqualTo(Integer.parseInt(psv.getParkingBrakeForceB()));
        assertThat(technicalRecord.getPsvBrakes().get(0).getServiceBrakeForceA()).isEqualTo(Integer.parseInt(psv.getServiceBrakeForceA()));
        assertThat(technicalRecord.getPsvBrakes().get(0).getServiceBrakeForceB()).isEqualTo(Integer.parseInt(psv.getServiceBrakeForceB()));
        assertThat(technicalRecord.getPsvBrakes().get(0).getSecondaryBrakeForceA()).isEqualTo(Integer.parseInt(psv.getSecondaryBrakeForceA()));
        assertThat(technicalRecord.getPsvBrakes().get(0).getSecondaryBrakeForceB()).isEqualTo(Integer.parseInt(psv.getSecondaryBrakeForceB()));
        assertThat(technicalRecord.getAxles().get(0).getTyre().getTyreCode()).isEqualTo(Integer.parseInt(tyre.getTyreCode()));
        assertThat(technicalRecord.getAxles().get(0).getTyre().getTyreSize()).isEqualTo(tyre.getTyreSize());
        assertThat(technicalRecord.getAxles().get(0).getTyre().getPlyRating()).isEqualTo(tyre.getPlyRating());
        assertThat(technicalRecord.getAxles().get(0).getTyre().getDataTrAxles()).isEqualTo(tyre.getDataTrAxles());
        assertThat(technicalRecord.getAxles().get(0).getTyre().getFitmentCode()).isEqualTo(tyre.getFitmentCode());
        assertThat(technicalRecord.getAxles().get(0).getTyre().getSpeedCategorySymbol()).isEqualTo(tyre.getSpeedCategorySymbol());
        assertThat(technicalRecord.getAxles().get(0).getGbWeight()).isEqualTo(axles.getGbWeight());
        assertThat(technicalRecord.getAxles().get(0).getEecWeight()).isEqualTo(axles.getEecWeight());
        assertThat(technicalRecord.getAxles().get(0).getAxleNumber()).isEqualTo(axles.getAxleNumber());
        assertThat(technicalRecord.getAxles().get(0).getKerbWeight()).isEqualTo(axles.getKerbWeight());
        assertThat(technicalRecord.getAxles().get(0).getLadenWeight()).isEqualTo(axles.getLadenWeight());
        assertThat(technicalRecord.getAxles().get(0).getLeverLength()).isEqualTo(axles.getLeverLength());
        assertThat(technicalRecord.getAxles().get(0).getDesignWeight()).isEqualTo(axles.getDesignWeight());
        assertThat(technicalRecord.getAxles().get(0).getBrakeActuator()).isEqualTo(axles.getBrakeActuator());
        assertThat(technicalRecord.getAxles().get(0).isParkingBrakeMrk()).isEqualTo(true);
        assertThat(technicalRecord.getAxles().get(0).isSpringBrakeParking()).isEqualTo(true);
        assertThat(technicalRecord.getAxlespacing().get(0).getAxles()).isEqualTo(as.getAxles());
        assertThat(technicalRecord.getAxlespacing().get(0).getValue()).isEqualTo(Integer.parseInt(as.getValue()));
        assertThat(technicalRecord.getPlates().get(0).getPlateIssuer()).isEqualTo(plate.getPlateIssuer());
        assertThat(technicalRecord.getPlates().get(0).getPlateIssueDate()).isEqualTo(plate.getPlateIssueDate());
        assertThat(technicalRecord.getPlates().get(0).getPlateSerialNumber()).isEqualTo(plate.getPlateSerialNumber());
        assertThat(technicalRecord.getPlates().get(0).getPlateReasonForIssue()).isEqualTo(plate.getPlateReasonForIssue());
    }

    @WithTag("Vott")
    @Title("VOTT-9 - AC1 - TC13 - RetrieveVehicleDataBadJwtTokenTest")
    @Test
    public void RetrieveVehicleDataBadJwtTokenTest() {
        Response response = VehicleDataAPI.getVehicleDataUsingVIN(validVINNumber, token+1);
        assertEquals(403, response.statusCode());
    }

    @WithTag("Vott")
    @Title("VOTT-9 - AC1 - TC14 - RetrieveVehicleDataNoParamsTest")
    @Test
    public void RetrieveVehicleDataNoParamsTest() {
        Response response = VehicleDataAPI.getVehicleDataNoParams(token);
        assertEquals(400, response.statusCode());
        assertEquals("No parameter defined", response.asString());
    }

    @WithTag("Vott")
    @Title("VOTT-9 - AC1 - TC15 - RetrieveVehicleDataBothVinAndVrmTest")
    @Test
    public void RetrieveVehicleDataBothVinAndVrmTest() {
        Response response = VehicleDataAPI.getVehicleDataUsingVIN_VRM(validVINNumber, validVehicleRegMark, token);
        assertEquals(400, response.statusCode());
        assertEquals("Too many parameters defined", response.asString());
    }

    @WithTag("Vott")
    @Title("VOTT-9 - AC1 - TC16 RetrieveVehicleDataNoAPIKeyTest")
    @Test
    public void RetrieveVehicleDataNoAPIKeyTest() {
        Response response = VehicleDataAPI.getVehicleDataNoAPIKey(validVINNumber, token);
        assertEquals(403, response.statusCode());
    }

    @WithTag("Vott")
    @Title("VOTT-9 - AC1 - TC17 - RetrieveVehicleDataInvalidAPIKey")
    @Test
    public void RetrieveVehicleDataInvalidAPIKey() {
        Response response = VehicleDataAPI.getVehicleDataInvalidAPIKey(validVINNumber, token);
        assertEquals(403, response.statusCode());
    }

    @WithTag("Vott")
    @Title("VOTT-9 - AC1 - TC18 - RetrieveVehicleDataVehicleRegMarkDoesntExistTest")
    @Test
    public void RetrieveVehicleDataVehicleRegMarkDoesntExistTest() {
        String invalidVehicleRegMark = "W01A00229";
        Response response = VehicleDataAPI.getVehicleDataUsingVRM(invalidVehicleRegMark, token);
        assertEquals(404, response.statusCode());
        assertEquals("Vehicle was not found", response.asString());
    }

    @WithTag("Vott")
    @Title("VOTT-9 - AC1 - TC19 - RetrieveVehicleDataVinNumberDoesntExistTest")
    @Test
    public void RetrieveVehicleDataVinNumberDoesntExistTest() {
        String invalidVINNumber = "A123456789";
        Response response = VehicleDataAPI.getVehicleDataUsingVIN(invalidVINNumber, token);
        assertEquals(404, response.statusCode());
        assertEquals("Vehicle was not found", response.asString());
    }

    @WithTag("Vott")
    @Title("VOTT-9 - AC1 - TC20 - RetrieveVehicleDataNonPrintableCharsParamsTest")
    @Test
    public void RetrieveVehicleDataNonPrintableCharsParamsTest() {
        String nonAlphaVehicleMark = "!@/'";
        Response response = VehicleDataAPI.getVehicleDataUsingVRM(nonAlphaVehicleMark, token);
        assertEquals(500, response.statusCode());
        assertEquals("Vehicle identifier is invalid", response.asString());
    }
}
