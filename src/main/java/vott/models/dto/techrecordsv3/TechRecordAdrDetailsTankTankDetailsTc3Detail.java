package vott.models.dto.techrecordsv3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;
import java.util.Objects;

@Generated("jsonschema2pojo")
public class TechRecordAdrDetailsTankTankDetailsTc3Detail {

    @SerializedName("tc3Type")
    @Expose
    private String tc3Type;
    @SerializedName("tc3PeriodicNumber")
    @Expose
    private String tc3PeriodicNumber;
    @SerializedName("tc3PeriodicExpiryDate")
    @Expose
    private String tc3PeriodicExpiryDate;

    public TechRecordAdrDetailsTankTankDetailsTc3Detail(String tc3Type, String tc3PeriodicNumber, String tc3PeriodicExpiryDate) {
        this.tc3Type = tc3Type;
        this.tc3PeriodicNumber = tc3PeriodicNumber;
        this.tc3PeriodicExpiryDate = tc3PeriodicExpiryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TechRecordAdrDetailsTankTankDetailsTc3Detail that = (TechRecordAdrDetailsTankTankDetailsTc3Detail) o;
        return Objects.equals(getTc3Type(), that.getTc3Type()) && Objects.equals(getTc3PeriodicNumber(), that.getTc3PeriodicNumber()) && Objects.equals(getTc3PeriodicExpiryDate(), that.getTc3PeriodicExpiryDate());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getTc3Type(), getTc3PeriodicNumber(), getTc3PeriodicExpiryDate());
    }

    public Object getTc3Type() {
        return tc3Type;
    }

    public void setTc3Type(String tc3Type) {
        this.tc3Type = tc3Type;
    }

    public String getTc3PeriodicNumber() {
        return tc3PeriodicNumber;
    }

    public void setTc3PeriodicNumber(String tc3PeriodicNumber) {
        this.tc3PeriodicNumber = tc3PeriodicNumber;
    }

    public Object getTc3PeriodicExpiryDate() {
        return tc3PeriodicExpiryDate;
    }

    public void setTc3PeriodicExpiryDate(String tc3PeriodicExpiryDate) {
        this.tc3PeriodicExpiryDate = tc3PeriodicExpiryDate;
    }

}
