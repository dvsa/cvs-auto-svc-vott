package vott.models.dto.techrecordsv3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Generated("RemediationClass")
public class AdrRemediationClass extends TechRecordV3 {
    @Expose
    @SerializedName("system_number")
    private String system_Number;
    @Expose
    @SerializedName("created_timestamp")
    private String created_Timestamp;

    @SerializedName("techRecord_adrDetails_dangerousGoods")
    @Expose
    private Boolean techRecordAdrDetailsDangerousGoods;
    @SerializedName("techRecord_adrDetails_vehicleDetails_type")
    @Expose
    private String techRecordAdrDetailsVehicleDetailsType;
    @SerializedName("techRecord_adrDetails_vehicleDetails_approvalDate")
    @Expose
    private String techRecordAdrDetailsVehicleDetailsApprovalDate;
    @SerializedName("techRecord_adrDetails_permittedDangerousGoods")
    @Expose
    private List<String> techRecordAdrDetailsPermittedDangerousGoods;
    @SerializedName("techRecord_adrDetails_compatibilityGroupJ")
    @Expose
    private String techRecordAdrDetailsCompatibilityGroupJ;
    @SerializedName("techRecord_adrDetails_additionalExaminerNotes")
    @Expose
    private List<TechRecordAdrDetailsAdditionalExaminerNote> techRecordAdrDetailsAdditionalExaminerNotes;
    @SerializedName("techRecord_adrDetails_applicantDetails_name")
    @Expose
    private String techRecordAdrDetailsApplicantDetailsName;
    @SerializedName("techRecord_adrDetails_applicantDetails_street")
    @Expose
    private String techRecordAdrDetailsApplicantDetailsStreet;
    @SerializedName("techRecord_adrDetails_applicantDetails_town")
    @Expose
    private String techRecordAdrDetailsApplicantDetailsTown;
    @SerializedName("techRecord_adrDetails_applicantDetails_city")
    @Expose
    private String techRecordAdrDetailsApplicantDetailsCity;
    @SerializedName("techRecord_adrDetails_applicantDetails_postcode")
    @Expose
    private String techRecordAdrDetailsApplicantDetailsPostcode;
    @SerializedName("techRecord_adrDetails_memosApply")
    @Expose
    private List<String> techRecordAdrDetailsMemosApply;
    @SerializedName("techRecord_adrDetails_documents")
    @Expose
    private List<String> techRecordAdrDetailsDocuments;
    @SerializedName("techRecord_adrDetails_listStatementApplicable")
    @Expose
    private Boolean techRecordAdrDetailsListStatementApplicable;
    @SerializedName("techRecord_adrDetails_batteryListNumber")
    @Expose
    private String techRecordAdrDetailsBatteryListNumber;
    @SerializedName("techRecord_adrDetails_brakeDeclarationsSeen")
    @Expose
    private Boolean techRecordAdrDetailsBrakeDeclarationsSeen;
    @SerializedName("techRecord_adrDetails_brakeDeclarationIssuer")
    @Expose
    private String techRecordAdrDetailsBrakeDeclarationIssuer;
    @SerializedName("techRecord_adrDetails_brakeEndurance")
    @Expose
    private Boolean techRecordAdrDetailsBrakeEndurance;
    @SerializedName("techRecord_adrDetails_weight")
    @Expose
    private Double techRecordAdrDetailsWeight;
    @SerializedName("techRecord_adrDetails_declarationsSeen")
    @Expose
    private Boolean techRecordAdrDetailsDeclarationsSeen;
    @SerializedName("techRecord_adrDetails_m145Statement")
    @Expose
    private Boolean techRecordAdrDetailsM145Statement;
    @SerializedName("techRecord_adrDetails_newCertificateRequested")
    @Expose
    private Boolean techRecordAdrDetailsNewCertificateRequested;
    @SerializedName("techRecord_adrDetails_additionalNotes_number")
    @Expose
    private List<String> techRecordAdrDetailsAdditionalNotesNumber;
    @SerializedName("techRecord_adrDetails_adrTypeApprovalNo")
    @Expose
    private String techRecordAdrDetailsAdrTypeApprovalNo;
    @SerializedName("techRecord_adrDetails_adrCertificateNotes")
    @Expose
    private String techRecordAdrDetailsAdrCertificateNotes;
    @SerializedName("techRecord_adrDetails_tank_tankDetails_tankManufacturer")
    @Expose
    private String techRecordAdrDetailsTankTankDetailsTankManufacturer;
    @SerializedName("techRecord_adrDetails_tank_tankDetails_yearOfManufacture")
    @Expose
    private Integer techRecordAdrDetailsTankTankDetailsYearOfManufacture;
    @SerializedName("techRecord_adrDetails_tank_tankDetails_tankManufacturerSerialNo")
    @Expose
    private String techRecordAdrDetailsTankTankDetailsTankManufacturerSerialNo;
    @SerializedName("techRecord_adrDetails_tank_tankDetails_tankTypeAppNo")
    @Expose
    private String techRecordAdrDetailsTankTankDetailsTankTypeAppNo;
    @SerializedName("techRecord_adrDetails_tank_tankDetails_tankCode")
    @Expose
    private String techRecordAdrDetailsTankTankDetailsTankCode;
    @SerializedName("techRecord_adrDetails_tank_tankDetails_specialProvisions")
    @Expose
    private String techRecordAdrDetailsTankTankDetailsSpecialProvisions;
    @SerializedName("techRecord_adrDetails_tank_tankDetails_tc2Details_tc2Type")
    @Expose
    private String techRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type;
    @SerializedName("techRecord_adrDetails_tank_tankDetails_tc2Details_tc2IntermediateApprovalNo")
    @Expose
    private String techRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo;
    @SerializedName("techRecord_adrDetails_tank_tankDetails_tc2Details_tc2IntermediateExpiryDate")
    @Expose
    private String techRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate;
    @SerializedName("techRecord_adrDetails_tank_tankDetails_tc3Details")
    @Expose
    private List<TechRecordAdrDetailsTankTankDetailsTc3Detail> techRecordAdrDetailsTankTankDetailsTc3Details;
    @SerializedName("techRecord_adrDetails_tank_tankDetails_tankStatement_substancesPermitted")
    @Expose
    private String techRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted;
    @SerializedName("techRecord_adrDetails_tank_tankDetails_tankStatement_select")
    @Expose
    private String techRecordAdrDetailsTankTankDetailsTankStatementSelect;
    @SerializedName("techRecord_adrDetails_tank_tankDetails_tankStatement_statement")
    @Expose
    private String techRecordAdrDetailsTankTankDetailsTankStatementStatement;
    @SerializedName("techRecord_adrDetails_tank_tankDetails_tankStatement_productListRefNo")
    @Expose
    private String techRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo;
    @SerializedName("techRecord_adrDetails_tank_tankDetails_tankStatement_productListUnNo")
    @Expose
    private List<String> techRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo;
    @SerializedName("techRecord_adrDetails_tank_tankDetails_tankStatement_productList")
    @Expose
    private String techRecordAdrDetailsTankTankDetailsTankStatementProductList;

    @SerializedName("techRecord_adrPassCertificateDetails")
    @Expose
    private List<TechRecordAdrPassCertificateDetail> techRecordAdrPassCertificateDetails;

    public String getSystem_Number() {
        return system_Number;
    }

    public void setSystem_Number(String system_Number) {
        this.system_Number = system_Number;
    }

    public String getCreated_Timestamp() {
        return created_Timestamp;
    }

    public void setCreated_Timestamp(String created_Timestamp) {
        this.created_Timestamp = created_Timestamp;
    }

    public Boolean getTechRecordAdrDetailsDangerousGoods() {
        return techRecordAdrDetailsDangerousGoods;
    }

    public void setTechRecordAdrDetailsDangerousGoods(Boolean techRecordAdrDetailsDangerousGoods) {
        this.techRecordAdrDetailsDangerousGoods = techRecordAdrDetailsDangerousGoods;
    }

    public String getTechRecordAdrDetailsVehicleDetailsType() {
        return techRecordAdrDetailsVehicleDetailsType;
    }

    public void setTechRecordAdrDetailsVehicleDetailsType(String techRecordAdrDetailsVehicleDetailsType) {
        this.techRecordAdrDetailsVehicleDetailsType = techRecordAdrDetailsVehicleDetailsType;
    }

    public String getTechRecordAdrDetailsVehicleDetailsApprovalDate() {
        return techRecordAdrDetailsVehicleDetailsApprovalDate;
    }

    public void setTechRecordAdrDetailsVehicleDetailsApprovalDate(
            String techRecordAdrDetailsVehicleDetailsApprovalDate) {
        this.techRecordAdrDetailsVehicleDetailsApprovalDate = techRecordAdrDetailsVehicleDetailsApprovalDate;
    }

    public List<String> getTechRecordAdrDetailsPermittedDangerousGoods() {
        return techRecordAdrDetailsPermittedDangerousGoods;
    }

    public void setTechRecordAdrDetailsPermittedDangerousGoods(
            List<String> techRecordAdrDetailsPermittedDangerousGoods) {
        this.techRecordAdrDetailsPermittedDangerousGoods = techRecordAdrDetailsPermittedDangerousGoods;
    }

    public String getTechRecordAdrDetailsCompatibilityGroupJ() {
        return techRecordAdrDetailsCompatibilityGroupJ;
    }

    public void setTechRecordAdrDetailsCompatibilityGroupJ(String techRecordAdrDetailsCompatibilityGroupJ) {
        this.techRecordAdrDetailsCompatibilityGroupJ = techRecordAdrDetailsCompatibilityGroupJ;
    }

    public List<TechRecordAdrDetailsAdditionalExaminerNote> getTechRecordAdrDetailsAdditionalExaminerNotes() {
        return techRecordAdrDetailsAdditionalExaminerNotes;
    }

    public void setTechRecordAdrDetailsAdditionalExaminerNotes(
            List<TechRecordAdrDetailsAdditionalExaminerNote> techRecordAdrDetailsAdditionalExaminerNotes) {
        this.techRecordAdrDetailsAdditionalExaminerNotes = techRecordAdrDetailsAdditionalExaminerNotes;
    }

    public String getTechRecordAdrDetailsApplicantDetailsName() {
        return techRecordAdrDetailsApplicantDetailsName;
    }

    public void setTechRecordAdrDetailsApplicantDetailsName(String techRecordAdrDetailsApplicantDetailsName) {
        this.techRecordAdrDetailsApplicantDetailsName = techRecordAdrDetailsApplicantDetailsName;
    }

    public String getTechRecordAdrDetailsApplicantDetailsStreet() {
        return techRecordAdrDetailsApplicantDetailsStreet;
    }

    public void setTechRecordAdrDetailsApplicantDetailsStreet(String techRecordAdrDetailsApplicantDetailsStreet) {
        this.techRecordAdrDetailsApplicantDetailsStreet = techRecordAdrDetailsApplicantDetailsStreet;
    }

    public String getTechRecordAdrDetailsApplicantDetailsTown() {
        return techRecordAdrDetailsApplicantDetailsTown;
    }

    public void setTechRecordAdrDetailsApplicantDetailsTown(String techRecordAdrDetailsApplicantDetailsTown) {
        this.techRecordAdrDetailsApplicantDetailsTown = techRecordAdrDetailsApplicantDetailsTown;
    }

    public String getTechRecordAdrDetailsApplicantDetailsCity() {
        return techRecordAdrDetailsApplicantDetailsCity;
    }

    public void setTechRecordAdrDetailsApplicantDetailsCity(String techRecordAdrDetailsApplicantDetailsCity) {
        this.techRecordAdrDetailsApplicantDetailsCity = techRecordAdrDetailsApplicantDetailsCity;
    }

    public String getTechRecordAdrDetailsApplicantDetailsPostcode() {
        return techRecordAdrDetailsApplicantDetailsPostcode;
    }

    public void setTechRecordAdrDetailsApplicantDetailsPostcode(String techRecordAdrDetailsApplicantDetailsPostcode) {
        this.techRecordAdrDetailsApplicantDetailsPostcode = techRecordAdrDetailsApplicantDetailsPostcode;
    }

    public List<String> getTechRecordAdrDetailsMemosApply() {
        return techRecordAdrDetailsMemosApply;
    }

    public void setTechRecordAdrDetailsMemosApply(List<String> techRecordAdrDetailsMemosApply) {
        this.techRecordAdrDetailsMemosApply = techRecordAdrDetailsMemosApply;
    }

    public List<String> getTechRecordAdrDetailsDocuments() {
        return techRecordAdrDetailsDocuments;
    }

    public void setTechRecordAdrDetailsDocuments(List<String> techRecordAdrDetailsDocuments) {
        this.techRecordAdrDetailsDocuments = techRecordAdrDetailsDocuments;
    }

    public Boolean getTechRecordAdrDetailsListStatementApplicable() {
        return techRecordAdrDetailsListStatementApplicable;
    }

    public void setTechRecordAdrDetailsListStatementApplicable(Boolean techRecordAdrDetailsListStatementApplicable) {
        this.techRecordAdrDetailsListStatementApplicable = techRecordAdrDetailsListStatementApplicable;
    }

    public String getTechRecordAdrDetailsBatteryListNumber() {
        return techRecordAdrDetailsBatteryListNumber;
    }

    public void setTechRecordAdrDetailsBatteryListNumber(String techRecordAdrDetailsBatteryListNumber) {
        this.techRecordAdrDetailsBatteryListNumber = techRecordAdrDetailsBatteryListNumber;
    }

    public Boolean getTechRecordAdrDetailsBrakeDeclarationsSeen() {
        return techRecordAdrDetailsBrakeDeclarationsSeen;
    }

    public void setTechRecordAdrDetailsBrakeDeclarationsSeen(Boolean techRecordAdrDetailsBrakeDeclarationsSeen) {
        this.techRecordAdrDetailsBrakeDeclarationsSeen = techRecordAdrDetailsBrakeDeclarationsSeen;
    }

    public String getTechRecordAdrDetailsBrakeDeclarationIssuer() {
        return techRecordAdrDetailsBrakeDeclarationIssuer;
    }

    public void setTechRecordAdrDetailsBrakeDeclarationIssuer(String techRecordAdrDetailsBrakeDeclarationIssuer) {
        this.techRecordAdrDetailsBrakeDeclarationIssuer = techRecordAdrDetailsBrakeDeclarationIssuer;
    }

    public Boolean getTechRecordAdrDetailsBrakeEndurance() {
        return techRecordAdrDetailsBrakeEndurance;
    }

    public void setTechRecordAdrDetailsBrakeEndurance(Boolean techRecordAdrDetailsBrakeEndurance) {
        this.techRecordAdrDetailsBrakeEndurance = techRecordAdrDetailsBrakeEndurance;
    }

    public Double getTechRecordAdrDetailsWeight() {
        return techRecordAdrDetailsWeight;
    }

    public void setTechRecordAdrDetailsWeight(Double techRecordAdrDetailsWeight) {
        this.techRecordAdrDetailsWeight = techRecordAdrDetailsWeight;
    }

    public Boolean getTechRecordAdrDetailsDeclarationsSeen() {
        return techRecordAdrDetailsDeclarationsSeen;
    }

    public void setTechRecordAdrDetailsDeclarationsSeen(Boolean techRecordAdrDetailsDeclarationsSeen) {
        this.techRecordAdrDetailsDeclarationsSeen = techRecordAdrDetailsDeclarationsSeen;
    }

    public Boolean getTechRecordAdrDetailsM145Statement() {
        return techRecordAdrDetailsM145Statement;
    }

    public void setTechRecordAdrDetailsM145Statement(Boolean techRecordAdrDetailsM145Statement) {
        this.techRecordAdrDetailsM145Statement = techRecordAdrDetailsM145Statement;
    }

    public Boolean getTechRecordAdrDetailsNewCertificateRequested() {
        return techRecordAdrDetailsNewCertificateRequested;
    }

    public void setTechRecordAdrDetailsNewCertificateRequested(Boolean techRecordAdrDetailsNewCertificateRequested) {
        this.techRecordAdrDetailsNewCertificateRequested = techRecordAdrDetailsNewCertificateRequested;
    }

    public List<String> getTechRecordAdrDetailsAdditionalNotesNumber() {
        return techRecordAdrDetailsAdditionalNotesNumber;
    }

    public void setTechRecordAdrDetailsAdditionalNotesNumber(List<String> techRecordAdrDetailsAdditionalNotesNumber) {
        this.techRecordAdrDetailsAdditionalNotesNumber = techRecordAdrDetailsAdditionalNotesNumber;
    }

    public String getTechRecordAdrDetailsAdrTypeApprovalNo() {
        return techRecordAdrDetailsAdrTypeApprovalNo;
    }

    public void setTechRecordAdrDetailsAdrTypeApprovalNo(String techRecordAdrDetailsAdrTypeApprovalNo) {
        this.techRecordAdrDetailsAdrTypeApprovalNo = techRecordAdrDetailsAdrTypeApprovalNo;
    }

    public String getTechRecordAdrDetailsAdrCertificateNotes() {
        return techRecordAdrDetailsAdrCertificateNotes;
    }

    public void setTechRecordAdrDetailsAdrCertificateNotes(String techRecordAdrDetailsAdrCertificateNotes) {
        this.techRecordAdrDetailsAdrCertificateNotes = techRecordAdrDetailsAdrCertificateNotes;
    }

    public String getTechRecordAdrDetailsTankTankDetailsTankManufacturer() {
        return techRecordAdrDetailsTankTankDetailsTankManufacturer;
    }

    public void setTechRecordAdrDetailsTankTankDetailsTankManufacturer(
            String techRecordAdrDetailsTankTankDetailsTankManufacturer) {
        this.techRecordAdrDetailsTankTankDetailsTankManufacturer = techRecordAdrDetailsTankTankDetailsTankManufacturer;
    }

    public Integer getTechRecordAdrDetailsTankTankDetailsYearOfManufacture() {
        return techRecordAdrDetailsTankTankDetailsYearOfManufacture;
    }

    public void setTechRecordAdrDetailsTankTankDetailsYearOfManufacture(
            Integer techRecordAdrDetailsTankTankDetailsYearOfManufacture) {
        this.techRecordAdrDetailsTankTankDetailsYearOfManufacture = techRecordAdrDetailsTankTankDetailsYearOfManufacture;
    }

    public String getTechRecordAdrDetailsTankTankDetailsTankManufacturerSerialNo() {
        return techRecordAdrDetailsTankTankDetailsTankManufacturerSerialNo;
    }

    public void setTechRecordAdrDetailsTankTankDetailsTankManufacturerSerialNo(
            String techRecordAdrDetailsTankTankDetailsTankManufacturerSerialNo) {
        this.techRecordAdrDetailsTankTankDetailsTankManufacturerSerialNo = techRecordAdrDetailsTankTankDetailsTankManufacturerSerialNo;
    }

    public String getTechRecordAdrDetailsTankTankDetailsTankTypeAppNo() {
        return techRecordAdrDetailsTankTankDetailsTankTypeAppNo;
    }

    public void setTechRecordAdrDetailsTankTankDetailsTankTypeAppNo(
            String techRecordAdrDetailsTankTankDetailsTankTypeAppNo) {
        this.techRecordAdrDetailsTankTankDetailsTankTypeAppNo = techRecordAdrDetailsTankTankDetailsTankTypeAppNo;
    }

    public String getTechRecordAdrDetailsTankTankDetailsTankCode() {
        return techRecordAdrDetailsTankTankDetailsTankCode;
    }

    public void setTechRecordAdrDetailsTankTankDetailsTankCode(String techRecordAdrDetailsTankTankDetailsTankCode) {
        this.techRecordAdrDetailsTankTankDetailsTankCode = techRecordAdrDetailsTankTankDetailsTankCode;
    }

    public String getTechRecordAdrDetailsTankTankDetailsSpecialProvisions() {
        return techRecordAdrDetailsTankTankDetailsSpecialProvisions;
    }

    public void setTechRecordAdrDetailsTankTankDetailsSpecialProvisions(
            String techRecordAdrDetailsTankTankDetailsSpecialProvisions) {
        this.techRecordAdrDetailsTankTankDetailsSpecialProvisions = techRecordAdrDetailsTankTankDetailsSpecialProvisions;
    }

    public String getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type() {
        return techRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type;
    }

    public void setTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type(
            String techRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type) {
        this.techRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type = techRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type;
    }

    public String getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo() {
        return techRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo;
    }

    public void setTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo(
            String techRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo) {
        this.techRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo = techRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo;
    }

    public String getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate() {
        return techRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate;
    }

    public void setTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate(
            String techRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate) {
        this.techRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate = techRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate;
    }

    public List<TechRecordAdrDetailsTankTankDetailsTc3Detail> getTechRecordAdrDetailsTankTankDetailsTc3Details() {
        return techRecordAdrDetailsTankTankDetailsTc3Details;
    }

    public void setTechRecordAdrDetailsTankTankDetailsTc3Details(
            List<TechRecordAdrDetailsTankTankDetailsTc3Detail> techRecordAdrDetailsTankTankDetailsTc3Details) {
        this.techRecordAdrDetailsTankTankDetailsTc3Details = techRecordAdrDetailsTankTankDetailsTc3Details;
    }

    public String getTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted() {
        return techRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted;
    }

    public void setTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted(
            String techRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted) {
        this.techRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted = techRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted;
    }

    public String getTechRecordAdrDetailsTankTankDetailsTankStatementSelect() {
        return techRecordAdrDetailsTankTankDetailsTankStatementSelect;
    }

    public void setTechRecordAdrDetailsTankTankDetailsTankStatementSelect(
            String techRecordAdrDetailsTankTankDetailsTankStatementSelect) {
        this.techRecordAdrDetailsTankTankDetailsTankStatementSelect = techRecordAdrDetailsTankTankDetailsTankStatementSelect;
    }

    public String getTechRecordAdrDetailsTankTankDetailsTankStatementStatement() {
        return techRecordAdrDetailsTankTankDetailsTankStatementStatement;
    }

    public void setTechRecordAdrDetailsTankTankDetailsTankStatementStatement(
            String techRecordAdrDetailsTankTankDetailsTankStatementStatement) {
        this.techRecordAdrDetailsTankTankDetailsTankStatementStatement = techRecordAdrDetailsTankTankDetailsTankStatementStatement;
    }

    public String getTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo() {
        return techRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo;
    }

    public void setTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo(
            String techRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo) {
        this.techRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo = techRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo;
    }

    public List<String> getTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo() {
        return techRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo;
    }

    public void setTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo(
            List<String> techRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo) {
        this.techRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo = techRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo;
    }

    public String getTechRecordAdrDetailsTankTankDetailsTankStatementProductList() {
        return techRecordAdrDetailsTankTankDetailsTankStatementProductList;
    }

    public void setTechRecordAdrDetailsTankTankDetailsTankStatementProductList(
            String techRecordAdrDetailsTankTankDetailsTankStatementProductList) {
        this.techRecordAdrDetailsTankTankDetailsTankStatementProductList = techRecordAdrDetailsTankTankDetailsTankStatementProductList;
    }
    public List<TechRecordAdrPassCertificateDetail> getTechRecordAdrPassCertificateDetails() {
        return techRecordAdrPassCertificateDetails;
    }

    public void setTechRecordAdrPassCertificateDetails(
            List<TechRecordAdrPassCertificateDetail> techRecordAdrPassCertificateDetails) {
        this.techRecordAdrPassCertificateDetails = techRecordAdrPassCertificateDetails;
    }

    @Generated("jsonschema2pojo")
    public enum TechRecordStatusCode {

        @SerializedName("provisional")
        PROVISIONAL("provisional"), @SerializedName("current")
        CURRENT("current"),
        @SerializedName("archived")
        ARCHIVED("archived");

        private final static Map<String, AdrRemediationClass.TechRecordStatusCode> CONSTANTS = new HashMap<String, AdrRemediationClass.TechRecordStatusCode>();

        static {
            for (AdrRemediationClass.TechRecordStatusCode c : values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private final String value;

        TechRecordStatusCode(String value) {
            this.value = value;
        }

        public static AdrRemediationClass.TechRecordStatusCode fromValue(String value) {
            AdrRemediationClass.TechRecordStatusCode constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

        public String value() {
            return this.value;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdrRemediationClass)) return false;
        AdrRemediationClass that = (AdrRemediationClass) o;

        return Objects.equals(
                //these are used to define api endpoint in remediation app but are not part of the data
                //getSystem_Number(), that.getSystem_Number())
                //&& Objects.equals(getCreated_Timestamp(), that.getCreated_Timestamp())

                getTechRecordAdrDetailsDangerousGoods(), that.getTechRecordAdrDetailsDangerousGoods())
                && Objects.equals(getTechRecordAdrDetailsVehicleDetailsType(), that.getTechRecordAdrDetailsVehicleDetailsType())
                && Objects.equals(getTechRecordAdrDetailsVehicleDetailsApprovalDate(), that.getTechRecordAdrDetailsVehicleDetailsApprovalDate())
                && Objects.equals(getTechRecordAdrDetailsPermittedDangerousGoods(), that.getTechRecordAdrDetailsPermittedDangerousGoods())
                && Objects.equals(getTechRecordAdrDetailsCompatibilityGroupJ(), that.getTechRecordAdrDetailsCompatibilityGroupJ())
                && Objects.equals(getTechRecordAdrDetailsAdditionalExaminerNotes(), that.getTechRecordAdrDetailsAdditionalExaminerNotes())
                && Objects.equals(getTechRecordAdrDetailsApplicantDetailsName(), that.getTechRecordAdrDetailsApplicantDetailsName())
                && Objects.equals(getTechRecordAdrDetailsApplicantDetailsStreet(), that.getTechRecordAdrDetailsApplicantDetailsStreet())
                && Objects.equals(getTechRecordAdrDetailsApplicantDetailsTown(), that.getTechRecordAdrDetailsApplicantDetailsTown())
                && Objects.equals(getTechRecordAdrDetailsApplicantDetailsCity(), that.getTechRecordAdrDetailsApplicantDetailsCity())
                && Objects.equals(getTechRecordAdrDetailsApplicantDetailsPostcode(), that.getTechRecordAdrDetailsApplicantDetailsPostcode())
                && Objects.equals(getTechRecordAdrDetailsMemosApply(), that.getTechRecordAdrDetailsMemosApply())
                && Objects.equals(getTechRecordAdrDetailsDocuments(), that.getTechRecordAdrDetailsDocuments())
                && Objects.equals(getTechRecordAdrDetailsListStatementApplicable(), that.getTechRecordAdrDetailsListStatementApplicable())
                && Objects.equals(getTechRecordAdrDetailsBatteryListNumber(), that.getTechRecordAdrDetailsBatteryListNumber())
                && Objects.equals(getTechRecordAdrDetailsBrakeDeclarationsSeen(), that.getTechRecordAdrDetailsBrakeDeclarationsSeen())
                && Objects.equals(getTechRecordAdrDetailsBrakeDeclarationIssuer(), that.getTechRecordAdrDetailsBrakeDeclarationIssuer())
                && Objects.equals(getTechRecordAdrDetailsBrakeEndurance(), that.getTechRecordAdrDetailsBrakeEndurance())
                && Objects.equals(getTechRecordAdrDetailsWeight(), that.getTechRecordAdrDetailsWeight())
                && Objects.equals(getTechRecordAdrDetailsDeclarationsSeen(), that.getTechRecordAdrDetailsDeclarationsSeen())
                && Objects.equals(getTechRecordAdrDetailsM145Statement(), that.getTechRecordAdrDetailsM145Statement())
                && Objects.equals(getTechRecordAdrDetailsNewCertificateRequested(), that.getTechRecordAdrDetailsNewCertificateRequested())
                && Objects.equals(getTechRecordAdrDetailsAdditionalNotesNumber(), that.getTechRecordAdrDetailsAdditionalNotesNumber())
                && Objects.equals(getTechRecordAdrDetailsAdrTypeApprovalNo(), that.getTechRecordAdrDetailsAdrTypeApprovalNo())
                && Objects.equals(getTechRecordAdrDetailsAdrCertificateNotes(), that.getTechRecordAdrDetailsAdrCertificateNotes())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsTankManufacturer(), that.getTechRecordAdrDetailsTankTankDetailsTankManufacturer())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsYearOfManufacture(), that.getTechRecordAdrDetailsTankTankDetailsYearOfManufacture())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsTankManufacturerSerialNo(), that.getTechRecordAdrDetailsTankTankDetailsTankManufacturerSerialNo())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsTankTypeAppNo(), that.getTechRecordAdrDetailsTankTankDetailsTankTypeAppNo())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsTankCode(), that.getTechRecordAdrDetailsTankTankDetailsTankCode())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsSpecialProvisions(), that.getTechRecordAdrDetailsTankTankDetailsSpecialProvisions())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type(), that.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo(), that.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate(), that.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsTc3Details(), that.getTechRecordAdrDetailsTankTankDetailsTc3Details())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted(), that.getTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsTankStatementSelect(), that.getTechRecordAdrDetailsTankTankDetailsTankStatementSelect())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsTankStatementStatement(), that.getTechRecordAdrDetailsTankTankDetailsTankStatementStatement())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo(), that.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo(), that.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsTankStatementProductList(), that.getTechRecordAdrDetailsTankTankDetailsTankStatementProductList())
                && Objects.equals(getTechRecordAdrPassCertificateDetails(), that.getTechRecordAdrPassCertificateDetails());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                //getSystem_Number(),
                //getCreated_Timestamp(),
                getTechRecordAdrDetailsDangerousGoods(),
                getTechRecordAdrDetailsVehicleDetailsType(),
                getTechRecordAdrDetailsVehicleDetailsApprovalDate(),
                getTechRecordAdrDetailsPermittedDangerousGoods(),
                getTechRecordAdrDetailsCompatibilityGroupJ(),
                getTechRecordAdrDetailsAdditionalExaminerNotes(),
                getTechRecordAdrDetailsApplicantDetailsName(),
                getTechRecordAdrDetailsApplicantDetailsStreet(),
                getTechRecordAdrDetailsApplicantDetailsTown(),
                getTechRecordAdrDetailsApplicantDetailsCity(),
                getTechRecordAdrDetailsApplicantDetailsPostcode(),
                getTechRecordAdrDetailsMemosApply(),
                getTechRecordAdrDetailsDocuments(),
                getTechRecordAdrDetailsListStatementApplicable(),
                getTechRecordAdrDetailsBatteryListNumber(),
                getTechRecordAdrDetailsBrakeDeclarationsSeen(),
                getTechRecordAdrDetailsBrakeDeclarationIssuer(),
                getTechRecordAdrDetailsBrakeEndurance(),
                getTechRecordAdrDetailsWeight(),
                getTechRecordAdrDetailsDeclarationsSeen(),
                getTechRecordAdrDetailsM145Statement(),
                getTechRecordAdrDetailsNewCertificateRequested(),
                getTechRecordAdrDetailsAdditionalNotesNumber(),
                getTechRecordAdrDetailsAdrTypeApprovalNo(),
                getTechRecordAdrDetailsAdrCertificateNotes(),
                getTechRecordAdrDetailsTankTankDetailsTankManufacturer(),
                getTechRecordAdrDetailsTankTankDetailsYearOfManufacture(),
                getTechRecordAdrDetailsTankTankDetailsTankManufacturerSerialNo(),
                getTechRecordAdrDetailsTankTankDetailsTankTypeAppNo(),
                getTechRecordAdrDetailsTankTankDetailsTankCode(),
                getTechRecordAdrDetailsTankTankDetailsSpecialProvisions(),
                getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type(),
                getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo(),
                getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate(),
                getTechRecordAdrDetailsTankTankDetailsTc3Details(),
                getTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted(),
                getTechRecordAdrDetailsTankTankDetailsTankStatementSelect(),
                getTechRecordAdrDetailsTankTankDetailsTankStatementStatement(),
                getTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo(),
                getTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo(),
                getTechRecordAdrDetailsTankTankDetailsTankStatementProductList(),
                getTechRecordAdrPassCertificateDetails());
    }

    @Override
    public String toString() {
        return "AdrRemediationClass [system_Number=" + system_Number + ", created_Timestamp=" + created_Timestamp
                + ", techRecordAdrDetailsDangerousGoods=" + techRecordAdrDetailsDangerousGoods
                + ", techRecordAdrDetailsVehicleDetailsType=" + techRecordAdrDetailsVehicleDetailsType
                + ", techRecordAdrDetailsVehicleDetailsApprovalDate=" + techRecordAdrDetailsVehicleDetailsApprovalDate
                + ", techRecordAdrDetailsPermittedDangerousGoods=" + techRecordAdrDetailsPermittedDangerousGoods
                + ", techRecordAdrDetailsCompatibilityGroupJ=" + techRecordAdrDetailsCompatibilityGroupJ
                + ", techRecordAdrDetailsAdditionalExaminerNotes=" + techRecordAdrDetailsAdditionalExaminerNotes
                + ", techRecordAdrDetailsApplicantDetailsName=" + techRecordAdrDetailsApplicantDetailsName
                + ", techRecordAdrDetailsApplicantDetailsStreet=" + techRecordAdrDetailsApplicantDetailsStreet
                + ", techRecordAdrDetailsApplicantDetailsTown=" + techRecordAdrDetailsApplicantDetailsTown
                + ", techRecordAdrDetailsApplicantDetailsCity=" + techRecordAdrDetailsApplicantDetailsCity
                + ", techRecordAdrDetailsApplicantDetailsPostcode=" + techRecordAdrDetailsApplicantDetailsPostcode
                + ", techRecordAdrDetailsMemosApply=" + techRecordAdrDetailsMemosApply
                + ", techRecordAdrDetailsDocuments=" + techRecordAdrDetailsDocuments
                + ", techRecordAdrDetailsListStatementApplicable=" + techRecordAdrDetailsListStatementApplicable
                + ", techRecordAdrDetailsBatteryListNumber=" + techRecordAdrDetailsBatteryListNumber
                + ", techRecordAdrDetailsBrakeDeclarationsSeen=" + techRecordAdrDetailsBrakeDeclarationsSeen
                + ", techRecordAdrDetailsBrakeDeclarationIssuer=" + techRecordAdrDetailsBrakeDeclarationIssuer
                + ", techRecordAdrDetailsBrakeEndurance=" + techRecordAdrDetailsBrakeEndurance
                + ", techRecordAdrDetailsWeight=" + techRecordAdrDetailsWeight
                + ", techRecordAdrDetailsDeclarationsSeen=" + techRecordAdrDetailsDeclarationsSeen
                + ", techRecordAdrDetailsM145Statement=" + techRecordAdrDetailsM145Statement
                + ", techRecordAdrDetailsNewCertificateRequested=" + techRecordAdrDetailsNewCertificateRequested
                + ", techRecordAdrDetailsAdditionalNotesNumber=" + techRecordAdrDetailsAdditionalNotesNumber
                + ", techRecordAdrDetailsAdrTypeApprovalNo=" + techRecordAdrDetailsAdrTypeApprovalNo
                + ", techRecordAdrDetailsAdrCertificateNotes=" + techRecordAdrDetailsAdrCertificateNotes
                + ", techRecordAdrDetailsTankTankDetailsTankManufacturer="
                + techRecordAdrDetailsTankTankDetailsTankManufacturer
                + ", techRecordAdrDetailsTankTankDetailsYearOfManufacture="
                + techRecordAdrDetailsTankTankDetailsYearOfManufacture
                + ", techRecordAdrDetailsTankTankDetailsTankManufacturerSerialNo="
                + techRecordAdrDetailsTankTankDetailsTankManufacturerSerialNo
                + ", techRecordAdrDetailsTankTankDetailsTankTypeAppNo="
                + techRecordAdrDetailsTankTankDetailsTankTypeAppNo + ", techRecordAdrDetailsTankTankDetailsTankCode="
                + techRecordAdrDetailsTankTankDetailsTankCode
                + ", techRecordAdrDetailsTankTankDetailsSpecialProvisions="
                + techRecordAdrDetailsTankTankDetailsSpecialProvisions
                + ", techRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type="
                + techRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type
                + ", techRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo="
                + techRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo
                + ", techRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate="
                + techRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate
                + ", techRecordAdrDetailsTankTankDetailsTc3Details=" + techRecordAdrDetailsTankTankDetailsTc3Details
                + ", techRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted="
                + techRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted
                + ", techRecordAdrDetailsTankTankDetailsTankStatementSelect="
                + techRecordAdrDetailsTankTankDetailsTankStatementSelect
                + ", techRecordAdrDetailsTankTankDetailsTankStatementStatement="
                + techRecordAdrDetailsTankTankDetailsTankStatementStatement
                + ", techRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo="
                + techRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo
                + ", techRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo="
                + techRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo
                + ", techRecordAdrDetailsTankTankDetailsTankStatementProductList="
                + techRecordAdrDetailsTankTankDetailsTankStatementProductList + ", techRecordAdrPassCertificateDetails="
                + techRecordAdrPassCertificateDetails + "]";
    }
}
