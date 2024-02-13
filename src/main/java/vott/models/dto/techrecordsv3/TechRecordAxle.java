package vott.models.dto.techrecordsv3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;
import java.util.Objects;

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
    private String tyresFitmentCode;
    @SerializedName("tyres_dataTrAxles")
    @Expose
    private Integer tyresDataTrAxles;

    public TechRecordAxle(
            Boolean parkingBrakeMrk,
            Integer axleNumber,
            Integer weightsGbWeight,
            Integer weightsDesignWeight,
            Integer weightsEecWeight,
            Integer tyresTyreCode,
            String tyresTyreSize,
            String tyresPlyRating,
            String tyresFitmentCode,
            Integer tyresDataTrAxles) {
        this.parkingBrakeMrk = parkingBrakeMrk;
        this.axleNumber = axleNumber;
        this.weightsGbWeight = weightsGbWeight;
        this.weightsDesignWeight = weightsDesignWeight;
        this.weightsEecWeight = weightsEecWeight;
        this.tyresTyreCode = tyresTyreCode;
        this.tyresTyreSize = tyresTyreSize;
        this.tyresPlyRating = tyresPlyRating;
        this.tyresFitmentCode = tyresFitmentCode;
        this.tyresDataTrAxles = tyresDataTrAxles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TechRecordAxle that = (TechRecordAxle) o;
        return Objects.equals(getParkingBrakeMrk(), that.getParkingBrakeMrk())
                && Objects.equals(getAxleNumber(), that.getAxleNumber())
                && Objects.equals(getWeightsGbWeight(), that.getWeightsGbWeight())
                && Objects.equals(getWeightsDesignWeight(), that.getWeightsDesignWeight())
                && Objects.equals(getWeightsEecWeight(), that.getWeightsEecWeight())
                && Objects.equals(getTyresTyreCode(), that.getTyresTyreCode())
                && Objects.equals(getTyresTyreSize(), that.getTyresTyreSize())
                && Objects.equals(getTyresPlyRating(), that.getTyresPlyRating())
                && Objects.equals(getTyresFitmentCode(), that.getTyresFitmentCode())
                && Objects.equals(getTyresDataTrAxles(), that.getTyresDataTrAxles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getParkingBrakeMrk(), getAxleNumber(), getWeightsGbWeight(), getWeightsDesignWeight(), getWeightsEecWeight(), getTyresTyreCode(), getTyresTyreSize(), getTyresPlyRating(), getTyresFitmentCode(), getTyresDataTrAxles());
    }

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

    public String getTyresFitmentCode() {
        return tyresFitmentCode;
    }

    public void setTyresFitmentCode(String tyresFitmentCode) {
        this.tyresFitmentCode = tyresFitmentCode;
    }

    public Integer getTyresDataTrAxles() {
        return tyresDataTrAxles;
    }

    public void setTyresDataTrAxles(Integer tyresDataTrAxles) {
        this.tyresDataTrAxles = tyresDataTrAxles;
    }

}
