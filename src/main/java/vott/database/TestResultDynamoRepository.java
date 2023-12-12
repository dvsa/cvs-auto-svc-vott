package vott.database;


import vott.database.connection.ConnectionFactory;
import vott.database.sqlgeneration.TableDetails;
import vott.models.dao.TestResult;
import vott.models.dao.TestResultDynamo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestResultDynamoRepository extends AbstractRepository<TestResultDynamo>{
    public TestResultDynamoRepository(ConnectionFactory connectionFactory) { super(connectionFactory); }

    @Override
    protected TableDetails getTableDetails() {

        TableDetails tableDetails = new TableDetails();

        tableDetails.setTableName("test_result_dynamo");
        tableDetails.setColumnNames(new String[] {
                "testResultId",
                "vin",
                "vrm_trm",
                "trailer_id",
                "system_number",
                "countryOfRegistration",
                "euVehicleCategory",
                "noOfAxles",
                "odometerReading",
                "odometerReadingUnits",
                "preparer_id",
                "name",
                "reasonForCancellation",
                "regnDate",
                "test_station_id",
                "name",
                "pNumber",
                "type",
                "testStatus",
                "additionalCommentsForAbandon",
                "additionalNotesRecorded",
                "certificateNumber",
                "fuel_emission_id",
                "emissionStandard",
                "fuelType",
                "modTypeCode",
                "description",
                "lastUpdatedAt",
                "test_type_id",
                "testTypeName",
                "testTypeClassification",
                "reasonForAbandoning",
                "secondaryCertificateNumber",
                "testExpiryDate",
                "testResult",
                "testTypeStartTimestamp",
                "testTypeEndTimestamp",
                "firstUseDate",
                "regnDate",
                "tester_id",
                "staffid",
                "name",
                "email_address",
                "code",
                "description",
                "vehicleConfiguration",
                "vehicleType",
                "vehicleSize",
                "defect_tr_id",
                "c_defect_tr_id",
                "numberOfSeats",
                "lastSeatbeltInstallationCheckDate",
                "numberOfSeatbeltsFitted",
                "seatbeltInstallationCheckDate",
                "nopInsertedAt"
        });

        return tableDetails;
    }

    @Override
    protected void setParameters(PreparedStatement preparedStatement, TestResultDynamo entity) throws SQLException {
        //Not required for current testing purposes as of 12/12/2023
    }

    @Override
    protected void setParametersFull(PreparedStatement preparedStatement, TestResultDynamo entity) throws SQLException {
        setParameters(preparedStatement, entity);
        //Not required for current testing purposes as of 12/12/2023
    }

    @Override
    protected TestResultDynamo mapToEntity(ResultSet rs) throws SQLException {
        TestResultDynamo tr = new TestResultDynamo();

//        tr.setId(rs.getString("id"));
//        tr.setVehicleID(rs.getString("vehicle_id"));
        tr.setFuelEmissionID(rs.getString("fuel_emission_id"));
        tr.setTestStationID(rs.getString("test_station_id"));
        tr.setTesterID(rs.getString("tester_id"));
        tr.setPreparerID(rs.getString("preparer_id"));
//        tr.setVehicleClassID(rs.getString("vehicle_class_id"));
        tr.setTestTypeID(rs.getString("test_type_id"));
        tr.setTestResultId(rs.getString("testResultId"));
        tr.setTestStatus(rs.getString("testStatus"));
        tr.setReasonForCancellation(rs.getString("reasonForCancellation"));
        tr.setNumberOfSeats(rs.getString("numberOfSeats"));
        tr.setOdometerReading(rs.getString("odometerReading"));
        tr.setOdometerReadingUnits(rs.getString("odometerReadingUnits"));
        tr.setCountryOfRegistration(rs.getString("countryOfRegistration"));
        tr.setNoOfAxles(rs.getString("noOfAxles"));
        tr.setRegnDate(rs.getString("regnDate"));
        tr.setFirstUseDate(rs.getString("firstUseDate"));
//        tr.setCreatedAt(rs.getString("createdAt"));
        tr.setLastUpdatedAt(rs.getString("lastUpdatedAt"));
        tr.setTestCode(rs.getString("testCode"));
        tr.setTestNumber(rs.getString("testNumber"));
        tr.setCertificateNumber(rs.getString("certificateNumber"));
        tr.setSecondaryCertificateNumber(rs.getString("secondaryCertificateNumber"));
        tr.setTestExpiryDate(rs.getString("testExpiryDate"));
//        tr.setTestAnniversaryDate(rs.getString("testAnniversaryDate"));
        tr.setTestTypeStartTimestamp(rs.getString("testTypeStartTimestamp"));
        tr.setTestTypeEndTimestamp(rs.getString("testTypeEndTimestamp"));
        tr.setNumberOfSeatbeltsFitted(rs.getString("numberOfSeatbeltsFitted"));
        tr.setLastSeatbeltInstallationCheckDate(rs.getString("lastSeatbeltInstallationCheckDate"));
        tr.setSeatbeltInstallationCheckDate(rs.getString("seatbeltInstallationCheckDate"));
        tr.setTestResult(rs.getString("testResult"));
        tr.setReasonForAbandoning(rs.getString("reasonForAbandoning"));
        tr.setAdditionalNotesRecorded(rs.getString("additionalNotesRecorded"));
        tr.setAdditionalCommentsForAbandon(rs.getString("additionalCommentsForAbandon"));
//        tr.setParticulateTrapFitted(rs.getString("particulateTrapFitted"));
//        tr.setParticulateTrapSerialNumber(rs.getString("particulateTrapSerialNumber"));
//        tr.setModificationTypeUsed(rs.getString("modificationTypeUsed"));
//        tr.setSmokeTestKLimitApplied(rs.getString("smokeTestKLimitApplied"));
//        tr.setCreatedByID(rs.getString("createdBy_Id"));
//        tr.setLastUpdatedByID(rs.getString("lastUpdatedBy_Id"));
        tr.setVehicleSize(rs.getString("vehicleSize"));
        tr.setTrailerID(rs.getString("trailer_id"));

        return tr;
    }
}
