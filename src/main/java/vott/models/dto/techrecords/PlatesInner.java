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

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import java.util.Objects;

/**
 * PlatesInner
 */

@Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-04-13T13:30:43.231Z[GMT]")
public class PlatesInner {
  @SerializedName("plateSerialNumber")
  private String plateSerialNumber = null;

  @SerializedName("plateIssueDate")
  private LocalDate plateIssueDate = null;

  /**
   * Used for all vehicle types
   */
  @JsonAdapter(PlateReasonForIssueEnum.Adapter.class)
  public enum PlateReasonForIssueEnum {
    FREE_REPLACEMENT("Free replacement"),
    REPLACEMENT("Replacement"),
    DESTROYED("Destroyed"),
    PROVISIONAL("Provisional"),
    ORIGINAL("Original"),
    MANUAL("Manual");

    private String value;

    PlateReasonForIssueEnum(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    public static PlateReasonForIssueEnum fromValue(String text) {
      for (PlateReasonForIssueEnum b : PlateReasonForIssueEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
    public static class Adapter extends TypeAdapter<PlateReasonForIssueEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final PlateReasonForIssueEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public PlateReasonForIssueEnum read(final JsonReader jsonReader) throws IOException {
        Object value = jsonReader.nextString();
        return PlateReasonForIssueEnum.fromValue(String.valueOf(value));
      }
    }
  }  @SerializedName("plateReasonForIssue")
  private PlateReasonForIssueEnum plateReasonForIssue = null;

  @SerializedName("plateIssuer")
  private String plateIssuer = null;

  @SerializedName("toEmailAddress")
  private String toEmailAddress = null;

  public PlatesInner plateSerialNumber(String plateSerialNumber) {
    this.plateSerialNumber = plateSerialNumber;
    return this;
  }

   /**
   * Used for all vehicle types
   * @return plateSerialNumber
  **/
    public String getPlateSerialNumber() {
    return plateSerialNumber;
  }

  public void setPlateSerialNumber(String plateSerialNumber) {
    this.plateSerialNumber = plateSerialNumber;
  }

  public PlatesInner plateIssueDate(LocalDate plateIssueDate) {
    this.plateIssueDate = plateIssueDate;
    return this;
  }

   /**
   * Used for all vehicle types
   * @return plateIssueDate
  **/
    public LocalDate getPlateIssueDate() {
    return plateIssueDate;
  }

  public void setPlateIssueDate(LocalDate plateIssueDate) {
    this.plateIssueDate = plateIssueDate;
  }

  public PlatesInner plateReasonForIssue(PlateReasonForIssueEnum plateReasonForIssue) {
    this.plateReasonForIssue = plateReasonForIssue;
    return this;
  }

   /**
   * Used for all vehicle types
   * @return plateReasonForIssue
  **/
    public PlateReasonForIssueEnum getPlateReasonForIssue() {
    return plateReasonForIssue;
  }

  public void setPlateReasonForIssue(PlateReasonForIssueEnum plateReasonForIssue) {
    this.plateReasonForIssue = plateReasonForIssue;
  }

  public PlatesInner plateIssuer(String plateIssuer) {
    this.plateIssuer = plateIssuer;
    return this;
  }

   /**
   * Used for all vehicle types
   * @return plateIssuer
  **/
    public String getPlateIssuer() {
    return plateIssuer;
  }

  public void setPlateIssuer(String plateIssuer) {
    this.plateIssuer = plateIssuer;
  }

  public PlatesInner toEmailAddress(String toEmailAddress) {
    this.toEmailAddress = toEmailAddress;
    return this;
  }

   /**
   * Used for all vehicle types
   * @return toEmailAddress
  **/
    public String getToEmailAddress() {
    return toEmailAddress;
  }

  public void setToEmailAddress(String toEmailAddress) {
    this.toEmailAddress = toEmailAddress;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PlatesInner platesInner = (PlatesInner) o;
    return Objects.equals(this.plateSerialNumber, platesInner.plateSerialNumber) &&
        Objects.equals(this.plateIssueDate, platesInner.plateIssueDate) &&
        Objects.equals(this.plateReasonForIssue, platesInner.plateReasonForIssue) &&
        Objects.equals(this.plateIssuer, platesInner.plateIssuer) &&
        Objects.equals(this.toEmailAddress, platesInner.toEmailAddress);
  }

  @Override
  public int hashCode() {
    return Objects.hash(plateSerialNumber, plateIssueDate, plateReasonForIssue, plateIssuer, toEmailAddress);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PlatesInner {\n");
    
    sb.append("    plateSerialNumber: ").append(toIndentedString(plateSerialNumber)).append("\n");
    sb.append("    plateIssueDate: ").append(toIndentedString(plateIssueDate)).append("\n");
    sb.append("    plateReasonForIssue: ").append(toIndentedString(plateReasonForIssue)).append("\n");
    sb.append("    plateIssuer: ").append(toIndentedString(plateIssuer)).append("\n");
    sb.append("    toEmailAddress: ").append(toIndentedString(toEmailAddress)).append("\n");
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
