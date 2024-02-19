package vott.models.dto.techrecordsv3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Tech Record PUT TRL Complete
 * <p>
 */
@Generated("jsonschema2pojo")
public class TechRecordTrlComplete extends TechRecordV3 {
    /**
     * (Required)
     * techRecord_noOfAxles
     * techRecord_make
     * techRecord_model
     * techRecord_firstUseDate
     * techRecord_maxLoadOnCoupling
     * techRecord_tyreUseCode
     * techRecord_suspensionType
     * techRecord_couplingType
     * techRecord_dimensions_length
     * techRecord_dimensions_width
     * techRecord_frontAxleToRearAxle
     * techRecord_rearAxleToRearTrl
     * techRecord_couplingCenterToRearAxleMin
     * techRecord_couplingCenterToRearAxleMax
     * techRecord_couplingCenterToRearTrlMax
     * techRecord_couplingCenterToRearTrlMin
     * techRecord_notes
     * techRecord_roadFriendly
     * techRecord_reasonForCreation
     * techRecord_statusCode
     * techRecord_vehicleClass_description
     * techRecord_vehicleType
     * techRecord_bodyType_description
     * techRecord_bodyType_code
     * techRecord_vehicleConfiguration
     * vin
     * techRecord_euVehicleCategory
     */
    @Expose
    @SerializedName("systemNumber")
    private String systemNumber; 
    @Expose
    @SerializedName("createdTimestamp")
    private String createdTimestamp;
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
    @SerializedName("techRecord_adrPassCertificateDetails")
    @Expose
    private List<TechRecordAdrPassCertificateDetail> techRecordAdrPassCertificateDetails;
    @SerializedName("techRecord_alterationMarker")
    @Expose
    private Boolean techRecordAlterationMarker;
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
    @SerializedName("techRecord_approvalType")
    @Expose
    private Object techRecordApprovalType;
    @SerializedName("techRecord_approvalTypeNumber")
    @Expose
    private String techRecordApprovalTypeNumber;
    @SerializedName("techRecord_authIntoService")
    @Expose
    private String techRecordAuthIntoService;
    @SerializedName("techRecord_batchId")
    @Expose
    private String techRecordBatchId;

    @SerializedName("techRecord_bodyType_code")
    @Expose
    private String techRecordBodyTypeCode;

    @SerializedName("techRecord_bodyType_description")
    @Expose
    private String techRecordBodyTypeDescription;
    @SerializedName("techRecord_brakes_antilockBrakingSystem")
    @Expose
    private Boolean techRecordBrakesAntilockBrakingSystem;
    @SerializedName("techRecord_brakes_dtpNumber")
    @Expose
    private String techRecordBrakesDtpNumber;
    @SerializedName("techRecord_brakes_loadSensingValve")
    @Expose
    private Boolean techRecordBrakesLoadSensingValve;
    @SerializedName("techRecord_centreOfRearmostAxleToRearOfTrl")
    @Expose
    private Integer techRecordCentreOfRearmostAxleToRearOfTrl;
    @SerializedName("techRecord_conversionRefNo")
    @Expose
    private String techRecordConversionRefNo;

    @SerializedName("techRecord_couplingCenterToRearAxleMax")
    @Expose
    private Integer techRecordCouplingCenterToRearAxleMax;

    @SerializedName("techRecord_couplingCenterToRearAxleMin")
    @Expose
    private Integer techRecordCouplingCenterToRearAxleMin;

    @SerializedName("techRecord_couplingCenterToRearTrlMax")
    @Expose
    private Integer techRecordCouplingCenterToRearTrlMax;

    @SerializedName("techRecord_couplingCenterToRearTrlMin")
    @Expose
    private Integer techRecordCouplingCenterToRearTrlMin;

    @SerializedName("techRecord_couplingType")
    @Expose
    private String techRecordCouplingType;
    @SerializedName("techRecord_departmentalVehicleMarker")
    @Expose
    private Boolean techRecordDepartmentalVehicleMarker;

    @SerializedName("techRecord_dimensions_length")
    @Expose
    private Integer techRecordDimensionsLength;

    @SerializedName("techRecord_dimensions_width")
    @Expose
    private Integer techRecordDimensionsWidth;

    @SerializedName("techRecord_euVehicleCategory")
    @Expose
    private TechRecordTrlComplete.TechRecordEuVehicleCategory techRecordEuVehicleCategory;

    @SerializedName("techRecord_firstUseDate")
    @Expose
    private String techRecordFirstUseDate;
    @SerializedName("techRecord_frameDescription")
    @Expose
    private Object techRecordFrameDescription;

    @SerializedName("techRecord_frontAxleToRearAxle")
    @Expose
    private Integer techRecordFrontAxleToRearAxle;
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
    @SerializedName("techRecord_letterOfAuth_letterType")
    @Expose
    private Object techRecordLetterOfAuthLetterType;
    @SerializedName("techRecord_letterOfAuth_letterDateRequested")
    @Expose
    private String techRecordLetterOfAuthLetterDateRequested;
    @SerializedName("techRecord_letterOfAuth_paragraphId")
    @Expose
    private Object techRecordLetterOfAuthParagraphId;
    @SerializedName("techRecord_letterOfAuth_letterIssuer")
    @Expose
    private String techRecordLetterOfAuthLetterIssuer;

    @SerializedName("techRecord_make")
    @Expose
    private String techRecordMake;
    @SerializedName("techRecord_manufactureYear")
    @Expose
    private Object techRecordManufactureYear;
    @SerializedName("techRecord_manufacturerDetails")
    @Expose
    private String techRecordManufacturerDetails;

    @SerializedName("techRecord_maxLoadOnCoupling")
    @Expose
    private Integer techRecordMaxLoadOnCoupling;
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
    private Integer techRecordNoOfAxles;

    @SerializedName("techRecord_notes")
    @Expose
    private String techRecordNotes;
    @SerializedName("techRecord_ntaNumber")
    @Expose
    private String techRecordNtaNumber;
    @SerializedName("techRecord_plates")
    @Expose
    private List<TechRecordPlate> techRecordPlates;
    @SerializedName("techRecord_purchaserDetails_address1")
    @Expose
    private String techRecordPurchaserDetailsAddress1;
    @SerializedName("techRecord_purchaserDetails_address2")
    @Expose
    private String techRecordPurchaserDetailsAddress2;
    @SerializedName("techRecord_purchaserDetails_address3")
    @Expose
    private String techRecordPurchaserDetailsAddress3;
    @SerializedName("techRecord_purchaserDetails_emailAddress")
    @Expose
    private String techRecordPurchaserDetailsEmailAddress;
    @SerializedName("techRecord_purchaserDetails_faxNumber")
    @Expose
    private String techRecordPurchaserDetailsFaxNumber;
    @SerializedName("techRecord_purchaserDetails_name")
    @Expose
    private String techRecordPurchaserDetailsName;
    @SerializedName("techRecord_purchaserDetails_postCode")
    @Expose
    private String techRecordPurchaserDetailsPostCode;
    @SerializedName("techRecord_purchaserDetails_postTown")
    @Expose
    private String techRecordPurchaserDetailsPostTown;
    @SerializedName("techRecord_purchaserDetails_purchaserNotes")
    @Expose
    private String techRecordPurchaserDetailsPurchaserNotes;
    @SerializedName("techRecord_purchaserDetails_telephoneNumber")
    @Expose
    private String techRecordPurchaserDetailsTelephoneNumber;
    @SerializedName("techRecord_manufacturerDetails_address1")
    @Expose
    private String techRecordManufacturerDetailsAddress1;
    @SerializedName("techRecord_manufacturerDetails_address2")
    @Expose
    private String techRecordManufacturerDetailsAddress2;
    @SerializedName("techRecord_manufacturerDetails_address3")
    @Expose
    private String techRecordManufacturerDetailsAddress3;
    @SerializedName("techRecord_manufacturerDetails_emailAddress")
    @Expose
    private String techRecordManufacturerDetailsEmailAddress;
    @SerializedName("techRecord_manufacturerDetails_faxNumber")
    @Expose
    private String techRecordManufacturerDetailsFaxNumber;
    @SerializedName("techRecord_manufacturerDetails_name")
    @Expose
    private String techRecordManufacturerDetailsName;
    @SerializedName("techRecord_manufacturerDetails_postCode")
    @Expose
    private String techRecordManufacturerDetailsPostCode;
    @SerializedName("techRecord_manufacturerDetails_postTown")
    @Expose
    private String techRecordManufacturerDetailsPostTown;
    @SerializedName("techRecord_manufacturerDetails_manufacturerNotes")
    @Expose
    private String techRecordManufacturerDetailsManufacturerNotes;
    @SerializedName("techRecord_manufacturerDetails_telephoneNumber")
    @Expose
    private String techRecordManufacturerDetailsTelephoneNumber;

    @SerializedName("techRecord_rearAxleToRearTrl")
    @Expose
    private Integer techRecordRearAxleToRearTrl;

    @SerializedName("techRecord_reasonForCreation")
    @Expose
    private String techRecordReasonForCreation;
    @SerializedName("techRecord_regnDate")
    @Expose
    private Object techRecordRegnDate;

    @SerializedName("techRecord_roadFriendly")
    @Expose
    private Boolean techRecordRoadFriendly;

    @SerializedName("techRecord_statusCode")
    @Expose
    private TechRecordTrlComplete.TechRecordStatusCode techRecordStatusCode;

    @SerializedName("techRecord_suspensionType")
    @Expose
    private String techRecordSuspensionType;

    @SerializedName("techRecord_tyreUseCode")
    @Expose
    private Object techRecordTyreUseCode;
    @SerializedName("techRecord_variantNumber")
    @Expose
    private String techRecordVariantNumber;
    @SerializedName("techRecord_variantVersionNumber")
    @Expose
    private String techRecordVariantVersionNumber;

    @SerializedName("techRecord_vehicleClass_description")
    @Expose
    private Object techRecordVehicleClassDescription;

    @SerializedName("techRecord_vehicleConfiguration")
    @Expose
    private TechRecordTrlComplete.TechRecordVehicleConfiguration techRecordVehicleConfiguration;

    @SerializedName("techRecord_vehicleType")
    @Expose
    private Object techRecordVehicleType;
    @SerializedName("trailerId")
    @Expose
    private String trailerId;

    @SerializedName("vin")
    @Expose
    private String vin;
    @SerializedName("techRecord_axles")
    @Expose
    private List<TechRecordAxle> techRecordAxles;
    @SerializedName("techRecord_hiddenInVta")
    @Expose
    private Boolean techRecordHiddenInVta;
    @SerializedName("techRecord_updateType")
    @Expose
    private String techRecordUpdateType;
    @SerializedName("techRecord_authIntoService_cocIssueDate")
    @Expose
    private String techRecordAuthIntoServiceCocIssueDate;
    @SerializedName("techRecord_authIntoService_dateReceived")
    @Expose
    private String techRecordAuthIntoServiceDateReceived;
    @SerializedName("techRecord_authIntoService_datePending")
    @Expose
    private String techRecordAuthIntoServiceDatePending;
    @SerializedName("techRecord_authIntoService_dateAuthorised")
    @Expose
    private String techRecordAuthIntoServiceDateAuthorised;
    @SerializedName("techRecord_authIntoService_dateRejected")
    @Expose
    private String techRecordAuthIntoServiceDateRejected;
    @SerializedName("techRecord_dimensions_axleSpacing")
    @Expose
    private List<TechRecordDimensionsAxleSpacing> techRecordDimensionsAxleSpacing;

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

    public List<TechRecordAdrPassCertificateDetail> getTechRecordAdrPassCertificateDetails() {
        return techRecordAdrPassCertificateDetails;
    }

    public void setTechRecordAdrPassCertificateDetails(List<TechRecordAdrPassCertificateDetail> techRecordAdrPassCertificateDetails) {
        this.techRecordAdrPassCertificateDetails = techRecordAdrPassCertificateDetails;
    }

    public Boolean getTechRecordAlterationMarker() {
        return techRecordAlterationMarker;
    }

    public void setTechRecordAlterationMarker(Boolean techRecordAlterationMarker) {
        this.techRecordAlterationMarker = techRecordAlterationMarker;
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

    public Object getTechRecordApprovalType() {
        return techRecordApprovalType;
    }

    public void setTechRecordApprovalType(Object techRecordApprovalType) {
        this.techRecordApprovalType = techRecordApprovalType;
    }

    public String getTechRecordApprovalTypeNumber() {
        return techRecordApprovalTypeNumber;
    }

    public void setTechRecordApprovalTypeNumber(String techRecordApprovalTypeNumber) {
        this.techRecordApprovalTypeNumber = techRecordApprovalTypeNumber;
    }

    public String getTechRecordAuthIntoService() {
        return techRecordAuthIntoService;
    }

    public void setTechRecordAuthIntoService(String techRecordAuthIntoService) {
        this.techRecordAuthIntoService = techRecordAuthIntoService;
    }

    public String getTechRecordBatchId() {
        return techRecordBatchId;
    }

    public void setTechRecordBatchId(String techRecordBatchId) {
        this.techRecordBatchId = techRecordBatchId;
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

    public Boolean getTechRecordBrakesAntilockBrakingSystem() {
        return techRecordBrakesAntilockBrakingSystem;
    }

    public void setTechRecordBrakesAntilockBrakingSystem(Boolean techRecordBrakesAntilockBrakingSystem) {
        this.techRecordBrakesAntilockBrakingSystem = techRecordBrakesAntilockBrakingSystem;
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

    public Integer getTechRecordCentreOfRearmostAxleToRearOfTrl() {
        return techRecordCentreOfRearmostAxleToRearOfTrl;
    }

    public void setTechRecordCentreOfRearmostAxleToRearOfTrl(Integer techRecordCentreOfRearmostAxleToRearOfTrl) {
        this.techRecordCentreOfRearmostAxleToRearOfTrl = techRecordCentreOfRearmostAxleToRearOfTrl;
    }

    public String getTechRecordConversionRefNo() {
        return techRecordConversionRefNo;
    }

    public void setTechRecordConversionRefNo(String techRecordConversionRefNo) {
        this.techRecordConversionRefNo = techRecordConversionRefNo;
    }


    public Integer getTechRecordCouplingCenterToRearAxleMax() {
        return techRecordCouplingCenterToRearAxleMax;
    }


    public void setTechRecordCouplingCenterToRearAxleMax(Integer techRecordCouplingCenterToRearAxleMax) {
        this.techRecordCouplingCenterToRearAxleMax = techRecordCouplingCenterToRearAxleMax;
    }


    public Integer getTechRecordCouplingCenterToRearAxleMin() {
        return techRecordCouplingCenterToRearAxleMin;
    }


    public void setTechRecordCouplingCenterToRearAxleMin(Integer techRecordCouplingCenterToRearAxleMin) {
        this.techRecordCouplingCenterToRearAxleMin = techRecordCouplingCenterToRearAxleMin;
    }


    public Integer getTechRecordCouplingCenterToRearTrlMax() {
        return techRecordCouplingCenterToRearTrlMax;
    }


    public void setTechRecordCouplingCenterToRearTrlMax(Integer techRecordCouplingCenterToRearTrlMax) {
        this.techRecordCouplingCenterToRearTrlMax = techRecordCouplingCenterToRearTrlMax;
    }


    public Integer getTechRecordCouplingCenterToRearTrlMin() {
        return techRecordCouplingCenterToRearTrlMin;
    }


    public void setTechRecordCouplingCenterToRearTrlMin(Integer techRecordCouplingCenterToRearTrlMin) {
        this.techRecordCouplingCenterToRearTrlMin = techRecordCouplingCenterToRearTrlMin;
    }


    public String getTechRecordCouplingType() {
        return techRecordCouplingType;
    }


    public void setTechRecordCouplingType(String techRecordCouplingType) {
        this.techRecordCouplingType = techRecordCouplingType;
    }

    public Boolean getTechRecordDepartmentalVehicleMarker() {
        return techRecordDepartmentalVehicleMarker;
    }

    public void setTechRecordDepartmentalVehicleMarker(Boolean techRecordDepartmentalVehicleMarker) {
        this.techRecordDepartmentalVehicleMarker = techRecordDepartmentalVehicleMarker;
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


    public TechRecordTrlComplete.TechRecordEuVehicleCategory getTechRecordEuVehicleCategory() {
        return techRecordEuVehicleCategory;
    }


    public void setTechRecordEuVehicleCategory(TechRecordTrlComplete.TechRecordEuVehicleCategory techRecordEuVehicleCategory) {
        this.techRecordEuVehicleCategory = techRecordEuVehicleCategory;
    }


    public String getTechRecordFirstUseDate() {
        return techRecordFirstUseDate;
    }


    public void setTechRecordFirstUseDate(String techRecordFirstUseDate) {
        this.techRecordFirstUseDate = techRecordFirstUseDate;
    }

    public Object getTechRecordFrameDescription() {
        return techRecordFrameDescription;
    }

    public void setTechRecordFrameDescription(Object techRecordFrameDescription) {
        this.techRecordFrameDescription = techRecordFrameDescription;
    }


    public Integer getTechRecordFrontAxleToRearAxle() {
        return techRecordFrontAxleToRearAxle;
    }


    public void setTechRecordFrontAxleToRearAxle(Integer techRecordFrontAxleToRearAxle) {
        this.techRecordFrontAxleToRearAxle = techRecordFrontAxleToRearAxle;
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

    public Object getTechRecordLetterOfAuthLetterType() {
        return techRecordLetterOfAuthLetterType;
    }

    public void setTechRecordLetterOfAuthLetterType(Object techRecordLetterOfAuthLetterType) {
        this.techRecordLetterOfAuthLetterType = techRecordLetterOfAuthLetterType;
    }

    public String getTechRecordLetterOfAuthLetterDateRequested() {
        return techRecordLetterOfAuthLetterDateRequested;
    }

    public void setTechRecordLetterOfAuthLetterDateRequested(String techRecordLetterOfAuthLetterDateRequested) {
        this.techRecordLetterOfAuthLetterDateRequested = techRecordLetterOfAuthLetterDateRequested;
    }

    public Object getTechRecordLetterOfAuthParagraphId() {
        return techRecordLetterOfAuthParagraphId;
    }

    public void setTechRecordLetterOfAuthParagraphId(Object techRecordLetterOfAuthParagraphId) {
        this.techRecordLetterOfAuthParagraphId = techRecordLetterOfAuthParagraphId;
    }

    public String getTechRecordLetterOfAuthLetterIssuer() {
        return techRecordLetterOfAuthLetterIssuer;
    }

    public void setTechRecordLetterOfAuthLetterIssuer(String techRecordLetterOfAuthLetterIssuer) {
        this.techRecordLetterOfAuthLetterIssuer = techRecordLetterOfAuthLetterIssuer;
    }


    public String getTechRecordMake() {
        return techRecordMake;
    }


    public void setTechRecordMake(String techRecordMake) {
        this.techRecordMake = techRecordMake;
    }

    public Object getTechRecordManufactureYear() {
        return techRecordManufactureYear;
    }

    public void setTechRecordManufactureYear(Object techRecordManufactureYear) {
        this.techRecordManufactureYear = techRecordManufactureYear;
    }

    public String getTechRecordManufacturerDetails() {
        return techRecordManufacturerDetails;
    }

    public void setTechRecordManufacturerDetails(String techRecordManufacturerDetails) {
        this.techRecordManufacturerDetails = techRecordManufacturerDetails;
    }


    public Integer getTechRecordMaxLoadOnCoupling() {
        return techRecordMaxLoadOnCoupling;
    }


    public void setTechRecordMaxLoadOnCoupling(Integer techRecordMaxLoadOnCoupling) {
        this.techRecordMaxLoadOnCoupling = techRecordMaxLoadOnCoupling;
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


    public Integer getTechRecordNoOfAxles() {
        return techRecordNoOfAxles;
    }


    public void setTechRecordNoOfAxles(Integer techRecordNoOfAxles) {
        this.techRecordNoOfAxles = techRecordNoOfAxles;
    }


    public String getTechRecordNotes() {
        return techRecordNotes;
    }


    public void setTechRecordNotes(String techRecordNotes) {
        this.techRecordNotes = techRecordNotes;
    }

    public String getTechRecordNtaNumber() {
        return techRecordNtaNumber;
    }

    public void setTechRecordNtaNumber(String techRecordNtaNumber) {
        this.techRecordNtaNumber = techRecordNtaNumber;
    }

    public List<TechRecordPlate> getTechRecordPlates() {
        return techRecordPlates;
    }

    public void setTechRecordPlates(List<TechRecordPlate> techRecordPlates) {
        this.techRecordPlates = techRecordPlates;
    }

    public String getTechRecordPurchaserDetailsAddress1() {
        return techRecordPurchaserDetailsAddress1;
    }

    public void setTechRecordPurchaserDetailsAddress1(String techRecordPurchaserDetailsAddress1) {
        this.techRecordPurchaserDetailsAddress1 = techRecordPurchaserDetailsAddress1;
    }

    public String getTechRecordPurchaserDetailsAddress2() {
        return techRecordPurchaserDetailsAddress2;
    }

    public void setTechRecordPurchaserDetailsAddress2(String techRecordPurchaserDetailsAddress2) {
        this.techRecordPurchaserDetailsAddress2 = techRecordPurchaserDetailsAddress2;
    }

    public String getTechRecordPurchaserDetailsAddress3() {
        return techRecordPurchaserDetailsAddress3;
    }

    public void setTechRecordPurchaserDetailsAddress3(String techRecordPurchaserDetailsAddress3) {
        this.techRecordPurchaserDetailsAddress3 = techRecordPurchaserDetailsAddress3;
    }

    public String getTechRecordPurchaserDetailsEmailAddress() {
        return techRecordPurchaserDetailsEmailAddress;
    }

    public void setTechRecordPurchaserDetailsEmailAddress(String techRecordPurchaserDetailsEmailAddress) {
        this.techRecordPurchaserDetailsEmailAddress = techRecordPurchaserDetailsEmailAddress;
    }

    public String getTechRecordPurchaserDetailsFaxNumber() {
        return techRecordPurchaserDetailsFaxNumber;
    }

    public void setTechRecordPurchaserDetailsFaxNumber(String techRecordPurchaserDetailsFaxNumber) {
        this.techRecordPurchaserDetailsFaxNumber = techRecordPurchaserDetailsFaxNumber;
    }

    public String getTechRecordPurchaserDetailsName() {
        return techRecordPurchaserDetailsName;
    }

    public void setTechRecordPurchaserDetailsName(String techRecordPurchaserDetailsName) {
        this.techRecordPurchaserDetailsName = techRecordPurchaserDetailsName;
    }

    public String getTechRecordPurchaserDetailsPostCode() {
        return techRecordPurchaserDetailsPostCode;
    }

    public void setTechRecordPurchaserDetailsPostCode(String techRecordPurchaserDetailsPostCode) {
        this.techRecordPurchaserDetailsPostCode = techRecordPurchaserDetailsPostCode;
    }

    public String getTechRecordPurchaserDetailsPostTown() {
        return techRecordPurchaserDetailsPostTown;
    }

    public void setTechRecordPurchaserDetailsPostTown(String techRecordPurchaserDetailsPostTown) {
        this.techRecordPurchaserDetailsPostTown = techRecordPurchaserDetailsPostTown;
    }

    public String getTechRecordPurchaserDetailsPurchaserNotes() {
        return techRecordPurchaserDetailsPurchaserNotes;
    }

    public void setTechRecordPurchaserDetailsPurchaserNotes(String techRecordPurchaserDetailsPurchaserNotes) {
        this.techRecordPurchaserDetailsPurchaserNotes = techRecordPurchaserDetailsPurchaserNotes;
    }

    public String getTechRecordPurchaserDetailsTelephoneNumber() {
        return techRecordPurchaserDetailsTelephoneNumber;
    }

    public void setTechRecordPurchaserDetailsTelephoneNumber(String techRecordPurchaserDetailsTelephoneNumber) {
        this.techRecordPurchaserDetailsTelephoneNumber = techRecordPurchaserDetailsTelephoneNumber;
    }

    public String getTechRecordManufacturerDetailsAddress1() {
        return techRecordManufacturerDetailsAddress1;
    }

    public void setTechRecordManufacturerDetailsAddress1(String techRecordManufacturerDetailsAddress1) {
        this.techRecordManufacturerDetailsAddress1 = techRecordManufacturerDetailsAddress1;
    }

    public String getTechRecordManufacturerDetailsAddress2() {
        return techRecordManufacturerDetailsAddress2;
    }

    public void setTechRecordManufacturerDetailsAddress2(String techRecordManufacturerDetailsAddress2) {
        this.techRecordManufacturerDetailsAddress2 = techRecordManufacturerDetailsAddress2;
    }

    public String getTechRecordManufacturerDetailsAddress3() {
        return techRecordManufacturerDetailsAddress3;
    }

    public void setTechRecordManufacturerDetailsAddress3(String techRecordManufacturerDetailsAddress3) {
        this.techRecordManufacturerDetailsAddress3 = techRecordManufacturerDetailsAddress3;
    }

    public String getTechRecordManufacturerDetailsEmailAddress() {
        return techRecordManufacturerDetailsEmailAddress;
    }

    public void setTechRecordManufacturerDetailsEmailAddress(String techRecordManufacturerDetailsEmailAddress) {
        this.techRecordManufacturerDetailsEmailAddress = techRecordManufacturerDetailsEmailAddress;
    }

    public String getTechRecordManufacturerDetailsFaxNumber() {
        return techRecordManufacturerDetailsFaxNumber;
    }

    public void setTechRecordManufacturerDetailsFaxNumber(String techRecordManufacturerDetailsFaxNumber) {
        this.techRecordManufacturerDetailsFaxNumber = techRecordManufacturerDetailsFaxNumber;
    }

    public String getTechRecordManufacturerDetailsName() {
        return techRecordManufacturerDetailsName;
    }

    public void setTechRecordManufacturerDetailsName(String techRecordManufacturerDetailsName) {
        this.techRecordManufacturerDetailsName = techRecordManufacturerDetailsName;
    }

    public String getTechRecordManufacturerDetailsPostCode() {
        return techRecordManufacturerDetailsPostCode;
    }

    public void setTechRecordManufacturerDetailsPostCode(String techRecordManufacturerDetailsPostCode) {
        this.techRecordManufacturerDetailsPostCode = techRecordManufacturerDetailsPostCode;
    }

    public String getTechRecordManufacturerDetailsPostTown() {
        return techRecordManufacturerDetailsPostTown;
    }

    public void setTechRecordManufacturerDetailsPostTown(String techRecordManufacturerDetailsPostTown) {
        this.techRecordManufacturerDetailsPostTown = techRecordManufacturerDetailsPostTown;
    }

    public String getTechRecordManufacturerDetailsManufacturerNotes() {
        return techRecordManufacturerDetailsManufacturerNotes;
    }

    public void setTechRecordManufacturerDetailsManufacturerNotes(String techRecordManufacturerDetailsManufacturerNotes) {
        this.techRecordManufacturerDetailsManufacturerNotes = techRecordManufacturerDetailsManufacturerNotes;
    }

    public String getTechRecordManufacturerDetailsTelephoneNumber() {
        return techRecordManufacturerDetailsTelephoneNumber;
    }

    public void setTechRecordManufacturerDetailsTelephoneNumber(String techRecordManufacturerDetailsTelephoneNumber) {
        this.techRecordManufacturerDetailsTelephoneNumber = techRecordManufacturerDetailsTelephoneNumber;
    }


    public Integer getTechRecordRearAxleToRearTrl() {
        return techRecordRearAxleToRearTrl;
    }


    public void setTechRecordRearAxleToRearTrl(Integer techRecordRearAxleToRearTrl) {
        this.techRecordRearAxleToRearTrl = techRecordRearAxleToRearTrl;
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


    public TechRecordTrlComplete.TechRecordStatusCode getTechRecordStatusCode() {
        return techRecordStatusCode;
    }


    public void setTechRecordStatusCode(TechRecordTrlComplete.TechRecordStatusCode techRecordStatusCode) {
        this.techRecordStatusCode = techRecordStatusCode;
    }


    public String getTechRecordSuspensionType() {
        return techRecordSuspensionType;
    }


    public void setTechRecordSuspensionType(String techRecordSuspensionType) {
        this.techRecordSuspensionType = techRecordSuspensionType;
    }


    public Object getTechRecordTyreUseCode() {
        return techRecordTyreUseCode;
    }


    public void setTechRecordTyreUseCode(Object techRecordTyreUseCode) {
        this.techRecordTyreUseCode = techRecordTyreUseCode;
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


    public Object getTechRecordVehicleClassDescription() {
        return techRecordVehicleClassDescription;
    }


    public void setTechRecordVehicleClassDescription(Object techRecordVehicleClassDescription) {
        this.techRecordVehicleClassDescription = techRecordVehicleClassDescription;
    }


    public TechRecordTrlComplete.TechRecordVehicleConfiguration getTechRecordVehicleConfiguration() {
        return techRecordVehicleConfiguration;
    }


    public void setTechRecordVehicleConfiguration(TechRecordTrlComplete.TechRecordVehicleConfiguration techRecordVehicleConfiguration) {
        this.techRecordVehicleConfiguration = techRecordVehicleConfiguration;
    }


    public Object getTechRecordVehicleType() {
        return techRecordVehicleType;
    }


    public void setTechRecordVehicleType(Object techRecordVehicleType) {
        this.techRecordVehicleType = techRecordVehicleType;
    }

    public String getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(String trailerId) {
        this.trailerId = trailerId;
    }


    public String getVin() {
        return vin;
    }


    public void setVin(String vin) {
        this.vin = vin;
    }

    public List<TechRecordAxle> getTechRecordAxles() {
        return techRecordAxles;
    }

    public void setTechRecordAxles(List<TechRecordAxle> techRecordAxles) {
        this.techRecordAxles = techRecordAxles;
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

    public String getTechRecordAuthIntoServiceCocIssueDate() {
        return techRecordAuthIntoServiceCocIssueDate;
    }

    public void setTechRecordAuthIntoServiceCocIssueDate(String techRecordAuthIntoServiceCocIssueDate) {
        this.techRecordAuthIntoServiceCocIssueDate = techRecordAuthIntoServiceCocIssueDate;
    }

    public String getTechRecordAuthIntoServiceDateReceived() {
        return techRecordAuthIntoServiceDateReceived;
    }

    public void setTechRecordAuthIntoServiceDateReceived(String techRecordAuthIntoServiceDateReceived) {
        this.techRecordAuthIntoServiceDateReceived = techRecordAuthIntoServiceDateReceived;
    }

    public String getTechRecordAuthIntoServiceDatePending() {
        return techRecordAuthIntoServiceDatePending;
    }

    public void setTechRecordAuthIntoServiceDatePending(String techRecordAuthIntoServiceDatePending) {
        this.techRecordAuthIntoServiceDatePending = techRecordAuthIntoServiceDatePending;
    }

    public String getTechRecordAuthIntoServiceDateAuthorised() {
        return techRecordAuthIntoServiceDateAuthorised;
    }

    public void setTechRecordAuthIntoServiceDateAuthorised(String techRecordAuthIntoServiceDateAuthorised) {
        this.techRecordAuthIntoServiceDateAuthorised = techRecordAuthIntoServiceDateAuthorised;
    }

    public String getTechRecordAuthIntoServiceDateRejected() {
        return techRecordAuthIntoServiceDateRejected;
    }

    public void setTechRecordAuthIntoServiceDateRejected(String techRecordAuthIntoServiceDateRejected) {
        this.techRecordAuthIntoServiceDateRejected = techRecordAuthIntoServiceDateRejected;
    }

    public List<TechRecordDimensionsAxleSpacing> getTechRecordDimensionsAxleSpacing() {
        return techRecordDimensionsAxleSpacing;
    }

    public void setTechRecordDimensionsAxleSpacing(List<TechRecordDimensionsAxleSpacing> techRecordDimensionsAxleSpacing) {
        this.techRecordDimensionsAxleSpacing = techRecordDimensionsAxleSpacing;
    }

    @Generated("jsonschema2pojo")
    public enum TechRecordEuVehicleCategory {

        @SerializedName("m1")
        M_1("m1"),
        @SerializedName("m2")
        M_2("m2"),
        @SerializedName("m3")
        M_3("m3"),
        @SerializedName("n1")
        N_1("n1"),
        @SerializedName("n2")
        N_2("n2"),
        @SerializedName("n3")
        N_3("n3"),
        @SerializedName("o1")
        O_1("o1"),
        @SerializedName("o2")
        O_2("o2"),
        @SerializedName("o3")
        O_3("o3"),
        @SerializedName("o4")
        O_4("o4"),
        @SerializedName("l1e-a")
        L_1_E_A("l1e-a"),
        @SerializedName("l1e")
        L_1_E("l1e"),
        @SerializedName("l2e")
        L_2_E("l2e"),
        @SerializedName("l3e")
        L_3_E("l3e"),
        @SerializedName("l4e")
        L_4_E("l4e"),
        @SerializedName("l5e")
        L_5_E("l5e"),
        @SerializedName("l6e")
        L_6_E("l6e"),
        @SerializedName("l7e")
        L_7_E("l7e");
        private final static Map<String, TechRecordTrlComplete.TechRecordEuVehicleCategory> CONSTANTS = new HashMap<String, TechRecordTrlComplete.TechRecordEuVehicleCategory>();

        static {
            for (TechRecordTrlComplete.TechRecordEuVehicleCategory c : values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private final String value;

        TechRecordEuVehicleCategory(String value) {
            this.value = value;
        }

        public static TechRecordTrlComplete.TechRecordEuVehicleCategory fromValue(String value) {
            TechRecordTrlComplete.TechRecordEuVehicleCategory constant = CONSTANTS.get(value);
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
        PROVISIONAL("provisional"),
        @SerializedName("current")
        CURRENT("current"),
        @SerializedName("archived")
        ARCHIVED("archived");
        private final static Map<String, TechRecordTrlComplete.TechRecordStatusCode> CONSTANTS = new HashMap<String, TechRecordTrlComplete.TechRecordStatusCode>();

        static {
            for (TechRecordTrlComplete.TechRecordStatusCode c : values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private final String value;

        TechRecordStatusCode(String value) {
            this.value = value;
        }

        public static TechRecordTrlComplete.TechRecordStatusCode fromValue(String value) {
            TechRecordTrlComplete.TechRecordStatusCode constant = CONSTANTS.get(value);
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
    public enum TechRecordVehicleConfiguration {

        @SerializedName("centre axle drawbar")
        CENTRE_AXLE_DRAWBAR("centre axle drawbar"),
        @SerializedName("semi-car transporter")
        SEMI_CAR_TRANSPORTER("semi-car transporter"),
        @SerializedName("semi-trailer")
        SEMI_TRAILER("semi-trailer"),
        @SerializedName("long semi-trailer")
        LONG_SEMI_TRAILER("long semi-trailer"),
        @SerializedName("low loader")
        LOW_LOADER("low loader"),
        @SerializedName("other")
        OTHER("other"),
        @SerializedName("drawbar")
        DRAWBAR("drawbar"),
        @SerializedName("four-in-line")
        FOUR_IN_LINE("four-in-line"),
        @SerializedName("dolly")
        DOLLY("dolly"),
        @SerializedName("full drawbar")
        FULL_DRAWBAR("full drawbar");
        private final static Map<String, TechRecordTrlComplete.TechRecordVehicleConfiguration> CONSTANTS = new HashMap<String, TechRecordTrlComplete.TechRecordVehicleConfiguration>();

        static {
            for (TechRecordTrlComplete.TechRecordVehicleConfiguration c : values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private final String value;

        TechRecordVehicleConfiguration(String value) {
            this.value = value;
        }

        public static TechRecordTrlComplete.TechRecordVehicleConfiguration fromValue(String value) {
            TechRecordTrlComplete.TechRecordVehicleConfiguration constant = CONSTANTS.get(value);
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