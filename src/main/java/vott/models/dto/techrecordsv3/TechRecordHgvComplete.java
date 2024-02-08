package vott.models.dto.techrecordsv3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Tech Record PUT HGV Complete
 * <p>
 *
 *
 */
@Generated("jsonschema2pojo")
public class TechRecordHgvComplete {

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
    private Object techRecordAdrDetailsVehicleDetailsApprovalDate;
    @SerializedName("techRecord_adrDetails_permittedDangerousGoods")
    @Expose
    private List<String> techRecordAdrDetailsPermittedDangerousGoods;
    @SerializedName("techRecord_adrDetails_compatibilityGroupJ")
    @Expose
    private Object techRecordAdrDetailsCompatibilityGroupJ;
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
    private Object techRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type;
    @SerializedName("techRecord_adrDetails_tank_tankDetails_tc2Details_tc2IntermediateApprovalNo")
    @Expose
    private String techRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo;
    @SerializedName("techRecord_adrDetails_tank_tankDetails_tc2Details_tc2IntermediateExpiryDate")
    @Expose
    private Object techRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate;
    @SerializedName("techRecord_adrDetails_tank_tankDetails_tc3Details")
    @Expose
    private List<TechRecordAdrDetailsTankTankDetailsTc3Detail> techRecordAdrDetailsTankTankDetailsTc3Details;
    @SerializedName("techRecord_adrDetails_tank_tankDetails_tankStatement_substancesPermitted")
    @Expose
    private String techRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted;
    @SerializedName("techRecord_adrDetails_tank_tankDetails_tankStatement_select")
    @Expose
    private Object techRecordAdrDetailsTankTankDetailsTankStatementSelect;
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
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_applicantDetails_name")
    @Expose
    private String techRecordApplicantDetailsName;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_applicantDetails_address1")
    @Expose
    private String techRecordApplicantDetailsAddress1;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_applicantDetails_address2")
    @Expose
    private String techRecordApplicantDetailsAddress2;
    /**
     *
     * (Required)
     *
     */
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
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_axles")
    @Expose
    private List<TechRecordAxle> techRecordAxles;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_bodyType_code")
    @Expose
    private String techRecordBodyTypeCode;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_bodyType_description")
    @Expose
    private String techRecordBodyTypeDescription;
    /**
     *
     * (Required)
     *
     */
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
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_dimensions_length")
    @Expose
    private Integer techRecordDimensionsLength;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_dimensions_width")
    @Expose
    private Integer techRecordDimensionsWidth;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_drawbarCouplingFitted")
    @Expose
    private Boolean techRecordDrawbarCouplingFitted;
    @SerializedName("techRecord_emissionsLimit")
    @Expose
    private Double techRecordEmissionsLimit;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_euroStandard")
    @Expose
    private String techRecordEuroStandard;
    /**
     * EU vehicle category
     * <p>
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_euVehicleCategory")
    @Expose
    private TechRecordHgvComplete.TechRecordEuVehicleCategory techRecordEuVehicleCategory;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_frontAxleToRearAxle")
    @Expose
    private Integer techRecordFrontAxleToRearAxle;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_frontAxleTo5thWheelMin")
    @Expose
    private Integer techRecordFrontAxleTo5thWheelMin;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_frontAxleTo5thWheelMax")
    @Expose
    private Integer techRecordFrontAxleTo5thWheelMax;
    @SerializedName("techRecord_frontVehicleTo5thWheelCouplingMin")
    @Expose
    private Integer techRecordFrontVehicleTo5thWheelCouplingMin;
    @SerializedName("techRecord_frontVehicleTo5thWheelCouplingMax")
    @Expose
    private Integer techRecordFrontVehicleTo5thWheelCouplingMax;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_fuelPropulsionSystem")
    @Expose
    private Object techRecordFuelPropulsionSystem;
    @SerializedName("techRecord_functionCode")
    @Expose
    private String techRecordFunctionCode;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_grossDesignWeight")
    @Expose
    private Integer techRecordGrossDesignWeight;
    @SerializedName("techRecord_grossEecWeight")
    @Expose
    private Integer techRecordGrossEecWeight;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_grossGbWeight")
    @Expose
    private Integer techRecordGrossGbWeight;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_make")
    @Expose
    private String techRecordMake;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_maxTrainGbWeight")
    @Expose
    private Double techRecordMaxTrainGbWeight;
    @SerializedName("techRecord_maxTrainEecWeight")
    @Expose
    private Double techRecordMaxTrainEecWeight;
    @SerializedName("techRecord_maxTrainDesignWeight")
    @Expose
    private Double techRecordMaxTrainDesignWeight;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_manufactureYear")
    @Expose
    private Object techRecordManufactureYear;
    @SerializedName("techRecord_microfilm_microfilmDocumentType")
    @Expose
    private Object techRecordMicrofilmMicrofilmDocumentType;
    @SerializedName("techRecord_microfilm_microfilmRollNumber")
    @Expose
    private String techRecordMicrofilmMicrofilmRollNumber;
    @SerializedName("techRecord_microfilm_microfilmSerialNumber")
    @Expose
    private String techRecordMicrofilmMicrofilmSerialNumber;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_model")
    @Expose
    private String techRecordModel;
    @SerializedName("techRecord_noOfAxles")
    @Expose
    private Object techRecordNoOfAxles;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_notes")
    @Expose
    private String techRecordNotes;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_offRoad")
    @Expose
    private Boolean techRecordOffRoad;
    @SerializedName("techRecord_plates")
    @Expose
    private List<TechRecordPlate> techRecordPlates;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_reasonForCreation")
    @Expose
    private String techRecordReasonForCreation;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_regnDate")
    @Expose
    private Object techRecordRegnDate;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_roadFriendly")
    @Expose
    private Boolean techRecordRoadFriendly;
    /**
     * Status Code
     * <p>
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_statusCode")
    @Expose
    private TechRecordHgvComplete.TechRecordStatusCode techRecordStatusCode;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_speedLimiterMrk")
    @Expose
    private Boolean techRecordSpeedLimiterMrk;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_tachoExemptMrk")
    @Expose
    private Boolean techRecordTachoExemptMrk;
    @SerializedName("techRecord_trainDesignWeight")
    @Expose
    private Integer techRecordTrainDesignWeight;
    @SerializedName("techRecord_trainEecWeight")
    @Expose
    private Double techRecordTrainEecWeight;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_trainGbWeight")
    @Expose
    private Double techRecordTrainGbWeight;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_tyreUseCode")
    @Expose
    private Object techRecordTyreUseCode;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_vehicleClass_description")
    @Expose
    private Object techRecordVehicleClassDescription;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_vehicleConfiguration")
    @Expose
    private Object techRecordVehicleConfiguration;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_approvalType")
    @Expose
    private Object techRecordApprovalType;
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
    /**
     *
     * (Required)
     *
     */
    @SerializedName("techRecord_vehicleType")
    @Expose
    private Object techRecordVehicleType;
    @SerializedName("primaryVrm")
    @Expose
    private String primaryVrm;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("vin")
    @Expose
    private String vin;
    @SerializedName("techRecord_hiddenInVta")
    @Expose
    private Boolean techRecordHiddenInVta;
    @SerializedName("techRecord_updateType")
    @Expose
    private String techRecordUpdateType;

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

    public Object getTechRecordAdrDetailsVehicleDetailsApprovalDate() {
        return techRecordAdrDetailsVehicleDetailsApprovalDate;
    }

    public void setTechRecordAdrDetailsVehicleDetailsApprovalDate(Object techRecordAdrDetailsVehicleDetailsApprovalDate) {
        this.techRecordAdrDetailsVehicleDetailsApprovalDate = techRecordAdrDetailsVehicleDetailsApprovalDate;
    }

    public List<String> getTechRecordAdrDetailsPermittedDangerousGoods() {
        return techRecordAdrDetailsPermittedDangerousGoods;
    }

    public void setTechRecordAdrDetailsPermittedDangerousGoods(List<String> techRecordAdrDetailsPermittedDangerousGoods) {
        this.techRecordAdrDetailsPermittedDangerousGoods = techRecordAdrDetailsPermittedDangerousGoods;
    }

    public Object getTechRecordAdrDetailsCompatibilityGroupJ() {
        return techRecordAdrDetailsCompatibilityGroupJ;
    }

    public void setTechRecordAdrDetailsCompatibilityGroupJ(Object techRecordAdrDetailsCompatibilityGroupJ) {
        this.techRecordAdrDetailsCompatibilityGroupJ = techRecordAdrDetailsCompatibilityGroupJ;
    }

    public List<TechRecordAdrDetailsAdditionalExaminerNote> getTechRecordAdrDetailsAdditionalExaminerNotes() {
        return techRecordAdrDetailsAdditionalExaminerNotes;
    }

    public void setTechRecordAdrDetailsAdditionalExaminerNotes(List<TechRecordAdrDetailsAdditionalExaminerNote> techRecordAdrDetailsAdditionalExaminerNotes) {
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

    public void setTechRecordAdrDetailsTankTankDetailsTankManufacturer(String techRecordAdrDetailsTankTankDetailsTankManufacturer) {
        this.techRecordAdrDetailsTankTankDetailsTankManufacturer = techRecordAdrDetailsTankTankDetailsTankManufacturer;
    }

    public Integer getTechRecordAdrDetailsTankTankDetailsYearOfManufacture() {
        return techRecordAdrDetailsTankTankDetailsYearOfManufacture;
    }

    public void setTechRecordAdrDetailsTankTankDetailsYearOfManufacture(Integer techRecordAdrDetailsTankTankDetailsYearOfManufacture) {
        this.techRecordAdrDetailsTankTankDetailsYearOfManufacture = techRecordAdrDetailsTankTankDetailsYearOfManufacture;
    }

    public String getTechRecordAdrDetailsTankTankDetailsTankManufacturerSerialNo() {
        return techRecordAdrDetailsTankTankDetailsTankManufacturerSerialNo;
    }

    public void setTechRecordAdrDetailsTankTankDetailsTankManufacturerSerialNo(String techRecordAdrDetailsTankTankDetailsTankManufacturerSerialNo) {
        this.techRecordAdrDetailsTankTankDetailsTankManufacturerSerialNo = techRecordAdrDetailsTankTankDetailsTankManufacturerSerialNo;
    }

    public String getTechRecordAdrDetailsTankTankDetailsTankTypeAppNo() {
        return techRecordAdrDetailsTankTankDetailsTankTypeAppNo;
    }

    public void setTechRecordAdrDetailsTankTankDetailsTankTypeAppNo(String techRecordAdrDetailsTankTankDetailsTankTypeAppNo) {
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

    public void setTechRecordAdrDetailsTankTankDetailsSpecialProvisions(String techRecordAdrDetailsTankTankDetailsSpecialProvisions) {
        this.techRecordAdrDetailsTankTankDetailsSpecialProvisions = techRecordAdrDetailsTankTankDetailsSpecialProvisions;
    }

    public Object getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type() {
        return techRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type;
    }

    public void setTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type(Object techRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type) {
        this.techRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type = techRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type;
    }

    public String getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo() {
        return techRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo;
    }

    public void setTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo(String techRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo) {
        this.techRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo = techRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo;
    }

    public Object getTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate() {
        return techRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate;
    }

    public void setTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate(Object techRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate) {
        this.techRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate = techRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate;
    }

    public List<TechRecordAdrDetailsTankTankDetailsTc3Detail> getTechRecordAdrDetailsTankTankDetailsTc3Details() {
        return techRecordAdrDetailsTankTankDetailsTc3Details;
    }

    public void setTechRecordAdrDetailsTankTankDetailsTc3Details(List<TechRecordAdrDetailsTankTankDetailsTc3Detail> techRecordAdrDetailsTankTankDetailsTc3Details) {
        this.techRecordAdrDetailsTankTankDetailsTc3Details = techRecordAdrDetailsTankTankDetailsTc3Details;
    }

    public String getTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted() {
        return techRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted;
    }

    public void setTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted(String techRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted) {
        this.techRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted = techRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted;
    }

    public Object getTechRecordAdrDetailsTankTankDetailsTankStatementSelect() {
        return techRecordAdrDetailsTankTankDetailsTankStatementSelect;
    }

    public void setTechRecordAdrDetailsTankTankDetailsTankStatementSelect(Object techRecordAdrDetailsTankTankDetailsTankStatementSelect) {
        this.techRecordAdrDetailsTankTankDetailsTankStatementSelect = techRecordAdrDetailsTankTankDetailsTankStatementSelect;
    }

    public String getTechRecordAdrDetailsTankTankDetailsTankStatementStatement() {
        return techRecordAdrDetailsTankTankDetailsTankStatementStatement;
    }

    public void setTechRecordAdrDetailsTankTankDetailsTankStatementStatement(String techRecordAdrDetailsTankTankDetailsTankStatementStatement) {
        this.techRecordAdrDetailsTankTankDetailsTankStatementStatement = techRecordAdrDetailsTankTankDetailsTankStatementStatement;
    }

    public String getTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo() {
        return techRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo;
    }

    public void setTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo(String techRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo) {
        this.techRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo = techRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo;
    }

    public List<String> getTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo() {
        return techRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo;
    }

    public void setTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo(List<String> techRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo) {
        this.techRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo = techRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo;
    }

    public String getTechRecordAdrDetailsTankTankDetailsTankStatementProductList() {
        return techRecordAdrDetailsTankTankDetailsTankStatementProductList;
    }

    public void setTechRecordAdrDetailsTankTankDetailsTankStatementProductList(String techRecordAdrDetailsTankTankDetailsTankStatementProductList) {
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

    public void setTechRecordAdrPassCertificateDetails(List<TechRecordAdrPassCertificateDetail> techRecordAdrPassCertificateDetails) {
        this.techRecordAdrPassCertificateDetails = techRecordAdrPassCertificateDetails;
    }

    /**
     *
     * (Required)
     *
     */
    public String getTechRecordApplicantDetailsName() {
        return techRecordApplicantDetailsName;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTechRecordApplicantDetailsName(String techRecordApplicantDetailsName) {
        this.techRecordApplicantDetailsName = techRecordApplicantDetailsName;
    }

    /**
     *
     * (Required)
     *
     */
    public String getTechRecordApplicantDetailsAddress1() {
        return techRecordApplicantDetailsAddress1;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTechRecordApplicantDetailsAddress1(String techRecordApplicantDetailsAddress1) {
        this.techRecordApplicantDetailsAddress1 = techRecordApplicantDetailsAddress1;
    }

    /**
     *
     * (Required)
     *
     */
    public String getTechRecordApplicantDetailsAddress2() {
        return techRecordApplicantDetailsAddress2;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTechRecordApplicantDetailsAddress2(String techRecordApplicantDetailsAddress2) {
        this.techRecordApplicantDetailsAddress2 = techRecordApplicantDetailsAddress2;
    }

    /**
     *
     * (Required)
     *
     */
    public String getTechRecordApplicantDetailsPostTown() {
        return techRecordApplicantDetailsPostTown;
    }

    /**
     *
     * (Required)
     *
     */
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

    /**
     *
     * (Required)
     *
     */
    public List<TechRecordAxle> getTechRecordAxles() {
        return techRecordAxles;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTechRecordAxles(List<TechRecordAxle> techRecordAxles) {
        this.techRecordAxles = techRecordAxles;
    }

    /**
     *
     * (Required)
     *
     */
    public String getTechRecordBodyTypeCode() {
        return techRecordBodyTypeCode;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTechRecordBodyTypeCode(String techRecordBodyTypeCode) {
        this.techRecordBodyTypeCode = techRecordBodyTypeCode;
    }

    /**
     *
     * (Required)
     *
     */
    public String getTechRecordBodyTypeDescription() {
        return techRecordBodyTypeDescription;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTechRecordBodyTypeDescription(String techRecordBodyTypeDescription) {
        this.techRecordBodyTypeDescription = techRecordBodyTypeDescription;
    }

    /**
     *
     * (Required)
     *
     */
    public String getTechRecordBrakesDtpNumber() {
        return techRecordBrakesDtpNumber;
    }

    /**
     *
     * (Required)
     *
     */
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

    public void setTechRecordDimensionsAxleSpacing(List<TechRecordDimensionsAxleSpacing> techRecordDimensionsAxleSpacing) {
        this.techRecordDimensionsAxleSpacing = techRecordDimensionsAxleSpacing;
    }

    /**
     *
     * (Required)
     *
     */
    public Integer getTechRecordDimensionsLength() {
        return techRecordDimensionsLength;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTechRecordDimensionsLength(Integer techRecordDimensionsLength) {
        this.techRecordDimensionsLength = techRecordDimensionsLength;
    }

    /**
     *
     * (Required)
     *
     */
    public Integer getTechRecordDimensionsWidth() {
        return techRecordDimensionsWidth;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTechRecordDimensionsWidth(Integer techRecordDimensionsWidth) {
        this.techRecordDimensionsWidth = techRecordDimensionsWidth;
    }

    /**
     *
     * (Required)
     *
     */
    public Boolean getTechRecordDrawbarCouplingFitted() {
        return techRecordDrawbarCouplingFitted;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTechRecordDrawbarCouplingFitted(Boolean techRecordDrawbarCouplingFitted) {
        this.techRecordDrawbarCouplingFitted = techRecordDrawbarCouplingFitted;
    }

    public Double getTechRecordEmissionsLimit() {
        return techRecordEmissionsLimit;
    }

    public void setTechRecordEmissionsLimit(Double techRecordEmissionsLimit) {
        this.techRecordEmissionsLimit = techRecordEmissionsLimit;
    }

    /**
     *
     * (Required)
     *
     */
    public String getTechRecordEuroStandard() {
        return techRecordEuroStandard;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTechRecordEuroStandard(String techRecordEuroStandard) {
        this.techRecordEuroStandard = techRecordEuroStandard;
    }

    /**
     * EU vehicle category
     * <p>
     *
     * (Required)
     *
     */
    public TechRecordHgvComplete.TechRecordEuVehicleCategory getTechRecordEuVehicleCategory() {
        return techRecordEuVehicleCategory;
    }

    /**
     * EU vehicle category
     * <p>
     *
     * (Required)
     *
     */
    public void setTechRecordEuVehicleCategory(TechRecordHgvComplete.TechRecordEuVehicleCategory techRecordEuVehicleCategory) {
        this.techRecordEuVehicleCategory = techRecordEuVehicleCategory;
    }

    /**
     *
     * (Required)
     *
     */
    public Integer getTechRecordFrontAxleToRearAxle() {
        return techRecordFrontAxleToRearAxle;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTechRecordFrontAxleToRearAxle(Integer techRecordFrontAxleToRearAxle) {
        this.techRecordFrontAxleToRearAxle = techRecordFrontAxleToRearAxle;
    }

    /**
     *
     * (Required)
     *
     */
    public Integer getTechRecordFrontAxleTo5thWheelMin() {
        return techRecordFrontAxleTo5thWheelMin;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTechRecordFrontAxleTo5thWheelMin(Integer techRecordFrontAxleTo5thWheelMin) {
        this.techRecordFrontAxleTo5thWheelMin = techRecordFrontAxleTo5thWheelMin;
    }

    /**
     *
     * (Required)
     *
     */
    public Integer getTechRecordFrontAxleTo5thWheelMax() {
        return techRecordFrontAxleTo5thWheelMax;
    }

    /**
     *
     * (Required)
     *
     */
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

    /**
     *
     * (Required)
     *
     */
    public Object getTechRecordFuelPropulsionSystem() {
        return techRecordFuelPropulsionSystem;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTechRecordFuelPropulsionSystem(Object techRecordFuelPropulsionSystem) {
        this.techRecordFuelPropulsionSystem = techRecordFuelPropulsionSystem;
    }

    public String getTechRecordFunctionCode() {
        return techRecordFunctionCode;
    }

    public void setTechRecordFunctionCode(String techRecordFunctionCode) {
        this.techRecordFunctionCode = techRecordFunctionCode;
    }

    /**
     *
     * (Required)
     *
     */
    public Integer getTechRecordGrossDesignWeight() {
        return techRecordGrossDesignWeight;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTechRecordGrossDesignWeight(Integer techRecordGrossDesignWeight) {
        this.techRecordGrossDesignWeight = techRecordGrossDesignWeight;
    }

    public Integer getTechRecordGrossEecWeight() {
        return techRecordGrossEecWeight;
    }

    public void setTechRecordGrossEecWeight(Integer techRecordGrossEecWeight) {
        this.techRecordGrossEecWeight = techRecordGrossEecWeight;
    }

    /**
     *
     * (Required)
     *
     */
    public Integer getTechRecordGrossGbWeight() {
        return techRecordGrossGbWeight;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTechRecordGrossGbWeight(Integer techRecordGrossGbWeight) {
        this.techRecordGrossGbWeight = techRecordGrossGbWeight;
    }

    /**
     *
     * (Required)
     *
     */
    public String getTechRecordMake() {
        return techRecordMake;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTechRecordMake(String techRecordMake) {
        this.techRecordMake = techRecordMake;
    }

    /**
     *
     * (Required)
     *
     */
    public Double getTechRecordMaxTrainGbWeight() {
        return techRecordMaxTrainGbWeight;
    }

    /**
     *
     * (Required)
     *
     */
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

    /**
     *
     * (Required)
     *
     */
    public Object getTechRecordManufactureYear() {
        return techRecordManufactureYear;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTechRecordManufactureYear(Object techRecordManufactureYear) {
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

    /**
     *
     * (Required)
     *
     */
    public String getTechRecordModel() {
        return techRecordModel;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTechRecordModel(String techRecordModel) {
        this.techRecordModel = techRecordModel;
    }

    public Object getTechRecordNoOfAxles() {
        return techRecordNoOfAxles;
    }

    public void setTechRecordNoOfAxles(Object techRecordNoOfAxles) {
        this.techRecordNoOfAxles = techRecordNoOfAxles;
    }

    /**
     *
     * (Required)
     *
     */
    public String getTechRecordNotes() {
        return techRecordNotes;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTechRecordNotes(String techRecordNotes) {
        this.techRecordNotes = techRecordNotes;
    }

    /**
     *
     * (Required)
     *
     */
    public Boolean getTechRecordOffRoad() {
        return techRecordOffRoad;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTechRecordOffRoad(Boolean techRecordOffRoad) {
        this.techRecordOffRoad = techRecordOffRoad;
    }

    public List<TechRecordPlate> getTechRecordPlates() {
        return techRecordPlates;
    }

    public void setTechRecordPlates(List<TechRecordPlate> techRecordPlates) {
        this.techRecordPlates = techRecordPlates;
    }

    /**
     *
     * (Required)
     *
     */
    public String getTechRecordReasonForCreation() {
        return techRecordReasonForCreation;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTechRecordReasonForCreation(String techRecordReasonForCreation) {
        this.techRecordReasonForCreation = techRecordReasonForCreation;
    }

    /**
     *
     * (Required)
     *
     */
    public Object getTechRecordRegnDate() {
        return techRecordRegnDate;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTechRecordRegnDate(Object techRecordRegnDate) {
        this.techRecordRegnDate = techRecordRegnDate;
    }

    /**
     *
     * (Required)
     *
     */
    public Boolean getTechRecordRoadFriendly() {
        return techRecordRoadFriendly;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTechRecordRoadFriendly(Boolean techRecordRoadFriendly) {
        this.techRecordRoadFriendly = techRecordRoadFriendly;
    }

    /**
     * Status Code
     * <p>
     *
     * (Required)
     *
     */
    public TechRecordHgvComplete.TechRecordStatusCode getTechRecordStatusCode() {
        return techRecordStatusCode;
    }

    /**
     * Status Code
     * <p>
     *
     * (Required)
     *
     */
    public void setTechRecordStatusCode(TechRecordHgvComplete.TechRecordStatusCode techRecordStatusCode) {
        this.techRecordStatusCode = techRecordStatusCode;
    }

    /**
     *
     * (Required)
     *
     */
    public Boolean getTechRecordSpeedLimiterMrk() {
        return techRecordSpeedLimiterMrk;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTechRecordSpeedLimiterMrk(Boolean techRecordSpeedLimiterMrk) {
        this.techRecordSpeedLimiterMrk = techRecordSpeedLimiterMrk;
    }

    /**
     *
     * (Required)
     *
     */
    public Boolean getTechRecordTachoExemptMrk() {
        return techRecordTachoExemptMrk;
    }

    /**
     *
     * (Required)
     *
     */
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

    /**
     *
     * (Required)
     *
     */
    public Double getTechRecordTrainGbWeight() {
        return techRecordTrainGbWeight;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTechRecordTrainGbWeight(Double techRecordTrainGbWeight) {
        this.techRecordTrainGbWeight = techRecordTrainGbWeight;
    }

    /**
     *
     * (Required)
     *
     */
    public Object getTechRecordTyreUseCode() {
        return techRecordTyreUseCode;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTechRecordTyreUseCode(Object techRecordTyreUseCode) {
        this.techRecordTyreUseCode = techRecordTyreUseCode;
    }

    /**
     *
     * (Required)
     *
     */
    public Object getTechRecordVehicleClassDescription() {
        return techRecordVehicleClassDescription;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTechRecordVehicleClassDescription(Object techRecordVehicleClassDescription) {
        this.techRecordVehicleClassDescription = techRecordVehicleClassDescription;
    }

    /**
     *
     * (Required)
     *
     */
    public Object getTechRecordVehicleConfiguration() {
        return techRecordVehicleConfiguration;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTechRecordVehicleConfiguration(Object techRecordVehicleConfiguration) {
        this.techRecordVehicleConfiguration = techRecordVehicleConfiguration;
    }

    /**
     *
     * (Required)
     *
     */
    public Object getTechRecordApprovalType() {
        return techRecordApprovalType;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTechRecordApprovalType(Object techRecordApprovalType) {
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

    /**
     *
     * (Required)
     *
     */
    public Object getTechRecordVehicleType() {
        return techRecordVehicleType;
    }

    /**
     *
     * (Required)
     *
     */
    public void setTechRecordVehicleType(Object techRecordVehicleType) {
        this.techRecordVehicleType = techRecordVehicleType;
    }

    public String getPrimaryVrm() {
        return primaryVrm;
    }

    public void setPrimaryVrm(String primaryVrm) {
        this.primaryVrm = primaryVrm;
    }

    /**
     *
     * (Required)
     *
     */
    public String getVin() {
        return vin;
    }

    /**
     *
     * (Required)
     *
     */
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


    /**
     * EU vehicle category
     * <p>
     *
     *
     */
    @Generated("jsonschema2pojo")
    public enum TechRecordEuVehicleCategory {

        @SerializedName("n1")
        N_1("n1"),
        @SerializedName("n2")
        N_2("n2"),
        @SerializedName("n3")
        N_3("n3");
        private final String value;
        private final static Map<String, TechRecordHgvComplete.TechRecordEuVehicleCategory> CONSTANTS = new HashMap<String, TechRecordHgvComplete.TechRecordEuVehicleCategory>();

        static {
            for (TechRecordHgvComplete.TechRecordEuVehicleCategory c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        TechRecordEuVehicleCategory(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static TechRecordHgvComplete.TechRecordEuVehicleCategory fromValue(String value) {
            TechRecordHgvComplete.TechRecordEuVehicleCategory constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * Status Code
     * <p>
     *
     *
     */
    @Generated("jsonschema2pojo")
    public enum TechRecordStatusCode {

        @SerializedName("provisional")
        PROVISIONAL("provisional"),
        @SerializedName("current")
        CURRENT("current"),
        @SerializedName("archived")
        ARCHIVED("archived");
        private final String value;
        private final static Map<String, TechRecordHgvComplete.TechRecordStatusCode> CONSTANTS = new HashMap<String, TechRecordHgvComplete.TechRecordStatusCode>();

        static {
            for (TechRecordHgvComplete.TechRecordStatusCode c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        TechRecordStatusCode(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static TechRecordHgvComplete.TechRecordStatusCode fromValue(String value) {
            TechRecordHgvComplete.TechRecordStatusCode constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}


