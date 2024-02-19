package vott.models.dto.techrecordsv3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Tech Record PUT HGV Complete
 * <p>
 */
@Generated("jsonschema2pojo")
public class TechRecordHgvComplete extends TechRecordV3 {
    /**
     * (Required)
     * techRecord_reasonForCreation
     * techRecord_statusCode
     * techRecord_vehicleType
     * vin
     * techRecord_vehicleConfiguration
     * techRecord_vehicleClass_description
     * techRecord_approvalType
     * techRecord_manufactureYear
     * techRecord_bodyType_code
     * techRecord_bodyType_description
     * techRecord_grossGbWeight
     * techRecord_grossDesignWeight
     * techRecord_brakes_dtpNumber
     * techRecord_euVehicleCategory
     * techRecord_axles
     * techRecord_euroStandard
     * techRecord_regnDate
     * techRecord_speedLimiterMrk
     * techRecord_tachoExemptMrk
     * techRecord_fuelPropulsionSystem
     * techRecord_make
     * techRecord_model
     * techRecord_trainGbWeight
     * techRecord_maxTrainGbWeight
     * techRecord_tyreUseCode
     * techRecord_dimensions_length
     * techRecord_dimensions_width
     * techRecord_frontAxleTo5thWheelMin
     * techRecord_frontAxleTo5thWheelMax
     * techRecord_frontAxleToRearAxle
     * techRecord_notes
     * techRecord_roadFriendly
     * techRecord_drawbarCouplingFitted
     * techRecord_offRoad
     * techRecord_applicantDetails_name
     * techRecord_applicantDetails_address1
     * techRecord_applicantDetails_address2
     * techRecord_applicantDetails_postTown
     */

    @Expose
    @SerializedName("systemNumber")
    private String systemNumber;
    @Expose
    @SerializedName("createdTimestamp")
    private String createdTimestamp;
    @SerializedName("secondaryVrms")
    @Expose
    private List<String> secondaryVrms;
    @SerializedName("partialVin")
    @Expose
    private String partialVin;
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
    @SerializedName("techRecord_alterationMarker")
    @Expose
    private Boolean techRecordAlterationMarker;
    @SerializedName("techRecord_adrPassCertificateDetails")
    @Expose
    private List<TechRecordAdrPassCertificateDetail> techRecordAdrPassCertificateDetails;

    @SerializedName("techRecord_applicantDetails_name")
    @Expose
    private String techRecordApplicantDetailsName;

    @SerializedName("techRecord_applicantDetails_address1")
    @Expose
    private String techRecordApplicantDetailsAddress1;

    @SerializedName("techRecord_applicantDetails_address2")
    @Expose
    private String techRecordApplicantDetailsAddress2;

    @SerializedName("techRecord_applicantDetails_postTown")
    @Expose
    private String techRecordApplicantDetailsPostTown;
    @SerializedName("techRecord_applicantDetails_address3")
    @Expose
    private String techRecordApplicantDetailsAddress3;
    @SerializedName("techRecord_applicantDetails_postCode")
    @Expose
    private String techRecordApplicantDetailsPostCode;
    @SerializedName("techRecord_applicantDetails_telephoneNumber")
    @Expose
    private String techRecordApplicantDetailsTelephoneNumber;
    @SerializedName("techRecord_applicantDetails_emailAddress")
    @Expose
    private String techRecordApplicantDetailsEmailAddress;
    @SerializedName("techRecord_applicationId")
    @Expose
    private String techRecordApplicationId;

    @SerializedName("techRecord_axles")
    @Expose
    private List<TechRecordAxle> techRecordAxles;

    @SerializedName("techRecord_bodyType_code")
    @Expose
    private String techRecordBodyTypeCode;

    @SerializedName("techRecord_bodyType_description")
    @Expose
    private String techRecordBodyTypeDescription;

    @SerializedName("techRecord_brakes_dtpNumber")
    @Expose
    private String techRecordBrakesDtpNumber;
    @SerializedName("techRecord_brakes_loadSensingValve")
    @Expose
    private Boolean techRecordBrakesLoadSensingValve;
    @SerializedName("techRecord_conversionRefNo")
    @Expose
    private String techRecordConversionRefNo;
    @SerializedName("techRecord_departmentalVehicleMarker")
    @Expose
    private Boolean techRecordDepartmentalVehicleMarker;
    @SerializedName("techRecord_dimensions_axleSpacing")
    @Expose
    private List<TechRecordDimensionsAxleSpacing> techRecordDimensionsAxleSpacing;

    @SerializedName("techRecord_dimensions_length")
    @Expose
    private Integer techRecordDimensionsLength;

    @SerializedName("techRecord_dimensions_width")
    @Expose
    private Integer techRecordDimensionsWidth;

    @SerializedName("techRecord_drawbarCouplingFitted")
    @Expose
    private Boolean techRecordDrawbarCouplingFitted;
    @SerializedName("techRecord_emissionsLimit")
    @Expose
    private Double techRecordEmissionsLimit;

    @SerializedName("techRecord_euroStandard")
    @Expose
    private String techRecordEuroStandard;
    /**
     * EU vehicle category
     * <p>
     * <p>
     * (Required)
     */
    @SerializedName("techRecord_euVehicleCategory")
    @Expose
    private TechRecordHgvComplete.TechRecordEuVehicleCategory techRecordEuVehicleCategory;

    @SerializedName("techRecord_frontAxleToRearAxle")
    @Expose
    private Integer techRecordFrontAxleToRearAxle;

    @SerializedName("techRecord_frontAxleTo5thWheelMin")
    @Expose
    private Integer techRecordFrontAxleTo5thWheelMin;

    @SerializedName("techRecord_frontAxleTo5thWheelMax")
    @Expose
    private Integer techRecordFrontAxleTo5thWheelMax;
    @SerializedName("techRecord_frontVehicleTo5thWheelCouplingMin")
    @Expose
    private Integer techRecordFrontVehicleTo5thWheelCouplingMin;
    @SerializedName("techRecord_frontVehicleTo5thWheelCouplingMax")
    @Expose
    private Integer techRecordFrontVehicleTo5thWheelCouplingMax;

    @SerializedName("techRecord_fuelPropulsionSystem")
    @Expose
    private Object techRecordFuelPropulsionSystem;
    @SerializedName("techRecord_functionCode")
    @Expose
    private String techRecordFunctionCode;

    @SerializedName("techRecord_grossDesignWeight")
    @Expose
    private Integer techRecordGrossDesignWeight;
    @SerializedName("techRecord_grossEecWeight")
    @Expose
    private Integer techRecordGrossEecWeight;

    @SerializedName("techRecord_grossGbWeight")
    @Expose
    private Integer techRecordGrossGbWeight;

    @SerializedName("techRecord_make")
    @Expose
    private String techRecordMake;

    @SerializedName("techRecord_maxTrainGbWeight")
    @Expose
    private Double techRecordMaxTrainGbWeight;
    @SerializedName("techRecord_maxTrainEecWeight")
    @Expose
    private Double techRecordMaxTrainEecWeight;
    @SerializedName("techRecord_maxTrainDesignWeight")
    @Expose
    private Double techRecordMaxTrainDesignWeight;

    @SerializedName("techRecord_manufactureYear")
    @Expose
    private Integer techRecordManufactureYear;
    @SerializedName("techRecord_microfilm_microfilmDocumentType")
    @Expose
    private Object techRecordMicrofilmMicrofilmDocumentType;
    @SerializedName("techRecord_microfilm_microfilmRollNumber")
    @Expose
    private String techRecordMicrofilmMicrofilmRollNumber;
    @SerializedName("techRecord_microfilm_microfilmSerialNumber")
    @Expose
    private String techRecordMicrofilmMicrofilmSerialNumber;

    @SerializedName("techRecord_model")
    @Expose
    private String techRecordModel;
    @SerializedName("techRecord_noOfAxles")
    @Expose
    private Object techRecordNoOfAxles;

    @SerializedName("techRecord_notes")
    @Expose
    private String techRecordNotes;

    @SerializedName("techRecord_offRoad")
    @Expose
    private Boolean techRecordOffRoad;
    @SerializedName("techRecord_plates")
    @Expose
    private List<TechRecordPlate> techRecordPlates;

    @SerializedName("techRecord_reasonForCreation")
    @Expose
    private String techRecordReasonForCreation;

    @SerializedName("techRecord_regnDate")
    @Expose
    private Object techRecordRegnDate;

    @SerializedName("techRecord_roadFriendly")
    @Expose
    private Boolean techRecordRoadFriendly;
    /**
     * Status Code
     * <p>
     * <p>
     * (Required)
     */
    @SerializedName("techRecord_statusCode")
    @Expose
    private TechRecordHgvComplete.TechRecordStatusCode techRecordStatusCode;

    @SerializedName("techRecord_speedLimiterMrk")
    @Expose
    private Boolean techRecordSpeedLimiterMrk;

    @SerializedName("techRecord_tachoExemptMrk")
    @Expose
    private Boolean techRecordTachoExemptMrk;
    @SerializedName("techRecord_trainDesignWeight")
    @Expose
    private Integer techRecordTrainDesignWeight;
    @SerializedName("techRecord_trainEecWeight")
    @Expose
    private Double techRecordTrainEecWeight;

    @SerializedName("techRecord_trainGbWeight")
    @Expose
    private Double techRecordTrainGbWeight;

    @SerializedName("techRecord_tyreUseCode")
    @Expose
    private Object techRecordTyreUseCode;

    @SerializedName("techRecord_vehicleClass_description")
    @Expose
    private String techRecordVehicleClassDescription;

    @SerializedName("techRecord_vehicleConfiguration")
    @Expose
    private String techRecordVehicleConfiguration;

    @SerializedName("techRecord_approvalType")
    @Expose
    private String techRecordApprovalType;
    @SerializedName("techRecord_approvalTypeNumber")
    @Expose
    private String techRecordApprovalTypeNumber;
    @SerializedName("techRecord_ntaNumber")
    @Expose
    private String techRecordNtaNumber;
    @SerializedName("techRecord_variantNumber")
    @Expose
    private String techRecordVariantNumber;
    @SerializedName("techRecord_variantVersionNumber")
    @Expose
    private String techRecordVariantVersionNumber;

    @SerializedName("techRecord_vehicleType")
    @Expose
    private String techRecordVehicleType;
    @SerializedName("primaryVrm")
    @Expose
    private String primaryVrm;

    @SerializedName("vin")
    @Expose
    private String vin;
    @SerializedName("techRecord_hiddenInVta")
    @Expose
    private Boolean techRecordHiddenInVta;
    @SerializedName("techRecord_updateType")
    @Expose
    private String techRecordUpdateType;

    public String getSystemNumber() {
        return systemNumber;
    }

    public void setSystemNumber(String systemNumber) {
        this.systemNumber = systemNumber;
    }

    public String getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(String createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public List<String> getSecondaryVrms() {
        return secondaryVrms;
    }

    public void setSecondaryVrms(List<String> secondaryVrms) {
        this.secondaryVrms = secondaryVrms;
    }

    public String getPartialVin() {
        return partialVin;
    }

    public void setPartialVin(String partialVin) {
        this.partialVin = partialVin;
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

    public Boolean getTechRecordAlterationMarker() {
        return techRecordAlterationMarker;
    }

    public void setTechRecordAlterationMarker(Boolean techRecordAlterationMarker) {
        this.techRecordAlterationMarker = techRecordAlterationMarker;
    }

    public List<TechRecordAdrPassCertificateDetail> getTechRecordAdrPassCertificateDetails() {
        return techRecordAdrPassCertificateDetails;
    }

    public void setTechRecordAdrPassCertificateDetails(
            List<TechRecordAdrPassCertificateDetail> techRecordAdrPassCertificateDetails) {
        this.techRecordAdrPassCertificateDetails = techRecordAdrPassCertificateDetails;
    }

    public String getTechRecordApplicantDetailsName() {
        return techRecordApplicantDetailsName;
    }

    public void setTechRecordApplicantDetailsName(String techRecordApplicantDetailsName) {
        this.techRecordApplicantDetailsName = techRecordApplicantDetailsName;
    }

    public String getTechRecordApplicantDetailsAddress1() {
        return techRecordApplicantDetailsAddress1;
    }

    public void setTechRecordApplicantDetailsAddress1(String techRecordApplicantDetailsAddress1) {
        this.techRecordApplicantDetailsAddress1 = techRecordApplicantDetailsAddress1;
    }

    public String getTechRecordApplicantDetailsAddress2() {
        return techRecordApplicantDetailsAddress2;
    }

    public void setTechRecordApplicantDetailsAddress2(String techRecordApplicantDetailsAddress2) {
        this.techRecordApplicantDetailsAddress2 = techRecordApplicantDetailsAddress2;
    }

    public String getTechRecordApplicantDetailsPostTown() {
        return techRecordApplicantDetailsPostTown;
    }

    public void setTechRecordApplicantDetailsPostTown(String techRecordApplicantDetailsPostTown) {
        this.techRecordApplicantDetailsPostTown = techRecordApplicantDetailsPostTown;
    }

    public String getTechRecordApplicantDetailsAddress3() {
        return techRecordApplicantDetailsAddress3;
    }

    public void setTechRecordApplicantDetailsAddress3(String techRecordApplicantDetailsAddress3) {
        this.techRecordApplicantDetailsAddress3 = techRecordApplicantDetailsAddress3;
    }

    public String getTechRecordApplicantDetailsPostCode() {
        return techRecordApplicantDetailsPostCode;
    }

    public void setTechRecordApplicantDetailsPostCode(String techRecordApplicantDetailsPostCode) {
        this.techRecordApplicantDetailsPostCode = techRecordApplicantDetailsPostCode;
    }

    public String getTechRecordApplicantDetailsTelephoneNumber() {
        return techRecordApplicantDetailsTelephoneNumber;
    }

    public void setTechRecordApplicantDetailsTelephoneNumber(String techRecordApplicantDetailsTelephoneNumber) {
        this.techRecordApplicantDetailsTelephoneNumber = techRecordApplicantDetailsTelephoneNumber;
    }

    public String getTechRecordApplicantDetailsEmailAddress() {
        return techRecordApplicantDetailsEmailAddress;
    }

    public void setTechRecordApplicantDetailsEmailAddress(String techRecordApplicantDetailsEmailAddress) {
        this.techRecordApplicantDetailsEmailAddress = techRecordApplicantDetailsEmailAddress;
    }

    public String getTechRecordApplicationId() {
        return techRecordApplicationId;
    }

    public void setTechRecordApplicationId(String techRecordApplicationId) {
        this.techRecordApplicationId = techRecordApplicationId;
    }

    public List<TechRecordAxle> getTechRecordAxles() {
        return techRecordAxles;
    }

    public void setTechRecordAxles(List<TechRecordAxle> techRecordAxles) {
        this.techRecordAxles = techRecordAxles;
    }

    public String getTechRecordBodyTypeCode() {
        return techRecordBodyTypeCode;
    }

    public void setTechRecordBodyTypeCode(String techRecordBodyTypeCode) {
        this.techRecordBodyTypeCode = techRecordBodyTypeCode;
    }

    public String getTechRecordBodyTypeDescription() {
        return techRecordBodyTypeDescription;
    }

    public void setTechRecordBodyTypeDescription(String techRecordBodyTypeDescription) {
        this.techRecordBodyTypeDescription = techRecordBodyTypeDescription;
    }

    public String getTechRecordBrakesDtpNumber() {
        return techRecordBrakesDtpNumber;
    }

    public void setTechRecordBrakesDtpNumber(String techRecordBrakesDtpNumber) {
        this.techRecordBrakesDtpNumber = techRecordBrakesDtpNumber;
    }

    public Boolean getTechRecordBrakesLoadSensingValve() {
        return techRecordBrakesLoadSensingValve;
    }

    public void setTechRecordBrakesLoadSensingValve(Boolean techRecordBrakesLoadSensingValve) {
        this.techRecordBrakesLoadSensingValve = techRecordBrakesLoadSensingValve;
    }

    public String getTechRecordConversionRefNo() {
        return techRecordConversionRefNo;
    }

    public void setTechRecordConversionRefNo(String techRecordConversionRefNo) {
        this.techRecordConversionRefNo = techRecordConversionRefNo;
    }

    public Boolean getTechRecordDepartmentalVehicleMarker() {
        return techRecordDepartmentalVehicleMarker;
    }

    public void setTechRecordDepartmentalVehicleMarker(Boolean techRecordDepartmentalVehicleMarker) {
        this.techRecordDepartmentalVehicleMarker = techRecordDepartmentalVehicleMarker;
    }

    public List<TechRecordDimensionsAxleSpacing> getTechRecordDimensionsAxleSpacing() {
        return techRecordDimensionsAxleSpacing;
    }

    public void setTechRecordDimensionsAxleSpacing(
            List<TechRecordDimensionsAxleSpacing> techRecordDimensionsAxleSpacing) {
        this.techRecordDimensionsAxleSpacing = techRecordDimensionsAxleSpacing;
    }

    public Integer getTechRecordDimensionsLength() {
        return techRecordDimensionsLength;
    }

    public void setTechRecordDimensionsLength(Integer techRecordDimensionsLength) {
        this.techRecordDimensionsLength = techRecordDimensionsLength;
    }

    public Integer getTechRecordDimensionsWidth() {
        return techRecordDimensionsWidth;
    }

    public void setTechRecordDimensionsWidth(Integer techRecordDimensionsWidth) {
        this.techRecordDimensionsWidth = techRecordDimensionsWidth;
    }

    public Boolean getTechRecordDrawbarCouplingFitted() {
        return techRecordDrawbarCouplingFitted;
    }

    public void setTechRecordDrawbarCouplingFitted(Boolean techRecordDrawbarCouplingFitted) {
        this.techRecordDrawbarCouplingFitted = techRecordDrawbarCouplingFitted;
    }

    public Double getTechRecordEmissionsLimit() {
        return techRecordEmissionsLimit;
    }

    public void setTechRecordEmissionsLimit(Double techRecordEmissionsLimit) {
        this.techRecordEmissionsLimit = techRecordEmissionsLimit;
    }

    public String getTechRecordEuroStandard() {
        return techRecordEuroStandard;
    }

    public void setTechRecordEuroStandard(String techRecordEuroStandard) {
        this.techRecordEuroStandard = techRecordEuroStandard;
    }

    public TechRecordHgvComplete.TechRecordEuVehicleCategory getTechRecordEuVehicleCategory() {
        return techRecordEuVehicleCategory;
    }

    public void setTechRecordEuVehicleCategory(
            TechRecordHgvComplete.TechRecordEuVehicleCategory techRecordEuVehicleCategory) {
        this.techRecordEuVehicleCategory = techRecordEuVehicleCategory;
    }

    public Integer getTechRecordFrontAxleToRearAxle() {
        return techRecordFrontAxleToRearAxle;
    }

    public void setTechRecordFrontAxleToRearAxle(Integer techRecordFrontAxleToRearAxle) {
        this.techRecordFrontAxleToRearAxle = techRecordFrontAxleToRearAxle;
    }

    public Integer getTechRecordFrontAxleTo5thWheelMin() {
        return techRecordFrontAxleTo5thWheelMin;
    }

    public void setTechRecordFrontAxleTo5thWheelMin(Integer techRecordFrontAxleTo5thWheelMin) {
        this.techRecordFrontAxleTo5thWheelMin = techRecordFrontAxleTo5thWheelMin;
    }

    public Integer getTechRecordFrontAxleTo5thWheelMax() {
        return techRecordFrontAxleTo5thWheelMax;
    }

    public void setTechRecordFrontAxleTo5thWheelMax(Integer techRecordFrontAxleTo5thWheelMax) {
        this.techRecordFrontAxleTo5thWheelMax = techRecordFrontAxleTo5thWheelMax;
    }

    public Integer getTechRecordFrontVehicleTo5thWheelCouplingMin() {
        return techRecordFrontVehicleTo5thWheelCouplingMin;
    }

    public void setTechRecordFrontVehicleTo5thWheelCouplingMin(Integer techRecordFrontVehicleTo5thWheelCouplingMin) {
        this.techRecordFrontVehicleTo5thWheelCouplingMin = techRecordFrontVehicleTo5thWheelCouplingMin;
    }

    public Integer getTechRecordFrontVehicleTo5thWheelCouplingMax() {
        return techRecordFrontVehicleTo5thWheelCouplingMax;
    }

    public void setTechRecordFrontVehicleTo5thWheelCouplingMax(Integer techRecordFrontVehicleTo5thWheelCouplingMax) {
        this.techRecordFrontVehicleTo5thWheelCouplingMax = techRecordFrontVehicleTo5thWheelCouplingMax;
    }

    public Object getTechRecordFuelPropulsionSystem() {
        return techRecordFuelPropulsionSystem;
    }

    public void setTechRecordFuelPropulsionSystem(Object techRecordFuelPropulsionSystem) {
        this.techRecordFuelPropulsionSystem = techRecordFuelPropulsionSystem;
    }

    public String getTechRecordFunctionCode() {
        return techRecordFunctionCode;
    }

    public void setTechRecordFunctionCode(String techRecordFunctionCode) {
        this.techRecordFunctionCode = techRecordFunctionCode;
    }

    public Integer getTechRecordGrossDesignWeight() {
        return techRecordGrossDesignWeight;
    }

    public void setTechRecordGrossDesignWeight(Integer techRecordGrossDesignWeight) {
        this.techRecordGrossDesignWeight = techRecordGrossDesignWeight;
    }

    public Integer getTechRecordGrossEecWeight() {
        return techRecordGrossEecWeight;
    }

    public void setTechRecordGrossEecWeight(Integer techRecordGrossEecWeight) {
        this.techRecordGrossEecWeight = techRecordGrossEecWeight;
    }

    public Integer getTechRecordGrossGbWeight() {
        return techRecordGrossGbWeight;
    }

    public void setTechRecordGrossGbWeight(Integer techRecordGrossGbWeight) {
        this.techRecordGrossGbWeight = techRecordGrossGbWeight;
    }

    public String getTechRecordMake() {
        return techRecordMake;
    }

    public void setTechRecordMake(String techRecordMake) {
        this.techRecordMake = techRecordMake;
    }

    public Double getTechRecordMaxTrainGbWeight() {
        return techRecordMaxTrainGbWeight;
    }

    public void setTechRecordMaxTrainGbWeight(Double techRecordMaxTrainGbWeight) {
        this.techRecordMaxTrainGbWeight = techRecordMaxTrainGbWeight;
    }

    public Double getTechRecordMaxTrainEecWeight() {
        return techRecordMaxTrainEecWeight;
    }

    public void setTechRecordMaxTrainEecWeight(Double techRecordMaxTrainEecWeight) {
        this.techRecordMaxTrainEecWeight = techRecordMaxTrainEecWeight;
    }

    public Double getTechRecordMaxTrainDesignWeight() {
        return techRecordMaxTrainDesignWeight;
    }

    public void setTechRecordMaxTrainDesignWeight(Double techRecordMaxTrainDesignWeight) {
        this.techRecordMaxTrainDesignWeight = techRecordMaxTrainDesignWeight;
    }

    public Integer getTechRecordManufactureYear() {
        return techRecordManufactureYear;
    }

    public void setTechRecordManufactureYear(Integer techRecordManufactureYear) {
        this.techRecordManufactureYear = techRecordManufactureYear;
    }

    public Object getTechRecordMicrofilmMicrofilmDocumentType() {
        return techRecordMicrofilmMicrofilmDocumentType;
    }

    public void setTechRecordMicrofilmMicrofilmDocumentType(Object techRecordMicrofilmMicrofilmDocumentType) {
        this.techRecordMicrofilmMicrofilmDocumentType = techRecordMicrofilmMicrofilmDocumentType;
    }

    public String getTechRecordMicrofilmMicrofilmRollNumber() {
        return techRecordMicrofilmMicrofilmRollNumber;
    }

    public void setTechRecordMicrofilmMicrofilmRollNumber(String techRecordMicrofilmMicrofilmRollNumber) {
        this.techRecordMicrofilmMicrofilmRollNumber = techRecordMicrofilmMicrofilmRollNumber;
    }

    public String getTechRecordMicrofilmMicrofilmSerialNumber() {
        return techRecordMicrofilmMicrofilmSerialNumber;
    }

    public void setTechRecordMicrofilmMicrofilmSerialNumber(String techRecordMicrofilmMicrofilmSerialNumber) {
        this.techRecordMicrofilmMicrofilmSerialNumber = techRecordMicrofilmMicrofilmSerialNumber;
    }

    public String getTechRecordModel() {
        return techRecordModel;
    }

    public void setTechRecordModel(String techRecordModel) {
        this.techRecordModel = techRecordModel;
    }

    public Object getTechRecordNoOfAxles() {
        return techRecordNoOfAxles;
    }

    public void setTechRecordNoOfAxles(Object techRecordNoOfAxles) {
        this.techRecordNoOfAxles = techRecordNoOfAxles;
    }

    public String getTechRecordNotes() {
        return techRecordNotes;
    }

    public void setTechRecordNotes(String techRecordNotes) {
        this.techRecordNotes = techRecordNotes;
    }

    public Boolean getTechRecordOffRoad() {
        return techRecordOffRoad;
    }

    public void setTechRecordOffRoad(Boolean techRecordOffRoad) {
        this.techRecordOffRoad = techRecordOffRoad;
    }

    public List<TechRecordPlate> getTechRecordPlates() {
        return techRecordPlates;
    }

    public void setTechRecordPlates(List<TechRecordPlate> techRecordPlates) {
        this.techRecordPlates = techRecordPlates;
    }

    public String getTechRecordReasonForCreation() {
        return techRecordReasonForCreation;
    }

    public void setTechRecordReasonForCreation(String techRecordReasonForCreation) {
        this.techRecordReasonForCreation = techRecordReasonForCreation;
    }

    public Object getTechRecordRegnDate() {
        return techRecordRegnDate;
    }

    public void setTechRecordRegnDate(Object techRecordRegnDate) {
        this.techRecordRegnDate = techRecordRegnDate;
    }

    public Boolean getTechRecordRoadFriendly() {
        return techRecordRoadFriendly;
    }

    public void setTechRecordRoadFriendly(Boolean techRecordRoadFriendly) {
        this.techRecordRoadFriendly = techRecordRoadFriendly;
    }

    public TechRecordHgvComplete.TechRecordStatusCode getTechRecordStatusCode() {
        return techRecordStatusCode;
    }

    public void setTechRecordStatusCode(TechRecordHgvComplete.TechRecordStatusCode techRecordStatusCode) {
        this.techRecordStatusCode = techRecordStatusCode;
    }

    public Boolean getTechRecordSpeedLimiterMrk() {
        return techRecordSpeedLimiterMrk;
    }

    public void setTechRecordSpeedLimiterMrk(Boolean techRecordSpeedLimiterMrk) {
        this.techRecordSpeedLimiterMrk = techRecordSpeedLimiterMrk;
    }

    public Boolean getTechRecordTachoExemptMrk() {
        return techRecordTachoExemptMrk;
    }

    public void setTechRecordTachoExemptMrk(Boolean techRecordTachoExemptMrk) {
        this.techRecordTachoExemptMrk = techRecordTachoExemptMrk;
    }

    public Integer getTechRecordTrainDesignWeight() {
        return techRecordTrainDesignWeight;
    }

    public void setTechRecordTrainDesignWeight(Integer techRecordTrainDesignWeight) {
        this.techRecordTrainDesignWeight = techRecordTrainDesignWeight;
    }

    public Double getTechRecordTrainEecWeight() {
        return techRecordTrainEecWeight;
    }

    public void setTechRecordTrainEecWeight(Double techRecordTrainEecWeight) {
        this.techRecordTrainEecWeight = techRecordTrainEecWeight;
    }

    public Double getTechRecordTrainGbWeight() {
        return techRecordTrainGbWeight;
    }

    public void setTechRecordTrainGbWeight(Double techRecordTrainGbWeight) {
        this.techRecordTrainGbWeight = techRecordTrainGbWeight;
    }

    public Object getTechRecordTyreUseCode() {
        return techRecordTyreUseCode;
    }

    public void setTechRecordTyreUseCode(Object techRecordTyreUseCode) {
        this.techRecordTyreUseCode = techRecordTyreUseCode;
    }

    public String getTechRecordVehicleClassDescription() {
        return techRecordVehicleClassDescription;
    }

    public void setTechRecordVehicleClassDescription(String techRecordVehicleClassDescription) {
        this.techRecordVehicleClassDescription = techRecordVehicleClassDescription;
    }

    public String getTechRecordVehicleConfiguration() {
        return techRecordVehicleConfiguration;
    }

    public void setTechRecordVehicleConfiguration(String techRecordVehicleConfiguration) {
        this.techRecordVehicleConfiguration = techRecordVehicleConfiguration;
    }

    public String getTechRecordApprovalType() {
        return techRecordApprovalType;
    }

    public void setTechRecordApprovalType(String techRecordApprovalType) {
        this.techRecordApprovalType = techRecordApprovalType;
    }

    public String getTechRecordApprovalTypeNumber() {
        return techRecordApprovalTypeNumber;
    }

    public void setTechRecordApprovalTypeNumber(String techRecordApprovalTypeNumber) {
        this.techRecordApprovalTypeNumber = techRecordApprovalTypeNumber;
    }

    public String getTechRecordNtaNumber() {
        return techRecordNtaNumber;
    }

    public void setTechRecordNtaNumber(String techRecordNtaNumber) {
        this.techRecordNtaNumber = techRecordNtaNumber;
    }

    public String getTechRecordVariantNumber() {
        return techRecordVariantNumber;
    }

    public void setTechRecordVariantNumber(String techRecordVariantNumber) {
        this.techRecordVariantNumber = techRecordVariantNumber;
    }

    public String getTechRecordVariantVersionNumber() {
        return techRecordVariantVersionNumber;
    }

    public void setTechRecordVariantVersionNumber(String techRecordVariantVersionNumber) {
        this.techRecordVariantVersionNumber = techRecordVariantVersionNumber;
    }

    public String getTechRecordVehicleType() {
        return techRecordVehicleType;
    }

    public void setTechRecordVehicleType(String techRecordVehicleType) {
        this.techRecordVehicleType = techRecordVehicleType;
    }

    public String getPrimaryVrm() {
        return primaryVrm;
    }

    public void setPrimaryVrm(String primaryVrm) {
        this.primaryVrm = primaryVrm;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Boolean getTechRecordHiddenInVta() {
        return techRecordHiddenInVta;
    }

    public void setTechRecordHiddenInVta(Boolean techRecordHiddenInVta) {
        this.techRecordHiddenInVta = techRecordHiddenInVta;
    }

    public String getTechRecordUpdateType() {
        return techRecordUpdateType;
    }

    public void setTechRecordUpdateType(String techRecordUpdateType) {
        this.techRecordUpdateType = techRecordUpdateType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TechRecordHgvComplete that = (TechRecordHgvComplete) o;
        return Objects.equals(getSecondaryVrms(), that.getSecondaryVrms())
                && Objects.equals(getPartialVin(), that.getPartialVin())
                && Objects.equals(getTechRecordAdrDetailsDangerousGoods(), that.getTechRecordAdrDetailsDangerousGoods())
                && Objects.equals(getTechRecordAdrDetailsVehicleDetailsType(),
                        that.getTechRecordAdrDetailsVehicleDetailsType())
                && Objects.equals(getTechRecordAdrDetailsVehicleDetailsApprovalDate(),
                        that.getTechRecordAdrDetailsVehicleDetailsApprovalDate())
                && Objects.equals(getTechRecordAdrDetailsPermittedDangerousGoods(),
                        that.getTechRecordAdrDetailsPermittedDangerousGoods())
                && Objects.equals(getTechRecordAdrDetailsCompatibilityGroupJ(),
                        that.getTechRecordAdrDetailsCompatibilityGroupJ())
                && Objects.equals(getTechRecordAdrDetailsAdditionalExaminerNotes(),
                        that.getTechRecordAdrDetailsAdditionalExaminerNotes())
                && Objects.equals(getTechRecordAdrDetailsApplicantDetailsName(),
                        that.getTechRecordAdrDetailsApplicantDetailsName())
                && Objects.equals(getTechRecordAdrDetailsApplicantDetailsStreet(),
                        that.getTechRecordAdrDetailsApplicantDetailsStreet())
                && Objects.equals(getTechRecordAdrDetailsApplicantDetailsTown(),
                        that.getTechRecordAdrDetailsApplicantDetailsTown())
                && Objects.equals(getTechRecordAdrDetailsApplicantDetailsCity(),
                        that.getTechRecordAdrDetailsApplicantDetailsCity())
                && Objects.equals(getTechRecordAdrDetailsApplicantDetailsPostcode(),
                        that.getTechRecordAdrDetailsApplicantDetailsPostcode())
                && Objects.equals(getTechRecordAdrDetailsMemosApply(), that.getTechRecordAdrDetailsMemosApply())
                && Objects.equals(getTechRecordAdrDetailsDocuments(), that.getTechRecordAdrDetailsDocuments())
                && Objects.equals(getTechRecordAdrDetailsListStatementApplicable(),
                        that.getTechRecordAdrDetailsListStatementApplicable())
                && Objects.equals(getTechRecordAdrDetailsBatteryListNumber(),
                        that.getTechRecordAdrDetailsBatteryListNumber())
                && Objects.equals(getTechRecordAdrDetailsBrakeDeclarationsSeen(),
                        that.getTechRecordAdrDetailsBrakeDeclarationsSeen())
                && Objects.equals(getTechRecordAdrDetailsBrakeDeclarationIssuer(),
                        that.getTechRecordAdrDetailsBrakeDeclarationIssuer())
                && Objects.equals(getTechRecordAdrDetailsBrakeEndurance(), that.getTechRecordAdrDetailsBrakeEndurance())
                && Objects.equals(getTechRecordAdrDetailsWeight(), that.getTechRecordAdrDetailsWeight())
                && Objects.equals(getTechRecordAdrDetailsDeclarationsSeen(),
                        that.getTechRecordAdrDetailsDeclarationsSeen())
                && Objects.equals(getTechRecordAdrDetailsM145Statement(), that.getTechRecordAdrDetailsM145Statement())
                && Objects.equals(getTechRecordAdrDetailsNewCertificateRequested(),
                        that.getTechRecordAdrDetailsNewCertificateRequested())
                && Objects.equals(getTechRecordAdrDetailsAdditionalNotesNumber(),
                        that.getTechRecordAdrDetailsAdditionalNotesNumber())
                && Objects.equals(getTechRecordAdrDetailsAdrTypeApprovalNo(),
                        that.getTechRecordAdrDetailsAdrTypeApprovalNo())
                && Objects.equals(getTechRecordAdrDetailsAdrCertificateNotes(),
                        that.getTechRecordAdrDetailsAdrCertificateNotes())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsTankManufacturer(),
                        that.getTechRecordAdrDetailsTankTankDetailsTankManufacturer())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsYearOfManufacture(),
                        that.getTechRecordAdrDetailsTankTankDetailsYearOfManufacture())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsTankManufacturerSerialNo(),
                        that.getTechRecordAdrDetailsTankTankDetailsTankManufacturerSerialNo())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsTankTypeAppNo(),
                        that.getTechRecordAdrDetailsTankTankDetailsTankTypeAppNo())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsTankCode(),
                        that.getTechRecordAdrDetailsTankTankDetailsTankCode())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsSpecialProvisions(),
                        that.getTechRecordAdrDetailsTankTankDetailsSpecialProvisions())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type(),
                        that.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo(),
                        that.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate(),
                        that.getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsTc3Details(),
                        that.getTechRecordAdrDetailsTankTankDetailsTc3Details())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted(),
                        that.getTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsTankStatementSelect(),
                        that.getTechRecordAdrDetailsTankTankDetailsTankStatementSelect())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsTankStatementStatement(),
                        that.getTechRecordAdrDetailsTankTankDetailsTankStatementStatement())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo(),
                        that.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo(),
                        that.getTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo())
                && Objects.equals(getTechRecordAdrDetailsTankTankDetailsTankStatementProductList(),
                        that.getTechRecordAdrDetailsTankTankDetailsTankStatementProductList())
                && Objects.equals(getTechRecordAlterationMarker(), that.getTechRecordAlterationMarker())
                && Objects.equals(getTechRecordAdrPassCertificateDetails(),
                        that.getTechRecordAdrPassCertificateDetails())
                && Objects.equals(getTechRecordApplicantDetailsName(), that.getTechRecordApplicantDetailsName())
                && Objects.equals(getTechRecordApplicantDetailsAddress1(), that.getTechRecordApplicantDetailsAddress1())
                && Objects.equals(getTechRecordApplicantDetailsAddress2(), that.getTechRecordApplicantDetailsAddress2())
                && Objects.equals(getTechRecordApplicantDetailsPostTown(), that.getTechRecordApplicantDetailsPostTown())
                && Objects.equals(getTechRecordApplicantDetailsAddress3(), that.getTechRecordApplicantDetailsAddress3())
                && Objects.equals(getTechRecordApplicantDetailsPostCode(), that.getTechRecordApplicantDetailsPostCode())
                && Objects.equals(getTechRecordApplicantDetailsTelephoneNumber(),
                        that.getTechRecordApplicantDetailsTelephoneNumber())
                && Objects.equals(getTechRecordApplicantDetailsEmailAddress(),
                        that.getTechRecordApplicantDetailsEmailAddress())
                && Objects.equals(getTechRecordApplicationId(), that.getTechRecordApplicationId())
                && Objects.equals(getTechRecordAxles(), that.getTechRecordAxles())
                && Objects.equals(getTechRecordBodyTypeCode(), that.getTechRecordBodyTypeCode())
                && Objects.equals(getTechRecordBodyTypeDescription(), that.getTechRecordBodyTypeDescription())
                && Objects.equals(getTechRecordBrakesDtpNumber(), that.getTechRecordBrakesDtpNumber())
                && Objects.equals(getTechRecordBrakesLoadSensingValve(), that.getTechRecordBrakesLoadSensingValve())
                && Objects.equals(getTechRecordConversionRefNo(), that.getTechRecordConversionRefNo())
                && Objects.equals(getTechRecordDepartmentalVehicleMarker(),
                        that.getTechRecordDepartmentalVehicleMarker())
                && Objects.equals(getTechRecordDimensionsAxleSpacing(), that.getTechRecordDimensionsAxleSpacing())
                && Objects.equals(getTechRecordDimensionsLength(), that.getTechRecordDimensionsLength())
                && Objects.equals(getTechRecordDimensionsWidth(), that.getTechRecordDimensionsWidth())
                && Objects.equals(getTechRecordDrawbarCouplingFitted(), that.getTechRecordDrawbarCouplingFitted())
                && Objects.equals(getTechRecordEmissionsLimit(), that.getTechRecordEmissionsLimit())
                && Objects.equals(getTechRecordEuroStandard(), that.getTechRecordEuroStandard())
                && getTechRecordEuVehicleCategory() == that.getTechRecordEuVehicleCategory()
                && Objects.equals(getTechRecordFrontAxleToRearAxle(), that.getTechRecordFrontAxleToRearAxle())
                && Objects.equals(getTechRecordFrontAxleTo5thWheelMin(), that.getTechRecordFrontAxleTo5thWheelMin())
                && Objects.equals(getTechRecordFrontAxleTo5thWheelMax(), that.getTechRecordFrontAxleTo5thWheelMax())
                && Objects.equals(getTechRecordFrontVehicleTo5thWheelCouplingMin(),
                        that.getTechRecordFrontVehicleTo5thWheelCouplingMin())
                && Objects.equals(getTechRecordFrontVehicleTo5thWheelCouplingMax(),
                        that.getTechRecordFrontVehicleTo5thWheelCouplingMax())
                && Objects.equals(getTechRecordFuelPropulsionSystem(), that.getTechRecordFuelPropulsionSystem())
                && Objects.equals(getTechRecordFunctionCode(), that.getTechRecordFunctionCode())
                && Objects.equals(getTechRecordGrossDesignWeight(), that.getTechRecordGrossDesignWeight())
                && Objects.equals(getTechRecordGrossEecWeight(), that.getTechRecordGrossEecWeight())
                && Objects.equals(getTechRecordGrossGbWeight(), that.getTechRecordGrossGbWeight())
                && Objects.equals(getTechRecordMake(), that.getTechRecordMake())
                && Objects.equals(getTechRecordMaxTrainGbWeight(), that.getTechRecordMaxTrainGbWeight())
                && Objects.equals(getTechRecordMaxTrainEecWeight(), that.getTechRecordMaxTrainEecWeight())
                && Objects.equals(getTechRecordMaxTrainDesignWeight(), that.getTechRecordMaxTrainDesignWeight())
                && Objects.equals(getTechRecordManufactureYear(), that.getTechRecordManufactureYear())
                && Objects.equals(getTechRecordMicrofilmMicrofilmDocumentType(),
                        that.getTechRecordMicrofilmMicrofilmDocumentType())
                && Objects.equals(getTechRecordMicrofilmMicrofilmRollNumber(),
                        that.getTechRecordMicrofilmMicrofilmRollNumber())
                && Objects.equals(getTechRecordMicrofilmMicrofilmSerialNumber(),
                        that.getTechRecordMicrofilmMicrofilmSerialNumber())
                && Objects.equals(getTechRecordModel(), that.getTechRecordModel())
                && Objects.equals(getTechRecordNoOfAxles(), that.getTechRecordNoOfAxles())
                && Objects.equals(getTechRecordNotes(), that.getTechRecordNotes())
                && Objects.equals(getTechRecordOffRoad(), that.getTechRecordOffRoad())
                && Objects.equals(getTechRecordPlates(), that.getTechRecordPlates())
                && Objects.equals(getTechRecordReasonForCreation(), that.getTechRecordReasonForCreation())
                && Objects.equals(getTechRecordRegnDate(), that.getTechRecordRegnDate())
                && Objects.equals(getTechRecordRoadFriendly(), that.getTechRecordRoadFriendly())
                && getTechRecordStatusCode() == that.getTechRecordStatusCode()
                && Objects.equals(getTechRecordSpeedLimiterMrk(), that.getTechRecordSpeedLimiterMrk())
                && Objects.equals(getTechRecordTachoExemptMrk(), that.getTechRecordTachoExemptMrk())
                && Objects.equals(getTechRecordTrainDesignWeight(), that.getTechRecordTrainDesignWeight())
                && Objects.equals(getTechRecordTrainEecWeight(), that.getTechRecordTrainEecWeight())
                && Objects.equals(getTechRecordTrainGbWeight(), that.getTechRecordTrainGbWeight())
                && Objects.equals(getTechRecordTyreUseCode(), that.getTechRecordTyreUseCode())
                && Objects.equals(getTechRecordVehicleClassDescription(), that.getTechRecordVehicleClassDescription())
                && Objects.equals(getTechRecordVehicleConfiguration(), that.getTechRecordVehicleConfiguration())
                && Objects.equals(getTechRecordApprovalType(), that.getTechRecordApprovalType())
                && Objects.equals(getTechRecordApprovalTypeNumber(), that.getTechRecordApprovalTypeNumber())
                && Objects.equals(getTechRecordNtaNumber(), that.getTechRecordNtaNumber())
                && Objects.equals(getTechRecordVariantNumber(), that.getTechRecordVariantNumber())
                && Objects.equals(getTechRecordVariantVersionNumber(), that.getTechRecordVariantVersionNumber())
                && Objects.equals(getTechRecordVehicleType(), that.getTechRecordVehicleType())
                && Objects.equals(getPrimaryVrm(), that.getPrimaryVrm())
                && Objects.equals(getVin(), that.getVin())
                && Objects.equals(getTechRecordHiddenInVta(), that.getTechRecordHiddenInVta())
                && Objects.equals(getTechRecordUpdateType(), that.getTechRecordUpdateType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSecondaryVrms(), getPartialVin(), getTechRecordAdrDetailsDangerousGoods(),
                getTechRecordAdrDetailsVehicleDetailsType(), getTechRecordAdrDetailsVehicleDetailsApprovalDate(),
                getTechRecordAdrDetailsPermittedDangerousGoods(), getTechRecordAdrDetailsCompatibilityGroupJ(),
                getTechRecordAdrDetailsAdditionalExaminerNotes(), getTechRecordAdrDetailsApplicantDetailsName(),
                getTechRecordAdrDetailsApplicantDetailsStreet(), getTechRecordAdrDetailsApplicantDetailsTown(),
                getTechRecordAdrDetailsApplicantDetailsCity(), getTechRecordAdrDetailsApplicantDetailsPostcode(),
                getTechRecordAdrDetailsMemosApply(), getTechRecordAdrDetailsDocuments(),
                getTechRecordAdrDetailsListStatementApplicable(), getTechRecordAdrDetailsBatteryListNumber(),
                getTechRecordAdrDetailsBrakeDeclarationsSeen(), getTechRecordAdrDetailsBrakeDeclarationIssuer(),
                getTechRecordAdrDetailsBrakeEndurance(), getTechRecordAdrDetailsWeight(),
                getTechRecordAdrDetailsDeclarationsSeen(), getTechRecordAdrDetailsM145Statement(),
                getTechRecordAdrDetailsNewCertificateRequested(), getTechRecordAdrDetailsAdditionalNotesNumber(),
                getTechRecordAdrDetailsAdrTypeApprovalNo(), getTechRecordAdrDetailsAdrCertificateNotes(),
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
                getTechRecordAdrDetailsTankTankDetailsTankStatementProductList(), getTechRecordAlterationMarker(),
                getTechRecordAdrPassCertificateDetails(), getTechRecordApplicantDetailsName(),
                getTechRecordApplicantDetailsAddress1(), getTechRecordApplicantDetailsAddress2(),
                getTechRecordApplicantDetailsPostTown(), getTechRecordApplicantDetailsAddress3(),
                getTechRecordApplicantDetailsPostCode(), getTechRecordApplicantDetailsTelephoneNumber(),
                getTechRecordApplicantDetailsEmailAddress(), getTechRecordApplicationId(), getTechRecordAxles(),
                getTechRecordBodyTypeCode(), getTechRecordBodyTypeDescription(), getTechRecordBrakesDtpNumber(),
                getTechRecordBrakesLoadSensingValve(), getTechRecordConversionRefNo(),
                getTechRecordDepartmentalVehicleMarker(), getTechRecordDimensionsAxleSpacing(),
                getTechRecordDimensionsLength(), getTechRecordDimensionsWidth(), getTechRecordDrawbarCouplingFitted(),
                getTechRecordEmissionsLimit(), getTechRecordEuroStandard(), getTechRecordEuVehicleCategory(),
                getTechRecordFrontAxleToRearAxle(), getTechRecordFrontAxleTo5thWheelMin(),
                getTechRecordFrontAxleTo5thWheelMax(), getTechRecordFrontVehicleTo5thWheelCouplingMin(),
                getTechRecordFrontVehicleTo5thWheelCouplingMax(), getTechRecordFuelPropulsionSystem(),
                getTechRecordFunctionCode(), getTechRecordGrossDesignWeight(), getTechRecordGrossEecWeight(),
                getTechRecordGrossGbWeight(), getTechRecordMake(), getTechRecordMaxTrainGbWeight(),
                getTechRecordMaxTrainEecWeight(), getTechRecordMaxTrainDesignWeight(), getTechRecordManufactureYear(),
                getTechRecordMicrofilmMicrofilmDocumentType(), getTechRecordMicrofilmMicrofilmRollNumber(),
                getTechRecordMicrofilmMicrofilmSerialNumber(), getTechRecordModel(), getTechRecordNoOfAxles(),
                getTechRecordNotes(), getTechRecordOffRoad(), getTechRecordPlates(), getTechRecordReasonForCreation(),
                getTechRecordRegnDate(), getTechRecordRoadFriendly(), getTechRecordStatusCode(),
                getTechRecordSpeedLimiterMrk(), getTechRecordTachoExemptMrk(), getTechRecordTrainDesignWeight(),
                getTechRecordTrainEecWeight(), getTechRecordTrainGbWeight(), getTechRecordTyreUseCode(),
                getTechRecordVehicleClassDescription(), getTechRecordVehicleConfiguration(),
                getTechRecordApprovalType(), getTechRecordApprovalTypeNumber(), getTechRecordNtaNumber(),
                getTechRecordVariantNumber(), getTechRecordVariantVersionNumber(), getTechRecordVehicleType(),
                getPrimaryVrm(), getVin(), getTechRecordHiddenInVta(), getTechRecordUpdateType());
    }

    @Generated("jsonschema2pojo")
    public enum TechRecordEuVehicleCategory {

        @SerializedName("n1")
        N_1("n1"), @SerializedName("n2")
        N_2("n2"), @SerializedName("n3")
        N_3("n3");

        private final static Map<String, TechRecordHgvComplete.TechRecordEuVehicleCategory> CONSTANTS = new HashMap<String, TechRecordHgvComplete.TechRecordEuVehicleCategory>();

        static {
            for (TechRecordHgvComplete.TechRecordEuVehicleCategory c : values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private final String value;

        TechRecordEuVehicleCategory(String value) {
            this.value = value;
        }

        public static TechRecordHgvComplete.TechRecordEuVehicleCategory fromValue(String value) {
            TechRecordHgvComplete.TechRecordEuVehicleCategory constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

    }

    @Generated("jsonschema2pojo")
    public enum TechRecordStatusCode {

        @SerializedName("provisional")
        PROVISIONAL("provisional"), @SerializedName("current")
        CURRENT("current"),
        @SerializedName("archived")
        ARCHIVED("archived");

        private final static Map<String, TechRecordHgvComplete.TechRecordStatusCode> CONSTANTS = new HashMap<String, TechRecordHgvComplete.TechRecordStatusCode>();

        static {
            for (TechRecordHgvComplete.TechRecordStatusCode c : values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private final String value;

        TechRecordStatusCode(String value) {
            this.value = value;
        }

        public static TechRecordHgvComplete.TechRecordStatusCode fromValue(String value) {
            TechRecordHgvComplete.TechRecordStatusCode constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

    }
}
