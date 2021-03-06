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

import javax.annotation.processing.Generated;
import java.util.Objects;

/**
 * Used for TRL only
 */
@Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-04-13T13:30:43.231Z[GMT]")
public class ManufacturerDetails {
  @SerializedName("name")
  private String name = null;

  @SerializedName("address1")
  private String address1 = null;

  @SerializedName("address2")
  private String address2 = null;

  @SerializedName("postTown")
  private String postTown = null;

  @SerializedName("address3")
  private String address3 = null;

  @SerializedName("postCode")
  private String postCode = null;

  @SerializedName("emailAddress")
  private String emailAddress = null;

  @SerializedName("telephoneNumber")
  private String telephoneNumber = null;

  @SerializedName("faxNumber")
  private String faxNumber = null;

  @SerializedName("manufacturerNotes")
  private String manufacturerNotes = null;

  public ManufacturerDetails name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ManufacturerDetails address1(String address1) {
    this.address1 = address1;
    return this;
  }

   /**
   * Get address1
   * @return address1
  **/
    public String getAddress1() {
    return address1;
  }

  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  public ManufacturerDetails address2(String address2) {
    this.address2 = address2;
    return this;
  }

   /**
   * Get address2
   * @return address2
  **/
    public String getAddress2() {
    return address2;
  }

  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  public ManufacturerDetails postTown(String postTown) {
    this.postTown = postTown;
    return this;
  }

   /**
   * Get postTown
   * @return postTown
  **/
    public String getPostTown() {
    return postTown;
  }

  public void setPostTown(String postTown) {
    this.postTown = postTown;
  }

  public ManufacturerDetails address3(String address3) {
    this.address3 = address3;
    return this;
  }

   /**
   * Get address3
   * @return address3
  **/
    public String getAddress3() {
    return address3;
  }

  public void setAddress3(String address3) {
    this.address3 = address3;
  }

  public ManufacturerDetails postCode(String postCode) {
    this.postCode = postCode;
    return this;
  }

   /**
   * Get postCode
   * @return postCode
  **/
    public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

  public ManufacturerDetails emailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
    return this;
  }

   /**
   * Get emailAddress
   * @return emailAddress
  **/
    public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public ManufacturerDetails telephoneNumber(String telephoneNumber) {
    this.telephoneNumber = telephoneNumber;
    return this;
  }

   /**
   * Get telephoneNumber
   * @return telephoneNumber
  **/
    public String getTelephoneNumber() {
    return telephoneNumber;
  }

  public void setTelephoneNumber(String telephoneNumber) {
    this.telephoneNumber = telephoneNumber;
  }

  public ManufacturerDetails faxNumber(String faxNumber) {
    this.faxNumber = faxNumber;
    return this;
  }

   /**
   * Get faxNumber
   * @return faxNumber
  **/
    public String getFaxNumber() {
    return faxNumber;
  }

  public void setFaxNumber(String faxNumber) {
    this.faxNumber = faxNumber;
  }

  public ManufacturerDetails manufacturerNotes(String manufacturerNotes) {
    this.manufacturerNotes = manufacturerNotes;
    return this;
  }

   /**
   * Get manufacturerNotes
   * @return manufacturerNotes
  **/
    public String getManufacturerNotes() {
    return manufacturerNotes;
  }

  public void setManufacturerNotes(String manufacturerNotes) {
    this.manufacturerNotes = manufacturerNotes;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ManufacturerDetails manufacturerDetails = (ManufacturerDetails) o;
    return Objects.equals(this.name, manufacturerDetails.name) &&
        Objects.equals(this.address1, manufacturerDetails.address1) &&
        Objects.equals(this.address2, manufacturerDetails.address2) &&
        Objects.equals(this.postTown, manufacturerDetails.postTown) &&
        Objects.equals(this.address3, manufacturerDetails.address3) &&
        Objects.equals(this.postCode, manufacturerDetails.postCode) &&
        Objects.equals(this.emailAddress, manufacturerDetails.emailAddress) &&
        Objects.equals(this.telephoneNumber, manufacturerDetails.telephoneNumber) &&
        Objects.equals(this.faxNumber, manufacturerDetails.faxNumber) &&
        Objects.equals(this.manufacturerNotes, manufacturerDetails.manufacturerNotes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, address1, address2, postTown, address3, postCode, emailAddress, telephoneNumber, faxNumber, manufacturerNotes);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ManufacturerDetails {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    address1: ").append(toIndentedString(address1)).append("\n");
    sb.append("    address2: ").append(toIndentedString(address2)).append("\n");
    sb.append("    postTown: ").append(toIndentedString(postTown)).append("\n");
    sb.append("    address3: ").append(toIndentedString(address3)).append("\n");
    sb.append("    postCode: ").append(toIndentedString(postCode)).append("\n");
    sb.append("    emailAddress: ").append(toIndentedString(emailAddress)).append("\n");
    sb.append("    telephoneNumber: ").append(toIndentedString(telephoneNumber)).append("\n");
    sb.append("    faxNumber: ").append(toIndentedString(faxNumber)).append("\n");
    sb.append("    manufacturerNotes: ").append(toIndentedString(manufacturerNotes)).append("\n");
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
