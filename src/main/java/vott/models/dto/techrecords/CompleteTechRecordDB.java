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

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import java.util.Objects;

/**
 * the Tech objects as they
 */
@Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-04-13T13:30:43.231Z[GMT]")
public class CompleteTechRecordDB {
  @SerializedName("systemNumber")
  private String systemNumber = null;

  @SerializedName("partialVin")
  private String partialVin = null;

  @SerializedName("primaryVrm")
  private String primaryVrm = null;

  @SerializedName("secondaryVrms")
  private List<String> secondaryVrms = null;

  @SerializedName("vin")
  private String vin = null;

  @SerializedName("trailerId")
  private String trailerId = null;

  @SerializedName("techRecord")
  private TechRecords techRecord = null;

  public CompleteTechRecordDB systemNumber(String systemNumber) {
    this.systemNumber = systemNumber;
    return this;
  }

   /**
   * It defines the composed primary key, in combination with \&quot;vin\&quot;.
   * @return systemNumber
  **/
    public String getSystemNumber() {
    return systemNumber;
  }

  public void setSystemNumber(String systemNumber) {
    this.systemNumber = systemNumber;
  }

  public CompleteTechRecordDB partialVin(String partialVin) {
    this.partialVin = partialVin;
    return this;
  }

   /**
   * Get partialVin
   * @return partialVin
  **/
    public String getPartialVin() {
    return partialVin;
  }

  public void setPartialVin(String partialVin) {
    this.partialVin = partialVin;
  }

  public CompleteTechRecordDB primaryVrm(String primaryVrm) {
    this.primaryVrm = primaryVrm;
    return this;
  }

   /**
   * Get primaryVrm
   * @return primaryVrm
  **/
    public String getPrimaryVrm() {
    return primaryVrm;
  }

  public void setPrimaryVrm(String primaryVrm) {
    this.primaryVrm = primaryVrm;
  }

  public CompleteTechRecordDB secondaryVrms(List<String> secondaryVrms) {
    this.secondaryVrms = secondaryVrms;
    return this;
  }

  public CompleteTechRecordDB addSecondaryVrmsItem(String secondaryVrmsItem) {
    if (this.secondaryVrms == null) {
      this.secondaryVrms = new ArrayList<String>();
    }
    this.secondaryVrms.add(secondaryVrmsItem);
    return this;
  }

   /**
   * Get secondaryVrms
   * @return secondaryVrms
  **/
    public List<String> getSecondaryVrms() {
    return secondaryVrms;
  }

  public void setSecondaryVrms(List<String> secondaryVrms) {
    this.secondaryVrms = secondaryVrms;
  }

  public CompleteTechRecordDB vin(String vin) {
    this.vin = vin;
    return this;
  }

   /**
   * Used for all vehicle types - PSV, HGV, TRL, car, lgv, motorcycle
   * @return vin
  **/
    public String getVin() {
    return vin;
  }

  public void setVin(String vin) {
    this.vin = vin;
  }

  public CompleteTechRecordDB trailerId(String trailerId) {
    this.trailerId = trailerId;
    return this;
  }

   /**
   * Used only for TRL
   * @return trailerId
  **/
    public String getTrailerId() {
    return trailerId;
  }

  public void setTrailerId(String trailerId) {
    this.trailerId = trailerId;
  }

  public CompleteTechRecordDB techRecord(TechRecords techRecord) {
    this.techRecord = techRecord;
    return this;
  }

   /**
   * Get techRecord
   * @return techRecord
  **/
    public TechRecords getTechRecord() {
    return techRecord;
  }

  public void setTechRecord(TechRecords techRecord) {
    this.techRecord = techRecord;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CompleteTechRecordDB completeTechRecordDB = (CompleteTechRecordDB) o;
    return Objects.equals(this.systemNumber, completeTechRecordDB.systemNumber) &&
        Objects.equals(this.partialVin, completeTechRecordDB.partialVin) &&
        Objects.equals(this.primaryVrm, completeTechRecordDB.primaryVrm) &&
        Objects.equals(this.secondaryVrms, completeTechRecordDB.secondaryVrms) &&
        Objects.equals(this.vin, completeTechRecordDB.vin) &&
        Objects.equals(this.trailerId, completeTechRecordDB.trailerId) &&
        Objects.equals(this.techRecord, completeTechRecordDB.techRecord);
  }

  @Override
  public int hashCode() {
    return Objects.hash(systemNumber, partialVin, primaryVrm, secondaryVrms, vin, trailerId, techRecord);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CompleteTechRecordDB {\n");
    
    sb.append("    systemNumber: ").append(toIndentedString(systemNumber)).append("\n");
    sb.append("    partialVin: ").append(toIndentedString(partialVin)).append("\n");
    sb.append("    primaryVrm: ").append(toIndentedString(primaryVrm)).append("\n");
    sb.append("    secondaryVrms: ").append(toIndentedString(secondaryVrms)).append("\n");
    sb.append("    vin: ").append(toIndentedString(vin)).append("\n");
    sb.append("    trailerId: ").append(toIndentedString(trailerId)).append("\n");
    sb.append("    techRecord: ").append(toIndentedString(techRecord)).append("\n");
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
