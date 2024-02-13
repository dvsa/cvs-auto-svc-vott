package vott.models.dto.techrecordsv3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.processing.Generated;
import java.util.Objects;

@Generated("jsonschema2pojo")
public class TechRecordAdrDetailsAdditionalExaminerNote {

    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("createdAtDate")
    @Expose
    private String createdAtDate;
    @SerializedName("lastUpdatedBy")
    @Expose
    private String lastUpdatedBy;

    public TechRecordAdrDetailsAdditionalExaminerNote(String createdAtDate, String lastUpdatedBy, String note) {
        this.createdAtDate = createdAtDate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TechRecordAdrDetailsAdditionalExaminerNote that = (TechRecordAdrDetailsAdditionalExaminerNote) o;
        return Objects.equals(getNote(), that.getNote()) && Objects.equals(getCreatedAtDate(), that.getCreatedAtDate()) && Objects.equals(getLastUpdatedBy(), that.getLastUpdatedBy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNote(), getCreatedAtDate(), getLastUpdatedBy());
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreatedAtDate() {
        return createdAtDate;
    }

    public void setCreatedAtDate(String createdAtDate) {
        this.createdAtDate = createdAtDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

}
