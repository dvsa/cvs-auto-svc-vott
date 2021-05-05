package vott.testhistory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.junit.runners.SerenityRunner;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Title;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import vott.auth.GrantType;
import vott.auth.OAuthVersion;
import vott.auth.TokenService;
import vott.config.VottConfiguration;
import vott.database.*;
import vott.database.connection.ConnectionFactory;
import vott.json.GsonInstance;
import vott.models.dao.*;
import vott.models.dto.enquiry.TechnicalRecord;
import vott.models.dto.enquiry.Vehicle;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.Callable;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static vott.e2e.RestAssuredAuthenticated.givenAuth;

@RunWith(SerenityRunner.class)
public class RetrieveVehicleDataImplicitTokenTest {

    // Variable + Constant Test Data Setup
    private VottConfiguration configuration = VottConfiguration.local();
    private String token;
    private final String xApiKey = configuration.getApiKeys().getEnquiryServiceApiKey();
    private String validVINNumber = "";
    private String validVehicleRegMark = "";
    private String nonAlphaVehicleMark = "!@/'";

    private final String invalidVINNumber = "A123456789";
    private final String invalidVehicleRegMark = "W01A00229";

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

    vott.models.dao.Vehicle vehicleUpsert = newTestVehicle();
    MakeModel mm = newTestMakeModel();
    VehicleClass vc = newTestVehicleClass();
    ContactDetails cd = newTestContactDetails();
    Identity identity = newTestIdentity();
    vott.models.dao.TechnicalRecord tr;
    Tyre tyre = newTestTyre();
    PSVBrakes psv;
    Axles axles;
    Plate plate;
    AxleSpacing as;

    @Before
    public void Setup() {
        RestAssured.baseURI = configuration.getApiProperties().getBranchSpecificUrl() + "/v1/enquiry/vehicle";
        this.token = new TokenService(OAuthVersion.V1, GrantType.IMPLICIT).getBearerToken();

        //Connect to DB
        ConnectionFactory connectionFactory = new ConnectionFactory(
                VottConfiguration.local()
        );

        vehicleRepository = new VehicleRepository(connectionFactory);
        vehiclePK = vehicleRepository.fullUpsert(newTestVehicle());

        makeModelRepository = new MakeModelRepository(connectionFactory);
        makeModelPK = makeModelRepository.partialUpsert(mm);

        identityRepository = new IdentityRepository(connectionFactory);
        identityPK = identityRepository.partialUpsert(identity);

        contactDetailsRepository = new ContactDetailsRepository(connectionFactory);
        contactDetailsPK = contactDetailsRepository.partialUpsert(cd);

        vehicleClassRepository = new VehicleClassRepository(connectionFactory);
        vehicleClassPK = vehicleClassRepository.partialUpsert(vc);

        technicalRecordRepository = new TechnicalRecordRepository(connectionFactory);
        tr = newTestTechnicalRecord();
        technicalRecordPK = technicalRecordRepository.fullUpsert(tr);

        psvBrakesRepository = new PSVBrakesRepository(connectionFactory);
        psv = newTestPSVBrakes();
        psvBrakesPK = psvBrakesRepository.fullUpsert(psv);

        tyreRepository = new TyreRepository(connectionFactory);
        tyrePK = tyreRepository.partialUpsert(tyre);

        axlesRepository = new AxlesRepository(connectionFactory);
        axles = newTestAxles();
        axlesPK = axlesRepository.fullUpsert(axles);

        plateRepository = new PlateRepository(connectionFactory);
        plate = newTestPlate();
        platePK = plateRepository.fullUpsert(plate);

        axleSpacingRepository = new AxleSpacingRepository(connectionFactory);
        as = newTestAxleSpacing();
        axleSpacingPK = axleSpacingRepository.fullUpsert(as);

        validVINNumber = vehicleUpsert.getVin();
        validVehicleRegMark = vehicleUpsert.getVrm_trm();

        with().timeout(Duration.ofSeconds(30)).await().until(vehicleIsPresentInDatabase(validVINNumber));
        with().timeout(Duration.ofSeconds(30)).await().until(techRecordIsPresentInDatabase(String.valueOf(vehiclePK)));
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

    @Title ("VOTT-9 - AC1 - TC1 - Happy Path - Retrieve Vehicle Data Using Client Creds token and a valid vin")
    @Test
    public void RetrieveVehicleDataAndTestHistoryUsingVinTest() throws InterruptedException {

        int tries = 0;
        int maxRetries = 20;
        int statusCode;
        Response response;

        //Retrieve and save test certificate (pdf) as byteArray
        do {
            response =
                    givenAuth(token, xApiKey)
                            .header("content-type", "application/json")
                            .queryParam("vinNumber", validVINNumber).

                            //send request
                                    when().//log().all().
                            get().

                            //verification
                                    then().//log().all().
                            extract().response();
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
        assertThat(technicalRecord.getFrontAxleTo5thWheelCouplingMax()).isEqualTo(Integer.parseInt(tr.getFrontAxleTo5thWheelCouplingMax()));
        assertThat(technicalRecord.getFrontAxleTo5thWheelCouplingMin()).isEqualTo(Integer.parseInt(tr.getFrontAxleTo5thWheelCouplingMin()));
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

    @Title("VOTT-9 - AC1 - TC2 - Happy Path - Retrieve Vehicle Data Using Client Creds token and a valid vrm")
    @Test
    public void RetrieveVehicleDataAndTestHistoryUsingVrmTest() {

        String response =
                givenAuth(token, xApiKey)
                        .header("content-type", "application/json")
                        .queryParam("VehicleRegMark", validVehicleRegMark).

                //send request
                when().//log().all().
                        get().

                //verification
                then().//log().all().
                        statusCode(200).
                        extract().response().asString();

        Gson gson = GsonInstance.get();

        Vehicle vehicle = gson.fromJson(response, Vehicle.class);

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
        assertThat(technicalRecord.getFrontAxleTo5thWheelCouplingMax()).isEqualTo(Integer.parseInt(tr.getFrontAxleTo5thWheelCouplingMax()));
        assertThat(technicalRecord.getFrontAxleTo5thWheelCouplingMin()).isEqualTo(Integer.parseInt(tr.getFrontAxleTo5thWheelCouplingMin()));
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

    @Title("VOTT-9 - AC1 - TC3 - Retrieve Vehicle Data Using a bad client creds JWT Token")
    @Test
    public void RetrieveVehicleDataAndTestHistoryBadJwtTokenTest() {

        //prep request
        givenAuth(token + 1, xApiKey)
            .header("content-type", "application/json")
            .queryParam("VehicleRegMark", validVehicleRegMark).

        //send request
        when().//log().all().
            get().

        //verification
        then().//log().all().
            statusCode(403).
            body("message", equalTo("User is not authorized to access this resource with an explicit deny"));
    }

    @Title("VOTT-9 - AC1 - TC4 - Retrieve Vehicle Data Using an implicit JWT Token and no query params")
    @Test
    public void RetrieveVehicleDataAndTestHistoryNoParamsTest() {

        //prep request
        givenAuth(token, xApiKey)
            .header("content-type", "application/json").

        //send request
        when().//log().all().
            get().

        //verification
        then().//log().all().
            statusCode(400).
            body(equalTo("No parameter defined"));
    }

    @Title("VOTT-9 - AC1 - TC5 - Retrieve Vehicle Data Using an implicit JWT Token and both vin and vrm as query params")
    @Test
    public void RetrieveVehicleDataAndTestHistoryBothVinAndVrmTest() {

        //prep request
        givenAuth(token, xApiKey)
                .header("content-type", "application/json")
                .queryParam("vinNumber", validVINNumber)
                .queryParam("VehicleRegMark", validVehicleRegMark).

                //send request
                        when().//log().all().
                get().

                //verification
                        then().//log().all().
                statusCode(400).
                body(equalTo("Too many parameters defined"));
    }

    @Title("VOTT-9 - AC1 - TC6 - Retrieve Vehicle Data Using an implicit JWT Token and no api key")
    @Test
    public void RetrieveVehicleDataAndTestHistoryNoAPIKeyTest() {

        //prep request
        givenAuth(token)
            .header("content-type", "application/json")
            .queryParam("vinNumber", validVINNumber).

        //send request
        when().//log().all().
            get().

        //verification
        then().//log().all().
            statusCode(403).
            body("message", equalTo("Forbidden"));
    }

    @Title("VOTT-9 - AC1 - TC7 - Retrieve Vehicle Data Using an implicit JWT Token and an invalid api key")
    @Test
    public void RetrieveVehicleDataAndTestHistoryInvalidAPIKey() {

        //prep request
        givenAuth(token, xApiKey + "badkey")
            .header("content-type", "application/json")
            .queryParam("vinNumber", validVINNumber).

        //send request
        when().//log().all().
            get().

        //verification
            then().//log().all().
            statusCode(403).
            body("message", equalTo("Forbidden"));
    }

    @Title("VOTT-9 - AC1 - TC8 - Retrieve Vehicle Data Using an implicit JWT Token and vrm that doesn't exist in db")
    @Test
    public void RetrieveVehicleDataAndTestHistoryVehicleRegMarkDoesntExistTest() {

        //prep request
        givenAuth(token, xApiKey)
            .header("content-type", "application/json")
            .queryParam("VehicleRegMark", invalidVehicleRegMark).

        //send request
        when().//log().all().
            get().

        //verification
        then().//log().all().
            statusCode(404).
            body(equalTo("Vehicle was not found"));
    }

    @Title("VOTT-9 - AC1 - TC9 - Retrieve Vehicle Data Using an implicit JWT Token and vin that doesn't exist in db")
    @Test
    public void RetrieveVehicleDataAndTestHistoryVinNumberDoesntExistTest() {

        //prep request
        givenAuth(token, xApiKey)
            .header("content-type", "application/json")
            .queryParam("vinNumber", invalidVINNumber).

        //send request
        when().//log().all().
            get().

        //verification
        then().//log().all().
            statusCode(404).
            body(equalTo("Vehicle was not found"));
    }

    @Title("VOTT-9 - AC1 - TC10 - Retrieve Vehicle Data Using an implicit JWT Token and non alpha numeric vrm")
    @Test
    public void RetrieveVehicleDataAndTestHistoryNonPrintableCharsParamsTest() {

        //prep request
        givenAuth(token, xApiKey)
                .header("content-type", "application/json")
                .queryParam("VehicleRegMark", nonAlphaVehicleMark).

                //send request
                        when().//log().all().
                get().

                //verification
                        then().//log().all().
                statusCode(500).
                body(equalTo("Vehicle identifier is invalid"));
    }

    private vott.models.dao.Vehicle newTestVehicle() {
        vott.models.dao.Vehicle vehicle = new vott.models.dao.Vehicle();

        vehicle.setSystemNumber("SYSTEM-NUMBER");
        vehicle.setVin("A12345");
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

    private vott.models.dao.TechnicalRecord newTestTechnicalRecord() {
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

    private PSVBrakes newTestPSVBrakes() {
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

    private Axles newTestAxles() {
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

    private Tyre newTestTyre() {
        Tyre tyre = new Tyre();

        tyre.setTyreSize("456");
        tyre.setPlyRating("10");
        tyre.setFitmentCode("55555");
        tyre.setDataTrAxles("Test Data");
        tyre.setSpeedCategorySymbol("1");
        tyre.setTyreCode("88888");

        return tyre;
    }

    private AxleSpacing newTestAxleSpacing() {
        AxleSpacing as = new AxleSpacing();

        as.setTechnicalRecordID(String.valueOf(technicalRecordPK));
        as.setAxles("Test");
        as.setValue("120");

        return as;
    }

    private Plate newTestPlate() {
        Plate plate = new Plate();
        plate.setTechnicalRecordID(String.valueOf(technicalRecordPK));
        plate.setPlateSerialNumber("666666");
        plate.setPlateIssueDate("2100-12-31");
        plate.setPlateReasonForIssue("Test Reason");
        plate.setPlateIssuer("Auto Test");

        return plate;
    }

    private Callable<Boolean> vehicleIsPresentInDatabase(String vin) {
        return () -> {
            List<vott.models.dao.Vehicle> vehicles = vehicleRepository.select(String.format("SELECT * FROM `vehicle` WHERE `vin` = '%s'", vin));
            return !vehicles.isEmpty();
        };
    }

    private Callable<Boolean> techRecordIsPresentInDatabase(String vehicleID) {
        return () -> {
            List<vott.models.dao.TechnicalRecord> testResults = technicalRecordRepository.select(String.format(
                    "SELECT *\n"
                            + "FROM `technical_record`\n"
                            + "WHERE `vehicle_id` = '%s'", vehicleID
            ));
            return !testResults.isEmpty();
        };
    }
}


