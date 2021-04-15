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
 * Disability Discrimination Act
 */
@Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-04-13T13:30:43.231Z[GMT]")
public class Dda {
  @SerializedName("certificateIssued")
  private Boolean certificateIssued = null;

  @SerializedName("wheelchairCapacity")
  private BigDecimal wheelchairCapacity = null;

  @SerializedName("wheelchairFittings")
  private String wheelchairFittings = null;

  @SerializedName("wheelchairLiftPresent")
  private Boolean wheelchairLiftPresent = null;

  @SerializedName("wheelchairLiftInformation")
  private String wheelchairLiftInformation = null;

  @SerializedName("wheelchairRampPresent")
  private Boolean wheelchairRampPresent = null;

  @SerializedName("wheelchairRampInformation")
  private String wheelchairRampInformation = null;

  @SerializedName("minEmergencyExits")
  private BigDecimal minEmergencyExits = null;

  @SerializedName("outswing")
  private String outswing = null;

  @SerializedName("ddaSchedules")
  private String ddaSchedules = null;

  @SerializedName("seatbeltsFitted")
  private BigDecimal seatbeltsFitted = null;

  @SerializedName("ddaNotes")
  private String ddaNotes = null;

  public Dda certificateIssued(Boolean certificateIssued) {
    this.certificateIssued = certificateIssued;
    return this;
  }

   /**
   * Used only for PSV
   * @return certificateIssued
  **/
    public Boolean isCertificateIssued() {
    return certificateIssued;
  }

  public void setCertificateIssued(Boolean certificateIssued) {
    this.certificateIssued = certificateIssued;
  }

  public Dda wheelchairCapacity(BigDecimal wheelchairCapacity) {
    this.wheelchairCapacity = wheelchairCapacity;
    return this;
  }

   /**
   * Used only for PSV
   * maximum: 99
   * @return wheelchairCapacity
  **/
    public BigDecimal getWheelchairCapacity() {
    return wheelchairCapacity;
  }

  public void setWheelchairCapacity(BigDecimal wheelchairCapacity) {
    this.wheelchairCapacity = wheelchairCapacity;
  }

  public Dda wheelchairFittings(String wheelchairFittings) {
    this.wheelchairFittings = wheelchairFittings;
    return this;
  }

   /**
   * Used only for PSV
   * @return wheelchairFittings
  **/
    public String getWheelchairFittings() {
    return wheelchairFittings;
  }

  public void setWheelchairFittings(String wheelchairFittings) {
    this.wheelchairFittings = wheelchairFittings;
  }

  public Dda wheelchairLiftPresent(Boolean wheelchairLiftPresent) {
    this.wheelchairLiftPresent = wheelchairLiftPresent;
    return this;
  }

   /**
   * Used only for PSV
   * @return wheelchairLiftPresent
  **/
    public Boolean isWheelchairLiftPresent() {
    return wheelchairLiftPresent;
  }

  public void setWheelchairLiftPresent(Boolean wheelchairLiftPresent) {
    this.wheelchairLiftPresent = wheelchairLiftPresent;
  }

  public Dda wheelchairLiftInformation(String wheelchairLiftInformation) {
    this.wheelchairLiftInformation = wheelchairLiftInformation;
    return this;
  }

   /**
   * Used only for PSV
   * @return wheelchairLiftInformation
  **/
    public String getWheelchairLiftInformation() {
    return wheelchairLiftInformation;
  }

  public void setWheelchairLiftInformation(String wheelchairLiftInformation) {
    this.wheelchairLiftInformation = wheelchairLiftInformation;
  }

  public Dda wheelchairRampPresent(Boolean wheelchairRampPresent) {
    this.wheelchairRampPresent = wheelchairRampPresent;
    return this;
  }

   /**
   * Used only for PSV
   * @return wheelchairRampPresent
  **/
    public Boolean isWheelchairRampPresent() {
    return wheelchairRampPresent;
  }

  public void setWheelchairRampPresent(Boolean wheelchairRampPresent) {
    this.wheelchairRampPresent = wheelchairRampPresent;
  }

  public Dda wheelchairRampInformation(String wheelchairRampInformation) {
    this.wheelchairRampInformation = wheelchairRampInformation;
    return this;
  }

   /**
   * Used only for PSV
   * @return wheelchairRampInformation
  **/
    public String getWheelchairRampInformation() {
    return wheelchairRampInformation;
  }

  public void setWheelchairRampInformation(String wheelchairRampInformation) {
    this.wheelchairRampInformation = wheelchairRampInformation;
  }

  public Dda minEmergencyExits(BigDecimal minEmergencyExits) {
    this.minEmergencyExits = minEmergencyExits;
    return this;
  }

   /**
   * Used only for PSV
   * maximum: 99
   * @return minEmergencyExits
  **/
    public BigDecimal getMinEmergencyExits() {
    return minEmergencyExits;
  }

  public void setMinEmergencyExits(BigDecimal minEmergencyExits) {
    this.minEmergencyExits = minEmergencyExits;
  }

  public Dda outswing(String outswing) {
    this.outswing = outswing;
    return this;
  }

   /**
   * Used only for PSV
   * @return outswing
  **/
    public String getOutswing() {
    return outswing;
  }

  public void setOutswing(String outswing) {
    this.outswing = outswing;
  }

  public Dda ddaSchedules(String ddaSchedules) {
    this.ddaSchedules = ddaSchedules;
    return this;
  }

   /**
   * Used only for PSV
   * @return ddaSchedules
  **/
    public String getDdaSchedules() {
    return ddaSchedules;
  }

  public void setDdaSchedules(String ddaSchedules) {
    this.ddaSchedules = ddaSchedules;
  }

  public Dda seatbeltsFitted(BigDecimal seatbeltsFitted) {
    this.seatbeltsFitted = seatbeltsFitted;
    return this;
  }

   /**
   * Used only for PSV
   * maximum: 999
   * @return seatbeltsFitted
  **/
    public BigDecimal getSeatbeltsFitted() {
    return seatbeltsFitted;
  }

  public void setSeatbeltsFitted(BigDecimal seatbeltsFitted) {
    this.seatbeltsFitted = seatbeltsFitted;
  }

  public Dda ddaNotes(String ddaNotes) {
    this.ddaNotes = ddaNotes;
    return this;
  }

   /**
   * Used only for PSV
   * @return ddaNotes
  **/
    public String getDdaNotes() {
    return ddaNotes;
  }

  public void setDdaNotes(String ddaNotes) {
    this.ddaNotes = ddaNotes;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Dda dda = (Dda) o;
    return Objects.equals(this.certificateIssued, dda.certificateIssued) &&
        Objects.equals(this.wheelchairCapacity, dda.wheelchairCapacity) &&
        Objects.equals(this.wheelchairFittings, dda.wheelchairFittings) &&
        Objects.equals(this.wheelchairLiftPresent, dda.wheelchairLiftPresent) &&
        Objects.equals(this.wheelchairLiftInformation, dda.wheelchairLiftInformation) &&
        Objects.equals(this.wheelchairRampPresent, dda.wheelchairRampPresent) &&
        Objects.equals(this.wheelchairRampInformation, dda.wheelchairRampInformation) &&
        Objects.equals(this.minEmergencyExits, dda.minEmergencyExits) &&
        Objects.equals(this.outswing, dda.outswing) &&
        Objects.equals(this.ddaSchedules, dda.ddaSchedules) &&
        Objects.equals(this.seatbeltsFitted, dda.seatbeltsFitted) &&
        Objects.equals(this.ddaNotes, dda.ddaNotes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(certificateIssued, wheelchairCapacity, wheelchairFittings, wheelchairLiftPresent, wheelchairLiftInformation, wheelchairRampPresent, wheelchairRampInformation, minEmergencyExits, outswing, ddaSchedules, seatbeltsFitted, ddaNotes);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Dda {\n");
    
    sb.append("    certificateIssued: ").append(toIndentedString(certificateIssued)).append("\n");
    sb.append("    wheelchairCapacity: ").append(toIndentedString(wheelchairCapacity)).append("\n");
    sb.append("    wheelchairFittings: ").append(toIndentedString(wheelchairFittings)).append("\n");
    sb.append("    wheelchairLiftPresent: ").append(toIndentedString(wheelchairLiftPresent)).append("\n");
    sb.append("    wheelchairLiftInformation: ").append(toIndentedString(wheelchairLiftInformation)).append("\n");
    sb.append("    wheelchairRampPresent: ").append(toIndentedString(wheelchairRampPresent)).append("\n");
    sb.append("    wheelchairRampInformation: ").append(toIndentedString(wheelchairRampInformation)).append("\n");
    sb.append("    minEmergencyExits: ").append(toIndentedString(minEmergencyExits)).append("\n");
    sb.append("    outswing: ").append(toIndentedString(outswing)).append("\n");
    sb.append("    ddaSchedules: ").append(toIndentedString(ddaSchedules)).append("\n");
    sb.append("    seatbeltsFitted: ").append(toIndentedString(seatbeltsFitted)).append("\n");
    sb.append("    ddaNotes: ").append(toIndentedString(ddaNotes)).append("\n");
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
