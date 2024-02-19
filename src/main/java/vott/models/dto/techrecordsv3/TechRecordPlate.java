package vott.models.dto.techrecordsv3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

/**
 * HGV Plates
 * <p>
 */
@Generated("jsonschema2pojo")
public class TechRecordPlate {

    @SerializedName("plateSerialNumber")
    @Expose
    private String plateSerialNumber;
    @SerializedName("plateIssueDate")
    @Expose
    private String plateIssueDate;
    @SerializedName("plateReasonForIssue")
    @Expose
    private Object plateReasonForIssue;
    @SerializedName("plateIssuer")
    @Expose
    private String plateIssuer;

    public String getPlateSerialNumber() {
        return plateSerialNumber;
    }

    public void setPlateSerialNumber(String plateSerialNumber) {
        this.plateSerialNumber = plateSerialNumber;
    }

    public String getPlateIssueDate() {
        return plateIssueDate;
    }

    public void setPlateIssueDate(String plateIssueDate) {
        this.plateIssueDate = plateIssueDate;
    }

    public Object getPlateReasonForIssue() {
        return plateReasonForIssue;
    }

    public void setPlateReasonForIssue(Object plateReasonForIssue) {
        this.plateReasonForIssue = plateReasonForIssue;
    }

    public String getPlateIssuer() {
        return plateIssuer;
    }

    public void setPlateIssuer(String plateIssuer) {
        this.plateIssuer = plateIssuer;
    }

}
