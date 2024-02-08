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

    @SerializedName("parkingBrakeMrk")
    @Expose
    private Boolean parkingBrakeMrk;
    @SerializedName("axleNumber")
    @Expose
    private Integer axleNumber;
    /**
     * (Required)
     */
    @SerializedName("weights_gbWeight")
    @Expose
    private Integer weightsGbWeight;
    /**
     * (Required)
     */
    @SerializedName("weights_designWeight")
    @Expose
    private Integer weightsDesignWeight;
    @SerializedName("weights_eecWeight")
    @Expose
    private Integer weightsEecWeight;
    /**
     * (Required)
     */
    @SerializedName("tyres_tyreCode")
    @Expose
    private Integer tyresTyreCode;
    /**
     * (Required)
     */
    @SerializedName("tyres_tyreSize")
    @Expose
    private String tyresTyreSize;
    @SerializedName("tyres_plyRating")
    @Expose
    private String tyresPlyRating;
    /**
     * (Required)
     */
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

    /**
     * (Required)
     */
    public Integer getWeightsGbWeight() {
        return weightsGbWeight;
    }

    /**
     * (Required)
     */
    public void setWeightsGbWeight(Integer weightsGbWeight) {
        this.weightsGbWeight = weightsGbWeight;
    }

    /**
     * (Required)
     */
    public Integer getWeightsDesignWeight() {
        return weightsDesignWeight;
    }

    /**
     * (Required)
     */
    public void setWeightsDesignWeight(Integer weightsDesignWeight) {
        this.weightsDesignWeight = weightsDesignWeight;
    }

    public Integer getWeightsEecWeight() {
        return weightsEecWeight;
    }

    public void setWeightsEecWeight(Integer weightsEecWeight) {
        this.weightsEecWeight = weightsEecWeight;
    }

    /**
     * (Required)
     */
    public Integer getTyresTyreCode() {
        return tyresTyreCode;
    }

    /**
     * (Required)
     */
    public void setTyresTyreCode(Integer tyresTyreCode) {
        this.tyresTyreCode = tyresTyreCode;
    }

    /**
     * (Required)
     */
    public String getTyresTyreSize() {
        return tyresTyreSize;
    }

    /**
     * (Required)
     */
    public void setTyresTyreSize(String tyresTyreSize) {
        this.tyresTyreSize = tyresTyreSize;
    }

    public String getTyresPlyRating() {
        return tyresPlyRating;
    }

    public void setTyresPlyRating(String tyresPlyRating) {
        this.tyresPlyRating = tyresPlyRating;
    }

    /**
     * (Required)
     */
    public Object getTyresFitmentCode() {
        return tyresFitmentCode;
    }

    /**
     * (Required)
     */
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
