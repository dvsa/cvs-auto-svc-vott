package vott.testhistory;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
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
import vott.models.dto.enquiry.TestResult;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static vott.e2e.RestAssuredAuthenticated.givenAuth;

@RunWith(SerenityRunner.class)
public class RetrieveTestHistoryClientCredsTokenTest {

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
        this.token = new TokenService(OAuthVersion.V2, GrantType.CLIENT_CREDENTIALS).getBearerToken();

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

    @Title ("VOTT-9 - AC1 - TC31 - Happy Path - Retrieve Test History Using Vin Test With A Client Credentials Token")
    @Test
    public void RetrieveTestHistoryUsingVinTest() throws InterruptedException {

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

        TestResult testResult  = gson.fromJson(response.asString(), TestResult.class);

        assertThat(testResult.getTester().getName()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getTester().getStaffId()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getTester().getEmailAddress()).isEqualTo(vehicleUpsert.getVin());

//        assertThat(testResult.getPreparer().getName()).isEqualTo(vehicleUpsert.getVin());
//        assertThat(testResult.getPreparer().getPreparerId()).isEqualTo(vehicleUpsert.getVin());

        assertThat(testResult.getRegndate()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getTestCode()).isEqualTo(vehicleUpsert.getVin());

        assertThat(testResult.getTestType().getTestTypeName()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getTestType().getTestTypeClassification()).isEqualTo(vehicleUpsert.getVin());

        assertThat(testResult.getCreatedAt()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getNoOfAxles()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getTestNumber()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getTestResult()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getTestStatus()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getCreatedById()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getFirstUsedate()).isEqualTo(vehicleUpsert.getVin());

        assertThat(testResult.getFuelEmission().getFuelType()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getFuelEmission().getDescription()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getFuelEmission().getModTypeCode()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getFuelEmission().getEmissionStandard()).isEqualTo(vehicleUpsert.getVin());

        assertThat(testResult.getLastUpdatedAt()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getNumberOfSeats()).isEqualTo(vehicleUpsert.getVin());

        assertThat(testResult.getVehicleClass().getCode()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getVehicleClass().getDescription()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getVehicleClass().getVehicleSize()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getVehicleClass().getVehicleType()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getVehicleClass().getEuVehicleCategory()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getVehicleClass().getVehicleConfiguration()).isEqualTo(vehicleUpsert.getVin());

        assertThat(testResult.getTestExpiryDate()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getLastUpdatedById()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getOdometerReading()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getCertificateNumber()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getReasonForAbandoning()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getTestAnniversaryDate()).isEqualTo(vehicleUpsert.getVin());
//        assertThat(testResult.getModificationTypeUsed()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getOdometerReadingUnits()).isEqualTo(vehicleUpsert.getVin());
//        assertThat(testResult.getTesTypeEndTimestamp()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getCountryOfRegistration()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getParticulateTrapFitted()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getReasonForCancellation()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getSmokeTestKLimitApplied()).isEqualTo(vehicleUpsert.getVin());
//        assertThat(testResult.getTestTypeStartTimestamp()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getAdditionalNotesRecorded()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getNumberOfSeatbeltsFitted()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getSecondaryCertificateNumber()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getParticulateTrapSerialNumber()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getAdditionalCommentsForAbandon()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getSeatbeltInstallationCheckDate()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getLastSeatbeltInstallationCheckDate()).isEqualTo(vehicleUpsert.getVin());

        assertThat(testResult.getCustomDefect().get(0).getDefectName()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getCustomDefect().get(0).getDefectNotes()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getCustomDefect().get(0).getReferenceNumber()).isEqualTo(vehicleUpsert.getVin());

//        assertThat(testResult.getDefects().get(0).getPrs()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getNotes()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getDefect().getImNumber()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getDefect().getItemNumber()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getDefect().getDeficiencyId()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getDefect().getDeficiencyRef()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getDefect().getImDescription()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getDefect().getDeficiencyText()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getDefect().getDeficiencySubId()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getDefect().getItemDescription()).isEqualTo(vehicleUpsert.getVin());
//        assertThat(testResult.getDefects().get(0).getDefect().getStdForProhibition).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getDefect().getDeficiencyCategory()).isEqualTo(vehicleUpsert.getVin());

        assertThat(testResult.getDefects().get(0).getLocation().getLateral()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getLocation().getVertical()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getLocation().getRowNumber()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getLocation().getAxleNumber()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getLocation().getHorizontal()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getLocation().getSeatNumber()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getLocation().getLongitudinal()).isEqualTo(vehicleUpsert.getVin());

        assertThat(testResult.getDefects().get(0).getProhibitionIssued()).isEqualTo(vehicleUpsert.getVin());
    }

    @Title("VOTT-9 - AC1 - TC32 - Happy Path - RetrieveTestHistoryUsingVrmTest")
    @Test
    public void RetrieveTestHistoryUsingVrmTest() {

        String response =
                givenAuth(token, xApiKey)
                        .header("content-type", "application/json")
                        .queryParam("vinNumber", "B2C1C11"). // todo enter paramed vin

                        //send request
                                when().//log().all().
                        get().

                        //verification
                                then().//log().all().
                        statusCode(200).
                        extract().response().asString();

        Gson gson = GsonInstance.get();

        TestResult testResult  = gson.fromJson(response, TestResult.class);

        assertThat(testResult.getTester().getName()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getTester().getStaffId()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getTester().getEmailAddress()).isEqualTo(vehicleUpsert.getVin());

//        assertThat(testResult.getPreparer().getName()).isEqualTo(vehicleUpsert.getVin());
//        assertThat(testResult.getPreparer().getPreparerId()).isEqualTo(vehicleUpsert.getVin());

        assertThat(testResult.getRegndate()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getTestCode()).isEqualTo(vehicleUpsert.getVin());

        assertThat(testResult.getTestType().getTestTypeName()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getTestType().getTestTypeClassification()).isEqualTo(vehicleUpsert.getVin());

        assertThat(testResult.getCreatedAt()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getNoOfAxles()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getTestNumber()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getTestResult()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getTestStatus()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getCreatedById()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getFirstUsedate()).isEqualTo(vehicleUpsert.getVin());

        assertThat(testResult.getFuelEmission().getFuelType()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getFuelEmission().getDescription()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getFuelEmission().getModTypeCode()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getFuelEmission().getEmissionStandard()).isEqualTo(vehicleUpsert.getVin());

        assertThat(testResult.getLastUpdatedAt()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getNumberOfSeats()).isEqualTo(vehicleUpsert.getVin());

        assertThat(testResult.getVehicleClass().getCode()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getVehicleClass().getDescription()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getVehicleClass().getVehicleSize()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getVehicleClass().getVehicleType()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getVehicleClass().getEuVehicleCategory()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getVehicleClass().getVehicleConfiguration()).isEqualTo(vehicleUpsert.getVin());

        assertThat(testResult.getTestExpiryDate()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getLastUpdatedById()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getOdometerReading()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getCertificateNumber()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getReasonForAbandoning()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getTestAnniversaryDate()).isEqualTo(vehicleUpsert.getVin());
//        assertThat(testResult.getModificationTypeUsed()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getOdometerReadingUnits()).isEqualTo(vehicleUpsert.getVin());
//        assertThat(testResult.getTesTypeEndTimestamp()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getCountryOfRegistration()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getParticulateTrapFitted()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getReasonForCancellation()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getSmokeTestKLimitApplied()).isEqualTo(vehicleUpsert.getVin());
//        assertThat(testResult.getTestTypeStartTimestamp()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getAdditionalNotesRecorded()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getNumberOfSeatbeltsFitted()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getSecondaryCertificateNumber()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getParticulateTrapSerialNumber()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getAdditionalCommentsForAbandon()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getSeatbeltInstallationCheckDate()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getLastSeatbeltInstallationCheckDate()).isEqualTo(vehicleUpsert.getVin());

        assertThat(testResult.getCustomDefect().get(0).getDefectName()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getCustomDefect().get(0).getDefectNotes()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getCustomDefect().get(0).getReferenceNumber()).isEqualTo(vehicleUpsert.getVin());

//        assertThat(testResult.getDefects().get(0).getPrs()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getNotes()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getDefect().getImNumber()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getDefect().getItemNumber()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getDefect().getDeficiencyId()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getDefect().getDeficiencyRef()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getDefect().getImDescription()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getDefect().getDeficiencyText()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getDefect().getDeficiencySubId()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getDefect().getItemDescription()).isEqualTo(vehicleUpsert.getVin());
//        assertThat(testResult.getDefects().get(0).getDefect().getStdForProhibition).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getDefect().getDeficiencyCategory()).isEqualTo(vehicleUpsert.getVin());

        assertThat(testResult.getDefects().get(0).getLocation().getLateral()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getLocation().getVertical()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getLocation().getRowNumber()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getLocation().getAxleNumber()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getLocation().getHorizontal()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getLocation().getSeatNumber()).isEqualTo(vehicleUpsert.getVin());
        assertThat(testResult.getDefects().get(0).getLocation().getLongitudinal()).isEqualTo(vehicleUpsert.getVin());

        assertThat(testResult.getDefects().get(0).getProhibitionIssued()).isEqualTo(vehicleUpsert.getVin());
    }

    @Title("VOTT-9 - AC1 - TC33 - RetrieveTestHistoryBadJwtTokenTest")
    @Test
    public void RetrieveTestHistoryBadJwtTokenTest() {

        //prep request
        givenAuth(token + 1, xApiKey)
                .header("content-type", "application/json")
                .queryParam("VehicleRegMark", validVINNumber).

                //send request
                        when().//log().all().
                get().

                //verification
                        then().//log().all().
                statusCode(403).
                body("message", equalTo("User is not authorized to access this resource with an explicit deny"));
    }

    @Title("VOTT-9 - AC1 - TC34 - RetrieveTestHistoryNoParamsTest")
    @Test
    public void RetrieveTestHistoryNoParamsTest() {

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

    @Title("VOTT-9 - AC1 - TC35 - RetrieveTestHistoryBothVinAndVrmTest")
    @Test
    public void RetrieveTestHistoryBothVinAndVrmTest() {

        //prep request
        givenAuth(token, xApiKey)
                .header("content-type", "application/json")
                .queryParam("vinNumber", validVINNumber)
                .queryParam("VehicleRegMark", validVINNumber).

                //send request
                        when().//log().all().
                get().

                //verification
                        then().//log().all().
                statusCode(400).
                body(equalTo("Too many parameters defined"));
    }

    @Title("VOTT-9 - AC1 - TC36 RetrieveTestHistoryNoAPIKeyTest")
    @Test
    public void RetrieveTestHistoryNoAPIKeyTest() {

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

    @Title("VOTT-9 - AC1 - TC37 - RetrieveTestHistoryInvalidAPIKey")
    @Test
    public void RetrieveTestHistoryInvalidAPIKey() {

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

    @Title("VOTT-9 - AC1 - TC38 - RetrieveTestHistoryVehicleRegMarkDoesntExistTest")
    @Test
    public void RetrieveTestHistoryVehicleRegMarkDoesntExistTest() {

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

    @Title("VOTT-9 - AC1 - TC39 - RetrieveTestHistoryVinNumberDoesntExistTest")
    @Test
    public void RetrieveTestHistoryVinNumberDoesntExistTest() {

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

    @Title("VOTT-9 - AC1 - TC40 - RetrieveTestHistoryNonPrintableCharsParamsTest")
    @Test
    public void RetrieveTestHistoryNonPrintableCharsParamsTest() {

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
