/*
 * Test Results Microservice
 * This is the API spec for capturing test results. These test result will be stored in the AWS DynamoDB database. Authorization details will be updated once we have confirmed the security scheme we are using.
 *
 * OpenAPI spec version: 1.0.0
 * Contact: test@test.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package vott.models.dto.testresults;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import javax.annotation.processing.Generated;
import java.util.Objects;

/**
 * Mandatory only for motorcycles. 2 &#x3D; MotorBikes over 200cc or with a sidecar, N &#x3D; Not Applicable, S &#x3D; Small PSV i.e less than or equal to 22 seats, 1 &#x3D; Motorbikes upto 200cc, T &#x3D; Trailer, L &#x3D; LARGE PSV (ie. greater than 23 seats), 3 &#x3D; 3 wheelers, V &#x3D; Heavy Goods Vehicle
 */

@Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-04-13T13:44:54.508Z[GMT]")
public class CompleteTestResultsVehicleClass {
  /**
   * Gets or Sets code
   */
  @JsonAdapter(CodeEnum.Adapter.class)
  public enum CodeEnum {
    _2("2"),
    N("n"),
    S("s"),
    _1("1"),
    T("t"),
    L("l"),
    _3("3"),
    V("v"),
    _4("4"),
    _7("7"),
    _5("5"),
    P("p"),
    U("u");

    private String value;

    CodeEnum(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    public static CodeEnum fromValue(String text) {
      for (CodeEnum b : CodeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
    public static class Adapter extends TypeAdapter<CodeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final CodeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public CodeEnum read(final JsonReader jsonReader) throws IOException {
        Object value = jsonReader.nextString();
        return CodeEnum.fromValue(String.valueOf(value));
      }
    }
  }  @SerializedName("code")
  private CodeEnum code = null;

  /**
   * Gets or Sets description
   */
  @JsonAdapter(DescriptionEnum.Adapter.class)
  public enum DescriptionEnum {
    MOTORBIKES_OVER_200CC_OR_WITH_A_SIDECAR("motorbikes over 200cc or with a sidecar"),
    NOT_APPLICABLE("not applicable"),
    SMALL_PSV_IE_LESS_THAN_OR_EQUAL_TO_22_SEATS_("small psv (ie: less than or equal to 22 seats)"),
    MOTORBIKES_UP_TO_200CC("motorbikes up to 200cc"),
    TRAILER("trailer"),
    LARGE_PSV_IE_GREATER_THAN_23_SEATS_("large psv(ie: greater than 23 seats)"),
    _3_WHEELERS("3 wheelers"),
    HEAVY_GOODS_VEHICLE("heavy goods vehicle"),
    MOT_CLASS_4("MOT class 4"),
    MOT_CLASS_7("MOT class 7"),
    MOT_CLASS_5("MOT class 5"),
    PSV_OF_UNKNOWN_OR_UNSPECIFIED_SIZE("PSV of unknown or unspecified size"),
    NOT_KNOWN("Not Known");

    private String value;

    DescriptionEnum(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    public static DescriptionEnum fromValue(String text) {
      for (DescriptionEnum b : DescriptionEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
    public static class Adapter extends TypeAdapter<DescriptionEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final DescriptionEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public DescriptionEnum read(final JsonReader jsonReader) throws IOException {
        Object value = jsonReader.nextString();
        return DescriptionEnum.fromValue(String.valueOf(value));
      }
    }
  }  @SerializedName("description")
  private DescriptionEnum description = null;

  public CompleteTestResultsVehicleClass code(CodeEnum code) {
    this.code = code;
    return this;
  }

   /**
   * Get code
   * @return code
  **/
  
  public CodeEnum getCode() {
    return code;
  }

  public void setCode(CodeEnum code) {
    this.code = code;
  }

  public CompleteTestResultsVehicleClass description(DescriptionEnum description) {
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  
  public DescriptionEnum getDescription() {
    return description;
  }

  public void setDescription(DescriptionEnum description) {
    this.description = description;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CompleteTestResultsVehicleClass completeTestResultsVehicleClass = (CompleteTestResultsVehicleClass) o;
    return Objects.equals(this.code, completeTestResultsVehicleClass.code) &&
        Objects.equals(this.description, completeTestResultsVehicleClass.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, description);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CompleteTestResultsVehicleClass {\n");
    
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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
