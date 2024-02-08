
package vott.models.dto.techrecordsv3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Tech Record PUT Motorcycle Complete
 * <p>
 *
 *
 */
@Generated("jsonschema2pojo")
public class TechRecordMotorcycleComplete {
    /**
     * (Required)
     * techRecord_numberOfWheelsDriven
     * techRecord_vehicleClass_description
     * techRecord_reasonForCreation
     * techRecord_vehicleType
     * techRecord_statusCode
     * techRecord_vehicleConfiguration
     * vin
     */

    @SerializedName("secondaryVrms")
    @Expose
    private List<String> secondaryVrms;
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
    @SerializedName("partialVin")
    @Expose
    private String partialVin;
    @SerializedName("primaryVrm")
    @Expose
    private String primaryVrm;
    @SerializedName("systemNumber")
    @Expose
    private String systemNumber;
    @SerializedName("techRecord_euVehicleCategory")
    @Expose
    private Object techRecordEuVehicleCategory;
    @SerializedName("techRecord_manufactureYear")
    @Expose
    private Integer techRecordManufactureYear;
    @SerializedName("techRecord_recordCompleteness")
    @Expose
    private String techRecordRecordCompleteness;
    @SerializedName("techRecord_noOfAxles")
    @Expose
    private Integer techRecordNoOfAxles;
    @SerializedName("techRecord_notes")
    @Expose
    private String techRecordNotes;

    @SerializedName("techRecord_reasonForCreation")
    @Expose
    private String techRecordReasonForCreation;
    @SerializedName("techRecord_regnDate")
    @Expose
    private String techRecordRegnDate;

    @SerializedName("techRecord_statusCode")
    @Expose
    private TechRecordMotorcycleComplete.TechRecordStatusCode techRecordStatusCode;

    @SerializedName("techRecord_vehicleClass_description")
    @Expose
    private TechRecordMotorcycleComplete.TechRecordVehicleClassDescription techRecordVehicleClassDescription;

    @SerializedName("techRecord_vehicleConfiguration")
    @Expose
    private TechRecordMotorcycleComplete.TechRecordVehicleConfiguration techRecordVehicleConfiguration;

    @SerializedName("techRecord_vehicleType")
    @Expose
    private Object techRecordVehicleType;

    @SerializedName("vin")
    @Expose
    private String vin;

    @SerializedName("techRecord_numberOfWheelsDriven")
    @Expose
    private Integer techRecordNumberOfWheelsDriven;
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

    public String getPartialVin() {
        return partialVin;
    }

    public void setPartialVin(String partialVin) {
        this.partialVin = partialVin;
    }

    public String getPrimaryVrm() {
        return primaryVrm;
    }

    public void setPrimaryVrm(String primaryVrm) {
        this.primaryVrm = primaryVrm;
    }

    public String getSystemNumber() {
        return systemNumber;
    }

    public void setSystemNumber(String systemNumber) {
        this.systemNumber = systemNumber;
    }

    public Object getTechRecordEuVehicleCategory() {
        return techRecordEuVehicleCategory;
    }

    public void setTechRecordEuVehicleCategory(Object techRecordEuVehicleCategory) {
        this.techRecordEuVehicleCategory = techRecordEuVehicleCategory;
    }

    public Integer getTechRecordManufactureYear() {
        return techRecordManufactureYear;
    }

    public void setTechRecordManufactureYear(Integer techRecordManufactureYear) {
        this.techRecordManufactureYear = techRecordManufactureYear;
    }

    public String getTechRecordRecordCompleteness() {
        return techRecordRecordCompleteness;
    }

    public void setTechRecordRecordCompleteness(String techRecordRecordCompleteness) {
        this.techRecordRecordCompleteness = techRecordRecordCompleteness;
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

    public String getTechRecordRegnDate() {
        return techRecordRegnDate;
    }

    public void setTechRecordRegnDate(String techRecordRegnDate) {
        this.techRecordRegnDate = techRecordRegnDate;
    }


    public TechRecordMotorcycleComplete.TechRecordStatusCode getTechRecordStatusCode() {
        return techRecordStatusCode;
    }


    public void setTechRecordStatusCode(TechRecordMotorcycleComplete.TechRecordStatusCode techRecordStatusCode) {
        this.techRecordStatusCode = techRecordStatusCode;
    }


    public TechRecordMotorcycleComplete.TechRecordVehicleClassDescription getTechRecordVehicleClassDescription() {
        return techRecordVehicleClassDescription;
    }


    public void setTechRecordVehicleClassDescription(TechRecordMotorcycleComplete.TechRecordVehicleClassDescription techRecordVehicleClassDescription) {
        this.techRecordVehicleClassDescription = techRecordVehicleClassDescription;
    }


    public TechRecordMotorcycleComplete.TechRecordVehicleConfiguration getTechRecordVehicleConfiguration() {
        return techRecordVehicleConfiguration;
    }


    public void setTechRecordVehicleConfiguration(TechRecordMotorcycleComplete.TechRecordVehicleConfiguration techRecordVehicleConfiguration) {
        this.techRecordVehicleConfiguration = techRecordVehicleConfiguration;
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


    public Integer getTechRecordNumberOfWheelsDriven() {
        return techRecordNumberOfWheelsDriven;
    }


    public void setTechRecordNumberOfWheelsDriven(Integer techRecordNumberOfWheelsDriven) {
        this.techRecordNumberOfWheelsDriven = techRecordNumberOfWheelsDriven;
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
    
    @Generated("jsonschema2pojo")
    public enum TechRecordStatusCode {

        @SerializedName("provisional")
        PROVISIONAL("provisional"),
        @SerializedName("current")
        CURRENT("current"),
        @SerializedName("archived")
        ARCHIVED("archived");
        private final String value;
        private final static Map<String, TechRecordMotorcycleComplete.TechRecordStatusCode> CONSTANTS = new HashMap<String, TechRecordMotorcycleComplete.TechRecordStatusCode>();

        static {
            for (TechRecordMotorcycleComplete.TechRecordStatusCode c: values()) {
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

        public static TechRecordMotorcycleComplete.TechRecordStatusCode fromValue(String value) {
            TechRecordMotorcycleComplete.TechRecordStatusCode constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
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
        private final String value;
        private final static Map<String, TechRecordMotorcycleComplete.TechRecordVehicleClassDescription> CONSTANTS = new HashMap<String, TechRecordMotorcycleComplete.TechRecordVehicleClassDescription>();

        static {
            for (TechRecordMotorcycleComplete.TechRecordVehicleClassDescription c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        TechRecordVehicleClassDescription(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static TechRecordMotorcycleComplete.TechRecordVehicleClassDescription fromValue(String value) {
            TechRecordMotorcycleComplete.TechRecordVehicleClassDescription constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
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
        private final String value;
        private final static Map<String, TechRecordMotorcycleComplete.TechRecordVehicleConfiguration> CONSTANTS = new HashMap<String, TechRecordMotorcycleComplete.TechRecordVehicleConfiguration>();

        static {
            for (TechRecordMotorcycleComplete.TechRecordVehicleConfiguration c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        TechRecordVehicleConfiguration(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static TechRecordMotorcycleComplete.TechRecordVehicleConfiguration fromValue(String value) {
            TechRecordMotorcycleComplete.TechRecordVehicleConfiguration constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}