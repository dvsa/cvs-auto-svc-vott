/*
 * Vehicles Microservice
 * This is the API spec for the vehicle microservice. Endpoints and parameters only exist for the operations getVehicle and getTechRecords. Other operations within the microservice are out of scope.
 *
 * OpenAPI spec version: 1.0.0
 * Contact: bpecete@deloittece.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package vott.models.dto.techrecords;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import java.util.Objects;

/**
 * Weights
 */

@Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-04-13T13:30:43.231Z[GMT]")
public class Weights {
  @SerializedName("axleNumber")
  private BigDecimal axleNumber = null;

  @SerializedName("parkingBrakeMrk")
  private Boolean parkingBrakeMrk = null;

  @SerializedName("weights")
  private AxleWeightProperties weights = null;

  @SerializedName("tyres")
  private AxleTyreProperties tyres = null;

  @SerializedName("brakes")
  private AxleBrakeProperties brakes = null;

  public Weights axleNumber(BigDecimal axleNumber) {
    this.axleNumber = axleNumber;
    return this;
  }

   /**
   * Used for all vehicle types - PSV, HGV and TRL
   * maximum: 99999
   * @return axleNumber
  **/
    public BigDecimal getAxleNumber() {
    return axleNumber;
  }

  public void setAxleNumber(BigDecimal axleNumber) {
    this.axleNumber = axleNumber;
  }

  public Weights parkingBrakeMrk(Boolean parkingBrakeMrk) {
    this.parkingBrakeMrk = parkingBrakeMrk;
    return this;
  }

   /**
   * Used for all vehicle types - PSV, HGV and TRL. Optional for HGV
   * @return parkingBrakeMrk
  **/
    public Boolean isParkingBrakeMrk() {
    return parkingBrakeMrk;
  }

  public void setParkingBrakeMrk(Boolean parkingBrakeMrk) {
    this.parkingBrakeMrk = parkingBrakeMrk;
  }

  public Weights weights(AxleWeightProperties weights) {
    this.weights = weights;
    return this;
  }

   /**
   * Get weights
   * @return weights
  **/
    public AxleWeightProperties getWeights() {
    return weights;
  }

  public void setWeights(AxleWeightProperties weights) {
    this.weights = weights;
  }

  public Weights tyres(AxleTyreProperties tyres) {
    this.tyres = tyres;
    return this;
  }

   /**
   * Get tyres
   * @return tyres
  **/
    public AxleTyreProperties getTyres() {
    return tyres;
  }

  public void setTyres(AxleTyreProperties tyres) {
    this.tyres = tyres;
  }

  public Weights brakes(AxleBrakeProperties brakes) {
    this.brakes = brakes;
    return this;
  }

   /**
   * Get brakes
   * @return brakes
  **/
    public AxleBrakeProperties getBrakes() {
    return brakes;
  }

  public void setBrakes(AxleBrakeProperties brakes) {
    this.brakes = brakes;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Weights weights = (Weights) o;
    return Objects.equals(this.axleNumber, weights.axleNumber) &&
        Objects.equals(this.parkingBrakeMrk, weights.parkingBrakeMrk) &&
        Objects.equals(this.weights, weights.weights) &&
        Objects.equals(this.tyres, weights.tyres) &&
        Objects.equals(this.brakes, weights.brakes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(axleNumber, parkingBrakeMrk, weights, tyres, brakes);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Weights {\n");
    
    sb.append("    axleNumber: ").append(toIndentedString(axleNumber)).append("\n");
    sb.append("    parkingBrakeMrk: ").append(toIndentedString(parkingBrakeMrk)).append("\n");
    sb.append("    weights: ").append(toIndentedString(weights)).append("\n");
    sb.append("    tyres: ").append(toIndentedString(tyres)).append("\n");
    sb.append("    brakes: ").append(toIndentedString(brakes)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
