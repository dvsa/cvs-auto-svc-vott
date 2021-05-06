package vott.database.sqlgeneration;

import vott.database.TechnicalRecordRepository;
import vott.database.TestResultRepository;
import vott.database.VehicleRepository;
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
}
