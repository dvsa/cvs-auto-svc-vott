package vott.models.dto.techrecordsv3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Tech Record PUT PSV Complete
 * <p>
 */
@Generated("jsonschema2pojo")
public class TechRecordPsvComplete extends TechRecordV3{
    /**
     * (Required)
     * vin
     * techRecord_vehicleConfiguration
     * techRecord_vehicleSize
     * techRecord_seatsLowerDeck
     * techRecord_seatsUpperDeck
     * techRecord_vehicleType
     * techRecord_noOfAxles
     * techRecord_statusCode
     * techRecord_reasonForCreation
     * techRecord_vehicleClass_description
     * techRecord_axles
     * techRecord_standingCapacity
     * techRecord_numberOfSeatbelts
     * techRecord_bodyMake
     * techRecord_bodyModel
     * techRecord_chassisMake
     * techRecord_chassisModel
     * techRecord_grossKerbWeight
     * techRecord_grossLadenWeight
     * techRecord_dda_certificateIssued
     * techRecord_brakes_brakeCode
     * techRecord_brakes_dataTrBrakeOne
     * techRecord_brakes_dataTrBrakeTwo
     * techRecord_brakes_dataTrBrakeThree
     * techRecord_brakes_brakeForceWheelsNotLocked_parkingBrakeForceA
     * techRecord_brakes_brakeForceWheelsNotLocked_secondaryBrakeForceA
     * techRecord_brakes_brakeForceWheelsNotLocked_serviceBrakeForceA
     * techRecord_brakes_brakeForceWheelsUpToHalfLocked_parkingBrakeForceB
     * techRecord_brakes_brakeForceWheelsUpToHalfLocked_secondaryBrakeForceB
     * techRecord_brakes_brakeForceWheelsUpToHalfLocked_serviceBrakeForceB
     */
    @SerializedName("partialVin")
    @Expose
    private String partialVin;
    @SerializedName("systemNumber")
    @Expose
    private String systemNumber;
    @Expose
    @SerializedName("createdTimestamp")
    private String createdTimestamp ; 
    @SerializedName("primaryVrm")
    @Expose
    private String primaryVrm;

    @SerializedName("techRecord_vehicleType")
    @Expose
    private Object techRecordVehicleType;

    @SerializedName("techRecord_statusCode")
    @Expose
    private TechRecordPsvComplete.TechRecordStatusCode techRecordStatusCode;

    @SerializedName("techRecord_reasonForCreation")
    @Expose
    private String techRecordReasonForCreation;

    @SerializedName("techRecord_vehicleConfiguration")
    @Expose
    private TechRecordPsvComplete.TechRecordVehicleConfiguration techRecordVehicleConfiguration;

    @SerializedName("techRecord_vehicleSize")
    @Expose
    private TechRecordPsvComplete.TechRecordVehicleSize techRecordVehicleSize;

    @SerializedName("techRecord_seatsLowerDeck")
    @Expose
    private Integer techRecordSeatsLowerDeck;

    @SerializedName("techRecord_seatsUpperDeck")
    @Expose
    private Integer techRecordSeatsUpperDeck;

    @SerializedName("techRecord_vehicleClass_description")
    @Expose
    private TechRecordPsvComplete.TechRecordVehicleClassDescription techRecordVehicleClassDescription;
    @SerializedName("techRecord_hiddenInVta")
    @Expose
    private Boolean techRecordHiddenInVta;
    @SerializedName("techRecord_recordCompleteness")
    @Expose
    private String techRecordRecordCompleteness;
    @SerializedName("techRecord_euVehicleCategory")
    @Expose
    private Object techRecordEuVehicleCategory;
    @SerializedName("techRecord_regnDate")
    @Expose
    private String techRecordRegnDate;
    @SerializedName("techRecord_manufactureYear")
    @Expose
    private Object techRecordManufactureYear;

    @SerializedName("techRecord_noOfAxles")
    @Expose
    private Integer techRecordNoOfAxles;
    @SerializedName("techRecord_departmentalVehicleMarker")
    @Expose
    private Boolean techRecordDepartmentalVehicleMarker;
    @SerializedName("techRecord_alterationMarker")
    @Expose
    private Boolean techRecordAlterationMarker;
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
    @SerializedName("techRecord_bodyType_description")
    @Expose
    private Object techRecordBodyTypeDescription;
    @SerializedName("techRecord_bodyType_code")
    @Expose
    private String techRecordBodyTypeCode;
    @SerializedName("techRecord_functionCode")
    @Expose
    private String techRecordFunctionCode;
    @SerializedName("techRecord_conversionRefNo")
    @Expose
    private String techRecordConversionRefNo;
    @SerializedName("techRecord_grossGbWeight")
    @Expose
    private Integer techRecordGrossGbWeight;
    @SerializedName("techRecord_grossDesignWeight")
    @Expose
    private Integer techRecordGrossDesignWeight;

    @SerializedName("techRecord_dda_certificateIssued")
    @Expose
    private Boolean techRecordDdaCertificateIssued;
    @SerializedName("techRecord_dda_wheelchairCapacity")
    @Expose
    private Integer techRecordDdaWheelchairCapacity;
    @SerializedName("techRecord_dda_wheelchairFittings")
    @Expose
    private String techRecordDdaWheelchairFittings;
    @SerializedName("techRecord_dda_wheelchairLiftPresent")
    @Expose
    private Boolean techRecordDdaWheelchairLiftPresent;
    @SerializedName("techRecord_dda_wheelchairLiftInformation")
    @Expose
    private String techRecordDdaWheelchairLiftInformation;
    @SerializedName("techRecord_dda_wheelchairRampPresent")
    @Expose
    private Boolean techRecordDdaWheelchairRampPresent;
    @SerializedName("techRecord_dda_wheelchairRampInformation")
    @Expose
    private String techRecordDdaWheelchairRampInformation;
    @SerializedName("techRecord_dda_minEmergencyExits")
    @Expose
    private Integer techRecordDdaMinEmergencyExits;
    @SerializedName("techRecord_dda_outswing")
    @Expose
    private String techRecordDdaOutswing;
    @SerializedName("techRecord_dda_ddaSchedules")
    @Expose
    private String techRecordDdaDdaSchedules;
    @SerializedName("techRecord_dda_seatbeltsFitted")
    @Expose
    private Integer techRecordDdaSeatbeltsFitted;
    @SerializedName("techRecord_dda_ddaNotes")
    @Expose
    private String techRecordDdaDdaNotes;

    @SerializedName("techRecord_standingCapacity")
    @Expose
    private Integer techRecordStandingCapacity;
    @SerializedName("techRecord_speedLimiterMrk")
    @Expose
    private Boolean techRecordSpeedLimiterMrk;
    @SerializedName("techRecord_tachoExemptMrk")
    @Expose
    private Boolean techRecordTachoExemptMrk;
    @SerializedName("techRecord_euroStandard")
    @Expose
    private Object techRecordEuroStandard;
    @SerializedName("techRecord_fuelPropulsionSystem")
    @Expose
    private Object techRecordFuelPropulsionSystem;
    @SerializedName("techRecord_emissionsLimit")
    @Expose
    private Double techRecordEmissionsLimit;
    @SerializedName("techRecord_trainDesignWeight")
    @Expose
    private Integer techRecordTrainDesignWeight;

    @SerializedName("techRecord_numberOfSeatbelts")
    @Expose
    private String techRecordNumberOfSeatbelts;
    @SerializedName("techRecord_seatbeltInstallationApprovalDate")
    @Expose
    private String techRecordSeatbeltInstallationApprovalDate;
    @SerializedName("techRecord_coifSerialNumber")
    @Expose
    private String techRecordCoifSerialNumber;
    @SerializedName("techRecord_coifCertifierName")
    @Expose
    private String techRecordCoifCertifierName;
    @SerializedName("techRecord_coifDate")
    @Expose
    private Object techRecordCoifDate;

    @SerializedName("techRecord_bodyMake")
    @Expose
    private String techRecordBodyMake;

    @SerializedName("techRecord_bodyModel")
    @Expose
    private String techRecordBodyModel;

    @SerializedName("techRecord_chassisMake")
    @Expose
    private String techRecordChassisMake;

    @SerializedName("techRecord_chassisModel")
    @Expose
    private String techRecordChassisModel;
    @SerializedName("techRecord_modelLiteral")
    @Expose
    private String techRecordModelLiteral;
    @SerializedName("techRecord_speedRestriction")
    @Expose
    private Double techRecordSpeedRestriction;

    @SerializedName("techRecord_grossKerbWeight")
    @Expose
    private Double techRecordGrossKerbWeight;

    @SerializedName("techRecord_grossLadenWeight")
    @Expose
    private Double techRecordGrossLadenWeight;
    @SerializedName("techRecord_unladenWeight")
    @Expose
    private Double techRecordUnladenWeight;
    @SerializedName("techRecord_maxTrainGbWeight")
    @Expose
    private Double techRecordMaxTrainGbWeight;
    @SerializedName("techRecord_dimensions_length")
    @Expose
    private Integer techRecordDimensionsLength;
    @SerializedName("techRecord_dimensions_width")
    @Expose
    private Integer techRecordDimensionsWidth;
    @SerializedName("techRecord_dimensions_height")
    @Expose
    private Integer techRecordDimensionsHeight;
    @SerializedName("techRecord_frontAxleToRearAxle")
    @Expose
    private Integer techRecordFrontAxleToRearAxle;
    @SerializedName("techRecord_remarks")
    @Expose
    private String techRecordRemarks;
    @SerializedName("techRecord_dispensations")
    @Expose
    private String techRecordDispensations;

    @SerializedName("techRecord_axles")
    @Expose
    private List<TechRecordAxle> techRecordAxles;
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
    @SerializedName("techRecord_brakes_dtpNumber")
    @Expose
    private String techRecordBrakesDtpNumber;

    @SerializedName("techRecord_brakes_brakeCode")
    @Expose
    private String techRecordBrakesBrakeCode;
    @SerializedName("techRecord_brakes_brakeCodeOriginal")
    @Expose
    private String techRecordBrakesBrakeCodeOriginal;

    @SerializedName("techRecord_brakes_dataTrBrakeOne")
    @Expose
    private String techRecordBrakesDataTrBrakeOne;

    @SerializedName("techRecord_brakes_dataTrBrakeTwo")
    @Expose
    private String techRecordBrakesDataTrBrakeTwo;

    @SerializedName("techRecord_brakes_dataTrBrakeThree")
    @Expose
    private String techRecordBrakesDataTrBrakeThree;
    @SerializedName("techRecord_brakes_retarderBrakeOne")
    @Expose
    private Object techRecordBrakesRetarderBrakeOne;
    @SerializedName("techRecord_brakes_retarderBrakeTwo")
    @Expose
    private Object techRecordBrakesRetarderBrakeTwo;

    @SerializedName("techRecord_brakes_brakeForceWheelsNotLocked_parkingBrakeForceA")
    @Expose
    private Integer techRecordBrakesBrakeForceWheelsNotLockedParkingBrakeForceA;

    @SerializedName("techRecord_brakes_brakeForceWheelsNotLocked_secondaryBrakeForceA")
    @Expose
    private Integer techRecordBrakesBrakeForceWheelsNotLockedSecondaryBrakeForceA;

    @SerializedName("techRecord_brakes_brakeForceWheelsNotLocked_serviceBrakeForceA")
    @Expose
    private Integer techRecordBrakesBrakeForceWheelsNotLockedServiceBrakeForceA;

    @SerializedName("techRecord_brakes_brakeForceWheelsUpToHalfLocked_parkingBrakeForceB")
    @Expose
    private Integer techRecordBrakesBrakeForceWheelsUpToHalfLockedParkingBrakeForceB;

    @SerializedName("techRecord_brakes_brakeForceWheelsUpToHalfLocked_secondaryBrakeForceB")
    @Expose
    private Integer techRecordBrakesBrakeForceWheelsUpToHalfLockedSecondaryBrakeForceB;

    @SerializedName("techRecord_brakes_brakeForceWheelsUpToHalfLocked_serviceBrakeForceB")
    @Expose
    private Integer techRecordBrakesBrakeForceWheelsUpToHalfLockedServiceBrakeForceB;
    @SerializedName("techRecord_microfilm_microfilmDocumentType")
    @Expose
    private Object techRecordMicrofilmMicrofilmDocumentType;
    @SerializedName("techRecord_microfilm_microfilmRollNumber")
    @Expose
    private String techRecordMicrofilmMicrofilmRollNumber;
    @SerializedName("techRecord_microfilm_microfilmSerialNumber")
    @Expose
    private String techRecordMicrofilmMicrofilmSerialNumber;
    @SerializedName("techRecord_brakeCode")
    @Expose
    private String techRecordBrakeCode;
    @SerializedName("secondaryVrms")
    @Expose
    private List<String> secondaryVrms;
    @SerializedName("techRecord_updateType")
    @Expose
    private String techRecordUpdateType;
    @SerializedName("techRecord_applicationId")
    @Expose
    private String techRecordApplicationId;



    public String getPartialVin() {
        return partialVin;
    }

    public void setPartialVin(String partialVin) {
        this.partialVin = partialVin;
    }

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

    public String getPrimaryVrm() {
        return primaryVrm;
    }

    public void setPrimaryVrm(String primaryVrm) {
        this.primaryVrm = primaryVrm;
    }


    public Object getTechRecordVehicleType() {
        return techRecordVehicleType;
    }


    public void setTechRecordVehicleType(Object techRecordVehicleType) {
        this.techRecordVehicleType = techRecordVehicleType;
    }


    public TechRecordPsvComplete.TechRecordStatusCode getTechRecordStatusCode() {
        return techRecordStatusCode;
    }


    public void setTechRecordStatusCode(TechRecordPsvComplete.TechRecordStatusCode techRecordStatusCode) {
        this.techRecordStatusCode = techRecordStatusCode;
    }


    public String getTechRecordReasonForCreation() {
        return techRecordReasonForCreation;
    }


    public void setTechRecordReasonForCreation(String techRecordReasonForCreation) {
        this.techRecordReasonForCreation = techRecordReasonForCreation;
    }


    public TechRecordPsvComplete.TechRecordVehicleConfiguration getTechRecordVehicleConfiguration() {
        return techRecordVehicleConfiguration;
    }


    public void setTechRecordVehicleConfiguration(TechRecordPsvComplete.TechRecordVehicleConfiguration techRecordVehicleConfiguration) {
        this.techRecordVehicleConfiguration = techRecordVehicleConfiguration;
    }


    public TechRecordPsvComplete.TechRecordVehicleSize getTechRecordVehicleSize() {
        return techRecordVehicleSize;
    }


    public void setTechRecordVehicleSize(TechRecordPsvComplete.TechRecordVehicleSize techRecordVehicleSize) {
        this.techRecordVehicleSize = techRecordVehicleSize;
    }


    public Integer getTechRecordSeatsLowerDeck() {
        return techRecordSeatsLowerDeck;
    }


    public void setTechRecordSeatsLowerDeck(Integer techRecordSeatsLowerDeck) {
        this.techRecordSeatsLowerDeck = techRecordSeatsLowerDeck;
    }


    public Integer getTechRecordSeatsUpperDeck() {
        return techRecordSeatsUpperDeck;
    }


    public void setTechRecordSeatsUpperDeck(Integer techRecordSeatsUpperDeck) {
        this.techRecordSeatsUpperDeck = techRecordSeatsUpperDeck;
    }


    public TechRecordPsvComplete.TechRecordVehicleClassDescription getTechRecordVehicleClassDescription() {
        return techRecordVehicleClassDescription;
    }


    public void setTechRecordVehicleClassDescription(TechRecordPsvComplete.TechRecordVehicleClassDescription techRecordVehicleClassDescription) {
        this.techRecordVehicleClassDescription = techRecordVehicleClassDescription;
    }

    public Boolean getTechRecordHiddenInVta() {
        return techRecordHiddenInVta;
    }

    public void setTechRecordHiddenInVta(Boolean techRecordHiddenInVta) {
        this.techRecordHiddenInVta = techRecordHiddenInVta;
    }

    public String getTechRecordRecordCompleteness() {
        return techRecordRecordCompleteness;
    }

    public void setTechRecordRecordCompleteness(String techRecordRecordCompleteness) {
        this.techRecordRecordCompleteness = techRecordRecordCompleteness;
    }

    public Object getTechRecordEuVehicleCategory() {
        return techRecordEuVehicleCategory;
    }

    public void setTechRecordEuVehicleCategory(Object techRecordEuVehicleCategory) {
        this.techRecordEuVehicleCategory = techRecordEuVehicleCategory;
    }

    public String getTechRecordRegnDate() {
        return techRecordRegnDate;
    }

    public void setTechRecordRegnDate(String techRecordRegnDate) {
        this.techRecordRegnDate = techRecordRegnDate;
    }

    public Object getTechRecordManufactureYear() {
        return techRecordManufactureYear;
    }

    public void setTechRecordManufactureYear(Object techRecordManufactureYear) {
        this.techRecordManufactureYear = techRecordManufactureYear;
    }


    public Integer getTechRecordNoOfAxles() {
        return techRecordNoOfAxles;
    }


    public void setTechRecordNoOfAxles(Integer techRecordNoOfAxles) {
        this.techRecordNoOfAxles = techRecordNoOfAxles;
    }

    public Boolean getTechRecordDepartmentalVehicleMarker() {
        return techRecordDepartmentalVehicleMarker;
    }

    public void setTechRecordDepartmentalVehicleMarker(Boolean techRecordDepartmentalVehicleMarker) {
        this.techRecordDepartmentalVehicleMarker = techRecordDepartmentalVehicleMarker;
    }

    public Boolean getTechRecordAlterationMarker() {
        return techRecordAlterationMarker;
    }

    public void setTechRecordAlterationMarker(Boolean techRecordAlterationMarker) {
        this.techRecordAlterationMarker = techRecordAlterationMarker;
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

    public Object getTechRecordBodyTypeDescription() {
        return techRecordBodyTypeDescription;
    }

    public void setTechRecordBodyTypeDescription(Object techRecordBodyTypeDescription) {
        this.techRecordBodyTypeDescription = techRecordBodyTypeDescription;
    }

    public String getTechRecordBodyTypeCode() {
        return techRecordBodyTypeCode;
    }

    public void setTechRecordBodyTypeCode(String techRecordBodyTypeCode) {
        this.techRecordBodyTypeCode = techRecordBodyTypeCode;
    }

    public String getTechRecordFunctionCode() {
        return techRecordFunctionCode;
    }

    public void setTechRecordFunctionCode(String techRecordFunctionCode) {
        this.techRecordFunctionCode = techRecordFunctionCode;
    }

    public String getTechRecordConversionRefNo() {
        return techRecordConversionRefNo;
    }

    public void setTechRecordConversionRefNo(String techRecordConversionRefNo) {
        this.techRecordConversionRefNo = techRecordConversionRefNo;
    }

    public Integer getTechRecordGrossGbWeight() {
        return techRecordGrossGbWeight;
    }

    public void setTechRecordGrossGbWeight(Integer techRecordGrossGbWeight) {
        this.techRecordGrossGbWeight = techRecordGrossGbWeight;
    }

    public Integer getTechRecordGrossDesignWeight() {
        return techRecordGrossDesignWeight;
    }

    public void setTechRecordGrossDesignWeight(Integer techRecordGrossDesignWeight) {
        this.techRecordGrossDesignWeight = techRecordGrossDesignWeight;
    }


    public Boolean getTechRecordDdaCertificateIssued() {
        return techRecordDdaCertificateIssued;
    }


    public void setTechRecordDdaCertificateIssued(Boolean techRecordDdaCertificateIssued) {
        this.techRecordDdaCertificateIssued = techRecordDdaCertificateIssued;
    }

    public Integer getTechRecordDdaWheelchairCapacity() {
        return techRecordDdaWheelchairCapacity;
    }

    public void setTechRecordDdaWheelchairCapacity(Integer techRecordDdaWheelchairCapacity) {
        this.techRecordDdaWheelchairCapacity = techRecordDdaWheelchairCapacity;
    }

    public String getTechRecordDdaWheelchairFittings() {
        return techRecordDdaWheelchairFittings;
    }

    public void setTechRecordDdaWheelchairFittings(String techRecordDdaWheelchairFittings) {
        this.techRecordDdaWheelchairFittings = techRecordDdaWheelchairFittings;
    }

    public Boolean getTechRecordDdaWheelchairLiftPresent() {
        return techRecordDdaWheelchairLiftPresent;
    }

    public void setTechRecordDdaWheelchairLiftPresent(Boolean techRecordDdaWheelchairLiftPresent) {
        this.techRecordDdaWheelchairLiftPresent = techRecordDdaWheelchairLiftPresent;
    }

    public String getTechRecordDdaWheelchairLiftInformation() {
        return techRecordDdaWheelchairLiftInformation;
    }

    public void setTechRecordDdaWheelchairLiftInformation(String techRecordDdaWheelchairLiftInformation) {
        this.techRecordDdaWheelchairLiftInformation = techRecordDdaWheelchairLiftInformation;
    }

    public Boolean getTechRecordDdaWheelchairRampPresent() {
        return techRecordDdaWheelchairRampPresent;
    }

    public void setTechRecordDdaWheelchairRampPresent(Boolean techRecordDdaWheelchairRampPresent) {
        this.techRecordDdaWheelchairRampPresent = techRecordDdaWheelchairRampPresent;
    }

    public String getTechRecordDdaWheelchairRampInformation() {
        return techRecordDdaWheelchairRampInformation;
    }

    public void setTechRecordDdaWheelchairRampInformation(String techRecordDdaWheelchairRampInformation) {
        this.techRecordDdaWheelchairRampInformation = techRecordDdaWheelchairRampInformation;
    }

    public Integer getTechRecordDdaMinEmergencyExits() {
        return techRecordDdaMinEmergencyExits;
    }

    public void setTechRecordDdaMinEmergencyExits(Integer techRecordDdaMinEmergencyExits) {
        this.techRecordDdaMinEmergencyExits = techRecordDdaMinEmergencyExits;
    }

    public String getTechRecordDdaOutswing() {
        return techRecordDdaOutswing;
    }

    public void setTechRecordDdaOutswing(String techRecordDdaOutswing) {
        this.techRecordDdaOutswing = techRecordDdaOutswing;
    }

    public String getTechRecordDdaDdaSchedules() {
        return techRecordDdaDdaSchedules;
    }

    public void setTechRecordDdaDdaSchedules(String techRecordDdaDdaSchedules) {
        this.techRecordDdaDdaSchedules = techRecordDdaDdaSchedules;
    }

    public Integer getTechRecordDdaSeatbeltsFitted() {
        return techRecordDdaSeatbeltsFitted;
    }

    public void setTechRecordDdaSeatbeltsFitted(Integer techRecordDdaSeatbeltsFitted) {
        this.techRecordDdaSeatbeltsFitted = techRecordDdaSeatbeltsFitted;
    }

    public String getTechRecordDdaDdaNotes() {
        return techRecordDdaDdaNotes;
    }

    public void setTechRecordDdaDdaNotes(String techRecordDdaDdaNotes) {
        this.techRecordDdaDdaNotes = techRecordDdaDdaNotes;
    }


    public Integer getTechRecordStandingCapacity() {
        return techRecordStandingCapacity;
    }


    public void setTechRecordStandingCapacity(Integer techRecordStandingCapacity) {
        this.techRecordStandingCapacity = techRecordStandingCapacity;
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

    public Object getTechRecordEuroStandard() {
        return techRecordEuroStandard;
    }

    public void setTechRecordEuroStandard(Object techRecordEuroStandard) {
        this.techRecordEuroStandard = techRecordEuroStandard;
    }

    public Object getTechRecordFuelPropulsionSystem() {
        return techRecordFuelPropulsionSystem;
    }

    public void setTechRecordFuelPropulsionSystem(Object techRecordFuelPropulsionSystem) {
        this.techRecordFuelPropulsionSystem = techRecordFuelPropulsionSystem;
    }

    public Double getTechRecordEmissionsLimit() {
        return techRecordEmissionsLimit;
    }

    public void setTechRecordEmissionsLimit(Double techRecordEmissionsLimit) {
        this.techRecordEmissionsLimit = techRecordEmissionsLimit;
    }

    public Integer getTechRecordTrainDesignWeight() {
        return techRecordTrainDesignWeight;
    }

    public void setTechRecordTrainDesignWeight(Integer techRecordTrainDesignWeight) {
        this.techRecordTrainDesignWeight = techRecordTrainDesignWeight;
    }


    public String getTechRecordNumberOfSeatbelts() {
        return techRecordNumberOfSeatbelts;
    }


    public void setTechRecordNumberOfSeatbelts(String techRecordNumberOfSeatbelts) {
        this.techRecordNumberOfSeatbelts = techRecordNumberOfSeatbelts;
    }

    public String getTechRecordSeatbeltInstallationApprovalDate() {
        return techRecordSeatbeltInstallationApprovalDate;
    }

    public void setTechRecordSeatbeltInstallationApprovalDate(String techRecordSeatbeltInstallationApprovalDate) {
        this.techRecordSeatbeltInstallationApprovalDate = techRecordSeatbeltInstallationApprovalDate;
    }

    public String getTechRecordCoifSerialNumber() {
        return techRecordCoifSerialNumber;
    }

    public void setTechRecordCoifSerialNumber(String techRecordCoifSerialNumber) {
        this.techRecordCoifSerialNumber = techRecordCoifSerialNumber;
    }

    public String getTechRecordCoifCertifierName() {
        return techRecordCoifCertifierName;
    }

    public void setTechRecordCoifCertifierName(String techRecordCoifCertifierName) {
        this.techRecordCoifCertifierName = techRecordCoifCertifierName;
    }

    public Object getTechRecordCoifDate() {
        return techRecordCoifDate;
    }

    public void setTechRecordCoifDate(Object techRecordCoifDate) {
        this.techRecordCoifDate = techRecordCoifDate;
    }


    public String getTechRecordBodyMake() {
        return techRecordBodyMake;
    }


    public void setTechRecordBodyMake(String techRecordBodyMake) {
        this.techRecordBodyMake = techRecordBodyMake;
    }


    public String getTechRecordBodyModel() {
        return techRecordBodyModel;
    }


    public void setTechRecordBodyModel(String techRecordBodyModel) {
        this.techRecordBodyModel = techRecordBodyModel;
    }


    public String getTechRecordChassisMake() {
        return techRecordChassisMake;
    }


    public void setTechRecordChassisMake(String techRecordChassisMake) {
        this.techRecordChassisMake = techRecordChassisMake;
    }


    public String getTechRecordChassisModel() {
        return techRecordChassisModel;
    }


    public void setTechRecordChassisModel(String techRecordChassisModel) {
        this.techRecordChassisModel = techRecordChassisModel;
    }

    public String getTechRecordModelLiteral() {
        return techRecordModelLiteral;
    }

    public void setTechRecordModelLiteral(String techRecordModelLiteral) {
        this.techRecordModelLiteral = techRecordModelLiteral;
    }

    public Double getTechRecordSpeedRestriction() {
        return techRecordSpeedRestriction;
    }

    public void setTechRecordSpeedRestriction(Double techRecordSpeedRestriction) {
        this.techRecordSpeedRestriction = techRecordSpeedRestriction;
    }


    public Double getTechRecordGrossKerbWeight() {
        return techRecordGrossKerbWeight;
    }


    public void setTechRecordGrossKerbWeight(Double techRecordGrossKerbWeight) {
        this.techRecordGrossKerbWeight = techRecordGrossKerbWeight;
    }


    public Double getTechRecordGrossLadenWeight() {
        return techRecordGrossLadenWeight;
    }


    public void setTechRecordGrossLadenWeight(Double techRecordGrossLadenWeight) {
        this.techRecordGrossLadenWeight = techRecordGrossLadenWeight;
    }

    public Double getTechRecordUnladenWeight() {
        return techRecordUnladenWeight;
    }

    public void setTechRecordUnladenWeight(Double techRecordUnladenWeight) {
        this.techRecordUnladenWeight = techRecordUnladenWeight;
    }

    public Double getTechRecordMaxTrainGbWeight() {
        return techRecordMaxTrainGbWeight;
    }

    public void setTechRecordMaxTrainGbWeight(Double techRecordMaxTrainGbWeight) {
        this.techRecordMaxTrainGbWeight = techRecordMaxTrainGbWeight;
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

    public Integer getTechRecordDimensionsHeight() {
        return techRecordDimensionsHeight;
    }

    public void setTechRecordDimensionsHeight(Integer techRecordDimensionsHeight) {
        this.techRecordDimensionsHeight = techRecordDimensionsHeight;
    }

    public Integer getTechRecordFrontAxleToRearAxle() {
        return techRecordFrontAxleToRearAxle;
    }

    public void setTechRecordFrontAxleToRearAxle(Integer techRecordFrontAxleToRearAxle) {
        this.techRecordFrontAxleToRearAxle = techRecordFrontAxleToRearAxle;
    }

    public String getTechRecordRemarks() {
        return techRecordRemarks;
    }

    public void setTechRecordRemarks(String techRecordRemarks) {
        this.techRecordRemarks = techRecordRemarks;
    }

    public String getTechRecordDispensations() {
        return techRecordDispensations;
    }

    public void setTechRecordDispensations(String techRecordDispensations) {
        this.techRecordDispensations = techRecordDispensations;
    }


    public List<TechRecordAxle> getTechRecordAxles() {
        return techRecordAxles;
    }


    public void setTechRecordAxles(List<TechRecordAxle> techRecordAxles) {
        this.techRecordAxles = techRecordAxles;
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

    public String getTechRecordBrakesDtpNumber() {
        return techRecordBrakesDtpNumber;
    }

    public void setTechRecordBrakesDtpNumber(String techRecordBrakesDtpNumber) {
        this.techRecordBrakesDtpNumber = techRecordBrakesDtpNumber;
    }


    public String getTechRecordBrakesBrakeCode() {
        return techRecordBrakesBrakeCode;
    }


    public void setTechRecordBrakesBrakeCode(String techRecordBrakesBrakeCode) {
        this.techRecordBrakesBrakeCode = techRecordBrakesBrakeCode;
    }

    public String getTechRecordBrakesBrakeCodeOriginal() {
        return techRecordBrakesBrakeCodeOriginal;
    }

    public void setTechRecordBrakesBrakeCodeOriginal(String techRecordBrakesBrakeCodeOriginal) {
        this.techRecordBrakesBrakeCodeOriginal = techRecordBrakesBrakeCodeOriginal;
    }


    public String getTechRecordBrakesDataTrBrakeOne() {
        return techRecordBrakesDataTrBrakeOne;
    }


    public void setTechRecordBrakesDataTrBrakeOne(String techRecordBrakesDataTrBrakeOne) {
        this.techRecordBrakesDataTrBrakeOne = techRecordBrakesDataTrBrakeOne;
    }


    public String getTechRecordBrakesDataTrBrakeTwo() {
        return techRecordBrakesDataTrBrakeTwo;
    }


    public void setTechRecordBrakesDataTrBrakeTwo(String techRecordBrakesDataTrBrakeTwo) {
        this.techRecordBrakesDataTrBrakeTwo = techRecordBrakesDataTrBrakeTwo;
    }


    public String getTechRecordBrakesDataTrBrakeThree() {
        return techRecordBrakesDataTrBrakeThree;
    }


    public void setTechRecordBrakesDataTrBrakeThree(String techRecordBrakesDataTrBrakeThree) {
        this.techRecordBrakesDataTrBrakeThree = techRecordBrakesDataTrBrakeThree;
    }

    public Object getTechRecordBrakesRetarderBrakeOne() {
        return techRecordBrakesRetarderBrakeOne;
    }

    public void setTechRecordBrakesRetarderBrakeOne(Object techRecordBrakesRetarderBrakeOne) {
        this.techRecordBrakesRetarderBrakeOne = techRecordBrakesRetarderBrakeOne;
    }

    public Object getTechRecordBrakesRetarderBrakeTwo() {
        return techRecordBrakesRetarderBrakeTwo;
    }

    public void setTechRecordBrakesRetarderBrakeTwo(Object techRecordBrakesRetarderBrakeTwo) {
        this.techRecordBrakesRetarderBrakeTwo = techRecordBrakesRetarderBrakeTwo;
    }


    public Integer getTechRecordBrakesBrakeForceWheelsNotLockedParkingBrakeForceA() {
        return techRecordBrakesBrakeForceWheelsNotLockedParkingBrakeForceA;
    }


    public void setTechRecordBrakesBrakeForceWheelsNotLockedParkingBrakeForceA(Integer techRecordBrakesBrakeForceWheelsNotLockedParkingBrakeForceA) {
        this.techRecordBrakesBrakeForceWheelsNotLockedParkingBrakeForceA = techRecordBrakesBrakeForceWheelsNotLockedParkingBrakeForceA;
    }


    public Integer getTechRecordBrakesBrakeForceWheelsNotLockedSecondaryBrakeForceA() {
        return techRecordBrakesBrakeForceWheelsNotLockedSecondaryBrakeForceA;
    }


    public void setTechRecordBrakesBrakeForceWheelsNotLockedSecondaryBrakeForceA(Integer techRecordBrakesBrakeForceWheelsNotLockedSecondaryBrakeForceA) {
        this.techRecordBrakesBrakeForceWheelsNotLockedSecondaryBrakeForceA = techRecordBrakesBrakeForceWheelsNotLockedSecondaryBrakeForceA;
    }


    public Integer getTechRecordBrakesBrakeForceWheelsNotLockedServiceBrakeForceA() {
        return techRecordBrakesBrakeForceWheelsNotLockedServiceBrakeForceA;
    }


    public void setTechRecordBrakesBrakeForceWheelsNotLockedServiceBrakeForceA(Integer techRecordBrakesBrakeForceWheelsNotLockedServiceBrakeForceA) {
        this.techRecordBrakesBrakeForceWheelsNotLockedServiceBrakeForceA = techRecordBrakesBrakeForceWheelsNotLockedServiceBrakeForceA;
    }


    public Integer getTechRecordBrakesBrakeForceWheelsUpToHalfLockedParkingBrakeForceB() {
        return techRecordBrakesBrakeForceWheelsUpToHalfLockedParkingBrakeForceB;
    }


    public void setTechRecordBrakesBrakeForceWheelsUpToHalfLockedParkingBrakeForceB(Integer techRecordBrakesBrakeForceWheelsUpToHalfLockedParkingBrakeForceB) {
        this.techRecordBrakesBrakeForceWheelsUpToHalfLockedParkingBrakeForceB = techRecordBrakesBrakeForceWheelsUpToHalfLockedParkingBrakeForceB;
    }


    public Integer getTechRecordBrakesBrakeForceWheelsUpToHalfLockedSecondaryBrakeForceB() {
        return techRecordBrakesBrakeForceWheelsUpToHalfLockedSecondaryBrakeForceB;
    }


    public void setTechRecordBrakesBrakeForceWheelsUpToHalfLockedSecondaryBrakeForceB(Integer techRecordBrakesBrakeForceWheelsUpToHalfLockedSecondaryBrakeForceB) {
        this.techRecordBrakesBrakeForceWheelsUpToHalfLockedSecondaryBrakeForceB = techRecordBrakesBrakeForceWheelsUpToHalfLockedSecondaryBrakeForceB;
    }


    public Integer getTechRecordBrakesBrakeForceWheelsUpToHalfLockedServiceBrakeForceB() {
        return techRecordBrakesBrakeForceWheelsUpToHalfLockedServiceBrakeForceB;
    }


    public void setTechRecordBrakesBrakeForceWheelsUpToHalfLockedServiceBrakeForceB(Integer techRecordBrakesBrakeForceWheelsUpToHalfLockedServiceBrakeForceB) {
        this.techRecordBrakesBrakeForceWheelsUpToHalfLockedServiceBrakeForceB = techRecordBrakesBrakeForceWheelsUpToHalfLockedServiceBrakeForceB;
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

    public String getTechRecordBrakeCode() {
        return techRecordBrakeCode;
    }

    public void setTechRecordBrakeCode(String techRecordBrakeCode) {
        this.techRecordBrakeCode = techRecordBrakeCode;
    }

    public List<String> getSecondaryVrms() {
        return secondaryVrms;
    }

    public void setSecondaryVrms(List<String> secondaryVrms) {
        this.secondaryVrms = secondaryVrms;
    }

    public String getTechRecordUpdateType() {
        return techRecordUpdateType;
    }

    public void setTechRecordUpdateType(String techRecordUpdateType) {
        this.techRecordUpdateType = techRecordUpdateType;
    }

    public String getTechRecordApplicationId() {
        return techRecordApplicationId;
    }

    public void setTechRecordApplicationId(String techRecordApplicationId) {
        this.techRecordApplicationId = techRecordApplicationId;
    }

    @Generated("jsonschema2pojo")
    public enum TechRecordStatusCode {

        @SerializedName("provisional")
        PROVISIONAL("provisional"),
        @SerializedName("current")
        CURRENT("current"),
        @SerializedName("archived")
        ARCHIVED("archived");
        private final static Map<String, TechRecordPsvComplete.TechRecordStatusCode> CONSTANTS = new HashMap<String, TechRecordPsvComplete.TechRecordStatusCode>();

        static {
            for (TechRecordPsvComplete.TechRecordStatusCode c : values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private final String value;

        TechRecordStatusCode(String value) {
            this.value = value;
        }

        public static TechRecordPsvComplete.TechRecordStatusCode fromValue(String value) {
            TechRecordPsvComplete.TechRecordStatusCode constant = CONSTANTS.get(value);
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
    public enum TechRecordVehicleClassDescription {

        @SerializedName("small psv (ie: less than or equal to 22 seats)")
        SMALL_PSV_IE_LESS_THAN_OR_EQUAL_TO_22_SEATS("small psv (ie: less than or equal to 22 seats)"),
        @SerializedName("large psv(ie: greater than 23 seats)")
        LARGE_PSV_IE_GREATER_THAN_23_SEATS("large psv(ie: greater than 23 seats)");
        private final static Map<String, TechRecordPsvComplete.TechRecordVehicleClassDescription> CONSTANTS = new HashMap<String, TechRecordPsvComplete.TechRecordVehicleClassDescription>();

        static {
            for (TechRecordPsvComplete.TechRecordVehicleClassDescription c : values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private final String value;

        TechRecordVehicleClassDescription(String value) {
            this.value = value;
        }

        public static TechRecordPsvComplete.TechRecordVehicleClassDescription fromValue(String value) {
            TechRecordPsvComplete.TechRecordVehicleClassDescription constant = CONSTANTS.get(value);
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

        @SerializedName("rigid")
        RIGID("rigid"),
        @SerializedName("articulated")
        ARTICULATED("articulated");
        private final static Map<String, TechRecordPsvComplete.TechRecordVehicleConfiguration> CONSTANTS = new HashMap<String, TechRecordPsvComplete.TechRecordVehicleConfiguration>();

        static {
            for (TechRecordPsvComplete.TechRecordVehicleConfiguration c : values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private final String value;

        TechRecordVehicleConfiguration(String value) {
            this.value = value;
        }

        public static TechRecordPsvComplete.TechRecordVehicleConfiguration fromValue(String value) {
            TechRecordPsvComplete.TechRecordVehicleConfiguration constant = CONSTANTS.get(value);
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
    public enum TechRecordVehicleSize {

        @SerializedName("small")
        SMALL("small"),
        @SerializedName("large")
        LARGE("large");
        private final static Map<String, TechRecordPsvComplete.TechRecordVehicleSize> CONSTANTS = new HashMap<String, TechRecordPsvComplete.TechRecordVehicleSize>();

        static {
            for (TechRecordPsvComplete.TechRecordVehicleSize c : values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private final String value;

        TechRecordVehicleSize(String value) {
            this.value = value;
        }

        public static TechRecordPsvComplete.TechRecordVehicleSize fromValue(String value) {
            TechRecordPsvComplete.TechRecordVehicleSize constant = CONSTANTS.get(value);
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


    public TechRecordPsvComplete setToSystemGeneratedValues(TechRecordPsvComplete returnedTechRecord)
    {
                this.setSystemNumber(returnedTechRecord.getSystemNumber());
                this.setCreatedTimestamp(returnedTechRecord.getCreatedTimestamp());
                this.setPartialVin(returnedTechRecord.getPartialVin());
                return this;
    }

}