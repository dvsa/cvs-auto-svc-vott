package vott.models.dto.techrecordsv3;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TechRecordV3 {
    @SerializedName("vin")
    @Expose
    private String vin;

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}
