package vott.database.sqlgeneration;

import vott.database.*;
import vott.models.dto.enquiry.TechnicalRecord;

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

    private String generateUpsertSql(TableDetails tableDetails, String[] updatePlaceholders) {
        String[] valuePlaceholders = new String[tableDetails.getColumnNames().length];
        Arrays.fill(valuePlaceholders, "?");

        return String.format(
            "INSERT INTO `%s` (%s) VALUES (%s) ON DUPLICATE KEY UPDATE %s",
            tableDetails.getTableName(),
            Arrays.stream(tableDetails.getColumnNames())
                .map(c -> '`' + c + '`')
                .collect(Collectors.joining(", ")),
            String.join(", ", valuePlaceholders),
            String.join(", ", updatePlaceholders)
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

    public static List<vott.models.dao.EVLView> getEVLViewWithCertificateNumberAndVrm(String certificateNumber, String vrm, EVLViewRepository evlViewRepository){
        List<vott.models.dao.EVLView> evl = evlViewRepository.select(String.format(
                "SELECT * \n"
                        + "FROM `evl_view`\n"
                        + "WHERE `evl_view`.`certificateNumber` = '%s'\n"
                        + "AND `evl_view`.`vrm_trm` = '%s' " , certificateNumber, vrm
        ));
        return evl;
    }

    public static List<vott.models.dao.TFLView> getTFLViewWithVin(String vin, TFLViewRepository tflViewRepository){
        List<vott.models.dao.TFLView> tfl = tflViewRepository.select(String.format(
                "SELECT * \n"
                        + "FROM `CVSNOPCVSB5043`.`tfl_view`\n"
                        + "WHERE `CVSNOPCVSB5043`.`tfl_view`.`vin` = '%s'" , vin
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
}
