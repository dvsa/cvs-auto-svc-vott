package vott.models.dto.techrecordsv3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tech Record PUT Car Complete
 * <p>
 */
@Generated("jsonschema2pojo")
public class TechRecordCarComplete extends TechRecordV3 {

    /**
     * (Required)
     * vin
     * techRecord_vehicleSubclass
     * techRecord_vehicleType
     * techRecord_statusCode
     * techRecord_noOfAxles
     * techRecord_reasonForCreation
     * techRecord_vehicleConfiguration
     */

    @Expose
    @SerializedName("systemNumber")
    private String systemNumber;
    @Expose
    @SerializedName("createdTimestamp")
    private String createdTimestamp;
    @SerializedName("vin")
    @Expose
    private String vin;
    @SerializedName("primaryVrm")
    @Expose
    private String primaryVrm;

    @SerializedName("techRecord_reasonForCreation")
    @Expose
    private String techRecordReasonForCreation;

    @SerializedName("techRecord_vehicleType")
    @Expose
    private Object techRecordVehicleType;

    @SerializedName("techRecord_statusCode")
    @Expose
    private TechRecordCarComplete.TechRecordStatusCode techRecordStatusCode;
    @SerializedName("techRecord_regnDate")
    @Expose
    private String techRecordRegnDate;
    @SerializedName("techRecord_manufactureYear")
    @Expose
    private Integer techRecordManufactureYear;

    @SerializedName("techRecord_noOfAxles")
    @Expose
    private Integer techRecordNoOfAxles;
    @SerializedName("techRecord_notes")
    @Expose
    private String techRecordNotes;

    @SerializedName("techRecord_vehicleSubclass")
    @Expose
    private List<TechRecordVehicleSubclass> techRecordVehicleSubclass;
    @SerializedName("techRecord_hiddenInVta")
    @Expose
    private Boolean techRecordHiddenInVta;
    @SerializedName("techRecord_updateType")
    @Expose
    private String techRecordUpdateType;
    @SerializedName("secondaryVrms")
    @Expose
    private List<String> secondaryVrms;

    @SerializedName("techRecord_vehicleConfiguration")
    @Expose
    private TechRecordCarComplete.TechRecordVehicleConfiguration techRecordVehicleConfiguration;
    @SerializedName("techRecord_euVehicleCategory")
    @Expose
    private Object techRecordEuVehicleCategory;
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

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getPrimaryVrm() {
        return primaryVrm;
    }

    public void setPrimaryVrm(String primaryVrm) {
        this.primaryVrm = primaryVrm;
    }

    public String getTechRecordReasonForCreation() {
        return techRecordReasonForCreation;
    }

    public void setTechRecordReasonForCreation(String techRecordReasonForCreation) {
        this.techRecordReasonForCreation = techRecordReasonForCreation;
    }

    public Object getTechRecordVehicleType() {
        return techRecordVehicleType;
    }

    public void setTechRecordVehicleType(Object techRecordVehicleType) {
        this.techRecordVehicleType = techRecordVehicleType;
    }

    public TechRecordCarComplete.TechRecordStatusCode getTechRecordStatusCode() {
        return techRecordStatusCode;
    }

    public void setTechRecordStatusCode(TechRecordCarComplete.TechRecordStatusCode techRecordStatusCode) {
        this.techRecordStatusCode = techRecordStatusCode;
    }

    public String getTechRecordRegnDate() {
        return techRecordRegnDate;
    }

    public void setTechRecordRegnDate(String techRecordRegnDate) {
        this.techRecordRegnDate = techRecordRegnDate;
    }

    public Integer getTechRecordManufactureYear() {
        return techRecordManufactureYear;
    }

    public void setTechRecordManufactureYear(Integer techRecordManufactureYear) {
        this.techRecordManufactureYear = techRecordManufactureYear;
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

    public List<TechRecordVehicleSubclass> getTechRecordVehicleSubclass() {
        return techRecordVehicleSubclass;
    }

    public void setTechRecordVehicleSubclass(List<TechRecordVehicleSubclass> techRecordVehicleSubclass) {
        this.techRecordVehicleSubclass = techRecordVehicleSubclass;
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

    public List<String> getSecondaryVrms() {
        return secondaryVrms;
    }

    public void setSecondaryVrms(List<String> secondaryVrms) {
        this.secondaryVrms = secondaryVrms;
    }

    public TechRecordCarComplete.TechRecordVehicleConfiguration getTechRecordVehicleConfiguration() {
        return techRecordVehicleConfiguration;
    }

    public void setTechRecordVehicleConfiguration(
            TechRecordCarComplete.TechRecordVehicleConfiguration techRecordVehicleConfiguration) {
        this.techRecordVehicleConfiguration = techRecordVehicleConfiguration;
    }

    public Object getTechRecordEuVehicleCategory() {
        return techRecordEuVehicleCategory;
    }

    public void setTechRecordEuVehicleCategory(Object techRecordEuVehicleCategory) {
        this.techRecordEuVehicleCategory = techRecordEuVehicleCategory;
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

    @Generated("jsonschema2pojo")
    public enum TechRecordStatusCode {

        @SerializedName("provisional")
        PROVISIONAL("provisional"),
        @SerializedName("current")
        CURRENT("current"),
        @SerializedName("archived")
        ARCHIVED("archived");

        private final static Map<String, TechRecordCarComplete.TechRecordStatusCode> CONSTANTS = new HashMap<String, TechRecordCarComplete.TechRecordStatusCode>();

        static {
            for (TechRecordCarComplete.TechRecordStatusCode c : values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private final String value;

        TechRecordStatusCode(String value) {
            this.value = value;
        }

        public static TechRecordCarComplete.TechRecordStatusCode fromValue(String value) {
            TechRecordCarComplete.TechRecordStatusCode constant = CONSTANTS.get(value);
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
        ARTICULATED("articulated"),
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

        private final static Map<String, TechRecordCarComplete.TechRecordVehicleConfiguration> CONSTANTS = new HashMap<String, TechRecordCarComplete.TechRecordVehicleConfiguration>();

        static {
            for (TechRecordCarComplete.TechRecordVehicleConfiguration c : values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private final String value;

        TechRecordVehicleConfiguration(String value) {
            this.value = value;
        }

        public static TechRecordCarComplete.TechRecordVehicleConfiguration fromValue(String value) {
            TechRecordCarComplete.TechRecordVehicleConfiguration constant = CONSTANTS.get(value);
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
