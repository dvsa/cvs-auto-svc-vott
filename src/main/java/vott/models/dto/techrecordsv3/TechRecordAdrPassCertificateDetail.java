package vott.models.dto.techrecordsv3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;
import java.util.HashMap;
import java.util.Map;

/**
 * ADR Certificate Details
 * <p>
 */
@Generated("jsonschema2pojo")
public class TechRecordAdrPassCertificateDetail {

    /**
     * (Required)
     */
    @SerializedName("createdByName")
    @Expose
    private String createdByName;
    /**
     * ADR Certificate Types
     * <p>
     * <p>
     * (Required)
     */
    @SerializedName("certificateType")
    @Expose
    private CertificateType certificateType;
    /**
     * (Required)
     */
    @SerializedName("generatedTimestamp")
    @Expose
    private String generatedTimestamp;
    /**
     * (Required)
     */
    @SerializedName("certificateId")
    @Expose
    private String certificateId;

    /**
     * (Required)
     */
    public String getCreatedByName() {
        return createdByName;
    }

    /**
     * (Required)
     */
    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    /**
     * ADR Certificate Types
     * <p>
     * <p>
     * (Required)
     */
    public CertificateType getCertificateType() {
        return certificateType;
    }

    /**
     * ADR Certificate Types
     * <p>
     * <p>
     * (Required)
     */
    public void setCertificateType(CertificateType certificateType) {
        this.certificateType = certificateType;
    }

    /**
     * (Required)
     */
    public String getGeneratedTimestamp() {
        return generatedTimestamp;
    }

    /**
     * (Required)
     */
    public void setGeneratedTimestamp(String generatedTimestamp) {
        this.generatedTimestamp = generatedTimestamp;
    }

    /**
     * (Required)
     */
    public String getCertificateId() {
        return certificateId;
    }

    /**
     * (Required)
     */
    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }


    /**
     * ADR Certificate Types
     * <p>
     */
    @Generated("jsonschema2pojo")
    public enum CertificateType {

        @SerializedName("PASS")
        PASS("PASS"),
        @SerializedName("REPLACEMENT")
        REPLACEMENT("REPLACEMENT");
        private final String value;
        private final static Map<String, CertificateType> CONSTANTS = new HashMap<String, CertificateType>();

        static {
            for (CertificateType c : values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        CertificateType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static CertificateType fromValue(String value) {
            CertificateType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
