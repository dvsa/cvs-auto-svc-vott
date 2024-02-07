package vott.database.sqlgeneration;

import org.mockito.internal.verification.Times;
import vott.database.*;
import vott.models.dao.VtEVLAdditions;
import vott.models.dao.VtEvlCvsRemoved;
import vott.models.dto.enquiry.TechnicalRecord;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class SqlGenerator {

    public String generateSelectSql(TableDetails tableDetails, int primaryKey) {
        List<String> columnNames = Arrays.stream(tableDetails.getColumnNames())
            .map(c -> '`' + c + '`')
            .collect(Collectors.toList());

        columnNames.add(0, "`" + tableDetails.getPrimaryKeyColumnName() + "`");

        return String.format(
            "SELECT %s FROM `%s` WHERE `%s` = %d",
            String.join(", ", columnNames),
            tableDetails.getTableName(),
            tableDetails.getPrimaryKeyColumnName(),
            primaryKey
        );
    }

    public String generateSelectByFingerprint (TableDetails tableDetails) {

        String sql = String.format(
                "SELECT id FROM %s WHERE testtype_fingerprint = MD5(CONCAT_WS('|', %s))",
                tableDetails.getTableName(),
                Arrays.stream(tableDetails.getColumnNames())
                        .map(c -> "IFNULL(?,'')")
                        .collect(Collectors.joining(", "))
        );

      //System.out.println(sql);
       return sql;
    };


    public String generateDeleteSql(TableDetails tableDetails, int primaryKey) {
        return String.format(
            "DELETE FROM `%s` WHERE `%s` = %d",
            tableDetails.getTableName(),
            tableDetails.getPrimaryKeyColumnName(),
            primaryKey
        );
    }

    public String generatePartialUpsertSql(TableDetails tableDetails) {
        return generateUpsertSql(
            tableDetails,
            generateUpdatePlaceholders(
                tableDetails.getPrimaryKeyColumnName()
            )
        );
    }

    public String generateFullUpsertSql(TableDetails tableDetails) {
        return generateUpsertSql(
            tableDetails,
            generateUpdatePlaceholders(
                tableDetails.getPrimaryKeyColumnName(),
                tableDetails.getColumnNames()
            )
        );
    }
    public String generateFullUpsertSqlWithoutID(TableDetails tableDetails) {
        return generateUpsertSqlWithoutID(
                tableDetails
        );
    }

    private String generateUpsertSql(TableDetails tableDetails, String[] updatePlaceholders) {
        String[] valuePlaceholders = new String[tableDetails.getColumnNames().length];
        Arrays.fill(valuePlaceholders, "?");

        String sql = String.format(
            "INSERT INTO `%s` (%s) VALUES (%s) ON DUPLICATE KEY UPDATE %s",
            tableDetails.getTableName(),
            Arrays.stream(tableDetails.getColumnNames())
                .map(c -> '`' + c + '`')
                .collect(Collectors.joining(", ")),
            String.join(", ", valuePlaceholders),
            String.join(", ", updatePlaceholders)
        );
        //System.out.println(sql);
        return sql;
    }

    private String generateUpsertSqlWithoutID(TableDetails tableDetails) {
        String[] valuePlaceholders = new String[tableDetails.getColumnNames().length];
        Arrays.fill(valuePlaceholders, "?");

        return String.format(
                "INSERT INTO `%s` (%s) VALUES (%s)",
                tableDetails.getTableName(),
                Arrays.stream(tableDetails.getColumnNames())
                        .map(c -> '`' + c + '`')
                        .collect(Collectors.joining(", ")),
                String.join(", ", valuePlaceholders)

        );
    }

    private String[] generateUpdatePlaceholders(String primaryKey) {
        return generateUpdatePlaceholders(primaryKey, new String[0]);
    }

    private String[] generateUpdatePlaceholders(String primaryKey, String[] columnNames) {
        String nonNullPrimaryKey = primaryKey == null ? "id": primaryKey;

        String primaryKeyPlaceholder = String.format(
            "`%s` = LAST_INSERT_ID(`%s`)",
            nonNullPrimaryKey,
            nonNullPrimaryKey
        );

        if (columnNames.length == 0) {
            return new String[] { primaryKeyPlaceholder };
        }

        List<String> updatePlaceholders = Arrays.stream(columnNames)
            .filter(c -> !c.equals(nonNullPrimaryKey))
            .map(c -> String.format("`%s` = ?", c))
            .collect(Collectors.toList());

        updatePlaceholders.add(0, primaryKeyPlaceholder);

        return updatePlaceholders.toArray(new String[0]);
    }

    public static Callable<Boolean> vehicleIsPresentInDatabase(String vin, VehicleRepository vehicleRepository) {
        return () -> {
            List<vott.models.dao.Vehicle> vehicles = vehicleRepository.select(String.format("SELECT * FROM `vehicle` WHERE `vin` = '%s'", vin));
            return !vehicles.isEmpty();
        };
    }

    public static Callable<Boolean> testResultIsPresentInDatabase(String vin, TestResultRepository testResultRepository) {
        return () -> {
            List<vott.models.dao.TestResult> testResults = testResultRepository.select(String.format(
                    "SELECT `test_result`.*\n"
                            + "FROM `vehicle`\n"
                            + "JOIN `test_result`\n"
                            + "ON `test_result`.`vehicle_id` = `vehicle`.`id`\n"
                            + "WHERE `vehicle`.`vin` = '%s'", vin
            ));
            return !testResults.isEmpty();
        };
    }

    public static Callable<Boolean> techRecordIsPresentInDatabase(String vehicleID, TechnicalRecordRepository technicalRecordRepository) {
        return () -> {
            List<vott.models.dao.TechnicalRecord> testResults = technicalRecordRepository.select(String.format(
                    "SELECT *\n"
                            + "FROM `technical_record`\n"
                            + "WHERE `vehicle_id` = '%s'", vehicleID
            ));
            return !testResults.isEmpty();
        };
    }

    public static List<vott.models.dao.Preparer> getPreparerDetailsWithVehicleID(String testResultVehicleID, PreparerRepository preparerRepository) {
        List<vott.models.dao.Preparer> preparersResult = preparerRepository.select(String.format(
                "SELECT `preparer`.*\n"
                        + "FROM `preparer`\n"
                        + "JOIN `test_result`\n"
                        + "ON `test_result`.`preparer_id` = `preparer`.`id`\n"
                        + "WHERE `test_result`.`vehicle_id` = '%s'" , testResultVehicleID
        ));
        return preparersResult;
    }

    public static List<vott.models.dao.Preparer> getPreparerDetailsWithTestResultID(String testResultID, PreparerRepository preparerRepository) {
        List<vott.models.dao.Preparer> preparersResult = preparerRepository.select(String.format(
                "SELECT `preparer`.*\n"
                        + "FROM `preparer`\n"
                        + "JOIN `test_result`\n"
                        + "ON `test_result`.`preparer_id` = `preparer`.`id`\n"
                        + "WHERE `test_result`.`testResultId` = '%s'" , testResultID
        ));
        return preparersResult;
    }

    public static List<vott.models.dao.Preparer> getPreparerDetailsWithVehicleID1(String testResultVehicleID, PreparerRepository preparerRepository) {
        List<vott.models.dao.Preparer> preparersResult = preparerRepository.select(String.format(
                "SELECT `preparer`.*\n"
                        + "FROM `preparer`\n"
                        + "JOIN `test_result`\n"
                        + "ON `test_result`.`preparer_id` = `preparer`.`id`\n"
                        + "WHERE `test_result`.`vehicle_id` = '%s'" , testResultVehicleID
        ));
        //preparerRepository.
        return preparersResult;
    }

    public static List<vott.models.dao.Tester> getTesterDetailsWithVehicleID(String testResultVehicleID, TesterRepository testerRepository) {
        List<vott.models.dao.Tester> testerResults = testerRepository.select(String.format(
                "SELECT `tester`.*\n"
                        + "FROM `tester`\n"
                        + "JOIN `test_result`\n"
                        + "ON `test_result`.`tester_id` = `tester`.`id`\n"
                        + "WHERE `test_result`.`vehicle_id` = '%s'" , testResultVehicleID
        ));
        return testerResults;
    }

    public static List<vott.models.dao.Tester> getTesterDetailsWithTestResultID(String testResultID, TesterRepository testerRepository) {
        List<vott.models.dao.Tester> testerResults = testerRepository.select(String.format(
                "SELECT `tester`.*\n"
                        + "FROM `tester`\n"
                        + "JOIN `test_result`\n"
                        + "ON `test_result`.`tester_id` = `tester`.`id`\n"
                        + "WHERE `test_result`.`testResultId` = '%s'" , testResultID
        ));
        return testerResults;
    }

    public static List<vott.models.dao.EVLView> getEVLViewWithCertificateNumberAndVrm(String certificateNumber, String vrm, EVLViewRepository evlViewRepository){
        List<vott.models.dao.EVLView> evl = evlViewRepository.select(String.format(
                "SELECT * \n"
                        + "FROM `evl_view`\n"
                        + "WHERE `evl_view`.`certificateNumber` = '%s'\n"
                        + "AND `evl_view`.`vrm_trm` = '%s' " , certificateNumber, vrm
        ));
        return evl;
    }

    public static List<vott.models.dao.EVLView> getEVLViewWithVrm(String vrm, EVLViewRepository evlViewRepository){
        List<vott.models.dao.EVLView> evl = evlViewRepository.select(String.format(
                "SELECT * \n"
                        + "FROM `evl_view`\n"
                        + "WHERE \n"
                        + "`evl_view`.`vrm_trm` = '%s' GROUP BY  `vrm_trm`, `certificateNumber` ORDER BY `vrm_trm`, `testExpiryDate` DESC " ,  vrm
        ));
        return evl;
    }
    public static void upsertVTEVLADDITIONS(VtEVLAdditionsRepository vtEVLAddRepo, VtEVLAdditions vtEvlAdd) throws SQLException,RuntimeException {
        vtEVLAddRepo.fullUpsertWithoutID(vtEvlAdd);
    }

    public static void upsertVtEvlCvsRemoved(VtEvlCvsRemovedRepository vtEVLRepo, VtEvlCvsRemoved vtEvl) throws SQLException,RuntimeException {
        vtEVLRepo.fullUpsertWithoutID(vtEvl);
    }

    public static List<vott.models.dao.VtEvlCvsRemoved> getVTEVLRecordsWithVin(String vin, VtEvlCvsRemovedRepository repo){

        List<vott.models.dao.VtEvlCvsRemoved> vtCvsRemoved = repo.select(String.format(
                "SELECT `vt`.`vrm`,`vt`.`vrm_test_record`,`vt`.`system_number`,`vt`.`vin` ,  \n"
                        + "`vt`.`certificateNumber`,`vt`.`testStartDate`,`vt`.`testExpiryDate` \n"
                        + " FROM `vt_evl_02_cvs_removed` AS `vt` \n"
                        + "LEFT JOIN (SELECT `v`.`system_number`,DATE(MAX(`tr`.`testTypeStartTimestamp`)) AS `testTypeStartTimestamp` FROM`test_result` `tr` \n"
                        + "JOIN `test_type` `tt` ON `tr`.`test_type_id`=`tt`.`id` \n"
                        + "JOIN `vehicle` `v` ON `tr`.`vehicle_id`=`v`.`id` \n"
                        + "WHERE \n"
                        + "LOWER(`tr`.`testResult`) ='fail' AND LOWER(`tt`.`testTypeClassification`) ='annual with certificate' \n"
                        + "AND `tr`.`testTypeStartTimestamp`>= DATE(NOW() - INTERVAL 1 YEAR) \n"
                        + "GROUP BY `v`.`system_number` \n"
                        + ") AS `fails` ON `vt`.`system_number`=`fails`.`system_number` \n"
                        + "WHERE \n"
                        + "(`fails`.`system_number`IS NULL OR `fails`.`testTypeStartTimestamp` < `vt`.`testStartDate`) \n"
                        + "AND `vt`.`vin`='%s'" , vin
        ));
        return vtCvsRemoved;
    }

    public static List<vott.models.dao.TFLView> getTFLViewWithVin(String vin, TFLViewRepository tflViewRepository){
        List<vott.models.dao.TFLView> tfl = tflViewRepository.select(String.format(
                "SELECT * \n"
                        + "FROM `tfl_view`\n"
                        + "WHERE `tfl_view`.`VIN` = '%s'" , vin
        ));
        return tfl;
    }

    public static List<vott.models.dao.AuthIntoServices> getAuthIntoServices(String vin, AuthIntoServicesRepository authIntoServicesRepository){
        List<vott.models.dao.AuthIntoServices> ais = authIntoServicesRepository.select(String.format(
                "SELECT `auth_into_service`.*  from auth_into_service \n"
                        + "JOIN `technical_record` ON `technical_record`.`id` = `auth_into_service`.`technical_record_id` \n"
                        + "JOIN `vehicle` ON `vehicle`.`id` = `technical_record`.`vehicle_id` \n"
                        + "WHERE `vehicle`.`vin` = '%s'", vin
        ));

        return ais;
    }

    public static List<vott.models.dao.TestResultDynamo> getTestResultDynamoWithTestResultId(String testResultId, TestResultDynamoRepository testResultDynamoRepository) {
        List<vott.models.dao.TestResultDynamo> testResults = testResultDynamoRepository.select(String.format(
                "SELECT\n" +
                        "test.testResultId,\n" +
                        "vehicle.vin,\n" +
                        "vehicle.vrm_trm,\n" +
                        "vehicle.trailer_id,\n" +
                        "vehicle.system_number,\n" +
                        "test.countryOfRegistration,\n" +
                        "vehicle_class.euVehicleCategory,\n" +
                        "test.noOfAxles,\n" +
                        "test.odometerReading,\n" +
                        "test.odometerReadingUnits,\n" +
                        "test.preparer_id,\n" +
                        "preparer.name,\n" +
                        "test.reasonForCancellation,\n" +
                        "test.regnDate,\n" +
                        "test.test_station_id,\n" +
                        "station.name,\n" +
                        "station.pNumber,\n" +
                        "station.type,\n" +
                        "test.testStatus,\n" +
                        "test.additionalCommentsForAbandon,\n" +
                        "test.additionalNotesRecorded,\n" +
                        "test.certificateNumber,\n" +
                        "test.fuel_emission_id,\n" +
                        "emission.emissionStandard,\n" +
                        "emission.fuelType,\n" +
                        "emission.modTypeCode,\n" +
                        "emission.description,\n" +
                        "test.lastUpdatedAt,\n" +
                        "test.test_type_id,\n" +
                        "test_type.testTypeName,\n" +
                        "test_type.testTypeClassification,\n" +
                        "test.reasonForAbandoning,\n" +
                        "test.secondaryCertificateNumber,\n" +
                        "test.testExpiryDate,\n" +
                        "test.testResult,\n" +
                        "test.testNumber,\n" +
                        "test.testTypeStartTimestamp,\n" +
                        "test.testTypeEndTimestamp,\n" +
                        "test.firstUseDate,\n" +
                        "test.regnDate,\n" +
                        "test.tester_id,\n" +
                        "test.testCode, \n" +
                        "tester.staffid,\n" +
                        "tester.name,\n" +
                        "tester.email_address,\n" +
                        "vehicle_class.code,\n" +
                        "vehicle_class.description,\n" +
                        "vehicle_class.vehicleConfiguration,\n" +
                        "vehicle_class.vehicleType,\n" +
                        "vehicle_class.vehicleSize,\n" +
                        "defect.test_result_id AS defect_tr_id,\n" +
                        "custom_defect.test_result_id AS c_defect_tr_id,\n" +
                        "-- psv only\n" +
                        "test.numberOfSeats,\n" +
                        "test.lastSeatbeltInstallationCheckDate,\n" +
                        "test.numberOfSeatbeltsFitted,\n" +
                        "test.seatbeltInstallationCheckDate\n" +
                        ", test.nopInsertedAt\n" +
                        "FROM test_result test\n" +
                        "LEFT JOIN vehicle ON vehicle.id = test.vehicle_id\n" +
                        "LEFT JOIN vehicle_class ON vehicle_class.id = test.vehicle_class_id\n" +
                        "LEFT JOIN preparer ON preparer.id = test.preparer_id\n" +
                        "LEFT JOIN test_station station ON station.id = test.test_station_id\n" +
                        "LEFT JOIN fuel_emission emission ON emission.id = test.fuel_emission_id\n" +
                        "LEFT JOIN test_type ON test_type.id = test.test_type_id\n" +
                        "LEFT JOIN tester ON tester.id = test.tester_id\n" +
                        "LEFT JOIN test_defect defect ON defect.test_result_id = test.id\n" +
                        "LEFT JOIN custom_defect ON custom_defect.test_result_id = test.id\n" +
                        "WHERE\n" +
                        "testresultId = '%s'", testResultId
        ));

        return testResults;
    }

    public static List<vott.models.dao.TestResult> getTestResultWithVIN(String vin, TestResultRepository testResultRepository) {
        List<vott.models.dao.TestResult> testResults = testResultRepository.select(String.format(
                "SELECT `test_result`.*\n"
                        + "FROM `vehicle`\n"
                        + "JOIN `test_result`\n"
                        + "ON `test_result`.`vehicle_id` = `vehicle`.`id`\n"
                        + "WHERE `vehicle`.`vin` = '%s'", vin
        ));

        return testResults;
    }

    public static List<vott.models.dao.TechnicalRecord> getVehicleWithVIN(String vin, TechnicalRecordRepository technicalRecordRepository) {
        List<vott.models.dao.TechnicalRecord> techRecord = technicalRecordRepository.select(String.format(
                "SELECT technical_record.*\n"
                        + "FROM `vehicle`\n"
                        + "JOIN `technical_record`\n"
                        + "ON `technical_record`.`vehicle_id` = `vehicle`.`id`\n"
                        + "WHERE `vehicle`.`vin` = '%s'", vin
        ));

        return techRecord;
    }

    public static List<vott.models.dao.TestStation> getTestStationWithTestResultId(String testResultId, TestStationRepository testStationRepository) {
        List<vott.models.dao.TestStation> testStations = testStationRepository.select(String.format(
                "SELECT test_station.* \n" +
                        "FROM test_station\n" +
                        "JOIN test_result ON test_result.test_station_id = test_station.id\n" +
                        "WHERE test_result.testResultId = '%s'", testResultId
        ));

        return testStations;
    }

    public static List<vott.models.dao.TestType> getTestTypeWithTestTypeId(String testTypeId, TestTypeRepository testTypeRepository) {
        List<vott.models.dao.TestType> testTypes = testTypeRepository.select(String.format(
                "SELECT test_type.*\n" +
                        "FROM test_type\n" +
                        "WHERE  test_type.id = '%s'", testTypeId
        ));

        return testTypes;
    }

    public static List<vott.models.dao.Defect> getDefectWithNopTestResultId(String nopTestResultId, DefectRepository defectRepository) {
            List<vott.models.dao.Defect> defect = defectRepository.select(String.format(
                    "SELECT `defect`.*\n" +
                            "FROM `test_defect`\n" +
                            "JOIN `defect`\n" +
                            "ON `test_defect`.`defect_id` = `defect`.`id`\n" +
                            "WHERE `test_defect`.`test_result_id` = '%s'", nopTestResultId
            ));
            return defect;
    }
}
