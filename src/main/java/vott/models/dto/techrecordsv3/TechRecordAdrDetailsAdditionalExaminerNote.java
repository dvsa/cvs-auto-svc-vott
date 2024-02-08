package vott.models.dto.techrecordsv3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class TechRecordAdrDetailsAdditionalExaminerNote {

    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("createdAtDate")
    @Expose
    private Object createdAtDate;
    @SerializedName("lastUpdatedBy")
    @Expose
    private String lastUpdatedBy;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Object getCreatedAtDate() {
        return createdAtDate;
    }

    public void setCreatedAtDate(Object createdAtDate) {
        this.createdAtDate = createdAtDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

}
