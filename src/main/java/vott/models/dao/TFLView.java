package vott.models.dao;

import lombok.Data;

@Data
public class TFLView {
    private String vrmTrm;
    private String vin;
    private String certificateNumber;
    private String modTypeCode;
    private String testStatus;
    private String emissionClassificationCode;
    private String testTypeStartTimestamp;
    private String testExpiryDate;
    private String pNumber;
    private String issueDate;
}
