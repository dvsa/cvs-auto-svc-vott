package vott.models.dao;

import lombok.Data;

@Data
public class TFLView {
    private String vrm;
    private String vin;
    private String serialNumberOfCertificate;
    private String certificationModificationType;
    private String testStatus;
    private String emissionClassificationCode;
    private String testValidFromDate;
    private String testExpiryDate;
    private String issuedBy;
    private String issueDate;
    private String issueDateTime;
}
