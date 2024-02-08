package vott.models.dto.techrecordsv3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

/**
 * TC3 Details
 * <p>
 */
@Generated("jsonschema2pojo")
public class TechRecordAdrDetailsTankTankDetailsTc3Detail {

    @SerializedName("tc3Type")
    @Expose
    private Object tc3Type;
    @SerializedName("tc3PeriodicNumber")
    @Expose
    private String tc3PeriodicNumber;
    @SerializedName("tc3PeriodicExpiryDate")
    @Expose
    private Object tc3PeriodicExpiryDate;

    public Object getTc3Type() {
        return tc3Type;
    }

    public void setTc3Type(Object tc3Type) {
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

    public void setTc3PeriodicExpiryDate(Object tc3PeriodicExpiryDate) {
        this.tc3PeriodicExpiryDate = tc3PeriodicExpiryDate;
    }

}
