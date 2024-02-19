package vott.database;

import vott.database.AbstractRepository;
import vott.database.connection.ConnectionFactory;
import vott.database.sqlgeneration.TableDetails;
import vott.models.dao.ADR;
import vott.models.dao.Defect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdrDetailsRepository extends AbstractRepository<ADR> {
    public AdrDetailsRepository(ConnectionFactory connectionFactory) { super(connectionFactory); }

    @Override
    protected TableDetails getTableDetails() {
        TableDetails tableDetails = new TableDetails();
        tableDetails.setTableName("AdrDetails");
        tableDetails.setColumnNames(new String[] {
                "technicalRecordID",
                "type",
                "approvalDate",
                "listStatementApplicable",
                "batteryListNumber",
                "declarationsSeen",
                "brakeDeclarationsSeen",
                "brakeDeclarationIssuer",
                "brakeEndurance",
                "weight",
                "compatibilityGroupJ",
                "additionalExaminerNotes",
                "applicantDetailsName",
                "street",
                "town",
                "city",
                "postcode",
                "memosApply",
                "adrTypeApprovalNo",
                "adrCertificateNotes",
                "tankManufacturer",
                "yearOfManufacture",
                "tankCode",
                "specialProvisions",
                "tankManufacturerSerialNo",
                "tankTypeAppNo",
                "tc2Type",
                "tc2IntermediateApprovalNo",
                "tc2IntermediateExpiryDate",
                "substancesPermitted",
                "statement",
                "productListRefNo",
                "productList",
                "m145Statement"
        });

        return tableDetails;
    }

    @Override
    protected void setParameters(PreparedStatement preparedStatement, ADR entity) throws SQLException {
        // 1-indexed
        preparedStatement.setString(1, entity.getTechnicalRecordID());
        preparedStatement.setString(2, entity.getType());
        preparedStatement.setString(3, entity.getApprovalDate());
        preparedStatement.setString(4, entity.getListStatementApplicable());
        preparedStatement.setString(5, entity.getBatteryListNumber());
        preparedStatement.setString(6, entity.getDeclarationsSeen());
        preparedStatement.setString(7, entity.getBrakeDeclarationsSeen());
        preparedStatement.setString(8, entity.getBrakeDeclarationIssuer());
        preparedStatement.setString(9, entity.getBrakeEndurance());
        preparedStatement.setString(10, entity.getWeight());
        preparedStatement.setString(11, entity.getCompatibilityGroupJ());
        preparedStatement.setString(12, entity.getAdditionalExaminerNotes());
        preparedStatement.setString(13, entity.getApplicantDetailsName());
        preparedStatement.setString(14, entity.getStreet());
        preparedStatement.setString(15, entity.getTown());
        preparedStatement.setString(16, entity.getCity());
        preparedStatement.setString(17, entity.getPostcode());
        preparedStatement.setString(18, entity.getMemosApply());
        preparedStatement.setString(19, entity.getAdrTypeApprovalNo());
        preparedStatement.setString(20, entity.getAdrCertificateNotes());
        preparedStatement.setString(21, entity.getTankManufacturer());
        preparedStatement.setString(22, entity.getYearOfManufacture());
        preparedStatement.setString(23, entity.getTankCode());
        preparedStatement.setString(24, entity.getSpecialProvisions());
        preparedStatement.setString(25, entity.getTankManufacturerSerialNo());
        preparedStatement.setString(26, entity.getTankTypeAppNo());
        preparedStatement.setString(27, entity.getTc2Type());
        preparedStatement.setString(28, entity.getTc2IntermediateApprovalNo());
        preparedStatement.setString(29, entity.getTc2IntermediateExpiryDate());
        preparedStatement.setString(30, entity.getSubstancesPermitted());
        preparedStatement.setString(31, entity.getStatement());
        preparedStatement.setString(32, entity.getProductListRefNo());
        preparedStatement.setString(33, entity.getProductList());
        preparedStatement.setString(34, entity.getM145Statement());

    }

    @Override
    protected void setParametersFull(PreparedStatement preparedStatement, ADR entity) throws SQLException {
        setParameters(preparedStatement, entity);

        preparedStatement.setString(35, entity.getTechnicalRecordID());
        preparedStatement.setString(36, entity.getType());
        preparedStatement.setString(37, entity.getApprovalDate());
        preparedStatement.setString(38, entity.getListStatementApplicable());
        preparedStatement.setString(39, entity.getBatteryListNumber());
        preparedStatement.setString(40, entity.getDeclarationsSeen());
        preparedStatement.setString(41, entity.getBrakeDeclarationsSeen());
        preparedStatement.setString(42, entity.getBrakeDeclarationIssuer());
        preparedStatement.setString(43, entity.getBrakeEndurance());
        preparedStatement.setString(44, entity.getWeight());
        preparedStatement.setString(45, entity.getCompatibilityGroupJ());
        preparedStatement.setString(46, entity.getAdditionalExaminerNotes());
        preparedStatement.setString(47, entity.getApplicantDetailsName());
        preparedStatement.setString(48, entity.getStreet());
        preparedStatement.setString(49, entity.getTown());
        preparedStatement.setString(50, entity.getCity());
        preparedStatement.setString(51, entity.getPostcode());
        preparedStatement.setString(52, entity.getMemosApply());
        preparedStatement.setString(53, entity.getAdrTypeApprovalNo());
        preparedStatement.setString(54, entity.getAdrCertificateNotes());
        preparedStatement.setString(55, entity.getTankManufacturer());
        preparedStatement.setString(56, entity.getYearOfManufacture());
        preparedStatement.setString(57, entity.getTankCode());
        preparedStatement.setString(58, entity.getSpecialProvisions());
        preparedStatement.setString(59, entity.getTankManufacturerSerialNo());
        preparedStatement.setString(60, entity.getTankTypeAppNo());
        preparedStatement.setString(61, entity.getTc2Type());
        preparedStatement.setString(62, entity.getTc2IntermediateApprovalNo());
        preparedStatement.setString(63, entity.getTc2IntermediateExpiryDate());
        preparedStatement.setString(64, entity.getSubstancesPermitted());
        preparedStatement.setString(65, entity.getStatement());
        preparedStatement.setString(66, entity.getProductListRefNo());
        preparedStatement.setString(67, entity.getProductList());
        preparedStatement.setString(68, entity.getM145Statement());
    }

    @Override
    protected ADR mapToEntity(ResultSet rs) throws SQLException {
        ADR adr = new ADR();

        adr.setTechnicalRecordID(rs.getString("technicalRecordID"));
        adr.setType(rs.getString("type"));
        adr.setApprovalDate(rs.getString("approvalDate"));
        adr.setListStatementApplicable(rs.getString("listStatementApplicable"));
        adr.setBatteryListNumber(rs.getString("batteryListNumber"));
        adr.setDeclarationsSeen(rs.getString("declarationsSeen"));
        adr.setBrakeDeclarationsSeen(rs.getString("brakeDeclarationsSeen"));
        adr.setBrakeDeclarationIssuer(rs.getString("brakeDeclarationIssuer"));
        adr.setBrakeEndurance(rs.getString("brakeEndurance"));
        adr.setWeight(rs.getString("weight"));
        adr.setCompatibilityGroupJ(rs.getString("compatibilityGroupJ"));
        adr.setAdditionalExaminerNotes(rs.getString("additionalExaminerNotes"));
        adr.setApplicantDetailsName(rs.getString("applicantDetailsName"));
        adr.setStreet(rs.getString("street"));
        adr.setTown(rs.getString("town"));
        adr.setCity(rs.getString("city"));
        adr.setPostcode(rs.getString("postcode"));
        adr.setMemosApply(rs.getString("memosApply"));
        adr.setAdrTypeApprovalNo(rs.getString("adrTypeApprovalNo"));
        adr.setAdrCertificateNotes(rs.getString("adrCertificateNotes"));
        adr.setTankManufacturer(rs.getString("tankManufacturer"));
        adr.setYearOfManufacture(rs.getString("yearOfManufacture"));
        adr.setTankCode(rs.getString("tankCode"));
        adr.setSpecialProvisions(rs.getString("specialProvisions"));
        adr.setTankManufacturerSerialNo(rs.getString("tankManufacturerSerialNo"));
        adr.setTankTypeAppNo(rs.getString("tankTypeAppNo"));
        adr.setTc2Type(rs.getString("tc2Type"));
        adr.setTc2IntermediateApprovalNo(rs.getString("tc2IntermediateApprovalNo"));
        adr.setTc2IntermediateExpiryDate(rs.getString("tc2IntermediateExpiryDate"));
        adr.setSubstancesPermitted(rs.getString("substancesPermitted"));
        adr.setStatement(rs.getString("statement"));
        adr.setProductListRefNo(rs.getString("productListRefNo"));
        adr.setProductList(rs.getString("productList"));
        adr.setM145Statement(rs.getString("m145Statement"));

        return adr;
    }
}
