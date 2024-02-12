package vott.models.dto.techrecordsv3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;
import java.util.HashMap;
import java.util.Map;


/**
 * Tech Record PUT Small TRL Complete
 * <p>
 */
@Generated("jsonschema2pojo")
public class TechRecordSmallTrlComplete extends TechRecordV3 {
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

    @SerializedName("techRecord_applicantDetails_address1")
    @Expose
    private String techRecordApplicantDetailsAddress1;
    @SerializedName("techRecord_applicantDetails_address2")
    @Expose
    private String techRecordApplicantDetailsAddress2;
    @SerializedName("techRecord_applicantDetails_address3")
    @Expose
    private String techRecordApplicantDetailsAddress3;
    @SerializedName("techRecord_applicantDetails_emailAddress")
    @Expose
    private String techRecordApplicantDetailsEmailAddress;
    @SerializedName("techRecord_applicantDetails_name")
    @Expose
    private String techRecordApplicantDetailsName;
    @SerializedName("techRecord_applicantDetails_postCode")
    @Expose
    private String techRecordApplicantDetailsPostCode;
    @SerializedName("techRecord_applicantDetails_postTown")
    @Expose
    private String techRecordApplicantDetailsPostTown;
    @SerializedName("techRecord_applicantDetails_telephoneNumber")
    @Expose
    private String techRecordApplicantDetailsTelephoneNumber;

    @SerializedName("techRecord_euVehicleCategory")
    @Expose
    private TechRecordSmallTrlComplete.TechRecordEuVehicleCategory techRecordEuVehicleCategory;
    @SerializedName("techRecord_manufactureYear")
    @Expose
    private Integer techRecordManufactureYear;

    @SerializedName("techRecord_noOfAxles")
    @Expose
    private Integer techRecordNoOfAxles;
    @SerializedName("techRecord_notes")
    @Expose
    private String techRecordNotes;

    @SerializedName("techRecord_reasonForCreation")
    @Expose
    private String techRecordReasonForCreation;

    @SerializedName("techRecord_statusCode")
    @Expose
    private TechRecordSmallTrlComplete.TechRecordStatusCode techRecordStatusCode;

    @SerializedName("techRecord_vehicleClass_description")
    @Expose
    private TechRecordSmallTrlComplete.TechRecordVehicleClassDescription techRecordVehicleClassDescription;

    @SerializedName("techRecord_vehicleConfiguration")
    @Expose
    private TechRecordSmallTrlComplete.TechRecordVehicleConfiguration techRecordVehicleConfiguration;
    @SerializedName("techRecord_vehicleSubclass")
    @Expose
    private Object techRecordVehicleSubclass;

    @SerializedName("techRecord_vehicleType")
    @Expose
    private Object techRecordVehicleType;

    @SerializedName("vin")
    @Expose
    private String vin;
    @SerializedName("trailerId")
    @Expose
    private String trailerId;
    @SerializedName("techRecord_hiddenInVta")
    @Expose
    private Boolean techRecordHiddenInVta;

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

    public String getTechRecordApplicantDetailsAddress3() {
        return techRecordApplicantDetailsAddress3;
    }

    public void setTechRecordApplicantDetailsAddress3(String techRecordApplicantDetailsAddress3) {
        this.techRecordApplicantDetailsAddress3 = techRecordApplicantDetailsAddress3;
    }

    public String getTechRecordApplicantDetailsEmailAddress() {
        return techRecordApplicantDetailsEmailAddress;
    }

    public void setTechRecordApplicantDetailsEmailAddress(String techRecordApplicantDetailsEmailAddress) {
        this.techRecordApplicantDetailsEmailAddress = techRecordApplicantDetailsEmailAddress;
    }

    public String getTechRecordApplicantDetailsName() {
        return techRecordApplicantDetailsName;
    }

    public void setTechRecordApplicantDetailsName(String techRecordApplicantDetailsName) {
        this.techRecordApplicantDetailsName = techRecordApplicantDetailsName;
    }

    public String getTechRecordApplicantDetailsPostCode() {
        return techRecordApplicantDetailsPostCode;
    }

    public void setTechRecordApplicantDetailsPostCode(String techRecordApplicantDetailsPostCode) {
        this.techRecordApplicantDetailsPostCode = techRecordApplicantDetailsPostCode;
    }

    public String getTechRecordApplicantDetailsPostTown() {
        return techRecordApplicantDetailsPostTown;
    }

    public void setTechRecordApplicantDetailsPostTown(String techRecordApplicantDetailsPostTown) {
        this.techRecordApplicantDetailsPostTown = techRecordApplicantDetailsPostTown;
    }

    public String getTechRecordApplicantDetailsTelephoneNumber() {
        return techRecordApplicantDetailsTelephoneNumber;
    }

    public void setTechRecordApplicantDetailsTelephoneNumber(String techRecordApplicantDetailsTelephoneNumber) {
        this.techRecordApplicantDetailsTelephoneNumber = techRecordApplicantDetailsTelephoneNumber;
    }


    public TechRecordSmallTrlComplete.TechRecordEuVehicleCategory getTechRecordEuVehicleCategory() {
        return techRecordEuVehicleCategory;
    }


    public void setTechRecordEuVehicleCategory(TechRecordSmallTrlComplete.TechRecordEuVehicleCategory techRecordEuVehicleCategory) {
        this.techRecordEuVehicleCategory = techRecordEuVehicleCategory;
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


    public String getTechRecordReasonForCreation() {
        return techRecordReasonForCreation;
    }


    public void setTechRecordReasonForCreation(String techRecordReasonForCreation) {
        this.techRecordReasonForCreation = techRecordReasonForCreation;
    }


    public TechRecordSmallTrlComplete.TechRecordStatusCode getTechRecordStatusCode() {
        return techRecordStatusCode;
    }


    public void setTechRecordStatusCode(TechRecordSmallTrlComplete.TechRecordStatusCode techRecordStatusCode) {
        this.techRecordStatusCode = techRecordStatusCode;
    }


    public TechRecordSmallTrlComplete.TechRecordVehicleClassDescription getTechRecordVehicleClassDescription() {
        return techRecordVehicleClassDescription;
    }


    public void setTechRecordVehicleClassDescription(TechRecordSmallTrlComplete.TechRecordVehicleClassDescription techRecordVehicleClassDescription) {
        this.techRecordVehicleClassDescription = techRecordVehicleClassDescription;
    }


    public TechRecordSmallTrlComplete.TechRecordVehicleConfiguration getTechRecordVehicleConfiguration() {
        return techRecordVehicleConfiguration;
    }


    public void setTechRecordVehicleConfiguration(TechRecordSmallTrlComplete.TechRecordVehicleConfiguration techRecordVehicleConfiguration) {
        this.techRecordVehicleConfiguration = techRecordVehicleConfiguration;
    }

    public Object getTechRecordVehicleSubclass() {
        return techRecordVehicleSubclass;
    }

    public void setTechRecordVehicleSubclass(Object techRecordVehicleSubclass) {
        this.techRecordVehicleSubclass = techRecordVehicleSubclass;
    }


    public Object getTechRecordVehicleType() {
        return techRecordVehicleType;
    }


    public void setTechRecordVehicleType(Object techRecordVehicleType) {
        this.techRecordVehicleType = techRecordVehicleType;
    }


    public String getVin() {
        return vin;
    }


    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(String trailerId) {
        this.trailerId = trailerId;
    }

    public Boolean getTechRecordHiddenInVta() {
        return techRecordHiddenInVta;
    }

    public void setTechRecordHiddenInVta(Boolean techRecordHiddenInVta) {
        this.techRecordHiddenInVta = techRecordHiddenInVta;
    }

    @Generated("jsonschema2pojo")
    public enum TechRecordEuVehicleCategory {

        @SerializedName("o1")
        O_1("o1"),
        @SerializedName("o2")
        O_2("o2");
        private final static Map<String, TechRecordSmallTrlComplete.TechRecordEuVehicleCategory> CONSTANTS = new HashMap<String, TechRecordSmallTrlComplete.TechRecordEuVehicleCategory>();

        static {
            for (TechRecordSmallTrlComplete.TechRecordEuVehicleCategory c : values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private final String value;

        TechRecordEuVehicleCategory(String value) {
            this.value = value;
        }

        public static TechRecordSmallTrlComplete.TechRecordEuVehicleCategory fromValue(String value) {
            TechRecordSmallTrlComplete.TechRecordEuVehicleCategory constant = CONSTANTS.get(value);
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
        private final static Map<String, TechRecordSmallTrlComplete.TechRecordStatusCode> CONSTANTS = new HashMap<String, TechRecordSmallTrlComplete.TechRecordStatusCode>();

        static {
            for (TechRecordSmallTrlComplete.TechRecordStatusCode c : values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private final String value;

        TechRecordStatusCode(String value) {
            this.value = value;
        }

        public static TechRecordSmallTrlComplete.TechRecordStatusCode fromValue(String value) {
            TechRecordSmallTrlComplete.TechRecordStatusCode constant = CONSTANTS.get(value);
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

        @SerializedName("motorbikes over 200cc or with a sidecar")
        MOTORBIKES_OVER_200_CC_OR_WITH_A_SIDECAR("motorbikes over 200cc or with a sidecar"),
        @SerializedName("not applicable")
        NOT_APPLICABLE("not applicable"),
        @SerializedName("small psv (ie: less than or equal to 22 seats)")
        SMALL_PSV_IE_LESS_THAN_OR_EQUAL_TO_22_SEATS("small psv (ie: less than or equal to 22 seats)"),
        @SerializedName("motorbikes up to 200cc")
        MOTORBIKES_UP_TO_200_CC("motorbikes up to 200cc"),
        @SerializedName("trailer")
        TRAILER("trailer"),
        @SerializedName("large psv(ie: greater than 23 seats)")
        LARGE_PSV_IE_GREATER_THAN_23_SEATS("large psv(ie: greater than 23 seats)"),
        @SerializedName("3 wheelers")
        _3_WHEELERS("3 wheelers"),
        @SerializedName("heavy goods vehicle")
        HEAVY_GOODS_VEHICLE("heavy goods vehicle"),
        @SerializedName("MOT class 4")
        MOT_CLASS_4("MOT class 4"),
        @SerializedName("MOT class 7")
        MOT_CLASS_7("MOT class 7"),
        @SerializedName("MOT class 5")
        MOT_CLASS_5("MOT class 5");
        private final static Map<String, TechRecordSmallTrlComplete.TechRecordVehicleClassDescription> CONSTANTS = new HashMap<String, TechRecordSmallTrlComplete.TechRecordVehicleClassDescription>();

        static {
            for (TechRecordSmallTrlComplete.TechRecordVehicleClassDescription c : values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private final String value;

        TechRecordVehicleClassDescription(String value) {
            this.value = value;
        }

        public static TechRecordSmallTrlComplete.TechRecordVehicleClassDescription fromValue(String value) {
            TechRecordSmallTrlComplete.TechRecordVehicleClassDescription constant = CONSTANTS.get(value);
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
        private final static Map<String, TechRecordSmallTrlComplete.TechRecordVehicleConfiguration> CONSTANTS = new HashMap<String, TechRecordSmallTrlComplete.TechRecordVehicleConfiguration>();

        static {
            for (TechRecordSmallTrlComplete.TechRecordVehicleConfiguration c : values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private final String value;

        TechRecordVehicleConfiguration(String value) {
            this.value = value;
        }

        public static TechRecordSmallTrlComplete.TechRecordVehicleConfiguration fromValue(String value) {
            TechRecordSmallTrlComplete.TechRecordVehicleConfiguration constant = CONSTANTS.get(value);
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