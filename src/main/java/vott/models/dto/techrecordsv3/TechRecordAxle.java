package vott.models.dto.techrecordsv3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

/**
 * HGV Axles
 * <p>
 */
@Generated("jsonschema2pojo")
public class TechRecordAxle {
    /**
     * (Required)
     * weights_gbWeight
     * weights_designWeight
     * tyres_tyreCode
     * tyres_tyreSize
     * tyres_fitmentCode
     */
    @SerializedName("parkingBrakeMrk")
    @Expose
    private Boolean parkingBrakeMrk;
    @SerializedName("axleNumber")
    @Expose
    private Integer axleNumber;

    @SerializedName("weights_gbWeight")
    @Expose
    private Integer weightsGbWeight;

    @SerializedName("weights_designWeight")
    @Expose
    private Integer weightsDesignWeight;
    @SerializedName("weights_eecWeight")
    @Expose
    private Integer weightsEecWeight;
    @SerializedName("tyres_tyreCode")
    @Expose
    private Integer tyresTyreCode;
    @SerializedName("tyres_tyreSize")
    @Expose
    private String tyresTyreSize;
    @SerializedName("tyres_plyRating")
    @Expose
    private String tyresPlyRating;
    @SerializedName("tyres_fitmentCode")
    @Expose
    private Object tyresFitmentCode;
    @SerializedName("tyres_dataTrAxles")
    @Expose
    private Integer tyresDataTrAxles;

    public Boolean getParkingBrakeMrk() {
        return parkingBrakeMrk;
    }

    public void setParkingBrakeMrk(Boolean parkingBrakeMrk) {
        this.parkingBrakeMrk = parkingBrakeMrk;
    }

    public Integer getAxleNumber() {
        return axleNumber;
    }

    public void setAxleNumber(Integer axleNumber) {
        this.axleNumber = axleNumber;
    }

    public Integer getWeightsGbWeight() {
        return weightsGbWeight;
    }

    public void setWeightsGbWeight(Integer weightsGbWeight) {
        this.weightsGbWeight = weightsGbWeight;
    }

    public Integer getWeightsDesignWeight() {
        return weightsDesignWeight;
    }

    public void setWeightsDesignWeight(Integer weightsDesignWeight) {
        this.weightsDesignWeight = weightsDesignWeight;
    }

    public Integer getWeightsEecWeight() {
        return weightsEecWeight;
    }

    public void setWeightsEecWeight(Integer weightsEecWeight) {
        this.weightsEecWeight = weightsEecWeight;
    }

    public Integer getTyresTyreCode() {
        return tyresTyreCode;
    }

    public void setTyresTyreCode(Integer tyresTyreCode) {
        this.tyresTyreCode = tyresTyreCode;
    }

    public String getTyresTyreSize() {
        return tyresTyreSize;
    }

    public void setTyresTyreSize(String tyresTyreSize) {
        this.tyresTyreSize = tyresTyreSize;
    }

    public String getTyresPlyRating() {
        return tyresPlyRating;
    }

    public void setTyresPlyRating(String tyresPlyRating) {
        this.tyresPlyRating = tyresPlyRating;
    }

    public Object getTyresFitmentCode() {
        return tyresFitmentCode;
    }

    public void setTyresFitmentCode(Object tyresFitmentCode) {
        this.tyresFitmentCode = tyresFitmentCode;
    }

    public Integer getTyresDataTrAxles() {
        return tyresDataTrAxles;
    }

    public void setTyresDataTrAxles(Integer tyresDataTrAxles) {
        this.tyresDataTrAxles = tyresDataTrAxles;
    }

}
