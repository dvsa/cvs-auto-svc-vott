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
     * createdByName
     * certificateType
     * generatedTimeStamp
     * certificateId
     */
    @SerializedName("createdByName")
    @Expose
    private String createdByName;
    @SerializedName("certificateType")
    @Expose
    private CertificateType certificateType;

    @SerializedName("generatedTimestamp")
    @Expose
    private String generatedTimestamp;

    @SerializedName("certificateId")
    @Expose
    private String certificateId;

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public CertificateType getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(CertificateType certificateType) {
        this.certificateType = certificateType;
    }

    public String getGeneratedTimestamp() {
        return generatedTimestamp;
    }

    public void setGeneratedTimestamp(String generatedTimestamp) {
        this.generatedTimestamp = generatedTimestamp;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    @Generated("jsonschema2pojo")
    public enum CertificateType {

        @SerializedName("PASS") PASS("PASS"), @SerializedName("REPLACEMENT") REPLACEMENT("REPLACEMENT");
        private final static Map<String, CertificateType> CONSTANTS = new HashMap<String, CertificateType>();

        static {
            for (CertificateType c : values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private final String value;

        CertificateType(String value) {
            this.value = value;
        }

        public static CertificateType fromValue(String value) {
            CertificateType constant = CONSTANTS.get(value);
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
